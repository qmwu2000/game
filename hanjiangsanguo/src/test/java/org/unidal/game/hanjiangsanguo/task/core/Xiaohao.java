package org.unidal.game.hanjiangsanguo.task.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.junit.Test;
import org.unidal.game.hanjiangsanguo.task.TaskDriver;
import org.unidal.helper.Files;
import org.unidal.lookup.ComponentTestCase;

public class Xiaohao extends ComponentTestCase {
	static {
		System.setProperty("devMode", "true");
	}

	@Test
	public void all() throws Exception {
		String[] accounts = { "xiaohao856", "2xiaohao362", "2xiaohao996", "2xiaohao543", "2xiaohao583", "2xiaohao508",
		      "youyong3918", "1xiaohao24", "1xiaohao970", "1xiaohao73", "1xiaohao966", "youyong3772", "1xiaohao428",
		      "gongxian134", "xiaohao531", "1xiaohao320", "1xiaohao293", "xiaohao816", "qmwu2015", "qmwu2016",
		      "gongxian8196", "xiaoke01", "xiaoke02" };

		System.out.println(accounts.length);

		TaskDriver driver = lookup(TaskDriver.class);

		action(accounts, driver);
		action(accounts, driver);
	}

	public Date getCurrentDay() {
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}

	private boolean getBanquet() throws FileNotFoundException, IOException {
		File file = new File("target/index.position");

		if (file != null) {
			file.createNewFile();
		}
		Properties pro = new Properties();
		pro.load(new FileInputStream(file));
		Date date = getCurrentDay();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String key = sdf.format(date);

		if (pro.get(key) == null) {
			String content = Files.forIO().readFrom(file, "utf-8");
			Files.forIO().writeTo(file, content + "\n" + key + "=true");
			return true;
		} else {
			return false;
		}
	}

	private void action(String[] accounts, TaskDriver driver) throws Exception {
		boolean first = getBanquet();

		for (String account : accounts) {
			driver.go("login", "107", account, account);

			if (first) {
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
			}
			
			driver.getContext().setAttribute("dahao", "dahao");
			driver.getContext().setAttribute("maxMineGold", "0");
			driver.go("mine", "");

			if (driver.getContext().getIntAttribute("member", "country", 0) > 0) {
				if (first) {
					driver.go("gift", "country"); // 国库
					driver.go("country", "sacrifice"); // 祭祀
					driver.go("country", "expostulation"); // 谏言
					driver.go("country", "dice"); // 国家骰子
					driver.go("country", "donate", "222500"); // 捐献
					driver.go("gift", "dice"); // 骰子
				}
				driver.go("trade", "oversea"); // 海外贸易
				driver.go("banquet", "active"); // 国宴
			}

			driver.reset();

			System.out.println();
			System.out.println();
		}
	}
}
