package com.narmware.canvera.activity;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.narmware.canvera.R;
import com.narmware.canvera.fragment.HomeFragment;
import com.narmware.canvera.fragment.LoginFragment;
import com.narmware.canvera.fragment.MyPhotoBookFragment;
import com.narmware.canvera.fragment.SharedPhotobookFragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,HomeFragment.OnFragmentInteractionListener,LoginFragment.OnFragmentInteractionListener,MyPhotoBookFragment.OnFragmentInteractionListener,SharedPhotobookFragment.OnFragmentInteractionListener{

    Button mBtnExplore,mBtnPhotobook;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        init();
        setFragment(new LoginFragment());
    }

    private void init() {
        mBtnExplore=findViewById(R.id.btn_explore);
        mBtnPhotobook=findViewById(R.id.btn_photobook);

        mBtnPhotobook.setOnClickListener(this);
        mBtnExplore.setOnClickListener(this);
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
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_explore:
                mBtnExplore.setBackground(getResources().getDrawable(R.drawable.button_bg_selected));
                mBtnPhotobook.setBackground(getResources().getDrawable(R.drawable.button_bg));
                setFragment(new LoginFragment());
                break;

            case R.id.btn_photobook:
                mBtnExplore.setBackground(getResources().getDrawable(R.drawable.button_bg));
                mBtnPhotobook.setBackground(getResources().getDrawable(R.drawable.button_bg_selected));
                setFragment(new HomeFragment());
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
