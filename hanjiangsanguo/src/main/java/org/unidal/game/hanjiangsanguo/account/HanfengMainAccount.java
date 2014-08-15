package org.unidal.game.hanjiangsanguo.account;

import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;
import org.unidal.game.hanjiangsanguo.member.transform.DefaultSaxParser;
import org.unidal.game.hanjiangsanguo.task.activity.MineActivity;
import org.unidal.helper.Files;

public class HanfengMainAccount extends MainAccount implements Initializable {
	public void initialize() throws InitializationException {
		try {
			String content = Files.forIO().readFrom(this.getClass().getResourceAsStream("/config/hanfeng.xml"), "utf-8");
			m_member = DefaultSaxParser.parse(content);
		} catch (Exception e) {
			throw new InitializationException("init error", e);
		}
	}

	@Override
	public void doFirstInDay() {
		super.doFirstInDay();
	   doDaHaoTask();
	}

	@Override
	public void doLastInDay() {
		super.doLastInDay();
	}

	@Override
	public void doCycleTask() {
		super.doCycleTask();
		m_driver.getContext().setAttribute(MineActivity.ID, "maxMineGold", "340000");
		m_driver.go(MineActivity.ID, "active");
	}

}
