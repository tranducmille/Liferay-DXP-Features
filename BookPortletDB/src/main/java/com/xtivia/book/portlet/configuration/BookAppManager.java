package com.xtivia.book.portlet.configuration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

import aQute.configurable.Configurable;

@Component(configurationPid = "com.xtivia.book.portlet.configuration.BookConfiguration")
public class BookAppManager {
	/*public boolean getTitle(Map options) {
		return (boolean) options.get(_configuration.title());
	}

	public boolean getAuthor(Map options) {
		return (boolean) options.get(_configuration.author());
	}*/
	
	@Activate
	@Modified	protected void activate(Map<String, Object> properties) {
		_configuration = Configurable.createConfigurable(BookConfiguration.class, properties);
	}

	private volatile BookConfiguration _configuration;
}
