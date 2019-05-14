package com.polotin.daixu.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.polotin.daixu.R;
import com.polotin.daixu.entity.Plan;
import com.polotin.daixu.presenter.IPlanPresenter;
import com.polotin.daixu.presenter.PlanPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class AddPlanActivity extends Activity implements View.OnClickListener, IPlanView, AMap.OnMyLocationChangeListener, GeocodeSearch.OnGeocodeSearchListener, AMapLocationListener, LocationSource {
    MapView mMapView = null;
    AMap aMap;
    UiSettings mUiSettings;//定义一个UiSettings对象
    GeocodeSearch geocoderSearch = null;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;
    private LocationSource.OnLocationChangedListener mListener = null;//定位监听器
    private TextView tvLocation;
    private TextView tvHours;
    private TextView tvContent;
    private Button btnAdd;
    private Button btnUseCurLocation;
    public static String curPostion = "";

    private IPlanPresenter iPlanPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);
        iPlanPresenter = new PlanPresenter(this);
        initViews();
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
            mUiSettings = aMap.getUiSettings();
        }
        initMap();
        initLocation();
    }

    void initViews() {
        btnUseCurLocation = findViewById(R.id.btn_use_curlocation);
        tvContent = findViewById(R.id.id_et_content);
        tvLocation = findViewById(R.id.id_et_position);
        tvHours = findViewById(R.id.id_et_hours);
        btnAdd = findViewById(R.id.id_btn_add);
        btnAdd.setOnClickListener(this);
        btnUseCurLocation.setOnClickListener(this);
        Intent intent = getIntent();
        if ("edit".equals(intent.getStringExtra("operation"))) {
            Gson gson = new Gson();
            Plan plan = gson.fromJson(intent.getStringExtra("plan"), Plan.class);
            tvContent.setText(plan.getContent());
            tvLocation.setText(plan.getPosition());
            tvHours.setText("" + plan.getHours());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_btn_add:
                Intent intent = getIntent();
                String phone = intent.getStringExtra("phoneNumber");
                String operation = intent.getStringExtra("operation");
                if ("edit".equals(intent.getStringExtra("operation"))) {
                    Gson gson = new Gson();
                    Plan plan = gson.fromJson(intent.getStringExtra("plan"), Plan.class);
                    plan.setPosition(tvLocation.getText().toString());
                    plan.setContent(tvContent.getText().toString());
                    plan.setHours(Float.valueOf(tvHours.getText().toString()));
                    iPlanPresenter.updatePlan(plan);
                } else {
                    Plan plan = new Plan();
                    plan.setPosition(tvLocation.getText().toString());
                    plan.setContent(tvContent.getText().toString());
                    plan.setHours(Float.valueOf(tvHours.getText().toString()));
                    plan.setPhoneNumber(phone);
                    iPlanPresenter.addPlan(plan);
                }
                break;
            case R.id.btn_use_curlocation:
                tvLocation.setText(curPostion);
                break;
        }
    }

    @Override
    public void addSuccess() {
        Toast.makeText(this, "操作成功", Toast.LENGTH_SHORT).show();
        MainActivity.instance.refreshList();
        this.finish();
    }

    void initMap() {
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
        aMap.setLocationSource(this);
        myLocationStyle.showMyLocation(true);
        myLocationStyle.radiusFillColor(android.R.color.transparent);
        myLocationStyle.strokeColor(android.R.color.transparent);
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setOnMyLocationChangeListener(this);
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);
        // 是否显示定位按钮
        mUiSettings.setMyLocationButtonEnabled(true);
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
    }

    void initLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    0);//自定义的code
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    0);//自定义的code
        }
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                amapLocation.getAddress();  // 地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                amapLocation.getCountry();  // 国家信息
                amapLocation.getProvince();  // 省信息
                amapLocation.getCity();  // 城市信息
                amapLocation.getDistrict();  // 城区信息
                amapLocation.getStreet();  // 街道信息
                amapLocation.getStreetNum();  // 街道门牌号信息
                amapLocation.getCityCode();  // 城市编码
                amapLocation.getAdCode();//地区编码

                // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
                if (isFirstLoc) {
                    //设置缩放级别
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(20));
                    //将地图移动到定位点
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(amapLocation.getLatitude(),
                            amapLocation.getLongitude())));
                    //点击定位按钮 能够将地图的中心移动到定位点
                    mListener.onLocationChanged(amapLocation);
                    //添加图钉
                    aMap.addMarker(getMarkerOptions(amapLocation));
                    //获取定位信息
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(amapLocation.getCountry() + "" + amapLocation.getProvince()
                            + "" + amapLocation.getCity() + "" + amapLocation.getProvince()
                            + "" + amapLocation.getDistrict() + "" + amapLocation.getStreet()
                            + "" + amapLocation.getStreetNum());
                    curPostion = buffer.toString();
                    if(getIntent().getStringExtra("operation").equals("add")) {
                        tvLocation.setText(buffer.toString());
                    }
                    isFirstLoc = false;
                }
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
                Toast.makeText(getApplicationContext(), "定位失败", Toast.LENGTH_LONG).show();
            }
        }
    }

    private MarkerOptions getMarkerOptions(AMapLocation amapLocation) {
        //设置图钉选项
        MarkerOptions options = new MarkerOptions();
        //图标
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.xui_ic_arrow_down_black));
        //位置
        options.position(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude()));
        StringBuffer buffer = new StringBuffer();
        buffer.append(amapLocation.getCountry() + "" + amapLocation.getProvince() + ""
                + amapLocation.getCity() + "" + amapLocation.getDistrict()
                + "" + amapLocation.getStreet() + "" + amapLocation.getStreetNum());
        //标题
        options.title(buffer.toString());
        //子标题
        options.snippet("（您目前所在的位置）");
        //设置多少帧刷新一次图片资源
        options.period(60);
        return options;
    }

    @Override
    public void onMyLocationChange(Location location) {

    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mListener = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
}
