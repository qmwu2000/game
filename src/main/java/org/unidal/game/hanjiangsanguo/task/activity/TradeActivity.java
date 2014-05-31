package org.unidal.game.hanjiangsanguo.task.activity;

import java.util.List;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.helper.Splitters;

public class TradeActivity extends AbstractTaskActivity {
	public static final String ID = "trade";

	public boolean execute(TaskContext ctx, TaskArguments args) throws Exception {
		String op = args.nextString(null);

		ensure(op != null);
		ctx.setDefaultCategory(ID);

		if ("oversea".equals(op)) {
			int maxTimes = getOverseaMaxTimes(ctx);

			if (maxTimes > 0) {
				if (handleOverseaBuyItem(ctx)) {
					handleOverseaTrade(ctx);
				}
			}
		} else if ("business".equals(op)) {
			int maxTimes = getBusinessMaxTimes(ctx);
			int times = args.nextInt(maxTimes);

			for (int i = 0; i < times; i++) {
				handleBusinessGo(ctx);
			}
		}

		return true;
	}

	private int getBusinessMaxTimes(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "business", "index", null);

		m_helper.doGet(ctx, url, "times", "trader[0].id");

		return ctx.getIntAttribute("times", 0);
	}

	private int getOverseaMaxTimes(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "overseastrade", "index", null);

		m_helper.doGet(ctx, url, "info.times");

		return ctx.getIntAttribute("info.times", 0);
	}

	private void handleBusinessGo(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "business", "go_business", "&id=%s", "trader[0].id");

		m_helper.doGet(ctx, url, "trader[0].id");
	}

	private boolean handleOverseaBuyItem(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "overseastrade", "buy_item", "&id=1");

		return m_helper.doGet(ctx, url);
	}

	private void handleOverseaTrade(TaskContext ctx) throws Exception {
		String indexUrl = m_helper.buildUrl2(ctx, "overseastrade", "get_list_by_country", null);

		m_helper.doGetWithScript(ctx, indexUrl, "var gs=''; for (var i in o.list) "
		      + "{ gs+=o.list[i].page+','+o.list[i].site+','+(o.list[i].member1=='0'?'1':'2')+','+o.list[i].id; break; } gs;", "list");

		List<String> parts = Splitters.by(',').split(ctx.getAttribute("list"));
		String page = parts.size() >= 4 ? parts.get(0) : "1";
		String place = parts.size() >= 4 ? parts.get(1) : "1";
		String site = parts.size() >= 4 ? parts.get(2) : "1";
		String id = parts.size() >= 4 ? parts.get(3) : "0";

		String url = m_helper.buildUrl2(ctx, "overseastrade", "join_team",
		      String.format("&id=%s&page=%s&place=%s&site=%s",id, page, place, site));

		m_helper.doGet(ctx, url);
	}
}
