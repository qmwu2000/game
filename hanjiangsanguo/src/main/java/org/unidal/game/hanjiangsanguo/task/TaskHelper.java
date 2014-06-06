package org.unidal.game.hanjiangsanguo.task;

import java.net.URL;
import java.text.MessageFormat;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.codehaus.plexus.logging.LogEnabled;
import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;
import org.unidal.helper.Files;
import org.unidal.helper.Splitters;
import org.unidal.tuple.Triple;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class TaskHelper implements Initializable, LogEnabled {
	private static final String UTF_8 = "utf-8";

	private Logger m_logger;

	private ScriptEngine m_engine;

	public String buildUrl(TaskContext ctx, String pattern, String... params) {
		Object[] args = new Object[params.length];
		int index = 0;

		for (String param : params) {
			int pos = param.indexOf('/');
			String category;
			String name;
			Object value;

			if (pos > 0) {
				category = param.substring(0, pos);
				name = param.substring(pos + 1);
			} else {
				category = "system";
				name = param;
			}

			if ("system".equals(category)) {
				if ("timestamp".equals(name)) {
					value = System.currentTimeMillis();
				} else {
					value = null;
				}
			} else {
				value = ctx.getAttribute(category, name);
			}

			args[index++] = value;
		}

		return String.format(pattern, args);
	}

	public String buildUrl2(TaskContext ctx, String c, String m, String suffix, Object... params) {
		String pattern = "http://s%s.game.hanjiangsanguo.com/index.php?v=2013091306&"
		      + "c=%s&&m=%s&&token_uid=%s&token=%s&channel=150&lang=zh-cn&rand=%s" + (suffix == null ? "" : suffix);
		Object[] args = new Object[params.length + 6];
		int index = 0;

		args[index++] = ctx.getAttribute("user", "server");
		args[index++] = c;
		args[index++] = m;
		args[index++] = ctx.getAttribute("user", "uid");
		args[index++] = ctx.getAttribute("user", "token");
		args[index++] = System.currentTimeMillis();

		for (Object param : params) {
			String value = param.toString();
			int pos = value.indexOf('/');
			String category;
			String name;

			if (pos > 0) {
				category = value.substring(0, pos);
				name = value.substring(pos + 1);
			} else {
				category = ctx.getDefaultCategory();
				name = value;
			}

			args[index++] = ctx.getAttribute(category, name);
		}

		return String.format(pattern, args);
	}

	public String buildUrl3(TaskContext ctx, String c, String m, String suffix, String... params) {
		String pattern = "http://uc.game.hanjiangsanguo.com/index.php?&"
		      + "c=%s&m=%s&&token=&channel=150&lang=zh-cn&rand=%s" + (suffix == null ? "" : suffix);
		Object[] args = new Object[params.length + 6];
		int index = 0;

		args[index++] = c;
		args[index++] = m;
		args[index++] = System.currentTimeMillis();

		for (String param : params) {
			int pos = param.indexOf('/');
			String category;
			String name;

			if (pos > 0) {
				category = param.substring(0, pos);
				name = param.substring(pos + 1);
			} else {
				category = ctx.getDefaultCategory();
				name = param;
			}

			args[index++] = ctx.getAttribute(category, name);
		}

		return String.format(pattern, args);
	}

	public boolean checkStatus(TaskContext ctx, String json) throws Exception {
		Object status = getJsonValue(json, "status");
		int value = (status instanceof Double ? ((Double) status).intValue() : -9);

		ctx.setAttribute("status", String.valueOf(value));

		if (value == 1 || value == 2) {
			return true;
		} else if (value == -2) {
			return false;
		} else if ("0".equals(status) || "1".equals(status)) {
			return true;
		} else {
			m_logger.warn(json.toString());
			return false;
		}
	}

	public boolean doGet(TaskContext ctx, String url, String... mappings) throws Exception {
		String json = Files.forIO().readFrom(new URL(url).openStream(), UTF_8);

		if (url.indexOf("hitegg") > 0) {
			System.err.println(json);
		} else if (url.endsWith("#")) {
			System.err.println(json);
		}

		if ("403".equals(json)) {
			ctx.setAttribute("status", "403");

			m_logger.info("{status:" + ctx.getAttribute("status") + "}" +" GET " + url);
			return false;
		} else if (!checkStatus(ctx, json)) {
			m_logger.info("{status:" + ctx.getAttribute("status") + "}" +" GET " + url);
			return false;
		}

		setOutputAttributes(ctx, json, mappings);
		m_logger.info("{status:" + ctx.getAttribute("status") + "}" +" GET " + url);
		return true;
	}

	public void doGetWithScript(TaskContext ctx, String url, String script, String mapping) throws Exception {
		String json = Files.forIO().readFrom(new URL(url).openStream(), UTF_8);

		try {
			new Gson().fromJson(json, JsonObject.class);
		} catch (Exception e) {
			m_logger.warn(json, e);
			m_logger.info("{status:" + ctx.getAttribute("status") + "}" +" GET " + url);
			return;
		}

		if (!checkStatus(ctx, json)) {
			m_logger.info("{status:" + ctx.getAttribute("status") + "}" +" GET " + url);
			return;
		}

		Triple<String, String, String> t = parseMapping(ctx, mapping);
		String category = t.getMiddle();
		String name = t.getLast();

		Object value = m_engine.eval("var o=" + json + ";" + script);

		ctx.setAttribute(category, name, value.toString());
		m_logger.info("{status:" + ctx.getAttribute("status") + "}" +" GET " + url);
	}

	@Override
	public void enableLogging(Logger logger) {
		m_logger = logger;
	}

	private Object getJsonValue(String json, String key) throws Exception {
		String script;

		if ("*".equals(key)) {
			script = String.format("var o=%s; var keys=''; for (var i in o) keys+=i+','; keys;", json);
		} else if (key.endsWith(".*")) {
			script = String.format("var o=%s; var keys=''; for (var i in o.%s) keys+=i+','; keys;", json,
			      key.substring(0, key.length() - ".*".length()));
		} else if (key.endsWith(".@count")) {
			script = String.format("var o=%s; var count=0; for (var i in o.%s) count++; count;", json,
			      key.substring(0, key.length() - ".@count".length()));
		} else {
			script = String.format("var o=%s;o.%s;", json, key);
		}

		return m_engine.eval(script);
	}

	@Override
	public void initialize() throws InitializationException {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByExtension("js");

		m_engine = engine;
	}

	private Triple<String, String, String> parseMapping(TaskContext ctx, String mapping) {
		Triple<String, String, String> t = new Triple<String, String, String>();

		int pos1 = mapping.indexOf(':');
		int pos2 = mapping.indexOf('/');

		if (pos1 < 0) {
			t.setFirst(mapping);
			t.setMiddle(ctx.getDefaultCategory());
			t.setLast(mapping);
		} else {
			String key = mapping.substring(0, pos1);

			t.setFirst(key);

			if (pos2 > pos1) {
				t.setMiddle(mapping.substring(pos1 + 1, pos2));
				t.setLast(mapping.substring(pos2 + 1));
			} else {
				t.setMiddle(mapping.substring(pos1 + 1));
				t.setLast(key);
			}
		}

		return t;
	}

	private void setOutputAttributes(TaskContext ctx, String json, String... mappings) throws Exception {
		for (String mapping : mappings) {
			Triple<String, String, String> t = parseMapping(ctx, mapping);
			String key = t.getFirst();
			String category = t.getMiddle();
			String name = t.getLast();
			Object value = null;

			try {
				value = getJsonValue(json, key);
			} catch (Exception e) {
				System.err.println(e);
			}

			if (value != null) {
				if ("*".equals(key)) {
					List<String> properties = Splitters.by(',').noEmptyItem().split(value.toString());

					for (String property : properties) {
						Object val = getJsonValue(json, property);

						ctx.setAttribute(category, property, val.toString());
					}
				} else if (key.endsWith(".*")) {
					List<String> properties = Splitters.by(',').noEmptyItem().split(value.toString());
					String prefix = key.substring(0, key.length() - 1);

					for (String property : properties) {
						Object val = getJsonValue(json, prefix + property);

						ctx.setAttribute(category, prefix + property, val.toString());
					}
				} else {
					if (value instanceof Number) {
						ctx.setAttribute(category, name, new MessageFormat("{0,number,0.#}").format(new Object[] { value }));
					} else {
						ctx.setAttribute(category, name, value.toString());
					}
				}
			}
		}
	}
}
