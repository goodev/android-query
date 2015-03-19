&lt;wiki:gadget url="http://android-query.googlecode.com/svn/trunk/demo/gadget/social.xml?v=2" height="115" width="700" border="0" /&gt;

# Contribution #

AQuery is open sourced and welcome any contribution.

## Github & Pull Request ##

The easiest way to contribute is to fork AQuery source at [GitHub](https://github.com/androidquery/androidquery) and send us a pull request.

## Pull Requests ##

AQuery is designed to be used as a single jar file. Pull requests that required us to break this rule will not be accepted.

There are couple criteria for us to merge a pull request:

  * No Dependency - It must not dependent on any 3rd party library.
  * No Resource - It must not require any additional resource (/res).
  * General Usage - The method or functionality must be generic to the public with at least 3 use cases.

## Extension ##

It's recommended that developers extend the AQuery object and put their app specific logic in the custom class.

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

Extending the AQuery object is an excellent way to add custom functionality without changing the AQuery source code directly.

When you feel a particular function is ready and want to contribute back to AQuery main source, "promote" the method to the AbstractAQuery main class and send us a pull request for review!

## Questions & Help ##

Post your question at our [group](http://groups.google.com/group/android-query). Also subscribe to AQuery [Facebook Page](http://www.facebook.com/pages/Android-Query/205050232863343) or [Twitter](http://twitter.com/#!/AndroidQuery) for our latest news and updates.