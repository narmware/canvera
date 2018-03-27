package com.narmware.canvera.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Toast;

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
import com.narmware.canvera.pojo.GalleryItemResponse;
import com.narmware.canvera.support.customfonts.MyButton;
import com.narmware.canvera.support.customfonts.MyEditText;
import com.narmware.canvera.support.customfonts.MyTextView;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookAppointActivity extends AppCompatActivity {
    @BindView(R.id.edt_start_date)protected MyTextView mEdtStartDate;
    @BindView(R.id.edt_end_date)protected MyTextView mEdtEndDate;
    @BindView(R.id.edt_eve_type)protected MyEditText mEdtType;
    @BindView(R.id.edt_eve_desc)protected MyEditText mEdtDesc;
    @BindView(R.id.edt_eve_loc)protected MyEditText mEdtLoc;
    @BindView(R.id.btn_submit)protected MyButton mBtnSubmit;

    @BindView(R.id.btn_back)protected ImageButton mBtnBack;
    @BindView(R.id.title)protected MyTextView mTxtTitle;

    RequestQueue mVolleyRequest;

    int mStartDay,mStartYear,mStartMonth;
    int mEndDay,mEndYear,mEndMonth;
    String mType,mDesc,mLocation,mStartDate,mEndDate;
    //current date
    int day,month,year;
    int validFlag=0;
    int validDate=0;

    Dialog mNoConnectionDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appoint);
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
        mTxtTitle.setText(R.string.book_appoint_title);
        mVolleyRequest = Volley.newRequestQueue(this);

        mEdtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(0);
            }
        });

        mEdtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(1);
            }
        });

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mType=mEdtType.getText().toString().trim();
                mDesc=mEdtDesc.getText().toString().trim();
                mLocation=mEdtLoc.getText().toString().trim();
                mStartDate=mEdtStartDate.getText().toString().trim();
                mEndDate=mEdtEndDate.getText().toString().trim();

                validateEvent();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        Calendar c = Calendar.getInstance();
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year= c.get(Calendar.YEAR);



        if(id==0)
        {
            /*mStartYear=0;
            mStartMonth=0;
            mStartDay=0;
            mEndYear=0;
            mEndMonth=0;
            mEndDay=0;*/
            return new DatePickerDialog(this, StartdatePickerListener,year,month, day);
        }

        if(id==1)
        {
           /* mStartYear=0;
            mStartMonth=0;
            mStartDay=0;
            mEndYear=0;
            mEndMonth=0;
            mEndDay=0;*/

            return new DatePickerDialog(this, EnddatePickerListener,year,month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener StartdatePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            mStartYear=selectedYear;
            mStartMonth=selectedMonth;
            mStartDay=selectedDay;

            //Toast.makeText(BookAppointActivity.this,selectedDay + "/" + (selectedMonth + 1) + " / " + selectedYear,Toast.LENGTH_SHORT).show();
            ValidateDate();

            if(validDate==0)
                mEdtStartDate.setText(selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay);
        }
    };

    private DatePickerDialog.OnDateSetListener EnddatePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            mEndYear=selectedYear;
            mEndMonth=selectedMonth;
            mEndDay=selectedDay;

            //Toast.makeText(BookAppointActivity.this,selectedDay + "/" + (selectedMonth + 1) + " / " + selectedYear,Toast.LENGTH_SHORT).show();
            ValidateDate();

            if(validDate==0)
                mEdtEndDate.setText(selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay);
        }
    };

    public void validateEvent()
    {
        validFlag=0;


        if(mEndDate.equals(""))
        {
            validFlag=1;
            mEdtEndDate.setError("Enter end date");
        }
        if(mStartDate.equals(""))
        {
            validFlag=1;
            mEdtStartDate.setError("Enter start date");
        }
        if(mLocation.equals(""))
        {
            validFlag=1;
            mEdtLoc.setError("Enter Location");
        }
        if(mType.equals(""))
        {
            validFlag=1;
            mEdtType.setError("Enter Type");
        }

        if(validFlag==0)
        {
            Toast.makeText(this, "Valid", Toast.LENGTH_SHORT).show();
            if(getCurrentFocus()!=null) {

                setEvent();

                View view=getCurrentFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public void ValidateDate()
    {
        validDate=0;
        if(mEndYear!=0) {
            if (mStartYear > mEndYear) {
                validDate = 1;
                Toast.makeText(this, "Please select valid date", Toast.LENGTH_SHORT).show();
            }
        }

        if(mEndMonth!=0) {
            if (mStartMonth > mEndMonth) {
                validDate = 1;
                Toast.makeText(this, "Please select valid date", Toast.LENGTH_SHORT).show();
            }
        }

        if(mStartMonth == mEndMonth && mStartYear == mEndYear)
        {
            if(mStartDay > mEndDay)
            {
                validDate = 1;
                Toast.makeText(this, "Please select valid date", Toast.LENGTH_SHORT).show();
            }
        }

    }
    private void setEvent() {
      /*  final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("getting images ...");
        dialog.setCancelable(false);
        dialog.show();*/

        Appointment appointment=new Appointment();
        appointment.setEvent_type(mType);
        appointment.setEvent_desc(mDesc);
        appointment.setEvent_loc(mLocation);
        appointment.setStart_date(mStartDate);
        appointment.setEnd_date(mEndDate);
        appointment.setUser_id(SharedPreferencesHelper.getUserId(BookAppointActivity.this));
        appointment.setPhm_id("2");

        Gson gson = new Gson();
        String json_string=gson.toJson(appointment);
        Log.e("Appointment json",json_string);

        HashMap<String,String> param = new HashMap();
        param.put(Constants.JSON_STRING,json_string);

        String url= SupportFunctions.appendParam(MyApplication.URL_BOOK_APPOINTMENT,param);

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
        mNoConnectionDialog = new Dialog(BookAppointActivity.this, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
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
                setEvent();
                mNoConnectionDialog.dismiss();
            }
        });
    }
}
