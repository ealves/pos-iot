package com.example.atividade2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ButtonsFragment.OnFragmentInteractionListener{

    private BackgroundFragment backgroundFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backgroundFragment = (BackgroundFragment) getSupportFragmentManager().findFragmentById(R.id.fragment2);
    }

    @Override
    public void onFragmentInteraction(int color) {
        backgroundFragment.setBackground(color);
    }
}