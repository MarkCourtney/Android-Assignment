package com.Assignment.ContactsApplication;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class ItemOverlay extends ItemizedOverlay<OverlayItem>
{
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	Context mContext;
	
	public ItemOverlay(Drawable defaultMarker, Context context) 
	{
		  super(boundCenterBottom(defaultMarker));
		  mContext = context;
		}
	
	public ItemOverlay(Drawable defaultMarker) 
	{
		super(boundCenterBottom(defaultMarker));
	}
	
	public void addOverlay(OverlayItem overlay) 
	{
	    mOverlays.add(overlay);
	    populate();
	}

	protected OverlayItem createItem(int i) 
	{
		return mOverlays.get(i);
	}
	
	public int size() 
	{
		return mOverlays.size();
	}
	
	@Override
	protected boolean onTap(int index) 
	{
		  OverlayItem item = mOverlays.get(index);
		  AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
		  dialog.setTitle(item.getTitle());
		  dialog.setMessage(item.getSnippet());
		  dialog.show();
		  return true;
	}
}
