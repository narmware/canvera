package com.narmware.patima.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.narmware.patima.R;
import com.narmware.patima.adapter.NavigationListAdapter;
import com.narmware.patima.fragment.ExploreFragment;
import com.narmware.patima.fragment.HomeFragment;
import com.narmware.patima.fragment.LoginFragment;
import com.narmware.patima.fragment.MyPhotoBookFragment;
import com.narmware.patima.fragment.SharedPhotobookFragment;
import com.narmware.patima.helpers.Constants;
import com.narmware.patima.pojo.NavItem;
import com.narmware.patima.support.customfonts.MyButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnFragmentInteractionListener, ExploreFragment.OnFragmentInteractionListener, LoginFragment.OnFragmentInteractionListener, MyPhotoBookFragment.OnFragmentInteractionListener, SharedPhotobookFragment.OnFragmentInteractionListener {

    @BindView(R.id.btn_explore)
    protected Button mBtnExplore;
    @BindView(R.id.btn_photobook)
    protected Button mBtnPhotobook;
    @BindView(R.id.nav_list)
    protected ListView mListNav;
    @BindView(R.id.btn_enq)
    protected MyButton mBtnEnq;
    @BindView(R.id.btn_call)
    protected MyButton mBtnCall;

    NavigationListAdapter mNavAdapter;
    ArrayList<NavItem> navItems;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    RequestQueue mVolleyRequest;
    String mUrl;
    public static DrawerLayout drawer;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        init();
        setNavList();
        setFragment(new ExploreFragment());
    }

    private void setNavList() {
        navItems = new ArrayList<>();
        navItems.add(new NavItem(R.drawable.ic_photo, Constants.NAV_TOP_IMG));
        navItems.add(new NavItem(R.drawable.ic_video, Constants.NAV_TOP_VID));
        navItems.add(new NavItem(R.drawable.ic_book, Constants.NAV_BOOK_APNT));
        navItems.add(new NavItem(R.drawable.ic_contact, Constants.NAV_CONTACT));
        navItems.add(new NavItem(R.drawable.ic_info, Constants.NAV_ABOUT));

        mNavAdapter = new NavigationListAdapter(navItems, HomeActivity.this);
        mListNav.setAdapter(mNavAdapter);

        mBtnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(HomeActivity.this, "Call", Toast.LENGTH_SHORT).show();

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:123456789"));
                if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);
            }
        });

        mBtnEnq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Enquiry", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @OnClick(R.id.btn_explore)
    protected void buttonExploreAction() {
        mBtnExplore.setBackground(getResources().getDrawable(R.drawable.button_bg_selected));
       mBtnPhotobook.setBackground(getResources().getDrawable(R.drawable.button_bg));

        setFragment(new ExploreFragment());
    }
    @OnClick(R.id.btn_photobook)
    protected void buttonPhotoBookAction() {
        mBtnExplore.setBackground(getResources().getDrawable(R.drawable.button_bg));
        mBtnPhotobook.setBackground(getResources().getDrawable(R.drawable.button_bg_selected));

        setFragment(new HomeFragment());
    }
    private void init() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        buttonPhotoBookAction();
        buttonExploreAction();

        mVolleyRequest = Volley.newRequestQueue(this);

    }

    public void setFragment(Fragment fragment)
    {
        mFragmentManager=getSupportFragmentManager();
        mFragmentTransaction=mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container,fragment);
        mFragmentTransaction.commit();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press back again to exit app", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void getDetails() {
        final ProgressDialog dialog = new ProgressDialog(this);
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

}
