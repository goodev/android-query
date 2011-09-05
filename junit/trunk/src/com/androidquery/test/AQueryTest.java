package com.androidquery.test;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.AQUtility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.View;
import android.widget.TextView;


public class AQueryTest extends ActivityInstrumentationTestCase2<AQueryTestActivity> {

	private AQuery aq;
	private String url;
	private Object result;
	private AjaxStatus status;
	
	public AQueryTest() {		
		super("com.androidquery.test", AQueryTestActivity.class);
		AQUtility.setDebug(true);
    }
	
	@Override
    protected void setUp() throws Exception {
        super.setUp();
        aq = new AQuery(getActivity());
    }
	
	private void log(Object msg, Object msg2){
		AQUtility.debug(msg, msg2);
	}
	
	private void log(Object msg){
		AQUtility.debug(msg);
	}
	
	private void waitDone(){
		
		synchronized(aq) {
			try {
				aq.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void done(String url, Object result, AjaxStatus status){
		
		this.url = url;
		this.result = result;
		this.status = status;

		log("done", result);
		
		done();
	}
	
	private void done(){
		
		synchronized (aq) {
			aq.notifyAll();
		}
		
	}
	
	//@UiThreadTest
	public void testAjax1() {
		
		String url = "http://www.google.com/uds/GnewsSearch?q=Obama&v=1.0";
        
        aq.ajax(url, JSONObject.class, this, "jsonCb");
        
        waitDone();
        
        JSONObject jo = (JSONObject) result;
        
        assertNotNull(jo);       
        assertNotNull(jo.opt("responseData"));
        
    }
	
	public void jsonCb(String url, JSONObject jo, AjaxStatus status){
				
		done(url, jo, status);
	}
	
	
}
