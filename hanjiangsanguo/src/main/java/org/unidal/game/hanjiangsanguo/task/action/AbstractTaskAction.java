package org.unidal.game.hanjiangsanguo.task.action;

import org.codehaus.plexus.logging.LogEnabled;
import org.codehaus.plexus.logging.Logger;
import org.unidal.game.hanjiangsanguo.task.TaskAction;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.game.hanjiangsanguo.task.TaskHelper;

public abstract class AbstractTaskAction implements TaskAction, LogEnabled {
	protected TaskHelper m_helper;

	protected Logger m_logger;

	@Override
	public void enableLogging(Logger logger) {
		m_logger = logger;
	}

	@Override
	public int getPriority() {
		return 10;
	}

	protected void assertContext(TaskContext ctx, String... attributes) {
		for (String attribute : attributes) {
			int pos = attribute.indexOf('/');
			String value;

			if (pos > 0) {
				String category = attribute.substring(0, pos - 1);
				String name = attribute.substring(pos + 1);

				value = ctx.getAttribute(category, name);
			} else {
				value = ctx.getAttribute(attribute);
			}

			if (value == null) {
				m_logger.warn(String.format("Context attribute(%s) not found!", attribute));
			}
		}
	}

	protected int getAct(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "member", "index", null);

		if (m_helper.doGet(ctx, url, "act:member")) {
			int act = ctx.getIntAttribute("member", "act", 0);

			return act;
		} else {
			return -1;
		}
	}

	protected int getSilver(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "member", "index", null);

		if (m_helper.doGet(ctx, url, "silver:member")) {
			int silver = ctx.getIntAttribute("member/silver", 0);

			return silver;
		} else {
			return -1;
		}
	}

	public void setTaskHelper(TaskHelper helper) {
		m_helper = helper;
	}
}
