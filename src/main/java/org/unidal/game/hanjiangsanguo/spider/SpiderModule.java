package org.unidal.game.hanjiangsanguo.spider;

import org.unidal.web.mvc.AbstractModule;
import org.unidal.web.mvc.annotation.ModuleMeta;
import org.unidal.web.mvc.annotation.ModulePagesMeta;

@ModuleMeta(name = "spider", defaultInboundAction = "home", defaultTransition = "default", defaultErrorAction = "default")
@ModulePagesMeta({

org.unidal.game.hanjiangsanguo.spider.page.home.Handler.class
})
public class SpiderModule extends AbstractModule {

}
