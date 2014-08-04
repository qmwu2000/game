package org.unidal.game.hanjiangsanguo.router;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.unidal.helper.Files;

@SuppressWarnings("serial")
public class RouteServlet extends HttpServlet {

	private static String GET = "get";

	private static String POST = "post";

	@Override
	public void init() throws ServletException {
		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		proxyRequest(req, resp, GET);
	}

	public byte[] compressToByte(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip;
		try {
			gzip = new GZIPOutputStream(out);
			gzip.write(str.getBytes("utf-8"));
			gzip.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}

	private void proxyRequest(HttpServletRequest req, HttpServletResponse resp, String type) throws IOException {
		RequestHolder holder = new RequestHolder(req);
		System.out.println(req.getServerName());

		String content = null;
		InputStream in = null;
		if (isMyUrl(req, holder)) {
			String userName = req.getParameter("u");
			String password = req.getParameter("p");

			System.out.println(userName + " " + password);
			URL url = new URL(holder.hanjiangUrl() + "&u=" + userName + "&p=" + password
			      + "&mac=00:00:00:00:00:00&devicetoken=000000");
			URLConnection con = url.openConnection();

			Cookie[] cookies = req.getCookies();

			if (cookies != null) {
				for (Cookie c : cookies) {
					con.setRequestProperty(c.getName(), c.getValue());

					resp.addCookie(c);
				}
			}
			req.getHeaderNames();

			in = con.getInputStream();
		} else {

			String uid = req.getParameter("uid");
			URL url = new URL(holder.hanjiangUrl() + "&mac=00:00:00:00:00:00&devicetoken=000000" + "&uid=" + uid);
			URLConnection con = url.openConnection();

			System.out.println(url);
			Cookie[] cookies = req.getCookies();

			if (cookies != null) {
				for (Cookie c : cookies) {
					con.setRequestProperty(c.getName(), c.getValue());

					resp.addCookie(c);
				}
			}
			req.getHeaderNames();

			in = con.getInputStream();
		}
		content = Files.forIO().readFrom(in, "utf-8");
		content = content.replace("\"0\"", "\"5\"");
		content = content.replace("\"1\"", "\"5\"");
		content = content.replace("\"2\"", "\"5\"");
		content = content.replace("\"3\"", "\"5\"");
		content = content.replace("\"4\"", "\"5\"");

		resp.setContentType("application/json");
		resp.addHeader("Access-Control-Allow-Origin", "*");
		resp.addHeader("Content-Type", "application/json");
		resp.addHeader("Server", "nginx/1.2.2");
		resp.addHeader("X-Powered-By", "PHP/5.4.5");
		resp.getWriter().write(content);
		resp.getWriter().flush();
		resp.setStatus(200);
	}

	private boolean isMyUrl(HttpServletRequest req, RequestHolder holder) {
		String query = holder.hanjiangUrl();

		if (query != null && query.indexOf("c=login&&m=user") > -1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected long getLastModified(HttpServletRequest req) {
		return super.getLastModified(req);
	}

	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doHead(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		proxyRequest(req, resp, POST);
	}

}
