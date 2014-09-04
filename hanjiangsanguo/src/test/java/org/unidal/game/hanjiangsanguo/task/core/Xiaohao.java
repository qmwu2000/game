package org.unidal.game.hanjiangsanguo.task.core;

import org.junit.Test;
import org.unidal.game.hanjiangsanguo.task.TaskDriver;
import org.unidal.lookup.ComponentTestCase;

public class Xiaohao extends ComponentTestCase {
	static {
		System.setProperty("devMode", "true");
	}

	@Test
	public void all() throws Exception {
		String[] accounts = {  
				"1xiaohao320", "1xiaohao293", "xiaohao816", "2xiaohao362", "2xiaohao996",
		      "2xiaohao543", "2xiaohao583", "2xiaohao508", "youyong3918", "1xiaohao24", "1xiaohao970", "1xiaohao73",
		      "1xiaohao966", "1xiaohao428", "xiaohao531", "xiaoke01",
		      "youyong3772", "gongxian134", "gongxian8196", "qmwu2015", "qmwu2016", "xiaohao856" };

		TaskDriver driver = lookup(TaskDriver.class);

		for (String account : accounts) {
			driver.go("login", "107", account, account);
			driver.go("gift", "vip"); // VIP工资
			driver.go("gift", "login"); // 连续登录
			driver.go("gift", "hitegg"); // 砸金蛋
			driver.go("gift", "arena"); // 演武榜
			driver.go("gift", "task"); // 任务
			driver.go("lottery", "lave"); // 每日抽奖
			driver.go("trade", "business"); // 每日通商
			driver.go("map", "island", "10"); // 金银洞
			driver.go("city", "exercise"); // 征收
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
				driver.go("country", "donate", "222500"); // 捐献
				driver.go("gift", "dice"); // 骰子
			}

			driver.reset();

			System.out.println();
			System.out.println();
		}
	}
	
	@Test
	public void siege() throws Exception {
		String[] accounts = {  
				"1xiaohao320", "1xiaohao293", "xiaohao816", "2xiaohao362", "2xiaohao996",
				"2xiaohao543", "2xiaohao583", "2xiaohao508", "youyong3918", "1xiaohao24", "1xiaohao970", "1xiaohao73",
				"1xiaohao966", "1xiaohao428", "xiaohao531", "xiaoke01",
				"youyong3772", "gongxian134", "gongxian8196", "qmwu2015", "qmwu2016", "xiaohao856" };
		
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
	public void arena() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);

		driver.go("login", "107", "qmwu2016", "qmwu2016");
		driver.go("arena", "rank");
	}
}
