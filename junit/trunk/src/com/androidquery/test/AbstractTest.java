package com.androidquery.test;

import com.androidquery.AQuery;
import com.androidquery.util.AQUtility;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

public abstract class AbstractTest<T extends Activity> extends ActivityInstrumentationTestCase2<T> {

	protected AQuery aq;
	
	public AbstractTest(Class cls){
		super("com.pekca.vikispot.android", cls);
		AQUtility.setDebug(true);
	}

	protected void setUp() throws Exception {
        super.setUp();
        aq = new AQuery(getActivity());
    }
	
	protected void log(Object msg, Object msg2){
		AQUtility.debug(msg, msg2);
	}
	
	protected void log(Object msg){
		AQUtility.debug(msg);
	}
	
	protected void waitAsync(){
		
		AQUtility.debugWait();
		
	}
	
	protected void waitSec(){
		
		synchronized(this){
			try {
				wait(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	protected void done(){
		
		AQUtility.debugNotify();
		
	}
	
}
