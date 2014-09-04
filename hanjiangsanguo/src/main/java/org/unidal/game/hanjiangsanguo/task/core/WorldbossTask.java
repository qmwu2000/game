package org.unidal.game.hanjiangsanguo.task.core;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.codehaus.plexus.logging.LogEnabled;
import org.codehaus.plexus.logging.Logger;
import org.unidal.game.hanjiangsanguo.task.Task;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.game.hanjiangsanguo.task.TaskHelper;
import org.unidal.lookup.annotation.Inject;

public class WorldbossTask implements Task, LogEnabled {
	public static final String ID = "worldboss";

	@Inject
	private TaskHelper m_helper;

	private Logger m_logger;

	@Override
	public void enableLogging(Logger logger) {
		m_logger = logger;
	}

	@Override
	public void execute(TaskContext ctx) throws Exception {
		ctx.setDefaultCategory(ID);

		handleMatrix(ctx);

		if (waitForReady(ctx)) {
			int lastHitLatency = 4;

			while (true) {
				handleBattle(ctx);

				int countdown = handleIndex(ctx);

				System.out.println("count down: " + countdown);

				if (countdown <= 0) {
					break;
				}

				TimeUnit.MILLISECONDS.sleep(60 * 1000L);
				countdown -= 60;

				if (countdown > lastHitLatency && countdown <= 60 + lastHitLatency) {
					long waiting = (countdown - lastHitLatency) * 1000L;

					m_logger.info(String.format("Waiting for %s ms for last hit ...", waiting));

					TimeUnit.MILLISECONDS.sleep(waiting);
				}
			}

			handleReward(ctx);
		}
	}

	private void handleBattle(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "worldboss", "battle", "&now=0");

		m_helper.doGet(ctx, url);

		String url2 = m_helper.buildUrl2(ctx, "worldboss", "index", null);

		m_helper.doGet(ctx, url2, "my.rank", "my.hit");
	}

	private int handleIndex(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "worldboss", "index", null);

		m_helper.doGet(ctx, url, "countdown");

		return ctx.getIntAttribute("countdown", -1);
	}

	private void handleMatrix(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "matrix", "update_matrix", "&list=%s&mid=%s", "list", "mid");

		m_helper.doGet(ctx, url);

		String url2 = m_helper.buildUrl2(ctx, "matrix", "use_matrix", "&mid=%s", "mid");

		m_helper.doGet(ctx, url2);
	}

	private void handleReward(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "worldboss", "reward", "#");

		m_helper.doGet(ctx, url, "reward.silver", "reward.get3", "reward.get2");
	}

	private boolean waitForReady(TaskContext ctx) throws Exception {
		handleIndex(ctx);

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
			int remainingSeconds = (60 - minute) * 60 - second;

			m_logger.info("Waiting for " + remainingSeconds + " seconds ...");
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
}
