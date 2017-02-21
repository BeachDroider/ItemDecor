package com.example.foad.itemdecor;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.HashMap;

/**
 * Created by foad on 21/2/17.
 */

public abstract class StickyHeaderAdapter extends RecyclerView.Adapter {

    private HashMap<String, View> mHeaders;

    public StickyHeaderAdapter(){

        mHeaders = new HashMap<>();

    }


    public View getHeader(String key){

        return mHeaders.get(key);
    }

    public void setHeader(String key, View view){

        if (!mHeaders.containsKey(key))
            mHeaders.put(key, view);

    }

}
