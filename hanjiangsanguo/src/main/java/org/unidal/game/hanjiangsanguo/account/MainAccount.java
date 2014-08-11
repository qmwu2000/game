package org.unidal.game.hanjiangsanguo.account;

import java.util.Calendar;

import org.unidal.game.hanjiangsanguo.member.entity.Member;
import org.unidal.game.hanjiangsanguo.task.TaskDriver;
import org.unidal.game.hanjiangsanguo.task.activity.ArenaActivity;
import org.unidal.game.hanjiangsanguo.task.activity.CountryBossActivity;
import org.unidal.game.hanjiangsanguo.task.activity.GemActivity;
import org.unidal.game.hanjiangsanguo.task.activity.HeavenActivity;
import org.unidal.game.hanjiangsanguo.task.activity.HeroActivity;
import org.unidal.game.hanjiangsanguo.task.activity.JiangActivity;
import org.unidal.game.hanjiangsanguo.task.activity.SoulActivity;
import org.unidal.game.hanjiangsanguo.task.activity.WorldbossActivity;
import org.unidal.lookup.annotation.Inject;

public abstract class MainAccount {
	protected Member m_member;

	@Inject
	protected TaskDriver m_driver;

	private void login() {
		m_driver.go("login", m_member.getServer(), m_member.getUserName(), m_member.getPassword());
	}

	public void doMainTask() {
		m_driver.go(SoulActivity.ID, m_member.getSoul().getLevel());
		m_driver.go(GemActivity.ID, m_member.getGem().getMapId(), m_member.getGem().getLevel());
		m_driver.go(HeavenActivity.ID, m_member.getHeaven().getLevel());
	}

	public void doGeneralTask() {
		m_driver.go(SoulActivity.ID, m_member.getSoul().getLevel());
		m_driver.go(GemActivity.ID, m_member.getGem().getMapId(), m_member.getGem().getLevel());
		m_driver.go(HeavenActivity.ID, m_member.getHeaven().getLevel());
		m_driver.go(HeroActivity.ID);
		m_driver.go(JiangActivity.ID, m_member.getJiang().getLevel(), m_member.getMainPerson());
	}
	
	public void bet(String arenaUid,String arenaServerId){
		login();
		m_driver.getContext().setAttribute(ArenaActivity.ID, "uid", arenaUid);
		m_driver.getContext().setAttribute(ArenaActivity.ID, "server", arenaServerId);
		m_driver.go("arena", "bet");
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
			if (isIdleTime()) {
				m_driver.go("country", "sacrifice"); // 祭祀
				m_driver.go("country", "dice"); // 国家骰子
			}
		}
	}

	public void doLastInDay() {
		login();
	}

	public void doCycleTask() {
		login();

		if (m_driver.getContext().getIntAttribute("member", "country", 0) > 0) {
			m_driver.go("banquet", "active"); // 国宴
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

	protected boolean isIdleTime() {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);

		if (hour >= 4) {
			return true;
		} else {
			return false;
		}
	}
}
