package com.narmware.canvera.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.narmware.canvera.MyApplication;
import com.narmware.canvera.R;
import com.narmware.canvera.adapter.GridImageAdapter;
import com.narmware.canvera.helpers.Constants;
import com.narmware.canvera.helpers.SharedPreferencesHelper;
import com.narmware.canvera.helpers.SupportFunctions;
import com.narmware.canvera.pojo.GalleryItem;
import com.narmware.canvera.pojo.GalleryItemResponse;
import com.narmware.canvera.support.customfonts.MyTextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryActivity extends AppCompatActivity {

    @BindView(R.id.recycler) RecyclerView mRecyclerView;
    @BindView(R.id.btn_switch) ImageButton mBtnSwitch;
    @BindView(R.id.btn_back) ImageButton mBtnBack;
    @BindView(R.id.gallery_title) MyTextView mTxtGalleryItem;

    GridImageAdapter mAdapter;
    List<GalleryItem> mGalleryItems=new ArrayList<>();
    String mTxtTitle,mAlbumId,mCatId;
    ArrayList<String> photoUrl=new ArrayList<>();
    RequestQueue mVolleyRequest;
    Dialog mNoConnectionDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_gallery);
        getSupportActionBar().hide();
        Intent intent=getIntent();
        mTxtTitle=intent.getStringExtra(Constants.GALLERY_TITLE);
        mAlbumId=intent.getStringExtra(Constants.ALBUM_ID);
        mCatId=intent.getStringExtra(Constants.CAT_ID);

        init();
    }

    private void init() {
        ButterKnife.bind(this);
        mVolleyRequest = Volley.newRequestQueue(GalleryActivity.this);

        //set list view when isGrid==false, set grid view when isGrid==true
        mTxtGalleryItem.setText(mTxtTitle);


        if(SharedPreferencesHelper.getIsGrid(GalleryActivity.this)==false) {
            mBtnSwitch.setImageResource(R.drawable.ic_grid);
            setAdapter(new LinearLayoutManager(GalleryActivity.this));

            if(mCatId==null) {
                getGalleryImages();
            }
            else {
                getCategoryImages();
            }
        }
        else if(SharedPreferencesHelper.getIsGrid(GalleryActivity.this)==true) {
            mBtnSwitch.setImageResource(R.drawable.ic_list);
            //setAdapter(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
            setAdapter(new GridLayoutManager(GalleryActivity.this,3));

            if(mCatId==null) {
                getGalleryImages();
            }
            else {
                getCategoryImages();
            }
        }

        mBtnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(GalleryActivity.this, SharedPreferencesHelper.getIsGrid(GalleryActivity.this)+"", Toast.LENGTH_SHORT).show();
//set grid
                if(SharedPreferencesHelper.getIsGrid(GalleryActivity.this)==false) {
                    mBtnSwitch.setImageResource(R.drawable.ic_list);
                    SharedPreferencesHelper.setIsGrid(true, GalleryActivity.this);
                    //setAdapter(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
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

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setAdapter(RecyclerView.LayoutManager mLayoutManager){

        mAdapter = new GridImageAdapter(GalleryActivity.this, mGalleryItems,photoUrl);
        //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(GalleryActivity.this,2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setFocusable(false);

        mAdapter.notifyDataSetChanged();

    }

    private void getGalleryImages() {
      /*  final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("getting images ...");
        dialog.setCancelable(false);
        dialog.show();*/

        HashMap<String,String> param = new HashMap();
        param.put(Constants.ALBUM_ID,mAlbumId);

        String url= SupportFunctions.appendParam(MyApplication.URL_ALBUM_GALLERY,param);

        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,url,null,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {

                        try
                        {
                            //getting test master array
                            Log.e("Gallery Json_string",response.toString());
                            Gson gson = new Gson();
                            GalleryItemResponse galleryResponse= gson.fromJson(response.toString(), GalleryItemResponse.class);
                            GalleryItem[] galleryItem=galleryResponse.getData();
                            mGalleryItems.clear();
                            for(GalleryItem item:galleryItem)
                            {
                                mGalleryItems.add(item);
                                Log.e("Gallery img title",item.getImg_path());
                                Log.e("Gallery img size",mGalleryItems.size()+"");

                            }
                            photoUrl.clear();
                            for(int i=0;i<mGalleryItems.size();i++)
                            {

                                if(mGalleryItems.get(i).getImg_type().equals(Constants.IMAGE_TYPE))
                                {
                                    Log.e("Gallery type", mGalleryItems.get(i).getImg_path());
                                    photoUrl.add(mGalleryItems.get(i).getImg_path());
                                    Log.e("Photo url size", photoUrl.size() + "");
                                }
                            }

                            mAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                            //  dialog.dismiss();
                        }
                        //dialog.dismiss();
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Test Error");
                        showNoConnectionDialog();
                        //dialog.dismiss();

                    }
                }
        );
        mVolleyRequest.add(obreq);
    }

    private void getCategoryImages() {
      /*  final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("getting images ...");
        dialog.setCancelable(false);
        dialog.show();*/

        HashMap<String,String> param = new HashMap();
        param.put(Constants.PHOTOGRAPHER_ID,"2");
        param.put(Constants.CAT_ID,mCatId);

        String url= SupportFunctions.appendParam(MyApplication.URL_GET_CATEGORY_ALBUM,param);

        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,url,null,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {

                        try
                        {
                            //getting test master array
                            Log.e("Cat GalleryJson_string",response.toString());
                            Gson gson = new Gson();
                            GalleryItemResponse galleryResponse= gson.fromJson(response.toString(), GalleryItemResponse.class);
                            GalleryItem[] galleryItem=galleryResponse.getData();
                            mGalleryItems.clear();
                            for(GalleryItem item:galleryItem)
                            {
                                mGalleryItems.add(item);
                                Log.e("Gallery img title",item.getImg_path());
                                Log.e("Gallery img size",mGalleryItems.size()+"");

                            }
                            photoUrl.clear();
                            for(int i=0;i<mGalleryItems.size();i++)
                            {

                                if(mGalleryItems.get(i).getImg_type().equals(Constants.IMAGE_TYPE))
                                {
                                    Log.e("Gallery type", mGalleryItems.get(i).getImg_path());
                                    photoUrl.add(mGalleryItems.get(i).getImg_path());
                                    Log.e("Photo url size", photoUrl.size() + "");
                                }
                            }

                            mAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                            //  dialog.dismiss();
                        }
                        //dialog.dismiss();
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Test Error");
                        showNoConnectionDialog();
                        //dialog.dismiss();

                    }
                }
        );
        mVolleyRequest.add(obreq);
    }

    private void showNoConnectionDialog() {
        mNoConnectionDialog = new Dialog(GalleryActivity.this, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        mNoConnectionDialog.setContentView(R.layout.dialog_noconnectivity);
        mNoConnectionDialog.setCancelable(false);
        mNoConnectionDialog.show();

        Button exit = mNoConnectionDialog.findViewById(R.id.dialog_no_connec_exit);
        Button tryAgain = mNoConnectionDialog.findViewById(R.id.dialog_no_connec_try_again);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getGalleryImages();
                mNoConnectionDialog.dismiss();
            }
        });
    }

}
