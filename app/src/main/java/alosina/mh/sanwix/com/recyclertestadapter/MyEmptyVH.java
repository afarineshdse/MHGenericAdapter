package alosina.mh.sanwix.com.recyclertestadapter;

import android.widget.TextView;

import mh.sanwix.com.GenericAdapter.MHBindView;

/**
 * Created by m.hoseini on 9/16/2017.
 */
@MHBindView(R.layout.layout_item_empty)
public class MyEmptyVH
{
    @MHBindView(R.id.tvTitle)
    public TextView title;
}
