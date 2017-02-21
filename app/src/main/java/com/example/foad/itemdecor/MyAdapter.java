package com.example.foad.itemdecor;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by foad on 8/2/17.
 */

public class MyAdapter extends StickyHeaderAdapter {

    public static int NORMAL_VIEW_TYPE = 0;
    public static int HEADER_VIEW_TYPE = 1;

    String entries[] = new String[]{
            "A", "A0","A1","A2",
            "B", "B0", "B1", "B2",
            "C", "C0", "C1", "C2",
            "D", "D0", "D1", "D2",
            "E", "E0", "E1", "E2",
            "F", "F0", "F1", "F2",
            "G", "G0", "G1", "G2",
            "H", "H0", "H1", "H2",
            "I", "I0", "I1", "I2",
            "J", "J0", "J1", "J2",
            "K", "K0", "K1", "K2",
            "L", "L0", "L1", "L2",
            "M", "M0", "M1", "M2",
            "N", "N0", "N1", "N2",
            "O", "O0", "O1", "O2",

    };

    public MyAdapter(){

    }

    @Override
    public int getItemViewType(int position) {
        if (position % 4 == 0)
            return HEADER_VIEW_TYPE;
        else return NORMAL_VIEW_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == NORMAL_VIEW_TYPE) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.normal_list_item, parent, false);
            return new NormalViewHolder(view);

        } else if (viewType == HEADER_VIEW_TYPE) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_list_item, parent, false);
            return new HeaderViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof NormalViewHolder){

            ((NormalViewHolder) holder).normalItemText.setText(getItem(position));

        } else if (holder instanceof HeaderViewHolder){

            ((HeaderViewHolder) holder).headerItemText.setText(getItem(position));
            ((HeaderViewHolder) holder).setGroupHeaderKey(getItem(position));

            storeHeader(getItem(position), ((HeaderViewHolder) holder).view);

        }

    }

    @Override
    public int getItemCount() {

        return entries.length;
    }

    private String getItem(int position){

        return entries[position];
    }







}
