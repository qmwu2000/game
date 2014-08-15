package org.unidal.game.hanjiangsanguo.task.action;

import org.unidal.game.hanjiangsanguo.task.TaskContext;

public class MineOccupy extends AbstractDurableTaskAction {
	public static final String ID = "mine.occupy";

	@Override
	public boolean check(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "mine", "index", null);

		m_helper.doGet(ctx, url, "log.type");

		return "2".equals(ctx.getAttribute("log.type"));
	}

	@Override
	public int getAvailableTimes(TaskContext ctx) throws Exception {
		int act = getAct(ctx);

		return Math.min(7, act);
	}

	@Override
	public long getCheckInterval() {
		return MINUTE;
	}

	@Override
	public long getMaxDuration() {
		return 30 * MINUTE;
	}

	@Override
	public String getType() {
		return "3";
	}

	@Override
	public boolean start(TaskContext ctx) throws Exception {
		ctx.setDefaultCategory(ID);

		prepare(ctx);

		String url = m_helper.buildUrl2(ctx, "mine", "index", "&p=1&t=2&id=%s", "id");

		m_helper.doGet(ctx, url, "status");

		return "1".equals(ctx.getAttribute("status"));
	}

	private void prepare(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "mine", "index", null);

		m_helper.doGetWithScript(ctx, url,
		      "var id=''; for (var i in o.list) if (o.list[i].status==0) {id=o.list[i].id;break;} id;", "id");
	}

	@Override
	public void end(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "mine", "give_up", null);

		m_helper.doGet(ctx, url, "silver");
	}
}
