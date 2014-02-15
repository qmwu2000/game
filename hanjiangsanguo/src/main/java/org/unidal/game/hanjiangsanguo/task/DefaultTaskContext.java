package org.unidal.game.hanjiangsanguo.task;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.plexus.logging.LogEnabled;
import org.codehaus.plexus.logging.Logger;

public class DefaultTaskContext implements TaskContext, LogEnabled {
	private Map<String, Map<String, String>> m_attributes = new HashMap<String, Map<String, String>>();

	private Logger m_logger;

	private String m_defaultCategory;

	@Override
	public void enableLogging(Logger logger) {
		m_logger = logger;
	}

	@Override
	public String getAttribute(String name) {
		return getAttribute(m_defaultCategory, name);
	}

	@Override
	public String getAttribute(String category, String name) {
		Map<String, String> map = m_attributes.get(category);
		String value = null;

		if (map != null) {
			value = map.get(name);
		}

		if (value != null) {
			return value;
		} else {
			m_logger.warn(String.format("Attribute(%s:%s) is missing!", category, name));

			return null;
		}
	}

	@Override
	public String getDefaultCategory() {
		return m_defaultCategory;
	}

	@Override
	public int getIntAttribute(String name, int defaultValue) {
		String value = getAttribute(name);

		try {
			if (value != null) {
				return (int) Double.parseDouble(value);
			}
		} catch (Exception e) {
			// ignore it
		}

		return defaultValue;
	}

	@Override
	public void setAttribute(String name, String value) {
		setAttribute(m_defaultCategory, name, value);
	}

	@Override
	public void setAttribute(String category, String name, String value) {
		Map<String, String> map = m_attributes.get(category);

		if (map == null) {
			map = new HashMap<String, String>();
			m_attributes.put(category, map);
		}

		m_logger.info("setAttribute: " + category + "/" + name + " = " + value);
		map.put(name, value);
	}

	@Override
	public void setDefaultCategory(String defaultCategory) {
		m_defaultCategory = defaultCategory;
	}
}
