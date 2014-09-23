package org.unidal.game.hanjiangsanguo.build;

import java.util.ArrayList;
import java.util.List;

import org.unidal.game.hanjiangsanguo.AccountManager;
import org.unidal.game.hanjiangsanguo.BossManager;
import org.unidal.game.hanjiangsanguo.account.DouDouMainAccount;
import org.unidal.game.hanjiangsanguo.account.HanfengMainAccount;
import org.unidal.game.hanjiangsanguo.account.HuaiyiMainAccount;
import org.unidal.game.hanjiangsanguo.account.KeJiYaoMainAccount;
import org.unidal.game.hanjiangsanguo.account.LaFengAccount;
import org.unidal.game.hanjiangsanguo.account.XiaoHaoAccount;
import org.unidal.game.hanjiangsanguo.account.YilianMainAccount;
import org.unidal.game.hanjiangsanguo.router.DefaultRouter;
import org.unidal.game.hanjiangsanguo.task.DefaultTaskContext;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.game.hanjiangsanguo.task.TaskDriver;
import org.unidal.game.hanjiangsanguo.task.TaskHelper;
import org.unidal.game.hanjiangsanguo.task.activity.ActivityActivity;
import org.unidal.game.hanjiangsanguo.task.activity.ArenaActivity;
import org.unidal.game.hanjiangsanguo.task.activity.BanquetActivity;
import org.unidal.game.hanjiangsanguo.task.activity.BanquetXiaohaoActivity;
import org.unidal.game.hanjiangsanguo.task.activity.BeautyActivity;
import org.unidal.game.hanjiangsanguo.task.activity.CityActivity;
import org.unidal.game.hanjiangsanguo.task.activity.CountryActivity;
import org.unidal.game.hanjiangsanguo.task.activity.CountryBossActivity;
import org.unidal.game.hanjiangsanguo.task.activity.CountryMineActivity;
import org.unidal.game.hanjiangsanguo.task.activity.CultivateActivity;
import org.unidal.game.hanjiangsanguo.task.activity.DrinkActivity;
import org.unidal.game.hanjiangsanguo.task.activity.GemActivity;
import org.unidal.game.hanjiangsanguo.task.activity.GeneralActivity;
import org.unidal.game.hanjiangsanguo.task.activity.GiftActivity;
import org.unidal.game.hanjiangsanguo.task.activity.HeavenActivity;
import org.unidal.game.hanjiangsanguo.task.activity.HeroActivity;
import org.unidal.game.hanjiangsanguo.task.activity.JiangActivity;
import org.unidal.game.hanjiangsanguo.task.activity.LoginActivity;
import org.unidal.game.hanjiangsanguo.task.activity.LotteryActivity;
import org.unidal.game.hanjiangsanguo.task.activity.MapActivity;
import org.unidal.game.hanjiangsanguo.task.activity.MatrixActivity;
import org.unidal.game.hanjiangsanguo.task.activity.MineActivity;
import org.unidal.game.hanjiangsanguo.task.activity.MusterActivity;
import org.unidal.game.hanjiangsanguo.task.activity.PracticeActivity;
import org.unidal.game.hanjiangsanguo.task.activity.RegisterActivity;
import org.unidal.game.hanjiangsanguo.task.activity.SoulActivity;
import org.unidal.game.hanjiangsanguo.task.activity.SoulEquipActivity;
import org.unidal.game.hanjiangsanguo.task.activity.StrengthenActivity;
import org.unidal.game.hanjiangsanguo.task.activity.TaskActivity;
import org.unidal.game.hanjiangsanguo.task.activity.TowerActivity;
import org.unidal.game.hanjiangsanguo.task.activity.TradeActivity;
import org.unidal.game.hanjiangsanguo.task.activity.WorldbossActivity;
import org.unidal.game.hanjiangsanguo.task.activity.ZuqiuActivity;
import org.unidal.lookup.configuration.AbstractResourceConfigurator;
import org.unidal.lookup.configuration.Component;

public class ComponentsConfigurator extends AbstractResourceConfigurator {
	public static void main(String[] args) {
		generatePlexusComponentsXmlFile(new ComponentsConfigurator());
	}

	@Override
	public List<Component> defineComponents() {
		List<Component> all = new ArrayList<Component>();

		all.add(C(TaskHelper.class));
		all.add(C(TaskDriver.class));
		all.add(C(TaskContext.class, DefaultTaskContext.class).is(PER_LOOKUP));

		all.add(C(HanfengMainAccount.class).req(TaskDriver.class));
		all.add(C(DouDouMainAccount.class).req(TaskDriver.class));
		all.add(C(HuaiyiMainAccount.class).req(TaskDriver.class));
		all.add(C(YilianMainAccount.class).req(TaskDriver.class));
		all.add(C(KeJiYaoMainAccount.class).req(TaskDriver.class));
		all.add(C(XiaoHaoAccount.class).req(TaskDriver.class));
		all.add(C(LaFengAccount.class).req(TaskDriver.class));

		all.add(C(AccountManager.class).req(HanfengMainAccount.class, DouDouMainAccount.class, HuaiyiMainAccount.class,
		      YilianMainAccount.class, KeJiYaoMainAccount.class).req(XiaoHaoAccount.class, TaskDriver.class,
		      LaFengAccount.class));
		all.add(C(BossManager.class).req(HanfengMainAccount.class, DouDouMainAccount.class, HuaiyiMainAccount.class,
		      YilianMainAccount.class, KeJiYaoMainAccount.class, LaFengAccount.class));
		all.addAll(defineTaskActivityComponents());

		all.add(C(DefaultRouter.class));

		// Please keep it as last
		all.addAll(new WebComponentConfigurator().defineComponents());

		return all;
	}

	private List<Component> defineTaskActivityComponents() {
		List<Component> all = new ArrayList<Component>();

		all.add(C(TaskActivity.class, LoginActivity.ID, LoginActivity.class));
		all.add(C(TaskActivity.class, MusterActivity.ID, MusterActivity.class));
		all.add(C(TaskActivity.class, MatrixActivity.ID, MatrixActivity.class));
		all.add(C(TaskActivity.class, MapActivity.ID, MapActivity.class));
		all.add(C(TaskActivity.class, PracticeActivity.ID, PracticeActivity.class));
		all.add(C(TaskActivity.class, CultivateActivity.ID, CultivateActivity.class));
		all.add(C(TaskActivity.class, GeneralActivity.ID, GeneralActivity.class));
		all.add(C(TaskActivity.class, GiftActivity.ID, GiftActivity.class));
		all.add(C(TaskActivity.class, StrengthenActivity.ID, StrengthenActivity.class));
		all.add(C(TaskActivity.class, CityActivity.ID, CityActivity.class));
		all.add(C(TaskActivity.class, LotteryActivity.ID, LotteryActivity.class));
		all.add(C(TaskActivity.class, TowerActivity.ID, TowerActivity.class));
		all.add(C(TaskActivity.class, TradeActivity.ID, TradeActivity.class));
		all.add(C(TaskActivity.class, CountryActivity.ID, CountryActivity.class));
		all.add(C(TaskActivity.class, ActivityActivity.ID, ActivityActivity.class));
		all.add(C(TaskActivity.class, RegisterActivity.ID, RegisterActivity.class));
		all.add(C(TaskActivity.class, BanquetActivity.ID, BanquetActivity.class));
		all.add(C(TaskActivity.class, BanquetXiaohaoActivity.ID, BanquetXiaohaoActivity.class));
		all.add(C(TaskActivity.class, MineActivity.ID, MineActivity.class));
		all.add(C(TaskActivity.class, CountryMineActivity.ID, CountryMineActivity.class));
		all.add(C(TaskActivity.class, ArenaActivity.ID, ArenaActivity.class));
		all.add(C(TaskActivity.class, DrinkActivity.ID, DrinkActivity.class));
		all.add(C(TaskActivity.class, ZuqiuActivity.ID, ZuqiuActivity.class));
		all.add(C(TaskActivity.class, CountryBossActivity.ID, CountryBossActivity.class));
		all.add(C(TaskActivity.class, WorldbossActivity.ID, WorldbossActivity.class));
		all.add(C(TaskActivity.class, SoulActivity.ID, SoulActivity.class));
		all.add(C(TaskActivity.class, GemActivity.ID, GemActivity.class));
		all.add(C(TaskActivity.class, HeavenActivity.ID, HeavenActivity.class));
		all.add(C(TaskActivity.class, HeroActivity.ID, HeroActivity.class));
		all.add(C(TaskActivity.class, JiangActivity.ID, JiangActivity.class));
		all.add(C(TaskActivity.class, SoulEquipActivity.ID, SoulEquipActivity.class));
		all.add(C(TaskActivity.class, BeautyActivity.ID, BeautyActivity.class));

		return all;
	}

}
