package org.unidal.game.hanjiangsanguo.task.core;

import org.junit.Test;
import org.unidal.game.hanjiangsanguo.account.XiaoHaoAccount;
import org.unidal.lookup.ComponentTestCase;

public class XiaohaoTest extends ComponentTestCase{

	@Test
	public void test(){
		XiaoHaoAccount accout = lookup(XiaoHaoAccount.class);
		
		accout.doClydeTask();
	}
}
