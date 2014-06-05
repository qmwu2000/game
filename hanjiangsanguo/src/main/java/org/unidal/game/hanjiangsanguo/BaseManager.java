package org.unidal.game.hanjiangsanguo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.unidal.helper.Files;

public class BaseManager {

	protected Date getCurrentDay() {
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}
	

	protected boolean isArena() throws FileNotFoundException, IOException {
		File file = new File("target/arean.position");

		if (file != null) {
			file.createNewFile();
		}
		Properties pro = new Properties();
		pro.load(new FileInputStream(file));
		Date date = getCurrentDay();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String key = sdf.format(date);

		if (pro.get(key) == null) {
			String content = Files.forIO().readFrom(file, "utf-8");
			Files.forIO().writeTo(file, content + "\n" + key + "=true");
			return true;
		} else {
			return false;
		}
	}
	

	protected boolean isFirstInDay() throws FileNotFoundException, IOException {
		File file = new File("target/index.position");

		if (file != null) {
			file.createNewFile();
		}
		Properties pro = new Properties();
		pro.load(new FileInputStream(file));
		Date date = getCurrentDay();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String key = sdf.format(date);

		if (pro.get(key) == null) {
			String content = Files.forIO().readFrom(file, "utf-8");
			Files.forIO().writeTo(file, content + "\n" + key + "=true");
			return true;
		} else {
			return false;
		}
	}

	protected boolean isBossTime() {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);

		if ((hour == 12 && minute < 16) || (hour == 20 && minute < 16)) {
			return true;
		} else {
			return false;
		}
	}

	protected boolean isIdleTime() {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);

		if (hour > 3) {
			return true;
		} else {
			return false;
		}
	}

}
