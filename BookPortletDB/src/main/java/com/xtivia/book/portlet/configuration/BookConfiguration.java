package com.xtivia.book.portlet.configuration;
import aQute.bnd.annotation.metatype.Meta;

@Meta.OCD(id = "com.xtivia.book.portlet.configuration.BookConfiguration")
public interface BookConfiguration {
	
	@Meta.AD( deflt = "title", required = true)
	public boolean title();
	
	@Meta.AD(deflt = "author", required = true)
	public boolean author();
	
	@Meta.AD(required = false)
	public boolean[] columns();
}
