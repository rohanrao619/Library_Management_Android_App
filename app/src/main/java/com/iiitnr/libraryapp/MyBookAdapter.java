package com.iiitnr.libraryapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MyBookAdapter extends RecyclerView.Adapter<MyBookAdapter.My_Book_View> {

    public MyBookAdapter(List<MyBook> myBooks) {

        this.myBooks=myBooks;
    }

    private List<MyBook> myBooks=new ArrayList<>();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");

    @NonNull
    @Override
    public My_Book_View onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.see_my_book_view,viewGroup,false);
        return new My_Book_View(view);
    }

    @Override
    public void onBindViewHolder(@NonNull My_Book_View my_book_view, int i) {

        my_book_view.bookId1.setText("ID : "+myBooks.get(i).getBid());
        my_book_view.bookName1.setText("Title : "+myBooks.get(i).getTitle());
        my_book_view.bookType1.setText("Type : "+myBooks.get(i).getType());
        my_book_view.bookIdate.setText("Issue Date : "+simpleDateFormat.format(myBooks.get(i).getIdate()));
        my_book_view.bookDdate.setText("Due Date : "+simpleDateFormat.format(myBooks.get(i).getDdate()));

    }

    @Override
    public int getItemCount() {
        return myBooks.size();
    }

    class My_Book_View extends RecyclerView.ViewHolder
    {
        TextView bookName1,bookId1,bookType1,bookIdate,bookDdate;

        public My_Book_View(@NonNull View itemView) {
            super(itemView);
            bookId1=(TextView) itemView.findViewById(R.id.bookId1);
            bookIdate=itemView.findViewById(R.id.bookIdate);
            bookName1=itemView.findViewById(R.id.bookName1);
            bookType1=itemView.findViewById(R.id.bookType1);
            bookDdate=itemView.findViewById(R.id.bookDdate);
        }
    }


}
