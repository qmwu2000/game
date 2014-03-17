package org.unidal.game.hanjiangsanguo.task.core;

import org.junit.Test;
import org.unidal.game.hanjiangsanguo.task.Task;
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
	public void mapTo10() throws Exception {
		TaskDriver driver = map();
		
		Task task = lookup(Task.class, MapActionTask.ID);
		
		for (int i = 1; i <= 10; i++) {
			driver.execute(task, "map/params", "l=1&s=1&id=" + i);
		}
	}

	@Test
	public void mapTo20() throws Exception {
		TaskDriver driver = map();

		Task task = lookup(Task.class, MapActionTask.ID);
		
		for (int i = 1; i <= 10; i++) {
			driver.execute(task, "map/params", "l=1&s=2&id=" + i);
		}
	}
	
	@Test
	public void mapTo30() throws Exception {
		TaskDriver driver = map();
		
		Task task = lookup(Task.class, MapActionTask.ID);
		
		for (int i = 1; i <= 10; i++) {
			driver.execute(task, "map/params", "l=1&s=3&id=" + i);
		}
	}
	
	@Test
	public void mapTo40() throws Exception {
		TaskDriver driver = map();
		
		Task task = lookup(Task.class, MapActionTask.ID);
		
		for (int i = 1; i <= 10; i++) {
			driver.execute(task, "map/params", "l=1&s=4&id=" + i);
		}
	}
	
	@Test
	public void mapTo50() throws Exception {
		TaskDriver driver = map();
		
		Task task = lookup(Task.class, MapActionTask.ID);
		
		for (int i = 1; i <= 10; i++) {
			driver.execute(task, "map/params", "l=2&s=2&id=" + i);
		}
	}
	
	@Test
	public void mapTo60() throws Exception {
		TaskDriver driver = map();
		
		Task task = lookup(Task.class, MapActionTask.ID);
		
		for (int i = 1; i <= 10; i++) {
			driver.execute(task, "map/params", "l=2&s=3&id=" + i);
		}
	}
	
	@Test
	public void mapTo70() throws Exception {
		TaskDriver driver = map();
		
		Task task = lookup(Task.class, MapActionTask.ID);
		
		for (int i = 4; i <= 10; i++) {
			driver.execute(task, "map/params", "l=2&s=4&id=" + i);
		}
	}

	private TaskDriver map() throws Exception {
	   TaskDriver driver = lookup(TaskDriver.class);
		Task login = lookup(Task.class, LoginTask.ID);
		
		driver.setup("qmwu2016", "qmwu2016", "107");
		driver.execute(login);
	   return driver;
   }

	@Test
	public void practiceGoLeap() throws Exception {
		before();

		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, PracticeTask.ID);

		driver.execute(task, "practice/action", "go_leap", "practice/maxtimes", "67");
	}

	@Test
	public void register100() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, RegisterTask.ID);

		driver.setup(null, null, "107", "user/token", "");
		driver.execute(task, "user/maxtimes", "100");
	}

	@Test
	public void registerOne() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, RegisterTask.ID);

		driver.setup(null, null, "107", "user/token", "");
		driver.execute(task, "user/username", "qmwu2016", "user/password", "qmwu2016", "user/name", "小柯001");
	}
}
