package mh.sanwix.com.GenericAdapter;

import android.view.View;

class MyKeyValue
{
    public int Key;
    public View Value;
    public Class clazz;
    public View.OnClickListener Listener;
    public MyKeyValue(int key,View value, Class _cllaz)
    {
        Key = key;
        Value = value;
        clazz = _cllaz;
    }

    public MyKeyValue(int key,  View.OnClickListener listener )
    {
        Key = key;
        Listener = listener;
    }
}
