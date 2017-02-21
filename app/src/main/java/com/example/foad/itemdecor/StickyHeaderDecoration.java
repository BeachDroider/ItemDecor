package com.example.foad.itemdecor;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


public class StickyHeaderDecoration extends RecyclerView.ItemDecoration  {

    private View currentHeaderView = null;
    private int canvasTop = 0;
    private int canvasHeight = 0;
    private StickyHeaderAdapter mAdapter;
    private RecyclerView mParent;
    private GestureDetector mGestureDetector;

    public StickyHeaderDecoration(RecyclerView parent){
        mParent = parent;
        mAdapter = (StickyHeaderAdapter) parent.getAdapter();
        mParent.getRecycledViewPool().setMaxRecycledViews(MyAdapter.HEADER_VIEW_TYPE, 0);
        mGestureDetector = new GestureDetector(parent.getContext(), new GestureListener());
        mParent.addOnItemTouchListener(new TouchListener());

    }


    private View getNextHeader(RecyclerView recyclerView){

        int i = 1;
        int size = recyclerView.getChildCount();
        View view;

        while (i < size){
            view = recyclerView.getChildAt(i);
            if (recyclerView.findContainingViewHolder(view) instanceof HeaderViewHolder) {
                return view;
            }
            i++;
        }
        return null;
    }


    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        View nextHeaderView = getNextHeader(parent);
        currentHeaderView = mAdapter.getHeader(((GenericViewHolder)(parent.getChildViewHolder(parent.getChildAt(0)))).getGroupHeaderKey());

        if (nextHeaderView.getTop() < nextHeaderView.getHeight()){
            c.clipRect(0,  0 , parent.getWidth(),  nextHeaderView.getTop());
            c.translate(0,  nextHeaderView.getTop() - nextHeaderView.getHeight() );

        }

        currentHeaderView.draw(c);
        canvasTop = nextHeaderView.getTop();
        canvasHeight = nextHeaderView.getHeight();

    }

    private class TouchListener extends RecyclerView.SimpleOnItemTouchListener{

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            boolean clicked = mGestureDetector.onTouchEvent(e);

            if (canvasTop < canvasHeight){

                if (clicked && (int)e.getY() < canvasTop){
                    onStickingHeaderClicked();
                    return true;
                } else {
                    return false;
                }

            } else {

                if (clicked && (int)e.getY() < canvasHeight){
                    onStickingHeaderClicked();
                    return true;
                } else {
                    return false;
                }
            }


        }
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return true;
        }
    }

    private void onStickingHeaderClicked(){
        Log.i("9898", "clicked sticking header");
        currentHeaderView.performClick();

    }

}
