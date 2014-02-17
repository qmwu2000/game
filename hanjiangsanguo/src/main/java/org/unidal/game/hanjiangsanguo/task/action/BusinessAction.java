package org.unidal.game.hanjiangsanguo.task.action;

import org.unidal.game.hanjiangsanguo.task.TaskContext;

public class BusinessAction extends AbstractTaskAction {
	public static final String ID = "business.go_business";

	@Override
	public int getAvailableTimes(TaskContext ctx) throws Exception {
		ctx.setDefaultCategory(ID);
		handleIndex(ctx);
		return ctx.getIntAttribute("times", 0);
	}

	@Override
	public void doAction(TaskContext ctx) throws Exception {
		handleGoBusiness(ctx);
	}

	@Override
   public String getType() {
	   return "4";
   }

	private void handleGoBusiness(TaskContext ctx) throws Exception {
		ctx.setDefaultCategory(ID);
		String url = m_helper.buildUrl2(ctx, "business", "go_business", "&id=%s", "trader[0].id");

		m_helper.doGet(ctx, url);
	}

	private void handleIndex(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "business", "index", null);

		m_helper.doGet(ctx, url, "times", "trader[0].id");
	}
}
