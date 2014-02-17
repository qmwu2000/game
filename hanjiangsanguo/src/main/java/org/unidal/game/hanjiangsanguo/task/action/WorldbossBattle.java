package org.unidal.game.hanjiangsanguo.task.action;

import org.unidal.game.hanjiangsanguo.task.TaskContext;

public class WorldbossBattle extends AbstractTaskAction {
	public static final String ID = "tavern.trade";

	@Override
	public int getAvailableTimes(TaskContext ctx) throws Exception {
		return 1;
	}

	@Override
	public void doAction(TaskContext ctx) throws Exception {
		// not right time
	}

	@Override
	public String getType() {
		return "12";
	}
}
