package org.unidal.game.hanjiangsanguo.task.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.plexus.logging.LogEnabled;
import org.codehaus.plexus.logging.Logger;
import org.unidal.game.hanjiangsanguo.task.Task;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.game.hanjiangsanguo.task.TaskHelper;
import org.unidal.helper.Splitters;
import org.unidal.lookup.annotation.Inject;

public class PracticeTask implements Task, LogEnabled {
	public static final String ID = "practice";

	@Inject
	private TaskHelper m_helper;

	private Logger m_logger;

	@Override
	public void execute(TaskContext ctx) throws Exception {
		ctx.setDefaultCategory(ID);

		String action = ctx.getAttribute("action");

		if ("go_leap".equals(action)) {
			handleGoLeap(ctx);
		} else {
			handlePracticeAll(ctx);
		}
	}

	private void handleGoLeap(TaskContext ctx) throws Exception {
		handleIndex(ctx);

		int freeTimes = ctx.getIntAttribute("freetimes", 0);

		if (freeTimes <= 0) {
			m_logger.warn("No freetimes left!");
			return;
		}

		int maxTimes = ctx.getIntAttribute("maxtimes", 0);

		for (int i = Math.min(freeTimes, maxTimes); i > 0; i--) {
			String url = m_helper.buildUrl2(ctx, "practice", "go_leap", "&gid=%s", "gid");

			m_helper.doGet(ctx, url, "status", "info.freetimes", "info.isturn");

			if (ctx.getIntAttribute("status", 0) < 0) {
				m_logger.warn("Error when practicing with go_leap!");
				break;
			} else if (ctx.getIntAttribute("info.freetimes", 0) <= 0) {
				m_logger.warn("No free times left!");
				break;
			} else if (ctx.getIntAttribute("info.isturn", 0) > 0) {
				m_logger.warn("The general should be turned first!");
				break;
			}
		}
	}

	private void handlePracticeAll(TaskContext ctx) throws Exception {
		handleGeneralList(ctx);

		String list = ctx.getAttribute("list");
		int pos = list.indexOf('|');
		Map<String, String> gs = Splitters.by(',', ':').split(list.substring(0, pos));
		Map<String, String> ps = Splitters.by(',', ':').split(list.substring(pos + 1));

		List<String> generals = new ArrayList<String>();
		List<String> places = new ArrayList<String>();

		for (Map.Entry<String, String> g : gs.entrySet()) {
			String gid = g.getKey();
			String isturn = g.getValue();

			generals.add(gid);

			if ("1".equals(isturn)) {
				ctx.setAttribute("gid", gid);

				handleTurn(ctx);
			}
		}

		for (Map.Entry<String, String> p : ps.entrySet()) {
			String id = p.getKey();
			String gid = p.getValue();

			if (gid.equals("0")) {
				places.add(id);
			} else {
				gs.remove(gid);
			}
		}

		for (String place : places) {
			if (gs.isEmpty()) {
				break;
			}

			String general = gs.remove(0);

			ctx.setAttribute("pid", place);
			ctx.setAttribute("gid", general);
			ctx.setAttribute("type", "2");

			handlePracticeStart(ctx);
		}
	}

	private void handleGeneralList(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "practice", "index", null);

		m_helper.doGetWithScript(ctx, url,
		      "var gs='',ps=''; for (var i in o.list) gs+=o.list[i].id+':'+o.list[i].isturn+','; "
		            + "for (var i in o.place) ps+=o.place[i].id+':'+o.place[i].gid+','; gs+'|'+ps;", "list");
	}

	private void handleIndex(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "practice", "index", null);

		m_helper.doGet(ctx, url, "freetimes");
	}

	private void handlePracticeStart(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "practice", "practice_start", "&pid=%s&gid=%s&type=%s", "pid", "gid", "type");

		m_helper.doGet(ctx, url);
	}

	private void handleTurn(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "practice", "turn", "&gid=%s", "gid");

		m_helper.doGet(ctx, url);
	}

	@Override
	public void enableLogging(Logger logger) {
		m_logger = logger;
	}
}
