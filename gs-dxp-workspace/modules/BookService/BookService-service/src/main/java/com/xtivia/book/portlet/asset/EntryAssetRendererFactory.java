package com.xtivia.book.portlet.asset;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.xtivia.book.portlet.model.Entry;
import com.xtivia.book.portlet.service.EntryLocalServiceUtil;

public class EntryAssetRendererFactory extends BaseAssetRendererFactory<Entry> {

	public static final String CLASS_NAME = Entry.class.getName();
	private static final boolean _LINKABLE = true;
	public static final String TYPE = "entry";

	@Override
	public AssetRenderer<Entry> getAssetRenderer(long classPK, int type) throws PortalException {
		Entry entry = EntryLocalServiceUtil.getEntry(classPK);
		return new EntryAssetRenderer(entry);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public boolean isLinkable() {
		return _LINKABLE;
	}

}
