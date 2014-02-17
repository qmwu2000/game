package org.unidal.game.hanjiangsanguo.task.action;

import org.unidal.game.hanjiangsanguo.task.TaskContext;

public class StrengthenUpgrade extends AbstractTaskAction {
	public static final String ID = "strengthen.strengthen_start";

	@Override
	public int getAvailableTimes(TaskContext ctx) throws Exception {
		return 0;
	}

	@Override
	public void doAction(TaskContext ctx) throws Exception {
		// not supported yet
		/**
		 * http://s97.game.hanjiangsanguo.com/index.php?v=2013091306&c=strengthen&&m=index&&token_uid=3912248&token=_LoD9T0PLjdw3rJy4mSYRw&channel=150&lang=zh-cn&rand=139246743006788
		 * http://s97.game.hanjiangsanguo.com/index.php?v=2013091306&c=strengthen&&m=strengthen_start&&token_uid=3912248&token=_LoD9T0PLjdw3rJy4mSYRw&channel=150&lang=zh-cn&rand=139246743006788&id=?&ratetype=0
		 */
	}

	@Override
   public String getType() {
	   return "8";
   }
}
