package com.iiitnr.libraryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class AdminHome extends AppCompatActivity implements View.OnClickListener {


    private Button searchBook,addBook,removeBook,updateBook,issueBook,returnBook,logOut;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        FirebaseApp.initializeApp(this);
        firebaseAuth=FirebaseAuth.getInstance();
        searchBook=(Button)findViewById(R.id.searchBook);
        addBook=(Button)findViewById(R.id.addBook);
        removeBook=(Button)findViewById(R.id.removeBook);
        updateBook=(Button)findViewById(R.id.updateBook);
        issueBook=(Button)findViewById(R.id.issueBook);
        returnBook=(Button)findViewById(R.id.returnBook);
        logOut=(Button)findViewById(R.id.logOut);

        searchBook.setOnClickListener(this);
        addBook.setOnClickListener(this);
        removeBook.setOnClickListener(this);
        updateBook.setOnClickListener(this);
        issueBook.setOnClickListener(this);
        returnBook.setOnClickListener(this);
        logOut.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        if(v==logOut)
        {
            firebaseAuth.signOut();
            startActivity(new Intent(getApplicationContext(),SignInActivity.class));
            finish();
        }

        if(v==searchBook)
        {
            Toast.makeText(this, "Work in progress", Toast.LENGTH_SHORT).show();
        }
        if(v==addBook)
        {
            startActivity(new Intent(getApplicationContext(),AdminAddBook.class));
        }
        if(v==removeBook)
        {
            startActivity(new Intent(getApplicationContext(),AdminRemoveBook.class));
        }
        if(v==issueBook)
        {
            startActivity(new Intent(getApplicationContext(),AdminIssueBook.class));
        }
        if(v==returnBook)
        {
            startActivity(new Intent(getApplicationContext(),AdminReturnBook.class));
        }

        if(v==updateBook)
        {
            startActivity(new Intent(getApplicationContext(),AdminUpdateBook.class));
        }


    }

}
