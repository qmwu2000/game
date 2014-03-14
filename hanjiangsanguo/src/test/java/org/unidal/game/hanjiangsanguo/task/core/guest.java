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

	public void before() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, LoginTask.ID);

		driver.setup("gongxian8196", "gongxian8196", "107", //
		      "practice/gid", "86386", //
		      "general/gid", "86386", "general/id", "37", //
		      "worldboss/list", "78314,78522,78313,-1,78320,-1,-1,78519,-1", "worldboss/mid", "2" //
		);
		driver.execute(task);
	}

	@Test
	public void practiceGoLeap() throws Exception {
		before();

		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, PracticeTask.ID);

		driver.execute(task, "practice/action", "go_leap", "practice/maxtimes", "154");
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
