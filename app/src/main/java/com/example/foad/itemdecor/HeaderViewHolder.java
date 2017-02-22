package com.example.foad.itemdecor;

import android.view.View;
import android.widget.TextView;



public class HeaderViewHolder extends GenericViewHolder{

    TextView headerItemText;
    View view;
    String key = null;
    StickyHeaderAdapter mAdapter;

    @Override
    public String getGroupHeaderKey(){
        return key;
    }

    @Override
    public void setGroupHeaderKey(String key){
        this.key = key;
    }

    public HeaderViewHolder(StickyHeaderAdapter adapter, View view){
        super(view);
        this.view = view;
        mAdapter = adapter;

        headerItemText = (TextView)view.findViewById(R.id.header_item_text);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.onHeaderClicked(key);
            }
        });


    }

}
