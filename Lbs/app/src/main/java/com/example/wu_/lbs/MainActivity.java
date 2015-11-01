package com.example.wu_.lbs;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MyLocationStyle;

public class MainActivity extends AppCompatActivity implements LocationSource,AMapLocationListener,AMap.OnMapClickListener
{
    private  LocationManagerProxy mlocationManagerProxy;
    private  MapView mapView;
    private  AMap aMap;
    private  OnLocationChangedListener mlistener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mapView= (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        aMap=mapView.getMap();
        setUpMap();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       mapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
    private void setUpMap(){
        aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_ROTATE);//实时定位类型
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);//标准地图类型，能看到河流等标志建筑物。
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.mylocation));
        aMap.setMyLocationStyle(myLocationStyle);
      // 构造 LocationManagerProxy 对象
       mlocationManagerProxy = LocationManagerProxy.getInstance(MainActivity.this);
       //设置定位资源。如果不设置此定位资源则定位按钮不可点击。
        aMap.setLocationSource(this);
       //设置默认定位按钮是否显示
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
       // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    //已弃用
    public void onLocationChanged(Location location) {

    }

    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
    public void onProviderEnabled(String provider) {

    }
    public void onProviderDisabled(String provider) {

    }

    @Override//激活定位
    public void activate(OnLocationChangedListener listener) {
       mlistener=listener;
        if(mlocationManagerProxy==null){
            mlocationManagerProxy=LocationManagerProxy.getInstance(this);
            mlocationManagerProxy.requestLocationUpdates(LocationProviderProxy.AMapNetwork,2000,10,this);
        }

    }

    @Override//停止定位
    public void deactivate() {
    mlistener=null;
        if(mlocationManagerProxy!=null){
            mlocationManagerProxy.removeUpdates(this);
            mlocationManagerProxy.destroy();
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if(mlistener != null) {
            mlistener.onLocationChanged(aMapLocation);
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }
}
