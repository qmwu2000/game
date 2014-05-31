package org.unidal.game.hanjiangsanguo.task.core;

import org.unidal.game.hanjiangsanguo.task.Task;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.game.hanjiangsanguo.task.TaskHelper;
import org.unidal.lookup.annotation.Inject;

public class SiegeTask implements Task {
	public static final String ID = "siege";

	@Inject
	private TaskHelper m_helper;

	@Override
	public void execute(TaskContext ctx) throws Exception {
		ctx.setDefaultCategory(ID);

		handleIndex(ctx);

		handleGetReward(ctx);
	}

	private void handleGetReward(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, ID, "get_reward", "&type=1");

		m_helper.doGet(ctx, url);
	}

	private void handleIndex(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, ID, "index", null);

		m_helper.doGet(ctx, url);
	}
}
