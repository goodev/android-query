# Error Handling #

## Android Main Thread Exception ##

Developer should make sure all exceptions are handled properly in the main thread. If an exception is thrown in the main thread, Android OS will prompt the user and force shutdown the current activity, resulting in very bad user experience.

## AQuery Error Handling ##

It is recommended to handle exceptions that can occur in the main thread, but unexpected runtime exceptions and errors do occur.

In order to avoid crashing, AQuery handles all the exceptions that could be thrown from registered ajax callback and listener methods.

### Uncaught Exception Handler ###

Uncaught exceptions can be handled by registering a handler.

```
AQUtility.setExceptionHandler(new UncaughtExceptionHandler() {
	
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {

		//handler code
		
	}
});

```

Any exception that would normally thrown in the main thread will be handled here. The default handler will simply log the error if debugging is on, and does nothing otherwise.

Note that you **should not use this method to handle normal user exception.**

System exceptions are also handled with this method. For example, the device might run out of space for caching, and this method could be invoked many times with IO exceptions for each ajax request.

### Error Reporting (BugSense example) ###

Error reporting is important for developers to get feedback and fix bugs. The uncaught exception handler can be used to report errors to error reporting service such as [Bugsense](http://www.bugsense.com/).

Here's a complete example of setting up BugSense with AQuery.

```

public class MainApplication extends Application{

	@Override
    public void onCreate() {     
       
	BugSenseHandler.setup(getApplicationContext(), API_KEY);
		
	AQUtility.setExceptionHandler(new UncaughtExceptionHandler() {
			
		@Override
		public void uncaughtException(Thread thread, Throwable ex) {

				
			AsyncTask<Throwable, Void, Void> task = new AsyncTask<Throwable, Void, Void>() {

				@Override
				protected Void doInBackground(Throwable... ex) {
					try {
						BugSense.submitError(0, new Date(), Log.getStackTraceString(ex[0]));
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;
				}
			};
				
			task.execute(ex);
				
		}
	});
        
        super.onCreate();
    }
	
	
	
}

```

### Reporting outside of AQuery scope ###

```

AQUtility.report(ex);

```

If you already setup reporting mechanism and want to report an exception with the uncaught exception handler, use the AQUtility.report() method.

## Debuging ##

Debug logging can be turned on with setDebug().

```
AQUtility.setDebug(true);
```

AQuery will log all the exceptions and important events such as an http request url and response, etc. When in doubt, turn on debug logging and see if there's exception happening behind the scene.

Note: Make sure debugging is off in your production app.