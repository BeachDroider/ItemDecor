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


public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    MyAdapter mAdapter;
    Paint mPaint;
    int mdy = 0;

    View currentHederView = null;

    int canvasTop = 0;
    int canvasHeight = 0;

    boolean stackCanPop = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

                if (canvasTop < canvasHeight){

                    if (clicked && (int)e.getY() < canvasTop){
                        Log.i("9898", "clicked sticking header");
                        currentHederView.performClick();
                        return true;
                    } else {
                        return false;
                    }

                } else {

                    if (clicked && (int)e.getY() < canvasHeight){
                        Log.i("9898", "clicked sticking header");
                        currentHederView.performClick();
                        return true;
                    } else {
                        return false;
                    }
                }


            }

        });

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {


            @Override
            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDrawOver(c, parent, state);


                View nextHeaderViewww = getNextHeader(parent);
                currentHederView = mAdapter.mHeaders.get(((GenericViewHolder)(parent.getChildViewHolder(parent.getChildAt(0)))).getKey());
                Log.i("0909", Integer.toString(currentHederView.hashCode()));
                if (nextHeaderViewww.getTop() < nextHeaderViewww.getHeight()){
                    c.save();
                    //c.clipRect(0,  0 , parent.getWidth(),  nextHeaderViewww.getTop());
                    //c.translate(0,  nextHeaderViewww.getTop() - nextHeaderViewww.getHeight() );
                    currentHederView.draw(c);
                    c.restore();
                    canvasTop = nextHeaderViewww.getTop();
                    canvasHeight = nextHeaderViewww.getHeight();


                } else {
                    currentHederView.draw(c);
                    canvasTop = nextHeaderViewww.getTop();
                    canvasHeight = nextHeaderViewww.getHeight();


                }
            }
        });

    }

    private View getNextHeader(RecyclerView recyclerView){

        int i = 1;
        int size = recyclerView.getChildCount();
        View view;

        while (i < size){
            view = recyclerView.getChildAt(i);
            if (view.getTag() != null) {
                return view;
            }
            i++;
        }
        return null;
    }



}
