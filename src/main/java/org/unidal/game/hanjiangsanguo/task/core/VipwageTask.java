package org.unidal.game.hanjiangsanguo.task.core;

import org.unidal.game.hanjiangsanguo.task.Task;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.game.hanjiangsanguo.task.TaskHelper;
import org.unidal.lookup.annotation.Inject;

public class VipwageTask implements Task {
	public static final String ID = "vipwage";

	@Inject
	private TaskHelper m_helper;

	@Override
	public void execute(TaskContext ctx) throws Exception {
		ctx.setDefaultCategory(ID);

		handleIndex(ctx);

		String get = ctx.getAttribute("get");
		int result = (int) Double.parseDouble(get);

		if (result == 1) {
			handleGetVipWage(ctx);
		}
	}

	private void handleIndex(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "vipwage", "index", null);

		m_helper.doGet(ctx, url, "get");
	}

	private void handleGetVipWage(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "vipwage", "get_vip_wage", null);

		m_helper.doGet(ctx, url);
	}
}
