package org.unidal.game.hanjiangsanguo.router;

import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.unidal.lookup.util.StringUtils;

public class RequestHolder {

	private String protocol;

	private String host;

	private int port;

	private String path;

	private String query;

	private Map<String, List<String>> headers = new HashMap<String, List<String>>();

	private String virtualServer;

	private Cookie[] cookies;

	@SuppressWarnings("rawtypes")
	private Enumeration names;

	private HttpServletRequest m_req;

	public RequestHolder(URL url, RequestHolder originalHolder) {
		protocol = url.getProtocol();
		host = url.getHost();
		port = url.getPort();
		path = url.getPath();
		query = url.getQuery();
		this.headers = originalHolder.headers;
		this.virtualServer = originalHolder.virtualServer;
	}

	@SuppressWarnings("unchecked")
	public RequestHolder(HttpServletRequest req) {
		m_req = req;
		protocol = req.getScheme();
		// the first filter F5 filter will replace host with target pool
		host = "127.0.0.1";
		port = req.getLocalPort();
		path = req.getRequestURI();
		query = req.getQueryString();

		cookies = req.getCookies();

		names = req.getHeaderNames();
		if (req.getCookies() != null) {
			for (Cookie cookie : req.getCookies()) {
				if ("phoenixVirtualServer".equals(cookie.getName())) {
					virtualServer = cookie.getValue();
					break;
				}
			}
		}
		Enumeration<String> names = req.getHeaderNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			Enumeration<String> values = req.getHeaders(name);
			List<String> valueList = new ArrayList<String>();
			while (values.hasMoreElements()) {
				valueList.add(values.nextElement());
			}
			headers.put(name, valueList);
		}
	}

	public RequestHolder() {
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Map<String, List<String>> getHeaders() {
		return headers;
	}

	public String getVirtualServer() {
		return virtualServer;
	}

	public void setVirtualServer(String virtualServer) {
		this.virtualServer = virtualServer;
	}

	public String hanjiangUrl() {
		String youxihost = m_req.getServerName();
		String imgHost = "img2.hanjiangsanguo.com";

		StringBuilder sb = new StringBuilder();
		sb.append(protocol);
		sb.append("://");

		host = youxihost;

		sb.append(host);
		sb.append(path);
		sb.append("?");
		if (!StringUtils.isEmpty(query)) {
			sb.append(query);
		}

		if (sb.indexOf("png") > -1) {
			String result = sb.toString().replace(youxihost, imgHost);

			return result;
		}
		return sb.toString();
	}

	public String toUrl() {
		StringBuilder sb = new StringBuilder();
		sb.append(protocol);
		sb.append("://");
		sb.append(host);
		if (port > 0) {
			sb.append(":");
			sb.append(port);
		}
		sb.append(path);
		if (!StringUtils.isEmpty(query)) {
			sb.append(query);
		}
		return sb.toString();
	}

	public Cookie[] getCookies() {
		return cookies;
	}

	@SuppressWarnings("rawtypes")
   public Enumeration getNames() {
		return names;
	}

}