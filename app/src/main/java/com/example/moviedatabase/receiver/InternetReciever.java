package com.example.moviedatabase.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.moviedatabase.util.Constants;

public class InternetReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(Constants.PREFERENCE_KEY,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (checkInternet(context)==true){
            Log.d("TAG_H","Internet is Connected");
            editor.putString(Constants.PREFERENCE_KEY, "Connected");
            editor.commit();
        }
        else{
            Log.d("TAG_H","Internet is Disconnected");

            editor.putString(Constants.PREFERENCE_KEY, "Disconnected");
            editor.commit();
        }

    }

    private boolean checkInternet(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

}
