package com.iiitnr.libraryapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    private static Context mCtx;
    private static SharedPref mInstance;

    private static final String Name="FCM";
    private static final String Key="Key";


    private SharedPref(Context context)
    {
       mCtx=context;
    }

    public static synchronized SharedPref getInstance(Context context)
    {
        if(mInstance==null)
            mInstance=new SharedPref(context);
        return mInstance;
    }

    public boolean storeToken(String token)
    {
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(Name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(Key,token);
        editor.apply();
        return true;

    }

    public String getToken()
    {
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(Name,Context.MODE_PRIVATE);
        return sharedPreferences.getString(Key,null);
    }





}
