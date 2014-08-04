package org.unidal.game.hanjiangsanguo.task.core;

import java.io.File;

import org.junit.Test;
import org.unidal.game.hanjiangsanguo.task.TaskDriver;
import org.unidal.game.hanjiangsanguo.task.activity.ZuqiuActivity;
import org.unidal.helper.Files;
import org.unidal.lookup.ComponentTestCase;

public class ShenJiang extends ComponentTestCase {

	private String register(int index) throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		String name = "1ziuqiu" + Integer.toString(index);

		driver.go("register", "simple", "107", name);
		driver.go("act_kemari", "index");

		int type = driver.getContext().getIntAttribute(ZuqiuActivity.ID, "shenjiang", 0);

		if (type == 26) {
			try {
				File file = new File("/tmp/shenjiang.txt");

				if (!file.exists()) {
					file.createNewFile();
				}
				String content = Files.forIO().readFrom(file, "utf8");

				Files.forIO().writeTo(file, content + ";" + name);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		driver.reset();
		return name;
	}

	@Test
	public void invitation() throws Exception {
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			try {
	         register(i);
         } catch (Exception e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
         }
		}
	}
	//
	// @Test
	// public void speedOne() throws Exception{
	// String name ="jackyc2";
	// TaskDriver driver = lookup(TaskDriver.class);
	// try {
	// driver.go("login", "107", name, "20100924");
	// driver.go("gift", "code");
	// levelBefore30(driver);
	// levelAfter30(driver);
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	// driver.go("gift", "invitation", "erpqgq7");
	// }
	// }
	//
	// private void levelBefore30(TaskDriver driver) throws Exception {
	// driver.go("muster", "on", "廖化");
	// driver.go("matrix", "update", "1", "廖化");
	//
	// driver.go("map", "action", "1", "1", "1", "10");
	// driver.go("practice", "train", "廖化");
	// driver.go("map", "reward", "10");
	// driver.go("muster", "on", "周仓");
	//
	// driver.go("matrix", "use", "2", "廖化", "周仓");
	// driver.go("matrix", "levelup", "2", "2");
	// driver.go("map", "action", "1", "2", "1", "9");
	// driver.go("general", "equip", "廖化", "劈山刀", "犀牛宝甲", "苍狼披风");
	// driver.go("strengthen", "equip", "廖化", "1", "10");
	// driver.go("strengthen", "equip", "廖化", "3", "10");
	// driver.go("strengthen", "equip", "廖化", "4", "10");
	// driver.go("map", "action", "1", "2", "10", "10");
	// driver.go("gift", "level", "16");
	//
	// driver.go("map", "reward", "20");
	// driver.go("muster", "on", "张梁");
	// driver.go("matrix", "levelup", "2");
	// driver.go("matrix", "update", "2", "张梁", "廖化", "周仓");
	// driver.go("matrix", "use", "2", "张梁", "廖化", "周仓");
	// driver.go("map", "action", "1", "3", "1", "10");
	// driver.go("map", "reward", "30");
	// driver.go("muster", "on", "张宝");
	// driver.go("matrix", "levelup", "2");
	// driver.go("matrix", "update", "2", "廖化", "张梁", "周仓");
	// driver.go("matrix", "use", "2", "廖化", "张梁", "周仓");
	// driver.go("map", "action", "1", "4", "1", "10");
	//
	// driver.go("strengthen", "equip", "廖化", "1", "15");
	// driver.go("strengthen", "equip", "廖化", "3", "15");
	// driver.go("strengthen", "equip", "廖化", "4", "15");
	// driver.go("matrix", "levelup", "2");
	// driver.go("matrix", "levelup", "2");
	// driver.go("muster", "on", "张宝");
	// driver.go("muster", "on", "张角");
	// driver.go("matrix", "update", "2", "张宝", "张角", "廖化", "张梁", "周仓");
	// driver.go("matrix", "use", "2", "张宝", "张角", "廖化", "张梁", "周仓");
	// driver.go("map", "action", "2", "1", "1", "10");
	// driver.go("gift", "level", "30");
	// }
	//
	// private void levelAfter30(TaskDriver driver) throws Exception {
	// driver.go("gift", "get_mission_reward", "40");
	// driver.go("gift", "get_mission_reward", "50");
	// driver.go("muster", "on", "蔡文姬");
	// driver.go("muster", "on", "张梁");
	// driver.go("muster", "on", "张宝");
	// driver.go("practice", "train", "蔡文姬");
	// driver.go("practice", "goleap", "蔡文姬", "40");
	// driver.go("matrix", "levelup", "3");
	// driver.go("matrix", "levelup", "3");
	// driver.go("matrix", "levelup", "3");
	// driver.go("matrix", "levelup", "3");
	// driver.go("matrix", "levelup", "3");
	// driver.go("matrix", "levelup", "3");
	//
	// driver.go("matrix", "update", "3", "张宝", "张梁", "蔡文姬", "廖化", "周仓");
	// driver.go("matrix", "use", "3", "张宝", "张梁", "蔡文姬", "廖化", "周仓");
	// driver.go("general", "equip", "蔡文姬", "隐者之扇", "玄黄藤甲", "鱼鳞披风");
	//
	// driver.go("strengthen", "equip", "蔡文姬", "2", "30");
	// driver.go("strengthen", "equip", "蔡文姬", "3", "30");
	// driver.go("strengthen", "equip", "蔡文姬", "4", "30");
	// //driver.go("cultivate", "gold","" , "蔡文姬", "20");
	// driver.go("map", "action", "2", "1", "1", "10");
	// driver.go("map", "action", "2", "2", "1", "10");
	// driver.go("map", "action", "2", "3", "1", "10");
	// driver.go("gift", "level", "35");
	// driver.go("gift", "level", "40");
	//
	// driver.go("matrix", "levelup", "3");
	// driver.go("matrix", "levelup", "3");
	// driver.go("practice", "goleap", "蔡文姬", "10");
	// driver.go("practice", "goleap", "蔡文姬", "10");
	// driver.go("map", "action", "2", "4", "1", "10");
	// driver.go("gift", "level", "45");
	// driver.go("matrix", "levelup", "3");
	// driver.go("cultivate", "gold", "" ,"蔡文姬", "20");
	// driver.go("practice", "goleap", "蔡文姬", "10");
	// driver.go("strengthen", "equip", "蔡文姬", "2", "10");
	// driver.go("strengthen", "equip", "蔡文姬", "3", "10");
	// driver.go("strengthen", "equip", "蔡文姬", "4", "10");
	// driver.go("map", "action", "3", "1", "1", "10");
	// driver.go("gift", "level", "30");
	// driver.go("gift", "task");
	// }
}
