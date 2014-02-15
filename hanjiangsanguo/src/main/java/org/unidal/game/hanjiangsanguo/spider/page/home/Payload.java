package org.unidal.game.hanjiangsanguo.spider.page.home;

import org.unidal.game.hanjiangsanguo.spider.SpiderPage;
import org.unidal.web.mvc.ActionContext;
import org.unidal.web.mvc.ActionPayload;
import org.unidal.web.mvc.payload.annotation.FieldMeta;

public class Payload implements ActionPayload<SpiderPage, Action> {
   private SpiderPage m_page;

   @FieldMeta("op")
   private Action m_action;

   public void setAction(String action) {
      m_action = Action.getByName(action, Action.VIEW);
   }

   @Override
   public Action getAction() {
      return m_action;
   }

   @Override
   public SpiderPage getPage() {
      return m_page;
   }

   @Override
   public void setPage(String page) {
      m_page = SpiderPage.getByName(page, SpiderPage.HOME);
   }

   @Override
   public void validate(ActionContext<?> ctx) {
      if (m_action == null) {
         m_action = Action.VIEW;
      }
   }
}
