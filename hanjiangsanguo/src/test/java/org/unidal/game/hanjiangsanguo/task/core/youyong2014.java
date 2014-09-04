package org.unidal.game.hanjiangsanguo.task.core;

import org.junit.Before;
import org.junit.Test;
import org.unidal.game.hanjiangsanguo.task.Task;
import org.unidal.game.hanjiangsanguo.task.TaskDriver;
import org.unidal.lookup.ComponentTestCase;

public class youyong2014 extends ComponentTestCase {
	static {
		System.setProperty("devMode", "true");
	}

	@Test
	public void activity() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, ActivityTask.ID);

		driver.execute(task);
	}

	@Test
	public void arena() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, ArenaTask.ID);

		driver.execute(task);
	}

	@Before
	public void before() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, LoginTask.ID);

		driver.setup("youyong2014", "forever123", "107", //
		      "practice/gid", "81151", //
		      "general/gid", "81151", "general/id", "106", //
		      "worldboss/list", "134130,-1,68659,-1,141996,-1,67302,-1,81151", "worldboss/mid", "4" //
		);
		driver.execute(task);
	}

	@Test
	public void business() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, BusinessTask.ID);

		driver.execute(task);
	}

	@Test
	public void cultivate() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);

		driver.go("login", "107", "youyong2014", "forever123");
		driver.go("cultivate", "money", "孙权", "100", "4");
	}

	@Test
	public void drink() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, DrinkTask.ID);

		driver.execute(task);
	}

	@Test
	public void general() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, GeneralTask.ID);

		driver.execute(task);
	}

	@Test
	public void lottery() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, LotteryTask.ID);

		driver.execute(task);
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
	public void mapReputation() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, MapTask.ID);

		for (int i = 0; i < 1; i++) {
			driver.execute(task, "map/action", "reputation");
		}
	}

	@Test
	public void mapScroll() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, MapTask.ID);

		for (int type : new int[] { 9, 7 }) {
			driver.execute(task, "map/action", "scroll", "map/scroll.color", "purple", "map/scroll.type", "" + type);
		}
	}

	@Test
	public void newyearboss() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, NewyearBossTask.ID);

		driver.execute(task);
	}

	@Test
	public void nop() {
	}

	@Test
	public void others() throws Exception {
		general();
		business();
		workshop();
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

		driver.execute(task, "practice/action", "go_leap", "practice/maxtimes", "5");
	}

	@Test
	public void task() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, TaskTask.ID);

		driver.execute(task);
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
	public void tavernTrade() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, TavernTask.ID);

		for (int i = 0; i < 200; i++) {
			driver.execute(task);
		}
	}

	@Test
	public void vipwage() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, VipwageTask.ID);

		driver.execute(task);
	}

	@Test
	public void workshop() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, WorkshopTask.ID);

		driver.execute(task);
	}

	@Test
	public void worldboss() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, WorldbossTask.ID);

		driver.execute(task);
	}
}
