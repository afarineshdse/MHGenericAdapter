package mh.sanwix.com.GenericAdapter;

import android.support.annotation.Nullable;
import android.view.View;

public interface MHOnItemClickListener
{
    void onItemClick(int recycler_id, View itemView, int position, @Nullable View clickedView);

    void onItemLongClick(int recycler_id, View itemView, int position, @Nullable View clickedView);
}
