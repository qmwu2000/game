package org.unidal.game.hanjiangsanguo.task;

import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;
import org.unidal.lookup.ContainerHolder;

public class TaskDriver extends ContainerHolder implements Initializable {
	private TaskContext m_ctx;

	public void setup(String username, String password, String server, String... params) {
		m_ctx.setAttribute("user", "username", username);
		m_ctx.setAttribute("user", "password", password);
		m_ctx.setAttribute("user", "server", server);

		setupContext(params);
	}

	@Override
	public void initialize() throws InitializationException {
		m_ctx = lookup(TaskContext.class);
	}

	public void execute(Task task, String... params) throws Exception {
		setupContext(params);

		task.execute(m_ctx);
	}

	private void setupContext(String... params) {
		int len = params.length;

		if (len % 2 != 0) {
			throw new IllegalArgumentException("Parameters is not paired!");
		}

		for (int i = 0; i < len; i += 2) {
			String key = params[i];
			String value = params[i + 1];
			int pos = key.indexOf('/');
			String category;
			String name;

			if (pos > 0) {
				category = key.substring(0, pos);
				name = key.substring(pos + 1);
			} else {
				category = "system";
				name = key;
			}

			m_ctx.setAttribute(category, name, value);
		}
	}
}
