package com.example.foad.itemdecor;

import android.support.v7.widget.RecyclerView;


import java.util.List;


public class MyAdapter extends StickyHeaderAdapter {


    public MyAdapter(List data){
        super(data);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);


        if (holder instanceof NormalViewHolder){

            ((NormalViewHolder) holder).normalItemText.setText(((MyItem)getItem(position)).text);

        } else if (holder instanceof HeaderViewHolder){

            ((HeaderViewHolder) holder).headerItemText.setText(((MyItem)getItem(position)).text);

        }

    }





}
