package com.iiitnr.libraryapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class SearchBook extends AppCompatActivity {


    private FirebaseFirestore db;
    private FirestoreRecyclerAdapter adapter;
    private int mode=0;
    private String key="";
    ProgressDialog progressDialog;
    private TextView ifNoBook;
    Query query;


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {





        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);



        ifNoBook=(TextView)findViewById(R.id.ifNoBook);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait !");
        FirebaseApp.initializeApp(this);
        db=FirebaseFirestore.getInstance();
        progressDialog.show();



        Intent intent=getIntent();

        switch(intent.getIntExtra("id",0))
        {
            case 1:{
                mode=0;
                query=db.collection("Book").whereEqualTo("id",intent.getIntExtra("bid",0)).whereGreaterThan("available",0);
                break;
            }
            case 2:{
                mode=0;
                query=db.collection("Book").whereEqualTo("id",intent.getIntExtra("bid",0));
                break;

            }
            case 3:{
                mode=1;
                key=intent.getStringExtra("btitle");
                query=db.collection("Book").whereEqualTo("type",intent.getStringExtra("btype")).whereGreaterThan("available",0);
                break;

            }
            case 4:{
                mode=1;

                key=intent.getStringExtra("btitle");
                query=db.collection("Book").whereEqualTo("type",intent.getStringExtra("btype"));
                break;
            }
            case 5:{

                mode=1;
                key=intent.getStringExtra("btitle");
                query=db.collection("Book").whereGreaterThan("available",0);
                break;
            }
            case 6:{
                mode=1;
                key=intent.getStringExtra("btitle");
                query=db.collection("Book");
                break;

            }
            case 7:{
                mode=0;
                query=db.collection("Book").whereEqualTo("type",intent.getStringExtra("btype")).whereGreaterThan("available",0);
                break;


            }
            case 8:{
                mode=0;
                query=db.collection("Book").whereEqualTo("type",intent.getStringExtra("btype"));
                break;

            }

        }
        //progressDialog.show();
        /*query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful())
                {
                    if(task.getResult().size()==0)
                    {
                        ifNoBook.setTextSize(25);
                        ifNoBook.setText("NO SUCH BOOK !");

                    }

                }
                else
                {
                    //progressDialog.cancel();
                    Toast.makeText(SearchBook.this, "Something went wrong !\nTry Again", Toast.LENGTH_SHORT).show();
                }

            }
        });
        */

            FirestoreRecyclerOptions options = new FirestoreRecyclerOptions.Builder<Book>().setQuery(query, Book.class).build();
            adapter = new BookAdapter(options, key.toUpperCase(), mode);
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        adapter.startListening();
            progressDialog.cancel();

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
