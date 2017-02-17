package com.example.foad.itemdecor;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by foad on 17/2/17.
 */



public class NormalViewHolder extends GenericViewHolder{

    TextView normalItemText;

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
    public String getKey(){
        return normalItemText.getText().toString().substring(0,1);
    }

}


