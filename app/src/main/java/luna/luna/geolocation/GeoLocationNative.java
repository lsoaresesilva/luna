package luna.luna.geolocation;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import ifpe.luajavaproject.MainActivity;

/**
 * Created by macbookair on 20/04/17.
 */

public class GeoLocationNative {

    private LocationManager locationManager;
    private LocationWrapper location;
    private LuaFunction lCallBack;
    private MainActivity activity;

    public GeoLocationNative(MainActivity activity) {
        this.activity = activity;
    }

    public LocationWrapper getLocation(){
        return this.location;
    }

    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            // Called when a new location is found by the network location provider.
            GeoLocationNative.this.location.updateLocationWrapper(location);
            lCallBack.call();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };

    // TODO: should it be singleton?
    public GeoLocationNative newGeoLocationNative() {
        GeoLocationNative instance = new GeoLocationNative(this.activity);
        instance.location = new LocationWrapper();
        return instance;
    }

    public void stop(){
        this.locationManager.removeUpdates(locationListener);
    }

    public boolean retrieveLocation(LuaTable options){
        /*if (callBackName == null) {
            throw new IllegalArgumentException("A callback function must be passed as argument.");
        }*/

        long minTime;
        float minDistance;
        String type;
        LuaValue luaCallBack;

        if( options != null ) {
            LuaValue luaMinTime = options.get("minTime");
            LuaValue luaMinDistance = options.get("minDistance");
            LuaValue luaType = options.get("type");
            luaCallBack = options.get("callback");
            if( luaCallBack == null || luaMinDistance == null || luaMinTime == null || luaType == null){
                throw new IllegalArgumentException("A option table with minTime, minDistance and type must be passed as argument.");
            }

            minTime = luaMinTime.tolong();
            minDistance = luaMinDistance.tofloat();
            type = luaType.toString();
        }else{
            throw new IllegalArgumentException("A option table with minTime, minDistance and type must be passed as argument.");
        }

        if( type.equals("") ) {
            type = "oneUpdate";
        }

        lCallBack = luaCallBack.checkfunction();

        if (this.locationManager == null) {
            locationManager = (LocationManager) this.activity.getSystemService(Context.LOCATION_SERVICE);
        }

        if (ActivityCompat.checkSelfPermission(this.activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return false;
        }

        if(type.equals("multipleUpdate")){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, locationListener);
        }else if ( type.equals("oneUpdate")) {
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null);
        }

        return true;
    }


}
