package org.unidal.game.hanjiangsanguo.task.action;

import org.unidal.game.hanjiangsanguo.task.TaskContext;

public class IslandMission extends AbstractTaskAction {
	public static final String ID = "island.mission";

	@Override
	public void doAction(TaskContext ctx) throws Exception {
		handlePk(ctx);
	}

	@Override
	public int getAvailableTimes(TaskContext ctx) throws Exception {
		ctx.setDefaultCategory(ID);
		prepareId(ctx);

		int startAct = getStartAct(ctx);
		int act = getAct(ctx);
		int times = 0;

		if (startAct > 0) {
			while (act >= startAct) {
				act -= startAct;
				startAct++;
				times++;
			}
		}

		return Math.min(10 - startAct, times);
	}

	private int getStartAct(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "island", "get_mission", "&id=%s", "id");

		if (m_helper.doGet(ctx, url, "info.act")) {
			return ctx.getIntAttribute("info.act", 0);
		} else {
			return -1;
		}
	}

	@Override
	public String getType() {
		return "9";
	}

	private void handlePk(TaskContext ctx) throws Exception {
		assertContext(ctx, "id");

		int id = ctx.getIntAttribute("id", 0);
		String url = m_helper.buildUrl2(ctx, "island", "pk", "&id=%s", "id");

		m_helper.doGet(ctx, url, "info.win");

		if (id < 19) {
			ctx.setAttribute("id", String.valueOf(id + 1));
		}
	}

	private void prepareId(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "island", "index", null);

		m_helper.doGetWithScript(ctx, url,
		      "var id=''; for (var i in o.list) if (o.list[i].openstatus==1) id=o.list[i].id; id;", "id");
	}
}
