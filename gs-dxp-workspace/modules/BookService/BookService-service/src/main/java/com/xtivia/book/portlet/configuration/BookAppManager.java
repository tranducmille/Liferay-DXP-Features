package com.xtivia.book.portlet.configuration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

import aQute.bnd.annotation.metatype.Configurable;

@Component(configurationPid = "com.xtivia.book.portlet.configuration.BookConfiguration")
public class BookAppManager {
	public String getDisplayOptions(Map options) {
		return (String) options.get(_configuration.displayOptions());
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		System.out.println("BookAppManager Activated");
		_configuration = Configurable.createConfigurable(BookConfiguration.class, properties);
	}

	private volatile BookConfiguration _configuration;
}
