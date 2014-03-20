package org.unidal.game.hanjiangsanguo.task.activity;

import java.util.List;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.helper.Splitters;

public class GiftActivity extends AbstractTaskActivity {
	public static final String ID = "gift";

	private boolean doInvitation(TaskContext ctx, String code) throws Exception {
		String url = m_helper.buildUrl2(ctx, "invitation", "change", String.format("&code=%s", code));

		return m_helper.doGet(ctx, url);
	}

	private boolean doTaskRewards(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "mainquest", "index", null);

		while (true) {
			m_helper.doGetWithScript(ctx, url,
			      "var gs=''; for (var i in o.list) if (o.list[i].status=='1') gs+=o.list[i].task_id+','; gs;", "list");

			String list = ctx.getAttribute("list");
			List<String> ids = Splitters.by(',').noEmptyItem().split(list);

			if (ids.isEmpty()) {
				break;
			}

			for (String id : ids) {
				doTaskReward(ctx, id);
			}
		}

		return true;
	}

	private boolean doTaskReward(TaskContext ctx, String id) throws Exception {
		String url = m_helper.buildUrl2(ctx, "mainquest", "get_task_reward", String.format("&id=%s", id));

		return m_helper.doGet(ctx, url);
	}

	private boolean doLevelGift(TaskContext ctx, String level) throws Exception {
		String url = m_helper.buildUrl2(ctx, "levelgift", "get_reward", String.format("&level=%s", level));

		return m_helper.doGet(ctx, url);
	}

	private boolean doLoginReward(TaskContext ctx, String day) throws Exception {
		String url = m_helper.buildUrl2(ctx, "logined", "get_reward", String.format("&id=%s", day));

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
		} else if ("login".equals(op)) {
			String day = args.nextString(null);

			doLoginReward(ctx, day);
		} else if ("task".equals(op)) {
			doTaskRewards(ctx);
		}

		return true;
	}
}
