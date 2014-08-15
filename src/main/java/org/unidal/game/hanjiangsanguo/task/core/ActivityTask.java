package org.unidal.game.hanjiangsanguo.task.core;

import org.unidal.game.hanjiangsanguo.task.Task;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.game.hanjiangsanguo.task.TaskHelper;
import org.unidal.lookup.annotation.Inject;

public class ActivityTask implements Task {
	public static final String ID = "activity";

	@Inject
	private TaskHelper m_helper;

	@Override
	public void execute(TaskContext ctx) throws Exception {
		ctx.setDefaultCategory(ID);

		String indexUrl = m_helper.buildUrl(ctx, "activity", "index", null);

		m_helper.doGet(ctx, indexUrl, "list.*");
	}
}
