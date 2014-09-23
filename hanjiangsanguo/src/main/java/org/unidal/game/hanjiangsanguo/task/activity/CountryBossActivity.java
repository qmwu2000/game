package org.unidal.game.hanjiangsanguo.task.activity;

import java.util.concurrent.TimeUnit;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;

public class CountryBossActivity extends AbstractTaskActivity {
	public static final String ID = "countryboss";

	private void handInspire(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "countryboss", "powerup", "&gold=0");

		m_helper.doGet(ctx, url);
	}

	private boolean isReady(TaskContext ctx) {
		return true;
	}

	private void handleBattle(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "countryboss", "battle", "&now=0");

		m_helper.doGet(ctx, url);
	}

	@Override
	public boolean execute(TaskContext ctx, TaskArguments args) throws Exception {
		ctx.setDefaultCategory(ID);

		if (isReady(ctx)) {
			for (int i = 0; i < 10; i++) {
				handInspire(ctx);
			}

			for (int i = 0; i < 15; i++) {
				handleBattle(ctx);

				TimeUnit.MILLISECONDS.sleep(60 * 1000L);
			}
		}
		return true;
	}

}
