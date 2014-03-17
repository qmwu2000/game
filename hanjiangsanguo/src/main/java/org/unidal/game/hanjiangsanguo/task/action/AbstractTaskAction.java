package org.unidal.game.hanjiangsanguo.task.action;

import org.codehaus.plexus.logging.LogEnabled;
import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;
import org.unidal.game.hanjiangsanguo.task.TaskAction;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.game.hanjiangsanguo.task.TaskHelper;
import org.unidal.lookup.ContainerHolder;

public abstract class AbstractTaskAction extends ContainerHolder implements TaskAction, Initializable, LogEnabled {
	protected TaskHelper m_helper;

	protected Logger m_logger;

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

	@Override
	public void enableLogging(Logger logger) {
		m_logger = logger;
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

	@Override
	public int getPriority() {
		return 10;
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

	@Override
	public void initialize() throws InitializationException {
		m_helper = lookup(TaskHelper.class);
	}
}
