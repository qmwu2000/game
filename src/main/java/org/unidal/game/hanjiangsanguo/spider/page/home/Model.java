package org.unidal.game.hanjiangsanguo.spider.page.home;

import org.unidal.game.hanjiangsanguo.spider.SpiderPage;
import org.unidal.web.mvc.ViewModel;

public class Model extends ViewModel<SpiderPage, Action, Context> {
	public Model(Context ctx) {
		super(ctx);
	}

	@Override
	public Action getDefaultAction() {
		return Action.VIEW;
	}
}
