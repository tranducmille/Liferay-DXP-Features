package com.xtivia.portlet.book.configuration;
import aQute.bnd.annotation.metatype.Meta;

@Meta.OCD(id = "com.xtivia.portlet.book.configuration.BookConfiguration")
public interface BookConfiguration {
	@Meta.AD(deflt = "title|author", required = false)
	public String displayOptions();

	@Meta.AD(required = false)
	public int itemsPerPage();
}
