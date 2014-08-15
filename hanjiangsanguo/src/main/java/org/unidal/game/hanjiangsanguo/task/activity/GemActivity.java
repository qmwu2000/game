package org.unidal.game.hanjiangsanguo.task.activity;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;

public class GemActivity extends AbstractTaskActivity {
	public static final String ID = "gem";

	public boolean execute(TaskContext ctx, TaskArguments args) throws Exception {
		String mapId = args.nextString(null);
		String id = args.nextString(null);

		if (check(ctx, mapId)) {
			mopUp(ctx, id);
		}
		reward(ctx);

		return true;
	}

	private void reward(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "sanctum", "get_reward", "&type=1&multiple=0");

		m_helper.doGet(ctx, url);
	}

	private void mopUp(TaskContext ctx, String id) throws Exception {
		for (int i = 0; i < 10; i++) {
			String url = m_helper.buildUrl2(ctx, "sanctum", "action", "&id=" + id);

			m_helper.doGet(ctx, url);
		}
	}

	private boolean check(TaskContext ctx, String mapId) throws Exception {
		String url = m_helper.buildUrl2(ctx, "sanctum", "select_map", "&l=" + mapId);

		m_helper.doGet(ctx, url, "times");

		int count = ctx.getIntAttribute("times", -1);

		if (count == 10) {
			return true;
		} else {
			return false;
		}
	}

}