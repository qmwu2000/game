package org.unidal.game.hanjiangsanguo.task.action;

import java.util.concurrent.TimeUnit;

import org.unidal.game.hanjiangsanguo.task.TaskContext;

public abstract class AbstractDurableTaskAction extends AbstractTaskAction {
	protected long SECOND = 1000L;

	protected long MINUTE = 60 * SECOND;

	public abstract boolean check(TaskContext ctx) throws Exception;

	@Override
	public void doAction(TaskContext ctx) throws Exception {
		if (start(ctx)) {
			long maxDuration = getMaxDuration();
			long checkInterval = getCheckInterval();
			long start = System.currentTimeMillis();

			while (start + maxDuration >= System.currentTimeMillis()) {
				if (!check(ctx)) {
					return;
				}

				TimeUnit.MILLISECONDS.sleep(checkInterval);
			}

			end(ctx);
		}
	}

	public abstract long getMaxDuration();

	public abstract long getCheckInterval();

	public abstract boolean start(TaskContext ctx) throws Exception;

	public abstract void end(TaskContext ctx) throws Exception;
}
