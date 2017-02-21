package com.example.foad.itemdecor;

import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class NormalViewHolder extends GenericViewHolder{

    TextView normalItemText;
    String key;

    public NormalViewHolder(View view){
        super(view);
        normalItemText = (TextView)view.findViewById(R.id.normal_item_text);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("8888", "clicked normal item: " + normalItemText.getText());
            }
        });

    }

    @Override
    public String getGroupHeaderKey(){
        return normalItemText.getText().toString().substring(0,1);
    }

    @Override
    public void setGroupHeaderKey(String key){
        this.key = key;
    }

}


