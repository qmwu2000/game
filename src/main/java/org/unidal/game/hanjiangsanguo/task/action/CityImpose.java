package org.unidal.game.hanjiangsanguo.task.action;

import org.unidal.game.hanjiangsanguo.task.TaskContext;

public class CityImpose extends AbstractTaskAction {
	public static final String ID = "city.impose";

	@Override
	public int getAvailableTimes(TaskContext ctx) throws Exception {
		ctx.setDefaultCategory(ID);
		handleIndex(ctx);
		return ctx.getIntAttribute("times", 0);
	}

	@Override
	public void doAction(TaskContext ctx) throws Exception {
		handleImpose(ctx);
	}

	@Override
   public String getType() {
	   return "2";
   }

	private void handleImpose(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "city", "impose", "&e=0");

		m_helper.doGet(ctx, url);
	}

	private void handleIndex(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "city", "index", null);

		m_helper.doGet(ctx, url, "times");
	}
}
