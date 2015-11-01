package com.example.wu_.lbs;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;


public class Fence extends Activity implements AMap.OnMapClickListener, AMapLocationListener {
    private MapView mapView;
    private AMap aMap;
    private PendingIntent mpendingIntent;
    LocationManagerProxy locationManagerProxy;
    String GEOFENCE_BROADCAST_ACTION = "com.location.apis.geofencedemo.broadcast";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView= (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        aMap=mapView.getMap();
        aMap.setOnMapClickListener(this) ;
        locationManagerProxy=LocationManagerProxy.getInstance(this);
        locationManagerProxy.requestLocationData(LocationManager.GPS_PROVIDER,2000,15,this);
        Intent intent=new Intent(GEOFENCE_BROADCAST_ACTION);
        mpendingIntent =PendingIntent.getBroadcast(getApplicationContext(),0,intent,0);
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(GEOFENCE_BROADCAST_ACTION);
        this.registerReceiver(mGeoFenceReceiver,intentFilter);
    }
private BroadcastReceiver mGeoFenceReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
        // 接受广播
        if (intent.getAction().equals(GEOFENCE_BROADCAST_ACTION)) {
            Bundle bundle = intent.getExtras();
            // 根据广播的status来确定是在区域内还是在区域外
            int status = bundle.getInt("status");
            if (status == 0) {
                Toast.makeText(getApplicationContext(), "不在区域",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "在区域内",
                        Toast.LENGTH_LONG).show();
            }

        }

    }
};
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        aMap.addCircle(new CircleOptions().center(latLng).radius(1000));
        locationManagerProxy.addGeoFenceAlert(latLng.latitude,latLng.longitude,1000,1000*60*30,mpendingIntent);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
