package org.unidal.game.hanjiangsanguo.account;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.plexus.logging.LogEnabled;
import org.codehaus.plexus.logging.Logger;
import org.unidal.game.hanjiangsanguo.task.TaskDriver;
import org.unidal.game.hanjiangsanguo.task.activity.ArenaActivity;
import org.unidal.game.hanjiangsanguo.task.activity.BanquetXiaohaoActivity;
import org.unidal.game.hanjiangsanguo.task.activity.CountryActivity;
import org.unidal.game.hanjiangsanguo.task.activity.MineActivity;
import org.unidal.lookup.annotation.Inject;

public class XiaoHaoAccount implements LogEnabled {

	@Inject
	protected TaskDriver m_driver;

	private Logger m_logger;

	public Map<String, String> getAccounts() {
		String[] accounts = { "xiaohao856", "2xiaohao362", "2xiaohao996", "2xiaohao543", "2xiaohao583", "2xiaohao508",
		      "youyong3918", "1xiaohao24", "1xiaohao970", "1xiaohao73", "1xiaohao966", "youyong3772", "1xiaohao428",
		      "gongxian134", "xiaohao531", "1xiaohao320", "1xiaohao293", "xiaohao816", "qmwu2015", "qmwu2016",
		      "gongxian8196", "xiaoke01", "xiaoke02", "1yaoqing73" };

		Map<String, String> strs = new LinkedHashMap<String, String>();

		String[] accounts2 = { "ERIC09241", "ERIC09242", "ERIC09243", "ERIC09245", "ericchen09", "ericchen10",
		      "JOYCE100", "JOYCE1000", "joyce01", "joyce02", "joyce03", "joyce04", "joyce05", "joyce06", "joyce07",
		      "joyce08" };

		for (String str : accounts) {
			strs.put(str, str);
		}
		for (String str : accounts2) {
			strs.put(str, "20100924");
		}

		for (int i = 11; i <= 15; i++) {
			strs.put("1yaoqing" + i, "1yaoqing" + i);
		}
		for (int i = 21; i <= 29; i++) {
			strs.put("1yaoqing" + i, "1yaoqing" + i);
		}
		for (int i = 35; i <= 39; i++) {
			strs.put("1yaoqing" + i, "1yaoqing" + i);
		}
		for (int i = 73; i <= 76; i++) {
			strs.put("1yaoqing" + i, "1yaoqing" + i);
		}
		strs.put("1yaoqing78", "1yaoqing78");

		for (int i = 1; i < 100; i++) {
			String account = "4yaoqing" + Integer.toString(100 + i).substring(1);

			strs.put(account, account);
		}
		for (int i = 1; i < 40; i++) {
			String account = "5yaoqing" + Integer.toString(100 + i).substring(1);

			strs.put(account, account);
		}
		return strs;
	}

	public void doFirstInDay() {
		Map<String, String> accounts = getAccounts();

		try {
			for (Entry<String, String> entry : accounts.entrySet()) {
				doFirist(entry.getKey(), entry.getValue());
			}
		} catch (Exception e) {
			m_logger.error(e.getMessage(), e);
		}
	}

	public void doBeauty() {
		Map<String, String> accounts = getAccounts();

		try {
			for (Entry<String, String> entry : accounts.entrySet()) {
				m_driver.go("login", "107", entry.getKey(), entry.getValue());
				m_driver.go("beauty", "beauty"); // 铜雀台
			}
		} catch (Exception e) {
			m_logger.error(e.getMessage(), e);
		}
	}

	public void addCountrys() {
		Map<String, String> accounts = getAccounts();

		try {
			for (Entry<String, String> entry : accounts.entrySet()) {
				m_driver.go("login", "107", entry.getKey(), entry.getValue());
				if (m_driver.getContext().getIntAttribute("member", "country", 0) == 2) {

				} else {
					m_driver.go(CountryActivity.ID, "addCountry");
				}

			}
		} catch (Exception e) {
			m_logger.error(e.getMessage(), e);
		}
	}

	public void doFirist(String account, String password) {
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
		m_driver.go("beauty", "beauty"); // 铜雀台

		// 大号国家
		if (m_driver.getContext().getIntAttribute("member", "country", 0) == 2) {
			m_driver.go("gift", "country"); // 国库
			m_driver.go("country", "sacrifice"); // 祭祀
			m_driver.go("country", "dice"); // 国家骰子
			m_driver.go("country", "donate", "422500"); // 捐献
			m_driver.go("gift", "dice"); // 骰子
			m_driver.go("country", "expostulation"); // 谏言
			m_driver.go("banquet", "active"); // 国宴

		} else {
			m_driver.go("gift", "country"); // 国库
			m_driver.go("country", "sacrifice"); // 祭祀
			m_driver.go("country", "donate2"); // 捐献
		}
		m_driver.reset();
	}

	public void doClydeTask() {
		Map<String, String> accounts = getAccounts();
		StringBuilder sb = new StringBuilder();
		try {
			for (Entry<String, String> entry : accounts.entrySet()) {
				doClydeJob(entry.getKey(), entry.getValue(), sb);
			}
		} catch (Exception e) {
			m_logger.error(e.getMessage(), e);
		}
		System.out.println(sb.toString());
	}

	private void doClydeJob(String account, String password, StringBuilder sb) {
		m_driver.go("login", "107", account, password);
		m_driver.go("gift", "hitegg"); // 砸金蛋

		if (m_driver.getContext().getIntAttribute("member", "country", 0) == 2) {
			m_driver.go("country", "expostulation"); // 谏言
			m_driver.go("banquet", "active"); // 国宴
			m_driver.getContext().setAttribute(MineActivity.ID, "maxMineGold", "150000");
			m_driver.go(MineActivity.ID, "active");

			sb.append("\"").append(account).append("").append(m_driver.getContext().getAttribute("member", "nickname")).append("\",");

			if (isIdleTime()) {
				m_driver.go("trade", "oversea"); // 海外贸易
			}
		} else {
			if (isIdleTime()) {
				m_driver.go("trade", "oversea"); // 海外贸易
			}
			m_driver.go(BanquetXiaohaoActivity.ID, "active"); // 国宴
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
