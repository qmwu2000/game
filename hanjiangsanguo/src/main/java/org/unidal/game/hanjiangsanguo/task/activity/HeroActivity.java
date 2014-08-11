package org.unidal.game.hanjiangsanguo.task.activity;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;

public class HeroActivity extends AbstractTaskActivity {
	public static final String ID = "hero";

	public boolean execute(TaskContext ctx, TaskArguments args) throws Exception {
		int time = check(ctx);

		for (int i = 0; i < time; i++) {
			start(ctx);

			while (true) {
				boolean win = action(ctx);

				if (!win) {
					break;
				}
			}
		}

		return true;
	}

	private void start(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "herothrone", "start", null);

		m_helper.doGet(ctx, url);
	}

	private boolean action(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "herothrone", "action", null);

		m_helper.doGet(ctx, url, "info.win");

		return ctx.getIntAttribute("info.win", -1) > 0;
	}

	private int check(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "herothrone", "index", null);

		m_helper.doGet(ctx, url, "info.times");

		return ctx.getIntAttribute("info.times", -1);
	}

}