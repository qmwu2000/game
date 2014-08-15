package org.unidal.game.hanjiangsanguo.task.activity;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.helper.Splitters;

public class ArenaActivity extends AbstractTaskActivity {
	public static final String ID = "arena";

	public boolean execute(TaskContext ctx, TaskArguments args) throws Exception {
		String op = args.nextString(null);

		ensure(op != null);
		ctx.setDefaultCategory(ID);

		if (op.equals("rank")) {
			String bet = args.nextString("false");

			handleRank(ctx, Boolean.parseBoolean(bet));
		} else if (op.equals("bet")) {
			String uid = ctx.getAttribute("arena", "uid");
			String server = ctx.getAttribute("arena", "server");

			doBet(ctx, uid, server);
		}

		return true;
	}

	private void handleRank(TaskContext ctx, boolean bet) throws Exception, IOException {
		int pages = getPages(ctx);
		Map<String, Integer> ranks = new TreeMap<String, Integer>();
		Map<String, Integer> oldRanks = loadRanks(getRankFile(true));
		Map<Integer, List<String>> map = new TreeMap<Integer, List<String>>(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o2 - o1;
			}
		});

		for (int page = 1; page <= pages; page++) {
			getRanks(ctx, page, ranks);
		}

		for (Map.Entry<String, Integer> e : ranks.entrySet()) {
			String key = e.getKey();
			Integer rank = e.getValue();
			Integer oldRank = oldRanks.get(key);

			if (oldRank != null) {
				int delta = oldRank - rank;
				List<String> list = map.get(delta);

				if (list == null) {
					list = new ArrayList<String>();
					map.put(delta, list);
				}

				list.add(key + "|" + rank);
			}
		}

		saveRanks(getRankFile(false), ranks);
		setProperties(ctx, map);
		printMap(map);
	}

	private void setProperties(TaskContext ctx, Map<Integer, List<String>> map) {
		for (Map.Entry<Integer, List<String>> e : map.entrySet()) {
			if (e.getKey() > 0) {
				String str = e.getValue().get(0);
				List<String> parts = Splitters.by('|').split(str);
				String name = parts.get(0);
				String uid = parts.get(1);
				String server = parts.get(2);
				String rank = parts.get(3);

				ctx.setAttribute("name", name);
				ctx.setAttribute("uid", uid);
				ctx.setAttribute("server", server);
				ctx.setAttribute("rank", rank);
			}

			break;
		}
	}

	private File getRankFile(boolean last) {
		SimpleDateFormat format = new SimpleDateFormat("MMdd");
		Calendar cal = Calendar.getInstance();

		if (last) {
			cal.add(Calendar.DATE, -1);
		}

		String date = format.format(cal.getTime());

		return new File("target/arena/rank." + date);
	}

	private int getPages(TaskContext ctx) throws Exception {
		String url = m_helper.buildUrl2(ctx, "worldarena", "arena", null);

		if (m_helper.doGet(ctx, url, "rank.pages")) {
			return ctx.getIntAttribute("rank.pages", 0);
		} else {
			return 0;
		}
	}

	private void doBet(TaskContext ctx, String uid, String server) throws Exception {
		String url = m_helper
		      .buildUrl2(ctx, "worldarena", "betting", String.format("&touid=%s&serverid=%s", uid, server));

		m_helper.doGet(ctx, url, "rank.pages");
	}

	private void getRanks(TaskContext ctx, int page, Map<String, Integer> ranks) throws Exception {
		String url = m_helper.buildUrl2(ctx, "worldarena", "get_rank_list", String.format("&page=%s", page));

		m_helper.doGetWithScript(ctx, url, "var gs=''; for (var i in o.rank.list) gs+=o.rank.list[i].rank+':'+"
		      + "o.rank.list[i].nickname+'|'+o.rank.list[i].uid+'|'+o.rank.list[i].serverid+','; gs;", "list");

		String list = ctx.getAttribute("list");
		Map<String, String> map = Splitters.by(',', ':').split(list);

		for (Map.Entry<String, String> e : map.entrySet()) {
			ranks.put(e.getValue(), Integer.valueOf(e.getKey()));
		}
	}

	private Map<String, Integer> loadRanks(File file) throws IOException {
		Map<String, Integer> ranks = new TreeMap<String, Integer>();

		if (file.exists()) {
			Properties properties = new Properties();

			properties.load(new FileReader(file));

			for (Map.Entry<Object, Object> e : properties.entrySet()) {
				ranks.put(e.getKey().toString(), Integer.valueOf(e.getValue().toString()));
			}
		}

		return ranks;
	}

	private void saveRanks(File file, Map<String, Integer> ranks) throws IOException {
		Properties properties = new Properties();

		for (Map.Entry<String, Integer> e : ranks.entrySet()) {
			properties.put(e.getKey(), e.getValue().toString());
		}

		file.getParentFile().mkdirs();
		properties.store(new FileWriter(file), "");
	}

	private void printMap(Map<Integer, List<String>> map) {
		for (Map.Entry<Integer, List<String>> e : map.entrySet()) {
			if (e.getKey() > 0) {
				System.err.println(e.getKey() + ": " + e.getValue());
			}
		}
	}
}
