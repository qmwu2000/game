package org.unidal.game.hanjiangsanguo.task.activity;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;

public class JiangActivity extends AbstractTaskActivity {
	public static final String ID = "jiang";

	public boolean execute(TaskContext ctx, TaskArguments args) throws Exception {
		String levelId = args.nextString(null);
		String personId = args.nextString(null);
		int number = check(ctx, levelId);

		for (int i = 0; i < number; i++) {
			action(ctx, levelId, personId);
		}
		return true;
	}

	private void action(TaskContext ctx, String levelId, String personId) throws Exception {
		String url = m_helper.buildUrl2(ctx, "generaltask", "action", "&id=" + levelId + "&gid=" + personId);

		m_helper.doGet(ctx, url);
	}

	private int check(TaskContext ctx, String mainPerson) throws Exception {
		String url = m_helper.buildUrl2(ctx, "generaltask", "index", null);

		m_helper.doGet(ctx, url, "number");

		return ctx.getIntAttribute("number", -1);
	}

}