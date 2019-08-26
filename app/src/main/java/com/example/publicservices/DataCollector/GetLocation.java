package com.example.publicservices.DataCollector;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.publicservices.MainActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class GetLocation {

    public Context context;
    public Activity activity;

    public GetLocation(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
    }

    public boolean getPermission(){

        // Permission

        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(context)
                        .setTitle("Izin Lokasi")
                        .setMessage("Aplikasi ini membutuhkan lokasi agar skripsi saya bisa lancar, mohon bantuannya :(")
                        .setPositiveButton("TENTU SAJA SAYA MAU MEMBANTU", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(activity,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        1);
                            }
                        })
                        .setNegativeButton("SAYA GAY", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            } return false;
        } else return true;
    }

    // ----- Get Address From Lat Lang

    public Map<String, Object> getAddress(double latitude, double longitude) {
        Map<String, Object> result = new HashMap<>();

        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                    result.put("lat", latitude);
                    result.put("long", longitude);
                    result.put("feature", address.getFeatureName());
                    result.put("locality", address.getLocality());
                    result.put("city", address.getSubAdminArea());
                    result.put("province", address.getAdminArea());
                    result.put("country", address.getCountryName());
                    result.put("fulladdress", address.getAddressLine(0));
                    result.put("time", MainActivity.getTimeStamp());
            }
        } catch (IOException e) {
            Log.e("address_error", e.getMessage());
        }

        return result;
    }

}