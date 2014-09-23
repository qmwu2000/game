package org.unidal.game.hanjiangsanguo.task.activity;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;

public class MapActivity extends AbstractTaskActivity {
	public static final String ID = "map";

	private boolean doAction(TaskContext ctx, int level, int stage, int id) throws Exception {
		String url = m_helper.buildUrl2(ctx, "map", "action", String.format("&l=%s&s=%s&id=%s", level, stage, id));
		int times = 2;
		int win = -1;

		while (times > 0 && win < 0) {
			m_helper.doGet(ctx, url, "info.win");
			win = ctx.getIntAttribute("info.win", 0);
			times--;
		}

		return win > 0;
	}

	private void doIsland(TaskContext ctx, int maxAct) throws Exception {
		int actNum = ctx.getIntAttribute("member", "act", 0);

		if (actNum > 1000) {
			maxAct = 15;
		} else if (actNum > 300) {
			maxAct = 12;
		} else if (actNum > 100) {
			maxAct = 10;
		} else {
			maxAct = 6;
		}
		
		while (true) {
			String indexUrl = m_helper.buildUrl2(ctx, "island", "index", null);

			m_helper.doGetWithScript(ctx, indexUrl,
			      "var id=''; for (var i in o.list) if (o.list[i].openstatus==1) id=o.list[i].id; id;", "id");

			String id = ctx.getAttribute("id");

			String missionUrl = m_helper.buildUrl2(ctx, "island", "get_mission", String.format("&id=%s", id));

			m_helper.doGet(ctx, missionUrl, "info.act");

			int act = ctx.getIntAttribute("info.act", Integer.MAX_VALUE);
			boolean flag = false;

			if (act <= maxAct) {
				String url = m_helper.buildUrl2(ctx, "island", "pk", String.format("&id=%s", id));

				m_helper.doGet(ctx, url, "info.win");

				if (ctx.getIntAttribute("info.win", 0) > 0) {
					flag = true;
				}
			}

			if (!flag) {
				break;
			}
		}
	}

	private boolean doNewResult(TaskContext ctx, int id) throws Exception {
		String url = m_helper.buildUrl2(ctx, "map", "get_newresult", String.format("&id=%s", id));

		return m_helper.doGet(ctx, url);
	}

	private boolean doReward(TaskContext ctx, int id) throws Exception {
		String url = m_helper.buildUrl2(ctx, "map", "get_mission_reward", String.format("&id=%s", id));

		return m_helper.doGet(ctx, url);
	}

	public boolean execute(TaskContext ctx, TaskArguments args) throws Exception {
		String op = args.nextString(null);

		ensure(op != null);
		ctx.setDefaultCategory(ID);

		if ("action".equals(op)) {
			int level = args.nextInt(0);
			int stage = args.nextInt(0);
			int fromId = args.nextInt(0);
			int toId = args.nextInt(fromId);

			ensure(level > 0, stage > 0, fromId > 0);

			for (int i = fromId; i <= toId; i++) {
				if (!doAction(ctx, level, stage, i)) {
					return false;
				}
			}
		} else if ("reward".equals(op)) {
			int id = args.nextInt(0);

			doReward(ctx, id);
		} else if ("newresult".equals(op)) {
			int id = args.nextInt(0);

			doNewResult(ctx, id);
		} else if ("island".equals(op)) {
			int maxAct = args.nextInt(0);

			doIsland(ctx, maxAct);
		}

		return true;
	}
}
