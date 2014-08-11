package org.unidal.game.hanjiangsanguo.task.activity;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.game.hanjiangsanguo.task.TaskHelper;
import org.unidal.lookup.annotation.Inject;

public class CountryBossActivity extends AbstractTaskActivity {
	public static final String ID = "countryBoss";

	@Inject
	private TaskHelper m_helper;

	private void handInspire(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "countryboss", "powerup", "&gold=0");

		m_helper.doGet(ctx, url);
	}

	private boolean isReady(TaskContext ctx) throws InterruptedException {
		int status = ctx.getIntAttribute("status", 0);

		if (status == 5) {
			System.err.println("Country boss is already done!");
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
			System.err.println("Too early or too late for country boss!");
			return false;
		}
	}

	private void handleBattle(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "countryboss", "battle", "&now=0");

		m_helper.doGet(ctx, url);
	}

	@Override
	public boolean execute(TaskContext ctx, TaskArguments args) throws Exception {
		ctx.setDefaultCategory(ID);

		if (isReady(ctx)) {
			int countdown = ctx.getIntAttribute("countdown", -1);
			int times = countdown / 60;
			long maxDelay = 0;

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
		return true;
	}

}
