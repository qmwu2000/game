package org.unidal.game.hanjiangsanguo.task;

public interface TaskContext {
	public String getAttribute(String name);

	public String getAttribute(String category, String name);

	public String getDefaultCategory();

	public int getIntAttribute(String name, int defaultValue);

	public void setAttribute(String name, String value);

	public void setAttribute(String category, String name, String value);

	public void setDefaultCategory(String defaultCategory);
}
