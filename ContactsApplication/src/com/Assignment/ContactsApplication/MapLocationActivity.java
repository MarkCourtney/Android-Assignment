package com.Assignment.ContactsApplication;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MapLocationActivity extends MapActivity 
{
	AddContactActivity ada = new AddContactActivity();
	EditContactActivity eca = new EditContactActivity();
	static GeoPoint point;
	MapView mapView;
	MapController mc;
	
	String location;
	String defaultTitle;
	String defaultLocation;
	OverlayItem overlayitem;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maplocation);
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        
        
        mc = mapView.getController(); 
        mc.setZoom(18);
        
        int adaSentCode = ada.getCode();
        int ecaSentCode = eca.getCode();
        
        if(adaSentCode == 25)
        {
        	location = ada.getLocation();
        }
        else if(ecaSentCode == 30)
        {
        	location = eca.getLocation();
        }
        
        ada.setCode();
        eca.setCode();
        
        searchLocation(location);
        mc.setCenter(point);
        
        List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.point);
		ItemOverlay itemizedoverlay = new ItemOverlay(drawable, this);
		
		if(defaultTitle.equals(""))
		{
			overlayitem = new OverlayItem(point, defaultTitle, defaultLocation);
		}
		else
		{
			overlayitem = new OverlayItem(point, defaultTitle, defaultLocation);
		}
		
		itemizedoverlay.addOverlay(overlayitem);
		mapOverlays.add(itemizedoverlay);
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(requestCode);
       
        Bundle bundle = data.getExtras();
    	String[] param1 = {bundle.getString("param1")};
    }

	@Override
	protected boolean isRouteDisplayed() 
	{
		return false;
	}
	
	
	private void searchLocation(String name)
	{
		try 
		{
			Geocoder geo = new Geocoder(this, Locale.getDefault());
			//List<Address> result = geo.getFromLocationName(name, 5);
			
			List<Address> addresses = new Geocoder(this,Locale.getDefault()).getFromLocationName(name, 1);
			
			if (addresses.size() > 0) 
			{
                point = new GeoPoint((int) (addresses.get(0).getLatitude() * 1E6), (int) (addresses.get(0).getLongitude() * 1E6));
                mc.animateTo(point);    
                mapView.invalidate();
            }
			 
			else if ((addresses == null)||(addresses.isEmpty()))
			{
				Toast.makeText(this, "No matches!",  Toast.LENGTH_LONG).show();
			}
			else
			{
				Toast.makeText(this, "Finished!", Toast.LENGTH_LONG).show();
			}
	 	} 
		catch (IOException e) 
		{
			e.printStackTrace();
			
			Toast.makeText(this, "Returning to default location", Toast.LENGTH_LONG).show();	
			point = new GeoPoint(53337459, -6267507);
			defaultTitle = "Default Location";
			defaultLocation = "DIT, Kevin Street";
		}
	}
}