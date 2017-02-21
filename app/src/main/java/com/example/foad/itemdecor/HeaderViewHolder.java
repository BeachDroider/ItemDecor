package com.example.foad.itemdecor;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Random;


public class HeaderViewHolder extends GenericViewHolder{

    TextView headerItemText;
    View view;
    String key = null;


    @Override
    public String getGroupHeaderKey(){
        return key;
    }

    public void setGroupHeaderKey(String key){
        this.key = key;
    }

    public HeaderViewHolder(View view){
        super(view);
        this.view = view;
        Random rnd = new Random();


        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        //      int color = Color.argb(255, 123, 123, 123);
        view.setBackgroundColor(color);
        headerItemText = (TextView)view.findViewById(R.id.header_item_text);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("8888", "clicked header item: " + headerItemText.getText());

            }
        });


    }

}
