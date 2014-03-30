package org.unidal.game.hanjiangsanguo.task.core;

import org.junit.Before;
import org.junit.Test;
import org.unidal.game.hanjiangsanguo.task.Task;
import org.unidal.game.hanjiangsanguo.task.TaskDriver;
import org.unidal.lookup.ComponentTestCase;

public class youyong2014 extends ComponentTestCase {
	private TaskDriver m_driver;

	@Before
	public void before() throws Exception {
		System.setProperty("devMode", "true");

		Task task = lookup(Task.class, LoginTask.ID);

		m_driver = lookup(TaskDriver.class);
		m_driver.setup("youyong2014", "forever123", "107", //
		      "practice/gid", "81151", //
		      "general/gid", "81151", "general/id", "106", //
		      "worldboss/list", "65914,-1,70058,-1,68660,-1,68659,-1,81151", "worldboss/mid", "4" //
		);

		m_driver.execute(task);
	}

	@Test
	public void all() throws Exception {
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
		Task task = lookup(Task.class, WorldbossTask.ID);

		m_driver.execute(task);
	}

	@Test
	public void activity() throws Exception {
		Task task = lookup(Task.class, ActivityTask.ID);

		m_driver.execute(task);
	}

	@Test
	public void vipwage() throws Exception {
		Task task = lookup(Task.class, VipwageTask.ID);

		m_driver.execute(task);
	}

	@Test
	public void lottery() throws Exception {
		Task task = lookup(Task.class, LotteryTask.ID);

		m_driver.execute(task);
	}

	@Test
	public void drink() throws Exception {
		Task task = lookup(Task.class, DrinkTask.ID);

		m_driver.execute(task);
	}

	@Test
	public void tavernTrade() throws Exception {
		Task task = lookup(Task.class, TavernTask.ID);

		for (int i = 0; i < 200; i++) {
			m_driver.execute(task);
		}
	}

	@Test
	public void task() throws Exception {
		Task task = lookup(Task.class, TaskTask.ID);

		m_driver.execute(task);
	}

	@Test
	public void tavernBuy() throws Exception {
		Task task = lookup(Task.class, TavernTask.ID);

		for (int i = 0; i < 200; i++) {
			m_driver.execute(task, "tavern/action", "buy", "tavern/generalid", "1000");
		}
	}

	@Test
	public void business() throws Exception {
		Task task = lookup(Task.class, BusinessTask.ID);

		m_driver.execute(task);
	}

	@Test
	public void practiceAll() throws Exception {
		Task task = lookup(Task.class, PracticeTask.ID);

		m_driver.execute(task);
	}

	@Test
	public void practiceGoLeap() throws Exception {
		Task task = lookup(Task.class, PracticeTask.ID);

		m_driver.execute(task, "practice/action", "go_leap", "practice/maxtimes", "5");
	}

	@Test
	public void general() throws Exception {
		Task task = lookup(Task.class, GeneralTask.ID);

		m_driver.execute(task);
	}

	@Test
	public void mapReputation() throws Exception {
		Task task = lookup(Task.class, MapTask.ID);

		for (int i = 0; i < 20; i++) {
			m_driver.execute(task, "map/action", "reputation");
		}
	}

	@Test
	public void mapScroll() throws Exception {
		Task task = lookup(Task.class, MapTask.ID);

		for (int i = 6; i <= 9; i++) {
			m_driver.execute(task, "map/action", "scroll", "map/scroll.color", "blue", "map/scroll.type", "" + i);
		}
	}

	@Test
	public void mapGeneral() throws Exception {
		Task task = lookup(Task.class, MapTask.ID);

		for (int i = 0; i < 2; i++) {
			m_driver.execute(task, "map/action", "general");
		}
	}

	@Test
	public void arena() throws Exception {
		Task task = lookup(Task.class, ArenaTask.ID);

		m_driver.execute(task);
	}

	@Test
	public void workshop() throws Exception {
		Task task = lookup(Task.class, WorkshopTask.ID);

		m_driver.execute(task);
	}
}
