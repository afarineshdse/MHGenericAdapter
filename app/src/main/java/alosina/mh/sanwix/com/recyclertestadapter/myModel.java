package alosina.mh.sanwix.com.recyclertestadapter;

import java.lang.annotation.Annotation;

import mh.sanwix.com.GenericAdapter.MHBindView;

public class myModel
{
    @MHBindView(value = R.id.tvTitle)
    public boolean data;

    @MHBindView(value = R.id.tvSubTitle)
    public String salam;



    public myModel(String data)
    {
        this.data = false;
        salam = "clickable";

    }



}
