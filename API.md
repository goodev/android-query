&lt;wiki:gadget url="http://android-query.googlecode.com/svn/trunk/demo/gadget/social.xml?v=2" height="115" width="700" border="0" /&gt;


# AQuery #


## Core Concept ##

There's only 1 main class in AQuery. That is, the AQuery class.

AQuery object has two states: "root" and the "view".

Let's start with a simple example:

Layout:
```
<LinearLayout 
    xmlns:android="http://schemas.android.com/apk/res/android"    
    android:layout_width="fill_parent"
    android:layout_height="fill_parent" android:orientation="vertical">
    
   <TextView
	    android:id="@+id/text"
	    android:layout_width="fill_parent"
	    android:layout_height="wrap_content"	    
	    android:text="Text"
	></TextView>
	
	<Button 
		android:id="@+id/button" 
		android:layout_width="wrap_content" 
		android:layout_height="wrap_content" 
		android:text="Button" 
	></Button>
	
</LinearLayout>

```

Activity:
```

public class TestActivity extends Activity {
	
	private AQuery aq;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		  
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);

		//use activity to create the AQuery object
		//this set the activity as the "root"	
  
		aq = new AQuery(this);
	  
		//id(R.id.text) - point the current "view" to the TextView with id=R.id.text
		//text("Hello") - call the corresponding methods to change it

		aq.id(R.id.text).text("Hello");
	  
		//id(R.id.button) - switch to a different "view"
		//text("Click Me") - update the button text
		//clicked(this, "buttonClicked") - assign a listener to the button, pass in the method name

		aq.id(R.id.button).text("Click Me").clicked(this, "buttonClicked");


		//this view does not exist in the layout
		//this shows how to check view existence and how to get the underlying view
		//if operation is performed on a non-existent view, all operations are ignored and return immeidately
		aq.id(R.id.image);
		if(aq.isExist()){
	
			ImageView imageView = aq.getImageView();
	
			//perform other non-AQuery operations
	
		}	  
		
	}
	
	public void buttonClicked(View button){
		
		//update the text
		aq.id(R.id.text).text("Hello World");
		
	}
	
}

```

AQuery usage:

  1. Create an AQuery object with an activity, the root view of a fragment, a list item view (or any view container), or a context (for background ajax requests)
  1. Use the id() or find() methods to pick the current "view"
  1. Call the appropriate methods to update the view
  1. Repeats #2

That's it!

## Fragment Support ##

AQuery can be initialized with the root view of a fragment. A good place to initialize the AQuery object is within the onCreateView method of the Fragment.

Here's an example:

```

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
    	View view = inflater.inflate(getContainerView(), container, false);        	
		
    	aq = new AQuery(getActivity(), view);
	return view;
		
    }

```

Note that the views of the activity might not be fully initialized within onCreateView() of a fragment. You might want to perform initialization work in onActivityCreated() instead.

## Reference ##

AQuery object holds a reference to the context upon initialization.
**Do not store AQuery references globally (static).**

The AQuery object is extremely light weight and can be created and disposed at will.

## Methods ##

Please refer to [javadoc](http://android-query.googlecode.com/svn/trunk/javadoc/com/androidquery/AbstractAQuery.html) for detail method signatures.