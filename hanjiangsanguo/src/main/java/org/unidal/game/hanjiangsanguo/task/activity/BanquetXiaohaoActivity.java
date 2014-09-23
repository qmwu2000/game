package org.unidal.game.hanjiangsanguo.task.activity;

import java.util.List;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.helper.Splitters;

public class BanquetXiaohaoActivity extends AbstractTaskActivity {
	public static final String ID = "banquetXiaohao";

	public boolean execute(TaskContext ctx, TaskArguments args) throws Exception {
		String op = args.nextString(null);

		ensure(op != null);
		ctx.setDefaultCategory(ID);

		handleInfo(ctx);

		int status = ctx.getIntAttribute("info.status", 0);


		if (status == 0) {
			int leftTimes = ctx.getIntAttribute("info.times", 0);
			if (leftTimes > 0) {
				int currentSize = ctx.getIntAttribute("list.@count", 0);

				if (currentSize <= 1) {
					prepareBanquet(ctx);
				} else {
					joinBanquet(ctx);
				}
			} else {
				return true;
			}
		}
		return true;
	}

	private void joinBanquet(TaskContext ctx) throws Exception {
		String list = ctx.getAttribute("list");
		List<String> items = Splitters.by(',').noEmptyItem().trim().split(list);
		String id = "";

		for (String item : items) {
			List<String> parts = Splitters.by(':').split(item);
			String caption = parts.get(0);

			id = caption;
			
			break;
		}

		if (!"".equals(id)) {
			String url = m_helper.buildUrl2(ctx, "banquet", "join_team", "&id=" + id);

			m_helper.doGet(ctx, url);
		}
	}

	private void prepareBanquet(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "banquet", "create_team", "&id=1");

		m_helper.doGet(ctx, url);
	}

	private void handleInfo(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "banquet", "index", "&id=1");

		m_helper.doGet(ctx, url, "info.times", "info.type", "info.status", "list.@count", "info.uid", "info.team");

		m_helper
		      .doGetWithScript(
		            ctx,
		            url,
		            "var gs=''; for (var i in o.list) gs+=o.list[i].caption+':'+o.list[i].now_number+':'+o.list[i].number+','; gs;",
		            "list");

	}
}
