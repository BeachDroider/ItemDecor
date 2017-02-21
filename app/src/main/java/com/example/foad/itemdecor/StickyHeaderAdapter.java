package com.example.foad.itemdecor;

import android.content.Context;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public abstract class StickyHeaderAdapter extends RecyclerView.Adapter {

    public static int NORMAL_VIEW_TYPE = 0;
    public static int HEADER_VIEW_TYPE = 1;


    private HashMap<String, View> mHeaders;
    private List mData;

    public StickyHeaderAdapter(List data){
        mData = data;
        mHeaders = new HashMap<>();

    }

    @Override
    public int getItemViewType(int position) {

        if (((StickyHeaderItem)getItem(position)).isHeader()){
            return HEADER_VIEW_TYPE;
        } else {
            return NORMAL_VIEW_TYPE;
        }

    }

    public View getHeader(String key){

        return mHeaders.get(key);
    }

    public void setData(List data){
        mData = data;
    }

    public Object getItem(int position){
        return mData.get(position);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((GenericViewHolder)holder).setGroupHeaderKey((((StickyHeaderItem)getItem(position)).getGroupKey()));


        if (holder instanceof HeaderViewHolder){
            if (!mHeaders.containsKey(((HeaderViewHolder) holder).getGroupHeaderKey()))
                mHeaders.put(((HeaderViewHolder) holder).getGroupHeaderKey(), ((HeaderViewHolder) holder).view);
        }


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == NORMAL_VIEW_TYPE) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.normal_list_item, parent, false);
            return new NormalViewHolder(view);

        } else if (viewType == HEADER_VIEW_TYPE) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_list_item, parent, false);
            return new HeaderViewHolder(this, view);
        }

        return null;
    }

    public void onHeaderClicked(String key){


        int first = -1;
        int count = 0;

        for (int i = 0; i < mData.size(); i++){

            StickyHeaderItem stickyHeaderItem = (StickyHeaderItem)mData.get(i);
            if (stickyHeaderItem.getGroupKey().equals(key) && !stickyHeaderItem.isHeader()){
                Log.i("9090", "removed: " + key);
                if (first == -1) first = i;
                count++;

            }



        }

        Log.i("9090"," First: " + Integer.toString(first));
        Log.i("9090"," Count: " + Integer.toString(count));
        for (int i = 0; i < count; i++){
            Log.i("9090", "removing: " + Integer.toString(first ));
            mData.remove(first );
        }

        notifyItemRangeRemoved(first, count);

    }
}
