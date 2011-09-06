package com.androidquery.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.androidquery.util.AQUtility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.View;
import android.widget.TextView;


public class AQueryImageTest extends AbstractTest<AQueryTestActivity> {

	
	public AQueryImageTest() {		
		super(AQueryTestActivity.class);
    }
	
	//Test: public T image(int resid)
	@UiThreadTest
	public void testImage1() {
		
		aq.id(R.id.image).image(R.drawable.icon);
        
		assertNotNull(aq.getImageView().getDrawable());
		
    }
	
	
	//Test: public T image(int resid)
	@UiThreadTest
	public void testImage2() {
		
		Drawable d = getActivity().getResources().getDrawable(R.drawable.icon);
		
		assertNotNull(d);
		
		aq.id(R.id.image).image(d);
        
		assertNotNull(aq.getImageView().getDrawable());
		
    }
	
	//Test: public T image(Bitmap bm)
	@UiThreadTest
	public void testImage3() {
		
		Bitmap bm = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.icon);
		
		assertNotNull(bm);
		
		aq.id(R.id.image).image(bm);
        
		assertNotNull(aq.getImageView().getDrawable());
		
    }
	
	private void clearCache(){
		
		BitmapAjaxCallback.clearCache();
		
		File cacheDir = AQUtility.getCacheDir(getActivity());
		AQUtility.cleanCache(cacheDir, 0, 0);
		
	}
	
	private String ICON_URL = "http://www.vikispot.com/z/images/vikispot/android-w.png";
	
	//Test: public T image(String url)
	/*
	@UiThreadTest
	public void testImage4() {
		
		clearCache();
		
		aq.id(R.id.image).image(ICON_URL);
        
		waitAsync();
		
		assertNotNull(aq.getImageView().getDrawable());
		
    }
	*/
}
