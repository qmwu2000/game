package org.unidal.game.hanjiangsanguo.task.activity;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;

public class MineActivity extends AbstractTaskActivity {
	public static final String ID = "mine";

	public boolean execute(TaskContext ctx, TaskArguments args) throws Exception {
		String op = args.nextString(null);

		ensure(op != null);
		ctx.setDefaultCategory(ID);

		if (isMining(ctx)) {
			if (shouldEnd(ctx)) {
				endMine(ctx);
				findAndStartMine(ctx);
			}
		} else {
			findAndStartMine(ctx);
		}
		return true;
	}

	private void findAndStartMine(TaskContext ctx) throws Exception {
		for (int page = 1; page < 5; page++) {
			String url = m_helper.buildUrl2(ctx, "mine", "index", "&p=" + page);

			m_helper
			      .doGetWithScript(
			            ctx,
			            url,
			            "var gs=''; for (var i in o.list) gs+=o.list[i].id+':'+o.list[i].status+':'+o.list[i].get_silver+':'+o.list[i].nickname+','; gs;",
			            "list");

			String list = ctx.getAttribute("list");

			if (list == null) {
				return;
			}
			String[] strs = list.split(",");

			for (String str : strs) {
				String[] temp = str.split(":");
				String id = temp[0];
				String status = temp[1];

				if (status.equals("0")) {
					String startUrl = m_helper.buildUrl2(ctx, "mine", "caikuang", "&t=2&p=" + page + "&id=" + id);

					m_helper.doGet(ctx, startUrl);

					return;
				}
			}
		}
	}

	private boolean shouldEnd(TaskContext ctx) throws Exception {
		int maxGold = ctx.getIntAttribute("maxMineGold", 0);

		int page = ctx.getIntAttribute("log.page", 0);
		int site = ctx.getIntAttribute("log.site", 0);

		String url = m_helper.buildUrl2(ctx, "mine", "index", "&p=" + page);

		m_helper
		      .doGetWithScript(
		            ctx,
		            url,
		            "var gs=''; for (var i in o.list) gs+=o.list[i].id+':'+o.list[i].silver+':'+o.list[i].get_silver+':'+o.list[i].nickname+','; gs;",
		            "list");

		String list = ctx.getAttribute("list");
		String[] strs = list.split(",");

		for (String str : strs) {
			String[] temp = str.split(":");
			String id = temp[0];
			String silver = temp[1];
			String getSilver = temp[2];

			if (id.equals(String.valueOf(site))) {
				int maxValue = Integer.parseInt(silver);
				int currentValue = Integer.parseInt(getSilver);

				if (currentValue >= maxValue * 0.8) {
					return true;
				}
				if (maxGold > 0 && currentValue >= maxGold) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isMining(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "mine", "index", "");
		m_helper.doGet(ctx, url, "log.id", "log.page", "log.site");

		int status = ctx.getIntAttribute("log.id", -1);

		if (status > 0) {
			return true;
		} else {
			return false;
		}
	}

	private void endMine(TaskContext ctx) throws Exception {
		int index = ctx.getIntAttribute("log.site", 10);
		int page = ctx.getIntAttribute("log.page", 10);
		String url1 = m_helper.buildUrl2(ctx, "mine", "give_up", "&p=" + page + "&s=" + index);
		String url2 = m_helper.buildUrl2(ctx, "mine", "get_silver", "&p=" + page + "&s=" + index);

		m_helper.doGet(ctx, url1);
		m_helper.doGet(ctx, url2);
	}
}
