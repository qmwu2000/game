package org.unidal.game.hanjiangsanguo.task;

public interface TaskAction {
	public int getPriority();
	
	public String getType();
	
	public int getAvailableTimes(TaskContext ctx) throws Exception;

	public void doAction(TaskContext ctx) throws Exception;
}
