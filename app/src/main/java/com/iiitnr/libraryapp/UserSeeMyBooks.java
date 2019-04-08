package com.iiitnr.libraryapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
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

public class UserSeeMyBooks extends AppCompatActivity {


    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    private TextView editMyBooks;
    private User U = new User();
    private String S = " ";
    private int flag;
    private Book B = new Book();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_see_my_books);
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        editMyBooks = (TextView) findViewById(R.id.editMyBooks);

        showBook();
    }

    private void showBook()
    {
        db.document("User/" + firebaseAuth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    U = task.getResult().toObject(User.class);

                    if (!U.getBook().isEmpty()) {
                        List<Integer> l = new ArrayList<Integer>();
                        l = U.getBook();

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
                        Date d = new Date();
                        for (int i = 0; i < l.size(); i++) {

                            db.collection("Book").whereEqualTo("id",l.get(i)/100).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful())
                                    {
                                        Book b=new Book();
                                        for(QueryDocumentSnapshot doc:task.getResult())
                                            B = doc.toObject(Book.class);

                                    }
                                    else
                                    {
                                        flag=1;
                                    }
                                }
                            });

                            S+="\n\nBook ID :"+l.get(i);
                            S += "\nTitle : " + B.getTitle() + "\nCategory : " + B.getType();
                            d=U.getDate().get(i).toDate();
                            S+="\nIssue Date : "+simpleDateFormat.format(d);
                            c=Calendar.getInstance();
                            c.add(Calendar.DAY_OF_MONTH,14);
                            d=c.getTime();
                            S+="\nReturn Date : "+simpleDateFormat.format(d);
                            S+="\nFine : "+U.getFine().get(i);
                        }
                        if (flag == 1) {
                            editMyBooks.setText("BAD CONNECTION");
                        } else {
                            editMyBooks.setText(S);

                        }
                    }
                } else {
                    editMyBooks.setText("BAD CONNECTION");
                }
            }
        });
    }


}