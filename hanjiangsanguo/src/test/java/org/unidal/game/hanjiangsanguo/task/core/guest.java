package org.unidal.game.hanjiangsanguo.task.core;

import org.junit.Test;
import org.unidal.game.hanjiangsanguo.task.Task;
import org.unidal.game.hanjiangsanguo.task.TaskDriver;
import org.unidal.lookup.ComponentTestCase;

public class guest extends ComponentTestCase {
	private TaskDriver m_driver;

	public void before() throws Exception {
		System.setProperty("devMode", "true");

		m_driver = lookup(TaskDriver.class);

		Task task = lookup(Task.class, LoginTask.ID);

		m_driver.setup("gongxian8196", "gongxian8196", "107", //
		      "practice/gid", "86386", //
		      "general/gid", "86386", "general/id", "37", //
		      "worldboss/list", "78314,78522,78313,-1,78320,-1,-1,78519,-1", "worldboss/mid", "2" //
		);
		m_driver.execute(task);
	}

	@Test
	public void practiceGoLeap() throws Exception {
		before();

		Task task = lookup(Task.class, PracticeTask.ID);

		m_driver.execute(task, "practice/action", "go_leap", "practice/maxtimes", "67");
	}

	@Test
	public void register100() throws Exception {
		Task task = lookup(Task.class, RegisterTask.ID);

		m_driver.setup(null, null, "107", "user/token", "");
		m_driver.execute(task, "user/maxtimes", "100");
	}

	@Test
	public void registerOne() throws Exception {
		Task task = lookup(Task.class, RegisterTask.ID);
		String index = "09";

		m_driver.setup(null, null, "107", "user/token", "");
		m_driver.execute(task, "user/username", "xiaoke" + index, "user/password", "xiaoke" + index, "user/name", "小柯");
	}
}
