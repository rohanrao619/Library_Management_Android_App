package com.iiitnr.libraryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class UserHome extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        FirebaseApp.initializeApp(this);
        firebaseAuth=FirebaseAuth.getInstance();
        title1=(TextView)findViewById(R.id.title1);
        searchBook1=(Button)findViewById(R.id.searchBook1);
        seeBook=(Button)findViewById(R.id.seeBook);
        logOut1=(Button)findViewById(R.id.logOut1);

        searchBook1.setOnClickListener(this);
        seeBook.setOnClickListener(this);
        logOut1.setOnClickListener(this);
    }


    private TextView title1;
    private Button searchBook1,seeBook,logOut1;
    private FirebaseAuth firebaseAuth;


    @Override
    public void onClick(View v) {
        if(v==logOut1)
        {
            firebaseAuth.signOut();
            startActivity(new Intent(getApplicationContext(),SignInActivity.class));
            finish();
        }

        if(v==searchBook1)
        {
            Toast.makeText(this, "Work in progress", Toast.LENGTH_SHORT).show();
        }

        if(v==seeBook)
        {
            Toast.makeText(this, "Work in progress", Toast.LENGTH_SHORT).show();
        }
    }
}
