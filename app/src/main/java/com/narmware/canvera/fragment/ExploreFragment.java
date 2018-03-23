package com.narmware.canvera.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.gson.Gson;
import com.narmware.canvera.MyApplication;
import com.narmware.canvera.R;
import com.narmware.canvera.activity.BookAppointActivity;
import com.narmware.canvera.activity.FeaturedGalleryActivity;
import com.narmware.canvera.adapter.CategoryAdapter;
import com.narmware.canvera.helpers.Constants;
import com.narmware.canvera.helpers.SharedPreferencesHelper;
import com.narmware.canvera.helpers.SupportFunctions;
import com.narmware.canvera.helpers.TopImgesAdapter;
import com.narmware.canvera.helpers.TopVideosAdapter;
import com.narmware.canvera.pojo.Category;
import com.narmware.canvera.pojo.CategoryResponse;
import com.narmware.canvera.pojo.ExploreBanner;
import com.narmware.canvera.pojo.ExploreBannerResponse;
import com.narmware.canvera.pojo.TopTakes;
import com.narmware.canvera.pojo.TopTakesResponse;
import com.narmware.canvera.pojo.TopVideoResponse;
import com.narmware.canvera.pojo.VideoPojo2;
import com.narmware.canvera.support.customfonts.MyButton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExploreFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExploreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExploreFragment extends Fragment implements TopImgesAdapter.Callbacks,TopVideosAdapter.Callbacks{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    @BindView(R.id.slider) protected SliderLayout mSlider;
    @BindView(R.id.custom_indicator) protected PagerIndicator custom_indicator;
    @BindView(R.id.recycler_popular_video_home) protected RecyclerView mPopularRecyclerView;
    @BindView(R.id.recycler_top_takes) protected RecyclerView mTopRecyclerView;
    @BindView(R.id.recycler_cat) protected RecyclerView mCatRecyclerView;
    @BindView(R.id.btn_apt) protected MyButton mBtnAppoint;

    ArrayList<VideoPojo2> mVideoData=new ArrayList<>();
    TopVideosAdapter mPopularAdapter;

    ArrayList<TopTakes> mTopTakes=new ArrayList<>();
    TopImgesAdapter mTopAdapter;

    ArrayList<Category> mCategories=new ArrayList<>();
    CategoryAdapter mCatAdapter;

    RequestQueue mVolleyRequest;
    String mUrl;
    int hitFlag=0;
    ArrayList<ExploreBanner> mBannerImages=new ArrayList<>();
    Dialog mNoConnectionDialog;


    public ExploreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExploreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExploreFragment newInstance(String param1, String param2) {
        ExploreFragment fragment = new ExploreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_explore, container, false);
        ButterKnife.bind(this, view);

        init();
        getExploreBanner();

        //setTopTakes();
        getFeaturedImages("1","0");

        //setPopularVideos();
        getFeaturedVideos("1","1");

       // setCategory();
        getCategory();
        return view;
    }

    private void init() {
        mVolleyRequest = Volley.newRequestQueue(getContext());

        mBtnAppoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), BookAppointActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setSlider() {
       // HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        HashMap<String,String> file_maps = new HashMap<String, String>();

        for(int i=0;i<mBannerImages.size();i++)
        {
           // Log.e("Banner slider size",mBannerImages.get(i).getBanner_title());
            file_maps.put(mBannerImages.get(i).getBanner_title(),mBannerImages.get(i).getBanner_url());
        }
        //file_maps.put("Hannibal", R.drawable.pre_mar_1);

        for(String name : file_maps.keySet()){
            //textSliderView displays image with text title
            //TextSliderView textSliderView = new TextSliderView(getActivity());

            //DefaultSliderView displays only image
            DefaultSliderView textSliderView = new DefaultSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mSlider.addSlider(textSliderView);
        }
        mSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        //mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomIndicator(custom_indicator);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setFitsSystemWindows(true);
        mSlider.setDuration(3000);

    }

    private void setPopularVideos() {
        SnapHelper snapHelper = new LinearSnapHelper();

        mPopularRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        mPopularRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mPopularRecyclerView.setNestedScrollingEnabled(false);
        snapHelper.attachToRecyclerView(mPopularRecyclerView);

        mPopularAdapter = new TopVideosAdapter(mVideoData,getContext());
        if(mVideoData.size()>1)
        {
            mPopularAdapter.setWithFooter(true);
        }
        mPopularAdapter.setCallback(this);
        mPopularRecyclerView.setAdapter(mPopularAdapter);
    }

    private void setTopTakes() {
        SnapHelper snapHelper = new LinearSnapHelper();
        mTopRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mTopRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mTopRecyclerView.addOnScrollListener(new CustomScrollListener());
        mTopRecyclerView.setNestedScrollingEnabled(false);
        snapHelper.attachToRecyclerView(mTopRecyclerView);

        mTopAdapter = new TopImgesAdapter(mTopTakes,getContext());
        if(mTopTakes.size()>1)
        {
            mTopAdapter.setWithFooter(true);
        }
        mTopAdapter.setCallback(this);
        mTopRecyclerView.setAdapter(mTopAdapter);
        mTopAdapter.notifyDataSetChanged();
    }

    private void setCategory() {

        mCatRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
        mCatRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mCatRecyclerView.setNestedScrollingEnabled(false);

        mCatAdapter = new CategoryAdapter(getContext(),mCategories);

        mCatRecyclerView.setAdapter(mCatAdapter);
        mCatAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickLoadMoreImages() {
        //Toast.makeText(getContext(),"No more images available", Toast.LENGTH_SHORT).show();
        SharedPreferencesHelper.setTopType(Constants.IMAGE_TYPE,getContext());
        Intent intent=new Intent(getContext(), FeaturedGalleryActivity.class);
        intent.putExtra(Constants.TOP_TYPE,Constants.IMAGE_TYPE);
        startActivity(intent);

    }

    @Override
    public void onClickLoadMoreVideos() {
        //Toast.makeText(getContext(),"No more videos available", Toast.LENGTH_SHORT).show();
        SharedPreferencesHelper.setTopType(Constants.VIDEO_TYPE,getContext());
        Intent intent=new Intent(getContext(), FeaturedGalleryActivity.class);
        intent.putExtra(Constants.TOP_TYPE,Constants.VIDEO_TYPE);
        startActivity(intent);
    }

    public class CustomScrollListener extends RecyclerView.OnScrollListener {
        public CustomScrollListener() {
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            switch (newState) {
                case RecyclerView.SCROLL_STATE_IDLE:
                    System.out.println("The RecyclerView is not scrolling");
                    break;
                case RecyclerView.SCROLL_STATE_DRAGGING:
                    //System.out.println("Scrolling now");
                    break;
                case RecyclerView.SCROLL_STATE_SETTLING:
                    //System.out.println("Scroll Settling");
                    break;

            }

        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            //for horizontal scrolling
            if (dx > 0) {
                System.out.println("Scrolled Left");
            } else if (dx < 0) {
                System.out.println("Scrolled Right");
            } else {
                System.out.println("No Horizontal Scrolled");
            }

            //for vertical scrolling
           /* if (dy > 0) {
                System.out.println("Scrolled Downwards");
            }

            else if (dy < 0) {
                System.out.println("Scrolled Upwards");
            }

            else {
                System.out.println("No Vertical Scrolled");
            }*/
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void getExploreBanner() {
        hitFlag=1;
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("getting details ...");
        dialog.setCancelable(false);
        dialog.show();

        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, MyApplication.URL_BANNER,null,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {
                    String testMasterDetails;

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {

                        try
                        {
                            //getting test master array
                            Log.e("Json_string",response.toString());
                            Gson gson = new Gson();
                            ExploreBannerResponse bannerResponse= gson.fromJson(response.toString(), ExploreBannerResponse.class);
                            ExploreBanner[] exploreBanners=bannerResponse.getData();

                            for(ExploreBanner item:exploreBanners)
                            {
                                mBannerImages.add(item);
                                Log.e("Banner Size",item.getBanner_title());
                                Log.e("Banner Size",mBannerImages.size()+"");

                            }

                                setSlider();
                        } catch (Exception e) {
                            e.printStackTrace();
                            dialog.dismiss();
                        }
                        dialog.dismiss();
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
                        dialog.dismiss();

                    }
                }
        );
        mVolleyRequest.add(obreq);
    }

    private void getFeaturedImages(String isFirst,String type) {
      /*  final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("getting images ...");
        dialog.setCancelable(false);
        dialog.show();*/
        hitFlag=2;

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
                            setTopTakes();
                           // mTopAdapter.notifyDataSetChanged();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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

    private void getFeaturedVideos(String isFirst,String type) {
      /*  final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("getting images ...");
        dialog.setCancelable(false);
        dialog.show();*/
        hitFlag=3;

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
                            Log.e("Json_string vid",response.toString());
                            Gson gson = new Gson();
                            TopVideoResponse topResponse= gson.fromJson(response.toString(), TopVideoResponse.class);
                            VideoPojo2[] topTakes=topResponse.getData();
                            for(VideoPojo2 item:topTakes)
                            {
                                mVideoData.add(item);
                                Log.e("Featured vid title",item.getUrl());
                                Log.e("Featured vid size",mVideoData.size()+"");

                            }

                            setPopularVideos();
                            //mPopularAdapter.notifyDataSetChanged();

                        } catch (Exception e) {
                            e.printStackTrace();
                            //  dialog.dismiss();
                        }
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        showNoConnectionDialog();
                        Log.e("Volley", "Test Error");

                    }
                }
        );
        mVolleyRequest.add(obreq);
    }

    private void getCategory() {
      /*  final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("getting images ...");
        dialog.setCancelable(false);
        dialog.show();*/
        hitFlag=4;

        HashMap<String,String> param = new HashMap();
        param.put(Constants.PHOTOGRAPHER_ID,"2");

        String url= SupportFunctions.appendParam(MyApplication.URL_GET_CATEGORIES,param);

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
                            Log.e("category Json_string",response.toString());
                            Gson gson = new Gson();
                            CategoryResponse categoryResponse= gson.fromJson(response.toString(), CategoryResponse.class);
                            Category[] categories=categoryResponse.getData();
                            ArrayList<Category> serverCategory=new ArrayList<>();

                            mCategories.add(new Category("Wedding & Pre Wedding","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRec7ZcFxlT05PnGGRg6nai4J5XuumSNzL-dqlMtvXCx7jgKB5Gag",false));
                            mCategories.add(new Category("Babies & kid","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRec7ZcFxlT05PnGGRg6nai4J5XuumSNzL-dqlMtvXCx7jgKB5Gag",false));
                            mCategories.add(new Category("Fashion & Portfolio","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRec7ZcFxlT05PnGGRg6nai4J5XuumSNzL-dqlMtvXCx7jgKB5Gag",false));
                            mCategories.add(new Category("Commercial & Object","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRec7ZcFxlT05PnGGRg6nai4J5XuumSNzL-dqlMtvXCx7jgKB5Gag",false));
                            mCategories.add(new Category("Corporate Event","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRec7ZcFxlT05PnGGRg6nai4J5XuumSNzL-dqlMtvXCx7jgKB5Gag",false));
                            mCategories.add(new Category("Travel & Portrait","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRec7ZcFxlT05PnGGRg6nai4J5XuumSNzL-dqlMtvXCx7jgKB5Gag",false));
                            mCategories.add(new Category("Nature & Wildlife","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRec7ZcFxlT05PnGGRg6nai4J5XuumSNzL-dqlMtvXCx7jgKB5Gag",false));
                            mCategories.add(new Category("Special Occasions","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRec7ZcFxlT05PnGGRg6nai4J5XuumSNzL-dqlMtvXCx7jgKB5Gag",false));

                            for(Category item:categories)
                            {
                                serverCategory.add(item);
                                Log.e("category title",item.getCat_title());

                            }

                            for(int i=0;i<serverCategory.size();i++)
                            {
                                String cat_title=serverCategory.get(i).getCat_title();
                                String cat_id=serverCategory.get(i).getCat_id();
                                Log.e("Cat",cat_title);

                                for(int j=0;j<mCategories.size();j++)
                                {
                                    if(cat_title.equals(mCategories.get(j).getCat_name()))
                                    {
                                        mCategories.get(j).setIs_available(true);
                                        mCategories.get(j).setCat_id(cat_id);
                                        Log.e("Category",mCategories.get(j).getCat_name());
                                    }
                                }
                            }

                            setCategory();

                        } catch (Exception e) {
                            e.printStackTrace();
                            //  dialog.dismiss();
                        }
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        showNoConnectionDialog();
                        Log.e("Volley", "Test Error");

                    }
                }
        );
        mVolleyRequest.add(obreq);
    }

    private void showNoConnectionDialog() {
        if (getContext() != null) {
            mNoConnectionDialog = new Dialog(getContext(), android.R.style.Theme_Light_NoTitleBar_Fullscreen);
            mNoConnectionDialog.setContentView(R.layout.dialog_noconnectivity);
            mNoConnectionDialog.setCancelable(false);
            mNoConnectionDialog.show();


            Button exit = mNoConnectionDialog.findViewById(R.id.dialog_no_connec_exit);
            Button tryAgain = mNoConnectionDialog.findViewById(R.id.dialog_no_connec_try_again);

            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatActivity act = (AppCompatActivity) getContext();
                    act.finish();
                }
            });

            tryAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Banner;
                    if (hitFlag == 1) {
                        getExploreBanner();
                    }
                    //fetured imgs
                    if (hitFlag == 2) {
                        getFeaturedImages("1", "0");
                    }

                    //featured videos
                    if (hitFlag == 3) {
                        getFeaturedVideos("1", "1");
                    }
                    //categories
                    if(hitFlag == 4)
                    {
                        getCategory();
                    }
                    mNoConnectionDialog.dismiss();
                }
            });
        }
    }
}
