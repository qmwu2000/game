package org.unidal.game.hanjiangsanguo.task.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.unidal.game.hanjiangsanguo.task.Task;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.game.hanjiangsanguo.task.TaskHelper;
import org.unidal.lookup.annotation.Inject;

public class LoginTask implements Task {
	public static final String ID = "login";

	@Inject
	private TaskHelper m_helper;

	@Override
	public void execute(TaskContext ctx) throws Exception {
		String username = ctx.getAttribute("user", "username");
		String server = ctx.getAttribute("user", "server");
		File file = new File("target/" + username + "." + server);
		boolean logined = false;

		if (file.exists()) {
			loadFromFile(ctx, file);

			logined = handleMemeber(ctx);
		}

		if (!logined) {
			handleLogin(ctx);
			handleMemeber(ctx);
			saveToFile(ctx, file);
		}
	}

	private void handleLogin(TaskContext ctx) throws Exception {
		ctx.setDefaultCategory("user");

		String loginUrl = m_helper.buildUrl3(ctx, "user", "login", "&u=%s&p=%s", "user/username", "user/password");

		m_helper.doGet(ctx, loginUrl, "uid:user");

		String tokenUrl = m_helper.buildUrl2(ctx, "login", "user", "&u=%s&p=%s", "user/username", "user/password");

		m_helper.doGet(ctx, tokenUrl, "token:user", "vip:user");
	}

	private boolean handleMemeber(TaskContext ctx) throws Exception {
		ctx.setDefaultCategory("member");

		String url = m_helper.buildUrl2(ctx, "member", "index", null);
		String[] keys = { "nickname", "level", "act", "act_limit", "silver", "gold", "rank", "soul", "gem", "jade",
		      "reputation", "missionlevel", "missionstage", "missionid" };

		boolean showAll = false;

		if (showAll) {
			return m_helper.doGet(ctx, url, "*");
		} else {
			return m_helper.doGet(ctx, url, keys);
		}
	}

	private void loadFromFile(TaskContext ctx, File file) throws IOException, FileNotFoundException {
		Properties properties = new Properties();

		properties.load(new FileReader(file));

		for (Map.Entry<Object, Object> e : properties.entrySet()) {
			ctx.setAttribute("user", e.getKey().toString(), e.getValue().toString());
		}
	}

	private void saveToFile(TaskContext ctx, File file) throws IOException {
		Properties properties = new Properties();

		properties.setProperty("uid", ctx.getAttribute("user", "uid"));
		properties.setProperty("token", ctx.getAttribute("user", "token"));
		properties.setProperty("vip", ctx.getAttribute("user", "vip"));

		properties.store(new FileWriter(file), "");
	}
}
