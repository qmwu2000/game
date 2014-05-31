package org.unidal.game.hanjiangsanguo.spider.page.home;

import org.unidal.game.hanjiangsanguo.spider.SpiderPage;
import org.unidal.web.mvc.view.BaseJspViewer;

public class JspViewer extends BaseJspViewer<SpiderPage, Action, Context, Model> {
	@Override
	protected String getJspFilePath(Context ctx, Model model) {
		Action action = model.getAction();

		switch (action) {
		case VIEW:
			return JspFile.VIEW.getPath();
		}

		throw new RuntimeException("Unknown action: " + action);
	}
}
