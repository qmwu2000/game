package org.unidal.game.hanjiangsanguo.task.activity;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;

public class BeautyActivity extends AbstractTaskActivity {
	public static final String ID = "beauty";

	public boolean execute(TaskContext ctx, TaskArguments args) throws Exception {
		String op = args.nextString(null);

		ensure(op != null);
		ctx.setDefaultCategory(ID);

		doActive(ctx);
		
		for(int i=0;i<10;i++){
			doOneTime(ctx);
		}

		return true;
	}

	private void doActive(TaskContext ctx) throws Exception {
		for (int i = 0; i < 5; i++) {
			String actionUrl = m_helper.buildUrl2(ctx, "beauty", "active_action", "&beauty_id=1&type=1");

			m_helper.doGet(ctx, actionUrl);
		}
	}

	private void doOneTime(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "beauty", "loot_index", "&beauty_id=1&chip_id=6");

		m_helper.doGetWithScript(ctx, url, "var gs=''; for (var i in o.loot_list) gs+=o.loot_list[i].uid+','; gs;",
		      "list");
		String list = ctx.getAttribute("list");
		String[] uids = list.split(",");

		if (uids.length > 0) {
			String actionUrl = m_helper.buildUrl2(ctx, "beauty", "loot_action", "&beauty_id=1&chip_id=6&t="
			      + uids[uids.length - 1]);

			m_helper.doGet(ctx, actionUrl);
		}
	}
}
