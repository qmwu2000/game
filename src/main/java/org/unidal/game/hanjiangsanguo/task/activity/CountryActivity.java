package org.unidal.game.hanjiangsanguo.task.activity;

import java.util.List;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.helper.Splitters;

public class CountryActivity extends AbstractTaskActivity {
	public static final String ID = "country";

	public boolean execute(TaskContext ctx, TaskArguments args) throws Exception {
		String op = args.nextString(null);

		ensure(op != null);
		ctx.setDefaultCategory(ID);

		if ("sacrifice".equals(op)) {
			handleSacrifice(ctx);
		} else if ("expostulation".equals(op)) {
			handleExpostulation(ctx);
		} else if ("donate".equals(op)) {
			int maxSilver = args.nextInt(0);

			handleDonate(ctx, maxSilver);
		} else if ("dice".equals(op)) {
			handleDice(ctx);
		}

		return true;
	}

	private void handleDice(TaskContext ctx) throws Exception {
		String indexUrl = m_helper.buildUrl2(ctx, "dice", "index", null);

		m_helper.doGet(ctx, indexUrl, "member.times");

		int times = ctx.getIntAttribute("member.times", 0);

		for (int i = 0; i < times; i++) {
			String url = m_helper.buildUrl2(ctx, "dice", "shake_dice", null);

			m_helper.doGet(ctx, url);
		}
	}

	private void handleDonate(TaskContext ctx, int maxSilver) throws Exception {
		String storageUrl = m_helper.buildUrl2(ctx, "country", "storage", null);

		m_helper.doGet(ctx, storageUrl, "donate.silver");

		while (true) {
			int silver = ctx.getIntAttribute("donate.silver", Integer.MAX_VALUE);
			boolean flag = false;

			if (silver < maxSilver) {
				String url = m_helper.buildUrl2(ctx, "country", "donate", "&type=1");

				flag = m_helper.doGet(ctx, url, "donate.silver", "silver");
			}

			if (!flag) {
				break;
			}
		}
	}

	private void handleExpostulation(TaskContext ctx) throws Exception {
		String indexUrl = m_helper.buildUrl2(ctx, "expostulation", "index", null);

		m_helper.doGetWithScript(ctx, indexUrl,
		      "var gs=''; for (var i in o.list) gs+=o.list[i].id+':'+o.list[i].status+':'+o.list[i].support+','; gs;",
		      "list");

		String list = ctx.getAttribute("list");
		List<String> items = Splitters.by(',').noEmptyItem().trim().split(list);

		for (String item : items) {
			List<String> parts = Splitters.by(':').split(item);
			String id = parts.get(0);
			String status = parts.get(1);
			String support = parts.get(2);

			if (status.equals("1") && support.equals("0")) {
				handleExpostulationSupport(ctx, id);
			} else if (status.equals("2")) {
				handleExpostulationReward(ctx, id);
			}
		}
	}

	private void handleExpostulationReward(TaskContext ctx, String id) throws Exception {
		String url = m_helper.buildUrl2(ctx, "expostulation", "get_reward", String.format("&id=%s", id));

		m_helper.doGet(ctx, url);
	}

	private void handleExpostulationSupport(TaskContext ctx, String id) throws Exception {
		String url = m_helper.buildUrl2(ctx, "expostulation", "support", String.format("&id=%s", id));

		m_helper.doGet(ctx, url);
	}

	private void handleSacrifice(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "countrysacrifice", "action", "&id=1");

		m_helper.doGet(ctx, url);
	}
}
