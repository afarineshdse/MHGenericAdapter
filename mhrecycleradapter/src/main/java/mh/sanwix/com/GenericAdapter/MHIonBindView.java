package mh.sanwix.com.GenericAdapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by m.hoseini on 8/28/2017.
 */

public interface MHIonBindView
{
    void BindViewHolder(ViewGroup rootView, int pos,View... chillViews);
}
