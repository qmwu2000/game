package org.unidal.game.hanjiangsanguo.task.activity;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;

public class SoulActivity extends AbstractTaskActivity {
	public static final String ID = "soul";

	public boolean execute(TaskContext ctx, TaskArguments args) throws Exception {
		String mapId = args.nextString(null);

		if (check(ctx, mapId)) {
			mopUp(ctx, mapId);
		}
		reward(ctx);

		return true;
	}

	private void reward(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "tower", "get_reward", "&type=3");

		m_helper.doGet(ctx, url);
	}

	private void mopUp(TaskContext ctx, String mapId) throws Exception {
		String url = m_helper.buildUrl2(ctx, "tower", "mop_up", "&id=" + mapId + "&times=" + 10);

		m_helper.doGet(ctx, url);
	}

	private boolean check(TaskContext ctx, String mapId) throws Exception {
		String url = m_helper.buildUrl2(ctx, "tower", "get_mopup_price", "&id=" + mapId);

		m_helper.doGet(ctx, url, "info.ten_sub");

		int count = ctx.getIntAttribute("info.ten_sub", -1);

		if (count == 0) {
			return true;
		} else {
			return false;
		}
	}

}