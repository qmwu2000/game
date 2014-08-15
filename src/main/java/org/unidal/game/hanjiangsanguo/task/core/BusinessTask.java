package org.unidal.game.hanjiangsanguo.task.core;

import org.unidal.game.hanjiangsanguo.task.Task;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.game.hanjiangsanguo.task.TaskHelper;
import org.unidal.lookup.annotation.Inject;

public class BusinessTask implements Task {
	public static final String ID = "business";

	@Inject
	private TaskHelper m_helper;

	@Override
	public void execute(TaskContext ctx) throws Exception {
		ctx.setDefaultCategory(ID);

		handleIndex(ctx);

		int times = ctx.getIntAttribute("times", 0);

		for (int i = 0; i < times; i++) {
			handleGoBusiness(ctx);
		}
	}

	private void handleGoBusiness(TaskContext ctx) throws Exception {
		int id = ctx.getIntAttribute("trader[0].id", 0);
		String url = m_helper.buildUrl2(ctx, ID, "go_business", "&id=" + id);

		m_helper.doGet(ctx, url, "trader[0].id", "trader[1].id", "trader[2].id");
	}

	private void handleIndex(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, ID, "index", null);

		m_helper.doGet(ctx, url, "times", "trader[0].id", "trader[1].id", "trader[2].id");
	}
}
