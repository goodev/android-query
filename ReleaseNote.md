# Release Notes #

## 0.26.8 ##

Download:

https://github.com/androidquery/androidquery/releases/tag/0.26.8

Changes:
  * Image loading: If API level 17 or above will use inputSharable = false as an option. This is a work around for EMFILE file descriptions limit introduced since Kitkat.

Feature:
  * Add item long click binding method

## 0.26.7 ##

Fix:
  * Fix critical bug that might cause partially downloaded file to be cached. Temp files are now created and renamed to destination files when transfer is completed.

Feature:
  * Add textColorId method to set text color by resource id
  * Add redirect() setting to AjaxCallback (default is true, to follow 302 redirect)
  * Add shortcut method to post with JSON
  * Add static proxy setting support

Changes:
  * If image is fetched over network and it's malformed (or not an image but with response code 2xx), cache will be invalidated automatically.
  * When user agent is not set and gzip is used, "gzip" will be used as user agent to work around Google servers (GAE and others) not gzip response when User-Agent header is missing.


## 0.25.9 ##

Feature:
  * Add visibility method to set visibility directly
  * AjaxCallback.getActiveCount() returns number of active ajax threads
  * Added retry to AjaxCallback

Fix:
  * Add "Content-Type: application/octet-stream" for multipart post binary fields
  * Fix gzip inputstream not properly closed issue
  * Support invalid domain with underscore.
  * Fix round corner images not fallback properly with network error
  * Fix progress bar not clearing for duplicate image ajax requests
  * Fix auth and progress param overwritten by aq.ajax(callback)

Changes:
  * When external storage is not available for temp storage, internal storage is used instead. App that process large ajax requests should still request external storage write permission.


## 0.24.4 ##

Feature:
  * Support progress bar, progress dialog for multipart-upload
  * Add id(String tag) method for selecting view by tag
  * Automatic fallback to normal connection when proxy failed
  * Support proxy with ajax image calls
  * Add convenient proxy method to aq

Fix:
  * Fix issue when images not loading when an identical image is loaded but canceled due to activity termination

Changes:
  * Multipart upload uses chunk upload
  * Content-Type "application/octet-stream" field header added for binary data with multipart POST

## 0.23.15 ##

Feature:
  * Round corner image support [doc](http://code.google.com/p/android-query/wiki/ImageLoading#Rounded_Corner)
  * Image aspect ratio anchor support [doc](http://code.google.com/p/android-query/wiki/ImageLoading#Anchor)
  * Ajax and image methods now support absolute file path as an url.
  * Add warnings to log for common mistakes such as calling ajax without setting response type or calling ajax with finished activity.
  * Support progress bar, progress diablog bar, and activity progress bar
  * Support HTTP DELETE method
  * Support individual ajax request timeout with callback.timeout()
  * Support ajax network abort with callback.abort()
  * Added InputStream param support for multipart post.
  * Support basic HTTP PUT method (experimental)

Bug Fix:
  * Fix error response gzip encoding issue when response is not 2xx
  * Fix shouldDelay incorrectly return true when down-sampled and rounded corner images are memory cached
  * Fix inputstream types closed prematurely for synchronous requests
  * Fix 0 length file created for 404 error for file ajax type
  * Fix basic auth multipart missing auth header issue
  * Twitter auth now support multipart photo upload POST
  * Fix issue where the inflate() method hold reference to current context

Changes:
  * Disable USE\_EXPECT\_CONTINUE for HttpClient with POST (not multi) to improve overall response time
  * Optimize image loading to support offscreen images (PageAdapter, etc)
  * Image requests are now stored in cache then loaded instead of allocating temporary byte[.md](.md) in memory. This behavior allow faster decoding, reduced memory usage, but with longer first time delay due to file storage time. This behavior is subject to change in the future. Use BitmapAjaxCallback.setDelayWrite(true) to revert this behavior.


## 0.22.10 ##

Feature:
  * Add image fallback type PRESET
  * Add ajax type inputstream
  * Add ajax type file
  * Add auth type for service Google Doc List
  * Add file download [doc](http://code.google.com/p/android-query/wiki/AsyncAPI?ts=1335460094&updated=AsyncAPI#File_Download)
  * Add automatic encoding detection for String ajax type [doc](http://code.google.com/p/android-query/wiki/AsyncAPI#Encoding_(BETA))
  * Add Activity.setProgressBarIndeterminateVisibility() support for .progress() to show ajax progress.

Bug Fix:
  * Fix shouldDelay issue with ListView with custom headers
  * File name is now sent with multipart post with file parameter

Build:
  * The auth plugin will be part of the "full" jar build instead to reduce the number of builds




## 0.21.5 ##

Feature:
  * Automatic gzip
  * Add expandable list adapter support.
  * Add twitter auth token retrieve method.
  * Add longClicked methods similar to clicked.
  * Add shouldDelay method for Gallery, ExpandableListView.
  * Add more efficient shouldDelay method for ListView
  * Add getToken() to Facebook handle.
  * Add getToken() and getSecret() to TwitterHandle.

GZip is automatically on for all ajax calls. No settings required. Use the setGZip(false) method to disable gzip.

Deprecated old ListView should delay method.

## 0.20.5 ##

Feature:
  * Support XmlPullParser ajax type [doc](http://code.google.com/p/android-query/wiki/AsyncAPI?ts=1328249717&updated=AsyncAPI#XML_Dom)
  * Http request cookies [doc](http://code.google.com/p/android-query/wiki/AsyncAPI#Http_Headers)
  * Http response cookies [doc](http://code.google.com/p/android-query/wiki/AsyncAPI#Http_Headers)
  * Http response headers [doc](http://code.google.com/p/android-query/wiki/AsyncAPI#Http_Headers)
  * Support progress dialog for ajax progress [doc](http://code.google.com/p/android-query/wiki/AsyncAPI#Progress)

Update:
  * ajax() http post now takes type Map<String, ?> including the old type Map<String, Object>
  * http multipart post now supports header and cookie


## 0.19.3 ##

New Feature:
  * [Long URL (2000+ length)](http://code.google.com/p/android-query/wiki/AsyncAPI#Long_URL_(2000+_length))
  * [Facebook, Twitter, BasicAuth](http://code.google.com/p/android-query/wiki/Authentication)
  * [Uncaught Error Handler](http://code.google.com/p/android-query/wiki/ErrorHandling)
  * Better GoogleChart image url support


## 0.18.5 ##

Fixed fallback issue with AQuery.GONE and AQuery.INVISIBLE type.

## 0.18.3 ##

New Feature:
  * [Google Service Authentication](http://code.google.com/p/android-query/wiki/Authentication)
  * [In-App Version Checking](http://code.google.com/p/android-query/wiki/Service)
  * [Custom HttpEntity](http://code.google.com/p/android-query/wiki/AsyncAPI?ts=1325587598&updated=AsyncAPI#Http_POST_(Custom_Entity))
  * [Image Loading - Network Failure Recovery](http://code.google.com/p/android-query/wiki/ImageLoading?ts=1325588160&updated=ImageLoading#Network_Failure_Recovery)

Added methods:
  * inflate
  * dismiss
  * getGallery
  * parent


Bug fix:
  * Padding is now supported for image loading with aspect ratio.




## 0.16.12 ##


Added methods:
  * getText
  * getSelectedItem
  * webImage
  * block (AjaxCallback)

New Feature:
  * [Zoomable Web Image](http://code.google.com/p/android-query/wiki/ImageLoading#Zoomable_Web_Image)
  * [Http Multipart POST](http://code.google.com/p/android-query/wiki/AsyncAPI#Http_POST_(Multipart))
  * [Activity Finish Detection](http://code.google.com/p/android-query/wiki/AsyncAPI#Activity_Finished_Detection)


## 0.15.7 ##

Changes:

  * Remove support for image button.
  * Optimize memory usage and speed when bitmap is memory cached
  * Invalidate memcache when network is restored from previous images fetch network errors
  * Optimize memory usage for ajax requests

Added methods:
  * recycle
  * encoding (AjaxCallback)

Fix:
  * Issue where invalidate throws null pointer exception when url is not cached



## 0.14.12 ##

10/5/2011

Added methods:
  * setSelection(int position)
  * invalidate(String url)
  * AQUtility.setCacheDir(cacheDir);
  * progress(View view)
  * id(View view)
  * getTag(int id)
  * animate(int animId)
  * animate(int animId, AnimationListener listener)
  * animate(Animation anim)
  * getCachedImage(int resId)
  * image(Bitmap bm, float ratio)
  * shouldDelay(View convertView, ViewGroup parent, String url, float velocity)
  * makeCachedFile(String url, String filename)
  * click
  * longClick


Added support for JSONArray.class as ajax response type. Added methods to support easier coding with ViewHolder pattern. Added way to specify cache directory (can be on SDCard). Added way to invalidate a cached url. Added method to detect list scroll speed to delay loading an list item resources.

Thanks contribution from:
marcosbeirigo,
Sangmin Ryu, neocoin

## 0.13.5 ##

9/27/2011

Added methods:
  * progress
  * getSpinner
  * Object getSelectedItem()
  * itemSelected(Object handler, String method)
  * itemSelected(OnItemSelectedListener listener)
  * cache(String url, long expire)

Added progress method to show loading progress. Added cache method to recache an url. Added various methods to support Spinner operations.

## 0.12.5 ##

9/20/2011

Fix issue where BitmapAjaxCallback custom callback are not invoke when image is cached in memory.

## 0.12.2 ##

9/16/2011

Added XmlDom class for simple and easy XML parsing. "ajax" methods now support XmlDom.class as content type.

## 0.11.5 ##

9/14/2011

Added method:
  * ajax(String url, Class type, long expire, AjaxCallback callback)
  * ajax(String url, Class type, long expire, Object handler, String callback)

Added file caching capability to ajax requests.

Added Google Service authentication method (BETA).

## 0.10.3 ##

9/8/2011

Added method:

  * image(String url, boolean memCache, boolean fileCache, int targetWidth, int fallbackId, Bitmap preset, int animId, float ratio)

Added dynamic or predefined aspect ratio feature for image loading.

## 0.9.2 ##

9/6/2011

Fixes:

  * Fix issue where api level 4 cannot compile with AjaxCallback due to use of AccountManager

## 0.9.1 ##

9/3/2011

Added method:

  * width(int dip)
  * height(int dip)
  * image(String url, boolean memCache, boolean fileCache, int targetWidth, int fallbackId, Bitmap preset, int animId)
  * getCachedImage(String url)
  * getCachedImage(String url, int targetWidth)

Add image loading function with "preloaded" image option and image animation.

## 0.8.2 ##

8/4/2011

Added method:

  * ajax(AjaxCallback callback)
  * ajax(String url, Map<String, Object> params, Class type, AjaxCallback callback)
  * ajax(String url, Map<String, Object> params, Class type, Object handler, String callback)

New ajax methods using the http post method.

## 0.6.1 ##

7/21/2011

Updated method:

  * image(String url, boolean memCache, boolean fileCache, int targetWidth, int resId)

Added fallback options to make image view invisible or gone.

  * image(File file, int targetWidth)
  * image(File file, boolean memCache, int targetWidth, BitmapAjaxCallback callback)

Added image loading from file with down sampling.

## 0.5.1 ##

7/16/2011

Added method:

  * AQuery(Context context)
  * id(int... path)
  * image(String url, boolean memCache, boolean fileCache, int targetWidth, int resId)
  * image(String url, boolean memCache, boolean fileCache, int targetWidth, int resId, BitmapAjaxCallback callback)
  * setOverScrollMode9


## 0.3.3 ##

7/8/2011

Added method:

  * checked (compound button, checkbox)
  * getChecked (compound button, checkbox)
  * clickable (view clickable)

## 0.3.1 ##

7/2/2011

Added method:

  * textChanged (listen to change of edit text)
  * getCachedFile (get previously cached file by ajax requests)

## 0.2.3 ##

6/13/2011

Optimized with proguard to reduce size.

Added methods:

  * ajax(java.lang.String url, java.lang.Class type, Object handler, String callback)


## 0.2.0 ##

6/8/2011

Added methods:

  * getContext
  * ajax
  * ajaxCancel
  * image(java.lang.String url)
  * image(java.lang.String url, boolean memCache, boolean fileCache)


## 0.1.0 ##

Added methods:

  * getProgressBar
  * getButton
  * getCheckBox
  * getListView
  * getGridView
  * getWebView
  * clicked(OnClickListener listener)
  * itemClicked(OnItemClickListener listener)