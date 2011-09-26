package com.androidquery.test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.http.client.utils.URLEncodedUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.service.MarketService;
import com.androidquery.util.AQUtility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.View;
import android.widget.TextView;


public class AQueryServiceTest extends AbstractTest<AQueryTestActivity> {

	
	private String url;
	private Object result;
	private AjaxStatus status;
	
	public AQueryServiceTest() {		
		super(AQueryTestActivity.class);
    }

	
	private void done(String url, Object result, AjaxStatus status){
		
		this.url = url;
		this.result = result;
		this.status = status;

		log("done", result);
		
		done();
	}
	

	
	public void testMarketSubmit() throws IOException{
		
		AQUtility.debug("start");
		
		String gurl = "http://192.168.107.130/test.htm";
		
		byte[] data = IOUtility.openBytes(gurl);
		
		String html = new String(data, "UTF-8");
		
		String url = "http://192.168.107.130/api/market?app=com.androidquery&locale=zh-TW";
		
		AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>();		
		cb.url(url).type(JSONObject.class).handler(this, "jsonCb");
		
		cb.param("html", html);
		
		aq.ajax(cb);
		
		waitAsync();
        
		JSONObject jo = (JSONObject) result;
		
		String pub = jo.optString("published");
		
		AQUtility.debug("pub", pub);
		
		assertNotNull(pub);
	}
	
	public void jsonCb(String url, JSONObject jo, AjaxStatus status){
		done(url, jo, status);
	}
	
	
	
	
}
