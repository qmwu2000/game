package org.unidal.game.hanjiangsanguo.task.activity;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;

public class HeavenActivity extends AbstractTaskActivity {
	public static final String ID = "heaven";

	public boolean execute(TaskContext ctx, TaskArguments args) throws Exception {
		String levelId = args.nextString(null);

		if (check(ctx, levelId)) {
			mopUp(ctx, levelId);
		}
		reward(ctx);

		return true;
	}

	private void reward(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "heaven", "get_reward", "&type=0");

		m_helper.doGet(ctx, url);
	}

	private void mopUp(TaskContext ctx, String levelId) throws Exception {
		String url = m_helper.buildUrl2(ctx, "heaven", "mop_up", "&id=" + levelId + "&times=" + 10);

		m_helper.doGet(ctx, url);
	}

	private boolean check(TaskContext ctx, String mapId) throws Exception {
		String url = m_helper.buildUrl2(ctx, "heaven", "get_mopup_price", "&id=" + mapId);

		m_helper.doGet(ctx, url, "info.ten_sub");

		int count = ctx.getIntAttribute("info.ten_sub", -1);

		if (count == 0) {
			return true;
		} else {
			return false;
		}
	}

}