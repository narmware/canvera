package com.narmware.canvera.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
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
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.narmware.canvera.R;
import com.narmware.canvera.adapter.SharedPhotoAdapter;
import com.narmware.canvera.helpers.Constants;
import com.narmware.canvera.pojo.SharedPhoto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SharedPhotobookFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SharedPhotobookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SharedPhotobookFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    @BindView(R.id.btn_add_album) Button mBtnAddAlbum;
    @BindView(R.id.bottom_sheet) LinearLayout layoutBottomSheet;
    @BindView(R.id.recycler) RecyclerView mRecyclerView;
    SharedPhotoAdapter mAdapter;
    List<SharedPhoto> mPhotoItems;
    RequestQueue mVolleyRequest;
    String mUrl;
    Dialog mNoConnectionDialog;

    BottomSheetBehavior sheetBehavior;
    public SharedPhotobookFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyPhotoBookFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SharedPhotobookFragment newInstance(String param1, String param2) {
        SharedPhotobookFragment fragment = new SharedPhotobookFragment();
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
        View view= inflater.inflate(R.layout.fragment_shared_photo_book, container, false);
        init(view);
        setAdapter(view);
        return view;
    }

    private void init(View view) {
        ButterKnife.bind(this,view);
        mVolleyRequest = Volley.newRequestQueue(getContext());

        mPhotoItems=new ArrayList<>();
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                       // Toast.makeText(getContext(), "hide Sheet", Toast.LENGTH_SHORT).show();
                        break;

                    case BottomSheetBehavior.STATE_EXPANDED: {
                        //Toast.makeText(getContext(), "open Sheet", Toast.LENGTH_SHORT).show();
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        //Toast.makeText(getContext(), "close Sheet", Toast.LENGTH_SHORT).show();
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        //Toast.makeText(getContext(), "Dragging", Toast.LENGTH_SHORT).show();

                        if (mBtnAddAlbum.getText().toString().equals(Constants.ADD_ALBUM)) {
                            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        }
                        if (mBtnAddAlbum.getText().toString().equals(Constants.CLOSE_ALBUM)){
                            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        }
                            break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }

        });
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        mBtnAddAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getContext(), "Add album", Toast.LENGTH_SHORT).show();

                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    mBtnAddAlbum.setBackgroundColor(Color.WHITE);
                  /*  TransitionDrawable transition = (TransitionDrawable) mBtnAddAlbum.getBackground();
                    transition.startTransition(500);*/
                    mBtnAddAlbum.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
                    mBtnAddAlbum.setText(Constants.CLOSE_ALBUM);
                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    mBtnAddAlbum.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
                   /* TransitionDrawable transition = (TransitionDrawable) mBtnAddAlbum.getBackground();
                    transition.reverseTransition(500);*/
                    mBtnAddAlbum.setTextColor(Color.WHITE);
                    mBtnAddAlbum.setText(Constants.ADD_ALBUM);

                    //sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            }
        });
    }


    public void setAdapter(View v){

        SharedPhoto ob1=new SharedPhoto("My Wedding","http://www.indiamarks.com/wp-content/uploads/Indian-Wedding-1.jpg","My Wedding album");
        SharedPhoto ob2=new SharedPhoto("Reception","http://www.marrymeweddings.in/images/gallery/stage-at-indian-wedding-reception-19.jpg","My Birthday album");
        SharedPhoto ob3=new SharedPhoto("Reception","http://www.marrymeweddings.in/images/gallery/stage-at-indian-wedding-reception-19.jpg","My Reception album");
        SharedPhoto ob4=new SharedPhoto("Reception","http://www.marrymeweddings.in/images/gallery/stage-at-indian-wedding-reception-19.jpg","My Engagement album");
        SharedPhoto ob5=new SharedPhoto("Reception","http://www.marrymeweddings.in/images/gallery/stage-at-indian-wedding-reception-19.jpg","My Party album");

        mPhotoItems.add(ob1);
        mPhotoItems.add(ob2);
        mPhotoItems.add(ob3);
        mPhotoItems.add(ob4);
        mPhotoItems.add(ob5);

        mRecyclerView = v.findViewById(R.id.recycler);
        mAdapter = new SharedPhotoAdapter(getContext(), mPhotoItems);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new CustomScrollListener());
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setFocusable(false);

        mAdapter.notifyDataSetChanged();

    }

    public class CustomScrollListener extends RecyclerView.OnScrollListener {
        public CustomScrollListener() {
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            switch (newState) {
                case RecyclerView.SCROLL_STATE_IDLE:
                    //System.out.println("The RecyclerView is not scrolling");
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
           /* if (dx > 0) {
                System.out.println("Scrolled Right");
            } else if (dx < 0) {
                System.out.println("Scrolled Left");
            } else {
                System.out.println("No Horizontal Scrolled");
            }*/

            //for vertical scrolling
            if (dy > 0) {
                if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    mBtnAddAlbum.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
                    mBtnAddAlbum.setTextColor(Color.WHITE);
                    mBtnAddAlbum.setText(Constants.ADD_ALBUM);

                }
                sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                System.out.println("Scrolled Downwards");

                }

                else if (dy < 0) {
                if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    mBtnAddAlbum.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
                    mBtnAddAlbum.setTextColor(Color.WHITE);
                    mBtnAddAlbum.setText(Constants.ADD_ALBUM);
                }
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                System.out.println("Scrolled Upwards");
            }

            else {
                System.out.println("No Vertical Scrolled");
            }
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

    @Override
    public void onResume() {
        super.onResume();
        //Toast.makeText(getContext(), "onResume", Toast.LENGTH_SHORT).show();
     /*   Toast.makeText(getContext(), "onResume", Toast.LENGTH_SHORT).show();
        YoYo.with(Techniques.FadeInRight)
                .duration(1500)
                .playOn(mFab);*/
    }

    @Override
    public void onPause() {
        super.onPause();
        //Toast.makeText(getContext(), "onPause", Toast.LENGTH_SHORT).show();
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

    private void GetSharedPhotoBook() {
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("getting details ...");
        dialog.setCancelable(false);
        dialog.show();

        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,mUrl,null,
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
                            JSONArray testMasterArray = response.getJSONArray("TESTMASTER");
                            // testMasterDetails = testMasterArray.toString();

                            Gson gson = new Gson();
                            // TestMasterPojo[] testMasterPojo= gson.fromJson(testMasterDetails, TestMasterPojo[].class);

                        } catch (JSONException e) {
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
