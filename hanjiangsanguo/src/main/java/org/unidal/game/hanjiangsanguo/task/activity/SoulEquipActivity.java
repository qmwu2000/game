package org.unidal.game.hanjiangsanguo.task.activity;

import org.unidal.game.hanjiangsanguo.task.TaskArguments;
import org.unidal.game.hanjiangsanguo.task.TaskContext;

public class SoulEquipActivity extends AbstractTaskActivity {
	public static final String ID = "SoulEquip";

	public boolean execute(TaskContext ctx, TaskArguments args) throws Exception {
		String gidinput = args.nextString(null);
		String sidinput = args.nextString(null);

		if (gidinput == null || sidinput == null) {
			return true;
		}
		String[] gids = gidinput.split(",");
		String[] sids = sidinput.split(",");

		unequip(ctx, gids, sids);
		equip(ctx, gids, sids);

		return true;
	}

	private void unequip(TaskContext ctx, String gids[], String[] sids) throws Exception {
		for (String gid : gids) {
			for (String sid : sids) {
				try {
	            String url = m_helper.buildUrl2(ctx, "soul", "unequip", "&gid=" + gid + "&sid=" + sid + "&site=2");

	            m_helper.doGet(ctx, url);
            } catch (Exception e) {
	            e.printStackTrace();
            }
			}
		}
	}

	private void equip(TaskContext ctx, String gids[], String[] sids) throws Exception {
		int length = gids.length;

		for (int i = 0; i < length; i++) {
			try {
	         String gid = gids[i];
	         String sid = sids[i];
	         String url = m_helper.buildUrl2(ctx, "soul", "equip", "&gid=" + gid + "&sid=" + sid + "&site=2");

	         m_helper.doGet(ctx, url);
         } catch (Exception e) {
	         e.printStackTrace();
         }

		}
	}

}