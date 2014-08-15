package org.unidal.game.hanjiangsanguo.task.action;

import org.unidal.game.hanjiangsanguo.task.TaskContext;

public class ArenaAction extends AbstractTaskAction {
	public static final String ID = "arena.action";

	@Override
	public int getAvailableTimes(TaskContext ctx) throws Exception {
		ctx.setDefaultCategory(ID);
		handleIndex(ctx);

		int times = ctx.getIntAttribute("log.times", 0);

		return Math.min(12, times);
	}

	@Override
	public void doAction(TaskContext ctx) throws Exception {
		handleAction(ctx);
	}

	@Override
	public String getType() {
		return "10";
	}

	private void handleAction(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "arena", "action", "&t=%s", "list[5].uid");

		m_helper.doGet(ctx, url);
	}

	private void handleIndex(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "arena", "index", null);

		m_helper.doGet(ctx, url, "log.times", "list[5].uid");
	}
}
