package org.unidal.game.hanjiangsanguo.task.action;

import org.unidal.game.hanjiangsanguo.task.TaskContext;

public class MapMission extends AbstractTaskAction {
	public static final String ID = "map.mission";

	@Override
	public int getAvailableTimes(TaskContext ctx) throws Exception {
		int act = getAct(ctx);

		return Math.min(10, act);
	}

	@Override
	public void doAction(TaskContext ctx) throws Exception {
		handleAction(ctx);
	}

	@Override
	public String getType() {
		return "6";
	}

	private void handleAction(TaskContext ctx) throws Exception {
		ctx.setDefaultCategory(ID);
		String url = m_helper.buildUrl2(ctx, "map", "action", "&l=%s&s=%s&id=%s", "member/missionlevel",
		      "member/missionstage", "member/missionid");

		m_helper.doGet(ctx, url, "info.win", "info.name");
	}
}
