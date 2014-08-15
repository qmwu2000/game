package org.unidal.game.hanjiangsanguo.task;

public interface TaskActionManager {
	public void execute(TaskContext ctx, String type, int times) throws Exception;

	public int getPriority(TaskContext ctx, String type, int times) throws Exception;
}
