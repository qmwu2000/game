package org.unidal.game.hanjiangsanguo.task.core;

import org.junit.Test;
import org.unidal.game.hanjiangsanguo.account.DouDouMainAccount;
import org.unidal.game.hanjiangsanguo.account.HanfengMainAccount;
import org.unidal.game.hanjiangsanguo.account.KeJiYaoMainAccount;
import org.unidal.game.hanjiangsanguo.account.LaFengAccount;
import org.unidal.game.hanjiangsanguo.account.XiaoHaoAccount;
import org.unidal.game.hanjiangsanguo.task.TaskDriver;
import org.unidal.game.hanjiangsanguo.task.activity.ArenaActivity;
import org.unidal.lookup.ComponentTestCase;

public class doudou extends ComponentTestCase {
	static {
		System.setProperty("devMode", "true");
	}
	
	@Test
	public void testLafeng() {
		LaFengAccount account = lookup(LaFengAccount.class);

		account.doDaHaoTask();
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

		account.doCycleTask();
		
		//account.doDaHaoTask();
		// account.doHreoSetUp();
		// account.doDaHaoTask();
	}

	@Test
	public void doBossSetup() throws Exception {
		DouDouMainAccount account = lookup(DouDouMainAccount.class);

		account.doBossSetUp();
		account.doFirstInDay();
	}
	
	@Test
	public void testArena() throws Exception{
		TaskDriver driver = lookup(TaskDriver.class);
		
		driver.go("login", "107", "2xiaohao362", "2xiaohao362");
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

		//account.doDaHaoTask();
		account.doShenJiang();
		
	}

	@Test
	public void doDouDouBoss() throws Exception {
		DouDouMainAccount account = lookup(DouDouMainAccount.class);

		account.doBossSetUp();
		account.doWorldBoss();
	}
	
	@Test
	public void doHanfengBosst() throws Exception {
		HanfengMainAccount account = lookup(HanfengMainAccount.class);

		account.doBossSetUp();
		account.doWorldBoss();
	}

	@Test
	public void doKejiYaoBoss() throws Exception {
		KeJiYaoMainAccount account = lookup(KeJiYaoMainAccount.class);

		account.doBossSetUp();
		account.doWorldBoss();
	}
	
	@Test
	public void doDouDouCountryBoss() throws Exception {
		DouDouMainAccount account = lookup(DouDouMainAccount.class);

		account.doCycleTask();
		//account.doShenJiang();
		//account.doCycleTask();
		//account.doBossSetUp();
		//account.doCountryBoss();
	}
	
	@Test
	public void doHanfengCountryBoss() throws Exception {
		HanfengMainAccount account = lookup(HanfengMainAccount.class);

		account.doBossSetUp();
		account.doCountryBoss();
	}

	@Test
	public void doKejiYaoCountryBoss() throws Exception {
		KeJiYaoMainAccount account = lookup(KeJiYaoMainAccount.class);

		account.doBossSetUp();
		account.doWorldBoss();
		//account.doCountryBoss();
	}
	
	@Test
	public void doXiaoHao() throws Exception {
		XiaoHaoAccount account = lookup(XiaoHaoAccount.class);

//		account.doFirstInDay();
		account.doClydeTask();
	}

}
