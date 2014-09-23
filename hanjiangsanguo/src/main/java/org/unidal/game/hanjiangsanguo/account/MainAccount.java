package org.unidal.game.hanjiangsanguo.account;

import java.util.Calendar;

import org.unidal.game.hanjiangsanguo.member.entity.Boss;
import org.unidal.game.hanjiangsanguo.member.entity.Hreo;
import org.unidal.game.hanjiangsanguo.member.entity.Jiang;
import org.unidal.game.hanjiangsanguo.member.entity.Member;
import org.unidal.game.hanjiangsanguo.task.TaskDriver;
import org.unidal.game.hanjiangsanguo.task.activity.ArenaActivity;
import org.unidal.game.hanjiangsanguo.task.activity.CountryBossActivity;
import org.unidal.game.hanjiangsanguo.task.activity.GemActivity;
import org.unidal.game.hanjiangsanguo.task.activity.HeavenActivity;
import org.unidal.game.hanjiangsanguo.task.activity.HeroActivity;
import org.unidal.game.hanjiangsanguo.task.activity.JiangActivity;
import org.unidal.game.hanjiangsanguo.task.activity.MatrixActivity;
import org.unidal.game.hanjiangsanguo.task.activity.SoulActivity;
import org.unidal.game.hanjiangsanguo.task.activity.SoulEquipActivity;
import org.unidal.game.hanjiangsanguo.task.activity.WorldbossActivity;
import org.unidal.lookup.annotation.Inject;

public abstract class MainAccount {
	protected Member m_member;

	@Inject
	protected TaskDriver m_driver;

	public void login() {
		m_driver.go("login", m_member.getServer(), m_member.getUserName(), m_member.getPassword());
	}

	public void doBossSetUp() {
		login();
		Boss boss = m_member.getBoss();

		m_driver.go(MatrixActivity.ID, "switch", boss.getMid(), boss.getMatrix());
		m_driver.go(SoulEquipActivity.ID, boss.getGids(), boss.getSids());
	}

	public void doJiangSetUp() {
		login();
		Jiang jiang = m_member.getJiang();

		m_driver.go(SoulEquipActivity.ID, jiang.getGids(), jiang.getSids());
	}

	public void doHreoSetUp() {
		login();
		Hreo hreo = m_member.getHreo();

		m_driver.go(MatrixActivity.ID, "switch", hreo.getMid(), hreo.getMatrix());
		m_driver.go(SoulEquipActivity.ID, hreo.getGids(), hreo.getSids());
	}

	public void doDaHaoTask() {
		login();
		m_driver.go("map", "island", "10"); // 金银洞
		m_driver.go("trade", "business"); // 每日通商
		m_driver.go(SoulActivity.ID, m_member.getSoul().getLevel());
		m_driver.go(GemActivity.ID, m_member.getGem().getMapId(), m_member.getGem().getLevel());
		m_driver.go(HeavenActivity.ID, m_member.getHeaven().getLevel());
		doHreoSetUp();
		m_driver.go(HeroActivity.ID);
	}

	public void doShenJiang() {
		login();
		m_driver.go(JiangActivity.ID, m_member.getJiang().getLevel(), m_member.getMainPerson());
	}

	public void doOtherAccountTask() {
		login();
		m_driver.go(SoulActivity.ID, m_member.getSoul().getLevel());
		m_driver.go(GemActivity.ID, m_member.getGem().getMapId(), m_member.getGem().getLevel());
		m_driver.go(HeavenActivity.ID, m_member.getHeaven().getLevel());
		m_driver.go(HeroActivity.ID);
		m_driver.go(JiangActivity.ID, m_member.getJiang().getLevel(), m_member.getMainPerson());
		m_driver.go("map", "island", "10"); // 金银洞
		m_driver.go("trade", "business"); // 每日通商
	}

	public void bet(String arenaUid, String arenaServerId) {
		login();
		m_driver.getContext().setAttribute(ArenaActivity.ID, "uid", arenaUid);
		m_driver.getContext().setAttribute(ArenaActivity.ID, "server", arenaServerId);
		m_driver.go("arena", "bet");

		doDaHaoTask();
		m_driver.go("trade", "business"); // 每日通商
	}

	public void doFirstInDay() {
		login();
		m_driver.go("gift", "vip"); // VIP工资
		m_driver.go("gift", "login"); // 连续登录
		m_driver.go("lottery", "lave"); // 每日抽奖
		m_driver.go("drink", "drink"); // 饮酒
		m_driver.go("gift", "arena"); // 演武榜,押注
		m_driver.go("gift", "hitegg"); // 砸金蛋
		m_driver.go("activity", "sacredtree"); // 神树
		m_driver.go("activity", "springlottery"); // 幸运大转盘
		m_driver.go("activity", "acttreasure"); // 寻宝

		if (m_driver.getContext().getIntAttribute("member", "country", 0) > 0) {
			m_driver.go("gift", "country"); // 国库
			m_driver.go("country", "sacrifice"); // 祭祀
			m_driver.go("country", "dice"); // 国家骰子
		}
	}
	
	public void doLastInDay() {
		login();
	}

	public void doCycleTask() {
		login();

		m_driver.getContext().setAttribute("member", "dahao", "dahao");
		m_driver.go("banquet", "active"); // 国宴
		m_driver.go("gift", "hitegg"); // 砸金蛋

		if (isIdleTime()) {
			m_driver.go("countrymine", "active"); // 国家矿
			m_driver.go("trade", "oversea"); // 海外贸易
		}
	}

	public void doWorldBoss() {
		String userName = m_member.getUserName();
		String password = m_member.getPassword();
		String server = m_member.getServer();
		String matrix = m_member.getBoss().getMatrix();
		String mid = m_member.getBoss().getMid();

		m_driver.setup(userName, password, server, "worldboss/list", matrix, "worldboss/mid", mid);
		m_driver.go("login", server, userName, password);
		m_driver.go(WorldbossActivity.ID);
	}

	public void doCountryBoss() {
		String userName = m_member.getUserName();
		String password = m_member.getPassword();
		String server = m_member.getServer();
		String matrix = m_member.getBoss().getMatrix();
		String mid = m_member.getBoss().getMid();

		m_driver.setup(userName, password, server, "worldboss/list", matrix, "worldboss/mid", mid);
		m_driver.go("login", server, userName, password);
		m_driver.go(CountryBossActivity.ID);
	}

	private boolean isIdleTime() {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		
		if ((hour >= 3 && hour <= 7) || hour >= 22) {
			return true;
		} else {
			return false;
		}
	}
}
