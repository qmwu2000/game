package org.unidal.game.hanjiangsanguo.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.plexus.logging.LogEnabled;
import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;
import org.unidal.lookup.ContainerHolder;
import org.unidal.lookup.annotation.Inject;

public class DefaultTaskActionManager extends ContainerHolder implements TaskActionManager, Initializable, LogEnabled {
	@Inject
	protected TaskHelper m_helper;

	private Logger m_logger;

	private Map<String, TaskAction> m_actions = new HashMap<String, TaskAction>();

	@Override
	public void enableLogging(Logger logger) {
		m_logger = logger;
	}

	@Override
	public void execute(TaskContext ctx, String type, int times) throws Exception {
		TaskAction task = getAction(type);
		int availableTimes = task.getAvailableTimes(ctx);

		if (times <= availableTimes) {
			m_logger.info(String.format("Doing action(%s) for %s times ...", task.getClass().getSimpleName(), times));

			for (int i = 0; i < times; i++) {
				task.doAction(ctx);
			}
		}
	}

	private TaskAction getAction(String type) {
		TaskAction action = m_actions.get(type);

		if (action != null) {
			return action;
		} else {
			throw new IllegalStateException(String.format("TaskAction(%s) is not confiugred!", type));
		}
	}

	@Override
	public int getPriority(TaskContext ctx, String type, int times) throws Exception {
		TaskAction task = getAction(type);
		int availableTimes = task.getAvailableTimes(ctx);

		if (times <= availableTimes) {
			return task.getPriority() + times;
		} else {
			return 0;
		}
	}

	@Override
	public void initialize() throws InitializationException {
		List<TaskAction> list = lookupList(TaskAction.class);

		for (TaskAction action : list) {
			TaskAction oldAction = m_actions.put(action.getType(), action);

			if (oldAction != null) {
				throw new IllegalStateException(String.format("TaskAction(%s) is conflicted with %s!", action.getClass()
				      .getSimpleName(), oldAction.getClass().getSimpleName()));
			}
		}
	}
}
