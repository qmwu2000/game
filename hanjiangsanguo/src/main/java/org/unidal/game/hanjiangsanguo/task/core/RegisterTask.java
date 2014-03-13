package org.unidal.game.hanjiangsanguo.task.core;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.unidal.game.hanjiangsanguo.task.Task;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.game.hanjiangsanguo.task.TaskHelper;
import org.unidal.helper.Files;
import org.unidal.lookup.annotation.Inject;

public class RegisterTask implements Task {
	public static final String ID = "register";

	@Inject
	private TaskHelper m_helper;

	@Override
	public void execute(TaskContext ctx) throws Exception {
		ctx.setDefaultCategory("user");

		handleRegisterGuest(ctx);
		handleLoginGuest(ctx);
		handleSelectRole(ctx);
		handleActivationCode(ctx, 7362);
		handleActivationCode(ctx, 1025);

		for (int i = 0; i < 3; i++) {
			if (handleHitEgg(ctx)) {
				handleActivationCode(ctx, 1781);

				int uid = ctx.getIntAttribute("uid", 0);
				String username = ctx.getAttribute("username");
				String password = ctx.getAttribute("password");

				if (username == null) {
					username = "gongxian" + (uid % 10000);
				}

				if (password == null) {
					password = "gongxian" + (uid % 10000);
				}

				handleRegisterBinding(ctx, username, password);

				record(username, password);
				ctx.setAttribute("hired", "true");
				break;
			}
		}
	}

	private void record(String username, String password) throws IOException {
		File file = new File("target/accounts.properties");
		String value = "";

		if (file.exists()) {
			value = Files.forIO().readFrom(file, "utf-8");
		}

		System.err.println(username + ":" + password);
		Files.forIO().writeTo(file, value + "\r\n" + username + ": " + password);
	}

	private void handleRegisterBinding(TaskContext ctx, String username, String password) throws Exception {
		//http://uc.game.hanjiangsanguo.com/index.php?&c=register&m=binding&&channel=150&lang=zh-cn&rand=139459269640612&uid=6497024&u=1xiaohao24&p=1xiaohao24&mobile=&v=0&mac=00%3A00%3A00%3A00%3A00%3A00&adid=&devicetoken=000000&channel=150&token=dB6X8fXjk_wDYmBw-JitwQ
		String url = m_helper.buildUrl3(ctx, "register", "binding", "&u=" + username + "&p=" + password+"&mobile=&v=0&mac=00:00:00:00:00:00&adid=&devicetoken=000000&channel=150&uid=%s&token=%s", "uid", "token");

		m_helper.doGet(ctx, url);
	}

	private void handleRegisterGuest(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl3(ctx, "register", "guest", "&devicetoken=000000&adid=&mac=00:00:00:00:00:00");

		m_helper.doGet(ctx, url, "uid");
	}

	private void handleLoginGuest(TaskContext ctx) throws Exception {
		TimeUnit.SECONDS.sleep(1);

		String url = m_helper
		      .buildUrl(
		            ctx,
		            "http://s%s.game.hanjiangsanguo.com/index.php?v=0&c=login&&m=guest&&token=&channel=150&lang=zh-cn&rand=%s&devicetoken=000000&adid=&uid=%s&mac=00:00:00:00:00:00",
		            "user/server", "timestamp", "user/uid");

		m_helper.doGet(ctx, url, "token");
	}

	private void handleActivationCode(TaskContext ctx, int code) throws Exception {
		String url = m_helper.buildUrl2(ctx, "activationcode", "get_reward", "&code=" + code);

		m_helper.doGet(ctx, url);
	}

	private void handleSelectRole(TaskContext ctx) throws Exception {
		int uid = ctx.getIntAttribute("uid", 0);
		String name = "obama" + (uid % 10000);
		String url = m_helper.buildUrl2(ctx, "member", "select_role", "&sex=1&name=" + name + "&token=%s", "token");

		m_helper.doGet(ctx, url);
	}

	private boolean handleHitEgg(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "hitegg", "hit_egg", "&id=1");

		m_helper.doGet(ctx, url, "reward.value.generalid");

		if (ctx.getIntAttribute("reward.value.generalid", 0) == 10) {
			return true;
		} else {
			return false;
		}
	}
}
