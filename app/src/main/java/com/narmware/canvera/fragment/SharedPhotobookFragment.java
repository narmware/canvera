package com.narmware.canvera.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.narmware.canvera.R;
import com.narmware.canvera.adapter.SharedPhotoAdapter;
import com.narmware.canvera.pojo.SharedPhoto;

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
                        //Toast.makeText(getContext(), "dragg Sheet", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "Add album", Toast.LENGTH_SHORT).show();

                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    //sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            }
        });
    }


    public void setAdapter(View v){

        SharedPhoto ob1=new SharedPhoto("My Wedding","http://www.indiamarks.com/wp-content/uploads/Indian-Wedding-1.jpg");
        SharedPhoto ob2=new SharedPhoto("Reception","http://www.marrymeweddings.in/images/gallery/stage-at-indian-wedding-reception-19.jpg");
        mPhotoItems.add(ob1);
        mPhotoItems.add(ob2);

        mRecyclerView = v.findViewById(R.id.recycler);
        mAdapter = new SharedPhotoAdapter(getContext(), mPhotoItems);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setFocusable(false);

        mAdapter.notifyDataSetChanged();

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
}
