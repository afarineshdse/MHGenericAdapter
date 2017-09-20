package mh.sanwix.com.GenericAdapter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by m.hoseini on 8/26/2017.
 */

class MHViewHolder<T> extends RecyclerView.ViewHolder
{
    private T MyModel;
    private List<MyKeyValue> ItemsHolder;

    public MHViewHolder(View itemView, Class<T> model)
    {
        super(itemView);
        ItemsHolder = new ArrayList<>();
        if (model != null)
            setViewModel(model, itemView);

    }

    public View setValue(int propertyID, Object value)
    {
        for (MyKeyValue kv : ItemsHolder)
            if (kv.Key == (propertyID))
            {
                if (kv.clazz == TextView.class)
                {
                    ((TextView) kv.Value).setText(value + "");
                    return kv.Value;
                }
                else if (kv.clazz == EditText.class)
                {
                    ((EditText) kv.Value).setText(value + "");
                    return kv.Value;
                }
                else if (kv.clazz == Button.class)
                {
                    ((Button) kv.Value).setText(value + "");
                    return kv.Value;
                }
                else if (kv.clazz == CheckBox.class)
                {
                    ((CheckBox) kv.Value).setChecked((Boolean) value);
                    return kv.Value;
                }
                else if (kv.clazz == RadioButton.class)
                {
                    ((RadioButton) kv.Value).setChecked((Boolean) value);
                    return kv.Value;
                }
                else if (kv.clazz == Switch.class)
                {
                    ((Switch) kv.Value).setChecked((Boolean) value);
                    return kv.Value;
                }
                else if (kv.clazz == ImageButton.class)
                {
                    if (value instanceof Integer)
                        ((ImageButton) kv.Value).setImageDrawable(this.itemView.getContext().getResources().getDrawable(Integer.parseInt(value + "")));
                    else if (value instanceof Drawable)
                        ((ImageButton) kv.Value).setImageDrawable(((Drawable) value));
                    else if (value instanceof Bitmap)
                        ((ImageButton) kv.Value).setImageBitmap(((Bitmap) value));

                    return kv.Value;
                }
                else if (kv.clazz == ImageView.class)
                {
                    if (value instanceof Integer)
                        ((ImageView) kv.Value).setImageDrawable(this.itemView.getContext().getResources().getDrawable(Integer.parseInt(value + "")));
                    else if (value instanceof Drawable)
                        ((ImageView) kv.Value).setImageDrawable(((Drawable) value));
                    else if (value instanceof Bitmap)
                        ((ImageView) kv.Value).setImageBitmap(((Bitmap) value));
                    return kv.Value;
                }
                else if (kv.clazz == RatingBar.class)
                {
                    ((RatingBar) kv.Value).setRating((Float.parseFloat(value+"")));
                }

            }
        return null;
    }


    public void setListener(int propertyID, Object value)
    {
        for (MyKeyValue kv : ItemsHolder)
            if (kv.Key == (propertyID))
            {
                kv.Value.setOnClickListener(((View.OnClickListener) value));
                return;
            }

    }


    public int getViewID(Class<T> model)
    {
        MHBindView col = model.getAnnotation(MHBindView.class);
        return (col != null) ? col.value() : 0;
    }

    private void setViewModel(Class<T> model, View itemView)
    {
        T MyModel = null;
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
            Log.i("MH_Model ", " setViewModel: Null");
            return;
        }

        for (Field f : model.getDeclaredFields())
        {
            Class<?> clazz = MyModel.getClass();
            int modifier = f.getModifiers();
            MHBindView col = f.getAnnotation(MHBindView.class);
            if (Modifier.isPublic(modifier) && !Modifier.isStatic(modifier) && !Modifier.isFinal(modifier) && col != null)
            {
                if (f.getType() == TextView.class)
                {
                    ItemsHolder.add(new MyKeyValue(col.value(), (TextView) itemView.findViewById(col.value()), TextView.class));
                }
                else if (f.getType() == Button.class)
                {
                    ItemsHolder.add(new MyKeyValue(col.value(), (Button) itemView.findViewById(col.value()), Button.class));
                }
                else if (f.getType() == EditText.class)
                {
                    ItemsHolder.add(new MyKeyValue(col.value(), (EditText) itemView.findViewById(col.value()), EditText.class));
                }
                else if (f.getType() == CheckBox.class)
                {
                    ItemsHolder.add(new MyKeyValue(col.value(), (CheckBox) itemView.findViewById(col.value()), CheckBox.class));
                }
                else if (f.getType() == RadioButton.class)
                {
                    ItemsHolder.add(new MyKeyValue(col.value(), (RadioButton) itemView.findViewById(col.value()), RadioButton.class));
                }
                else if (f.getType() == Switch.class)
                {
                    ItemsHolder.add(new MyKeyValue(col.value(), (Switch) itemView.findViewById(col.value()), Switch.class));
                }
                else if (f.getType() == ImageButton.class)
                {
                    ItemsHolder.add(new MyKeyValue(col.value(), (ImageButton) itemView.findViewById(col.value()), ImageButton.class));
                }
                else if (f.getType() == ImageView.class)
                {
                    ItemsHolder.add(new MyKeyValue(col.value(), (ImageView) itemView.findViewById(col.value()), ImageView.class));
                }
                else if (f.getType() == RatingBar.class)
                {
                    ItemsHolder.add(new MyKeyValue(col.value(), (RatingBar) itemView.findViewById(col.value()), RatingBar.class));
                }


            }
        }


    }

    private Field getFieldByName(T model, String name)
    {
        Class<?> clazz = model.getClass();
        if (clazz != null)
        {
            Field[] fs = clazz.getDeclaredFields();
            if (fs != null)
                for (Field f : fs)
                    if (f != null && f.getName() != null)
                        if (f.getName().equalsIgnoreCase(name))
                            return f;

        }
        return null;
    }


    public Object[] getItemsHolder()
    {
        Object[] vs = new Object[ItemsHolder.size()];
        for (int i = 0; i < ItemsHolder.size(); i++)
            vs[i] = ItemsHolder.get(i).Value;
        return vs;
    }
}
