package com.example.foad.itemdecor;

/**
 * Created by foad on 21/2/17.
 */

public class MyItem implements StickyHeaderItem {

    String text;
    Boolean isHeader;
    public MyItem(String text, boolean isHeader){
        this.text = text;
        this.isHeader = isHeader;
    }

    @Override
    public String getGroupKey() {
        return text.substring(0,1);
    }

    @Override
    public boolean isHeader() {
        return isHeader;
    }
}
