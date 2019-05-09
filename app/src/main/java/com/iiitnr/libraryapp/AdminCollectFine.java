package com.iiitnr.libraryapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminCollectFine extends AppCompatActivity {




    private TextInputLayout editUser;
    private Button collect;
    private ProgressDialog progressDialog;
    private FirebaseFirestore db;
    private User U;

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }

    private void collectFine()
    {
        int tot=U.getLeft_fine();
        for(int i=0;i<U.getFine().size();i++)
        {
            tot+=U.getFine().get(i);
        }

        if(tot==0)
        {
            Toast.makeText(this, "This User has no Fine !", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            AlertDialog.Builder alert=new AlertDialog.Builder(this);
            alert.setCancelable(false).setTitle("Collect Fine !").setMessage("Collect Rs."+tot+" from "+U.getName()).setPositiveButton("Collect", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    progressDialog.show();
                    List<Integer> list=new ArrayList();
                    list=U.getFine();
                    for(int i=0;i<list.size();i++)
                    {
                        list.set(i,0);
                    }
                    U.setFine(list);
                    U.setLeft_fine(0);
                    db.document("User/"+U.getEmail()).set(U).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful())
                            {
                                Toast.makeText(AdminCollectFine.this, "Fine Collected !", Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                                return;
                            }
                            else
                            {
                                Toast.makeText(AdminCollectFine.this, "Try Again !", Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                                return;

                            }
                        }

                    });


                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();
                    return;
                }
            });

            AlertDialog alertDialog=alert.create();
            alertDialog.show();

        }

    }



    private boolean verifyUser() {
        String t = editUser.getEditText().getText().toString().trim();
        if (t.isEmpty()) {
            editUser.setErrorEnabled(true);
            editUser.setError("Card No. Required");
            return true;
        } else {
            editUser.setErrorEnabled(false);
            return false;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_collect_fine);
        editUser=(TextInputLayout)findViewById(R.id.editUser);
        collect=(Button)findViewById(R.id.collect);

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        FirebaseApp.initializeApp(this);
        db=FirebaseFirestore.getInstance();
        progressDialog.setMessage("Please Wait !");

        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();

                if(verifyUser())
                    return;

                int card=Integer.parseInt(editUser.getEditText().getText().toString().trim());
                db.collection("User").whereEqualTo("card",card).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful())

                        {

                            if(!task.getResult().isEmpty()) {
                                for (QueryDocumentSnapshot x : task.getResult())
                                    U = x.toObject(User.class);
                                progressDialog.cancel();
                                collectFine();
                            }
                            else
                            {
                                progressDialog.cancel();
                                Toast.makeText(AdminCollectFine.this, "No Such User !", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            progressDialog.cancel();
                            Toast.makeText(AdminCollectFine.this, "Try Again !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


    }
}
