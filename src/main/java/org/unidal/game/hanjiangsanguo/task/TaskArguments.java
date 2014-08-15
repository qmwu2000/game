package org.unidal.game.hanjiangsanguo.task;

public class TaskArguments {
	private String[] m_args;

	private int m_index;

	public TaskArguments(String[] args) {
		m_args = args;
	}

	public String nextString(String defaultValue) {
		return getString(m_index++, defaultValue);
	}

	public int nextInt(int defaultValue) {
		return getInt(m_index++, defaultValue);
	}

	public String getString(int index, String defaultValue) {
		if (index < m_args.length) {
			return m_args[index];
		} else {
			return defaultValue;
		}
	}

	public int getInt(int index, int defaultValue) {
		String value = getString(index, null);

		try {
			if (value != null) {
				return (int) Double.parseDouble(value);
			}
		} catch (Exception e) {
			// ignore it
		}

		return defaultValue;
	}

	public String[] restString() {
		String[] rest = new String[m_args.length - m_index];

		System.arraycopy(m_args, m_index, rest, 0, rest.length);
		return rest;
	}
}
