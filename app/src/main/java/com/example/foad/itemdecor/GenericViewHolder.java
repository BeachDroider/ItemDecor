package com.example.foad.itemdecor;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by foad on 17/2/17.
 */

public abstract class GenericViewHolder extends RecyclerView.ViewHolder {

    public abstract String getKey();

    public GenericViewHolder(View view){
        super(view);
    }

}