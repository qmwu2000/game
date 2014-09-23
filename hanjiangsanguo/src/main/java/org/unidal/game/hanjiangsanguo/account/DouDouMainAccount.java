package org.unidal.game.hanjiangsanguo.account;

import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;
import org.unidal.game.hanjiangsanguo.member.transform.DefaultSaxParser;
import org.unidal.game.hanjiangsanguo.task.activity.MatrixActivity;
import org.unidal.game.hanjiangsanguo.task.activity.MineActivity;
import org.unidal.game.hanjiangsanguo.task.activity.SoulEquipActivity;
import org.unidal.helper.Files;

public class DouDouMainAccount extends MainAccount implements Initializable {
	public void initialize() throws InitializationException {
		try {
			String content = Files.forIO().readFrom(this.getClass().getResourceAsStream("/config/doudou.xml"), "utf-8");
			m_member = DefaultSaxParser.parse(content);
		} catch (Exception e) {
			throw new InitializationException("init error", e);
		}
	}

	@Override
	public void doFirstInDay() {
		super.doFirstInDay();
		super.doDaHaoTask();
		super.doShenJiang();
		updateMatrix();
	}

	@Override
	public void doLastInDay() {
		super.doLastInDay();
	}

	@Override
	public void bet(String arenaUid, String arenaServerId) {
		super.bet(arenaUid, arenaServerId);
		updateMatrix();
	}

	private void updateMatrix() {
		m_driver.go(MatrixActivity.ID, "switch", "10", "-2,43887,-2,-2,136825,-2,128609,66097,60009");
	}

	@Override
	public void doCycleTask() {
		super.doCycleTask();
		m_driver.go(SoulEquipActivity.ID, "66097,136825,43887", "42026,42652,33617");
		updateMatrix();
		
		m_driver.getContext().setAttribute(MineActivity.ID, "maxMineGold", "300000");
		m_driver.go(MineActivity.ID, "active");
	}

}
