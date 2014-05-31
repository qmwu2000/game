package org.unidal.game.hanjiangsanguo.task.core;

import org.unidal.game.hanjiangsanguo.task.Task;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.game.hanjiangsanguo.task.TaskHelper;
import org.unidal.lookup.annotation.Inject;

public class WorkshopTask implements Task {
	public static final String ID = "workshop";

	@Inject
	private TaskHelper m_helper;

	@Override
	public void execute(TaskContext ctx) throws Exception {
		ctx.setDefaultCategory(ID);

		handleIndex(ctx);

		int count = ctx.getIntAttribute("list.@count", 0);

		for (int i = 0; i < count; i++) {
			ctx.setAttribute("site", String.valueOf(i + 1));

			handleGetReward(ctx);
		}
	}

	private void handleGetReward(TaskContext ctx) throws Exception {
		String actionUrl = m_helper.buildUrl2(ctx, ID, "get_reward", "&s=%s", "site");

		m_helper.doGet(ctx, actionUrl);
	}

	private void handleIndex(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, ID, "index", null);

		m_helper.doGet(ctx, url, "list.@count");
	}
}
