package org.unidal.game.hanjiangsanguo.task.core;

import org.junit.Test;
import org.unidal.game.hanjiangsanguo.task.TaskDriver;
import org.unidal.lookup.ComponentTestCase;

public class SpeedPlan extends ComponentTestCase {
	static {
		System.setProperty("devMode", "true");
	}

	@Test
	public void test() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);

		driver.go("login", "107", "xiaoke04", "xiaoke04");
		driver.go("muster", "on", "甘宁");
		driver.go("matrix", "update", "1", "甘宁", "廖化");
		driver.go("gift", "login");
		driver.go("gift", "task");

		driver.go("map", "action", "1", "1", "1", "10");
		driver.go("practice", "train", "甘宁");
		driver.go("practice", "train", "廖化");
		driver.go("map", "reward", "10");
		driver.go("muster", "on", "周仓");
		driver.go("matrix", "use", "2", "甘宁", "廖化");
		driver.go("matrix", "levelup", "2", "2");

		driver.go("map", "action", "1", "2", "1", "10");
		driver.go("map", "reward", "20");
		driver.go("muster", "on", "张梁");
		driver.go("matrix", "levelup", "2");
		driver.go("matrix", "use", "2", "甘宁", "廖化", "张梁");

		driver.go("map", "action", "1", "3", "1", "10");
		driver.go("map", "reward", "30");
		driver.go("muster", "on", "张宝");
		driver.go("matrix", "levelup", "2");
		driver.go("matrix", "update", "2", "甘宁", "廖化", "张梁", "周仓");

		driver.go("cultivate", "money", "甘宁", "30");
		driver.go("cultivate", "gold", "甘宁", "30");
		driver.go("gift", "task");

		driver.go("map", "action", "1", "4", "1", "7");
		driver.go("practice", "goleap", "甘宁", "5");
		driver.go("general", "equip", "甘宁", "落凤弓", "锦缎披风");
		driver.go("general", "equip", "廖化", "三尖两刃刀", "天河寒江甲", "苍狼披风");
		driver.go("general", "equip", "张梁", "铁蒺藜骨朵", "犀牛宝甲");
		driver.go("map", "action", "1", "4", "8", "10");
		driver.go("map", "newresult", "1");
		driver.go("map", "reward", "40");
		driver.go("matrix", "levelup", "2");
		driver.go("matrix", "update", "2", "甘宁", "张宝", "张梁", "周仓", "廖化");
		driver.go("gift", "level", "25");
		driver.go("gift", "task");

		driver.go("strengthen", "equip", "甘宁", "1", "20"); // 落凤弓

		driver.go("map", "action", "2", "1", "1", "10");
		driver.go("map", "reward", "50");
		driver.go("matrix", "levelup", "2");
		driver.go("gift", "level", "30");
		driver.go("general", "equip", "甘宁", "狮心锁环甲");
		driver.go("strengthen", "equip", "甘宁", "3", "20"); // 狮心锁环甲
		driver.go("general", "equip", "甘宁", "衠钢槊");
		driver.go("strengthen", "equip", "甘宁", "1", "20"); // 衠钢槊
		driver.go("general", "equip", "甘宁", "虎纹披风");
		driver.go("strengthen", "equip", "甘宁", "4", "20"); // 虎纹披风
		driver.go("general", "equip", "廖化", "落凤弓");
		driver.go("general", "equip", "廖化", "狮心锁环甲");
		driver.go("gift", "task");

		driver.go("map", "action", "2", "2", "1", "10");
		driver.go("map", "reward", "60");
		driver.go("matrix", "levelup", "2");
		driver.go("gift", "level", "35");
		driver.go("gift", "task");

		driver.go("strengthen", "equip", "甘宁", "4", "10"); // 虎纹披风
		driver.go("practice", "goleap", "甘宁", "20");
		driver.go("strengthen", "equip", "甘宁", "1", "10"); // 衠钢槊
		driver.go("strengthen", "equip", "甘宁", "3", "10"); // 狮心锁环甲
		driver.go("cultivate", "gold", "甘宁", "40");
		driver.go("map", "action", "2", "3", "1", "10");
		driver.go("map", "reward", "70");
		driver.go("matrix", "levelup", "2");
		driver.go("gift", "level", "40");
		driver.go("gift", "task");

		driver.go("city", "impose", "50");
		driver.go("strengthen", "equip", "甘宁", "1", "20"); // 衠钢槊
		driver.go("strengthen", "equip", "甘宁", "3", "20"); // 狮心锁环甲
		driver.go("map", "action", "2", "4", "1", "10");
		driver.go("map", "newresult", "2");
		driver.go("map", "reward", "80");
		driver.go("matrix", "levelup", "2");
		driver.go("gift", "level", "45");
		driver.go("gift", "task");

		driver.go("practice", "goleap", "甘宁", "60");
		driver.go("cultivate", "gold", "甘宁", "50");
		driver.go("gift", "task");

		driver.go("strengthen", "equip", "甘宁", "1", "10"); // 衠钢槊
		driver.go("strengthen", "equip", "甘宁", "3", "10"); // 狮心锁环甲
		driver.go("map", "action", "3", "1", "1", "10");
		driver.go("map", "reward", "90");
		driver.go("matrix", "levelup", "2");
		driver.go("gift", "level", "50");
		driver.go("gift", "task");
		driver.go("lottery", "leap");

		// driver.go("gift", "invitation", "6q2addm");
		// driver.go("tower", "pk", "2", "12");
	}
}
