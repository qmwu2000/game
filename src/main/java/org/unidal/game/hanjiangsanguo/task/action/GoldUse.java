package org.unidal.game.hanjiangsanguo.task.action;

import org.unidal.game.hanjiangsanguo.task.TaskContext;

public class GoldUse extends AbstractTaskAction {
	public static final String ID = "gold.use";

	@Override
	public int getAvailableTimes(TaskContext ctx) throws Exception {
		return 0;
	}

	@Override
	public void doAction(TaskContext ctx) throws Exception {
		// not supported
	}

	@Override
	public String getType() {
		return "5";
	}
}
