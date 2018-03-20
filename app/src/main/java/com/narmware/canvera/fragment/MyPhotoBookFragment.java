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
import com.google.gson.Gson;
import com.narmware.canvera.MyApplication;
import com.narmware.canvera.R;
import com.narmware.canvera.adapter.MyPhotoAdapter;
import com.narmware.canvera.helpers.Constants;
import com.narmware.canvera.helpers.SupportFunctions;
import com.narmware.canvera.pojo.MyPhoto;
import com.narmware.canvera.pojo.MyPhotoResponse;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyPhotoBookFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyPhotoBookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyPhotoBookFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;
    MyPhotoAdapter mAdapter;
    List<MyPhoto> mPhotoItems=new ArrayList<>();
    RequestQueue mVolleyRequest;
    String mUrl;
    Dialog mNoConnectionDialog;
    public MyPhotoBookFragment() {
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
    public static MyPhotoBookFragment newInstance(String param1, String param2) {
        MyPhotoBookFragment fragment = new MyPhotoBookFragment();
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
        View view= inflater.inflate(R.layout.fragment_my_photo_book, container, false);
        init(view);
        setAdapter(view);
        GetMyPhotoBook();
        return view;
    }
    public void setAdapter(View v){

/*
        MyPhoto ob1=new MyPhoto("My Wedding","http://www.indiamarks.com/wp-content/uploads/Indian-Wedding-1.jpg","My Wedding album");
        MyPhoto ob2=new MyPhoto("Reception","http://www.marrymeweddings.in/images/gallery/stage-at-indian-wedding-reception-19.jpg","My Reception album");
        MyPhoto ob3=new MyPhoto("Reception","http://www.marrymeweddings.in/images/gallery/stage-at-indian-wedding-reception-19.jpg","My Birthday album");
        MyPhoto ob4=new MyPhoto("Reception","http://www.marrymeweddings.in/images/gallery/stage-at-indian-wedding-reception-19.jpg","My Party album");
        MyPhoto ob5=new MyPhoto("Reception","http://www.marrymeweddings.in/images/gallery/stage-at-indian-wedding-reception-19.jpg","My Pre-Wedding album");

        mPhotoItems.add(ob1);
        mPhotoItems.add(ob2);
        mPhotoItems.add(ob3);
        mPhotoItems.add(ob4);
        mPhotoItems.add(ob5);
*/

        mRecyclerView = v.findViewById(R.id.recycler);
        mAdapter = new MyPhotoAdapter(getContext(), mPhotoItems);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setFocusable(false);

        mAdapter.notifyDataSetChanged();

    }

    private void init(View view) {
        ButterKnife.bind(this, view);
        mVolleyRequest = Volley.newRequestQueue(getContext());
        mPhotoItems = new ArrayList<>();
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

    private void GetMyPhotoBook() {
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("getting details ...");
        dialog.setCancelable(false);
        dialog.show();

        HashMap<String,String> param = new HashMap();
        param.put(Constants.USER_ID,"1");

        String url= SupportFunctions.appendParam(MyApplication.URL_MY_ALBUM,param);

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
                            // testMasterDetails = testMasterArray.toString();

                            Log.e("myphotobook Json_string",response.toString());
                            Gson gson = new Gson();

                            MyPhotoResponse photoResponse= gson.fromJson(response.toString(), MyPhotoResponse.class);
                            MyPhoto[] photo=photoResponse.getData();
                            for(MyPhoto item:photo)
                            {
                                mPhotoItems.add(item);
                                Log.e("Featured img title",item.getPhoto_title());
                                Log.e("Featured img size",mPhotoItems.size()+"");

                            }
                            mAdapter.notifyDataSetChanged();

                            // TestMasterPojo[] testMasterPojo= gson.fromJson(testMasterDetails, TestMasterPojo[].class);

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
                GetMyPhotoBook();
                mNoConnectionDialog.dismiss();
            }
        });
    }
}
