package com.xtivia.book.portlet.audit;

import java.util.ArrayList;
import java.util.List;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.util.Validator;

public class AttributesBuilder {
	public AttributesBuilder(Object newBean, Object oldBean) {
		_newBean = newBean;
		_oldBean = oldBean;
	}

	public void add(String name) {
		String newValue = String.valueOf(BeanPropertiesUtil.getObject(_newBean, name));
		String oldValue = String.valueOf(BeanPropertiesUtil.getObject(_oldBean, name));

		if (!Validator.equals(newValue, oldValue)) {
			Attribute attribute = new Attribute(name, newValue, oldValue);

			_attributes.add(attribute);
		}
	}

	public List<Attribute> getAttributes() {
		return _attributes;
	}

	private List<Attribute> _attributes = new ArrayList<Attribute>();
	private Object _newBean;
	private Object _oldBean;
}
