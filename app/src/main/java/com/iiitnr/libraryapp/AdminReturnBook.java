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

public class AdminReturnBook extends AppCompatActivity {



    private Button returnButton;
    private TextInputLayout editCardNo2, editBid4;
    private FirebaseFirestore db;
    private ProgressDialog p;
    private boolean res1, res2;
    private User U = new User();
    private Book B = new Book();

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_return_book);
        FirebaseApp.initializeApp(this);
        returnButton = (Button) findViewById(R.id.returnButton);
        editBid4 = (TextInputLayout) findViewById(R.id.editBid4);
        editCardNo2 = (TextInputLayout) findViewById(R.id.editCardNo2);
        db = FirebaseFirestore.getInstance();
        p = new ProgressDialog(this);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnBook();
            }
        });
    }

    private boolean verifyCard() {
        String t = editCardNo2.getEditText().getText().toString().trim();
        if (t.isEmpty()) {
            editCardNo2.setErrorEnabled(true);
            editCardNo2.setError("Card No. Required");
            return true;
        } else {
            editCardNo2.setErrorEnabled(false);
            return false;
        }
    }


    private boolean verifyBid() {
        String t = editBid4.getEditText().getText().toString().trim();
        if (t.isEmpty()) {
            editBid4.setErrorEnabled(true);
            editBid4.setError("Book Id Required");
            return true;
        } else {
            editCardNo2.setErrorEnabled(false);
            return false;
        }
    }


    private boolean getUser() {
        db.collection("User").whereEqualTo("card", Integer.parseInt(editCardNo2.getEditText().getText().toString().trim())).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                        Toast.makeText(AdminReturnBook.this, "No Such User !", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    res1 = false;
                    p.cancel();
                    Toast.makeText(AdminReturnBook.this, "Try Again !", Toast.LENGTH_SHORT).show();
                }


            }
        });

        return res1;
    }

    private boolean getBook() {
        db.document("Book/" + Integer.parseInt(editBid4.getEditText().getText().toString().trim()) / 100).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        res2 = true;
                        B = task.getResult().toObject(Book.class);
                    } else {
                        res2 = false;
                        p.cancel();
                        Toast.makeText(AdminReturnBook.this, "No Such Book !", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    res2 = false;
                    p.cancel();
                    Toast.makeText(AdminReturnBook.this, "Try Again !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return res2;
    }
    
    
    private void returnBook()
    {

        if (verifyBid() | verifyCard())
            return;

        p.setMessage("Please Wait !");
        p.show();
        if (getUser()&getBook())
        {


            if(!U.getBook().contains(Integer.parseInt(editBid4.getEditText().getText().toString().trim())))
            {
                p.cancel();
                Toast.makeText(AdminReturnBook.this, "Given Book is not issued to the User !", Toast.LENGTH_SHORT).show();
                return;
            }

            List<Integer> l = new ArrayList<Integer>();
            l = U.getBook();
            int index=l.indexOf(Integer.parseInt(editBid4.getEditText().getText().toString().trim()));
            l.remove(index);
            U.setBook(l);

            l = U.getFine();
            U.setLeft_fine(U.getLeft_fine()+l.get(index));
            l.remove(index);
            U.setFine(l);

            l = U.getRe();
            l.remove(index);
            U.setRe(l);

            List<Timestamp> l1 = new ArrayList<>();
            l1 = U.getDate();
            l1.remove(index);
            U.setDate(l1);

            db.document("User/" + U.getEmail()).set(U).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                        int i;
                        B.setAvailable(B.getAvailable()+1);
                        List<Integer> l1=new ArrayList<>();
                        l1=B.getUnit();
                        i=l1.indexOf(Integer.parseInt(editBid4.getEditText().getText().toString().trim()) % 100);
                        l1.remove(i);
                        B.setUnit(l1);

                        db.document("Book/" + B.getId()).set(B).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    p.cancel();
                                    Toast.makeText(AdminReturnBook.this, "Book Returned Successfully !", Toast.LENGTH_SHORT).show();

                                } else {
                                    p.cancel();
                                    Toast.makeText(AdminReturnBook.this, "Try Again !", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        p.cancel();
                        Toast.makeText(AdminReturnBook.this, "Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }


    }

}
