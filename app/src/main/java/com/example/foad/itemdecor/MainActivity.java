package com.example.foad.itemdecor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    MyAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ArrayList<MyItem> myItems = new ArrayList<>();
        myItems.add(new MyItem("A", true));
        myItems.add(new MyItem("A0", false));
        myItems.add(new MyItem("A1", false));
        myItems.add(new MyItem("A2", false));

        myItems.add(new MyItem("B", true));
        myItems.add(new MyItem("B0", false));
        myItems.add(new MyItem("B1", false));
        myItems.add(new MyItem("B2", false));

        myItems.add(new MyItem("C", true));
        myItems.add(new MyItem("C0", false));
        myItems.add(new MyItem("C1", false));
        myItems.add(new MyItem("C2", false));

        myItems.add(new MyItem("D", true));
        myItems.add(new MyItem("D0", false));
        myItems.add(new MyItem("D1", false));
        myItems.add(new MyItem("D2", false));


        myItems.add(new MyItem("E", true));
        myItems.add(new MyItem("E0", false));
        myItems.add(new MyItem("E1", false));
        myItems.add(new MyItem("E2", false));

        myItems.add(new MyItem("F", true));
        myItems.add(new MyItem("F0", false));
        myItems.add(new MyItem("F1", false));
        myItems.add(new MyItem("F2", false));
        myItems.add(new MyItem("F3", false));
        myItems.add(new MyItem("F4", false));

        myItems.add(new MyItem("G", true));

        myItems.add(new MyItem("H", true));
        myItems.add(new MyItem("H0", false));

        myItems.add(new MyItem("I", true));
        myItems.add(new MyItem("I0", false));
        myItems.add(new MyItem("I1", false));
        myItems.add(new MyItem("I2", false));

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAdapter = new MyAdapter(myItems);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new StickyHeaderDecoration(mRecyclerView));


    }




}
