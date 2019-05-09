package com.iiitnr.libraryapp;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.List;

public class AdminAddBook extends AppCompatActivity implements View.OnClickListener{

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_book);
        FirebaseApp.initializeApp(this);
        editTitle=(TextInputLayout)findViewById(R.id.editTitle);
        editBid=(TextInputLayout) findViewById(R.id.editBid);
        editUnits=(TextInputLayout)findViewById(R.id.editUnits);
        spinner1=(Spinner)findViewById(R.id.spinner1);
        button1=(Button)findViewById(R.id.button1);
        p1=new ProgressDialog(this);
        p1.setCancelable(false);
        db=FirebaseFirestore.getInstance();


        String A[]=getResources().getStringArray(R.array.list1);
        ArrayAdapter adapter =new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,A);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button1.setOnClickListener(this);
    }

    private boolean verifyTitle()
    {
        String t=editTitle.getEditText().getText().toString().trim();
        if(t.isEmpty())
        {   editTitle.setErrorEnabled(true);
            editTitle.setError("Title Required");
            return true;
        }
        else
        {
            editTitle.setErrorEnabled(false);
            return false;
        }
    }

    private boolean verifyBid()
    {
        String b=editBid.getEditText().getText().toString().trim();
        if(b.isEmpty())
        {   editBid.setErrorEnabled(true);
            editBid.setError("Book Id Required");
            return true;
        }
        else
        {
            editBid.setErrorEnabled(false);
            return false;
        }
    }

    private boolean verifyUnits()
    {
        String u=editUnits.getEditText().getText().toString().trim();
        if(u.isEmpty())
        {   editUnits.setErrorEnabled(true);
            editUnits.setError("No. of Units Required");
            return true;
        }
        else
        {
            editUnits.setErrorEnabled(false);
            return false;
        }
    }

    private boolean verifyCategory()
    {
        if (type.equals("Select Book Category"))
        {
            Toast.makeText(this, "Please select Book Category !", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void addBook()
    {

        boolean res=(verifyBid()|verifyTitle()|verifyUnits()|verifyCategory());
        if(res==true)
            return;
        else
        {

            p1.setMessage("Adding Book");
            p1.show();
            final String id=editBid.getEditText().getText().toString().trim();
            int id1=Integer.parseInt(id);
            db.document("Book/"+id1).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if((task.isSuccessful())&&(task.getResult().exists()==false))
                    {    String id=editBid.getEditText().getText().toString().trim();
                        String title=editTitle.getEditText().getText().toString().trim();
                        String units=editUnits.getEditText().getText().toString().trim();
                        int id1=Integer.parseInt(id),unit1=Integer.parseInt(units);
                        Book b=new Book(title.toUpperCase(),type,unit1,id1);
                        db.document("Book/"+id1).set(b).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {   p1.cancel();
                                    Toast.makeText(AdminAddBook.this, "Book Added !", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {   p1.cancel();
                                    Toast.makeText(AdminAddBook.this, "Try Again !", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else
                        {
                            p1.cancel();
                            Toast.makeText(AdminAddBook.this, "This Book is already added \n or Bad Connection !", Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }
    }

    private TextInputLayout editTitle;
    private TextInputLayout editBid;
    private TextInputLayout editUnits;
    private Spinner spinner1;
    private FirebaseFirestore db;
    Button button1;
    String type;
    ProgressDialog p1;



    @Override
    public void onClick(View v) {

       addBook();

    }
}
