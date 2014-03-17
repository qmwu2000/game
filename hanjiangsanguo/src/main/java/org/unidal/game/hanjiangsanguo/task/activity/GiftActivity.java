package org.unidal.game.hanjiangsanguo.task.activity;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;

public class GiftActivity extends AbstractTaskActivity {
	public static final String ID = "gift";

	private boolean doLevelGift(TaskContext ctx, String level) throws Exception {
		String url = m_helper.buildUrl2(ctx, "levelgift", "get_reward", String.format("&level=%s", level));

		return m_helper.doGet(ctx, url);
	}

	private boolean doInvitation(TaskContext ctx, String code) throws Exception {
		String url = m_helper.buildUrl2(ctx, "invitation", "change", String.format("&code=%s", code));

		return m_helper.doGet(ctx, url);
	}

	public boolean execute(TaskContext ctx, TaskArguments args) throws Exception {
		String op = args.nextString(null);

		ensure(op != null);
		ctx.setDefaultCategory(ID);

		if ("level".equals(op)) {
			String level = args.nextString(null);

			doLevelGift(ctx, level);
		} else if ("invitation".equals(op)) {
			String code = args.nextString(null);

			doInvitation(ctx, code);
		}

		return true;
	}
}
