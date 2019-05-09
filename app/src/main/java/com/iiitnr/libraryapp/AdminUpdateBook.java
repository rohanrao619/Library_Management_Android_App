package com.iiitnr.libraryapp;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AdminUpdateBook extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout editTitle2;
    private TextInputLayout editBid2;
    private TextInputLayout editUnits2;
    private Spinner spinner2;
    private FirebaseFirestore db;
    private Button button2;
    private String type;
    private ProgressDialog p1;

    private Book book=new Book();
    private int qtity;

    private boolean verifyTitle()
    {
        String t=editTitle2.getEditText().getText().toString().trim();
            if(t.isEmpty())
            {
                return false;
            }
            else
            {
                return true;
            }
    }

    private boolean verifyBid()
    {
        String b=editBid2.getEditText().getText().toString().trim();
        if(b.isEmpty())
        {  editBid2.setErrorEnabled(true);
            editBid2.setError("Book ID Required");
            return true;
        }
        else
        {
            editBid2.setErrorEnabled(false);
            return false;
        }
    }

    private boolean verifyUnits()
    {
        String u=editUnits2.getEditText().getText().toString().trim();
            if(u.isEmpty())
            {
                return false;
            }
            else
            {
                return true;
            }
    }

    private boolean verifyCategory()
    {
        if (type.equals("Select Book Category"))
        {
            return false;
        }
        return true;
    }

private void updateBook()
{
    if(verifyBid())
        return;

    if(!(verifyCategory()|verifyTitle()|verifyUnits()))
    {
        Toast.makeText(this, "Select something to Update !", Toast.LENGTH_SHORT).show();
        return;
    }

    p1.setMessage("Updating ...");
    p1.show();

    db.document("Book/"+Integer.parseInt(editBid2.getEditText().getText().toString().trim())).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if(task.isSuccessful())
            {
                if(task.getResult().exists()==true)
                {
                    book=task.getResult().toObject(Book.class);
                    if(verifyCategory()){
                        book.setType(type);
                    }

                    if(verifyUnits()){
                        int temp1=book.getTotal();
                        book.setTotal(Integer.parseInt(editUnits2.getEditText().getText().toString().trim()));
                        qtity=book.getAvailable()-temp1+book.getTotal();
                        book.setAvailable(qtity);
                    }
                    if(verifyTitle())
                    {
                        book.setTitle(editTitle2.getEditText().getText().toString().trim().toUpperCase());
                    }
                    if(qtity>=0) {
                        db.document("Book/" + Integer.parseInt(editBid2.getEditText().getText().toString().trim())).set(book).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    p1.cancel();
                                    Toast.makeText(AdminUpdateBook.this, "Updated Successfully !", Toast.LENGTH_SHORT).show();
                                } else {
                                    p1.cancel();
                                    Toast.makeText(AdminUpdateBook.this, "Try Again !", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else {
                        p1.cancel();
                        Toast.makeText(AdminUpdateBook.this, "Can't Reduce No. of Units \ndue to issued units !", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    p1.cancel();
                    Toast.makeText(AdminUpdateBook.this, "No Such Book !", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                p1.cancel();
                Toast.makeText(AdminUpdateBook.this, "Try Again !", Toast.LENGTH_SHORT).show();
            }
        }
    });



}

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }

@Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_book);
    FirebaseApp.initializeApp(this);
        editTitle2=(TextInputLayout)findViewById(R.id.editTitle2);
        editBid2=(TextInputLayout) findViewById(R.id.editBid2);
        editUnits2=(TextInputLayout)findViewById(R.id.editUnits2);
        spinner2=(Spinner)findViewById(R.id.spinner2);
        button2=(Button)findViewById(R.id.button2);
        p1=new ProgressDialog(this);
        p1.setCancelable(false);
        db=FirebaseFirestore.getInstance();

    String A[]=getResources().getStringArray(R.array.list1);
        ArrayAdapter adapter =new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,A);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button2.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        updateBook();
    }
}
