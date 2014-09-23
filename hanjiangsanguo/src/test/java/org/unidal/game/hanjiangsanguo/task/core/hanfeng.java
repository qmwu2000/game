package org.unidal.game.hanjiangsanguo.task.core;

import org.junit.Test;
import org.unidal.game.hanjiangsanguo.account.HanfengMainAccount;
import org.unidal.lookup.ComponentTestCase;

public class hanfeng extends ComponentTestCase{
	
	static {
		System.setProperty("devMode", "true");
	}

	@Test
	public void test(){
		HanfengMainAccount accout = lookup(HanfengMainAccount.class);
		
		accout.doDaHaoTask();
		
		accout.doShenJiang();
//		accout.doDaHaoTask();
//		accout.doCountryBoss();
//		
//		accout.doWorldBoss();
//		accout.doCycleTask();
	}
}
