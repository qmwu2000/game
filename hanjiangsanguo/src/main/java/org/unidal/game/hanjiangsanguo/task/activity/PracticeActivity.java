package org.unidal.game.hanjiangsanguo.task.activity;

import java.util.List;
import java.util.Map;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.helper.Splitters;
import org.unidal.tuple.Pair;

public class PracticeActivity extends AbstractTaskActivity {
	public static final String ID = "practice";

	private boolean doGoLeap(TaskContext ctx, String gid) throws Exception {
		String url = m_helper.buildUrl2(ctx, "practice", "go_leap", String.format("&gid=%s", gid));

		return m_helper.doGet(ctx, url);
	}

	private boolean doTrain(TaskContext ctx, String pid, String gid) throws Exception {
		String url = m_helper.buildUrl2(ctx, "practice", "practice_start",
		      String.format("&pid=%s&gid=%s&type=2", pid, gid));

		m_helper.doGet(ctx, url, "info.freetimes", "info.isturn");

		if (ctx.getIntAttribute("status", 0) < 0) {
			m_logger.warn("Error when practicing with go_leap!");
			return false;
		} else if (ctx.getIntAttribute("info.freetimes", 0) <= 0) {
			m_logger.warn("No free times left!");
		} else if (ctx.getIntAttribute("info.isturn", 0) > 0) {
			m_logger.warn("The general should be turned first!");
			return false;
		}

		return true;
	}

	public boolean execute(TaskContext ctx, TaskArguments args) throws Exception {
		String op = args.nextString(null);

		ensure(op != null);
		ctx.setDefaultCategory(ID);

		if ("train".equals(op)) {
			String name = args.nextString(null);
			Pair<String, String> pair = findIdAndPlace(ctx, name);

			doTrain(ctx, pair.getKey(), pair.getValue());
		} else if ("goleap".equals(op)) {
			String name = args.nextString(null);
			int times = args.nextInt(1);
			String id = mapId(ctx, name);

			for (int i = 0; i < times; i++) {
				doGoLeap(ctx, id);
			}
		}

		return true;
	}

	private Pair<String, String> findIdAndPlace(TaskContext ctx, String name) throws Exception {
		String url = m_helper.buildUrl2(ctx, "practice", "index", null);

		m_helper.doGetWithScript(ctx, url,
		      "var gs='',ps=''; for (var i in o.list) gs+=o.list[i].name+':'+o.list[i].id+','; "
		            + "for (var i in o.place) if (o.place[i].gid==0) ps+=o.place[i].id+','; gs+'|'+ps;", "list");

		String list = ctx.getAttribute("list");
		int pos = list.indexOf('|');
		Map<String, String> gs = Splitters.by(',', ':').split(list.substring(0, pos));
		List<String> ps = Splitters.by(',').split(list.substring(pos + 1));
		String gid = gs.get(name);
		String place = ps.size() > 0 ? ps.get(0) : null;

		return new Pair<String, String>(place, gid);
	}

	private String mapId(TaskContext ctx, String name) throws Exception {
		String url = m_helper.buildUrl2(ctx, "practice", "index", null);

		m_helper.doGetWithScript(ctx, url,
		      "var gs=''; for (var i in o.list) gs+=o.list[i].name+':'+o.list[i].id+','; gs;", "list");

		String list = ctx.getAttribute("list");
		Map<String, String> gs = Splitters.by(',', ':').split(list);
		String gid = gs.get(name);

		return gid;
	}
}
