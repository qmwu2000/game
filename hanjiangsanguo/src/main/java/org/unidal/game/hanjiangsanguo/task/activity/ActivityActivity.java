package org.unidal.game.hanjiangsanguo.task.activity;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;

public class ActivityActivity extends AbstractTaskActivity {
	public static final String ID = "activity";

	private void doSacredtree(TaskContext ctx) throws Exception {
		String indexUrl = m_helper.buildUrl2(ctx, "sacredtree", "index", null);

		m_helper.doGet(ctx, indexUrl, "time");

		if (ctx.getIntAttribute("time", 0) == 1) {
			String url = m_helper.buildUrl2(ctx, "sacredtree", "watering", "&type=1#");

			m_helper.doGet(ctx, url, "add_exp");
		}
	}

	private void doSprintLottery(TaskContext ctx) throws Exception {
		String indexUrl = m_helper.buildUrl2(ctx, "springlottery", "index", null);

		m_helper.doGet(ctx, indexUrl, "lottery");

		if (ctx.getIntAttribute("lottery", 0) == 1) {
			String url = m_helper.buildUrl2(ctx, "springlottery", "action", "#");

			m_helper.doGet(ctx, url, "info.gettype", "info.getvalue");
		}
	}

	public boolean execute(TaskContext ctx, TaskArguments args) throws Exception {
		String op = args.nextString(null);

		ensure(op != null);
		ctx.setDefaultCategory(ID);

		if ("sacredtree".equals(op)) {
			if (ctx.getIntAttribute("member", "sacredtree", 0) > 0) {
				doSacredtree(ctx);
			}
		} else if ("springlottery".equals(op)) {
			if (ctx.getIntAttribute("member", "springlottery", 0) > 0) {
				doSprintLottery(ctx);
			}
		}

		return true;
	}
}
