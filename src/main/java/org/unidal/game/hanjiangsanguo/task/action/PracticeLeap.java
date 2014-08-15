package org.unidal.game.hanjiangsanguo.task.action;

import org.unidal.game.hanjiangsanguo.task.TaskContext;

public class PracticeLeap extends AbstractTaskAction {
	public static final String ID = "practice.leap";

	@Override
	public int getAvailableTimes(TaskContext ctx) throws Exception {
		ctx.setDefaultCategory(ID);
		handleIndex(ctx);
		return ctx.getIntAttribute("freetimes", 0);
	}

	@Override
	public void doAction(TaskContext ctx) throws Exception {
		handleGoLeap(ctx);
	}

	@Override
   public String getType() {
	   return "1";
   }

	private void handleGoLeap(TaskContext ctx) throws Exception {
		assertContext(ctx, "payload/gid");

		String url = m_helper.buildUrl2(ctx, "practice", "go_leap", "&gid=%s", "payload/gid");

		m_helper.doGet(ctx, url);
	}

	private void handleIndex(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "practice", "index", null);

		m_helper.doGet(ctx, url, "freetimes");
	}
}
