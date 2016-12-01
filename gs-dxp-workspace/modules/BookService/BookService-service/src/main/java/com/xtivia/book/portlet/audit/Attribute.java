package com.xtivia.book.portlet.audit;

import com.liferay.portal.kernel.util.StringPool;

public class Attribute {

	public Attribute(String name) {
		this(name, StringPool.BLANK, StringPool.BLANK);
	}

	public Attribute(String name, String newValue, String oldValue) {
		_name = name;
		_newValue = newValue;
		_oldValue = oldValue;
	}

	public String getName() {
		return _name;
	}

	public String getNewValue() {
		return _newValue;
	}

	public String getOldValue() {
		return _oldValue;
	}

	private String _name;
	private String _newValue;
	private String _oldValue;
}
