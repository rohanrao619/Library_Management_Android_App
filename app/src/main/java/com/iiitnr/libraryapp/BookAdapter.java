package com.iiitnr.libraryapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

public class BookAdapter extends FirestoreRecyclerAdapter<Book, BookAdapter.Book> {


    private String key;
    private int mode;

    public BookAdapter(@NonNull FirestoreRecyclerOptions options,String key,int mode) {
        super(options);
        this.key=key;
        this.mode=mode;
    }

    @Override
    protected void onBindViewHolder(@NonNull Book holder, int position, @NonNull com.iiitnr.libraryapp.Book model) {

        holder.bookId.setText("ID : "+String.valueOf(model.getId()));
        holder.bookType.setText("Category : "+model.getType());
        holder.bookAvailable.setText("Available : "+String.valueOf(model.getAvailable()));
        holder.bookName.setText("Title : "+model.getTitle());
        holder.bookTotal.setText("Total : "+String.valueOf(model.getTotal()));


          if((!model.getTitle().contains(key))&&(mode==1))
            {
               holder.itemView.setVisibility(View.GONE);
               RecyclerView.LayoutParams layoutParams=(RecyclerView.LayoutParams)holder.itemView.getLayoutParams();
               layoutParams.height=0;
               layoutParams.width=0;
               holder.itemView(layoutParams);
            }
    }

    @NonNull
    @Override
    public Book onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.book_view,viewGroup,false);

        return new Book(view);
    }

    class Book extends RecyclerView.ViewHolder
    {

        TextView bookName,bookId,bookType,bookAvailable,bookTotal;


        public Book(@NonNull View itemView) {
            super(itemView);
            bookId= itemView.findViewById(R.id.bookId);
            bookAvailable=itemView.findViewById(R.id.bookAvailable);
            bookName=itemView.findViewById(R.id.bookName);
            bookType=itemView.findViewById(R.id.bookType);
            bookTotal=itemView.findViewById(R.id.bookTotal);
        }

        public void itemView(Object o) {
        }
    }
}
