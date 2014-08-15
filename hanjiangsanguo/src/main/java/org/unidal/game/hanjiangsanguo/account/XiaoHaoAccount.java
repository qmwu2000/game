package org.unidal.game.hanjiangsanguo.account;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.plexus.logging.LogEnabled;
import org.codehaus.plexus.logging.Logger;
import org.unidal.game.hanjiangsanguo.task.TaskDriver;
import org.unidal.game.hanjiangsanguo.task.activity.ArenaActivity;
import org.unidal.lookup.annotation.Inject;

public class XiaoHaoAccount implements LogEnabled {

	@Inject
	protected TaskDriver m_driver;

	private Map<String, Boolean> m_countryState = new HashMap<String, Boolean>();

	private Logger m_logger;

	public Map<String, String> getAccounts() {
		String[] accounts = { "xiaohao856", "2xiaohao362", "2xiaohao996", "2xiaohao543", "2xiaohao583", "2xiaohao508",
		      "youyong3918", "1xiaohao24", "1xiaohao970", "1xiaohao73", "1xiaohao966", "youyong3772", "1xiaohao428",
		      "gongxian134", "xiaohao531", "1xiaohao320", "1xiaohao293", "xiaohao816", "qmwu2015", "qmwu2016",
		      "gongxian8196", "xiaoke01", "xiaoke02" };
		Map<String, String> strs = new HashMap<String, String>();

		String[] accounts2 = { "ERIC09241", "ERIC09242", "ERIC09243", "ERIC09244", "ERIC09245", "ERIC09246",
		      "ericchen09", "ericchen10", "JOYCE1000", "joyce02", "joyce03", "joyce04", "joyce05", "joyce06", "joyce07",
		      "joyce08" };

		for (String str : accounts) {
			strs.put(str, str);
		}
		for (String str : accounts2) {
			strs.put(str, "20100924");
		}

		return strs;
	}

	public void doFirstInDay() {
		Map<String, String> accounts = getAccounts();

		try {
			for (Entry<String, String> entry : accounts.entrySet()) {
				doFirist(entry.getKey(), entry.getValue());
			}
			// processOtherXiaohao(accounts);
		} catch (Exception e) {
			m_logger.error(e.getMessage(), e);
		}
	}

	private void doFirist(String account, String password) {
		m_driver.go("login", "107", account, password);
		m_driver.go("gift", "vip"); // VIP工资
		m_driver.go("gift", "login"); // 连续登录
		m_driver.go("gift", "task"); // 任务
		m_driver.go("lottery", "lave"); // 每日抽奖
		m_driver.go("gift", "arena"); // 演武榜,押注
		m_driver.go("drink", "drink"); // 饮酒
		m_driver.go("map", "island", "10"); // 金银洞
		m_driver.go("trade", "business"); // 每日通商
		m_driver.go("activity", "sacredtree"); // 神树
		m_driver.go("activity", "springlottery"); // 幸运大转盘
		m_driver.go("activity", "acttreasure"); // 寻宝
		m_driver.go("city", "exercise"); // 征收
		m_driver.go("gift", "exercise"); // 整军
		m_driver.go("gift", "hitegg"); // 砸金蛋

		if (m_driver.getContext().getIntAttribute("member", "country", 0) > 0) {
			m_driver.go("gift", "country"); // 国库
			m_driver.go("country", "sacrifice"); // 祭祀
			m_driver.go("country", "dice"); // 国家骰子
			m_driver.go("country", "donate", "422500"); // 捐献
			m_driver.go("gift", "dice"); // 骰子
			m_driver.go("country", "expostulation"); // 谏言
			m_driver.go("banquet", "active"); // 国宴

			m_countryState.put(account, true);
		} else {
			m_countryState.put(account, false);
		}
		m_driver.reset();
	}

	private void processOtherXiaohao(Map<String, String> accounts) {
		try {
			String firstInAccount = null;
			for (Entry<String, Boolean> entry : m_countryState.entrySet()) {
				String key = entry.getKey();
				Boolean value = entry.getValue();

				if (value == true) {
					firstInAccount = key;
					break;
				}
			}
			exitCountry(firstInAccount, accounts.get(firstInAccount));

			Thread.sleep(100);

			int index = 0;

			for (Entry<String, Boolean> entry : m_countryState.entrySet()) {
				index++;
				try {
					String key = entry.getKey();
					Boolean value = entry.getValue();

					if (value == false) {
						xiaohaoJoinAndExit(key, accounts.get(key));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (index > 15) {
					break;
				}
			}
			addCountry(firstInAccount, accounts.get(firstInAccount));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addCountry(String account, String password) throws Exception {
		TaskDriver driver = m_driver;
		driver.go("login", "107", account, password);

		driver.go("country", "addCountry");

		driver.reset();

		Thread.sleep(300);

		driver.go("login", "107", "superwyx", "wyx1116");
		driver.go("country", "approve");
		driver.reset();
	}

	private void exitCountry(String account, String password) throws Exception {
		TaskDriver driver = m_driver;
		driver.go("login", "107", account, password);

		driver.go("country", "exitCountry");
		driver.reset();
	}

	private void xiaohaoJoinAndExit(String account, String password) throws Exception {
		addCountry(account, password);

		TaskDriver driver = m_driver;

		driver.go("login", "107", account, account);

		if (driver.getContext().getIntAttribute("member", "country", 0) > 0) {
			driver.go("gift", "country"); // 国库
			driver.go("country", "sacrifice"); // 祭祀
			driver.go("country", "donate", "222500"); // 捐献
		}
		driver.reset();

		exitCountry(account, password);
	}

	public void doClydeTask() {
		Map<String, String> accounts = getAccounts();

		try {
			for (Entry<String, String> entry : accounts.entrySet()) {
				doClydeJob(entry.getKey(), entry.getValue());
			}
			for (Entry<String, String> entry : accounts.entrySet()) {
				doClydeJob(entry.getKey(), entry.getValue());
			}
		} catch (Exception e) {
			m_logger.error(e.getMessage(), e);
		}
	}

	private void doClydeJob(String account, String password) {
		m_driver.go("login", "107", account, password);
		m_driver.go("city", "exercise"); // 征收
		m_driver.go("gift", "exercise"); // 整军
		m_driver.go("gift", "hitegg"); // 砸金蛋

		if (m_driver.getContext().getIntAttribute("member", "country", 0) > 0) {
			m_driver.go("country", "expostulation"); // 谏言
			m_driver.go("banquet", "active"); // 国宴

			if (isIdleTime()) {
				m_driver.go("trade", "oversea"); // 海外贸易
			}
		}
		m_driver.reset();
	}

	@Override
	public void enableLogging(Logger logger) {
		m_logger = logger;
	}

	protected boolean isIdleTime() {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);

		if (hour >= 4) {
			return true;
		} else {
			return false;
		}
	}

	public void bet(String arenaUid, String arenaServerId) {
		Map<String, String> accounts = getAccounts();

		try {
			for (Entry<String, String> entry : accounts.entrySet()) {
				m_driver.go("login", "107", entry.getKey(), entry.getValue());
				m_driver.getContext().setAttribute(ArenaActivity.ID, "uid", arenaUid);
				m_driver.getContext().setAttribute(ArenaActivity.ID, "server", arenaServerId);
				m_driver.go("arena", "bet");
			}
		} catch (Exception e) {
			m_logger.error(e.getMessage(), e);
		}
	}
}
