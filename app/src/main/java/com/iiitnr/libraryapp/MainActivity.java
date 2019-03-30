package com.iiitnr.libraryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button buttonSignOut;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonSignOut=(Button)findViewById(R.id.buttonSignOut);
        firebaseAuth=FirebaseAuth.getInstance();
        buttonSignOut.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

     if(v==buttonSignOut)
     {
         firebaseAuth.signOut();
         startActivity(new Intent(getApplicationContext(),SignInActivity.class));
         finish();
     }
    }
}
