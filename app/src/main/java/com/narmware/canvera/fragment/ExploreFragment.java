package com.narmware.canvera.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
import com.narmware.canvera.adapter.PopularVideoAdapter;
import com.narmware.canvera.adapter.TopTakesAdapter;
import com.narmware.canvera.pojo.ExploreBanner;
import com.narmware.canvera.pojo.ExploreBannerResponse;
import com.narmware.canvera.pojo.TopTakes;
import com.narmware.canvera.pojo.VideoPojo2;

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
public class ExploreFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ArrayList<VideoPojo2> mVideoData;
    PopularVideoAdapter mPopularAdapter;

    ArrayList<TopTakes> mTopTakes;
    TopTakesAdapter mTopAdapter;

    RequestQueue mVolleyRequest;
    String mUrl;
    Dialog mNoConnectionDialog;
    ArrayList<ExploreBanner> mBannerImages;
    @BindView(R.id.slider) protected SliderLayout mSlider;
    @BindView(R.id.custom_indicator) protected PagerIndicator custom_indicator;
    @BindView(R.id.recycler_popular_video_home) protected RecyclerView mPopularRecyclerView;
    @BindView(R.id.recycler_top_takes) protected RecyclerView mTopRecyclerView;

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
        setPopularVideos();
        setTopTakes();
        setSlider();
        return view;
    }

    private void init() {
        mBannerImages=new ArrayList<>();
        mVolleyRequest = Volley.newRequestQueue(getContext());
    }

    private void setSlider() {
        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        Log.e("Banner Size",mBannerImages.size()+"");

        file_maps.put("Hannibal", R.drawable.pre_mar_1);
            file_maps.put("Big Bang Theory", R.drawable.wedding_couple);

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
        mSlider.setDuration(3000);

    }

    private void setDummyPopularVideos() {
        mVideoData = new ArrayList<>();

        mVideoData.add(new VideoPojo2("Fall in love with learning", "https://www.youtube.com/watch?v=CMDlZBo_lrc"));
        mVideoData.add(new VideoPojo2("Score 90% & above easily", "https://www.youtube.com/watch?v=KruPef5zQvY"));
        mVideoData.add(new VideoPojo2("How to learn faster", "https://www.youtube.com/watch?v=B9SptdjpJBQ"));
        mVideoData.add(new VideoPojo2("Finals Week! - 6 Study Tips & Tricks", "https://www.youtube.com/watch?v=a9FduCpUhoI"));
        mVideoData.add(new VideoPojo2("11 Secrets to Memorize Things Quicker Than Others", "https://www.youtube.com/watch?v=mHdy1xS59xA"));
        mVideoData.add(new VideoPojo2("How to remember/retain better anything you study: Practical/Scientific Tips - Roman Saini", "https://www.youtube.com/watch?v=l49QT-vPOPw"));
    }

    private void setPopularVideos() {
        mPopularRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        mPopularRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mPopularRecyclerView.setNestedScrollingEnabled(false);

        setDummyPopularVideos();
        mPopularAdapter = new PopularVideoAdapter(getContext(), mVideoData);
        mPopularRecyclerView.setAdapter(mPopularAdapter);
    }

    private void setDummyTopTakes() {
        mTopTakes = new ArrayList<>();

        mTopTakes.add(new TopTakes("http://www.indiamarks.com/wp-content/uploads/Indian-Wedding-1.jpg"));
        mTopTakes.add(new TopTakes("http://www.indiamarks.com/wp-content/uploads/Indian-Wedding-1.jpg"));
        mTopTakes.add(new TopTakes("http://www.indiamarks.com/wp-content/uploads/Indian-Wedding-1.jpg"));
        mTopTakes.add(new TopTakes("http://www.marrymeweddings.in/images/gallery/stage-at-indian-wedding-reception-19.jpg"));
        mTopTakes.add(new TopTakes("http://www.marrymeweddings.in/images/gallery/stage-at-indian-wedding-reception-19.jpg"));
    }

    private void setTopTakes() {
        mTopRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mTopRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mTopRecyclerView.addOnScrollListener(new CustomScrollListener());
        mTopRecyclerView.setNestedScrollingEnabled(false);

        setDummyTopTakes();
        mTopAdapter = new TopTakesAdapter(getContext(), mTopTakes);
        mTopRecyclerView.setAdapter(mTopAdapter);
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
                System.out.println("Scrolled Right");
            } else if (dx < 0) {
                System.out.println("Scrolled Left");

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
                        dialog.dismiss();

                    }
                }
        );
        mVolleyRequest.add(obreq);
    }

    private void showNoConnectionDialog() {
        mNoConnectionDialog = new Dialog(getContext(), android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        mNoConnectionDialog.setContentView(R.layout.dialog_noconnectivity);
        mNoConnectionDialog.setCancelable(false);
        mNoConnectionDialog.show();

        Button exit = mNoConnectionDialog.findViewById(R.id.dialog_no_connec_exit);
        Button tryAgain = mNoConnectionDialog.findViewById(R.id.dialog_no_connec_try_again);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AppCompatActivity act = (AppCompatActivity) getContext();
                act.finish();
            }
        });

        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNoConnectionDialog.dismiss();
            }
        });
    }
}
