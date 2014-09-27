package org.unidal.game.hanjiangsanguo.task.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.unidal.game.hanjiangsanguo.task.TaskDriver;
import org.unidal.lookup.ComponentTestCase;

public class Xiaohao extends ComponentTestCase {
	private static List<String> accounts = new ArrayList<String>(Arrays.asList("1xiaohao320", "1xiaohao293",
	      "xiaohao816", "2xiaohao362", "2xiaohao996", "2xiaohao543", "2xiaohao583", "2xiaohao508", "youyong3918",
	      "1xiaohao24", "1xiaohao970", "1xiaohao966", "1xiaohao428", "xiaohao531", "xiaoke01", "1xiaohao73",
	      "youyong3772", "gongxian134", "gongxian8196", "qmwu2015", "qmwu2016", "xiaohao856"));

	private static List<String> allAccounts = new ArrayList<String>();

	static {
		System.setProperty("devMode", "true");

		allAccounts.addAll(accounts);

		for (int i = 1; i <= 28; i++) {
			allAccounts.add(String.format("%syaoqing%02d", 1, i));
		}

		for (int i = 1; i <= 9; i++) {
			allAccounts.add(String.format("%syaoqing%02d", 2, i));
		}

		for (int i = 1; i <= 28; i++) {
			allAccounts.add(String.format("%syaoqing%02d", 4, i));
		}

		for (int i = 1; i <= 18; i++) {
			allAccounts.add(String.format("%syaoqing%02d", 5, i));
		}

		for (int i = 1; i <= 6; i++) {
			allAccounts.add(String.format("%syaoqing%02d", 6, i));
		}
	}

	@Test
	public void all() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);

		for (String account : allAccounts) {
			driver.go("login", "107", account, account);
			driver.go("gift", "vip"); // VIP工资
			driver.go("gift", "login"); // 连续登录
			driver.go("gift", "hitegg"); // 砸金蛋
			driver.go("gift", "arena"); // 演武榜
			driver.go("gift", "task"); // 任务
			driver.go("lottery", "lave"); // 每日抽奖
			driver.go("trade", "business"); // 每日通商
			driver.go("map", "island", "10"); // 金银洞
			driver.go("city", "exercise", "0"); // 征收
			driver.go("gift", "exercise"); // 整军

			driver.go("activity", "sacredtree"); // 神树
			driver.go("activity", "springlottery"); // 幸运大转盘
			driver.go("activity", "acttreasure"); // 寻宝

			if (driver.getContext().getIntAttribute("member", "country", 0) > 0) {
				driver.go("gift", "country"); // 国库
				driver.go("trade", "oversea"); // 海外贸易
				driver.go("country", "sacrifice"); // 祭祀
				driver.go("country", "expostulation"); // 谏言
				driver.go("banquet", "active"); // 宴会
				driver.go("country", "dice"); // 国家骰子
//				driver.go("country", "donate", "222500"); // 捐献
				driver.go("gift", "dice"); // 骰子
			}

			driver.reset();

			System.out.println();
			System.out.println();
		}
	}

	@Test
	public void summary() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);

		for (String account : accounts) {
			driver.go("login", "107", account, account);

			driver.reset();

			System.out.println();
			System.out.println();
		}
	}

	@Test
	public void siege() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);

		for (String account : accounts) {
			driver.go("login", "107", account, account);

			if (driver.getContext().getIntAttribute("member", "country", 0) > 0) {
				driver.go("country", "siege"); // 国战
			}

			driver.reset();

			System.out.println();
			System.out.println();
		}
	}

	@Test
	public void tree() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);

		for (String account : accounts) {
			driver.go("login", "107", account, account);

//			driver.go("tree"); // 神树
			 driver.go("gift", "loginReward"); // 登陆返礼
			driver.reset();

			System.out.println();
			System.out.println();
		}
	}
}
