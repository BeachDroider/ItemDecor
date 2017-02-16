package com.example.foad.itemdecor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    MyAdapter mAdapter;
    Paint mPaint;
    int mdy = 0;

    Stack<View> headerViews = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        headerViews = new Stack<>();

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mAdapter = new MyAdapter(this);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mPaint = new Paint();
        mPaint.setColor(Color.RED);

        mRecyclerView.getRecycledViewPool().setMaxRecycledViews(MyAdapter.HEADER_VIEW_TYPE, 0);




        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mdy = dy;
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener(){
            GestureDetector gestureDetector = new GestureDetector(MainActivity.this , new GestureDetector.SimpleOnGestureListener(){

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });


            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                boolean clicked = gestureDetector.onTouchEvent(e);

                if (clicked && e.getY() < 100.0f){
                    Log.i("9898", "clicked header");
                }
                return false;
            }

        });

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {

            View currentHederView = null;

            @Override
            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDrawOver(c, parent, state);


                if (currentHederView == null){
                    currentHederView = parent.getChildAt(0);
                }

                int width = parent.getWidth();
                int top = parent.getChildAt(0).getTop();
                int height = parent.getChildAt(1).getHeight();

                if (mdy > 0  && parent.getChildAt(0).getTag() != null && parent.getChildAt(0) != currentHederView){

                    Log.i("8888", "pushed to stack");
                    headerViews.push(currentHederView);
                    currentHederView = parent.getChildAt(0);
                }


                if (mdy < 0 && parent.getChildAt(1).getTag() != null ) {
                    Log.i("8888", "peek");
                    currentHederView = headerViews.peek();

                }


                if (mdy < 0 && parent.getChildAt(2).getTag() != null && !headerViews.isEmpty() && currentHederView == headerViews.peek()) {
                    Log.i("8888", "popped stack");
                    headerViews.pop();
                }


                // if we are reaching top of list due to acceleretation followed by a previous fast scroll without
                // a current scroll event, in that case everything should be restored to initial state
                if (parent.getChildAdapterPosition(parent.getChildAt(0)) == 0){
                    currentHederView = parent.getChildAt(0);
                    headerViews.clear();
                }


                if (parent.getChildAt(1).getTag() != null){

                    c.save();
                    c.clipRect(0,  0 , width,   height + top);
                    c.translate(0,  top);

                    currentHederView.draw(c);
                    c.restore();

                } else {
                    currentHederView.draw(c);

                }



            }

        });

    }



}
