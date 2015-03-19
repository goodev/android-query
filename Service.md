&lt;wiki:gadget url="http://android-query.googlecode.com/svn/trunk/demo/gadget/social.xml?v=2" height="115" width="700" border="0" /&gt;

# In-App Version Checking #

When a new app version is released, developers often want to notify existing users that an update is available. However, there's no market API to query the version and update information for Android.

A simple solution is to setup a web service for an app, and update the content when an app is uploaded to the market place.

## AQuery Market API ##

AQuery offers a simple web service for any Android app to check against their version info and prompt the user with recent changes and allow user to update if they prefer. The service is free and is packaged with the core AQuery lib.

### Code ###

Invoking the version service is simple. Add these 3 lines in your launching activity. [[javadoc](http://android-query.googlecode.com/svn/trunk/javadoc/com/androidquery/service/MarketService.html)]

```

@Override
public void onCreate(Bundle savedInstanceState) {
	
	super.onCreate(savedInstanceState);
				
	MarketService ms = new MarketService(this);
	ms.level(MarketService.MINOR).checkVersion();
	
}
```

### Recent Changes ###

The recent changes information is fetched directly from the app's main page on the Android market.

Make sure to fill in the "Recent Changes" box when an app is published. The AQuery api will do the rest.

#### Android Market Console ####
![http://www.androidquery.com/z/images/androidquery/market-info.png](http://www.androidquery.com/z/images/androidquery/market-info.png)

#### App dialog ####
![http://www.androidquery.com/z/images/androidquery/app-info.png](http://www.androidquery.com/z/images/androidquery/app-info.png)

### Granularity ###

AQuery provide 3 levels of granularity for prompting the user for updates.

```
ms.level(MarketService.MINOR)
```

For example, when a new feature or a bug fix is released, we might want to prompt the user for update. However, if the release only contains a minor bug fix or optimization, we might not want to interrupt the users.

For this feature to work, the version number of the app must be in the following format:

MAJOR.MINOR.REVISION

For example, the user app version:
3.11.4

Latest version:
3.11.6

A prompt level of MINOR will not prompt the user, because the minor version code (11) is not lower than the latest's (11).

A prompt level of REVISION will triggers a prompt, because the revision code (4) is lower than the latest's (6).

### Internationalization ###

The service supports the locales that Android Market is currently supporting, which are:

English (en) | 日本語 (ja) | français (fr) | suomi (fi) | čeština (cs) | norsk (no) | español (es) | русский (ru) | Deutsch (de) | 한국어 (ko) | italiano (it) | português (pt) | polski (pl) | svenska (sv) | Nederlands (nl) | dansk (da) | 中文（繁體） (zh-TW)

Simplified Chinese (zh-CN) is supported with text taken from the zh-TW locale.

#### App dialog in other locales ####
![http://www.androidquery.com/z/images/androidquery/app-info2.png](http://www.androidquery.com/z/images/androidquery/app-info2.png)

### Non Android Market App ###

Currently the service only supports Google's Android Market and not for other markets such as Amazon. Since apps can have different version codes and apk across different markets, there's no guarantee that all markets will have the same version.

```
   if(isGoogleMarketApp()){
       MarketService ms = new MarketService(this);
       ms.level(MarketService.MINOR).checkVersion();
   }

```

### Customizing Buttons ###

You can override the the default url of "Update" and "Review" with the updateUrl() and rateUrl() methods.

Note: Amazon app store will reject your app if it contains links to the Google Android Market.

### Caching ###

By default, the version information is cached on the device for a few hours. There is at most 2 version checks per day per user to conserve battery. Each version check takes about ~10b (if up to date) to 4kb (depends on message size) of download bandwidth.

### Delay ###

Due to various caching mechanism on server and devices, there could be a few hours delay between publishing your app on Android market and getting the latest in-app version check.

### Dismiss Dialogs ###

Call AndroidQuery.dismiss() to dismiss any showing dialogs.

```
aq.dismiss();
```