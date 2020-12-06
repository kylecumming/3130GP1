package com.example.csci3130groupproject;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.logging.Handler;

public class GeoLocation {

    public static void getAddress(final String locationAddress, final Context context, Handler handler){
        Thread thread = new Thread(){
            public GoogleMap map;

            @Override
            public void run() {
                super.run();
                Geocoder geocoder =  new Geocoder(context, Locale.getDefault());
                try{
                    List addressList = geocoder.getFromLocationName(locationAddress,1);
                    if(addressList  != null && addressList.size() > 0){
                        Address address = (Address) addressList.get(0);
                        MarkerOptions options = new MarkerOptions().position(new LatLng(address.getLatitude(),address.getLongitude())).title("My house");
                        map.addMarker(options);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}
