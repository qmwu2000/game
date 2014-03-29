package org.unidal.game.hanjiangsanguo.task.activity;

import java.util.List;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.helper.Splitters;

public class GiftActivity extends AbstractTaskActivity {
	public static final String ID = "gift";

	private boolean doArenaRewards(TaskContext ctx) throws Exception {
		String indexUrl = m_helper.buildUrl2(ctx, "arena", "index", null);

		m_helper.doGet(ctx, indexUrl);

		String url = m_helper.buildUrl2(ctx, "arena", "get_reward", null);

		return m_helper.doGet(ctx, url);
	}

	private boolean doCountrySalary(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "country", "get_salary", null);

		return m_helper.doGet(ctx, url);
	}

	private void doExercise(TaskContext ctx) throws Exception {
		String indexUrl = m_helper.buildUrl2(ctx, "exercise", "index", null);

		m_helper.doGet(ctx, indexUrl, "info.gift");

		int times = ctx.getIntAttribute("info.gift", 0);

		for (int i = 0; i < times; i++) {
			String url = m_helper.buildUrl2(ctx, "exercise", "open_gift", null);

			m_helper.doGet(ctx, url);
		}
	}

	private boolean doHitEgg(TaskContext ctx) throws Exception {
		String indexUrl = m_helper.buildUrl2(ctx, "hitegg", "index", null);

		m_helper.doGetWithScript(ctx, indexUrl,
		      "var gs=''; for (var i in o.list) if (o.list[i].open==1) gs+=o.list[i].id+','; gs;", "list");

		String list = ctx.getAttribute("list");
		List<String> ids = Splitters.by(',').noEmptyItem().split(list);

		for (String id : ids) {
			String url = m_helper.buildUrl2(ctx, "hitegg", "hit_egg", String.format("&id=%s", id));

			m_helper.doGet(ctx, url);
		}

		return true;
	}

	private boolean doInvitation(TaskContext ctx, String code) throws Exception {
		String url = m_helper.buildUrl2(ctx, "invitation", "change", String.format("&code=%s", code));

		return m_helper.doGet(ctx, url);
	}

	private boolean doLevelGift(TaskContext ctx, String level) throws Exception {
		String url = m_helper.buildUrl2(ctx, "levelgift", "get_reward", String.format("&level=%s", level));

		return m_helper.doGet(ctx, url);
	}

	private boolean doLoginReward(TaskContext ctx) throws Exception {
		String indexUrl = m_helper.buildUrl2(ctx, "logined", "index", null);

		m_helper.doGetWithScript(ctx, indexUrl,
		      "var gs=''; for (var i in o.list) if (o.list[i].type=='1') gs+=o.list[i].id+','; gs;", "list");

		String list = ctx.getAttribute("list");
		List<String> ids = Splitters.by(',').noEmptyItem().split(list);

		for (String id : ids) {
			String url = m_helper.buildUrl2(ctx, "logined", "get_reward", String.format("&id=%s", id));

			m_helper.doGet(ctx, url);
		}

		return true;
	}

	private boolean doTaskReward(TaskContext ctx, String id) throws Exception {
		String url = m_helper.buildUrl2(ctx, "mainquest", "get_task_reward", String.format("&id=%s", id));

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

	private boolean doVipWage(TaskContext ctx) throws Exception {
		String indexUrl = m_helper.buildUrl2(ctx, "vipwage", "index", null);

		m_helper.doGet(ctx, indexUrl, "get");

		if (ctx.getIntAttribute("get", 0) == 1) {
			String url = m_helper.buildUrl2(ctx, "vipwage", "get_vip_wage", null);

			m_helper.doGet(ctx, url);
		}

		return true;
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
		} else if ("vip".equals(op)) {
			doVipWage(ctx);
		} else if ("login".equals(op)) {
			doLoginReward(ctx);
		} else if ("hitegg".equals(op)) {
			doHitEgg(ctx);
		} else if ("task".equals(op)) {
			doTaskRewards(ctx);
		} else if ("arena".equals(op)) {
			doArenaRewards(ctx);
		} else if ("country".equals(op)) {
			doCountrySalary(ctx);
		} else if ("exercise".equals(op)) {
			doExercise(ctx);
		}

		return true;
	}
}
