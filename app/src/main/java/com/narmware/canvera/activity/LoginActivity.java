package com.narmware.canvera.activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.narmware.canvera.R;
import com.narmware.canvera.databinding.ActivityLoginBinding;
import com.narmware.canvera.fragment.LoginFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener {
    protected ActivityLoginBinding mBinding;
    protected FragmentManager mFragmentManager;
    @BindView(R.id.login_fragment_container) protected FrameLayout mFragmentContainer;

    private void init() {
        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        ButterKnife.bind(this);
        init();

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.login_fragment_container, new LoginFragment());
        transaction.commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        
    }
}
