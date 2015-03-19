&lt;wiki:gadget url="http://android-query.googlecode.com/svn/trunk/demo/gadget/social.xml?v=2" height="115" width="700" border="0" /&gt;

## Authentication ##

AQuery provides an easy and simple authentication mechanism for Google and other OAuth enabled services. The featured is designed for developers who prefer low level api access to Google API's, Facebook, and Twitter, etc...

## Permission ##

For Google service, include these permissions:
```
<uses-permission android:name="android.permission.GET_ACCOUNTS" />	
<uses-permission android:name="android.permission.USE_CREDENTIALS" />		
```

## Dependency ##

BasicAuth, Facebook, and Twitter requires [android-query-auth.jar](http://code.google.com/p/android-query/downloads/list).

Twitter also depends on the [SignPost](http://code.google.com/p/oauth-signpost/) library:

  * [signpost-commonshttp4-1.2.1.1.jar](http://code.google.com/p/oauth-signpost/)
  * [signpost-core-1.2.1.1.jar](http://code.google.com/p/oauth-signpost/)


## Code ##

AQuery handles the authentication life cycle and token management automatically. Simply provide an "account handle" with the .auth() method.

Here's a complete authentication example using Google's PICASA service:

```

GoogleHandle handle = new GoogleHandle(this, AQuery.AUTH_PICASA, AQuery.ACTIVE_ACCOUNT);

String url = "https://picasaweb.google.com/data/feed/api/user/default?alt=json";
aq.auth(handle).ajax(url, JSONObject.class, new AjaxCallback<JSONObject>(){
	@Override
	public void callback(String url, JSONObject object, AjaxStatus status) {
		System.out.println(object);
	}
});	


```

## Account Handle ##

Account handle holds the current authentication token which are cached on the device. Account handle should be shared by all ajax calls within an activity that requires the same type of authentication.

## Supported Google Service ##

Note: **Google Account Manager requires API 7+.**

The following Google Service is supported, with their corresponding token types:

| **Name** | **Type** | **Google's API** |
|:---------|:---------|:-----------------|
| G Reader | AQuery.AUTH\_READER | [API](http://code.google.com/p/pyrfeed/wiki/GoogleReaderAPI) |
| Picasa | AQuery.AUTH\_PICASA | [API](http://code.google.com/apis/picasaweb/docs/2.0/reference.html) |
| Spreadsheets |AQuery.AUTH\_SPREADSHEETS | [API](http://code.google.com/apis/spreadsheets/data/3.0/reference.html) |
| Youtube | AQuery.AUTH\_YOUTUBE | [API](http://code.google.com/apis/youtube/2.0/developers_guide_protocol_audience.html) |
| Analytics | AQuery.AUTH\_ANALYTICS | [API](http://code.google.com/apis/analytics/docs/mgmt/v3/mgmtReference.html) |
| Blogger| AQuery.AUTH\_BLOGGER | [API](http://code.google.com/apis/blogger/docs/2.0/json/using.html) |
| Calendar | AQuery.AUTH\_CALENDAR | [API](http://code.google.com/apis/calendar/v3/getting_started.html) |
| Contacts| AQuery.AUTH\_CONTACTS | [API](http://code.google.com/apis/contacts/docs/3.0/reference.html) |
| Maps | AQuery.AUTH\_MAPS | [API](http://code.google.com/apis/maps/) |


## Facebook ##

```
public void auth_facebook(){
	
	FacebookHandle handle = new FacebookHandle(this, APP_ID, PERMISSIONS);
	
	String url = "https://graph.facebook.com/me/feed";
	aq.auth(handle).progress(R.id.progress).ajax(url, JSONObject.class, this, "facebookCb");
	
}

```

### Single Sign On (SSO) ###

By default, the FacebookHandle uses the web login for authentication. Single sign on (SSO) can be used for a simpler user authentication process. If available, the phone's Facebook user account will be used and user will not need to enter username and password.

Because SSO uses Facebook's login activity, a little more work is required:

```

private FacebookHandle handle;
private final int ACTIVITY_SSO = 1000;

public void auth_facebook_sso(){
	
	handle = new FacebookHandle(this, APP_ID, PERMISSIONS);
	handle.sso(ACTIVITY_SSO);
	
	String url = "https://graph.facebook.com/me/feed";
	aq.auth(handle).progress(R.id.progress).ajax(url, JSONObject.class, this, "facebookCb");
	
}

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	
	switch(requestCode) {
		
    	case ACTIVITY_SSO: {
    		if(handle != null){
    			handle.onActivityResult(requestCode, resultCode, data);	  
    		}
    		break;
    	}
    	
	}
}



```



### Permission ###

```
private static String PERMISSIONS = "read_stream,read_friendlists,manage_friendlists,manage_notifications,publish_stream,publish_checkins,offline_access,user_photos,user_likes,user_groups,friends_photos";
	
```

Permission is Facebook open graph's "scope", as described [here](http://developers.facebook.com/docs/reference/api/permissions/). Permission terms are separated by comma.

Note that Facebook access token expire every two hours unless "offline\_access" is granted.

### Expire Check ###

```

FacebookHandle handle = new FacebookHandle(this, APP_ID, PERMISSIONS){
	
	@Override
	public boolean expired(AbstractAjaxCallback<?, ?> cb, AjaxStatus status) {
		
		//custom check if re-authentication is required
		if(status.getCode() == 401){
			return true;
		}
		
		return super.expired(cb, status);
	}
	
};


```

Facebook doesn't seem to have a standard to indicate when re-authentication is required. Token can be expired by time or when user changed the app permissions on Facebook. Authentication is often required when the app configuration changed.

The default FacebookHandle checks if the response code is 400, 401, or 403 along with some special cases handling. If further customization is required, extend the FacebookHandle and overwrite the expired() method. If expired() returns true, the handle will request the user to re-authenticate, if needed.

## Twitter ##

```

public void auth_twitter(){
	
	TwitterHandle handle = new TwitterHandle(this, CONSUMER_KEY, CONSUMER_SECRET);
	
	String url = "http://twitter.com/statuses/mentions.json";
	aq.auth(handle).progress(R.id.progress).ajax(url, JSONArray.class, this, "twitterCb");
	
	
}

```

Use the TwitterHandle to authenticate with OAuth1. Consumer key and consumer secret are provided from Twitter's app development console.

### Callback Url ###

```
04-18 15:40:47.282: W/DefaultRequestDirector(1043): Authentication
error: Unable to respond to any of these challenges: {oauth=WWW-
Authenticate: OAuth realm="https://api.twitter.com"}
```


Make sure "Callback Url" is being set on Twitter developer console, otherwise will result in authentication error. Callback url can be a dummy url or point to your web server callback.

### Token Only ###

Token and secret can be retrieved as follow.

```
TwitterHandle handle = new TwitterHandle(this, CONSUMER_KEY, CONSUMER_SECRET){
	
	@Override
	protected void authenticated(String secret, String token) {
		AQUtility.debug("secret", secret);
		AQUtility.debug("token", token);
	}
	
};

boolean refreshToken = false;
handle.authenticate(refreshToken);

```



## Basic Auth ##

```

public void auth_basic(){
	
	BasicHandle handle = new BasicHandle("tinyeeliu@gmail.com", "password");
	String url = "http://xpenser.com/api/v1.0/reports/";
	aq.auth(handle).progress(R.id.progress).ajax(url, JSONArray.class, this, "basicCb");
	
}

```

Use the BasicHandle for basic authentication. The default implementation does not handle authentication error (user changed password, etc...).

Developer must check for authentication error (ex. code == 401) and create a new BasicHandle for subsequent requests.

## Extension ##

Developers can extend the authentication framework for other web services by extending AccountHandle.

Documentation pending.

### Dismiss Dialogs ###

Call AndroidQuery.dismiss() in OnDestroy() to dismiss any showing dialogs.

```
aq.dismiss();
```