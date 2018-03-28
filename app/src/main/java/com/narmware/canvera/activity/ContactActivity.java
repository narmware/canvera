package com.narmware.canvera.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
import com.narmware.canvera.helpers.Constants;
import com.narmware.canvera.helpers.SharedPreferencesHelper;
import com.narmware.canvera.helpers.SupportFunctions;
import com.narmware.canvera.pojo.Appointment;
import com.narmware.canvera.pojo.Feedback;
import com.narmware.canvera.support.customfonts.MyButton;
import com.narmware.canvera.support.customfonts.MyEditText;
import com.narmware.canvera.support.customfonts.MyTextView;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactActivity extends AppCompatActivity {

    @BindView(R.id.btn_submit)protected MyButton mBtnSubmit;

    @BindView(R.id.btn_back)protected ImageButton mBtnBack;
    @BindView(R.id.title)protected MyTextView mTxtTitle;
    @BindView(R.id.edt_feed_name)protected MyEditText mEdtFeedName;
    @BindView(R.id.edt_feed_email)protected MyEditText mEdtFeedEmail;
    @BindView(R.id.edt_feedback)protected MyEditText mEdtFeedback;

    String mName,mEmail,mFeed;
    RequestQueue mVolleyRequest;

    int validFlag=0;

    Dialog mNoConnectionDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        getSupportActionBar().hide();

        //used to hide keyboard bydefault
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        init();
        //used to close open keyboard
       /* if(getCurrentFocus()!=null) {


            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        }*/
    }

    private void init() {
        ButterKnife.bind(this);
        mTxtTitle.setText(R.string.contact_us);
        mVolleyRequest = Volley.newRequestQueue(this);

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateFeedback();
            }
        });
    }

    public void ValidateFeedback()
    {
        validFlag=0;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        mName=mEdtFeedName.getText().toString().trim();
        mEmail=mEdtFeedEmail.getText().toString().trim();
        mFeed=mEdtFeedback.getText().toString().trim();

        if(mName.equals(""))
        {
            validFlag=1;
            mEdtFeedName.setError("Please enter name");
        }
        if(mEmail.equals("") || !mEmail.matches(emailPattern))
        {
            validFlag=1;
            mEdtFeedEmail.setError("Please enter valid email");

        }
        if(mFeed.equals(""))
        {
            validFlag=1;
            mEdtFeedback.setError("Please enter feedback");
        }

        if(validFlag==0)
        {
            setFeedback();
        }
    }
    private void setFeedback() {
      /*  final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("getting images ...");
        dialog.setCancelable(false);
        dialog.show();*/

        Feedback feedback=new Feedback();
        feedback.setFeed_name(mName);
        feedback.setFeed_desc(mFeed);
        feedback.setFeed_email(mEmail);
        feedback.setUser_id(SharedPreferencesHelper.getUserId(ContactActivity.this));
        feedback.setPhm_id(SharedPreferencesHelper.getPhotographerId(ContactActivity.this));

        Gson gson = new Gson();
        String json_string=gson.toJson(feedback);
        Log.e("Feedback json",json_string);

        HashMap<String,String> param = new HashMap();
        param.put(Constants.JSON_STRING,json_string);

        String url= SupportFunctions.appendParam(MyApplication.URL_FEEDBACK,param);

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
        mNoConnectionDialog = new Dialog(ContactActivity.this, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
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
                setFeedback();
                mNoConnectionDialog.dismiss();
            }
        });
    }
}
