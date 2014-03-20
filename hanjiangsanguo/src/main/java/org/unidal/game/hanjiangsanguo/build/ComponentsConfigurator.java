package org.unidal.game.hanjiangsanguo.build;

import java.util.ArrayList;
import java.util.List;

import org.unidal.game.hanjiangsanguo.task.DefaultTaskActionManager;
import org.unidal.game.hanjiangsanguo.task.DefaultTaskContext;
import org.unidal.game.hanjiangsanguo.task.Task;
import org.unidal.game.hanjiangsanguo.task.TaskAction;
import org.unidal.game.hanjiangsanguo.task.TaskActionManager;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.game.hanjiangsanguo.task.TaskDriver;
import org.unidal.game.hanjiangsanguo.task.TaskHelper;
import org.unidal.game.hanjiangsanguo.task.action.ArenaAction;
import org.unidal.game.hanjiangsanguo.task.action.BusinessAction;
import org.unidal.game.hanjiangsanguo.task.action.CityImpose;
import org.unidal.game.hanjiangsanguo.task.action.CultivateRoll;
import org.unidal.game.hanjiangsanguo.task.action.GoldUse;
import org.unidal.game.hanjiangsanguo.task.action.IslandMission;
import org.unidal.game.hanjiangsanguo.task.action.MapMission;
import org.unidal.game.hanjiangsanguo.task.action.MineOccupy;
import org.unidal.game.hanjiangsanguo.task.action.PracticeLeap;
import org.unidal.game.hanjiangsanguo.task.action.StrengthenUpgrade;
import org.unidal.game.hanjiangsanguo.task.action.TavernTrade;
import org.unidal.game.hanjiangsanguo.task.action.WorldbossBattle;
import org.unidal.game.hanjiangsanguo.task.activity.CityActivity;
import org.unidal.game.hanjiangsanguo.task.activity.CultivateActivity;
import org.unidal.game.hanjiangsanguo.task.activity.GeneralActivity;
import org.unidal.game.hanjiangsanguo.task.activity.GiftActivity;
import org.unidal.game.hanjiangsanguo.task.activity.LoginActivity;
import org.unidal.game.hanjiangsanguo.task.activity.LotteryActivity;
import org.unidal.game.hanjiangsanguo.task.activity.MapActivity;
import org.unidal.game.hanjiangsanguo.task.activity.MatrixActivity;
import org.unidal.game.hanjiangsanguo.task.activity.MusterActivity;
import org.unidal.game.hanjiangsanguo.task.activity.PracticeActivity;
import org.unidal.game.hanjiangsanguo.task.activity.StrengthenActivity;
import org.unidal.game.hanjiangsanguo.task.activity.TaskActivity;
import org.unidal.game.hanjiangsanguo.task.activity.TowerActivity;
import org.unidal.game.hanjiangsanguo.task.core.ActivityTask;
import org.unidal.game.hanjiangsanguo.task.core.ArenaTask;
import org.unidal.game.hanjiangsanguo.task.core.BusinessTask;
import org.unidal.game.hanjiangsanguo.task.core.DiceTask;
import org.unidal.game.hanjiangsanguo.task.core.DrinkTask;
import org.unidal.game.hanjiangsanguo.task.core.GeneralTask;
import org.unidal.game.hanjiangsanguo.task.core.LoginTask;
import org.unidal.game.hanjiangsanguo.task.core.LotteryTask;
import org.unidal.game.hanjiangsanguo.task.core.MapActionTask;
import org.unidal.game.hanjiangsanguo.task.core.MapTask;
import org.unidal.game.hanjiangsanguo.task.core.PracticeTask;
import org.unidal.game.hanjiangsanguo.task.core.RegisterTask;
import org.unidal.game.hanjiangsanguo.task.core.TaskTask;
import org.unidal.game.hanjiangsanguo.task.core.TavernTask;
import org.unidal.game.hanjiangsanguo.task.core.VipwageTask;
import org.unidal.game.hanjiangsanguo.task.core.WorkshopTask;
import org.unidal.game.hanjiangsanguo.task.core.WorldbossTask;
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

		all.addAll(defineTaskComponents());
		all.addAll(defineTaskActionComponents());
		all.addAll(defineTaskActivityComponents());

		// Please keep it as last
		all.addAll(new WebComponentConfigurator().defineComponents());

		return all;
	}

	private List<Component> defineTaskActionComponents() {
		List<Component> all = new ArrayList<Component>();

		all.add(C(TaskActionManager.class, DefaultTaskActionManager.class) //
		      .req(TaskHelper.class));

		all.add(C(TaskAction.class, PracticeLeap.ID, PracticeLeap.class));
		all.add(C(TaskAction.class, CityImpose.ID, CityImpose.class));
		all.add(C(TaskAction.class, MineOccupy.ID, MineOccupy.class));
		all.add(C(TaskAction.class, BusinessAction.ID, BusinessAction.class));
		all.add(C(TaskAction.class, GoldUse.ID, GoldUse.class));
		all.add(C(TaskAction.class, MapMission.ID, MapMission.class));
		all.add(C(TaskAction.class, CultivateRoll.ID, CultivateRoll.class));
		all.add(C(TaskAction.class, StrengthenUpgrade.ID, StrengthenUpgrade.class));
		all.add(C(TaskAction.class, IslandMission.ID, IslandMission.class));
		all.add(C(TaskAction.class, ArenaAction.ID, ArenaAction.class));
		all.add(C(TaskAction.class, TavernTrade.ID, TavernTrade.class));
		all.add(C(TaskAction.class, WorldbossBattle.ID, WorldbossBattle.class));

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

		return all;
	}

	private List<Component> defineTaskComponents() {
		List<Component> all = new ArrayList<Component>();

		all.add(C(Task.class, LoginTask.ID, LoginTask.class) //
		      .req(TaskHelper.class));

		all.add(C(Task.class, VipwageTask.ID, VipwageTask.class) //
		      .req(TaskHelper.class));

		all.add(C(Task.class, LotteryTask.ID, LotteryTask.class) //
		      .req(TaskHelper.class));

		all.add(C(Task.class, DrinkTask.ID, DrinkTask.class) //
		      .req(TaskHelper.class));

		all.add(C(Task.class, BusinessTask.ID, BusinessTask.class) //
		      .req(TaskHelper.class));

		all.add(C(Task.class, PracticeTask.ID, PracticeTask.class) //
		      .req(TaskHelper.class));

		all.add(C(Task.class, ArenaTask.ID, ArenaTask.class) //
		      .req(TaskHelper.class));

		all.add(C(Task.class, TavernTask.ID, TavernTask.class) //
		      .req(TaskHelper.class));

		all.add(C(Task.class, MapTask.ID, MapTask.class) //
		      .req(TaskHelper.class));

		all.add(C(Task.class, MapActionTask.ID, MapActionTask.class) //
		      .req(TaskHelper.class));

		all.add(C(Task.class, WorkshopTask.ID, WorkshopTask.class) //
		      .req(TaskHelper.class));

		all.add(C(Task.class, WorldbossTask.ID, WorldbossTask.class) //
		      .req(TaskHelper.class));

		all.add(C(Task.class, GeneralTask.ID, GeneralTask.class) //
		      .req(TaskHelper.class));

		all.add(C(Task.class, DiceTask.ID, DiceTask.class) //
		      .req(TaskHelper.class));

		all.add(C(Task.class, TaskTask.ID, TaskTask.class) //
		      .req(TaskHelper.class, TaskActionManager.class));

		all.add(C(Task.class, ActivityTask.ID, ActivityTask.class) //
		      .req(TaskHelper.class));

		all.add(C(Task.class, RegisterTask.ID, RegisterTask.class) //
		      .req(TaskHelper.class));

		return all;
	}
}
