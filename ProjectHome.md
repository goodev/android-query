[https://pledgie.com/campaigns/22663.png?skin\_name=chrome](https://pledgie.com/campaigns/22663)

# Release #

<font color='red' size='2'>DOWNLOADS MOVED TO GITHUB</font>

Latest:
https://github.com/androidquery/androidquery/releases/tag/0.26.8

# Android Query #

Android-Query (AQuery) is a light-weight library for doing asynchronous tasks and manipulating UI elements in Android. Our goal is to make Android coding simpler, easier, and more fun!


  * [Documentation](http://code.google.com/p/android-query/wiki/API)
  * [API Demo App](https://market.android.com/details?id=com.androidquery), [Sample App (SimpleFeed)](https://market.android.com/details?id=com.androidquery.simplefeed), [SimpleFeed Source](https://github.com/androidquery/simplefacebook).
  * [javadoc](http://android-query.googlecode.com/svn/trunk/javadoc/index.html?com/androidquery/AbstractAQuery.html)
  * [Android Development Blog](http://android-query.blogspot.com/)
  * Download [jar](http://code.google.com/p/android-query/downloads/list), [Release Notes](http://code.google.com/p/android-query/wiki/ReleaseNote)
  * [Discussion Group](http://groups.google.com/group/android-query)
  * [GitHub](https://github.com/androidquery/androidquery)

## Why AQuery? ##
  * [Less Code](#Less_Code.md)
  * [AJAX Callback](#AJAX_Callback.md)
  * [Image Loading](#Image_Loading.md)
  * [XML Parsing](http://code.google.com/p/android-query/wiki/AsyncAPI#XML)
  * [Chaining](#Chaining.md)
  * [Binding](#Binding.md)
  * [Authentication](http://code.google.com/p/android-query/wiki/Authentication)
  * [In-app Version Check](http://code.google.com/p/android-query/wiki/Service)
  * [Alleviate Fragmentation](#Alleviate_Fragmentation.md)
  * [Multiple UI, One Piece of Code](#Multiple_UI,_One_Piece_of_Code.md)
  * [Extendable](#Extendable.md)
  * [Light Weight](#Light_Weight.md)
  * [Non-intrusive](#Non-intrusive.md)
  * Open Source


### Less Code ###

AQuery allows the developer to be more expressive and write-less/do-more. Simpler code is easier to read and maintain.

Compare the length of these pieces of code that does the same thing.

**Before AQuery:**
```
public void renderContent(Content content, View view) {
	
	
	ImageView tbView = (ImageView) view.findViewById(R.id.icon); 
	if(tbView != null){
		
		tbView.setImageBitmap(R.drawable.icon);
		tbView.setVisibility(View.VISIBLE);
		
		tbView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					someMethod(v);
				}
			});
		
	}
	
	TextView nameView = (TextView) view.findViewById(R.id.name);   	
	if(nameView != null){
		nameView.setText(content.getPname());
	}
	
	TextView timeView = (TextView) view.findViewById(R.id.time);  
	
	if(timeView != null){
		long now = System.currentTimeMillis();
		timeView.setText(FormatUtility.relativeTime(now, content.getCreate()));
		timeView.setVisibility(View.VISIBLE);
	}
	
	TextView descView = (TextView) view.findViewById(R.id.desc);   	
	
	if(descView != null){
		descView.setText(content.getDesc());
		descView.setVisibility(View.VISIBLE);
	}
}

```

**With AQuery:**
```

public void renderContent(Content content, View view) {
	
	AQuery aq = new AQuery(view);
	
	aq.id(R.id.icon).image(R.drawable.icon).visible().clicked(this, "someMethod");	
	aq.id(R.id.name).text(content.getPname());
	aq.id(R.id.time).text(FormatUtility.relativeTime(System.currentTimeMillis(), content.getCreate())).visible();
	aq.id(R.id.desc).text(content.getDesc()).visible();		
	
	
}

```



### AJAX Callback ###

[See Ajax Doc](http://code.google.com/p/android-query/wiki/AsyncAPI)

Asynchronous AJAX or RPC calls are simple.

#### JSON Example ####
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

Features:
  * JSON
  * HTML/XML
  * byte[.md](.md)
  * Http POST, Multipart POST
  * Caching
  * Google Service Authentication

[See Ajax Doc](http://code.google.com/p/android-query/wiki/AsyncAPI)

Related Blog: [XML Parsing](http://blog.androidquery.com/2011/09/simpler-and-easier-xml-parsing-in.html)

### Image Loading ###

[See ImageLoading Doc](http://code.google.com/p/android-query/wiki/ImageLoading)

AQuery supports easy asynchronous image loading from network, with automatic file and memory caching.

```
//fetch and set the image from internet, cache with file and memory 
aq.id(R.id.image1).image("http://www.vikispot.com/z/images/vikispot/android-w.png");
```

Images are cached under the app's cache/aquery folder.

Getting previously cached file:
```
//returns the cached file by url, returns null if url is not cached
File file = aq.getCachedFile(url);
```

Features:
  * Simple
  * Memory & File Caching
  * Down Sampling
  * Zoomable (WebView)
  * Fallback Image
  * Preloading
  * Animation
  * Dynamic Aspect Ratio
  * Avoid Duplicated Simultaneous Fetches
  * Custom Callback


[See ImageLoading Doc](http://code.google.com/p/android-query/wiki/ImageLoading)

Related Blog: [Downsampling](http://blog.androidquery.com/2011/05/down-sample-images-to-avoid-out-of.html)

### Chaining ###

All "set" methods in AQuery returns itself. Starts chaining!

```
String name = "My name in black text, red background, visible, and invoke nameClicked when clicked";
aq.id(R.id.name).text(name).background(R.color.red).textColor(R.color.black).enabled(true).visible().clicked(this, "nameClicked");
```

### Binding ###

AQuery makes binding listeners to views simple and easy.

```
@Override
protected void onCreate(Bundle savedInstanceState){
  	
  	//set content view here...
  	
	AQuery aq = new AQuery(this);	
	
	aq.id(R.id.button).clicked(this, "buttonClicked");
	aq.id(R.id.list).itemClicked(this, "itemClicked")
}

public void buttonClicked(View view){

	//a button is clicked

}

public void itemClicked(AdapterView<?> parent, View v, int pos, long id) {
		
	//list item is clicked
	
}
```

### Alleviate Fragmentation ###

Features in new API are great, but majority of mobile devices are not updated with the latest API. See [Android Platform Versions Distribution](http://developer.android.com/resources/dashboard/platform-versions.html)

AQuery alleviate this issue by dynamically inspecting the Android framework, and make new API features available to code that are compiled for lower API. If the feature is not available, AQuery simply ignores the request.

**Enable hardware acceleration with API 4:**
```
@Override
protected void onCreate(Bundle savedInstanceState){
  	
  	//My API level 4 onCreate setup here... 
  	
	AQuery aq = new AQuery(this);	
	
	//Enable hardware acceleration if the device has API 11 or above	
	aq.hardwareAccelerated11();
	
}

```

**Enable activity transition animation in API 4:**
```
@Override
protected void onCreate(Bundle savedInstanceState){
  	
  	AQuery aq = new AQuery(this);	
	
	//Override activity transition for device with API level 5	
	aq.overridePendingTransition5(R.anim.slide_in_right, R.anim.slide_out_left)
  	
  	//API level 4 onCreate setup here... 
  	
}
```

**Invoke any method safely:**
```

//here we invoke the getTag method of a view

//same as calling public Object getTag()
String tag1 = (String) aq.invoke("getTag", null);

//same as calling public Object getTag(int key)
String tag2 = (String) aq.invoke("getTag", new Class[]{int.class}, R.id.tag);		

```

Related Blog:
[Android Fragmentation & Target Build Level](http://android-query.blogspot.com/2011/05/android-fragmentation-and-buildtarget.html)

### Multiple UI, One Piece of Code ###

With the waves of new Tablet devices, Android apps will need to support a wider range of screen sizes. Developers might need to design different UI layout for phone and tablets, but do we need to write a different set of code also?

AQuery allows developers to manage different layouts with the same piece of code. If a view is not in a layout, AQuery will just ignore all the operations performed on the view.

```
public void renderContent(Content content, View view) {

	//this is a phone!

	//this view exists
	aq.id(R.id.textInMobileLayoutOnly).text("Welcome to my Mobile App!");
	
	//this button exist in tablet layout only, but it's ok
	//AQuery will ignore all the operations on this view
	aq.id(R.id.butttonInTabletOnly).text("Open a new tab!");	

}
```


### Extendable ###

Extending AQuery for your need is simple.

```

public class MyQuery extends AbstractAQuery<MyQuery>{

	public MyQuery(View view) {
		super(view);
	}

	public MyQuery(Activity act) {
		super(act);
	}
	
	public MyQuery myMethod(String text){
		
		if(view != null){		
			//do something to the view		
		}
		return this;
	}
	
}

```

If you like to give back to the community, fork AQuery at [GitHub](https://github.com/androidquery/androidquery) and submit a pull request.

See [Contribution](http://code.google.com/p/android-query/wiki/Contribution)

### Light Weight ###

Memory is scarce for mobile apps. The AQuery lib is standalone and relatively small and optimized with proguard (~60k). The actual increase in apk size will be even smaller.

Related Blog:
[Android ProGuard Optimization](http://android-query.blogspot.com/2011/06/android-optimization-with-proper.html)

### Non-intrusive ###

AQuery has no dependency and does not require developers to inherit specialized views or activities. Developers are free to use AQuery along with any other library without any conflict.

### Philosophy ###

AQuery's philosophy is to make common tasks as simple as possible in a non-intrusive manner.

### History ###

AQuery is inspired by jQuery, a javascript framework for web site, hence the name "Query".

### API Demo App ###

Demo with AQuery code snippets is available on [Android Market](https://market.android.com/details?id=com.androidquery).

https://market.android.com/details?id=com.androidquery

### Demo App ###

Try out our [SimpleFB (Facebook)](https://market.android.com/details?id=com.androidquery.facebook) written with AQuery.


### Contribute! ###

AQuery is a new library and need nourishment from the community.

Git users: fork [androidquery](https://github.com/androidquery/androidquery) at GitHub!

### Subscribe AQuery News ###

New release will be posted here:
  * [Android-Query Group](http://groups.google.com/group/android-query)
  * [Android-Query Facebook Page](http://www.facebook.com/pages/Android-Query/205050232863343)
  * [Android-Query Twitter](http://twitter.com/AndroidQuery)

Subscribe to join the AQuery discussion.

## Support ##

If you like AQuery's approach to simplify Android development, please spread the words to other Android developers.

Help us by staring this project and share us on Twitter.

&lt;wiki:gadget url="http://android-query.googlecode.com/svn/trunk/demo/gadget/social.xml?v=4" height="115" width="750" border="0" /&gt;