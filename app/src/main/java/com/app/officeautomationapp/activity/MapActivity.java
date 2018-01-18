package com.app.officeautomationapp.activity;

import android.os.Bundle;
import android.view.View;

import com.app.officeautomationapp.R;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;

/**
 * Created by Administrator on 2018/1/18 0018.
 */

public class MapActivity extends BaseActivity implements View.OnClickListener  {

    private TextureMapView mMapView;
    private BaiduMap mBaiduMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);
        double lon=getIntent().getDoubleExtra("lon",39.963175);
        double lati=getIntent().getDoubleExtra("lati",116.400244);
        mMapView = (TextureMapView) findViewById(R.id.mTexturemap);
        mBaiduMap = mMapView.getMap();

        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);//普通地图 ,mBaiduMap是地图控制器对象
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.FOLLOWING, true, BitmapDescriptorFactory .fromResource(R.mipmap.icon_marka),
                0xAAFFFF88, 0xAA00FF00));
        LatLng point = new LatLng(lati, lon);
        //构建Marker图标

        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_marka);

        //构建MarkerOption，用于在地图上添加Marker

        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);

        //在地图上添加Marker，并显示

        mBaiduMap.addOverlay(option);

        MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(point);
        mBaiduMap.animateMapStatus(status);
    }

    @Override
    public void onClick(View view) {

    }
}
