package com.narmware.patima.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.narmware.patima.MyApplication;
import com.narmware.patima.R;
import com.narmware.patima.activity.HomeActivity;
import com.narmware.patima.helpers.Constants;
import com.narmware.patima.helpers.SharedPreferencesHelper;
import com.narmware.patima.helpers.SupportFunctions;
import com.narmware.patima.pojo.Login;
import com.narmware.patima.support.customfonts.MyButton;
import com.narmware.patima.support.customfonts.MyEditText;
import com.narmware.patima.support.customfonts.MyTextView;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignInFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
@BindView(R.id.signin) protected MyTextView mTxtSignIn;
    @BindView(R.id.forget) protected MyTextView mTxtForget;
    @BindView(R.id.edt_user) protected MyEditText mEdtUserName;
    @BindView(R.id.edt_pass) protected MyEditText mEdtPass;
    String mUserName,mPassword;
    String mForgetEmail;
    int validationFlag=0;
    RequestQueue mVolleyRequest;
    Dialog mNoConnectionDialog;
    Dialog mForgetDialog;

    public SignInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignInFragment newInstance(String param1, String param2) {
        SignInFragment fragment = new SignInFragment();
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
        View view= inflater.inflate(R.layout.fragment_sign_in, container, false);
        ButterKnife.bind(this, view);
        init(view);
        mTxtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserName=mEdtUserName.getText().toString();
                mPassword=mEdtPass.getText().toString();
                validationFlag=0;

                if(mPassword=="" || mPassword.equals(""))
                {
                    validationFlag=1;
                    mEdtPass.setError("Please enter valid password");
                }
                if(mUserName=="" || mUserName.equals(""))
                {
                    validationFlag=1;
                    mEdtUserName.setError("Please enter username");
                }

                if(validationFlag==0)
                {
                    UserLogin();
                }

            }
        });

        mTxtForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForgetPasswordDialog();
            }
        });
        return view;
    }

    private void init(View view) {

        mVolleyRequest = Volley.newRequestQueue(getContext());
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
    private void UserLogin() {
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Validating User ...");
        dialog.setCancelable(false);
        dialog.show();

        HashMap<String,String> param = new HashMap();
        param.put(Constants.USERNAME,mUserName);
        param.put(Constants.PASSWORD,mPassword);

        String url= SupportFunctions.appendParam(MyApplication.URL_USER_LOGIN,param);

        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,url,null,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {

                        try
                        {
                            Log.e("Login Json_string",response.toString());
                            Gson gson = new Gson();
                            Login loginResponse=gson.fromJson(response.toString(), Login.class);
                            int res= Integer.parseInt(loginResponse.getResponse());

                            if(res==Constants.VALID_DATA) {
                                SharedPreferencesHelper.setLogin(true,getContext());
                                SharedPreferencesHelper.setUserId(loginResponse.getUser_id(),getContext());
                                SharedPreferencesHelper.setPhotographerId(loginResponse.getPhotographer_id(),getContext());
                                Intent intent = new Intent(getContext(), HomeActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                            else {
                                Toast.makeText(getContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
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
                        showNoConnectionDialog();
                        dialog.dismiss();

                    }
                }
        );
        mVolleyRequest.add(obreq);
    }

    private void ForgetPassword() {
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Validating User ...");
        dialog.setCancelable(false);
        dialog.show();

        HashMap<String,String> param = new HashMap();
        param.put(Constants.EMAIL,mForgetEmail);

        String url= SupportFunctions.appendParam(MyApplication.URL_FORGET_PASSWORD,param);

        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET,url,null,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {

                        try
                        {
                            Log.e("Forget Json_string",response.toString());
                            Gson gson = new Gson();
                            Login loginResponse=gson.fromJson(response.toString(), Login.class);
                            int res= Integer.parseInt(loginResponse.getResponse());

                            if(res==Constants.VALID_DATA) {
                                mForgetDialog.dismiss();

                                new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Done")
                                        .setContentText("Your password sent to your mail id")
                                        .setConfirmText("Ok")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                      /*  sDialog
                                .setTitleText("Discarded!")
                                .setContentText("Your imaginary file has been deleted!")
                                .setConfirmText("OK")
                                .setConfirmClickListener(null)
                                .showCancelButton(false)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);*/


                      sDialog.dismiss();
                                            }
                                        })

                                        .show();
                            }
                            else {
                                //Toast.makeText(getContext(), "User is not registered", Toast.LENGTH_SHORT).show();

                                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("Oops!")
                                        .setContentText("User is not registered")
                                        .setConfirmText("Ok")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                sDialog.dismiss();

                                            }
                                        })

                                        .show();
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
                UserLogin();
                mNoConnectionDialog.dismiss();
            }
        });
    }

    private void showForgetPasswordDialog() {
        mForgetDialog = new Dialog(getContext());
        //mForgetDialog.getWindow().setLayout(500,500);
        mForgetDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        mForgetDialog.setContentView(R.layout.dialog_forget_password);
        mForgetDialog.setCancelable(true);
        mForgetDialog.show();

        final MyEditText mEdtMail = mForgetDialog.findViewById(R.id.edt_email);
        MyButton mBtnSubmit = mForgetDialog.findViewById(R.id.btn_submit);

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                mForgetEmail=mEdtMail.getText().toString().trim();

                if(!mForgetEmail.matches(emailPattern)||mForgetEmail==null)
                {
                    mEdtMail.setError("Please enter valid email");
                }
                if(mForgetEmail!=null)
                {
                    ForgetPassword();
                }
                else {
                    mEdtMail.setError("Please enter your email id");
                }
            }
        });

    }
}
