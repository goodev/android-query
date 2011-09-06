package com.androidquery.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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


public class AQueryAsyncTest extends AbstractTest<AQueryTestActivity> {

	
	private String url;
	private Object result;
	private AjaxStatus status;
	
	public AQueryAsyncTest() {		
		super(AQueryTestActivity.class);
    }

	
	private void done(String url, Object result, AjaxStatus status){
		
		this.url = url;
		this.result = result;
		this.status = status;

		log("done", result);
		
		done();
	}
	

	

	
	//Test: public <K> T ajax(AjaxCallback<K> callback)
	public void testAjax1() {
		
		String url = "http://www.google.com/uds/GnewsSearch?q=Obama&v=1.0";
        
		AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>(){
			
			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				
				done(url, jo, status);
				
			}
			
		};
		
		cb.url(url).type(JSONObject.class);		
        aq.ajax(cb);
        
        waitAsync();
        
        JSONObject jo = (JSONObject) result;
        
        assertNotNull(jo);       
        assertNotNull(jo.opt("responseData"));
        
    }
	
	//Test: public <K> T ajax(String url, Class<K> type, AjaxCallback<K> callback)
	public void testAjax2() {
		
		String url = "http://www.google.com/uds/GnewsSearch?q=Obama&v=1.0";
        
		AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>(){
			
			@Override
			public void callback(String url, JSONObject jo, AjaxStatus status) {
				
				done(url, jo, status);
				
			}
			
		};
		
			
        aq.ajax(url, JSONObject.class, cb);
        
        waitAsync();
        
        JSONObject jo = (JSONObject) result;
        
        assertNotNull(jo);       
        assertNotNull(jo.opt("responseData"));
        
    }
	
	//Test: public <K> T ajax(String url, Class<K> type, Object handler, String callback)
	public void testAjax3() {
		
		String url = "http://www.google.com/uds/GnewsSearch?q=Obama&v=1.0";
        
        aq.ajax(url, JSONObject.class, this, "jsonCb");
        
        waitAsync();
        
        JSONObject jo = (JSONObject) result;
        
        assertNotNull(jo);       
        assertNotNull(jo.opt("responseData"));
        
    }
	
	//Test: <K> T ajax(String url, Map<String, Object> params, Class<K> type, AjaxCallback<K> callback)
	public void testAjax4(){
		
        String url = "http://search.twitter.com/search.json";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("q", "androidquery");
		
        aq.ajax(url, params, JSONObject.class, new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                   
            	done(url, jo, status);
               
            }
        });
		
        waitAsync();
		
        JSONObject jo = (JSONObject) result;
        assertNotNull(jo);       
        assertNotNull(jo.opt("results"));
        
	}
	
	//Test: public <K> T ajax(String url, Map<String, Object> params, Class<K> type, Object handler, String callback)
	public void testAjax5(){
		
        String url = "http://search.twitter.com/search.json";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("q", "androidquery");
		
        aq.ajax(url, params, JSONObject.class, this, "jsonCb");
		
        waitAsync();
		
        JSONObject jo = (JSONObject) result;
        assertNotNull(jo);       
        assertNotNull(jo.opt("results"));
        
	}
	
	
	public void jsonCb(String url, JSONObject jo, AjaxStatus status){
				
		done(url, jo, status);
	}
	
	
}
