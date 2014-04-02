package org.unidal.game.hanjiangsanguo.task.core;

import org.junit.Test;
import org.unidal.game.hanjiangsanguo.task.TaskDriver;
import org.unidal.lookup.ComponentTestCase;

public class Register extends ComponentTestCase {
	static {
		System.setProperty("devMode", "true");
	}

	@Test
	public void register() throws Exception {
		TaskDriver driver = lookup(TaskDriver.class);
		int index = 1;

		for (int i = 0; i < 1000; i++) {
			String name = "xiaoyou" + Integer.toString(100 + index).substring(1);

			driver.go("register", "lottery", "107", name);

			if (driver.getContext().getIntAttribute("lucky", 0) == 1) {
				System.err.println(name);
				index++;
			}
			
			driver.reset();
			System.out.println();
			System.out.println();
		}
	}
}
