&lt;wiki:gadget url="http://android-query.googlecode.com/svn/trunk/demo/gadget/social.xml?v=2" height="115" width="700" border="0" /&gt;

## Demo ##

All image loading features and code examples are available with our live [demo app](https://market.android.com/details?id=com.androidquery) on Android Market.

**We highly recommended that you try the demo app to get a feel of AQuery's image loading features.**

## Code ##

Asynchronous image loading is easy with AQuery.

### Simple ###
```
//load an image to an ImageView from network, cache image to file and memory

aq.id(R.id.image1).image("http://www.vikispot.com/z/images/vikispot/android-w.png");
```

### Cache Control ###
```

//load an image from network, but only cache with file

//this image is huge, avoid memory caching
boolean memCache = false;
boolean fileCache = true;

aq.id(R.id.image1).image("http://www.vikispot.com/z/images/vikispot/android-w.png", memCache, fileCache);
```

### Down Sampling (handling huge images) ###
```

//we are loading a huge image from the network, but we only need the image to be bigger than 200 pixels wide
//passing in the target width of 200 will down sample the image to conserve memory
//aquery will only down sample with power of 2 (2,4,8...) for good image quality and efficiency
//the resulting image width will be between 200 and 399 pixels

String imageUrl = "http://farm6.static.flickr.com/5035/5802797131_a729dac808_b.jpg";		
aq.id(R.id.image1).image(imageUrl, true, true, 200, 0);
```

Related Blog: [Downsampling](http://blog.androidquery.com/2011/05/down-sample-images-to-avoid-out-of.html)

### Fallback Image ###

```

//if we are not able to load the image, use a default image (R.drawable.default_image)

String imageUrl = "http://www.vikispot.com/z/images/vikispot/android-w.png";
aq.id(R.id.image1).image(imageUrl, true, true, 0, R.drawable.default_image);

//make image view "invisible" if image failed to load
imageUrl = "http://a.b.com/invalid.jpg";		
aq.id(R.id.image1).image(imageUrl, true, true, 0, AQuery.INVISIBLE);


//make image view "gone" if image failed to load
aq.id(R.id.image1).image(imageUrl, true, true, 0, AQuery.GONE);


```

### Preloading ###
```

//get the bitmap for a previously fetched thumbnail
String thumbnail = "http://farm6.static.flickr.com/5035/5802797131_a729dac808_s.jpg";	
Bitmap preset = aq.getCachedImage(thumbnail);

//set the image view with a thumbnail, and fetch the high resolution image asynchronously
String imageUrl = "http://farm6.static.flickr.com/5035/5802797131_a729dac808_b.jpg";		
aq.id(R.id.image).image(imageUrl, false, false, 0, 0, preset, AQuery.FADE_IN);

```

### Progress ###

We can show the image loading progress by using the progress() method. Pass in the progress bar id, or any other view id to the progress method, and the view will be shown or hide to display loading progress.

```
String imageUrl = "http://farm6.static.flickr.com/5035/5802797131_a729dac808_b.jpg";		
aq.id(R.id.image).progress(R.id.progress).image(imageUrl, false, false);
```

Sample grid item layout:
```
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="fill_parent"
    android:layout_height="100dip" 
    >
    
    <ProgressBar 		
	android:layout_width="15dip"       
        android:layout_height="15dip"
	android:id="@+id/progress" 
	android:layout_centerInParent="true"
	/>
    
    <ImageView
        android:id="@+id/image"       
        android:layout_width="fill_parent"       
        android:layout_height="75dip"
        />

</RelativeLayout>
```


### Animation ###
```

//display the image with a predefined fade in animation
String imageUrl = "http://www.vikispot.com/z/images/vikispot/android-w.png";		
aq.id(R.id.image).image(imageUrl, true, true, 0, null, 0, AQuery.FADE_IN);

```

### Rounded Corner ###

The round option transform the image to a rounded corner image with a specified radius. Note that the value is in pixels (not dip).

Due to performance impact, round corner should not be used for large images.

```

String url = "http://www.vikispot.com/z/images/vikispot/android-w.png";

ImageOptions options = new ImageOptions();
options.round = 15;

aq.id(R.id.image).image(url, options);

```


### Aspect Ratio ###

##### Preserve Image Aspect Ratio #####

```
String imageUrl = "http://farm3.static.flickr.com/2199/2218403922_062bc3bcf2.jpg";	
aq.id(R.id.image).image(imageUrl, true, true, 0, 0, null, AQuery.FADE_IN, AQuery.RATIO_PRESERVE);

```

##### Fixed Predefined Aspect Ratio #####

```

String imageUrl = "http://farm3.static.flickr.com/2199/2218403922_062bc3bcf2.jpg";	

aq.id(R.id.image1).image(imageUrl, true, true, 0, 0, null, 0, AQuery.RATIO_PRESERVE);	

//1:1, a square	
aq.id(R.id.image2).image(imageUrl, true, true, 0, 0, null, 0, 1.0f / 1.0f);		
aq.id(R.id.image3).image(imageUrl, true, true, 0, 0, null, 0, 1.5f / 1.0f);	
	
//16:9, a video thumbnail
aq.id(R.id.image4).image(imageUrl, true, true, 0, 0, null, 0, 9.0f / 16.0f);	
aq.id(R.id.image5).image(imageUrl, true, true, 0, 0, null, 0, 3.0f / 4.0f);
```

**Note**

The aspect ratio feature will set the ImageView to use the [ScaleType](http://developer.android.com/reference/android/widget/ImageView.ScaleType.html) MATRIX and adjust its height to the correct aspect ratio.

### Anchor ###

If the image aspect ratio is taller then the desired aspect ratio, the anchor option can be used to control which vertical portion of the image should be displayed.

Anchor values:
  * 1.0 : Display top of the image
  * 0 : Display the center of the image
  * -1.0 : Display bottom of the image
  * AQuery.ANCHOR\_DYNAMIC : Display image with a top bias for photos.

```

ImageOptions options = new ImageOptions();

options.ratio = 1;
options.anchor = 1.0;
aq.id(R.id.image1).image(imageUrl, options);


```

### Custom Callback ###

```
String imageUrl = "http://www.vikispot.com/z/images/vikispot/android-w.png";

final int tint = 0x77AA0000;

aq.id(R.id.image1).image(imageUrl, true, true, 0, 0, new BitmapAjaxCallback(){

	@Override
	public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status){
				
		iv.setImageBitmap(bm);
		
		//do something to the bitmap
		iv.setColorFilter(tint, PorterDuff.Mode.SRC_ATOP);
		
	}
	
});

```

### File (Async) ###

Load image from a file asynchronously. Down sampling is recommended when loading huge images from camera or gallery to avoid out of memory errors.

##### Simple #####
```

File file = new File(path);        

//load image from file, down sample to target width of 300 pixels  
aq.id(R.id.avatar).image(file, 300);

```

##### Callback #####

```

File file = new File(path);

//load image from file with callback
aq.id(R.id.avatar).image(file, false, 300, new BitmapAjaxCallback(){

    @Override
    public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status){
    	
    	iv.setImageBitmap(bm);
        
    	//do something with the bm
        
    }
    
});

```
### Duplicated URL ###
```

//AQuery detects duplicated image urls request and only fetches 1 image and apply them to all associated image views

String imageUrl = "http://www.vikispot.com/z/images/vikispot/android-w.png";
aq.id(R.id.image1).image(imageUrl);

//no network fetch for 2nd request, image will be shown when first request is completed
aq.id(R.id.image2).image(imageUrl);

```

### BitmapAjaxCallback ###

All the AQuery image methods uses the BitmapAjaxCallback internally. You can configure and use the callback directly if you prefer.

```
String imageUrl = "http://farm3.static.flickr.com/2199/2218403922_062bc3bcf2.jpg";	

//create a bitmap ajax callback object
BitmapAjaxCallback cb = new BitmapAjaxCallback();

//configure the callback
cb.url(imageUrl).animation(AQuery.FADE_IN).ratio(1.0f);

//invoke it with an image view
aq.id(R.id.image).image(cb);

```

### Access Cached Images ###

```
//returns the cached file by url, returns null if url is not cached
File file = aq.getCachedFile(url);
```

### Zoomable Web Image ###

In addition to ImageView, a WebView can be used to display an image along with Android build in zoom support for WebView. Image will be centered and fill the width or height of the webview depending on it's orientation.

```
private void image_zoom(){

	String url = "http://farm4.static.flickr.com/3531/3769416703_b76406f9de.jpg";   	
	aq.id(R.id.web).progress(R.id.progress).webImage(url);
	
}
```

### List View Example ###

When working with AQuery, make sure the views you want to operate can be "findBy" the view or activity you created with the AQuery object. In the case for rendering list items, create an AQuery object with the item container view.

#### Example ####

```

ArrayAdapter<JSONObject> aa = new ArrayAdapter<JSONObject>(this, R.layout.content_item_s, items){
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null){
			convertView = getLayoutInflater().inflate(R.layout.content_item_s, null);
		}
		
		JSONObject jo = getItem(position);
		
		AQuery aq = new AQuery(convertView);
		aq.id(R.id.name).text(jo.optString("titleNoFormatting", "No Title"));
		aq.id(R.id.meta).text(jo.optString("publisher", ""));
		
		String tb = jo.optJSONObject("image").optString("tbUrl");
		aq.id(R.id.tb).progress(R.id.progress).image(tb, true, true, 0, 0, null, AQuery.FADE_IN_NETWORK, 1.0f);
		
		
		return convertView;
		
	}
};
```

#### Delay Image Loading ####

AQuery provide a shouldDelay() method to help you decide whether to load expensive resources when the list is scrolling (flinging) really fast. It's recommended to delay load large images and other resources over the internet.

Please see [javadoc](http://android-query.googlecode.com/svn/trunk/javadoc/com/androidquery/AbstractAQuery.html#shouldDelay(View%2C%20ViewGroup%2C%20java.lang.String%2C%20float)) for more detail.

```

ArrayAdapter<Photo> aa = new ArrayAdapter<Photo>(this, R.layout.photo_item, entries){
	
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null){
			convertView = getLayoutInflater().inflate(R.layout.photo_item, parent, false);
		}
		
		Photo photo = getItem(position);
		
		AQuery aq = new AQuery(convertView);
		
		aq.id(R.id.name).text(photo.title);
		aq.id(R.id.meta).text(photo.author);
		
		String tbUrl = photo.tb;
		
		Bitmap placeholder = aq.getCachedImage(R.drawable.image_ph);
		
		if(aq.shouldDelay(position, convertView, parent, tbUrl)){
			aq.id(R.id.tb).image(placeholder, 0.75f);
		}else{
			aq.id(R.id.tb).image(tbUrl, true, true, 0, 0, placeholder, 0, 0.75f);
		}
		
		return convertView;
		
	}
	
};

//apply any custom onScrollListener
aq.id(R.id.list).scrolled(new OnScrollListener(){...});

//apply the adapter
aq.id(R.id.list).adapter(aa);

```

**NOTE**

The shouldDelay() method uses the **setOnScrollListener()** method and will override any previously non-aquery assigned scroll listener. If a scrolled listener is required, use the aquery method **scrolled(OnScrollListener listener)** to register your listener instead.

ListView, GridView, Gallery, and ExpandableListView are supported. For ExpandableListView, use the variation method:

```
shouldDelay(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent, java.lang.String url) 
```

#### ViewHolder Pattern ####

ViewHolder pattern optimize performance by caching the views returned by findViewById().

```

ArrayAdapter<JSONObject> aa = new ArrayAdapter<JSONObject>(this, R.layout.content_item_s, items){
	
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder;
		
		if(convertView == null){					
			convertView = getLayoutInflater().inflate(R.layout.content_item_s, null);
			holder = new ViewHolder();
			holder.imageview = (ImageView) convertView.findViewById(R.id.tb);
			holder.progress = (ProgressBar) convertView.findViewById(R.id.progress);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		JSONObject jo = getItem(position);
		String tb = jo.optJSONObject("image").optString("tbUrl");
		
		AQuery aq = new AQuery(convertView);
		aq.id(holder.imageview).progress(holder.progress).image(tb, true, true, 0, 0, null, 0, 1.0f);
		
		return convertView;
	}
};

```



### Cache Busting ###

Images on the web usually doesn't change. If your image changes with identical url and would like to refresh the image, simply set file and mem cache to false and it will force the image to be refetched.

```
String url = "http://www.vikispot.com/z/images/vikispot/android-w.png";

//force a network refetch without any caching
aq.id(R.id.image).image(url, false, false);

//force no proxy cache by appending a random number to url 
String url2 = url + "?t=" + System.currentTimeMillis();
aq.id(R.id.image2).image(url2, false, false);
```

### Network Failure Recovery ###

If network is disconnected and causing images to failed to load and use fallback images instead, the fallback images will be cached and continue to be served until the network connection is recovered.

When a next subsequent successful connection is established, the fallback image cache will be flushed and allow failed images to be reloaded.

### Cache Configuration ###

By default, AQuery uses the internal file system to cache files, which is only accessible to your app. If you want to use the external SDCard for storing your cached files, use the setCacheDir() function.

```
	
File ext = Environment.getExternalStorageDirectory();
File cacheDir = new File(ext, "myapp");	
AQUtility.setCacheDir(cacheDir);
	

```

### Sharing Image ###

In order to share resources to other apps, the file must be on the external storage (SDCard). The makeSharedFile() method create a copy of a cached url to a temporary folder in SDCard. You can then pass the file as an URI and share it with other apps, like Gmail or Facebook.

```

public void image_send(){
	
	String url = "http://www.vikispot.com/z/images/vikispot/android-w.png";		
	File file = aq.makeSharedFile(url, "android.png");
	
	if(file != null){		
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/jpeg");
		intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		startActivityForResult(Intent.createChooser(intent, "Share via:"), SEND_REQUEST);
	}
}

```


## Configuration ##

More fine tuning can be done when an application starts. A good place to set the configuration is in the onCreate() method of the application.

```

public class MainApplication extends Application{

	
	@Override
    public void onCreate() {     
          
        //set the max number of concurrent network connections, default is 4
	AjaxCallback.setNetworkLimit(8);

	//set the max number of icons (image width <= 50) to be cached in memory, default is 20
	BitmapAjaxCallback.setIconCacheLimit(20);

	//set the max number of images (image width > 50) to be cached in memory, default is 20
	BitmapAjaxCallback.setCacheLimit(40);

	//set the max size of an image to be cached in memory, default is 1600 pixels (ie. 400x400)
	BitmapAjaxCallback.setPixelLimit(400 * 400);
        
	//set the max size of the memory cache, default is 1M pixels (4MB)
	BitmapAjaxCallback.setMaxPixelLimit(2000000);		        
        
        super.onCreate();
    }
	
	
}
```




## Maintenance ##

AQuery provides few utility functions to help you control ajax and caching behavior.


### Clean Up ###

If you use file cache for images, regularly clean the cache dir when the application exits.

#### Simple ####

```
protected void onDestroy(){
	
	super.onDestroy();
	
	//clean the file cache when root activity exit
	//the resulting total cache size will be less than 3M	
	if(isTaskRoot()){
    		AQUtility.cleanCacheAsync(this);
    	}
}

```

#### Advance ####
```
protected void onDestroy(){
	
	super.onDestroy();
	
	if(isTaskRoot()){

    		//clean the file cache with advance option
    		long triggerSize = 3000000; //starts cleaning when cache size is larger than 3M
    		long targetSize = 2000000;	//remove the least recently used files until cache size is less than 2M
    		AQUtility.cleanCacheAsync(this, triggerSize, targetSize);
	}
	
}

```

### Low Memory ###

Low memory happens when the device is overloaded with apps. When this happens, we want to remove all images from memory cache.

```
public class MainApplication extends Application{

    @Override
    public void onLowMemory(){	

	//clear all memory cached images when system is in low memory
	//note that you can configure the max image cache count, see CONFIGURATION
    	BitmapAjaxCallback.clearCache();
    }
	
}
```