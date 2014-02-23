package org.unidal.game.hanjiangsanguo.task.core;

import org.unidal.game.hanjiangsanguo.task.Task;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.game.hanjiangsanguo.task.TaskHelper;
import org.unidal.lookup.annotation.Inject;

public class WorldbossTask implements Task {
	public static final String ID = "worldboss";

	@Inject
	private TaskHelper m_helper;

	@Override
	public void execute(TaskContext ctx) throws Exception {
		ctx.setDefaultCategory(ID);

		handleIndex(ctx);

		int countdown = ctx.getIntAttribute("countdown", -1);
		int times = countdown / 60;

		handleMatrix(ctx);

		for (int i = 0; i < times; i++) {
			long start = System.currentTimeMillis();

			handleBattle(ctx);

			long end = System.currentTimeMillis();

			Thread.sleep(60 * 1000L - (end - start));
		}

		handleReward(ctx);
	}

	private void handleBattle(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "worldboss", "battle", "&now=0");

		m_helper.doGet(ctx, url);
	}

	private void handleIndex(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "worldboss", "index", null);

		m_helper.doGet(ctx, url, "countdown");
	}

	private void handleMatrix(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "matrix", "update_matrix", "&list=%s&mid=%s", "list", "mid");

		m_helper.doGet(ctx, url);
	}

	private void handleReward(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "worldboss", "reward", null);

		m_helper.doGet(ctx, url);
	}
}
