package org.unidal.game.hanjiangsanguo.task.activity;

import java.util.Arrays;
import java.util.Map;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.helper.Splitters;

public class GeneralActivity extends AbstractTaskActivity {
	public static final String ID = "general";

	private boolean doEquip(TaskContext ctx, String gid, String eid) throws Exception {
		String url = m_helper.buildUrl2(ctx, "general", "equip", String.format("&gid=%s&eid=%s", gid, eid));

		return m_helper.doGet(ctx, url);
	}

	public boolean execute(TaskContext ctx, TaskArguments args) throws Exception {
		String op = args.nextString(null);

		ensure(op != null);
		ctx.setDefaultCategory(ID);

		if ("equip".equals(op)) {
			String name = args.nextString(null);
			String[] equips = args.restString();
			String gid = mapGid(ctx, name);
			String[] eids = mapEids(ctx, equips);

			for (String eid : eids) {
				if (eid != null && eid.length() > 0) {
					doEquip(ctx, gid, eid);
				} else {
					throw new RuntimeException("Invalid equipment: " + Arrays.asList(equips));
				}
			}
		}

		return true;
	}

	private String mapGid(TaskContext ctx, String name) throws Exception {
		String url = m_helper.buildUrl2(ctx, "general", "index", null);

		m_helper.doGetWithScript(ctx, url,
		      "var gs='',ps=''; for (var i in o.list) gs+=o.list[i].name+':'+o.list[i].id+','; gs;", "list");

		String list = ctx.getAttribute("list");
		Map<String, String> gs = Splitters.by(',', ':').split(list);
		String gid = gs.get(name);

		return gid;
	}

	private String[] mapEids(TaskContext ctx, String[] equips) throws Exception {
		String url = m_helper.buildUrl2(ctx, "general", "index", null);

		m_helper.doGetWithScript(ctx, url,
		      "var gs='',ps=''; for (var i in o.equipments) gs+=o.equipments[i].name+':'+o.equipments[i].id+','; gs;",
		      "list");

		String list = ctx.getAttribute("list");
		Map<String, String> gs = Splitters.by(',', ':').split(list);
		String[] eids = new String[equips.length];
		int index = 0;

		for (String equip : equips) {
			eids[index++] = gs.get(equip);
		}

		return eids;
	}
}
