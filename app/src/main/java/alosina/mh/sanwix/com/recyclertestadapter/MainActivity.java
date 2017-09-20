package alosina.mh.sanwix.com.recyclertestadapter;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mh.sanwix.com.GenericAdapter.*;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, MHIonBindView, View.OnFocusChangeListener, MHOnItemClickListener
{
    List<myModel> models = new ArrayList<>();
    RecyclerView mRecycler;
    MHRecyclerAdapter<myModel, MyVHModel> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_main);
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));

        mAdapter = new MHRecyclerAdapter<myModel, MyVHModel>(MyVHModel.class);
        mRecycler = findViewById(R.id.mrecycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);
        mAdapter.setItems(models);
        mAdapter.setBindViewCallBack(this);
        mAdapter.setEmptyView(MyEmptyVH.class);
        mRecycler.addOnItemTouchListener(mAdapter.buildTouchItemListener(this,mRecycler,this));
    }


    @Override
    public void onClick(View view)
    {
        Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void BindViewHolder(ViewGroup rootView, final int pos, View... chillViews)
    {

    }

    @Override
    public void onFocusChange(View view, boolean b)
    {
        int pos = Integer.parseInt(view.getTag().toString());
        final myModel m = mAdapter.getItem(pos);
        m.salam = ((EditText) view).getText().toString();
        if (!b)
        mAdapter.notifyItemChanged(pos);
    }

    @Override
    public void onItemClick(int recycler_id, View itemView, int position, @Nullable View clickedView)
    {
        if (clickedView != null && clickedView.getId() == R.id.tvSubTitle)
            Toast.makeText(this, "asd", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(int recycler_id, View itemView, int position, @Nullable View clickedView)
    {

    }
}


