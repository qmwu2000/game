package org.unidal.game.hanjiangsanguo.task.activity;

import java.util.List;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.helper.Splitters;

public class BanquetActivity extends AbstractTaskActivity {
	public static final String ID = "banquet";

	public boolean execute(TaskContext ctx, TaskArguments args) throws Exception {
		String op = args.nextString(null);

		ensure(op != null);
		ctx.setDefaultCategory(ID);

		if (op.equals("active")) {
			handleInfo(ctx);

			int status = ctx.getIntAttribute("info.status", 0);

			if (status == 0) {
				int leftTimes = ctx.getIntAttribute("info.times", 0);

				if (leftTimes > 0) {
					int currentSize = ctx.getIntAttribute("list.@count", 0);

					if (currentSize < 3) {
						prepareBanquet(ctx);
					} else {
						joinBanquet(ctx);
					}
				}
			} else {
				if (checkIsFull(ctx)) {
					startBanquet(ctx);
				}
			}
		}

		return true;
	}

	private boolean checkIsFull(TaskContext ctx) {
		int uid = ctx.getIntAttribute("info.uid", -1);
		int team = ctx.getIntAttribute("info.team", -1);

		if (uid == team && uid > 0) {
			String list = ctx.getAttribute("list");
			List<String> items = Splitters.by(',').noEmptyItem().trim().split(list);

			for (String item : items) {
				List<String> parts = Splitters.by(':').split(item);
				String caption = parts.get(0);
				String nowNumber = parts.get(1);

				if (caption.equals(String.valueOf(uid))) {
					if (Integer.parseInt(nowNumber) == 10) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private void startBanquet(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "banquet", "action", "");

		m_helper.doGet(ctx, url);
	}

	private void joinBanquet(TaskContext ctx) throws Exception {
		String list = ctx.getAttribute("list");
		List<String> items = Splitters.by(',').noEmptyItem().trim().split(list);
		String id = "";

		for (String item : items) {
			List<String> parts = Splitters.by(':').split(item);
			String caption = parts.get(0);
			String nowNumber = parts.get(1);

			if (Integer.parseInt(nowNumber) <= 10) {
				id = caption;
				break;
			}
		}

		if (!"".equals(id)) {
			String url = m_helper.buildUrl2(ctx, "banquet", "join_team", "&id=" + id);

			m_helper.doGet(ctx, url);
		}
	}

	private void prepareBanquet(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "banquet", "create_team", "&id=3");

		m_helper.doGet(ctx, url);
	}

	private void handleInfo(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "banquet", "index", "&id=1");

		m_helper.doGet(ctx, url, "info.times", "info.type", "info.status", "list.@count", "info.uid", "info.team");

		m_helper.doGetWithScript(ctx, url,
		      "var gs=''; for (var i in o.list) gs+=o.list[i].caption+':'+o.list[i].now_number+','; gs;", "list");

	}
}
