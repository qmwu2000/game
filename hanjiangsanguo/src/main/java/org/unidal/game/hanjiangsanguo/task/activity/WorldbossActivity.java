package org.unidal.game.hanjiangsanguo.task.activity;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.game.hanjiangsanguo.task.TaskHelper;
import org.unidal.lookup.annotation.Inject;

public class WorldbossActivity extends AbstractTaskActivity {
	public static final String ID = "worldboss";

	@Inject
	private TaskHelper m_helper;

	@Override
	public boolean execute(TaskContext ctx, TaskArguments args) throws Exception {
		ctx.setDefaultCategory(ID);

		handleIndex(ctx);

		if (isReady(ctx)) {
			int countdown = ctx.getIntAttribute("countdown", -1);
			int times = countdown / 60;
			long maxDelay = 0;

			handleMatrix(ctx);

			for (int i = 0; i < 10; i++) {
				handInspire(ctx);
			}

			for (int i = 0; i < times; i++) {
				long start = System.currentTimeMillis();

				handleBattle(ctx);

				long delay = System.currentTimeMillis() - start;

				if (delay > maxDelay) {
					maxDelay = delay;
				}

				TimeUnit.MILLISECONDS.sleep(60 * 1000L);
				countdown -= 60;

				if (countdown > 0 && countdown < 60) {
					// last time hits at last second
					TimeUnit.MILLISECONDS.sleep(countdown * 1000L - maxDelay - 10);
				}
			}
		}

		handleReward(ctx);

		return true;
	}

	private boolean isReady(TaskContext ctx) throws InterruptedException {
		int status = ctx.getIntAttribute("status", 0);

		if (status == 5) {
			System.err.println("World boss is already done!");
			return false;
		}

		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		int hour = cal.get(Calendar.HOUR_OF_DAY);

		switch (hour) {
		case 11:
		case 19:
			// wait for few minutes
			int minute = cal.get(Calendar.MINUTE);
			int second = cal.get(Calendar.SECOND);
			int remainingSeconds = 3600 - minute * 60 - second;

			TimeUnit.SECONDS.sleep(remainingSeconds);
			return true;
		case 12:
		case 20:
			// ready now
			return true;
		default:
			System.err.println("Too early or too late for world boss!");
			return false;
		}
	}

	private void handleBattle(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "worldboss", "battle", "&now=0");

		m_helper.doGet(ctx, url);
	}

	private void handleIndex(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "worldboss", "index", null);

		m_helper.doGet(ctx, url, "countdown");
	}

	private void handInspire(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "worldboss", "powerup", "&gold=0");

		m_helper.doGet(ctx, url);
	}

	protected void handleMatrix(TaskContext ctx) throws Exception {
		try {
			String url = m_helper.buildUrl2(ctx, "matrix", "update_matrix", "&list=%s&mid=%s", "list", "mid");

			m_helper.doGet(ctx, url);

			String url2 = m_helper.buildUrl2(ctx, "matrix", "use_matrix", "&list=%s&mid=%s", "list", "mid");

			m_helper.doGet(ctx, url2);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleReward(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "worldboss", "reward", "#");

		m_helper.doGet(ctx, url, "reward.silver", "reward.get3", "reward.get2");
	}
}
