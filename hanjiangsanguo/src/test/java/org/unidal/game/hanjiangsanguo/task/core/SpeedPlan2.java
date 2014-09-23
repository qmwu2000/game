package org.unidal.game.hanjiangsanguo.task.core;

import org.junit.Test;
import org.unidal.game.hanjiangsanguo.task.TaskDriver;
import org.unidal.game.hanjiangsanguo.task.activity.CountryActivity;
import org.unidal.lookup.ComponentTestCase;

public class SpeedPlan2 extends ComponentTestCase {
	static {
		System.setProperty("devMode", "true");
	}

	private String register(int index) throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		String name = "7yaoqing" + Integer.toString(100 + index).substring(1);

		driver.go("register", "simple", "107", name);
		driver.reset();
		index++;

		return name;
	}

	@Test
	public void addCountry() throws Exception {
		StringBuilder sb = new StringBuilder();

		for (int i = 40; i <= 100; i++) {
			TaskDriver driver = lookup(TaskDriver.class);
			String account = "5yaoqing" + Integer.toString(100 + i).substring(1);
			driver.go("login", "107", account, account);

			int level = driver.getContext().getIntAttribute("level", -1);
			int country = driver.getContext().getIntAttribute("member", "country", 0);

			if (level >= 50) {
				if (country > 0) {

				} else {
					driver.go(CountryActivity.ID, "addCountry");
				}
			}
		}
		System.out.println(sb.toString());
	}

	@Test
	public void invitation() throws Exception {
		for (int i = 1; i < 100; i++) {
			String name = register(i);
			TaskDriver driver = lookup(TaskDriver.class);
			try {
				driver.go("login", "107", name, name);
				driver.go("gift", "code");
				levelBefore30(driver);
				levelAfter30(driver);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// driver.go("gift", "invitation", "erpqgq7");
			}
		}
	}

	@Test
	public void testLevel() throws Exception {
		for (int i = 20; i <= 50; i++) {
			TaskDriver driver = lookup(TaskDriver.class);
			String account = "6yaoqing" + Integer.toString(100 + i).substring(1);
			driver.go("login", "107", account, account);

			int level = driver.getContext().getIntAttribute("level", -1);
			System.err.println(account + " " + level);
		}
	}

	@Test
	public void leaveCountry() throws Exception {
		String[] accounts = { "2xiaohao362", "2xiaohao996", "2xiaohao543", "2xiaohao508", "youyong3918", "1xiaohao24",
		      "1xiaohao970", "1xiaohao73", "youyong3772", "1xiaohao428", "gongxian134", "xiaohao531", "1xiaohao320",
		      "1xiaohao293", "gongxian8196", "xiaoke01", "xiaoke02", "1yaoqing73", "ERIC09245", "ericchen09",
		      "1yaoqing74", "1yaoqing75" };
		
		for (String str : accounts) {
			TaskDriver driver = lookup(TaskDriver.class);

			driver.go("login", "107", str, str);
			driver.go("country", "exitCountry");
		}
	}

	@Test
	public void speedOne() throws Exception {
		String name = "eric09246";
		TaskDriver driver = lookup(TaskDriver.class);
		try {
			driver.go("login", "108", name, "20100924");
			driver.go("gift", "code");
			levelBefore30(driver);
			// levelAfter30(driver);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}
	

	@Test
	public void invitationAll() throws Exception {
		String[] accounts = getAllAccounts();
		
		for (String account : accounts) {
			map(account);
		}
	}

	@Test
	public void donateAll() {
		String[] accounts = getAllAccounts();

		for (String account : accounts) {
			TaskDriver driver = lookup(TaskDriver.class);

			driver.go("login", "107", account, account);
			int gold = driver.getContext().getIntAttribute("gold", 0);

			if (gold > 2000) {
				System.err.println(account + "加入国家");

				driver.go("country", "addCountry");

				TaskDriver driver2 = lookup(TaskDriver.class);

				driver2.go("login", "107", "superwyx", "wyx1116");
				driver.go("country", "approve");

				TaskDriver driver3 = lookup(TaskDriver.class);

				driver3.go("login", "107", account, account);
				driver3.go("country", "donateAll");
				driver3.go("country", "exitCountry");
			}
		}
	}

	private String[] getAllAccounts() {
//	   String[] accounts = { "6yaoqing02", "6yaoqing03", "6yaoqing04", "6yaoqing05", "6yaoqing13", "6yaoqing14",
//		      "6yaoqing15", "6yaoqing16", "6yaoqing17", "6yaoqing25", "6yaoqing26", "6yaoqing27", "6yaoqing28",
//		      "6yaoqing29", "6yaoqing30", "6yaoqing35", "6yaoqing36", "6yaoqing37", "6yaoqing39", "6yaoqing40",
//		      "6yaoqing41", "6yaoqing42", "6yaoqing43", "6yaoqing44", "6yaoqing45", "6yaoqing46", "6yaoqing47",
//		      "6yaoqing48", "6yaoqing49", "6yaoqing50", "6yaoqing52", "6yaoqing53", "6yaoqing54", "6yaoqing55",
//		      "6yaoqing56", "6yaoqing60", "6yaoqing61", "6yaoqing62", "6yaoqing63", "6yaoqing64", "6yaoqing65",
//		      "6yaoqing72", "6yaoqing73", "6yaoqing74", "6yaoqing75", "6yaoqing76", "6yaoqing77", "6yaoqing80",
//		      "6yaoqing81", "6yaoqing82", "6yaoqing83", "6yaoqing84", "6yaoqing85", "6yaoqing86", "6yaoqing87",
//		      "6yaoqing88", "6yaoqing89", "6yaoqing90", "6yaoqing91", "6yaoqing92", "6yaoqing93", "6yaoqing94",
//		      "6yaoqing98", "6yaoqing99" };
	   
		String [] accounts={"6yaoqing74","6yaoqing87","6yaoqing88","6yaoqing89","6yaoqing90"};

	   return accounts;
   }

	@Test
	public void testAll() throws Exception {
		String[] accounts = {  "6yaoqing74", "6yaoqing75", "6yaoqing76", "6yaoqing77", "6yaoqing80",
		      "6yaoqing81", "6yaoqing82", "6yaoqing83", "6yaoqing84", "6yaoqing85", "6yaoqing86", "6yaoqing87",
		      "6yaoqing88", "6yaoqing89", "6yaoqing90", "6yaoqing91", "6yaoqing92", "6yaoqing93", "6yaoqing94",
		      "6yaoqing98", "6yaoqing99" };
		
//		String [] accounts={"6yaoqing74","6yaoqing87","6yaoqing88","6yaoqing89","6yaoqing90"};

		for (String account : accounts) {
			TaskDriver driver = lookup(TaskDriver.class);

			driver.go("login", "107", account, account);

			int level = driver.getContext().getIntAttribute("level", -1);
			if (level < 80) {
				System.out.println(account);
				mapAfter50(account);
			}else{
				System.out.println();
			}
		}
	}

	public void mapAfter50(String account) throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);

		driver.go("login", "107", account, account);

		driver.go("country", "addCountry");

		TaskDriver driver2 = lookup(TaskDriver.class);

		driver2.go("login", "107", "superwyx", "wyx1116");
		driver.go("country", "approve");

		TaskDriver driver3 = lookup(TaskDriver.class);
		driver3.go("login", "107", account, account);
		driver3.go("country", "donate2");

		Thread.sleep(60 * 2 * 1000);

		driver3.go("country", "hero");
		driver3.go("country", "useHero");

		map(account);
	}

	public void map(String name) throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		try {
			driver.go("login", "107", name, name);

			driver.go("map", "action", "3", "2", "1", "10");
			driver.go("map", "action", "3", "3", "1", "10");
			driver.go("map", "action", "4", "1", "1", "10");
			driver.go("map", "action", "4", "2", "1", "10");
			driver.go("map", "action", "4", "3", "1", "10");
			driver.go("map", "action", "4", "4", "1", "10");
			driver.go("map", "action", "4", "5", "1", "10");
			driver.go("map", "action", "4", "6", "1", "10");
			driver.go("map", "action", "5", "1", "1", "10");
			driver.go("map", "action", "5", "2", "1", "10");
			driver.go("map", "action", "5", "3", "1", "10");
			driver.go("map", "action", "5", "4", "1", "10");
			driver.go("map", "action", "5", "6", "1", "10");
			driver.go("map", "action", "6", "1", "1", "10");
			driver.go("map", "action", "6", "2", "1", "10");
			driver.go("map", "action", "6", "3", "1", "10");
			driver.go("map", "action", "6", "4", "1", "10");
			driver.go("map", "action", "7", "1", "1", "10");
			driver.go("map", "action", "7", "2", "1", "10");
			driver.go("map", "action", "7", "3", "1", "10");
			driver.go("map", "action", "7", "4", "1", "10");

			driver.go("gift", "jingsu");
			driver.go("gift", "task");
			driver.go("gift", "level", "55");
			driver.go("gift", "level", "60");
			driver.go("gift", "level", "65");
			driver.go("gift", "level", "70");
			driver.go("gift", "level", "80");
			driver.go("gift", "level", "90");

			driver.go("country", "invitation", "2n55ssi");
			driver.go("city", "exerciseAll");
			driver.go("country", "donateAll");
			driver.go("country", "exitCountry");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	@Test
	public void peiyang() throws Exception {
		String name = "6yaoqing93";
		TaskDriver driver = lookup(TaskDriver.class);
		try {
			driver.go("login", "107", name, name);
//			driver.go("cultivate", "gold", "", "蔡文姬", "300");
			
//			driver.go("map", "action", "3", "3", "1", "10");
//			driver.go("map", "action", "4", "1", "1", "10");
//			driver.go("map", "action", "4", "2", "1", "10");
//			driver.go("map", "action", "4", "3", "1", "10");
//			driver.go("map", "action", "4", "4", "1", "10");
//			driver.go("map", "action", "4", "5", "1", "10");
//			driver.go("map", "action", "4", "6", "4", "10");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}

	}

	private void levelBefore30(TaskDriver driver) throws Exception {
		driver.go("muster", "on", "廖化");
		driver.go("matrix", "update", "1", "廖化");

		driver.go("map", "action", "1", "1", "1", "10");
		driver.go("practice", "train", "廖化");
		driver.go("map", "reward", "10");
		driver.go("muster", "on", "周仓");

		driver.go("matrix", "use", "2", "廖化", "周仓");
		driver.go("matrix", "levelup", "2", "2");
		driver.go("map", "action", "1", "2", "1", "9");
		driver.go("general", "equip", "廖化", "劈山刀", "犀牛宝甲", "苍狼披风");
		driver.go("strengthen", "equip", "廖化", "1", "10");
		driver.go("strengthen", "equip", "廖化", "3", "10");
		driver.go("strengthen", "equip", "廖化", "4", "10");
		driver.go("map", "action", "1", "2", "10", "10");
		driver.go("gift", "level", "16");

		driver.go("map", "reward", "20");
		driver.go("muster", "on", "张梁");
		driver.go("matrix", "levelup", "2");
		driver.go("matrix", "update", "2", "张梁", "廖化", "周仓");
		driver.go("matrix", "use", "2", "张梁", "廖化", "周仓");
		driver.go("map", "action", "1", "3", "1", "10");
		driver.go("map", "reward", "30");
		driver.go("muster", "on", "张宝");
		driver.go("matrix", "levelup", "2");
		driver.go("matrix", "update", "2", "廖化", "张梁", "周仓");
		driver.go("matrix", "use", "2", "廖化", "张梁", "周仓");
		driver.go("map", "action", "1", "4", "1", "10");

		driver.go("strengthen", "equip", "廖化", "1", "15");
		driver.go("strengthen", "equip", "廖化", "3", "15");
		driver.go("strengthen", "equip", "廖化", "4", "15");
		driver.go("matrix", "levelup", "2");
		driver.go("matrix", "levelup", "2");
		driver.go("muster", "on", "张宝");
		driver.go("muster", "on", "张角");
		driver.go("matrix", "update", "2", "张宝", "张角", "廖化", "张梁", "周仓");
		driver.go("matrix", "use", "2", "张宝", "张角", "廖化", "张梁", "周仓");
		driver.go("map", "action", "2", "1", "1", "10");
		driver.go("gift", "level", "30");
	}

	private void levelAfter30(TaskDriver driver) throws Exception {
		driver.go("gift", "get_mission_reward", "40");
		driver.go("gift", "get_mission_reward", "50");
		driver.go("muster", "on", "蔡文姬");
		driver.go("muster", "on", "张梁");
		driver.go("muster", "on", "张宝");
		driver.go("practice", "train", "蔡文姬");
		driver.go("gift", "pass", "1", "4"); // 过关奖励

		driver.go("practice", "goleap", "蔡文姬", "30"); // 没有突飞 之前需要领取奖励
		driver.go("matrix", "levelup", "3");
		driver.go("matrix", "levelup", "3");
		driver.go("matrix", "levelup", "3");
		driver.go("matrix", "levelup", "3");
		driver.go("matrix", "levelup", "3");
		driver.go("matrix", "levelup", "3");
		driver.go("matrix", "levelup", "3");
		driver.go("matrix", "levelup", "3");

		driver.go("matrix", "update", "3", "张宝", "张梁", "蔡文姬", "廖化", "张角");
		driver.go("matrix", "use", "3", "张宝", "张梁", "蔡文姬", "廖化", "张角");
		driver.go("general", "equip", "蔡文姬", "隐者之扇", "玄黄藤甲", "鱼鳞披风");

		driver.go("strengthen", "equip", "蔡文姬", "2", "25");
		driver.go("strengthen", "equip", "蔡文姬", "3", "25");
		driver.go("strengthen", "equip", "蔡文姬", "4", "25");

		driver.go("map", "action", "2", "2", "1", "10");

		driver.go("strengthen", "equip", "蔡文姬", "2", "10");
		driver.go("strengthen", "equip", "蔡文姬", "3", "10");
		driver.go("strengthen", "equip", "蔡文姬", "4", "10");
		driver.go("matrix", "levelup", "3");
		driver.go("matrix", "levelup", "3");
		driver.go("matrix", "update", "3", "张宝", "张梁", "蔡文姬", "廖化", "张角");

		driver.go("gift", "level", "35");
		driver.go("gift", "pass", "5", "6"); // 过关奖励
		driver.go("practice", "goleap", "蔡文姬", "30");
		driver.go("map", "action", "2", "3", "1", "9");
		driver.go("gift", "level", "40");
		driver.go("matrix", "levelup", "3");
		driver.go("cultivate", "gold", "", "蔡文姬", "20");
		driver.go("map", "action", "2", "3", "10", "10");
		driver.go("matrix", "levelup", "3");
		driver.go("matrix", "levelup", "3");
		driver.go("practice", "goleap", "蔡文姬", "10");
		driver.go("practice", "goleap", "蔡文姬", "10");
		driver.go("map", "action", "2", "4", "1", "10");

		driver.go("gift", "level", "45");
		driver.go("matrix", "levelup", "3");
		driver.go("cultivate", "gold", "", "蔡文姬", "20");
		driver.go("practice", "goleap", "蔡文姬", "20");
		driver.go("strengthen", "equip", "蔡文姬", "2", "10");
		driver.go("strengthen", "equip", "蔡文姬", "3", "10");
		driver.go("strengthen", "equip", "蔡文姬", "4", "10");
		driver.go("map", "action", "3", "1", "1", "10");
		driver.go("gift", "level", "30");
		driver.go("gift", "level", "50");
		driver.go("gift", "task");
		driver.go("gift", "jingsu");
		driver.go("map", "action", "3", "2", "1", "10");
	}
	
	public static void main(String args[]){
		System.out.println("\u4f20\u9012\u53c2\u6570\u5931\u8d25");
	}
	
}
