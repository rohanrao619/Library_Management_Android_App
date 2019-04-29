package com.iiitnr.libraryapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private TextView ifNoBook1;
    private User U = new User();
    private Book B = new Book();
    private int s;

    private MyBook[] myBooks= new MyBook[3];


    RecyclerView recyclerView;
    //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_see_my_books);
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        ifNoBook1 = (TextView) findViewById(R.id.ifNoBook1);
        recyclerView=(RecyclerView)findViewById(R.id.recycle1) ;
        showBook();
    }

    private void setBook(int i)
    {

        db.document("Book/"+U.getBook().get(i)/100).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                B=task.getResult().toObject(Book.class);
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
                        s=U.getBook().size();


                        List<Integer> l = new ArrayList<Integer>();
                        l = U.getBook();


                        for (int i = 0; i < l.size(); i++) {

                            setBook(i);
                            MyBook myBook=new MyBook();
                            myBook.setBid(U.getBook().get(i));
                            Date d = new Date();
                            d=U.getDate().get(i).toDate();
                            myBook.setIdate(d);
                            Calendar c=Calendar.getInstance();
                            c.setTime(d);
                            c.add(Calendar.DAY_OF_MONTH,14);
                            d=c.getTime();
                            myBook.setDdate(d);
                            myBooks[i]=myBook;
                            Toast.makeText(UserSeeMyBooks.this, ""+myBook.getBid(), Toast.LENGTH_SHORT).show();
                        }
                        RecyclerView.Adapter adapter=new MyBookAdapter(myBooks);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getParent()));
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);

                    }
                }
            }
        });


        MyBook[] myBooks1=new MyBook[1];
        for(int q=0;q<s;q++)
            myBooks1[q]=myBooks[q];
        //Toast.makeText(this, ""+myBooks[0].getBid(), Toast.LENGTH_SHORT).show();
       /* RecyclerView.Adapter adapter=new MyBookAdapter(myBooks1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);*/
    }

}