package com.narmware.patima.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.google.gson.Gson;
import com.narmware.patima.MyApplication;
import com.narmware.patima.R;
import com.narmware.patima.activity.ContactActivity;
import com.narmware.patima.helpers.Constants;
import com.narmware.patima.helpers.SharedPreferencesHelper;
import com.narmware.patima.helpers.SupportFunctions;
import com.narmware.patima.pojo.Feedback;
import com.narmware.patima.pojo.Register;
import com.narmware.patima.support.customfonts.MyButton;
import com.narmware.patima.support.customfonts.MyEditText;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RequestQueue mVolleyRequest;
    private OnFragmentInteractionListener mListener;
    @BindView(R.id.edt_first_name) protected MyEditText mEdtFirstName;
    @BindView(R.id.edt_middle_name) protected MyEditText mEdtMiddleName;
    @BindView(R.id.edt_last_name) protected MyEditText mEdtLastName;
    @BindView(R.id.edt_mail) protected MyEditText mEdtMail;
    @BindView(R.id.edt_mobile) protected MyEditText mEdtMobile;
    @BindView(R.id.edt_address) protected MyEditText mEdtAddress;
    @BindView(R.id.btn_submit) protected MyButton mBtnSubmit;

    String mFirstName,mLastName,mMiddleName,mMobile,mAddress,mEmail;
    int validFlag=0;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        View view= inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);
        mVolleyRequest = Volley.newRequestQueue(getContext());

        //used to hide keyboard bydefault
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validFlag=0;
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                mFirstName=mEdtFirstName.getText().toString().trim();
                mMiddleName=mEdtMiddleName.getText().toString().trim();
                mLastName=mEdtLastName.getText().toString().trim();
                mEmail=mEdtMail.getText().toString().trim();
                mAddress=mEdtAddress.getText().toString().trim();
                mMobile=mEdtMobile.getText().toString().trim();

                if(mFirstName.equals("")||mFirstName==null)
                {
                    validFlag=1;
                    mEdtFirstName.setError("Please enter first name");
                }
                if(mLastName.equals("")||mLastName==null)
                {
                    validFlag=1;
                    mEdtLastName.setError("Please enter last name");
                }
                if(mMiddleName.equals("")||mMiddleName==null)
                {
                    validFlag=1;
                    mEdtMiddleName.setError("Please enter middle name");
                }
                if(mMobile.equals("")||mMobile==null||mMobile.length()<10)
                {
                    validFlag=1;
                    mEdtMobile.setError("Please enter valid mobile number");
                }
                if(!mEmail.matches(emailPattern))
                {
                    validFlag=1;
                    mEdtMail.setError("Please enter valid email");
                }
                if(mEmail.equals("")||mEmail==null)
                {
                    validFlag=1;
                    mEdtMail.setError("Please enter valid email");

                }
                if(mAddress.equals("")||mAddress==null)
                {
                    validFlag=1;
                    mEdtAddress.setError("Please enter address");
                }

                if(validFlag==0)
                {
                    registerUser();
                }
            }
        });
        return view;
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

    private void registerUser() {
      /*  final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("getting images ...");
        dialog.setCancelable(false);
        dialog.show();*/

        Register register=new Register();
        register.setFirst_name(mFirstName);
        register.setMiddle_name(mMiddleName);
        register.setLast_name(mLastName);
        register.setAdd(mAddress);
        register.setMobile(mMobile);
        register.setPhm_id("3");
        register.setEmail(mEmail);

        Gson gson = new Gson();
        String json_string=gson.toJson(register);
        Log.e("Register json",json_string);

        HashMap<String,String> param = new HashMap();
        param.put(Constants.JSON_STRING,json_string);

        String url= SupportFunctions.appendParam(MyApplication.URL_REGISTER,param);
        Log.e("Url",url);

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
                            Feedback feed=gson.fromJson(response.toString(),Feedback.class);
                            int res= Integer.parseInt(feed.getResponse());
                            if(res==Constants.SUCCESS)
                            {
                                Toast.makeText(getContext(),"Already Registered",Toast.LENGTH_LONG).show();
                            }
                            if(res==Constants.VALID_DATA)
                            {
                                Toast.makeText(getContext(),"Registered successfully",Toast.LENGTH_LONG).show();
                            }
                            if(res==Constants.ERROR)
                            {
                                Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_LONG).show();
                            }

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
