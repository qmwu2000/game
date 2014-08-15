package org.unidal.game.hanjiangsanguo.task.core;

import org.unidal.game.hanjiangsanguo.task.Task;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.game.hanjiangsanguo.task.TaskHelper;
import org.unidal.lookup.annotation.Inject;

public class TavernTask implements Task {
	public static final String ID = "tavern";

	@Inject
	private TaskHelper m_helper;

	@Override
	public void execute(TaskContext ctx) throws Exception {
		String action = ctx.getAttribute("tavern", "action");

		if ("buy".equals(action)) {
			handleBuy(ctx);
		} else {
			handleTrade(ctx);
		}
	}

	private void handleBuy(TaskContext ctx) throws Exception {
		ctx.setDefaultCategory(ID);

		String url = m_helper.buildUrl2(ctx, "tavern", "buy", "&generalid=%s", "generalid");

		m_helper.doGet(ctx, url);
	}

	private void handleTrade(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "tavern", "trade", null);

		m_helper.doGet(ctx, url);
	}
}
