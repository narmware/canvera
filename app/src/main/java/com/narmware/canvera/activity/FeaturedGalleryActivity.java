package com.narmware.canvera.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.narmware.canvera.MyApplication;
import com.narmware.canvera.R;
import com.narmware.canvera.adapter.FeaturedGalleryAdapter;
import com.narmware.canvera.helpers.Constants;
import com.narmware.canvera.helpers.SupportFunctions;
import com.narmware.canvera.pojo.TopTakes;
import com.narmware.canvera.pojo.TopTakesResponse;
import com.narmware.canvera.pojo.VideoPojo2;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeaturedGalleryActivity extends AppCompatActivity {

    @BindView(R.id.recycler) RecyclerView mRecyclerView;
    FeaturedGalleryAdapter mAdapter;
    RequestQueue mVolleyRequest;
    ArrayList<TopTakes> mTopTakes=new ArrayList<>();
    ArrayList<VideoPojo2> mVideoData=new ArrayList<>();

    String mType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_featured_img_gallery);
        getSupportActionBar().hide();

        Intent intent=getIntent();
        mType=intent.getStringExtra(Constants.TOP_TYPE);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        mVolleyRequest = Volley.newRequestQueue(FeaturedGalleryActivity.this);

        if(mType.equals(Constants.IMAGE_TYPE)) {
            getFeaturedImages("1",Constants.IMAGE_TYPE);
        }

        if(mType.equals(Constants.VIDEO_TYPE)) {
            getFeaturedVideos("1",Constants.VIDEO_TYPE);
        }
        //setAdapter();
        //set list view when isGrid==false, set grid view when isGrid==true

    }

    public void setImageAdapter(RecyclerView.LayoutManager mLayoutManager){
        /*mGalleryItems=new ArrayList<>();
        FeaturedGalleryImgItem ob1=new FeaturedGalleryImgItem("title","http://www.indiamarks.com/wp-content/uploads/Indian-Wedding-1.jpg");
        FeaturedGalleryImgItem ob2=new FeaturedGalleryImgItem("title","http://www.marrymeweddings.in/images/gallery/stage-at-indian-wedding-reception-19.jpg");
        FeaturedGalleryImgItem ob3=new FeaturedGalleryImgItem("title","http://www.marrymeweddings.in/images/gallery/stage-at-indian-wedding-reception-19.jpg");

        mGalleryItems.add(ob1);
        mGalleryItems.add(ob2);
        mGalleryItems.add(ob3);*/


        ArrayList<String> photoUrl=new ArrayList<>();
        for(int i=0;i<mTopTakes.size();i++)
        {
            photoUrl.add(mTopTakes.get(i).getUrl());
        }
        mAdapter = new FeaturedGalleryAdapter(FeaturedGalleryActivity.this, mTopTakes,photoUrl);
        //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(GalleryActivity.this,2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setFocusable(false);

        mAdapter.notifyDataSetChanged();

    }

    private void getFeaturedImages(String isFirst,String type) {
      /*  final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("getting images ...");
        dialog.setCancelable(false);
        dialog.show();*/

        HashMap<String,String> param = new HashMap();
        param.put(Constants.IS_FIRST,isFirst);
        param.put(Constants.TOP_TYPE,type);

        String url= SupportFunctions.appendParam(MyApplication.URL_FEATURED_IMGS,param);

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
                            Log.e("Json_string",response.toString());
                            Gson gson = new Gson();
                            TopTakesResponse topResponse= gson.fromJson(response.toString(), TopTakesResponse.class);
                            TopTakes[] topTakes=topResponse.getData();
                            for(TopTakes item:topTakes)
                            {
                                mTopTakes.add(item);

                                Log.e("Featured img title",item.getUrl());
                                Log.e("Featured img size",mTopTakes.size()+"");

                            }

                            setImageAdapter(new LinearLayoutManager(FeaturedGalleryActivity.this));

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
                        //dialog.dismiss();

                    }
                }
        );
        mVolleyRequest.add(obreq);
    }

    private void getFeaturedVideos(String isFirst,String type) {
      /*  final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("getting images ...");
        dialog.setCancelable(false);
        dialog.show();*/

        HashMap<String,String> param = new HashMap();
        param.put(Constants.IS_FIRST,isFirst);
        param.put(Constants.TOP_TYPE,type);

        String url= SupportFunctions.appendParam(MyApplication.URL_FEATURED_IMGS,param);

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
                            Log.e("Json_string",response.toString());
                            Gson gson = new Gson();
                            TopTakesResponse topResponse= gson.fromJson(response.toString(), TopTakesResponse.class);
                            TopTakes[] topTakes=topResponse.getData();
                            for(TopTakes item:topTakes)
                            {
                                mTopTakes.add(item);

                                Log.e("Featured img title",item.getUrl());
                                Log.e("Featured img size",mTopTakes.size()+"");

                            }

                            setImageAdapter(new LinearLayoutManager(FeaturedGalleryActivity.this));

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
                        //dialog.dismiss();

                    }
                }
        );
        mVolleyRequest.add(obreq);
    }

}
