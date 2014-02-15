package org.unidal.game.hanjiangsanguo.build;

import java.util.ArrayList;
import java.util.List;

import org.unidal.game.hanjiangsanguo.task.DefaultTaskContext;
import org.unidal.game.hanjiangsanguo.task.Task;
import org.unidal.game.hanjiangsanguo.task.TaskContext;
import org.unidal.game.hanjiangsanguo.task.TaskDriver;
import org.unidal.game.hanjiangsanguo.task.TaskHelper;
import org.unidal.game.hanjiangsanguo.task.core.ActivityTask;
import org.unidal.game.hanjiangsanguo.task.core.ArenaTask;
import org.unidal.game.hanjiangsanguo.task.core.BusinessTask;
import org.unidal.game.hanjiangsanguo.task.core.DrinkTask;
import org.unidal.game.hanjiangsanguo.task.core.LoginTask;
import org.unidal.game.hanjiangsanguo.task.core.LotteryTask;
import org.unidal.game.hanjiangsanguo.task.core.MapTask;
import org.unidal.game.hanjiangsanguo.task.core.PracticeTask;
import org.unidal.game.hanjiangsanguo.task.core.TaskTask;
import org.unidal.game.hanjiangsanguo.task.core.TavernTask;
import org.unidal.game.hanjiangsanguo.task.core.VipwageTask;
import org.unidal.game.hanjiangsanguo.task.core.WorkshopTask;
import org.unidal.game.hanjiangsanguo.task.core.WorldbossTask;
import org.unidal.lookup.configuration.AbstractResourceConfigurator;
import org.unidal.lookup.configuration.Component;

public class ComponentsConfigurator extends AbstractResourceConfigurator {
	@Override
	public List<Component> defineComponents() {
		List<Component> all = new ArrayList<Component>();

		all.add(C(TaskHelper.class));
		all.add(C(TaskDriver.class));
		all.add(C(TaskContext.class, DefaultTaskContext.class).is(PER_LOOKUP));

		all.addAll(defineTaskComponents());

		// Please keep it as last
		all.addAll(new WebComponentConfigurator().defineComponents());

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

		all.add(C(Task.class, WorkshopTask.ID, WorkshopTask.class) //
		      .req(TaskHelper.class));
		
		all.add(C(Task.class, WorldbossTask.ID, WorldbossTask.class) //
				.req(TaskHelper.class));
		
		all.add(C(Task.class, TaskTask.ID, TaskTask.class) //
				.req(TaskHelper.class));
		
		all.add(C(Task.class, TaskTask.ID, TaskTask.class) //
				.req(TaskHelper.class));

		all.add(C(Task.class, ActivityTask.ID, ActivityTask.class) //
		      .req(TaskHelper.class));

		return all;
	}

	public static void main(String[] args) {
		generatePlexusComponentsXmlFile(new ComponentsConfigurator());
	}
}
