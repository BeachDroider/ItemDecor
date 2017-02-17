package com.example.foad.itemdecor;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    MyAdapter mAdapter;
    Paint mPaint;
    int mdy = 0;

    Stack<View> headerViews = null;
    View currentHederView = null;

    int canvasTop = 0;
    int canvasHeight = 0;



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
                Log.i("9898", "e.getY(): " + Float.toString(e.getY()));

                if (clicked && (int)e.getY() < canvasTop){

                    Log.i("9898", "clicked sticking header");
                    currentHederView.performClick();
                    return true;
                }
                return false;
            }

        });

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {


            @Override
            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDrawOver(c, parent, state);

                if (currentHederView == null){
                    currentHederView = parent.getChildAt(0);
                }

                if (mdy > 0  && parent.getChildAt(0).getTag() != null && parent.getChildAt(0) != currentHederView){

                    Log.i("8888", "pushed to stack");
                    headerViews.push(currentHederView);
                    currentHederView = parent.getChildAt(0);
                }

                if (mdy < 0 && parent.getChildAt(1).getTag() != null && !headerViews.isEmpty()) {
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

                View nextHeaderViewww = getNextHeader(parent);
                if (nextHeaderViewww.getTop() < nextHeaderViewww.getHeight()){
                    c.save();
                    c.clipRect(0,  0 , parent.getWidth(),  nextHeaderViewww.getTop());
                    c.translate(0,  nextHeaderViewww.getTop() - nextHeaderViewww.getHeight() );
                    currentHederView.draw(c);
                    c.restore();
                    Log.i("9898", "canvas top: " + Integer.toString(nextHeaderViewww.getTop() ));
                    Log.i("9898", "canvas height: " + nextHeaderViewww.getHeight());
                    canvasTop = nextHeaderViewww.getTop();
                    canvasHeight = nextHeaderViewww.getHeight();


                } else {
                    currentHederView.draw(c);
                    canvasTop = nextHeaderViewww.getTop();
                    canvasHeight = nextHeaderViewww.getHeight();
                    Log.i("9898", "canvas top: " + Integer.toString(canvasTop));


                }


            }

        });

    }

    private View getNextHeader(RecyclerView recyclerView){


        int i = 1;
        int size = recyclerView.getChildCount();
        View view = null;

        while (i < size){
            view = recyclerView.getChildAt(i);
            if (view.getTag() != null) return view;
            i++;
        }
        return null;
    }



}
