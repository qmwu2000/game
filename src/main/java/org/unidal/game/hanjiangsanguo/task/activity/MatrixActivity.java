package org.unidal.game.hanjiangsanguo.task.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;
import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.helper.Joiners;
import org.unidal.helper.Splitters;

public class MatrixActivity extends AbstractTaskActivity {
	public static final String ID = "matrix";

	private Map<Integer, String> m_matrixs = new HashMap<Integer, String>();

	private boolean doMatrixUpdate(TaskContext ctx, int mid, String[] ids) throws Exception {
		String list = makeList(mid, ids);
		String url = m_helper.buildUrl2(ctx, "matrix", "update_matrix", String.format("&list=%s&mid=%s", list, mid));

		return m_helper.doGet(ctx, url);
	}

	private boolean doMatrixLevelup(TaskContext ctx, int mid) throws Exception {
		String url = m_helper.buildUrl2(ctx, "matrix", "levelup", String.format("&mid=%s", mid));

		return m_helper.doGet(ctx, url);
	}

	private boolean doMatrixUse(TaskContext ctx, int mid) throws Exception {
		String url = m_helper.buildUrl2(ctx, "matrix", "use_matrix", String.format("&mid=%s", mid));

		return m_helper.doGet(ctx, url);
	}

	public boolean execute(TaskContext ctx, TaskArguments args) throws Exception {
		String op = args.nextString(null);

		ensure(op != null);
		ctx.setDefaultCategory(ID);

		if ("update".equals(op)) {
			int mid = args.nextInt(0);
			String[] params = args.restString();
			String[] ids = mapIds(ctx, params);

			doMatrixUpdate(ctx, mid, ids);
		} else if ("use".equals(op)) {
			int mid = args.nextInt(0);
			String[] params = args.restString();
			String[] ids = mapIds(ctx, params);

			doMatrixUpdate(ctx, mid, ids);
			doMatrixUse(ctx, mid);
		} else if ("levelup".equals(op)) {
			int mid = args.nextInt(0);
			int times = args.nextInt(1);

			for (int i = 0; i < times; i++) {
				doMatrixLevelup(ctx, mid);
			}
		}

		return true;
	}

	@Override
	public void initialize() throws InitializationException {
		super.initialize();

		m_matrixs.put(1, "2,4,6,7,9");
		m_matrixs.put(2, "1,2,3,5,8");
		m_matrixs.put(3, "1,4,5,6,9");
	}

	private String makeList(int mid, String[] ids) {
		String matrix = m_matrixs.get(mid);
		List<String> indexes = Splitters.by(',').noEmptyItem().split(matrix);
		String[] list = new String[9];
		int len = indexes.size();

		for (int i = 0; i < len; i++) {
			int index = Integer.parseInt(indexes.get(i));

			if (i < ids.length) {
				list[index - 1] = ids[i];
			}
		}

		return Joiners.by(',').join(list);
	}

	private String[] mapIds(TaskContext ctx, String[] names) throws Exception {
		String url = m_helper.buildUrl2(ctx, "matrix", "index", null);

		m_helper.doGetWithScript(ctx, url,
		      "var gs=''; for (var i in o.general) gs+=o.general[i].name+':'+o.general[i].id+','; gs;", "list");

		String list = ctx.getAttribute("list");
		Map<String, String> map = Splitters.by(',', ':').trim().split(list);
		String[] ids = new String[names.length];
		int index = 0;

		for (String name : names) {
			String id = map.get(name);

			ids[index++] = id;
		}

		return ids;
	}
}
