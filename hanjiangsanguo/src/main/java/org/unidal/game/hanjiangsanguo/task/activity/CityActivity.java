package org.unidal.game.hanjiangsanguo.task.activity;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;

public class CityActivity extends AbstractTaskActivity {
	public static final String ID = "city";

	private boolean doImpose(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "city", "impose", "&e=0");

		return m_helper.doGet(ctx, url);
	}

	private boolean doImpress(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "city", "impress", "&e=0");

		return m_helper.doGet(ctx, url);
	}

	public boolean execute(TaskContext ctx, TaskArguments args) throws Exception {
		String op = args.nextString(null);

		ensure(op != null);
		ctx.setDefaultCategory(ID);

		if ("impose".equals(op)) {
			int times = args.nextInt(1);

			for (int i = 0; i < times; i++) {
				if (!doImpose(ctx)) {
					return false;
				}
			}
		} else if ("impress".equals(op)) {
			int times = args.nextInt(1);

			for (int i = 0; i < times; i++) {
				if (!doImpress(ctx)) {
					return false;
				}
			}
		}

		return true;
	}
}
