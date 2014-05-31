package org.unidal.game.hanjiangsanguo.task.core;

import org.unidal.game.hanjiangsanguo.task.Task;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.game.hanjiangsanguo.task.TaskHelper;
import org.unidal.lookup.annotation.Inject;

public class DiceTask implements Task {
	public static final String ID = "dice";

	@Inject
	private TaskHelper m_helper;

	@Override
	public void execute(TaskContext ctx) throws Exception {
		ctx.setDefaultCategory(ID);

		handleIndex(ctx);

		int times = ctx.getIntAttribute("member.times", 0);

		for (int i = 0; i < times; i++) {
			handleShakeDice(ctx);
		}
	}

	private void handleShakeDice(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, ID, "shake_dice", null);

		m_helper.doGet(ctx, url);
	}

	private void handleIndex(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, ID, "index", null);

		m_helper.doGet(ctx, url, "member.times");
	}
}
