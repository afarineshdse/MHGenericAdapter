package mh.sanwix.com.GenericAdapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by m.hoseini on 8/7/2017.
 */

class MHTouchItemClick<ViewHolderModel> implements RecyclerView.OnItemTouchListener
{
    GestureDetector mGestureDetector;
    RecyclerView rv;
    private MHOnItemClickListener mListener;
    Class<ViewHolderModel> viewHolderModel;
    List<Integer> ClickableIds;
    boolean isidOK ;
    public void setViewHolderModel(Class<ViewHolderModel> model)
    {
        this.viewHolderModel = model;
        isidOK = false;
    }

    public void setIds(List<Integer> ids)
    {
        if (this.ClickableIds == null || this.ClickableIds.isEmpty())
            this.ClickableIds = new ArrayList<>();
        ClickableIds.addAll(ids);
        //distinct
        Set<Integer> hs = new HashSet<>();
        hs.addAll(ClickableIds);
        ClickableIds.clear();
        ClickableIds.addAll(hs);
        //distinct

        isidOK = true;
    }

    public MHTouchItemClick(Context context, RecyclerView _rv, MHOnItemClickListener listener, Class<ViewHolderModel> myVHolder)
    {
        ClickableIds = new ArrayList<>();
        mListener = listener;
        rv = _rv;
        setViewHolderModel(myVHolder);
        findIDS(myVHolder);
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapUp(MotionEvent e)
            {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e)
            {
                super.onLongPress(e);
                View raw = rv.findChildViewUnder(e.getX(), e.getY());
                View clickedview = null;
                if (isidOK)
                {
                    for (int i = 0; i < ClickableIds.size(); i++)
                    {
                        int id = ClickableIds.get(i);
                        View tmp = raw.findViewById(id);
                        clickedview = isPointInsideView(e.getRawX(), e.getRawY(), tmp);
                        if (clickedview != null)
                            break;
                    }
                }
                else
                {
                    if (raw instanceof ViewGroup)
                    {
                        for (int i = 0; i < ClickableIds.size(); i++)
                        {
                            int id = ClickableIds.get(i);
                            View tmp = raw.findViewById(id);
                            clickedview = isPointInsideView(e.getRawX(), e.getRawY(), tmp);
                            if (clickedview != null)
                                break;
                        }
                    }
                    else
                        clickedview = isPointInsideView(e.getRawX(), e.getRawY(), raw);
                }

                if (raw != null && mListener != null)
                    mListener.onItemLongClick(rv.getId(), raw, rv.getChildLayoutPosition(raw), clickedview);
            }
        });
    }

    private void findIDS(Class<ViewHolderModel> model)
    {
        ViewHolderModel MyModel = null;
        //try
        try
        {
            MyModel = model.newInstance();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        if (MyModel == null)
        {
            Log.i("MH_VHModel ", " setViewModel: Null");
            return;
        }

        for (Field f : model.getDeclaredFields())
        {
            Class<?> clazz = MyModel.getClass();
            int modifier = f.getModifiers();
            MHBindView col = f.getAnnotation(MHBindView.class);
            if (Modifier.isPublic(modifier) && !Modifier.isStatic(modifier) &&
                    !Modifier.isFinal(modifier) && col != null && col.isClickable())
                ClickableIds.add(col.value());
        }
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e)
    {
        View raw = rv.findChildViewUnder(e.getX(), e.getY());
        View clickedview = null;
        if (isidOK)
        {
            for (int i = 0; i < ClickableIds.size(); i++)
            {
                int id = ClickableIds.get(i);
                View tmp = raw.findViewById(id);
                clickedview = isPointInsideView(e.getRawX(), e.getRawY(), tmp);
                if (clickedview != null)
                    break;
            }
        }
        else
        {
            if (raw instanceof ViewGroup)
            {
                for (int i = 0; i < ClickableIds.size(); i++)
                {
                    int id = ClickableIds.get(i);
                    View tmp = raw.findViewById(id);
                    clickedview = isPointInsideView(e.getRawX(), e.getRawY(), tmp);
                    if (clickedview != null)
                        break;
                }
            }
            else
                clickedview = isPointInsideView(e.getRawX(), e.getRawY(), raw);
        }

        if (raw != null && mListener != null && mGestureDetector.onTouchEvent(e))
            mListener.onItemClick(rv.getId(), raw, rv.getChildLayoutPosition(raw), clickedview);
        return false;
    }

    private View isPointInsideView(float x, float y, View child)
    {

        int location[] = new int[2];
        child.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];

        //point is inside view bounds
        if ((x > viewX && x < (viewX + child.getWidth())) &&
                (y > viewY && y < (viewY + child.getHeight())))
        {
            return child;
        }
        else
        {
            return null;
        }

    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e)
    {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept)
    {

    }


}
