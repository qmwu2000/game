package org.unidal.game.hanjiangsanguo.task.core;

import org.unidal.game.hanjiangsanguo.task.Task;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.game.hanjiangsanguo.task.TaskHelper;
import org.unidal.lookup.annotation.Inject;

public class LotteryTask implements Task {
	public static final String ID = "lottery";

	@Inject
	private TaskHelper m_helper;

	@Override
	public void execute(TaskContext ctx) throws Exception {
		ctx.setDefaultCategory(ID);

		handleIndex(ctx);

		int total = ctx.getIntAttribute("log.info.lavenumber", 0);

		for (int i = 0; i < total; i++) {
			handleAction(ctx);
		}
	}

	private void handleAction(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "lottery", "action", null);

		m_helper.doGet(ctx, url);
	}

	private void handleIndex(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "lottery", "index", null);

		m_helper.doGet(ctx, url, "log.info.lavenumber");
	}
}
