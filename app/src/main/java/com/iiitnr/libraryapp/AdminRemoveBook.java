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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminRemoveBook extends AppCompatActivity implements View.OnClickListener {


    private Button findBook;
    private TextInputLayout editBid1;
    FirebaseFirestore db;
    private ProgressDialog progressDialog;
    Book b1;


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_remove_book);
        findBook=(Button)findViewById(R.id.findBook);
        editBid1=(TextInputLayout)findViewById(R.id.editBid1);
        FirebaseApp.initializeApp(this);
        db=FirebaseFirestore.getInstance();
        findBook.setOnClickListener(this);
        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        b1=new Book();



        findBook.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        if(v==findBook) {
            if (editBid1.getEditText().getText().toString().trim().isEmpty()) {

                progressDialog.cancel();
                editBid1.setError("Book Id Required ");
                editBid1.setErrorEnabled(true);
                return;
            }

            String id = editBid1.getEditText().getText().toString().trim();
            db.document("Book/" + id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {
                            AlertDialog.Builder alert= new AlertDialog.Builder(AdminRemoveBook.this);
                            b1 = task.getResult().toObject(Book.class);
                            String temp = "Title : " + b1.getTitle() + "\nCategory : " + b1.getType() + "\nNo. of Units : " + b1.getTotal();
                            progressDialog.cancel();
                            alert.setMessage(temp).setTitle("Please Confirm !").setCancelable(false).setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.cancel();
                                    progressDialog.setMessage("Removing ... ");
                                    progressDialog.show();
                                    if(b1.getAvailable()==b1.getTotal())
                                    {
                                        db.document("Book/"+editBid1.getEditText().getText().toString().trim()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                progressDialog.cancel();
                                                Toast.makeText(AdminRemoveBook.this, "Book Removed", Toast.LENGTH_SHORT).show();

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.cancel();
                                                Toast.makeText(AdminRemoveBook.this, "Try Again !", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                    else
                                    {   progressDialog.cancel();
                                        Toast.makeText(AdminRemoveBook.this, "This Book is issued to Users !\nReturn before Removing this Book.", Toast.LENGTH_LONG).show();

                                    }

                                }
                            }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            AlertDialog alertDialog=alert.create();
                            alertDialog.show();


                        } else {
                            progressDialog.cancel();
                            Toast.makeText(AdminRemoveBook.this, "No such Book found !", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        progressDialog.cancel();
                        Toast.makeText(AdminRemoveBook.this, "Try Again !", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
    }
}


