package org.unidal.game.hanjiangsanguo.task.core;

import org.unidal.game.hanjiangsanguo.task.Task;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.game.hanjiangsanguo.task.TaskHelper;
import org.unidal.lookup.annotation.Inject;

public class GeneralTask implements Task {
	public static final String ID = "general";

	@Inject
	private TaskHelper m_helper;

	@Override
	public void execute(TaskContext ctx) throws Exception {
		ctx.setDefaultCategory(ID);

		handleIndex(ctx);

		int times = ctx.getIntAttribute("number", 0);

		for (int i = 0; i < times; i++) {
			handleAction(ctx);
		}
	}

	private void handleAction(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "generaltask", "action", "&id=%s&type=0&gid=%s", "id", "gid");

		m_helper.doGet(ctx, url);
	}

	private void handleIndex(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "generaltask", "index", null);

		m_helper.doGet(ctx, url, "number");
	}
}
