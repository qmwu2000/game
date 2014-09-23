package org.unidal.game.hanjiangsanguo.task.activity;

import org.codehaus.plexus.logging.LogEnabled;
import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;
import org.unidal.game.hanjiangsanguo.task.TaskHelper;
import org.unidal.lookup.ContainerHolder;

public abstract class AbstractTaskActivity extends ContainerHolder implements TaskActivity, Initializable, LogEnabled {
	protected TaskHelper m_helper;

	protected Logger m_logger;

	@Override
	public void enableLogging(Logger logger) {
		m_logger = logger;
	}

	@Override
	public void initialize() throws InitializationException {
		m_helper = lookup(TaskHelper.class);
	}

	protected void ensure(boolean... expressions) {
		for (int i = 0; i < expressions.length; i++) {
			if (!expressions[i]) {
				throw new RuntimeException("Failed on expression: " + i);
			}
		}
	}

}
