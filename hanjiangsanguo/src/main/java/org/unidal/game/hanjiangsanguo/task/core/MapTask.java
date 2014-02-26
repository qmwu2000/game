package org.unidal.game.hanjiangsanguo.task.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;
import org.unidal.game.hanjiangsanguo.task.Task;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.game.hanjiangsanguo.task.TaskHelper;
import org.unidal.lookup.annotation.Inject;
import org.unidal.tuple.Pair;
import org.unidal.tuple.Triple;

public class MapTask implements Task, Initializable {
	public static final String ID = "map";

	@Inject
	private TaskHelper m_helper;

	private Map<String, Pair<Integer, Integer>> m_scrolls = new HashMap<String, Pair<Integer, Integer>>();

	private List<Triple<Integer, Integer, Integer>> m_map = new ArrayList<Triple<Integer, Integer, Integer>>();

	private List<Triple<Integer, Integer, Integer>> m_generals = new ArrayList<Triple<Integer, Integer, Integer>>();

	@Override
	public void execute(TaskContext ctx) throws Exception {
		ctx.setDefaultCategory(ID);

		String action = ctx.getAttribute("action");
		int maxTimes = ctx.getIntAttribute("maxtimes", 1);

		if ("reputation".equals(action)) {
			doMapAction(ctx, 12, maxTimes);
		} else if ("general".equals(action)) {
			doGeneralAction(ctx);
		} else if ("scroll".equals(action)) {
			handleScroll(ctx);
		} else {
			doMapAction(ctx, 7, maxTimes);
		}
	}

	private void doMapAction(TaskContext ctx, int level, int maxTimes) throws Exception {
		for (Triple<Integer, Integer, Integer> e : m_map) {
			if (e.getFirst() == level) {
				ctx.setAttribute("info.missionlevel", String.valueOf(e.getFirst()));
				ctx.setAttribute("info.missionstage", String.valueOf(e.getMiddle()));
				ctx.setAttribute("info.missionid", String.valueOf(e.getLast()));

				handleMission(ctx);

				int times = ctx.getIntAttribute("info.nowmaxtimes", maxTimes);

				for (int i = 0; i < times; i++) {
					handleAction(ctx);
				}
			}
		}
	}

	private void doGeneralAction(TaskContext ctx) throws Exception {
		for (Triple<Integer, Integer, Integer> e : m_generals) {
			ctx.setAttribute("info.missionlevel", String.valueOf(e.getFirst()));
			ctx.setAttribute("info.missionstage", String.valueOf(e.getMiddle()));
			ctx.setAttribute("info.missionid", String.valueOf(e.getLast()));

			while (true) {
				handleMission(ctx);

				int times = ctx.getIntAttribute("info.nowmaxtimes", 0);
				int recruitrate = ctx.getIntAttribute("info.recruitrate", 0);

				if (recruitrate > 0 && times > 0) {
					handleAction(ctx);
				} else {
					break;
				}
			}
		}
	}

	private void handleScroll(TaskContext ctx) throws Exception {
		String color = ctx.getAttribute("scroll.color");
		String type = ctx.getAttribute("scroll.type");
		Pair<Integer, Integer> scroll = m_scrolls.get(color);

		if (scroll != null && type != null) {
			int level = scroll.getKey();
			int stages = scroll.getValue();

			ctx.setAttribute("info.missionlevel", String.valueOf(level));

			for (int stage = stages; stage > 0; stage--) {
				ctx.setAttribute("info.missionstage", String.valueOf(stage));
				ctx.setAttribute("info.missionid", type);

				handleMission(ctx);

				int times = ctx.getIntAttribute("info.nowmaxtimes", 0);

				for (int i = 0; i < times; i++) {
					handleAction(ctx);
				}
			}
		}
	}

	private void handleAction(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "map", "action", "&l=%s&s=%s&id=%s", "info.missionlevel",
		      "info.missionstage", "info.missionid");

		m_helper.doGet(ctx, url, "info.win");
	}

	private void handleMission(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "map", "mission", "&l=%s&s=%s&id=%s", "info.missionlevel",
		      "info.missionstage", "info.missionid");

		m_helper.doGet(ctx, url, "info.nowmaxtimes", "info.recruitrate");
	}

	@Override
	public void initialize() throws InitializationException {
		m_scrolls.put("white", new Pair<Integer, Integer>(6, 4));
		m_scrolls.put("green", new Pair<Integer, Integer>(7, 4));
		m_scrolls.put("blue", new Pair<Integer, Integer>(8, 4));
		m_scrolls.put("yellow", new Pair<Integer, Integer>(9, 7));
		m_scrolls.put("purple", new Pair<Integer, Integer>(10, 7));

		for (int level = 1; level <= 3; level++) {
			for (int i = 6; i <= 10; i++) {
				m_map.add(new Triple<Integer, Integer, Integer>(12, level, i));
			}
		}

		m_generals.add(new Triple<Integer, Integer, Integer>(5, 2, 10));
		m_generals.add(new Triple<Integer, Integer, Integer>(5, 4, 10));
		m_generals.add(new Triple<Integer, Integer, Integer>(6, 1, 10));
		m_generals.add(new Triple<Integer, Integer, Integer>(6, 2, 10));
		m_generals.add(new Triple<Integer, Integer, Integer>(6, 4, 10));
		m_generals.add(new Triple<Integer, Integer, Integer>(7, 2, 10));
		m_generals.add(new Triple<Integer, Integer, Integer>(7, 4, 10));
		m_generals.add(new Triple<Integer, Integer, Integer>(8, 2, 10));
	}

	static class Mission {
		private int m_level;

		private int m_stage;

		private int m_id;

		public Mission(int level, int stage, int id) {
			m_level = level;
			m_stage = stage;
			m_id = id;
		}

		public void setup(TaskContext ctx) {
			ctx.setAttribute("info.missionlevel", String.valueOf(m_level));
			ctx.setAttribute("info.missionstage", String.valueOf(m_stage));
			ctx.setAttribute("info.missionid", String.valueOf(m_id));
		}
	}
}
