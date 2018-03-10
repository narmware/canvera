package com.narmware.canvera.activity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
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
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.narmware.canvera.R;
import com.narmware.canvera.fragment.ExploreFragment;
import com.narmware.canvera.fragment.HomeFragment;
import com.narmware.canvera.fragment.LoginFragment;
import com.narmware.canvera.fragment.MyPhotoBookFragment;
import com.narmware.canvera.fragment.SharedPhotobookFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,HomeFragment.OnFragmentInteractionListener,ExploreFragment.OnFragmentInteractionListener,LoginFragment.OnFragmentInteractionListener,MyPhotoBookFragment.OnFragmentInteractionListener,SharedPhotobookFragment.OnFragmentInteractionListener{

    @BindView(R.id.btn_explore) protected Button mBtnExplore;
    @BindView(R.id.btn_photobook)protected Button  mBtnPhotobook;

    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    RequestQueue mVolleyRequest;
    String mUrl;

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

        setFragment(new ExploreFragment());
        init();
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
            super.onBackPressed();
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
