package com.narmware.patima.activity;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.narmware.patima.R;
import com.narmware.patima.fragment.LoginFragment;
import com.narmware.patima.fragment.RegisterFragment;
import com.narmware.patima.fragment.SignInFragment;

import butterknife.ButterKnife;


public class LoginActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener, SignInFragment.OnFragmentInteractionListener,RegisterFragment.OnFragmentInteractionListener{
    protected FragmentManager mFragmentManager;

    private void init() {
        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.login_fragment_container, new LoginFragment());
        transaction.commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
