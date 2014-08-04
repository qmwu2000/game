package org.unidal.game.hanjiangsanguo.task.activity;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;

public class ZuqiuActivity extends AbstractTaskActivity {

	public static final String ID = "act_kemari";

	public boolean execute(TaskContext ctx, TaskArguments args) throws Exception {
		String op = args.nextString(null);

		ensure(op != null);
		ctx.setDefaultCategory(ID);

		getAct(ctx);

		return true;
	}

	private void getAct(TaskContext ctx) throws Exception {
		String url2 = m_helper.buildUrl2(ctx, "act_kemari", "action", "&type=2");

		m_helper.doGet(ctx, url2, "reward_info.type");

		int type = ctx.getIntAttribute("reward_info.type", 0);

		ctx.setAttribute(ID, "shenjiang", String.valueOf(type));
	}
}
