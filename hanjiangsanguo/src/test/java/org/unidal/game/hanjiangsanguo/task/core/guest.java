package org.unidal.game.hanjiangsanguo.task.core;

import org.junit.Test;
import org.unidal.game.hanjiangsanguo.task.Task;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.game.hanjiangsanguo.task.TaskDriver;
import org.unidal.lookup.ComponentTestCase;

public class guest extends ComponentTestCase {
	static {
		System.setProperty("devMode", "true");
	}

	@Test
	public void register100() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, RegisterTask.ID);

		for (int i = 0; i < 100; i++) {
			TaskContext ctx = driver.setup(null, null, "107", "user/token", "");

			driver.execute(task);

			if ("true".equals(ctx.getAttribute("hired"))) {
				break;
			}
		}
	}

	@Test
	public void registerOne() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, RegisterTask.ID);

		driver.setup(null, null, "107", "user/token", "");

		driver.execute(task, "user/username", "qmwu2015", "user/password", "qmwu2015");
	}
}
