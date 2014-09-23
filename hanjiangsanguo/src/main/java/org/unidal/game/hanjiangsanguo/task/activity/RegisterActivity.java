package org.unidal.game.hanjiangsanguo.task.activity;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.helper.Files;

public class RegisterActivity extends AbstractTaskActivity {
	public static final String ID = "register";

	@Override
	public boolean execute(TaskContext ctx, TaskArguments args) throws Exception {
		String op = args.nextString(null);

		ensure(op != null);
		ctx.setDefaultCategory("user");

		if ("lottery".equals(op)) {
			String server = args.nextString(null);
			String name = args.nextString(null);

			ctx.setAttribute("user", "server", server);

			register(ctx);

			lottery(ctx, name);
		} else if ("hitegg".equals(op)) {
			register(ctx);
			hitegg(ctx);
		} else {
			String server = args.nextString(null);
			String name = args.nextString(null);

			ctx.setAttribute("user", "server", server);

			register(ctx);

			simple(ctx, name);
			return true;
		}

		return true;
	}

	private void handleActivationCode(TaskContext ctx, int code) throws Exception {
		String url = m_helper.buildUrl2(ctx, "activationcode", "get_reward", "&code=" + code);

		m_helper.doGet(ctx, url);
	}

	private boolean handleHitEgg(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "hitegg", "hit_egg", "&id=1");

		m_helper.doGet(ctx, url, "reward.value.generalid");

		int generalId = ctx.getIntAttribute("reward.value.generalid", 0);

		ctx.setAttribute("reward.value.generalid", null);

		if (generalId == 10) {
			return true;
		} else {
			return false;
		}
	}

	private void handleLoginGuest(TaskContext ctx) throws Exception {
		TimeUnit.MILLISECONDS.sleep(200);

		String url = m_helper
		      .buildUrl(
		            ctx,
		            "http://s%s.game.hanjiangsanguo.com/index.php?v=0&c=login&&m=guest&&token=&channel=150&lang=zh-cn&rand=%s&devicetoken=000000&adid=&uid=%s&mac=00:00:00:00:00:00",
		            "user/server", "timestamp", "user/uid");

		m_helper.doGet(ctx, url, "token");
	}

	private boolean handleLottery(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "springlottery", "action", null);

		m_helper.doGet(ctx, url, "info.gettype", "info.getvalue");

		if (ctx.getIntAttribute("info.gettype", 0) == 8) {
			int value = ctx.getIntAttribute("info.getvalue", 0);

			switch (value) {
			case 74: // 飞龙甲
				System.err.println("飞龙甲");
				break;
			case 75: // 虎王袍
				System.err.println("虎王袍");
				break;
			default:
				System.err.println(value + ": ???");
			}

			return true;
		}

		return false;
	}

	private void handleRegisterBinding(TaskContext ctx, String username, String password) throws Exception {
		// http://uc.game.hanjiangsanguo.com/index.php?&c=register&m=binding&&channel=150&lang=zh-cn&rand=139459269640612&uid=6497024&u=1xiaohao24&p=1xiaohao24&mobile=&v=0&mac=00%3A00%3A00%3A00%3A00%3A00&adid=&devicetoken=000000&channel=150&token=dB6X8fXjk_wDYmBw-JitwQ
		String url = m_helper
		      .buildUrl3(ctx, "register", "binding", "&u=" + username + "&p=" + password
		            + "&mobile=&v=0&mac=00:00:00:00:00:00&adid=&devicetoken=000000&channel=150&uid=%s&token=%s", "uid",
		            "token");

		m_helper.doGet(ctx, url);
	}

	private void handleRegisterGuest(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl3(ctx, "register", "guest", "&devicetoken=000000&adid=&mac=00:00:00:00:00:00");

		m_helper.doGet(ctx, url, "uid");
	}

	private void handleRegisterReward(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "regreward", "get_reward", null);

		m_helper.doGet(ctx, url);
	}

	private void handleSelectRole(TaskContext ctx) throws Exception {
		TimeUnit.MILLISECONDS.sleep(500);
		String prefix = "数字号码";
		int uid = ctx.getIntAttribute("uid", 0);
		String name = prefix + Integer.toHexString(uid);

		String url = m_helper.buildUrl2(ctx, "member", "select_role", "&sex=1&name=" + name + "&token=%s", "token");

		m_helper.doGet(ctx, url);
	}

	private boolean hitegg(TaskContext ctx) throws Exception, IOException {
		for (int i = 0; i < 8; i++) {
			if (handleHitEgg(ctx)) {
				String username = ctx.getAttribute("username");
				String password = ctx.getAttribute("password");

				handleActivationCode(ctx, 1781);
				handleRegisterBinding(ctx, username, password);
				handleRegisterReward(ctx);

				record(username, password);
				return true;
			}
		}

		return false;
	}

	private boolean lottery(TaskContext ctx, String name) throws Exception, IOException {
		boolean found = false;

		for (int i = 0; i < 8; i++) {
			if (handleLottery(ctx)) {
				found = true;
			}
		}

		if (found) {
			handleActivationCode(ctx, 1781);
			handleRegisterBinding(ctx, name, name);
			handleRegisterReward(ctx);

			record(name, name);
			ctx.setAttribute("lucky", "1");
		}

		return found;
	}

	private boolean simple(TaskContext ctx, String name) throws Exception, IOException {
		handleRegisterBinding(ctx, name, name);
		handleRegisterReward(ctx);

		record(name, name);
		ctx.setAttribute("lucky", "1");

		return true;
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

	private void register(TaskContext ctx) throws Exception, IOException {
		handleRegisterGuest(ctx);
		handleLoginGuest(ctx);
		handleSelectRole(ctx);

//		int[] codes = { 9732, 9320, 8763, 8371, 7482, 7362, 7293, 5236, 5231, 4278, 3381, 3029, 3421, 1025 };
//
//		for (int code : codes) {
//			handleActivationCode(ctx, code);
//		}
	}
}
