package com.app.officeautomationapp.activity;

import android.os.Bundle;
import android.view.View;

import com.app.officeautomationapp.R;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MarkerOptions;
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
        double lon=getIntent().getDoubleExtra("lon",39.963175);
        double lati=getIntent().getDoubleExtra("lati",116.400244);
        mMapView = (TextureMapView) findViewById(R.id.mTexturemap);
        mBaiduMap = mMapView.getMap();

        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);//普通地图 ,mBaiduMap是地图控制器对象

        LatLng point = new LatLng(lon, lati);
        //构建Marker图标

        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_marka);

        //构建MarkerOption，用于在地图上添加Marker

        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);

        //在地图上添加Marker，并显示

        mBaiduMap.addOverlay(option);

    }

    @Override
    public void onClick(View view) {

    }
}
