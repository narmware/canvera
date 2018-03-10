package com.narmware.canvera.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.narmware.canvera.R;
import com.narmware.canvera.adapter.GridImageAdapter;
import com.narmware.canvera.helpers.SharedPreferencesHelper;
import com.narmware.canvera.pojo.GalleryItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryActivity extends AppCompatActivity {

    @BindView(R.id.recycler) RecyclerView mRecyclerView;
    @BindView(R.id.btn_switch) ImageButton mBtnSwitch;
    GridImageAdapter mAdapter;
    List<GalleryItem> mGalleryItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_gallery);
        getSupportActionBar().hide();
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        //setAdapter();
        //set list view when isGrid==false, set grid view when isGrid==true

        if(SharedPreferencesHelper.getIsGrid(GalleryActivity.this)==false) {
            mBtnSwitch.setImageResource(R.drawable.ic_grid);
            setAdapter(new LinearLayoutManager(GalleryActivity.this));
        }
        else if(SharedPreferencesHelper.getIsGrid(GalleryActivity.this)==true) {
            mBtnSwitch.setImageResource(R.drawable.ic_list);
            setAdapter(new GridLayoutManager(GalleryActivity.this,3));
        }

        mBtnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(GalleryActivity.this, SharedPreferencesHelper.getIsGrid(GalleryActivity.this)+"", Toast.LENGTH_SHORT).show();
//set grid
                if(SharedPreferencesHelper.getIsGrid(GalleryActivity.this)==false) {
                    mBtnSwitch.setImageResource(R.drawable.ic_list);
                    SharedPreferencesHelper.setIsGrid(true, GalleryActivity.this);
                    setAdapter(new GridLayoutManager(GalleryActivity.this,3));
                }
                //set list
                else if(SharedPreferencesHelper.getIsGrid(GalleryActivity.this)==true) {
                    mBtnSwitch.setImageResource(R.drawable.ic_grid);
                    SharedPreferencesHelper.setIsGrid(false, GalleryActivity.this);
                    setAdapter(new LinearLayoutManager(GalleryActivity.this));
                }
            }
        });
    }

    public void setAdapter(RecyclerView.LayoutManager mLayoutManager){
        mGalleryItems=new ArrayList<>();
        GalleryItem ob1=new GalleryItem("http://www.indiamarks.com/wp-content/uploads/Indian-Wedding-1.jpg");
        GalleryItem ob2=new GalleryItem("http://www.marrymeweddings.in/images/gallery/stage-at-indian-wedding-reception-19.jpg");
        GalleryItem ob3=new GalleryItem("http://www.marrymeweddings.in/images/gallery/stage-at-indian-wedding-reception-19.jpg");
        GalleryItem ob4=new GalleryItem("http://www.marrymeweddings.in/images/gallery/stage-at-indian-wedding-reception-19.jpg");
        GalleryItem ob5=new GalleryItem("http://www.marrymeweddings.in/images/gallery/stage-at-indian-wedding-reception-19.jpg");

        mGalleryItems.add(ob1);
        mGalleryItems.add(ob2);
        mGalleryItems.add(ob3);
        mGalleryItems.add(ob4);
        mGalleryItems.add(ob5);

        ArrayList<String> photoUrl=new ArrayList<>();
        for(int i=0;i<mGalleryItems.size();i++)
        {
            photoUrl.add(mGalleryItems.get(i).getImg_path());
        }
        mAdapter = new GridImageAdapter(GalleryActivity.this, mGalleryItems,photoUrl);
        //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(GalleryActivity.this,2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setFocusable(false);

        mAdapter.notifyDataSetChanged();

    }

}
