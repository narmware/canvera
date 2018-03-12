package com.narmware.canvera.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.narmware.canvera.R;
import com.narmware.canvera.adapter.PopularVideoAdapter;
import com.narmware.canvera.pojo.VideoPojo2;

import java.util.ArrayList;

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
    RecyclerView mPopularRecyclerView;

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
        setPopularVideos(view);
        return view;
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

    private void setPopularVideos(View view) {
        mPopularRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_popular_video_home);
        mPopularRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mPopularRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mPopularRecyclerView.setNestedScrollingEnabled(false);

        setDummyPopularVideos();
        mPopularAdapter = new PopularVideoAdapter(getContext(), mVideoData);
        mPopularRecyclerView.setAdapter(mPopularAdapter);
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
}
