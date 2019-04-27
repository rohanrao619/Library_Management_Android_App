package com.iiitnr.libraryapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    private void setBook(int i)
    {

        db.document("Book/"+U.getBook().get(i)/100).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                B=task.getResult().toObject(Book.class);
                Toast.makeText(UserSeeMyBooks.this, ""+B.getTitle(), Toast.LENGTH_SHORT).show();

            }

        });
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


                        for (int i = 0; i < l.size(); i++) {

                            setBook(i);

                            Date d = new Date();
                            S+="\n\nBook ID :"+l.get(i);
                            S += "\nTitle : " + B.getTitle() + "\nCategory : " + B.getType();
                            d=U.getDate().get(i).toDate();
                            S+="\nIssue Date : "+simpleDateFormat.format(d);
                            Calendar c=Calendar.getInstance();
                            c.setTime(d);
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