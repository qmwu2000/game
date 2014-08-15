package org.unidal.game.hanjiangsanguo.task.activity;

import java.util.ArrayList;
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
		} else if ("addCountry".equals(op)) {
			handelAddCountry(ctx);
		} else if ("exitCountry".equals(op)) {
			handelExitCountry(ctx);
		} else if ("approve".equals(op)) {
			handelApprove(ctx);
		}
		return true;
	}

	private List<Integer> findIds(TaskContext ctx) throws Exception {
		List<Integer> results = new ArrayList<Integer>();
		String url = m_helper.buildUrl2(ctx, "country", "get_audit_list", null);

		m_helper.doGetWithScript(ctx, url,
		      "var gs=''; for (var i in o.list) gs+=o.list[i].uid+':'+o.list[i].nickname+','; gs;", "list");

		String list = ctx.getAttribute("list");

		String[] strs = list.split(",");

		for (String str : strs) {
			String[] temp = str.split(":");
			String id = temp[0];

			results.add(Integer.parseInt(id));
		}
		return results;
	}

	private void handelApprove(TaskContext ctx) throws Exception {
		List<Integer> ids = findIds(ctx);
		int length = ids.size();

		for (int i = length - 1; i >= 0; i--) {
			int id = ids.get(i);

			String indexUrl = m_helper.buildUrl2(ctx, "country", "audit", "&uid=" + id + "&type=1");

			m_helper.doGet(ctx, indexUrl);
		}
	}

	private void handelAddCountry(TaskContext ctx) throws Exception {
		String indexUrl = m_helper.buildUrl2(ctx, "country", "apply", "&id=2&page=1");

		m_helper.doGet(ctx, indexUrl);
	}

	private void handelExitCountry(TaskContext ctx) throws Exception {
		String indexUrl = m_helper.buildUrl2(ctx, "country", "betray", null);

		m_helper.doGet(ctx, indexUrl);
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

				int left = ctx.getIntAttribute(ID, "silver", 0);

				if (left > 300 * 10000) {
					flag = true;
				}
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
