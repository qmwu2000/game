package org.unidal.game.hanjiangsanguo.task.action;

import org.unidal.game.hanjiangsanguo.task.TaskContext;

public class CultivateRoll extends AbstractTaskAction {
	public static final String ID = "cultivate.roll";

	@Override
	public int getAvailableTimes(TaskContext ctx) throws Exception {
		return 0;
	}

	@Override
	public void doAction(TaskContext ctx) throws Exception {
		// not supported yet
		/**
		 * http://s97.game.hanjiangsanguo.com/index.php?v=2013091306&c=cultivate&&m=index&&token_uid=3912248&token=_LoD9T0PLjdw3rJy4mSYRw&channel=150&lang=zh-cn&rand=13924671206342
		 * http://s97.game.hanjiangsanguo.com/index.php?v=2013091306&c=cultivate&&m=roll&&token_uid=3912248&token=_LoD9T0PLjdw3rJy4mSYRw&channel=150&lang=zh-cn&rand=139246718498240&gid=?&mode=2
		 * http://s97.game.hanjiangsanguo.com/index.php?v=2013091306&c=cultivate&&m=giveup&&token_uid=3912248&token=_LoD9T0PLjdw3rJy4mSYRw&channel=150&lang=zh-cn&rand=139246715549572&gid=?
		 * http://s97.game.hanjiangsanguo.com/index.php?v=2013091306&c=cultivate&&m=save&&token_uid=3912248&token=_LoD9T0PLjdw3rJy4mSYRw&channel=150&lang=zh-cn&rand=139246715549572&gid=?
		 */
	}
	
	@Override
   public String getType() {
	   return "7";
   }
}
