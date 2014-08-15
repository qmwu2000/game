package org.unidal.game.hanjiangsanguo.task.action;

import org.unidal.game.hanjiangsanguo.task.TaskContext;

public class TavernTrade extends AbstractTaskAction {
	public static final String ID = "tavern.trade";

	@Override
	public int getAvailableTimes(TaskContext ctx) throws Exception {
		int silver = getSilver(ctx);

		if (silver > 0) {
			return silver / 25000;
		} else {
			return 0;
		}
	}

	@Override
	public void doAction(TaskContext ctx) throws Exception {
		handleTrade(ctx);
	}

	@Override
	public String getType() {
		return "11";
	}

	private void handleTrade(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "tavern", "trade", null);

		m_helper.doGet(ctx, url);
	}
}
