package org.unidal.game.hanjiangsanguo.task.core;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;
import org.unidal.game.hanjiangsanguo.task.Task;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.game.hanjiangsanguo.task.TaskHelper;
import org.unidal.lookup.annotation.Inject;

public class TaskTask implements Task, Initializable {
	public static final String ID = "task";

	@Inject
	private TaskHelper m_helper;

	private Map<String, String> m_types = new HashMap<String, String>();

	@Override
	public void execute(TaskContext ctx) throws Exception {
		ctx.setDefaultCategory(ID);

		handleIndex(ctx);

		int times = ctx.getIntAttribute("info.nowmaxtimes", 0);

		for (int i = 0; i < times; i++) {
			handleGoBusiness(ctx);
		}
	}

	private void handleGoBusiness(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "business", "go_business", "&id=%s", "trader[0].id");

		m_helper.doGet(ctx, url);
	}

	private void handleIndex(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "business", "index", null);

		m_helper.doGet(ctx, url, "times", "trader[0].id", "trader[1].id", "trader[2].id");
	}

	@Override
	public void initialize() throws InitializationException {
		m_types.put("1", "武将突飞");
		m_types.put("2", "银币征收");
		m_types.put("3", "银矿抢占");
		m_types.put("4", "每日通商");
		m_types.put("5", "元宝消费");
		m_types.put("6", "关卡挑战");
		m_types.put("7", "属性培养");
		m_types.put("8", "装备升级");
		m_types.put("9", "金银守卫");
		m_types.put("10", "演武竞技");
		m_types.put("11", "宝石贸易");
		m_types.put("12", "世界BOSS");
	}
}
