package org.unidal.game.hanjiangsanguo.task.activity;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;

public class LotteryActivity extends AbstractTaskActivity {
	public static final String ID = "lottery";

	private void doBuy(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "lottery", "buy_lottery_num", null);

		m_helper.doGet(ctx, url);
	}

	private void doDraw(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "lottery", "action", null);

		m_helper.doGet(ctx, url);
	}

	public boolean execute(TaskContext ctx, TaskArguments args) throws Exception {
		String op = args.nextString(null);

		ensure(op != null);
		ctx.setDefaultCategory(ID);

		if ("leap".equals(op)) {
			if (refreshUntil(ctx, "8")) {
				for (int i = 0; i < 10; i++) {
					doBuy(ctx);
				}

				for (int i = 0; i < 11; i++) {
					doDraw(ctx);
				}
			}
		}

		return true;
	}

	private boolean refreshUntil(TaskContext ctx, String expectedType) throws Exception {
		String indexUrl = m_helper.buildUrl2(ctx, "lottery", "index", null);

		m_helper.doGet(ctx, indexUrl, "list[0].gettype");

		while (true) {
			String type = ctx.getAttribute("list[0].gettype");

			if (expectedType.equals(type)) {
				return true;
			}

			String refreshUrl = m_helper.buildUrl2(ctx, "lottery", "refresh", null);

			if (!m_helper.doGet(ctx, refreshUrl, "list[0].gettype")) {
				return false;
			}
		}
	}
}
