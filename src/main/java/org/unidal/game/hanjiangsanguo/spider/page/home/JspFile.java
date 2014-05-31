package org.unidal.game.hanjiangsanguo.spider.page.home;

public enum JspFile {
	VIEW("/jsp/spider/home.jsp"),

	;

	private String m_path;

	private JspFile(String path) {
		m_path = path;
	}

	public String getPath() {
		return m_path;
	}
}
