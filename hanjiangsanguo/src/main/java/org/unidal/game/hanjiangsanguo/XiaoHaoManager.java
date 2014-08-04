package org.unidal.game.hanjiangsanguo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.plexus.logging.LogEnabled;
import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.game.hanjiangsanguo.task.TaskDriver;
import org.unidal.game.hanjiangsanguo.task.activity.ArenaActivity;
import org.unidal.game.hanjiangsanguo.task.activity.MineActivity;
import org.unidal.helper.Files;
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

	private Map<String, Boolean> m_countryState = new HashMap<String, Boolean>();

	public void empty() {
	}

	private void buildArenaInfo(String server, String account, String password, TaskDriver driver) throws Exception {
		try {
			Calendar cal = Calendar.getInstance();
			int hour = cal.get(Calendar.HOUR_OF_DAY);

			if (hour == 23 && isArena()) {
				System.err.println("====== true");
				try {
					driver.go("login", server, account, password);
					driver.go("arena", "rank");

					m_arenaUid = driver.getContext().getAttribute("arena", "uid");
					m_arenaServerId = driver.getContext().getAttribute("arena", "server");
					driver.reset();
				} catch (Exception e) {
					m_logger.error(e.getMessage(), e);
				}

				File file = new File("target/arean.position");

				if (file != null) {
					file.createNewFile();
				}
				Date date = getCurrentDay();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String key = sdf.format(date);
				String content = Files.forIO().readFrom(file, "utf-8");
				Files.forIO().writeTo(file, content + "\n" + key + "=true");

				m_arena = true;
			} else {

				System.err.println("====== false");
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
					if (!isBossTime()) {
						m_arena = false;
						m_firstInDay = isFirstInDay();
						buildArenaInfo("107", "2xiaohao362", "2xiaohao362", m_driver);

						TaskContext context = m_driver.getContext();

						context.setAttribute(MineActivity.ID, "maxMineGold", "320000");
						daHaoAction("3023", "138760", "forever123F", m_driver, true);

						context = m_driver.getContext();
						context.setAttribute(MineActivity.ID, "maxMineGold", "400000");
						daHaoAction("107", "superwyx", "wyx1116", m_driver, false);

						context = m_driver.getContext();
						context.setAttribute(MineActivity.ID, "maxMineGold", "400000");
						daHaoAction("107", "youyong2014", "forever123", m_driver, false);

						context = m_driver.getContext();
						context.setAttribute(MineActivity.ID, "maxMineGold", "400000");
						daHaoAction("107", "58289076", "tw648467992qq", m_driver, false);

						context = m_driver.getContext();
						context.setAttribute(MineActivity.ID, "maxMineGold", "400000");
						daHaoAction("107", "qq1525262674", "qq1525262674", m_driver, false);

						xiaohaoTask();
					}
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

	private void addCountry(String account) throws Exception {
		TaskDriver driver = m_driver;
		driver.go("login", "107", account, account);

		driver.go("country", "addCountry");

		driver.reset();

		driver.go("login", "107", "superwyx", "wyx1116");
		driver.go("country", "approve");
		driver.reset();
	}

	private void exitCountry(String account) throws Exception {
		TaskDriver driver = m_driver;
		driver.go("login", "107", account, account);

		driver.go("country", "exitCountry");
		driver.reset();
	}

	private void xiaohaoCountry(String account) throws Exception {
		addCountry(account);

		TaskDriver driver = m_driver;

		driver.go("login", "107", account, account);

		if (driver.getContext().getIntAttribute("member", "country", 0) > 0) {
			if (m_firstInDay) {
				driver.go("gift", "country"); // 国库
				driver.go("country", "sacrifice"); // 祭祀
				driver.go("country", "donate", "222500"); // 捐献
			}
			driver.go("country", "expostulation"); // 谏言
		}
		driver.reset();

		exitCountry(account);
	}

	private void xiaohaoTask() throws Exception {
		String[] accounts = { "xiaohao856", "2xiaohao362", "2xiaohao996", "2xiaohao543", "2xiaohao583", "2xiaohao508",
		      "youyong3918", "1xiaohao24", "1xiaohao970", "1xiaohao73", "1xiaohao966", "youyong3772", "1xiaohao428",
		      "gongxian134", "xiaohao531", "1xiaohao320", "1xiaohao293", "xiaohao816", "qmwu2015", "qmwu2016",
		      "gongxian8196", "xiaoke01", "xiaoke02" };

		try {
			TaskDriver driver = m_driver;

			xiaohaoAction(accounts, driver);
			//xiaohaoAction(accounts, driver);
		} catch (Exception e) {
			m_logger.error(e.getMessage(), e);
		}
		
		System.out.println("===========================");

		try {
			String firstIn = null;
			for (Entry<String, Boolean> entry : m_countryState.entrySet()) {
				String key = entry.getKey();
				Boolean value = entry.getValue();

				if (value == true) {
					firstIn = key;
					break;
				}
			}
			
			System.out.println("firstIn:"+firstIn);
			exitCountry(firstIn);
			
			Thread.sleep(1000);
			
			for (Entry<String, Boolean> entry : m_countryState.entrySet()) {
				String key = entry.getKey();
				Boolean value = entry.getValue();

				if (value == false) {
					System.out.println("jiaru:"+key);
					xiaohaoCountry(key);
					break;
				}
			}
			addCountry(firstIn);
		} catch (Exception e) {
			e.printStackTrace();
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
				driver.go("gift", "task"); // 任务
				driver.go("lottery", "lave"); // 每日抽奖
				driver.go("trade", "business"); // 每日通商
				driver.go("map", "island", "10"); // 金银洞
				driver.go("gift", "arena"); // 演武榜,押注
			//	driver.go("city", "exercise"); // 征收
			//	driver.go("gift", "exercise"); // 整军
				driver.go("drink", "drink"); // 饮酒
				driver.go("activity", "sacredtree"); // 神树
				driver.go("activity", "springlottery"); // 幸运大转盘
				driver.go("activity", "acttreasure"); // 寻宝
			}

			driver.go("gift", "hitegg"); // 砸金蛋

			if (driver.getContext().getIntAttribute("member", "country", 0) > 0) {
				if (m_firstInDay) {
					driver.go("gift", "country"); // 国库
					driver.go("country", "sacrifice"); // 祭祀
					driver.go("country", "dice"); // 国家骰子
					driver.go("country", "donate", "222500"); // 捐献
					driver.go("gift", "dice"); // 骰子
				}
				if (isIdleTime()) {
					driver.go("trade", "oversea"); // 海外贸易
				}
				driver.go("country", "expostulation"); // 谏言
				driver.go("banquet", "active"); // 国宴

				m_countryState.put(account, true);
			} else {
				m_countryState.put(account, false);
			}
			driver.reset();
		}
	}

	protected void daHaoAction(String server, String account, String password, TaskDriver driver, boolean caikuang)
	      throws Exception {
		try {

			driver.go("login", server, account, password);
			driver.getContext().setAttribute("member", "dahao", "dahao");

			if (m_firstInDay) {
				driver.go("gift", "vip"); // VIP工资
				driver.go("gift", "login"); // 连续登录
				driver.go("activity", "sacredtree"); // 神树
				driver.go("lottery", "lave"); // 每日抽奖
				driver.go("drink", "drink"); // 饮酒
				driver.go("gift", "arena"); // 演武榜,押注
			}

			if (m_arena) {
				driver.getContext().setAttribute(ArenaActivity.ID, "uid", m_arenaUid);
				driver.getContext().setAttribute(ArenaActivity.ID, "server", m_arenaServerId);
				driver.go("arena", "bet");
			}

			driver.go("gift", "hitegg"); // 砸金蛋

			if (caikuang) {
				driver.go("mine", "active"); // 银矿
			}

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

	protected boolean isMineTime() {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);

		if (hour >= 1 && hour <= 9) {
			return true;
		}
		return false;
	}

	@Override
	public void enableLogging(Logger logger) {
		m_logger = logger;
	}
}
