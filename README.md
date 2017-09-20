# MHGenericAdapter
a generic adapter for recycler view

### Installing

Only add maven url to your project-level build.gradle

```
maven { url "https://jitpack.io" }
```

And add gradl url to your app-level build.gradle

```
compile "com.github.MH480:MHGenericAdapter:0.1.1"
```
### Advantages
    * You can get rid of creating so many adapter classes for each recycler view
    
    * you can code less and use more
    
    * you can have onClick and onLongClick functionalities
    
    * you will be albe to have selectable items
    
    * Empty view is added,so you can have recycler view with empty view
    
    * simply bind your layout and data using @MHBindView annotation
    
    * simply make a view clickable using @MHBindView annotation
    
After your build finished, you can use library as showen below

## Getting Started

## Usage
you can use it like simple adapter but you must pass 3 steps

first define the items model as seperate java class

here is my ```ItemModel``` class
```MHGenericAdapter
public class ItemModel
{
    public int id;
    public String title;
 }
```

second create the viewholder class as seperate java class as below
the ```ViewHolderModel```
```
@MHBindView(R.layout.item) // defines the layout id
public class ViewHolderModel
{
    @MHBindView(value = R.id.tvID) // defines the id of the view
    public TextView tID;
    @MHBindView(value = R.id.btnTitle,isClickable = true) //defines the id of the view and adds click listener to it
    public Button bTitle;
}
```
third step is the item.xml file
```
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical">
    
    <TextView
            android:id="@+id/tvID"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"/>
            
            
    <Button
            android:id="@+id/btnTitle"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"/>
    
</FrameLayout>
    
```
the ```ItemModel```class is my items class for adapter items

and the ```ViewHolderModel``` class is my view holder class

and you must pass ViewHolderModel.class as constructor parameter


finaly you can create new instace of #MHGenericAdapter  like below

```
  MHRecyclerAdapter<ItemModel,ViewHolderModel> mAdapterRequests;
  mAdapterRequests = new MHRecyclerAdapter<ItemModel,ViewHolderModel>(ViewHolderModel.class);
```

## Deployment

We are still working

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/MH480/MHGenericAdapter/0.1.1). 

## Authors

* **MOhammad Hoseini** - *Initial work* - [sanwix](https://github.com/MH480)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the Apache.2 License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone who's code was used
* Inspiration
* etc
