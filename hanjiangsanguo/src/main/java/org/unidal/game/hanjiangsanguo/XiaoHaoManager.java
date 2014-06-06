package org.unidal.game.hanjiangsanguo;

import java.util.Calendar;

import org.codehaus.plexus.logging.LogEnabled;
import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.game.hanjiangsanguo.task.TaskDriver;
import org.unidal.game.hanjiangsanguo.task.activity.ArenaActivity;
import org.unidal.game.hanjiangsanguo.task.activity.MineActivity;
import org.unidal.helper.Threads;
import org.unidal.lookup.annotation.Inject;

public class XiaoHaoManager extends BaseManager implements Initializable, LogEnabled {

	@Inject
	private TaskDriver m_driver;

	private Logger m_logger;

	private String m_arenaUid = null;

	private String m_arenaServerId = null;

	private boolean m_arena = false;

	private boolean m_firstInDay = false;

	public void empty() {
	}

	private void buildArenaInfo(String server, String account, String password, TaskDriver driver) throws Exception {
		try {
			Calendar cal = Calendar.getInstance();
			int hour = cal.get(Calendar.HOUR_OF_DAY);

			if (isArena() && hour == 23) {
				try {
					driver.go("login", server, account, password);
					driver.go("arena", "rank");

					m_arenaUid = driver.getContext().getAttribute("arena", "uid");
					m_arenaServerId = driver.getContext().getAttribute("arena", "server");
					driver.reset();
				} catch (Exception e) {
					m_logger.error(e.getMessage(), e);
				}
				m_arena = true;
			} else {
				m_arena = false;
			}
		} catch (Exception e) {
			m_logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void initialize() throws InitializationException {
		Threads.forGroup("hanjiang").start(new RobotTask());
	}

	public class RobotTask implements Runnable {

		@Override
		public void run() {
			boolean active = true;

			while (active) {
				try {
					m_firstInDay = isFirstInDay();
					buildArenaInfo("107", "2xiaohao362", "2xiaohao362", m_driver);

					if (!isBossTime()) {
						TaskContext context = m_driver.getContext();

						context.setAttribute(MineActivity.ID, "maxMineGold", "320000");
						daHaoAction("3023", "138760", "forever123F", m_driver);

						context = m_driver.getContext();
						context.setAttribute(MineActivity.ID, "maxMineGold", "150000");
						daHaoAction("107", "superwyx", "wyx1116", m_driver);

						context = m_driver.getContext();
						context.setAttribute(MineActivity.ID, "maxMineGold", "150000");
						daHaoAction("107", "youyong2014", "forever123", m_driver);
					}
					xiaohao();
				} catch (Exception e) {
					m_logger.error(e.getMessage(), e);
				}

				try {
					Thread.sleep(1000 * 60 * 15);
				} catch (InterruptedException e) {
					break;
				}
			}
		}
	}

	private void xiaohao() throws Exception {
		try {
			String[] accounts = { "xiaohao856", "2xiaohao362", "2xiaohao996", "2xiaohao543", "2xiaohao583", "2xiaohao508",
			      "youyong3918", "1xiaohao24", "1xiaohao970", "1xiaohao73", "1xiaohao966", "youyong3772", "1xiaohao428",
			      "gongxian134", "xiaohao531", "1xiaohao320", "1xiaohao293", "xiaohao816", "qmwu2015", "qmwu2016",
			      "gongxian8196", "xiaoke01", "xiaoke02" };

			TaskDriver driver = m_driver;

			xiaohaoAction(accounts, driver);
			xiaohaoAction(accounts, driver);
		} catch (Exception e) {
			m_logger.error(e.getMessage(), e);
		}
	}

	private void xiaohaoAction(String[] accounts, TaskDriver driver) throws Exception {

		for (String account : accounts) {
			driver.go("login", "107", account, account);

			if (m_arena) {
				driver.getContext().setAttribute(ArenaActivity.ID, "uid", m_arenaUid);
				driver.getContext().setAttribute(ArenaActivity.ID, "server", m_arenaServerId);
				driver.go("arena", "bet");
			}

			if (m_firstInDay) {
				driver.go("gift", "vip"); // VIP工资
				driver.go("gift", "login"); // 连续登录
				driver.go("gift", "hitegg"); // 砸金蛋
				driver.go("gift", "arena"); // 演武榜,押注
				driver.go("gift", "task"); // 任务
				driver.go("lottery", "lave"); // 每日抽奖
				driver.go("trade", "business"); // 每日通商
				driver.go("map", "island", "10"); // 金银洞
				driver.go("city", "exercise"); // 征收
				driver.go("gift", "exercise"); // 整军
				driver.go("activity", "sacredtree"); // 神树
				driver.go("activity", "springlottery"); // 幸运大转盘
			}

			if (driver.getContext().getIntAttribute("member", "country", 0) > 0) {
				if (m_firstInDay) {
					driver.go("gift", "country"); // 国库
					driver.go("country", "sacrifice"); // 祭祀
					driver.go("country", "expostulation"); // 谏言
					driver.go("country", "dice"); // 国家骰子
					driver.go("country", "donate", "222500"); // 捐献
					driver.go("gift", "dice"); // 骰子
				}
				if (isIdleTime()) {
					driver.go("trade", "oversea"); // 海外贸易
				}
				driver.go("banquet", "active"); // 国宴
			}
			driver.reset();
		}
	}

	protected void daHaoAction(String server, String account, String password, TaskDriver driver) throws Exception {
		try {

			driver.go("login", server, account, password);
			driver.getContext().setAttribute("member", "dahao", "dahao");

			if (m_firstInDay) {
				driver.go("gift", "vip"); // VIP工资
				driver.go("gift", "login"); // 连续登录
				driver.go("activity", "sacredtree"); // 神树
				driver.go("activity", "springlottery"); // 幸运大转盘
			}

			if (m_arena) {
				driver.getContext().setAttribute(ArenaActivity.ID, "uid", m_arenaUid);
				driver.getContext().setAttribute(ArenaActivity.ID, "server", m_arenaServerId);
				driver.go("arena", "bet");
			}

			driver.go("gift", "hitegg"); // 砸金蛋
			driver.go("mine", "active"); // 银矿

			if (driver.getContext().getIntAttribute("member", "country", 0) > 0) {
				if (m_firstInDay) {
					driver.go("gift", "country"); // 国库
					driver.go("country", "sacrifice"); // 祭祀
					driver.go("country", "dice"); // 国家骰子
				}
				driver.go("banquet", "active"); // 国宴

				if (isIdleTime()) {
					driver.go("countrymine", "active"); // 国家矿
					driver.go("trade", "oversea"); // 海外贸易
				}
			}
			driver.reset();
		} catch (Exception e) {
			m_logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void enableLogging(Logger logger) {
		m_logger = logger;
	}
}
