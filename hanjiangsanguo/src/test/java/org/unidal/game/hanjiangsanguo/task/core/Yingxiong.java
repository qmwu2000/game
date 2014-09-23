package org.unidal.game.hanjiangsanguo.task.core;

import org.junit.Test;
import org.unidal.game.hanjiangsanguo.task.TaskDriver;
import org.unidal.lookup.ComponentTestCase;

public class Yingxiong extends ComponentTestCase {

	static {
		System.setProperty("devMode", "true");
	}

	@Test
	public void oldAccount() {
		String[] oldAccounts = { "2xiaohao362", "2xiaohao996", "2xiaohao543", "2xiaohao508", "youyong3918", "1xiaohao24",
		      "1xiaohao970", "1xiaohao73", "youyong3772", "1xiaohao428", "gongxian134", "xiaohao531", "1xiaohao320",
		      "1xiaohao293", "gongxian8196", "xiaoke01", "xiaoke02", "1yaoqing73", "ERIC09245", "ericchen09",
		      "1yaoqing74", "1yaoqing75" };

		for (String str : oldAccounts) {
			TaskDriver driver = lookup(TaskDriver.class);

			driver.go("login", "107", str, str);
			driver.go("country", "addCountry");
		}
	}

	@Test
	public void testLevel() {
		StringBuilder sb = new StringBuilder();

		for (int i = 1; i < 100; i++) {
			TaskDriver driver = lookup(TaskDriver.class);
			String str = "7yaoqing" + Integer.toString(100 + i).substring(1);

			driver.go("login", "107", str, str);
			int level = driver.getContext().getIntAttribute("level", -1);

			if (level > 50) {
				// driver.go("country", "addCountry");
				sb.append("\"").append(str).append("\",").append(level).append("\n");
			}
		}
		System.out.println(sb.toString());
	}

	@Test
	public void addCountry() {
		String[] accounts = {"6yaoqing86","6yaoqing91","6yaoqing92","6yaoqing93"};
		StringBuilder sb = new StringBuilder();

		for (String str : accounts) {
			TaskDriver driver = lookup(TaskDriver.class);

			driver.go("login", "107", str, str);
			int level = driver.getContext().getIntAttribute("level", -1);
			String nickName  = driver.getContext().getAttribute("member","nickname");

			System.out.println(level+"====");
			if (level < 80) {
				 driver.go("country", "addCountry");
				//sb.append("\"").append(str).append(":").append(nickName).append("\",");
			}
		}
		System.out.println(sb.toString());
	}

	@Test
	public void donate() {
		String[] accounts = getAccount();

		for (String str : accounts) {
			TaskDriver driver = lookup(TaskDriver.class);

			driver.go("login", "107", str, str);
			driver.go("country", "donate2");
		}
	}
	
	@Test
	public void buy() {
		String[] accounts = getAccount();

		for (String str : accounts) {
			TaskDriver driver = lookup(TaskDriver.class);

			driver.go("login", "107", str, str);

			driver.go("country", "hero");

		}
	}

	@Test
	public void userHero() {
		String[] accounts = getAccount();

		for (String str : accounts) {
			TaskDriver driver = lookup(TaskDriver.class);

			driver.go("login", "107", str, str);
			driver.go("country", "useHero");

		}
	}

	@Test
	public void testAtCountry() throws Exception {
		StringBuilder sb = new StringBuilder();
		for (String account : getAccount()) {
			TaskDriver driver = lookup(TaskDriver.class);
			driver.go("login", "107", account, account);

			if (driver.getContext().getIntAttribute("member", "country", 0) == 2) {
				sb.append("\"").append(account).append("\",");
			}
		}
		System.out.println(sb.toString());
	}

	private String[] getAccount() {
//		String[] accounts = { "6yaoqing02", "6yaoqing03", "6yaoqing04", "6yaoqing05", "6yaoqing13", "6yaoqing14",
//		      "6yaoqing15", "6yaoqing16", "6yaoqing17", "6yaoqing25", "6yaoqing26", "6yaoqing27", "6yaoqing28",
//		      "6yaoqing29", "6yaoqing30", "6yaoqing35", "6yaoqing36", "6yaoqing37", "6yaoqing39", "6yaoqing40",
//		      "6yaoqing41", "6yaoqing42", "6yaoqing43", "6yaoqing44", "6yaoqing45", "6yaoqing46", "6yaoqing47",
//		      "6yaoqing48", "6yaoqing49", "6yaoqing50", "6yaoqing52", "6yaoqing53", "6yaoqing54", "6yaoqing55",
//		      "6yaoqing56", "6yaoqing60", "6yaoqing61", "6yaoqing62", "6yaoqing63", "6yaoqing64", "6yaoqing65",
//		      "6yaoqing72", "6yaoqing73", "6yaoqing74", "6yaoqing75", "6yaoqing76", "6yaoqing77", "6yaoqing80",
//		      "6yaoqing81", "6yaoqing82", "6yaoqing83", "6yaoqing84", "6yaoqing85", "6yaoqing86", "6yaoqing87",
//		      "6yaoqing88", "6yaoqing89", "6yaoqing90", "6yaoqing91", "6yaoqing92", "6yaoqing93", "6yaoqing94",
//		      "6yaoqing98", "6yaoqing99" };
//		

		String[] accounts = { "6yaoqing74", "6yaoqing75", "6yaoqing76", "6yaoqing77", "6yaoqing80", "6yaoqing81",
		      "6yaoqing82", "6yaoqing83", "6yaoqing84", "6yaoqing85", "6yaoqing86", "6yaoqing87", "6yaoqing88",
		      "6yaoqing89", "6yaoqing90", "6yaoqing91", "6yaoqing92", "6yaoqing93", "6yaoqing94", "6yaoqing98",
		      "6yaoqing99" };
		
		//String [] accounts={"6yaoqing74","6yaoqing87","6yaoqing88","6yaoqing89","6yaoqing90"};

		return accounts;
	}

}
