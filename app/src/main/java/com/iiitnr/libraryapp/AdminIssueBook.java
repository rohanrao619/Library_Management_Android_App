package com.iiitnr.libraryapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AdminIssueBook extends AppCompatActivity {


    private TextInputLayout editCardNo1, editBid3;
    private FirebaseFirestore db;
    private ProgressDialog p;
    private boolean res1, res2;
    private User U = new User();
    private Book B1 = new Book();

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_issue_book);
        FirebaseApp.initializeApp(this);
        Button issueButton = (Button) findViewById(R.id.issueButton);
        editBid3 = (TextInputLayout) findViewById(R.id.editBid3);
        editCardNo1 = (TextInputLayout) findViewById(R.id.editCardNo1);
        db=FirebaseFirestore.getInstance();
        p = new ProgressDialog(this);
        issueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                issueBook();
            }
        });
    }


    private boolean verifyCard() {
        String t = editCardNo1.getEditText().getText().toString().trim();
        if (t.isEmpty()) {
            editCardNo1.setErrorEnabled(true);
            editCardNo1.setError("Card No. Required");
            return true;
        } else {
            editCardNo1.setErrorEnabled(false);
            return false;
        }
    }


    private boolean verifyBid() {
        String t = editBid3.getEditText().getText().toString().trim();
        if (t.isEmpty()) {
            editBid3.setErrorEnabled(true);
            editBid3.setError("Book Id Required");
            return true;
        } else {
            editBid3.setErrorEnabled(false);
            return false;
        }
    }

    private boolean getUser() {
        db.collection("User").whereEqualTo("card", Integer.parseInt(editCardNo1.getEditText().getText().toString().trim())).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    if (task.getResult().size() == 1) {
                        res1 = true;
                        for (QueryDocumentSnapshot doc : task.getResult())
                            U = doc.toObject(User.class);
                    } else {

                        res1 = false;
                        p.cancel();
                        Toast.makeText(AdminIssueBook.this, "No Such User !", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    res1 = false;
                    p.cancel();
                    Toast.makeText(AdminIssueBook.this, "Try Again !", Toast.LENGTH_SHORT).show();
                }


            }
        });

        return res1;
    }

    private boolean getBook() {

        db.document("Book/" + Integer.parseInt(editBid3.getEditText().getText().toString().trim()) / 100).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        res2 = true;
                        B1 = task.getResult().toObject(Book.class);
                    } else {
                        res2 = false;
                        p.cancel();
                        Toast.makeText(AdminIssueBook.this, "No Such Book !", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    res2 = false;
                    p.cancel();
                    Toast.makeText(AdminIssueBook.this, "Try Again !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return res2;
    }

    private void issueBook() {

        Log.d("abc","invoked");
        if (verifyBid() | verifyCard()) {
            return;
        }
        p.setMessage("Please Wait !");
        p.show();
        if (getBook()&getUser())
        {

            if (U.getBook().size() >= 5) {
                p.cancel();
                Toast.makeText(AdminIssueBook.this, "User Already Has 5 books issued !", Toast.LENGTH_SHORT).show();
                return;
            }
            if (B1.getAvailable() == 0) {
                p.cancel();
                Toast.makeText(AdminIssueBook.this, "No Units of this Book Available !", Toast.LENGTH_SHORT).show();
                return;
            }
            if (B1.getUnit().contains(Integer.parseInt(editBid3.getEditText().getText().toString().trim()) % 100)) {
                p.cancel();
                Toast.makeText(AdminIssueBook.this, "This Unit is Already Issued !", Toast.LENGTH_SHORT).show();
                return;
            }
            List<Integer> l = new ArrayList<Integer>();
            l = U.getBook();
            l.add(Integer.parseInt(editBid3.getEditText().getText().toString().trim()));
            U.setBook(l);
            l = U.getFine();
            l.add(0);
            U.setFine(l);
            l = U.getRe();
            l.add(1);
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
            c=Calendar.getInstance();
            Date d = c.getTime();
            Timestamp t = new Timestamp(d);
            l1.add(t);
            U.setDate(l1);
            db.document("User/" +U.getEmail()).set(U).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                        B1.setAvailable(B1.getAvailable()-1);
                        List<Integer> l1=new ArrayList<>();
                        l1=B1.getUnit();
                        l1.add(Integer.parseInt(editBid3.getEditText().getText().toString().trim()) % 100);
                        B1.setUnit(l1);

                        db.document("Book/" + B1.getId()).set(B1).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    p.cancel();
                                    Toast.makeText(AdminIssueBook.this, "Book Issued Successfully !", Toast.LENGTH_SHORT).show();

                                } else {
                                    p.cancel();
                                    Toast.makeText(AdminIssueBook.this, "Try Again !", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        p.cancel();
                        Toast.makeText(AdminIssueBook.this, "Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}
