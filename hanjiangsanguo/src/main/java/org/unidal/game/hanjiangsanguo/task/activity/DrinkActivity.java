package org.unidal.game.hanjiangsanguo.task.activity;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;

public class DrinkActivity extends AbstractTaskActivity {
	public static final String ID = "drink";

	public boolean execute(TaskContext ctx, TaskArguments args) throws Exception {
		String url = m_helper.buildUrl2(ctx, "drink", "go_drink", "&type=1");

		m_helper.doGet(ctx, url);
		
		return true;
	}

}
