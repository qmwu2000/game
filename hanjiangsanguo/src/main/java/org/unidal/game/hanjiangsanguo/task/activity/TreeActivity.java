package org.unidal.game.hanjiangsanguo.task.activity;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;

public class TreeActivity extends AbstractTaskActivity {
	public static final String ID = "tree";

	public boolean execute(TaskContext ctx, TaskArguments args) throws Exception {
		ctx.setDefaultCategory(ID);

		if (isLastDay(ctx)) {
			int times = 0;

			while (true) {
				int id = ctx.getIntAttribute("tree.tree_id", -1);

				if (id == 1) {
					handleWatering(ctx);
					handleIndex(ctx);
					times++;
				} else {
					break;
				}
			}

			if (times > 0) {
				System.err.println(times + " Times!");
			}
		}

		return true;
	}

	private boolean handleIndex(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "sacredtree", "index", null);

		return m_helper.doGet(ctx, url, "cd", "exp", "tree.tree_id");
	}

	private boolean handleWatering(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "sacredtree", "watering", "&type=1");

		return m_helper.doGet(ctx, url, "cd", "exp", "tree.tree_id");
	}

	private boolean isLastDay(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "sacredtree", "index", null);

		m_helper.doGet(ctx, url, "cd", "tree.tree_id");

		int cd = ctx.getIntAttribute("cd", -1);

		if (cd > 0 && cd < 86400) {
			return true;
		} else {
			return false;
		}
	}
}
