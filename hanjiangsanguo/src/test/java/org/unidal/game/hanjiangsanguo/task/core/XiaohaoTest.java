package org.unidal.game.hanjiangsanguo.task.core;

import org.junit.Test;
import org.unidal.game.hanjiangsanguo.account.XiaoHaoAccount;
import org.unidal.game.hanjiangsanguo.task.TaskDriver;
import org.unidal.lookup.ComponentTestCase;

public class XiaohaoTest extends ComponentTestCase{
	
	static {
		System.setProperty("devMode", "true");
	}

	@Test
	public void test(){
		XiaoHaoAccount accout = lookup(XiaoHaoAccount.class);
		
		
		accout.doClydeTask();
	}
	
	@Test
	public void beauty(){
		TaskDriver driver = lookup(TaskDriver.class);
		driver.go("login", "107", "1xiaohao320", "1xiaohao320");
		driver.go("beauty", "beauty");
	}
	
	@Test
	public void test2(){
		System.out.println("\u7b49\u7ea7\u4e0d\u8db3");
		System.out.println("\u6b63\u5728\u6218\u6597\u4e2d");
		System.out.println("\u5df2\u7ecf\u62e5\u6709\u8be5\u788e\u7247");
	}
}
