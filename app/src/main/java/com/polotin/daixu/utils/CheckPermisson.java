package com.polotin.daixu.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;

import com.polotin.daixu.values.Constant;
import com.polotin.daixu.view.LoginActivity;

public class CheckPermisson {
    public static void checkPermission(Context context){

        int netPermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.INTERNET);
        int writePermmision = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(netPermission!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.instance, Manifest.permission.INTERNET)){
                ActivityCompat.requestPermissions(LoginActivity.instance, new String[]{Manifest.permission.INTERNET}, Constant.CODE_INTERNET);
            }
        }
        if(writePermmision != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.instance, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(LoginActivity.instance,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constant.CODE_WRITE_EXTERNAL);
            }
        }
        if(readPermission != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.instance, Manifest.permission.READ_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(LoginActivity.instance,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constant.CODE_READ_EXTERNAL);
            }
        }
    }
}
