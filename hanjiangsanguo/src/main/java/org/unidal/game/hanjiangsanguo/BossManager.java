package org.unidal.game.hanjiangsanguo;

import java.util.Calendar;

import org.codehaus.plexus.logging.LogEnabled;
import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;
import org.unidal.game.hanjiangsanguo.account.DouDouMainAccount;
import org.unidal.game.hanjiangsanguo.account.HanfengMainAccount;
import org.unidal.game.hanjiangsanguo.account.HuaiyiMainAccount;
import org.unidal.game.hanjiangsanguo.account.KeJiYaoMainAccount;
import org.unidal.game.hanjiangsanguo.account.LaFengAccount;
import org.unidal.game.hanjiangsanguo.account.MainAccount;
import org.unidal.game.hanjiangsanguo.account.YilianMainAccount;
import org.unidal.helper.Threads;
import org.unidal.lookup.annotation.Inject;

public class BossManager implements Initializable, Runnable, LogEnabled {

	@Inject
	private DouDouMainAccount m_doudou;

	@Inject
	private KeJiYaoMainAccount m_kejiyao;

	@Inject
	private HanfengMainAccount m_hanfeng;

	@Inject
	private HuaiyiMainAccount m_huaiyi;

	@Inject
	private YilianMainAccount m_yilian;
	
	@Inject
	private LaFengAccount m_lafeng;

	public void empty() {
	}

	@Override
	public void enableLogging(Logger logger) {
	}

	@Override
	public void initialize() throws InitializationException {
		Threads.forGroup("game").start(this);
	}

	@Override
	public void run() {
		while (true) {
			Calendar cal = Calendar.getInstance();
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			int minute = cal.get(Calendar.MINUTE);
			int week = cal.get(Calendar.DAY_OF_WEEK);

			if ((hour == 20) && minute == 0) {
				Threads.forGroup("game").start(new WorldBoss(m_hanfeng));
				Threads.forGroup("game").start(new WorldBoss(m_doudou));
				Threads.forGroup("game").start(new WorldBoss(m_kejiyao));
				Threads.forGroup("game").start(new WorldBoss(m_yilian));
				Threads.forGroup("game").start(new WorldBoss(m_huaiyi));
				Threads.forGroup("game").start(new WorldBoss(m_lafeng));
			}

			if (hour == 20 && minute == 30 && week == Calendar.FRIDAY) {
				Threads.forGroup("game").start(new CountryBoss(m_hanfeng));
				Threads.forGroup("game").start(new CountryBoss(m_doudou));
				Threads.forGroup("game").start(new CountryBoss(m_kejiyao));
				Threads.forGroup("game").start(new CountryBoss(m_yilian));
				Threads.forGroup("game").start(new CountryBoss(m_huaiyi));
				Threads.forGroup("game").start(new CountryBoss(m_lafeng));
			}

			try {
				Thread.sleep(60 * 1000);
			} catch (InterruptedException e) {
			}
		}
	}

	public class CountryBoss implements Runnable {

		private MainAccount m_account;

		private CountryBoss(MainAccount account) {
			m_account = account;
		}

		@Override
		public void run() {
			try {
				m_account.doBossSetUp();
				m_account.doCountryBoss();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public class WorldBoss implements Runnable {

		private MainAccount m_account;

		private WorldBoss(MainAccount account) {
			m_account = account;
		}

		@Override
		public void run() {
			try {
				m_account.doBossSetUp();
				m_account.doWorldBoss();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
