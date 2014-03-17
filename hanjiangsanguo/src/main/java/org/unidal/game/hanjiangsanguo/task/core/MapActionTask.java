package org.unidal.game.hanjiangsanguo.task.core;

import org.unidal.game.hanjiangsanguo.task.Task;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.game.hanjiangsanguo.task.TaskHelper;
import org.unidal.lookup.annotation.Inject;

public class MapActionTask implements Task {
	public static final String ID = "map.action";

	@Inject
	private TaskHelper m_helper;

	@Override
	public void execute(TaskContext ctx) throws Exception {
		ctx.setDefaultCategory("map");

		int maxTimes = ctx.getIntAttribute("maxtimes", 1);

		while (maxTimes-- > 0) {
			if (handleAction(ctx)) {
				break;
			}
		}
	}

	private boolean handleAction(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "map", "action", "&%s", "params");

		m_helper.doGet(ctx, url, "info.win");

		return ctx.getIntAttribute("info.win", 0) > 0;
	}
}
