&lt;wiki:gadget url="http://android-query.googlecode.com/svn/trunk/demo/gadget/social.xml?v=2" height="115" width="700" border="0" /&gt;

# XML Parsing #

One of the key feature of AQuery is asynchronous http fetch, which often is used to invoke REST type of services over the internet. These types of API/services, such as Facebook, Twitter, or Picasa (example provided in this post) usually serves results in XML and/or JSON formats for apps to consume.

For this purpose, AQuery wants to provide a simple, easy, and light-weight method to parse these XML responses. Such method must be independent on third party library and supports API level 4.

### XML Dom ###

XmlDom is a specialized class for simple and easy XML parsing, designed to be used in basic Android api 4+ runtime without any dependency, and integrated directly with AQuery’s ajax method. [javadoc](http://android-query.googlecode.com/svn/trunk/javadoc/index.html?com/androidquery/util/XmlDom.html)

### Example ###

The following piece of code fetch and parse the picasa featured photos feed with build in AQuery ajax and XMLDom parsing.

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
    
}}


```

### Methods ###

XMLDom methods are simple and easy way to extract information from XML doc. The tags() and children() method allow us to navigate to the desired nodes, and the text() and attr() methods let us extract the XML values as a String.

```
//tag(), tags()

//return a list of "entry" nodes
List<XmlDom> entries = xml.tags("entry");  
 
//child(), children()
 
//child is similar to tag, except it only return child nodes immediately under the parent
XmlDom title = entry.child("title");
 
//text()
 
//extract the text value of the title tag
String text = title.text();
 
//attr()
 
//extract the src attribute of a content tag
String image = content.attr("src");

//Serialization (beta)

//Convert a XmlDom object to a XML string
String rawXml = xml.toString();

```

### DOM vs SAX & PullParser ###

The XmlDom object, as implied, parse the XML into a Xml Document that entirely resides in memory.

For parsing XML documents bigger than 1M, using XmlDom might not be suitable as memory is limited on mobile devices.

### Advance Usage ###

XmlDom is a simple parser, if more advance features are required, you can use the getElement() method to retrieve the underlying org.w3c.dom.Element for each node.