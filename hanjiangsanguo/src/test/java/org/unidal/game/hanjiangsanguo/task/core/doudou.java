package org.unidal.game.hanjiangsanguo.task.core;

import org.junit.Test;
import org.unidal.game.hanjiangsanguo.account.DouDouMainAccount;
import org.unidal.game.hanjiangsanguo.account.HanfengMainAccount;
import org.unidal.game.hanjiangsanguo.account.KeJiYaoMainAccount;
import org.unidal.game.hanjiangsanguo.task.TaskDriver;
import org.unidal.game.hanjiangsanguo.task.activity.ArenaActivity;
import org.unidal.lookup.ComponentTestCase;

public class doudou extends ComponentTestCase {
	static {
		System.setProperty("devMode", "true");
	}

	@Test
	public void testHanfeng() {
		HanfengMainAccount account = lookup(HanfengMainAccount.class);

		account.doDaHaoTask();
		// account.doJiangSetUp();
		// account.doBossSetUp();
		// account.doHreoSetUp();

		// account.doShenJiang();
		// account.doDaHaoTask();
		// accout.doWorldBoss();
		// accout.doCycleTask();
	}

	@Test
	public void testKeji() {
		KeJiYaoMainAccount account = lookup(KeJiYaoMainAccount.class);

		// account.doJiangSetUp();
		account.doBossSetUp();
		// account.doHreoSetUp();

	}

	@Test
	public void doHreoSetUp() throws Exception {
		DouDouMainAccount account = lookup(DouDouMainAccount.class);

		account.doDaHaoTask();

		// account.doHreoSetUp();
		// account.doDaHaoTask();
	}

	@Test
	public void doBossSetup() throws Exception {
		DouDouMainAccount account = lookup(DouDouMainAccount.class);

		account.doBossSetUp();
	}
	
	@Test
	public void testArena() throws Exception{
		TaskDriver driver = lookup(TaskDriver.class);
		
		driver.go("login", "107", "superwyx", "wyx1116");
		driver.go("arena", "rank");

		String arenaUid = driver.getContext().getAttribute("arena", "uid");
		String arenaServerId = driver.getContext().getAttribute("arena", "server");
		System.out.println(arenaUid+" "+arenaServerId);
		
		driver.getContext().setAttribute(ArenaActivity.ID, "uid", arenaUid);
		driver.getContext().setAttribute(ArenaActivity.ID, "server", arenaServerId);
	
		driver.go("arena","bet");
		
	}

	@Test
	public void doJiangSetup() throws Exception {
		DouDouMainAccount account = lookup(DouDouMainAccount.class);

		account.doJiangSetUp();
	}

}
