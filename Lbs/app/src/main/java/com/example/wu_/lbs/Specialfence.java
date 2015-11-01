package com.example.wu_.lbs;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiItemDetail;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import java.util.List;


public class Specialfence extends Activity implements AMapLocationListener,PoiSearch.OnPoiSearchListener,View.OnClickListener, LocationSource {
    LocationManagerProxy locationManagerProxy;
    AMap aMap;
    MapView mapView;
    AMapLocation aMapLocation;
    EditText et;
    private  OnLocationChangedListener mlistener;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.special);
        mapView= (MapView) findViewById(R.id.map);
        aMap=mapView.getMap();
        mapView.onCreate(savedInstanceState);
        setUpMap();
    }
    private void setUpMap(){
         // locationManagerProxy=LocationManagerProxy.getInstance(this);//初始化定位对象。
        //locationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, 2000, 10, this);
        aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_ROTATE);//实时定位类型
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);//标准地图类型，能看到河流等标志建筑物。
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.mylocation));
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource(this);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setMyLocationEnabled(true);

        Button button= (Button) findViewById(R.id.buto);
        button.setOnClickListener(this);
        et= (EditText) findViewById(R.id.dizhi);
        lv= (ListView) findViewById(R.id.list);
    }

    public void search(){
    PoiSearch.Query query=new PoiSearch.Query("星巴克","",aMapLocation.getCityCode());
    query.setPageNum(1);
    query.setPageSize(10);//设置每页做多返回多少条poiitem
    PoiSearch poiSearch=new PoiSearch(this,query);
    poiSearch.setOnPoiSearchListener(this);
    LatLonPoint latLonPoint=new LatLonPoint(aMapLocation.getLatitude(),aMapLocation.getLongitude());
    poiSearch.setBound(new PoiSearch.SearchBound(latLonPoint,12000,true));//第二个参数是搜索的半径
    poiSearch.searchPOIAsyn();
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
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLocationChanged(AMapLocation args) {
        aMapLocation=args;
        if(mlistener != null) {
            mlistener.onLocationChanged(aMapLocation);
        }
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
    PoiItem poiItem; LatLng MlatLng;
    @Override//地图搜索的回调方法。
    public void onPoiSearched(PoiResult poiResult, int i) {

        if(i==0){
            List<PoiItem> list = poiResult.getPois();
            String[] arr1=new String[list.size()];
            for(int j=0;j<list.size();j++){
                poiItem=list.get(j);
                arr1[j]=poiItem.toString();
                MlatLng=new LatLng(poiItem.getLatLonPoint().getLatitude(),poiItem.getLatLonPoint().getLongitude());
//                CircleOptions circleOptions = new CircleOptions();
//                circleOptions.center(latLng).radius(300)
//                        .fillColor(Color.argb(180, 224, 171, 10))
//                        .strokeColor(Color.RED);
//                aMap.addCircle(circleOptions);
//                CircleOptions circleOptions2 = new CircleOptions();
//                circleOptions.center(latLng).radius(600)
//                        .fillColor(Color.argb(180, 224, 171, 10))
//                        .strokeColor(Color.BLUE);
//                aMap.addCircle(circleOptions2);
//                CircleOptions circleOptions3 = new CircleOptions();
//                circleOptions.center(latLng).radius(1000)
//                        .fillColor(Color.argb(180, 224, 171, 10))
//                        .strokeColor(Color.GREEN);
//                aMap.addCircle(new CircleOptions().center(latLng).radius(1000));
            }
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CircleOptions circleOptions = new CircleOptions();
                    circleOptions.center(MlatLng).radius(300)
                            .fillColor(Color.argb(180, 224, 171, 10))
                            .strokeColor(Color.RED);
                    aMap.addCircle(circleOptions);
                    CircleOptions circleOptions2 = new CircleOptions();
                    circleOptions.center(MlatLng).radius(600)
                            .fillColor(Color.argb(180, 224, 171, 10))
                            .strokeColor(Color.BLUE);
                    aMap.addCircle(circleOptions2);
                    CircleOptions circleOptions3 = new CircleOptions();
                    circleOptions.center(MlatLng).radius(1000)
                            .fillColor(Color.argb(180, 224, 171, 10))
                            .strokeColor(Color.GREEN);
                    aMap.addCircle(circleOptions3);
                }
            });
            ArrayAdapter<String> adapte=new ArrayAdapter<String>(Specialfence.this,R.layout.array_item,arr1);
            lv.setAdapter(adapte);

     }
    }

    @Override
    public void onPoiItemDetailSearched(PoiItemDetail poiItemDetail, int i) {

    }

    @Override
    public void onClick(View v) {
        search();
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mlistener=listener;
        if(locationManagerProxy==null){
            locationManagerProxy=LocationManagerProxy.getInstance(this);
            locationManagerProxy.requestLocationUpdates(LocationProviderProxy.AMapNetwork,2000*60,10,this);
        }

    }

    @Override
    public void deactivate() {
        mlistener=null;
        if(locationManagerProxy!=null){
            locationManagerProxy.removeUpdates(this);
            locationManagerProxy.destroy();
        }
    }
}
