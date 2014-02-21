package com.Assignment.ContactsApplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class ContactsApplicationActivity extends Activity		// Splash Screen
{
    
	protected int splashTime = 3000;	// 3 seconds
	
	protected Thread splashThread;		// Thread that splash screen runs on
	
	public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        final ContactsApplicationActivity SplashScreen = this; 

        // thread for displaying the SplashScreen
        splashThread = new Thread() 
        {
            public void run() 
            {
                try 
                {
                    synchronized(this)
                    {
                    	wait(splashTime);
                    }
                } 
                catch(InterruptedException e) 
                {
                	
                }
                finally 
                {
                    finish();

                    //start a new activity
                    Intent i = new Intent();
        	    	
                    i.setClass(SplashScreen, ContactsListActivity.class);
                    startActivity(i);
                }
            }
        };
        splashThread.start();
    }

    //Function that will handle the touch
    @Override
    public boolean onTouchEvent(MotionEvent event) 
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN) 
        {
            synchronized(splashThread)
            {
                    splashThread.notifyAll();
            }
        }
        return true;
    }
    
}