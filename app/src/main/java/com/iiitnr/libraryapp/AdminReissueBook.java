package com.iiitnr.libraryapp;

import android.app.ProgressDialog;
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
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AdminReissueBook extends AppCompatActivity {


    private Button reissueButton;
    private TextInputLayout editCardNo5, editBid5;
    private FirebaseFirestore db;
    private ProgressDialog p;
    private boolean res1;
    private User U = new User();

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_reissue_book);
        FirebaseApp.initializeApp(this);
        reissueButton = (Button) findViewById(R.id.reissueButton);
        editBid5 = (TextInputLayout) findViewById(R.id.editBid5);
        editCardNo5 = (TextInputLayout) findViewById(R.id.editCardNo5);
        db = FirebaseFirestore.getInstance();
        p = new ProgressDialog(this);
        p.setMessage("Please Wait !");
        reissueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reissueBook();
            }
        });
    }

    private boolean verifyCard() {
        String t = editCardNo5.getEditText().getText().toString().trim();
        if (t.isEmpty()) {
            editCardNo5.setErrorEnabled(true);
            editCardNo5.setError("Card No. Required");
            return true;
        } else {
            editCardNo5.setErrorEnabled(false);
            return false;
        }
    }


    private boolean verifyBid() {
        String t = editBid5.getEditText().getText().toString().trim();
        if (t.isEmpty()) {
            editBid5.setErrorEnabled(true);
            editBid5.setError("Book Id Required");
            return true;
        } else {
            editBid5.setErrorEnabled(false);
            return false;
        }
    }

    private boolean getUser() {

        db.collection("User").whereEqualTo("card", Integer.parseInt(editCardNo5.getEditText().getText().toString().trim())).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()&&task.getResult().size()==1) {
                        res1 = true;
                        for (QueryDocumentSnapshot doc : task.getResult())
                            U = doc.toObject(User.class);
                        return;
                    }
                else if(task.isSuccessful()&&task.getResult().size()!=1) {

                        res1 = false;
                        p.cancel();
                        Toast.makeText(AdminReissueBook.this, "No Such User !", Toast.LENGTH_SHORT).show();
                    }
                 else {
                    res1 = false;
                    p.cancel();
                    Toast.makeText(AdminReissueBook.this, "Try Again !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return res1;
    }


    private void reissueBook() {
        if (verifyBid() | verifyCard()) {
            return;
        }
        p.show();
        if (!getUser())
        {
            return;
        }


            if (!U.getBook().contains(Integer.parseInt(editBid5.getEditText().getText().toString().trim()))) {
                p.cancel();
                Toast.makeText(AdminReissueBook.this, "This Book is not issued to this User !", Toast.LENGTH_SHORT).show();
                return;
            }

            List<Integer> l = new ArrayList<Integer>();

            int i = U.getBook().indexOf(Integer.parseInt(editBid5.getEditText().getText().toString().trim()));

            U.setLeft_fine(U.getLeft_fine() + U.getFine().get(i));
            l = U.getFine();
            l.set(i, 0);
            U.setFine(l);

            l = U.getRe();
            l.set(i, 1);
            U.setRe(l);

            List<Timestamp> l1 = new ArrayList<>();
            l1 = U.getDate();
            Calendar c = new Calendar() {
                @Override
                protected void computeTime() {

                }

                @Override
                protected void computeFields() {

                }

                @Override
                public void add(int field, int amount) {

                }

                @Override
                public void roll(int field, boolean up) {

                }

                @Override
                public int getMinimum(int field) {
                    return 0;
                }

                @Override
                public int getMaximum(int field) {
                    return 0;
                }

                @Override
                public int getGreatestMinimum(int field) {
                    return 0;
                }

                @Override
                public int getLeastMaximum(int field) {
                    return 0;
                }
            };
            c = Calendar.getInstance();
            Date d = c.getTime();
            Timestamp t = new Timestamp(d);
            l1.set(i, t);
            U.setDate(l1);


            db.document("User/" + U.getEmail()).set(U).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful())
                    {
                        p.cancel();
                        Toast.makeText(AdminReissueBook.this, "Re-Issued Successfully !", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        p.cancel();
                        Toast.makeText(AdminReissueBook.this, "Please try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
