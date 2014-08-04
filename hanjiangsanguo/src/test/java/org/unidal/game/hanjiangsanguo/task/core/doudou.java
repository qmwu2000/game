package org.unidal.game.hanjiangsanguo.task.core;

import org.junit.Before;
import org.junit.Test;
import org.unidal.game.hanjiangsanguo.task.Task;
import org.unidal.game.hanjiangsanguo.task.TaskDriver;
import org.unidal.lookup.ComponentTestCase;

public class doudou extends ComponentTestCase {
	static {
		System.setProperty("devMode", "true");
	}

	@Before
	public void before() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, LoginTask.ID);

		driver.setup("SUPERWYX", "wyx1116", "107", //
		      "practice/gid", "81151", //
		      "general/gid", "81151", "general/id", "106", //
		      "worldboss/list", "-2,60009,-2,-2,43887,-2,136825,128609,66097", "worldboss/mid", "10" //
		);
		driver.execute(task);
	}

	@Test
	public void others() throws Exception {
		vipwage();
		lottery();
		drink();
		general();
		business();
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
	public void countryBoss() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, CountryBossTask.ID);

		driver.execute(task);
	}


	@Test
	public void worldbossboss() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, NewYearBossTask.ID);

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

		driver.execute(task, "practice/action", "go_leap", "practice/maxtimes", "5");
	}

	@Test
	public void general() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, GeneralTask.ID);

		driver.execute(task);
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
	public void mine() throws Exception{
		TaskDriver driver = lookup(TaskDriver.class);
		
		driver.getContext().setAttribute("dahao", "dahao");
		driver.go("countryMine", "mine"); 

	}
	
	@Test
	public void mapScroll() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, MapTask.ID);
//		int[] types = { 9, 8, 6 };
//
//		for (int type : types) {
//			driver.execute(task, "map/action", "scroll", "map/scroll.color", "blue", "map/scroll.type", "" + type);
//		}

		int[] types2 = { 9,7 };
		for (int type : types2) {
			driver.execute(task, "map/action", "scroll", "map/scroll.color", "yellow", "map/scroll.type", "" + type);
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
	public void workshop() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		Task task = lookup(Task.class, WorkshopTask.ID);

		driver.execute(task);
	}
}
