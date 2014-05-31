package org.unidal.game.hanjiangsanguo.task.core;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;
import org.unidal.game.hanjiangsanguo.task.Task;
import org.unidal.game.hanjiangsanguo.task.TaskActionManager;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.game.hanjiangsanguo.task.TaskHelper;
import org.unidal.lookup.annotation.Inject;

public class TaskTask implements Task, Initializable {
	public static final String ID = "task";

	@Inject
	private TaskHelper m_helper;

	@Inject
	private TaskActionManager m_manager;

	private Map<String, String> m_names = new HashMap<String, String>();

	private boolean acceptTask(TaskContext ctx) throws Exception {
		int times = ctx.getIntAttribute(ID, "number", 0);

		for (int index = 1; index <= 3; index++) {
			int s = ctx.getIntAttribute(ID, "taskstatus" + index, 0);

			if (s == 1) {
				setTaskIndex(ctx, index);
				return true;
			} else if (s == 2) {
				handleGetReward(ctx, index);

				times--;
				break;
			}
		}

		if (times > 0) {
			int index = selectTask(ctx);

			if (index > 0) {
				handleAcceptTask(ctx, index);
				return true;
			}
		}

		return false;
	}

	private void doTask(TaskContext ctx) throws Exception {
		int index = ctx.getIntAttribute(ID, "task.index", 0);
		String type = ctx.getAttribute(ID, "task.type");
		int times = ctx.getIntAttribute(ID, "task.number", 0);

		if (index > 0 && times > 0) {
			m_manager.execute(ctx, type, times);
		} else {
			throw new IllegalStateException(String.format("Invalid task index! index=%s, type=%s, times=%s.", index, type,
			      times));
		}
	}

	@Override
	public void execute(TaskContext ctx) throws Exception {
		ctx.setDefaultCategory(ID);

		while (true) {
			handleIndex(ctx);

			if (acceptTask(ctx)) {
				doTask(ctx);
			} else {
				break;
			}

			TimeUnit.SECONDS.sleep(1);
		}
	}

	private void handleAcceptTask(TaskContext ctx, int index) throws Exception {
		String url = m_helper.buildUrl2(ctx, ID, "accept_task", "&s=" + index);

		m_helper.doGet(ctx, url);
	}

	private void handleGetReward(TaskContext ctx, int index) throws Exception {
		String url = m_helper.buildUrl2(ctx, ID, "get_reward", "&s=" + index);

		m_helper.doGet(ctx, url, "number");
	}

	private void handleIndex(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, ID, "index", null);

		ctx.setDefaultCategory(ID);
		m_helper.doGet(ctx, url, "number", "free_times", "taskstatus1", "taskid1.type", "taskid1.value", "taskid1.plan",
		      "taskstatus2", "taskid2.type", "taskid2.value", "taskid2.plan", "taskstatus3", "taskid3.type",
		      "taskid3.value", "taskid3.plan");
	}

	@Override
	public void initialize() throws InitializationException {
		m_names.put("1", "武将突飞");
		m_names.put("2", "银币征收");
		m_names.put("3", "银矿抢占");
		m_names.put("4", "每日通商");
		m_names.put("5", "元宝消费");
		m_names.put("6", "关卡挑战");
		m_names.put("7", "属性培养");
		m_names.put("8", "装备升级");
		m_names.put("9", "金银守卫");
		m_names.put("10", "演武竞技");
		m_names.put("11", "宝石贸易");
		m_names.put("12", "世界BOSS");
		m_names.put("14", "天下史诗");
	}

	private int selectTask(TaskContext ctx) throws Exception {
		int priority = 0;
		int index = 0;
		String type = null;
		int times = 0;

		for (int i = 1; i <= 3; i++) {
			String action = ctx.getAttribute(ID, "taskid" + i + ".type");
			int value = ctx.getIntAttribute(ID, "taskid" + i + ".value", 0);
			int plan = ctx.getIntAttribute(ID, "taskid" + i + ".plan", 0);
			int p = m_manager.getPriority(ctx, action, value - plan);

			if (p > priority) {
				priority = p;
				type = action;
				index = i;
				times = value - plan;
			}
		}

		ctx.setAttribute(ID, "task.index", String.valueOf(index));
		ctx.setAttribute(ID, "task.type", type);
		ctx.setAttribute(ID, "task.number", String.valueOf(times));
		return index;
	}

	private void setTaskIndex(TaskContext ctx, int index) {
		String type = ctx.getAttribute(ID, "taskid" + index + ".type");
		int value = ctx.getIntAttribute(ID, "taskid" + index + ".value", 0);
		int plan = ctx.getIntAttribute(ID, "taskid" + index + ".plan", 0);

		ctx.setAttribute(ID, "task.index", String.valueOf(index));
		ctx.setAttribute(ID, "task.type", type);
		ctx.setAttribute(ID, "task.number", String.valueOf(value - plan));
	}
}
