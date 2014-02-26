package org.unidal.game.hanjiangsanguo.task.core;

import org.junit.Before;
import org.junit.Test;
import org.unidal.game.hanjiangsanguo.task.Task;
import org.unidal.game.hanjiangsanguo.task.TaskDriver;
import org.unidal.lookup.ComponentTestCase;

public class youyong2009 extends ComponentTestCase {
	@Before
	public void before() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, LoginTask.ID);

		driver.setup("youyong2009", "forever123", "99", //
				"practice/gid", "137664", //
				"general/gid", "137664", "general/id", "86", //
				"worldboss/list", "136503,-1,145409,-1,139938,-1,147050,-1,137664", "worldboss/mid", "4" //
				);
		driver.execute(task);
	}

	@Test
	public void all() throws Exception {
		vipwage();
		lottery();
		drink();
		general();
		workshop();
	}
	
	@Test
	public void nop() {
	}

	@Test
	public void worldboss() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, WorldbossTask.ID);
		
		driver.execute(task);
	}
	
	@Test
	public void general() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, GeneralTask.ID);
		
		driver.execute(task);
	}
	
	@Test
	public void activity() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, ActivityTask.ID);

		driver.execute(task);
	}

	@Test
	public void vipwage() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, VipwageTask.ID);

		driver.execute(task);
	}

	@Test
	public void lottery() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, LotteryTask.ID);

		driver.execute(task);
	}

	@Test
	public void drink() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, DrinkTask.ID);

		driver.execute(task);
	}

	@Test
	public void tavernTrade() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, TavernTask.ID);

		for (int i = 0; i < 200; i++) {
			driver.execute(task);
		}
	}

	@Test
	public void tavernBuy() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, TavernTask.ID);

		for (int i = 0; i < 200; i++) {
			driver.execute(task, "tavern/action", "buy", "tavern/generalid", "1000");
		}
	}

	@Test
	public void business() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, BusinessTask.ID);

		driver.execute(task);
	}

	@Test
	public void practiceAll() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, PracticeTask.ID);

		driver.execute(task);
	}

	@Test
	public void practiceGoLeap() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, PracticeTask.ID);

		for (int i = 0; i < 106; i++) {
			driver.execute(task, "practice/action", "go_leap");
		}
	}

	@Test
	public void mapReputation() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, MapTask.ID);

		for (int i = 0; i < 10; i++) {
			driver.execute(task, "map/action", "reputation");
		}
	}
	
	@Test
	public void mapGeneral() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, MapTask.ID);
		
		for (int i = 0; i < 2; i++) {
			driver.execute(task, "map/action", "general");
		}
	}

	@Test
	public void arena() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, ArenaTask.ID);

		driver.execute(task);
	}

	@Test
	public void task() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, TaskTask.ID);

		driver.execute(task);
	}

	@Test
	public void workshop() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, WorkshopTask.ID);

		driver.execute(task);
	}
}
