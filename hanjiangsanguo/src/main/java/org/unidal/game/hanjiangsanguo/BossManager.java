package org.unidal.game.hanjiangsanguo;

import java.util.Calendar;

import org.codehaus.plexus.logging.LogEnabled;
import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;
import org.unidal.game.hanjiangsanguo.task.Task;
import org.unidal.game.hanjiangsanguo.task.TaskDriver;
import org.unidal.game.hanjiangsanguo.task.core.CountryBossTask;
import org.unidal.game.hanjiangsanguo.task.core.LoginTask;
import org.unidal.game.hanjiangsanguo.task.core.WorldbossTask;
import org.unidal.helper.Threads;
import org.unidal.lookup.ContainerHolder;

public class BossManager extends ContainerHolder implements Initializable, Runnable, LogEnabled {

	public void empty() {
	}

	@Override
	public void enableLogging(Logger logger) {
	}

	@Override
	public void initialize() throws InitializationException {
		Threads.forGroup("game").start(this);
	}

	@Override
	public void run() {
		while (true) {
			Calendar cal = Calendar.getInstance();
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			int minute = cal.get(Calendar.MINUTE);
			int week = cal.get(Calendar.DAY_OF_WEEK);

			if ((hour == 20) && minute == 0) {
				Threads.forGroup("game")
				      .start(
				            new WorldBoss("3023", "138760", "forever123F",
				                  "-1,179051,-1,201942,-1,185227,190951,-1,195761", "1"));
				Threads.forGroup("game").start(
				      new WorldBoss("107", "superwyx", "wyx1116", "-2,60009,-2,-2,43887,-2,136825,128609,66097", "10"));
				Threads.forGroup("game").start(
				      new WorldBoss("107", "youyong2014", "forever123", "65914,-1,70058,-1,68660,-1,67302,-1,81151", "4"));
			}

			if (hour == 20 && minute == 30 && week == Calendar.FRIDAY) {
				Threads.forGroup("game").start(
				      new CountryBoss("3023", "138760", "forever123F", "-1,179051,-1,201942,-1,185227,190951,-1,195761",
				            "1"));
				Threads.forGroup("game").start(
				      new CountryBoss("107", "superwyx", "wyx1116", "-2,60009,-2,-2,43887,-2,136825,128609,66097", "10"));
				Threads.forGroup("game")
				      .start(
				            new CountryBoss("107", "youyong2014", "forever123",
				                  "65914,-1,70058,-1,68660,-1,67302,-1,81151", "4"));
			}

			try {
				Thread.sleep(60 * 1000);
			} catch (InterruptedException e) {
			}
		}
	}

	public class CountryBoss implements Runnable {

		private String m_userName;

		private String m_pwd;

		private String m_server;

		private String m_list;

		private String m_mid;

		private CountryBoss(String server, String userName, String pwd, String list, String mid) {
			m_userName = userName;
			m_pwd = pwd;
			m_server = server;
			m_list = list;
			m_mid = mid;
		}

		@Override
		public void run() {
			try {
				TaskDriver driver = lookup(TaskDriver.class);
				Task task = lookup(Task.class, LoginTask.ID);

				driver.setup(m_userName, m_pwd, m_server, "worldboss/list", m_list, "worldboss/mid", m_mid);
				driver.execute(task);

				task = lookup(Task.class, CountryBossTask.ID);
				driver.execute(task);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public class WorldBoss implements Runnable {

		private String m_userName;

		private String m_pwd;

		private String m_server;

		private String m_list;

		private String m_mid;

		private WorldBoss(String server, String userName, String pwd, String list, String mid) {
			m_userName = userName;
			m_pwd = pwd;
			m_server = server;
			m_list = list;
			m_mid = mid;
		}

		@Override
		public void run() {
			try {
				TaskDriver driver = lookup(TaskDriver.class);
				Task task = lookup(Task.class, LoginTask.ID);

				driver.setup(m_userName, m_pwd, m_server, "worldboss/list", m_list, "worldboss/mid", m_mid);
				driver.execute(task);

				task = lookup(Task.class, WorldbossTask.ID);
				driver.execute(task);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
