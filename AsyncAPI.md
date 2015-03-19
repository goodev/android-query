&lt;wiki:gadget url="http://android-query.googlecode.com/svn/trunk/demo/gadget/social.xml?v=2" height="115" width="700" border="0" /&gt;

# Asynchronous Network #

## Permission ##

Make sure the following permission is included in your manifest.

```
<uses-permission android:name="android.permission.INTERNET" /> 
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```

WRITE\_EXTERNAL\_STORAGE is required for temporary caching for large response types.


## Code ##

Asynchronous AJAX or RPC calls are simple with AQuery.

#### Supported Types ####

AQuery transform the data automatically base on the class type passed in the ajax method.

Supported types:
  * JSONObject
  * JSONArray
  * String (HTML, XML)
  * XmlDom (XML parsing)
  * XmlPullParser (Large XML files)
  * byte array
  * User defined custom type (Transformer)
  * [Bitmap](http://code.google.com/p/android-query/wiki/ImageLoading)

If there are native Android data types (without third party dependency) you want AQuery to support, please send us feedback.

#### JSON ####
```


public void asyncJson(){
	
	//perform a Google search in just a few lines of code
	
	String url = "http://www.google.com/uds/GnewsSearch?q=Obama&v=1.0";
	
	aq.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {

		@Override
		public void callback(String url, JSONObject json, AjaxStatus status) {
			
			
			if(json != null){
				
				//successful ajax call, show status code and json content
				Toast.makeText(aq.getContext(), status.getCode() + ":" + json.toString(), Toast.LENGTH_LONG).show();
			
			}else{
				
				//ajax error, show error code
				Toast.makeText(aq.getContext(), "Error:" + status.getCode(), Toast.LENGTH_LONG).show();
			}
		}
	});
	
}

```

#### JSON (activity as callback) ####
```
public void asyncJson(){
	
	//perform a Google search in just a few lines of code
	
	String url = "http://www.google.com/uds/GnewsSearch?q=Obama&v=1.0";		
	aq.ajax(url, JSONObject.class, this, "jsonCallback");
	
}

public void jsonCallback(String url, JSONObject json, AjaxStatus status){
	
	if(json != null){		
		//successful ajax call		
	}else{		
		//ajax error
	}
	
}

```

Note that AQuery uses a weak reference to hold the handler for this method. This is to make sure an activity won't be memory leaked when it's terminated before the AJAX request finishes.

#### HTML/XML ####

```
//fetch Google's homepage in html

String url = "http://www.google.com";

aq.ajax(url, String.class, new AjaxCallback<String>() {

	@Override
	public void callback(String url, String html, AjaxStatus status) {
		
	}
	
});
```

#### XML Dom ####

AQuery provide a light weight XML parser called XmlDom. [javadoc](http://android-query.googlecode.com/svn/trunk/javadoc/index.html?com/androidquery/util/XmlDom.html)

XmlDom is a specialized class for simple and easy XML parsing. It's designed to be used in basic Android api 4+ runtime without any dependency.

Example to parse Picasa's featured photos feed:

```
public void xml_ajax(){		
	String url = "https://picasaweb.google.com/data/feed/base/featured?max-results=8";		
	aq.ajax(url, XmlDom.class, this, "picasaCb");		
}

public void picasaCb(String url, XmlDom xml, AjaxStatus status){

	List<XmlDom> entries = xml.tags("entry");		
	List<String> titles = new ArrayList<String>();
	
	String imageUrl = null;
	
	for(XmlDom entry: entries){
		titles.add(entry.text("title"));
		imageUrl = entry.tag("content", "type", "image/jpeg").attr("src");
	}
		
	aq.id(R.id.image).image(imageUrl);
	
}

```

Related Blog: [XML Parsing](http://blog.androidquery.com/2011/09/simpler-and-easier-xml-parsing-in.html)

#### XmlPullParser ####

For large XML response that cannot fit in memory, the XMLPullParser can be used to avoid memory issue.

```

public void callback(String url, XmlPullParser xpp, AjaxStatus status) {
	
	Map<String, String> images = new LinkedHashMap<String, String>();
	String currentTitle = null;
	
	try{
	
	int eventType = xpp.getEventType();
        while(eventType != XmlPullParser.END_DOCUMENT) {
          
        	if(eventType == XmlPullParser.START_TAG){
        		
        		String tag = xpp.getName();
        		
        		if("title".equals(tag)){
        			currentTitle = xpp.nextText();
        		}else if("content".equals(tag)){
        			String imageUrl = xpp.getAttributeValue(0);
        			images.put(currentTitle, imageUrl);
        		}
        	}
        	eventType = xpp.next();
        }
	
	}catch(Exception e){
		AQUtility.report(e);
	}
	
	showResult(images, status);
	
}

```

#### bytes ####

```
//fetch a remote resource in raw bytes

String url = "http://www.vikispot.com/z/images/vikispot/android-w.png";

aq.ajax(url, byte[].class, new AjaxCallback<byte[]>() {

	@Override
	public void callback(String url, byte[] object, AjaxStatus status) {
		Toast.makeText(aq.getContext(), "bytes array:" + object.length, Toast.LENGTH_LONG).show();
	}
});
```

#### Bitmap ####

```
//fetch a remote resource in raw bitmap

String url = "http://www.vikispot.com/z/images/vikispot/android-w.png";

aq.ajax(url, Bitmap.class, new AjaxCallback<Bitmap>() {

	@Override
	public void callback(String url, Bitmap object, AjaxStatus status) {
		
	}
});
```


### File ###


```

String url = "https://picasaweb.google.com/data/feed/base/featured?max-results=8";		

aq.progress(R.id.progress).ajax(url, File.class, new AjaxCallback<File>(){
	
	public void callback(String url, File file, AjaxStatus status) {
		
		if(file != null){
			showResult("File:" + file.length() + ":" + file, status);
		}else{
			showResult("Failed", status);
		}
	}
	
});

```

### File Download ###

If you can specify where to store the downloaded file with the download() method. Note that these files will not be deleted like cached files.

```
String url = "https://picasaweb.google.com/data/feed/base/featured?max-results=16";		

File ext = Environment.getExternalStorageDirectory();
File target = new File(ext, "aquery/myfolder/photos.xml");		

aq.progress(R.id.progress).download(url, target, new AjaxCallback<File>(){
	
	public void callback(String url, File file, AjaxStatus status) {
		
		if(file != null){
			showResult("File:" + file.length() + ":" + file, status);
		}else{
			showResult("Failed", status);
		}
	}
	
});
```

### InputStream ###

```
String url = "https://picasaweb.google.com/data/feed/base/featured?max-results=8";		

aq.progress(R.id.progress).ajax(url, InputStream.class, new AjaxCallback<InputStream>(){
	
	public void callback(String url, InputStream is, AjaxStatus status) {
		
		if(is != null){
			showResult("InputStream:" + is, status);
		}else{
			showResult("Failed", status);
		}
	}
	
});

```

#### Custom Type ####

AQuery by default supports types that are native to Android. For app specific data types, you can assign a custom Transformer to the ajax call and convert the raw data to a desired type. The benefit of transformer is that it's run under the background thread (similar to the natively supported types), therefore minimizing CPU cycles and avoid blocking on the UI thread.

This example show how to use Gson to transform raw data to user defined types.

```
private static class Profile{
	public String id;
	public String name;		
}

private static class GsonTransformer implements Transformer{

	public <T> T transform(String url, Class<T> type, String encoding, byte[] data, AjaxStatus status) {			
		Gson g = new Gson();
		return g.fromJson(new String(data), type);
	}
}

public void async_transformer(){
	
	String url = "https://graph.facebook.com/205050232863343";		
	GsonTransformer t = new GsonTransformer();
	
	aq.transformer(t).progress(R.id.progress).ajax(url, Profile.class, new AjaxCallback<Profile>(){			
		public void callback(String url, Profile profile, AjaxStatus status) {	
			Gson gson = new Gson();
			showResult("GSON Object:" + gson.toJson(profile), status);		
		}			
	});
    
}


```

#### Custom Type (Static Transformer) ####

A stateless static default transformer can also be set with AjaxCallback.setTransformer().

Transformers are selected in the following priority:
  1. Native
  1. instance transformer()
  1. static setTransformer()

```

//set the static transformer when application starts
GsonTransformer t = new GsonTransformer();
AjaxCallback.setTransformer(t);

//any subsequent ajax calls will use the static transformer for custom types
String url = "https://graph.facebook.com/205050232863343";
aq.ajax(url, Profile.class, this, "callback");

```

### Http POST ###

```
public void async_post(){
	
    //do a twiiter search with a http post
	
    String url = "http://search.twitter.com/search.json";
	
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("q", "androidquery");
	
    aq.ajax(url, params, JSONObject.class, new AjaxCallback<JSONObject>() {

        @Override
        public void callback(String url, JSONObject json, AjaxStatus status) {
               
            showResult(json);
           
        }
    });
	
}

```

### Http POST (Multipart) ###

AQuery support multipart post such as uploading an image to a service. Simply do a regular POST and put byte array, Inputstream, or File as one of the parameters.

```
private void aync_multipart(){
	
	String url = "https://graph.facebook.com/me/photos";
	
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("message", "Message");
	
	//Simply put a byte[] to the params, AQuery will detect it and treat it as a multi-part post
	byte[] data = getImageData();
	params.put("source", data);
	
	//Alternatively, put a File or InputStream instead of byte[]
	//File file = getImageFile();		
	//params.put("source", file);
	
	AQuery aq = new AQuery(getApplicationContext());
	aq.auth(handle).ajax(url, params, JSONObject.class, this, "photoCb");
	
}

```

### Http POST (Custom Entity) ###

If a custom entity is required, you can use the special AQuery.POST\_ENTITY key name to pass in an HttpEntity object.

```

public void async_post_entity() throws UnsupportedEncodingException{
	
    String url = "http://search.twitter.com/search.json";
	
    List<NameValuePair> pairs = new ArrayList<NameValuePair>();
	pairs.add(new BasicNameValuePair("q", "androidquery"));				
	HttpEntity entity = new UrlEncodedFormEntity(pairs, "UTF-8");
    
	Map<String, Object> params = new HashMap<String, Object>();
	params.put(AQuery.POST_ENTITY, entity);
    
    aq.progress(R.id.progress).ajax(url, params, JSONObject.class, new AjaxCallback<JSONObject>() {

        @Override
        public void callback(String url, JSONObject json, AjaxStatus status) {
            
            showResult(json, status);
           
        }
    });
}

```

### Http POST JSON ###

Posting JSON string can be done with custom entity or the following shortcut method. This is same as POST with StringEntity and header "Content-Type": "application/json". Note that the 3rd parameter is the expected http response type of the url, which might or might not be a JSONObject.

```

JSONObject input = new JSONObject();
input.putOpt("hello", "world");
	        
aq.post(url, input, JSONObject.class, cb);
```


### Long URL (2000+ length) ###

Url length shouldn't be longer than 2000 characters. ([reference](http://stackoverflow.com/questions/417142/what-is-the-maximum-length-of-a-url))

The http POST method might be used to get around this limit. For example, [Google Chart](http://code.google.com/apis/chart/image/docs/post_requests.html) allow a post method for requests that will run over 2000 characters long.

AQuery detects such case and automatically switch from GET to POST method if required.



### Caching ###

Caching is easy with ajax requests. Just pass in an expire time as a parameter, and if the data is available, it will be served from the file cache instead of fetching over the network.

```

String url = "http://www.google.com";

//return a cached copy if the data is recently fetched within 15 minutes 
long expire = 15 * 60 * 1000;

aq.ajax(url, String.class, expire, new AjaxCallback<String>() {

    @Override
    public void callback(String url, String html, AjaxStatus status) {        
    	showResult(html);
    }
        
});


```

### Refresh Content ###

Passing expire time as -1 will ensure the content be refreshed immediately and to be file cached.

```

String url = "http://www.google.com";

long expire = -1;

aq.ajax(url, String.class, expire, new AjaxCallback<String>() {

    @Override
    public void callback(String url, String html, AjaxStatus status) {        
    	showResult(html);
    }
        
});


```

### Invalidating Cache ###

Calling the invalidate method of the AjaxStatus object will invalidate the url content so that it will not be cached. It's useful when http status is 200 but the object returned is invalid.

```

public void callback(String url, JSONObject json, AjaxStatus status) {
    
	if(json != null){
		if("1".equals(json.optString("status"))){
			//do something
		}else{
			//we believe the request is a failure, don't cache it
			status.invalidate();
		}
	}
}

```


### Progress ###

We can show the loading progress by using the progress() method. Pass in the progress bar id, or any other view id to the progress method, and the view will be shown or hide to display ajax progress.

```

String url = "http://www.google.com/uds/GnewsSearch?q=Obama&v=1.0";                
aq.progress(R.id.progress).ajax(url, JSONObject.class, this, "jsonCb");

```



```

ProgressDialog dialog = new ProgressDialog(this);

dialog.setIndeterminate(true);
dialog.setCancelable(true);
dialog.setInverseBackgroundForced(false);
dialog.setCanceledOnTouchOutside(true);
dialog.setTitle("Sending...");

String url = "http://www.google.com/uds/GnewsSearch?q=Obama&v=1.0";                
aq.progress(dialog).ajax(url, JSONObject.class, this, "jsonCb");

```


```
//Use activity's setProgressBarIndeterminateVisibility(show);
//Remember to call this before setContentView(): requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); 
String url = "http://www.google.com/uds/GnewsSearch?q=Obama&v=1.0";          
aq.progress(this).ajax(url, JSONObject.class, this, "jsonCb");  
```

### Activity Finished Detection ###

AQuery will examine the initialization context and skip the ajax callback if an activity is already stopped before the aync task is completed. This behavior avoid unnecessary work and avoid modifying ui states that could be illegal (such as showing a dialog when an activity is not longer active).

```

//using the activity as context will ensure no callback is called when activity is finished
AQuery aq = new AQuery(activity);

//using the application context will ensure the callback to be called as long as the application is running
AQuery aq = new AQuery(getApplicationContext());

```

### Synchronous Call (Block) ###

If the ajax call is run outside of main thread, the sync() method can be used to block the calling thread until the ajax call is completed (or until it's timed out).

Fetch result synchronously:

```

String url = "http://www.google.com/uds/GnewsSearch?q=Obama&v=1.0";
        
AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>();		
cb.url(url).type(JSONObject.class);		
        
aq.sync(cb);
        
JSONObject jo = cb.getResult();
AjaxStatus status = cb.getStatus();

```

Note that this will block the thread until the result is returned. The actual work will be done in another thread pool. Calling this method in Main/UI thread will cause an exception.

### Ajax Status ###

An AjaxStatus object is returned with any callback. This object holds some meta information regarding the aync task performed.

```
public void callback(String url, JSONObject json, AjaxStatus status) {
    
    int source = status.getSource();
    int responseCode = status.getCode();
    long duration = status.getDuration();
    Date fetchedTime = status.getTime();
    String message = status.getMessage();
    String redirect = status.getRedirect();
    DefaultHttpClient client = status.getClient();

    //returns the html response when response code is not 2xx
    String errorResponse = status.getError();	
}

```

[javadoc](http://android-query.googlecode.com/svn/trunk/javadoc/index.html?com/androidquery/AbstractAQuery.html)

### Advance ###

The AQuery object provide various commonly used "ajax" methods. For more advanced (but less commonly used) features such as http header and authentication, AjaxCallback can be used directly to customize the requests.

Note: url and type are required parameters.

```

String url = "http://www.google.com/uds/GnewsSearch?q=Obama&v=1.0";

AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>();        
cb.url(url).type(JSONObject.class).weakHandler(this, "jsonCb").fileCache(true).expire(0);

aq.ajax(cb);

```

### Http Headers ###

Use the header method to configure http request headers.

```

String url = "http://www.google.com";

AjaxCallback<String> cb = new AjaxCallback<String>();        
cb.url(url).type(String.class).weakHandler(this, "stringCb");

cb.header("Referer", "http://code.google.com/p/android-query/");
cb.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");

aq.ajax(cb);

```

### Http Cookies ###

Use the cookie method to set http cookies.

```

String url = "http://www.androidquery.com/p/doNothing";

AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>();		
cb.url(url).type(JSONObject.class).weakHandler(this, "cookieCb");

cb.cookie("hello", "world").cookie("foo", "bar");		
aq.ajax(cb);

```

### Http Response Headers ###

```
status.getHeaders()
```

Returns a list of headers. If the request is cached or a http multipart post, this method returns an empty list.

### Http Response Cookies ###


```
status.getCookies();
```

Returns a list of cookies. If the request is cached or a http multipart post, this method returns an empty list.

### Encoding (BETA) ###

The default ajax callback uses UTF-8 to transform the object. If type String is specified and the content is html, AQuery will make a best-effort attempt to detect the encoding by inspecting the response header and the inline html META tag. If file cache is enabled, the content will be stored as UTF-8 bytes and subsequent read will not require encoding detection again.

If custom encoding is needed, uses the encoding() method to fix the encoding.

```
	
//Using String.class type will attempt to detect the encoding of a page and transform it to utf-8
		
String url = "http://114.xixik.com/gb2312_big5/";
aq.progress(R.id.progress).ajax(url, String.class, 0, this, "encodingCb");
		
	

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