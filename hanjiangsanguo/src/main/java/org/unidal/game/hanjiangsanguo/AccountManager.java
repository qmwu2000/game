package org.unidal.game.hanjiangsanguo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.codehaus.plexus.logging.LogEnabled;
import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;
import org.unidal.game.hanjiangsanguo.account.DouDouMainAccount;
import org.unidal.game.hanjiangsanguo.account.HanfengMainAccount;
import org.unidal.game.hanjiangsanguo.account.HuaiyiMainAccount;
import org.unidal.game.hanjiangsanguo.account.KeJiYaoMainAccount;
import org.unidal.game.hanjiangsanguo.account.XiaoHaoAccount;
import org.unidal.game.hanjiangsanguo.account.YilianMainAccount;
import org.unidal.game.hanjiangsanguo.task.TaskDriver;
import org.unidal.helper.Files;
import org.unidal.helper.Threads;
import org.unidal.lookup.annotation.Inject;

public class AccountManager extends BaseManager implements Initializable, LogEnabled {

	@Inject
	private TaskDriver m_driver;

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
	private XiaoHaoAccount m_xiaohao;

	private Logger m_logger;

	private String m_arenaUid = null;

	private String m_arenaServerId = null;

	private boolean m_firstInDay = false;

	public void empty() {
	}

	private boolean buildArenaInfo(String server, String account, String password, TaskDriver driver) throws Exception {
		boolean arena = false;
		try {
			Calendar cal = Calendar.getInstance();
			int hour = cal.get(Calendar.HOUR_OF_DAY);

			if (hour == 23 && isArena()) {
				System.err.println("====== true");
				try {
					driver.go("login", server, account, password);
					driver.go("arena", "rank");

					m_arenaUid = driver.getContext().getAttribute("arena", "uid");
					m_arenaServerId = driver.getContext().getAttribute("arena", "server");
					driver.reset();
				} catch (Exception e) {
					m_logger.error(e.getMessage(), e);
				}

				File file = new File("target/arean.position");

				if (file != null) {
					file.createNewFile();
				}
				Date date = getCurrentDay();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String key = sdf.format(date);
				String content = Files.forIO().readFrom(file, "utf-8");
				Files.forIO().writeTo(file, content + "\n" + key + "=true");

				arena = true;
			} else {

				System.err.println("====== false");
				arena = false;
			}
		} catch (Exception e) {
			m_logger.error(e.getMessage(), e);
		}
		return arena;
	}

	@Override
	public void initialize() throws InitializationException {
		Threads.forGroup("hanjiang").start(new RobotTask());
	}

	public class RobotTask implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {
					if (!isBossTime()) {
						m_firstInDay = isFirstInDay();

						if (m_firstInDay) {
							m_hanfeng.doFirstInDay();
							m_doudou.doFirstInDay();
							m_kejiyao.doFirstInDay();
							m_yilian.doFirstInDay();
							m_huaiyi.doFirstInDay();
							m_xiaohao.doFirstInDay();
							
						} else {
							m_hanfeng.doCycleTask();
							m_doudou.doCycleTask();
							m_kejiyao.doCycleTask();
							m_yilian.doCycleTask();
							m_huaiyi.doCycleTask();
							m_xiaohao.doClydeTask();
						}

						boolean arean = buildArenaInfo("107", "2xiaohao362", "2xiaohao362", m_driver);

						if(arean){
							m_hanfeng.bet(m_arenaUid, m_arenaServerId);
							m_doudou.bet(m_arenaUid, m_arenaServerId);
							m_kejiyao.bet(m_arenaUid, m_arenaServerId);
							m_yilian.bet(m_arenaUid, m_arenaServerId);
							m_huaiyi.bet(m_arenaUid, m_arenaServerId);
							m_xiaohao.bet(m_arenaUid, m_arenaServerId);
						}
					}
				} catch (Throwable e) {
					m_logger.error(e.getMessage(), e);
				}

				try {
					Thread.sleep(1000 * 60 * 15);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	@Override
	public void enableLogging(Logger logger) {
		m_logger = logger;
	}
}
