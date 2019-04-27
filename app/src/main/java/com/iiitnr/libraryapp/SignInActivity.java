package com.iiitnr.libraryapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        FirebaseApp.initializeApp(getApplicationContext());
        editID = (TextInputLayout) findViewById(R.id.editID);
        editPass = (TextInputLayout) findViewById(R.id.editPass);
        firebaseAuth=FirebaseAuth.getInstance();
        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        toSignUp = (TextView) findViewById(R.id.toSignUp);
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        db=FirebaseFirestore.getInstance();

       if(firebaseAuth.getCurrentUser()!=null) {
           progressDialog.setMessage("Please Wait... Signing You in !");
           progressDialog.show();
           String cur = firebaseAuth.getCurrentUser().getEmail().trim();
           db.document("User/"+cur).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
               @Override
               public void onSuccess(DocumentSnapshot documentSnapshot) {
                   User obj=documentSnapshot.toObject(User.class);
                   if(obj.getType()==0)
                   {
                       progressDialog.cancel();
                       startActivity(new Intent(getApplicationContext(),UserHome.class));
                       finish();

                   }
                   else
                   {
                       progressDialog.cancel();
                       startActivity(new Intent(getApplicationContext(),AdminHome.class));
                       finish();

                   }

               }
           }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                   progressDialog.cancel();
                   Toast.makeText(SignInActivity.this, "Please Sign in Again", Toast.LENGTH_SHORT).show();
               }
           });

        }
        buttonSignIn.setOnClickListener(this);
        toSignUp.setOnClickListener(this);
    }
  private FirebaseFirestore db;
    private TextInputLayout editID;
    private TextInputLayout editPass;
    private Button buttonSignIn;
    private TextView toSignUp;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private boolean verifyEmailId()
    {
        String emailId=editID.getEditText().getText().toString().trim();
        if(emailId.isEmpty())
        {   editID.setErrorEnabled(true);
            editID.setError("Email ID Required");
            return true;
        }
        else
        {
            editID.setErrorEnabled(false);
            return false;
        }
    }


    private boolean verifyPass()
    {
        String pass=editPass.getEditText().getText().toString().trim();
        if(pass.isEmpty())
        {   editPass.setErrorEnabled(true);
            editPass.setError("Password Required");
            return true;
        }
        else
        {
            editPass.setErrorEnabled(false);
            return false;
        }
    }

    private void signinUser() {

        boolean res= (verifyEmailId()|verifyPass());
        if(res==true)
            return;
        String id=editID.getEditText().getText().toString().trim()+"@iiitnr.edu.in";
        String pass=editPass.getEditText().getText().toString().trim();
        progressDialog.setMessage("Signing In ... ");
       progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(id,pass).addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    String id=editID.getEditText().getText().toString().trim()+"@iiitnr.edu.in";
                    db.collection("User").whereEqualTo("email",id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            User obj=new User();
                            for(QueryDocumentSnapshot doc:queryDocumentSnapshots)
                               obj=doc.toObject(User.class);


                            db.document("User/"+firebaseAuth.getCurrentUser().getEmail()).update("fcmToken",SharedPref.getInstance(getApplicationContext()).getToken())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(SignInActivity.this, "Registered for Notifications Successfully !", Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {
                                                Toast.makeText(SignInActivity.this, "Registration for Notifications Failed !\nPlease Sign in Again to Retry", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });

                            if(obj.getType()==0)
                            {
                                progressDialog.cancel();
                                Toast.makeText(SignInActivity.this,"Signed in !",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),UserHome.class));
                                finish();

                            }
                            else
                            {
                                progressDialog.cancel();
                                Toast.makeText(SignInActivity.this,"Signed in !",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),AdminHome.class));
                                finish();

                            }



                        }
                    });
                }

                else
                {   progressDialog.cancel();
                    Toast.makeText(SignInActivity.this,"Wrong Credentials or Bad Connection ! Try Again ",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public void onClick(View v) {

        if (v == buttonSignIn)
            signinUser();

        else if (v == toSignUp) {
            startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            finish();

        }
    }
}

