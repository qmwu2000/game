package org.unidal.game.hanjiangsanguo.task.activity;

import java.util.List;
import java.util.Map;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.helper.Splitters;

public class CultivateActivity extends AbstractTaskActivity {
	public static final String ID = "cultivate";

	private boolean doCultivate(TaskContext ctx, String gid, int mode) throws Exception {
		if (roll(ctx, gid, mode)) {
			save(ctx, gid);

			m_logger.info("Bingo!");
		} else {
			giveup(ctx, gid);
		}

		return true;
	}

	private boolean roll(TaskContext ctx, String gid, int mode) throws Exception {
		int wuliup = ctx.getIntAttribute("info.wuliup", 0);
		int zhiliup = ctx.getIntAttribute("info.zhiliup", 0);
		int tiliup = ctx.getIntAttribute("info.tiliup", 0);
		String url = m_helper.buildUrl2(ctx, "cultivate", "roll", String.format("&gid=%s&mode=%s", gid, mode));

		m_helper.doGet(ctx, url, "info.wuliup", "info.zhiliup", "info.tiliup");

		int wuliup2 = ctx.getIntAttribute("info.wuliup", 0);
		int zhiliup2 = ctx.getIntAttribute("info.zhiliup", 0);
		int tiliup2 = ctx.getIntAttribute("info.tiliup", 0);
		int sum = wuliup + zhiliup + tiliup;
		int sum2 = wuliup2 + zhiliup2 + tiliup2;
		int delta = (wuliup2 * wuliup2 + zhiliup2 * zhiliup2 + tiliup2 * tiliup2) //
		      - (wuliup * wuliup + zhiliup * zhiliup + tiliup * tiliup);

		if (sum2 > sum) {
			return true;
		} else if (sum2 == sum && delta < 0) {
			return true;
		} else if (sum2 == sum - 1 && delta < 0 && wuliup2 - wuliup > 0) {
			return true;
		} else {
			// restore
			ctx.setAttribute("info.wuliup", String.valueOf(wuliup));
			ctx.setAttribute("info.zhiliup", String.valueOf(zhiliup));
			ctx.setAttribute("info.tiliup", String.valueOf(tiliup));
			return false;
		}
	}

	private boolean save(TaskContext ctx, String gid) throws Exception {
		String url = m_helper.buildUrl2(ctx, "cultivate", "save", String.format("&gid=%s", gid));

		return m_helper.doGet(ctx, url);
	}

	private boolean giveup(TaskContext ctx, String gid) throws Exception {
		String url = m_helper.buildUrl2(ctx, "cultivate", "giveup", String.format("&gid=%s", gid));

		return m_helper.doGet(ctx, url);
	}

	public boolean execute(TaskContext ctx, TaskArguments args) throws Exception {
		String op = args.nextString(null);

		ensure(op != null);
		ctx.setDefaultCategory(ID);

		if ("money".equals(op)) {
			String name = args.nextString(null);
			int times = args.nextInt(1);
			String gid = mapId(ctx, name);

			for (int i = 0; i < times; i++) {
				doCultivate(ctx, gid, 1);
			}
		} else if ("gold".equals(op)) {
			String name = args.nextString(null);
			int times = args.nextInt(1);
			String gid = mapId(ctx, name);

			for (int i = 0; i < times; i++) {
				doCultivate(ctx, gid, 2);
			}
		}

		return true;
	}

	private String mapId(TaskContext ctx, String name) throws Exception {
		String url = m_helper.buildUrl2(ctx, "cultivate", "index", null);

		m_helper.doGetWithScript(ctx, url, "var gs=''; for (var i in o.list) gs+=o.list[i].name+':'+o.list[i].id+" //
		      + "'|'+o.list[i].wuliup+'|'+o.list[i].zhiliup+'|'+o.list[i].tiliup+','; gs;", "list");

		String list = ctx.getAttribute("list");
		Map<String, String> map = Splitters.by(',', ':').trim().split(list);
		String str = map.get(name);
		List<String> values = Splitters.by('|').split(str);

		ctx.setAttribute("info.wuliup", values.get(1));
		ctx.setAttribute("info.zhiliup", values.get(2));
		ctx.setAttribute("info.tiliup", values.get(3));

		return values.get(0);
	}
}
