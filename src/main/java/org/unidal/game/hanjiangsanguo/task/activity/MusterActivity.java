package org.unidal.game.hanjiangsanguo.task.activity;

import java.util.Map;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.helper.Splitters;

public class MusterActivity extends AbstractTaskActivity {
	public static final String ID = "muster";

	private boolean doMusterGoBattle(TaskContext ctx, String id) throws Exception {
		String url = m_helper.buildUrl2(ctx, "muster", "go_battle", String.format("&gid=%s", id));

		return m_helper.doGet(ctx, url);
	}

	public boolean execute(TaskContext ctx, TaskArguments args) throws Exception {
		String op = args.nextString(null);

		ensure(op != null);
		ctx.setDefaultCategory(ID);

		if ("on".equals(op)) {
			String name = args.nextString(null);
			String id = mapId(ctx, name);

			doMusterGoBattle(ctx, id);
		}

		return true;
	}

	private String mapId(TaskContext ctx, String name) throws Exception {
		String url = m_helper.buildUrl2(ctx, "muster", "index", null);

		m_helper.doGetWithScript(ctx, url,
		      "var gs=''; for (var i in o.list) gs+=o.list[i].name+':'+o.list[i].id+','; gs;", "list");

		String list = ctx.getAttribute("list");
		Map<String, String> map = Splitters.by(',', ':').trim().split(list);
		String id = map.get(name);

		return id;
	}
}
