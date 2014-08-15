package org.unidal.game.hanjiangsanguo.task.activity;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;

public class CountryMineActivity extends AbstractTaskActivity {
	public static final String ID = "countrymine";

	public boolean execute(TaskContext ctx, TaskArguments args) throws Exception {
		String op = args.nextString(null);

		ensure(op != null);
		ctx.setDefaultCategory(ID);

		String dahao = ctx.getAttribute("member", "dahao");

		if (dahao != null) {
			if (isMining(ctx)) {
				if (shouldEnd(ctx)) {
					endMine(ctx);
					findAndStartMine(ctx);
				}
			} else {
				findAndStartMine(ctx);
			}
		}
		return true;
	}

	private void findAndStartMine(TaskContext ctx) throws Exception {
		int times = ctx.getIntAttribute("log.times", 0);

		if (times > 0) {

			for (int page = 1; page < 5; page++) {
				String url = m_helper.buildUrl2(ctx, "countrymine", "index", "&p=" + page);

				m_helper
				      .doGetWithScript(
				            ctx,
				            url,
				            "var gs=''; for (var i in o.list) gs+=o.list[i].id+':'+o.list[i].status+':'+o.list[i].jade+':'+o.list[i].get_jade+','; gs;",
				            "list");

				String list = ctx.getAttribute("list");
				String[] strs = list.split(",");

				for (String str : strs) {
					String[] temp = str.split(":");
					String id = temp[0];
					String status = temp[1];

					if (status.equals("0")) {
						String startUrl = m_helper.buildUrl2(ctx, "countrymine", "caikuang", "&t=2&p=" + page + "&id=" + id);

						m_helper.doGet(ctx, startUrl);

						return;
					}
				}
			}
		}
	}

	private boolean shouldEnd(TaskContext ctx) throws Exception {
		int page = ctx.getIntAttribute("log.page", 0);
		int site = ctx.getIntAttribute("log.site", 0);

		String url = m_helper.buildUrl2(ctx, "countrymine", "index", "&p=" + page);

		m_helper
		      .doGetWithScript(
		            ctx,
		            url,
		            "var gs=''; for (var i in o.list) gs+=o.list[i].id+':'+o.list[i].status+':'+o.list[i].jade+':'+o.list[i].get_jade+','; gs;",
		            "list");

		String list = ctx.getAttribute("list");
		String[] strs = list.split(",");

		for (String str : strs) {
			String[] temp = str.split(":");
			String id = temp[0];
			String jade = temp[2];
			String getJade = temp[3];

			if (id.equals(String.valueOf(site)) && jade.equals(getJade)) {
				return true;
			}
		}
		return false;
	}

	private boolean isMining(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "countrymine", "index", "");
		m_helper.doGet(ctx, url, "log.page", "log.site", "log.times");

		int status = ctx.getIntAttribute("log.page", -1);

		if (status > 0) {
			return true;
		} else {
			return false;
		}
	}

	private void endMine(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "countrymine", "get_reward", "");

		m_helper.doGet(ctx, url);
	}

}
