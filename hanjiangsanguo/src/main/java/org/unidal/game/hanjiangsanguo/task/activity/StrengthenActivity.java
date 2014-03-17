package org.unidal.game.hanjiangsanguo.task.activity;

import java.util.Map;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.helper.Splitters;

public class StrengthenActivity extends AbstractTaskActivity {
	public static final String ID = "strengthen";

	private boolean doUpgrade(TaskContext ctx, String eid) throws Exception {
		String url = m_helper.buildUrl2(ctx, "strengthen", "strengthen_start", String.format("&id=%s&ratetype=0", eid));

		m_helper.doGet(ctx, url);

		int status = ctx.getIntAttribute("status", 0);

		return status > 0 || status == -6 || status == -3;
	}

	public boolean execute(TaskContext ctx, TaskArguments args) throws Exception {
		String op = args.nextString(null);

		ensure(op != null);
		ctx.setDefaultCategory(ID);

		if ("equip".equals(op)) {
			String name = args.nextString(null);
			String type = args.nextString(null);
			int times = args.nextInt(1);
			String gid = mapGid(ctx, name);
			String eid = mapEid(ctx, gid, type);

			for (int i = 0; i < times; i++) {
				if (!doUpgrade(ctx, eid)) {
					throw new RuntimeException("Unable to strengthen: " + name + "," + type);
				}
			}
		}

		return true;
	}

	private String mapGid(TaskContext ctx, String name) throws Exception {
		String url = m_helper.buildUrl2(ctx, "general", "index", null);

		m_helper.doGetWithScript(ctx, url,
		      "var gs=''; for (var i in o.list) gs+=o.list[i].name+':'+o.list[i].id+','; gs;", "list");

		String list = ctx.getAttribute("list");
		Map<String, String> gs = Splitters.by(',', ':').split(list);
		String gid = gs.get(name);

		return gid;
	}

	private String mapEid(TaskContext ctx, String gid, String type) throws Exception {
		String url = m_helper.buildUrl2(ctx, "strengthen", "index", null);

		m_helper.doGetWithScript(ctx, url, String.format(
		      "var gs=''; for (var i in o.list)  if (o.list[i].gid=='%s' && o.list[i].type=='%s') gs+=o.list[i].id; gs;",
		      gid, type), "eid");

		String eid = ctx.getAttribute("eid");

		return eid;
	}
}
