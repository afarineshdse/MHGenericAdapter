package mh.sanwix.com.GenericAdapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by m.hoseini on 8/26/2017.
 */

public class MHRecyclerAdapter<Model, VHModel> extends RecyclerView.Adapter<MHViewHolder<VHModel>> implements MHIBaseAdapter<Model>
{
    //region Const Values
    private static final int EMPTY_VIEW_TYPE = 455;
    private static final int MAIN_VIEW_TYPE = 479;
    //endregion

    //region Models
    private Class<VHModel> MyVHolder;
    private List<Model> items;
    private Class<?> MyEmptyVH;

    //endregion

    //region Varriables
    private SparseBooleanArray _selectedItems;
    private List<MyKeyValue> listeners;
    private List<String> propertiesNames;
    private boolean isSelectable;
    private boolean isMultiSelection;
    private boolean hasEmptyView;

    //endregion

    //interface
    private MHIonBindView bindView;

    @LayoutRes
    private int resId;
    @LayoutRes
    private int resId_empty;


    public MHRecyclerAdapter(Class<VHModel> myVHModelClass)
    {

        initilize(getViewID(myVHModelClass), myVHModelClass, false, false, new ArrayList<Model>());
    }

    public MHRecyclerAdapter(Class<VHModel> myVHModelClass, boolean isSelectable)
    {
        initilize(getViewID(myVHModelClass), myVHModelClass, isSelectable, false, new ArrayList<Model>());
    }

    public MHRecyclerAdapter(Class<VHModel> myVHModelClass, boolean isSelectable, boolean isMultiSelection)
    {
        initilize(getViewID(myVHModelClass), myVHModelClass, isSelectable, isMultiSelection, new ArrayList<Model>());
    }

    public MHRecyclerAdapter(Class<VHModel> myVHModelClass, boolean isSelectable, boolean isMultiSelection, List<Model> list)
    {
        initilize(getViewID(myVHModelClass), myVHModelClass, isSelectable, isMultiSelection, list);
    }

    public MHRecyclerAdapter(@LayoutRes int _resId, Class<VHModel> myVHModelClass)
    {
        initilize(_resId, myVHModelClass, false, false, new ArrayList<Model>());
    }

    public MHRecyclerAdapter(@LayoutRes int _resId, Class<VHModel> myVHModelClass, boolean isSelectable)
    {
        initilize(_resId, myVHModelClass, isSelectable, false, new ArrayList<Model>());
    }

    public MHRecyclerAdapter(@LayoutRes int _resId, Class<VHModel> myVHModelClass, boolean isSelectable, boolean isMultiSelection)
    {
        initilize(_resId, myVHModelClass, isSelectable, isMultiSelection, new ArrayList<Model>());
    }

    public MHRecyclerAdapter(@LayoutRes int _resId, Class<VHModel> myVHModelClass, boolean isSelectable, boolean isMultiSelection, List<Model> list)
    {
        initilize(_resId, myVHModelClass, isSelectable, isMultiSelection, list);
    }

    private void initilize(@LayoutRes int _resId, Class<VHModel> myVHModelClass, boolean isSelectable, boolean isMultiSelection, List<Model> list)
    {
        resId = _resId;
        if (resId == 0)
            throw new RuntimeException("Could not found layout in class " + myVHModelClass.getSimpleName() + "\n are you missing an annotaion ?");
        MyVHolder = myVHModelClass;
        this.isSelectable = isSelectable;
        this.isMultiSelection = isMultiSelection;
        items = list;
        _selectedItems = new SparseBooleanArray();
        listeners = new ArrayList<>();
        hasEmptyView = false;

    }

    /**
     * find Layout id in given Model
     * @param model
     * @return
     */
    private int getViewID(Class<VHModel> model)
    {
        MHBindView col = model.getAnnotation(MHBindView.class);
        return (col != null) ? col.value() : 0;
    }

    /**
     * Exit point of onbindView in Adapter
     * @param bindView Interface MH IonBindView
     */
    public void setBindViewCallBack(MHIonBindView bindView)
    {
        this.bindView = bindView;
        notifyDataSetChanged();
    }

    /**
     * add View With Given ID as Clickable View on top of stack
     * @param propertyID ID of clickable view
     */
    public void addListener(@IdRes int propertyID)
    {
        this.listeners.add(new MyKeyValue(propertyID, null));
        notifyDataSetChanged();
    }

    /**
     * remove clickable view from top of stack
     */
    public void remLastListener()
    {
        if (this.listeners.size() > 0)
        {
            this.listeners.remove(listeners.size() - 1);
            notifyDataSetChanged();
        }
    }

    //region External interfaces Methods


    @Override
    public <EmptyVH extends Object> void setEmptyView(Class<EmptyVH> emptyVHModelClass)
    {
        MHBindView col = emptyVHModelClass.getAnnotation(MHBindView.class);
        int ID = (col != null) ? col.value() : 0;
        setEmptyView(ID, emptyVHModelClass);
    }


    @Override
    public <EmptyVH extends Object> void setEmptyView(@LayoutRes int id, Class<EmptyVH> emptyVHModelClass)
    {
        resId_empty = id;
        if (resId_empty == 0)
            throw new RuntimeException("Could not found layout in class " + emptyVHModelClass.getSimpleName() + "\n are you missing an annotaion ?");
        MyEmptyVH = emptyVHModelClass;
        hasEmptyView = true;
    }


    @Override
    public boolean hasEmptyView()
    {
        return hasEmptyView;
    }


    @Override
    public List<Model> getItems()
    {
        return items;
    }


    @Override
    public void setItems(List<Model> _items)
    {
        items = new ArrayList<>();
        items.addAll(_items);
        propertiesNames = getPropertiesNames(items.get(0));
        notifyDataSetChanged();
    }

    private List<String> getPropertiesNames(Model m)
    {
        List<String> propssss = new ArrayList<>();
        Class<Model> clazz = (Class<Model>) m.getClass();

        for (Field f : clazz.getDeclaredFields())
            if (Modifier.isPublic(f.getModifiers()) && !Modifier.isFinal(f.getModifiers()) && !Modifier.isStatic(f.getModifiers()))
            {
                MHBindView col = f.getAnnotation(MHBindView.class);
                if (col != null)
                {
                    propssss.add(f.getName());
                    if (col.isClickable())
                        addListener(col.value());
                }
            }
        return propssss;
    }


    @Override
    public Model getItem(int index)
    {
        return items.get(index);
    }


    @Override
    public void upItem(int index, Model obj)
    {
        items.set(index, obj);
        notifyItemChanged(index);
    }


    @Override
    public void remItem(int index)
    {
        items.remove(index);
        notifyItemRemoved(index);
    }


    @Override
    public void remRangeItems(List<Integer> indices)
    {
        for (int i = 0; i < indices.size(); i++)
            items.remove(indices.get(i));
        notifyDataSetChanged();
    }


    @Override
    public void toggleItem(int index)
    {
        if (isSelectable || isMultiSelection)
            selectItem(index, true);
    }


    @Override
    public void selectItem(int index, boolean selected)
    {
        if (_selectedItems.get(index, !selected))
            _selectedItems.delete(index);
        else
        {
            if (!isMultiSelection == isSelectable)
                _selectedItems.clear();

            _selectedItems.put(index, selected);
        }
        notifyDataSetChanged();
    }


    @Override
    public int getSelectedItemsCount()
    {
        return _selectedItems.size();
    }


    @Override
    public boolean isSelected(int index)
    {
        return (isSelectable || isMultiSelection) && _selectedItems.get(index, false);
    }


    @Override
    public void ClearSelection()
    {
        if (isSelectable || isMultiSelection)
        {
            _selectedItems.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public void ClearSelectionAt(int index)
    {
        if (isSelectable || isMultiSelection)
        {
            _selectedItems.delete(index);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public List<Model> getSelectedItems()
    {
        List<Model> mlist = new ArrayList<>();
        for (int i = 0; i < _selectedItems.size(); i++)
            mlist.add(getSelectedItem(i));
        return mlist;
    }

    @NonNull
    @Override
    public List<Integer> getSelectedItemsIndices()
    {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < _selectedItems.size(); i++)
            list.add(_selectedItems.keyAt(i));
        return list;
    }

    @Override
    public Model getSelectedItem(int index)
    {
        return getItem(_selectedItems.keyAt(index));
    }

    @Override
    public void appendItems(List<Model> appends)
    {
        int size = items.size();
        items.addAll(appends);
        notifyItemRangeInserted(size, appends.size());
    }

    @Override
    public void beginLazyLoading(RecyclerView.ViewHolder vh)
    {

    }

    @Override
    public void endLazyLoading(RecyclerView.ViewHolder vh)
    {

    }

    @Override
    public RecyclerView.OnItemTouchListener buildTouchItemListener(Context context, RecyclerView _rv, MHOnItemClickListener listener)
    {
        List<Integer> ids = new ArrayList<>();
        for (MyKeyValue kv : listeners)
        {
            ids.add(kv.Key);
        }
        MHTouchItemClick<VHModel> click = new MHTouchItemClick<VHModel>(context, _rv, listener, MyVHolder);
        click.setIds(ids);
        return click;
    }
    //endregion

    @Override
    public MHViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MHViewHolder<?> mhvh = null;
        View v;
        switch (viewType)
        {
            case EMPTY_VIEW_TYPE:
                if (hasEmptyView)
                {
                    v = LayoutInflater.from(parent.getContext()).inflate(resId_empty, parent, false);
                    mhvh = new MHViewHolder(v, MyEmptyVH);
                }
                break;
            case MAIN_VIEW_TYPE:
            default:
                v = LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
                mhvh = new MHViewHolder<VHModel>(v, MyVHolder);
                /*if (listeners != null && !listeners.isEmpty())
                    for (int i = 0; i < listeners.size(); i++)
                    {
                        MyKeyValue kv = listeners.get(i);
                        mhvh.setListener(kv.Key, kv.Listener);
                    }*/

                break;
        }
        return mhvh;
    }

    @Override
    public void onBindViewHolder(MHViewHolder holder, int position)
    {
        if (holder == null)
            return;
        if (getItemViewType(position) == EMPTY_VIEW_TYPE)
        {
            return;
        }
        Model m = items.get(position);
        List<View> childs = new ArrayList<>();
        Class<Model> clazz = (Class<Model>) m.getClass();
        for (String propName : propertiesNames)
            try
            {
                Field f = clazz.getDeclaredField(propName);
                MHBindView col = f.getAnnotation(MHBindView.class);
                if (col != null)
                {
                    f.setAccessible(true);
                    Object value = f.get(m);
                    View j = holder.setValue(col.value(), value);
                    childs.add(j);
                }
            }
            catch (NoSuchFieldException e)
            {
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }


        if (bindView != null && holder.itemView instanceof ViewGroup)
        {
            try
            {
                View[] array = new View[childs.size()];
                childs.toArray(array);
                bindView.BindViewHolder((ViewGroup) holder.itemView, position, array);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

    }

    @Override
    public int getItemViewType(int position)
    {
        return hasEmptyView && items.size() == 0 ? EMPTY_VIEW_TYPE : MAIN_VIEW_TYPE;
    }

    @Override
    public int getItemCount()
    {
        return hasEmptyView && items.size() == 0 ? 1 : items.size();
    }
}
