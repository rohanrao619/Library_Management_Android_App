package com.iiitnr.libraryapp;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UserReissueBook extends AppCompatActivity {



    private Button reissueButton2;
    Spinner spinner4;
    private FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    private ProgressDialog p;
    private boolean res1;
    private User U = new User();
    private String flag;
    private List<String> A=new ArrayList<>();


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reissue_book);
        FirebaseApp.initializeApp(this);
        A.add("Select Book");
        reissueButton2 = (Button) findViewById(R.id.reissueButton2);
        spinner4=(Spinner)findViewById(R.id.spinner4);
        db = FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        p = new ProgressDialog(this);
        p.setMessage("Please Wait !");

        p.show();
        db.document("User/"+firebaseAuth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful())
                {

                    p.cancel();
                    U=task.getResult().toObject(User.class);

                    int i;
                    for(i=0;i<U.getBook().size();i++)
                    {
                        if(U.getRe().get(i)==1)
                            A.add(String.valueOf(U.getBook().get(i)));
                    }

                }
                else
                {
                    p.cancel();

                }
            }
        });


        setSpinner4();


        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                flag=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        reissueButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reissueBook();
            }
        });

    }


    private void setSpinner4()
    {


        ArrayAdapter adapter =new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,A);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter);

    }

    private boolean verifyCategory()
    {
        if (flag.equals("Select Book"))
        {
            Toast.makeText(this, "Please select a book to Re-issue !", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void reissueBook() {

        if(verifyCategory())
            return;

        p.show();

        List<Integer> l = new ArrayList<Integer>();

        int i = U.getBook().indexOf(Integer.parseInt(flag));

        U.setLeft_fine(U.getLeft_fine() + U.getFine().get(i));
        l = U.getFine();
        l.set(i, 0);
        U.setFine(l);

        l = U.getRe();
        l.set(i, 0);
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
                    Toast.makeText(UserReissueBook.this, "Re-Issued Successfully !", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    p.cancel();
                    Toast.makeText(UserReissueBook.this, "Please try Again !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        A.remove(flag);
        setSpinner4();
        p.cancel();

    }

}
