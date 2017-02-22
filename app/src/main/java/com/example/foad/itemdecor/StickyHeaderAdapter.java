package com.example.foad.itemdecor;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static java.lang.Boolean.TRUE;


public abstract class StickyHeaderAdapter extends RecyclerView.Adapter {

    public static int NORMAL_VIEW_TYPE = 0;
    public static int HEADER_VIEW_TYPE = 1;

    private HashMap<String, View> mHeaders;
    private HashMap<String, Boolean> mExpansionState;
    private List mData;
    private List mFilteredData;

    public StickyHeaderAdapter(List data){
        mData = data;
        mFilteredData = new ArrayList(mData);
        mHeaders = new HashMap<>();

        mExpansionState = new HashMap<>();
        Iterator iterator = mExpansionState.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Boolean> pair = (Map.Entry<String, Boolean>)iterator.next();
            pair.setValue(TRUE);
        }



    }

    @Override
    public int getItemViewType(int position) {

        if (((StickyHeaderItem)getItem(position)).isHeader()){
            return HEADER_VIEW_TYPE;
        } else {
            return NORMAL_VIEW_TYPE;
        }

    }

    @Override
    public int getItemCount() {
        return mFilteredData.size();
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

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((GenericViewHolder)holder).setGroupHeaderKey((((StickyHeaderItem)getItem(position)).getGroupKey()));


        if (holder instanceof HeaderViewHolder){
            if (!mHeaders.containsKey(((HeaderViewHolder) holder).getGroupHeaderKey()))
                mHeaders.put(((HeaderViewHolder) holder).getGroupHeaderKey(), ((HeaderViewHolder) holder).view);



            if (!mExpansionState.containsKey(((HeaderViewHolder) holder).getGroupHeaderKey()))
                mExpansionState.put(((HeaderViewHolder) holder).getGroupHeaderKey(), TRUE);


        }


    }

    public View getHeader(String key){

        return mHeaders.get(key);
    }

    public Object getItem(int position){
        return mFilteredData.get(position);

    }

    public void onHeaderClicked(String key){


        if (mExpansionState.get(key)){
            mExpansionState.put(key, Boolean.FALSE);
            collapse(key);
        } else {
            mExpansionState.put(key, TRUE);
            expand(key);
        }
    }
    
    private void collapse(String key){



        int first = findFirstNonHeaderIndexOf(mFilteredData, key);
        int last = findLastIndexOf(mFilteredData, key);
        int count = last - first + 1;

        if (first != -1){
            removeRange(first, count, mFilteredData);
            notifyItemRangeRemoved(first, count);
        }

    }

    private void expand(String key){

        int mainListFirstIndex = findFirstNonHeaderIndexOf(mData, key);
        int mainListLastIndex = findLastIndexOf(mData, key);
        int filteredListFirstIndex = findFirstIndexOf(mFilteredData, key) ;

        if (mainListFirstIndex != -1){

            mFilteredData.addAll(filteredListFirstIndex + 1 , mData.subList(mainListFirstIndex , mainListLastIndex + 1));
            notifyItemRangeInserted(filteredListFirstIndex + 1 , mainListLastIndex - mainListFirstIndex + 1 );

        }



        Log.i("9090", "MainListFirst: " + Integer.toString(mainListFirstIndex));
        Log.i("9090", "MainListLast: " + Integer.toString(mainListLastIndex));
        Log.i("9090", "FilteredListFirst: " + Integer.toString(filteredListFirstIndex));

    }

    private void removeRange(int first, int count, List list){
        for (int i = 0; i < count; i++){
            list.remove(first);
        }
    }

    private int findFirstNonHeaderIndexOf (List list, String key){

        for (int i = 0; i < list.size(); i++){
            StickyHeaderItem stickyHeaderItem = (StickyHeaderItem)list.get(i);
            if (stickyHeaderItem.getGroupKey().equals(key) && !stickyHeaderItem.isHeader()){
                return i;
            }

        }

        return -1;
    }

    private int findFirstIndexOf (List list, String key){

        for (int i = 0; i < list.size(); i++){
            StickyHeaderItem stickyHeaderItem = (StickyHeaderItem)list.get(i);
            if (stickyHeaderItem.getGroupKey().equals(key) ){
                return i;
            }

        }

        return -1;
    }

    private int findLastIndexOf(List list, String key){

        int lastIndex = -1;

        for (int i = 0; i < list.size(); i++){
            StickyHeaderItem stickyHeaderItem = (StickyHeaderItem)list.get(i);
            if (stickyHeaderItem.getGroupKey().equals(key) && !stickyHeaderItem.isHeader()){
                lastIndex = i;
            }

        }

        return lastIndex;

    }
}
