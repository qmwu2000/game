package org.unidal.game.hanjiangsanguo.task.activity;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;

public class MapActivity extends AbstractTaskActivity {
	public static final String ID = "map";

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
		}

		return true;
	}

	private boolean doNewResult(TaskContext ctx, int id) throws Exception {
		String url = m_helper.buildUrl2(ctx, "map", "get_newresult", String.format("&id=%s", id));

		return m_helper.doGet(ctx, url);
	}
	
	private boolean doReward(TaskContext ctx, int id) throws Exception {
		String url = m_helper.buildUrl2(ctx, "map", "get_mission_reward", String.format("&id=%s", id));
		
		return m_helper.doGet(ctx, url);
	}

	private boolean doAction(TaskContext ctx, int level, int stage, int id) throws Exception {
		String url = m_helper.buildUrl2(ctx, "map", "action", String.format("&l=%s&s=%s&id=%s", level, stage, id));

		m_helper.doGet(ctx, url, "info.win");

		return ctx.getIntAttribute("info.win", 0) > 0;
	}
}
