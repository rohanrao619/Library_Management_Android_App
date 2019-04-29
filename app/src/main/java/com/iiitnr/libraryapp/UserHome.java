package com.iiitnr.libraryapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

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
        buttonReissue=(Button)findViewById(R.id.buttonReissue);

        db=FirebaseFirestore.getInstance();
        searchBook1.setOnClickListener(this);
        seeBook.setOnClickListener(this);
        logOut1.setOnClickListener(this);
        buttonReissue.setOnClickListener(this);
    }


    private TextView title1;
    private Button searchBook1,seeBook,logOut1,buttonReissue;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;


    @Override
    public void onClick(View v) {
        if(v==logOut1)
        {
            db.document("User/"+firebaseAuth.getCurrentUser().getEmail()).update("fcmToken",null).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {

                        firebaseAuth.signOut();
                        startActivity(new Intent(getApplicationContext(),SignInActivity.class));
                        finish();

                    }
                    else
                    {
                        Toast.makeText(UserHome.this, "Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if(v==searchBook1)
        {
            startActivity(new Intent(getApplicationContext(),SearchBookSet.class));
        }

        if(v==seeBook)
        {
            startActivity(new Intent(getApplicationContext(),UserSeeMyBooks.class));
        }

        if(v==buttonReissue)
        {
            startActivity(new Intent(getApplicationContext(),UserReissueBook.class));
        }
    }
}
