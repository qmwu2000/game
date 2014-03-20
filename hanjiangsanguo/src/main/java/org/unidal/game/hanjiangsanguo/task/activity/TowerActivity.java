package org.unidal.game.hanjiangsanguo.task.activity;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;

public class TowerActivity extends AbstractTaskActivity {
	public static final String ID = "tower";

	private boolean doPK(TaskContext ctx, int id) throws Exception {
		String url = m_helper.buildUrl2(ctx, "tower", "pk", String.format("&id=%s", id));

		return m_helper.doGet(ctx, url, "info.win");
	}

	public boolean execute(TaskContext ctx, TaskArguments args) throws Exception {
		String op = args.nextString(null);

		ensure(op != null);
		ctx.setDefaultCategory(ID);

		if ("pk".equals(op)) {
			int fromId = args.nextInt(0);
			int toId = args.nextInt(fromId);

			for (int id = fromId; id <= toId; id++) {
				doPK(ctx, id);
			}
		}

		return true;
	}
}
