package org.unidal.game.hanjiangsanguo.task.core;

import org.unidal.game.hanjiangsanguo.task.Task;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.game.hanjiangsanguo.task.TaskHelper;
import org.unidal.lookup.annotation.Inject;

public class DrinkTask implements Task {
	public static final String ID = "drink";

	@Inject
	private TaskHelper m_helper;

	@Override
	public void execute(TaskContext ctx) throws Exception {
		ctx.setDefaultCategory(ID);

		hanldeGoDrink(ctx);
	}

	private void hanldeGoDrink(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "drink", "go_drink", "&type=1");

		m_helper.doGet(ctx, url);
	}
}
