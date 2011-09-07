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
import android.widget.ImageView;
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
	private String LAND_URL = "http://farm6.static.flickr.com/5035/5802797131_a729dac808_b.jpg";
	private String INVALID_URL = "http://www.vikispot.com/z/images/vikispot/xyz.png";
	
	//@UiThreadTest
	
	//Test: public T image(String url)	
	public void testImage4() {
		
		clearCache();
		
		AQUtility.post(new Runnable() {
			
			@Override
			public void run() {
				aq.id(R.id.image).image(ICON_URL);
			}
		});
		
		waitAsync();
		
		assertNotNull(aq.getImageView().getDrawable());
		
		Bitmap bm = aq.getCachedImage(ICON_URL);
		
		assertNotNull(bm);
		
    }
	
	//Test: public T image(String url, boolean memCache, boolean fileCache)	
	public void testImage5() {
		
		clearCache();
		
		AQUtility.post(new Runnable() {
			
			@Override
			public void run() {
				aq.id(R.id.image).image(ICON_URL, false, false);
			}
		});
		
		waitAsync();
		
		assertNotNull(aq.getImageView().getDrawable());
		
		Bitmap bm = aq.getCachedImage(ICON_URL);		
		assertNull(bm);
		
    }
	
	//Test: public T image(String url, boolean memCache, boolean fileCache, int targetWidth, int fallbackId)
	public void testImage6() {
		
		clearCache();
		
		AQUtility.post(new Runnable() {
			
			@Override
			public void run() {
				aq.id(R.id.image).image(LAND_URL, true, true, 200, 0);
			}
		});
		
		waitAsync();
		
		assertNotNull(aq.getImageView().getDrawable());
		
		Bitmap bm = aq.getCachedImage(LAND_URL, 200);		
		assertNotNull(bm);
		
    }
	
	public void testImage6a() {
		
		clearCache();
		
		AQUtility.post(new Runnable() {
			
			@Override
			public void run() {
				aq.id(R.id.image).image(INVALID_URL, true, true, 200, R.drawable.icon);
			}
		});
		
		waitAsync();
		
		assertNotNull(aq.getImageView().getDrawable());
		
		
		
    }
	
	//Test: public T image(String url, boolean memCache, boolean fileCache, int targetWidth, int fallbackId, Bitmap preset, int animId)
	public void testImage7() {
		
		clearCache();
		
		final Bitmap thumb = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.icon);	
		
		AQUtility.post(new Runnable() {
			
			@Override
			public void run() {
				
							
				aq.id(R.id.image).image(ICON_URL, true, true, 0, 0, thumb, 0);
				assertNotNull(aq.getImageView().getDrawable());
			}
		});
		
		
		waitAsync();
		
		Bitmap bm = aq.getCachedImage(ICON_URL);		
		assertNotNull(bm);
		
    }	
	
	//Test: public T image(String url, boolean memCache, boolean fileCache, int targetWidth, int fallbackId, Bitmap preset, int animId)
	public void testImage7a() {
		
		clearCache();
		
		
		AQUtility.post(new Runnable() {
			
			@Override
			public void run() {
							
				aq.id(R.id.image).image(ICON_URL, true, true, 0, 0, null, AQuery.FADE_IN);
			}
		});
		
		
		waitAsync();
		
		Bitmap bm = aq.getCachedImage(ICON_URL);		
		assertNotNull(bm);
		
    }	
	
	//Test: public T image(BitmapAjaxCallback callback)
	public void testImage8() {
		
		clearCache();
		
		
		AQUtility.post(new Runnable() {
			
			@Override
			public void run() {
							
				//aq.id(R.id.image).image(ICON_URL, true, true, 0, 0, null, AQuery.FADE_IN);
				BitmapAjaxCallback cb = new BitmapAjaxCallback();
				cb.url(ICON_URL);
				
				aq.id(R.id.image).image(cb);
			}
		});
		
		
		waitAsync();
		
		assertNotNull(aq.getImageView().getDrawable());
		
		
		Bitmap bm = aq.getCachedImage(ICON_URL);		
		assertNotNull(bm);
		
    }	
	
	//Test: public T image(String url, boolean memCache, boolean fileCache, int targetWidth, int resId, BitmapAjaxCallback callback)
	public void testImage9() {
		
		clearCache();
		
		AQUtility.post(new Runnable() {
			
			@Override
			public void run() {
							
				BitmapAjaxCallback cb = new BitmapAjaxCallback(){
				
					protected void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
					
						iv.setImageBitmap(bm);
						
					}
					
				};
				
				aq.id(R.id.image).image(ICON_URL, true, true, 0, 0, cb);
			}
		});
		
		
		waitAsync();
		
		assertNotNull(aq.getImageView().getDrawable());
		
		
		Bitmap bm = aq.getCachedImage(ICON_URL);		
		assertNotNull(bm);
		
    }		
	
	private void prefetchFile(){
		
		AQUtility.post(new Runnable() {
			
			@Override
			public void run() {
				aq.id(R.id.image).image(LAND_URL, true, true, 200, 0);
			}
		});
		
		waitAsync();
		
		assertNotNull(aq.getImageView().getDrawable());
		
		waitSec();
		
		File file = aq.getCachedFile(LAND_URL);
		
		assertNotNull(file);
	}
	
	//Test: public T image(File file, int targetWidth)
	public void testImage10() {
		
		clearCache();
		
		prefetchFile();
		
		AQUtility.post(new Runnable() {
			
			@Override
			public void run() {
				File file = aq.getCachedFile(LAND_URL);
				aq.id(R.id.image2).image(file, 200);
			}
		});
		
		assertNotNull(aq.getImageView().getDrawable());
		
    }	
	
	//Test: public T image(File file, boolean memCache, int targetWidth, BitmapAjaxCallback callback)
	public void testImage11() {
		
		clearCache();
		
		prefetchFile();
		
		AQUtility.post(new Runnable() {
			
			@Override
			public void run() {
				File file = aq.getCachedFile(LAND_URL);
				aq.id(R.id.image2).image(file, true, 200, new BitmapAjaxCallback(){
					@Override
					protected void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
						iv.setImageBitmap(bm);
					}
				});
			}
		});
		
		assertNotNull(aq.getImageView().getDrawable());
		
    }	
	
	
	//Test: public T image(String url, boolean memCache, boolean fileCache, int targetWidth, int fallbackId, Bitmap preset, int animId, float ratio)
	public void testImage12() {
		
		clearCache();
		
		AQUtility.post(new Runnable() {
			
			@Override
			public void run() {
				
				aq.id(R.id.image).image(ICON_URL, true, true, 0, 0, null, 0, AQuery.RATIO_PRESERVE);
			}
		});
		
		
		waitAsync();
		
		Bitmap bm = aq.getCachedImage(ICON_URL);		
		assertNotNull(bm);
		
    }	
}
