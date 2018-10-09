package com.polotin.daixu.utils;

import com.polotin.daixu.entity.ValidationCode;
import com.polotin.daixu.values.Constant;
import com.polotin.daixu.view.LoginActivity;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CacheUtil {
    public static boolean cacheValidationCode(){
        File cacheFile = LoginActivity.instance.getCacheDir();
        System.out.println("VALIDATE_CODE_TIME_" + Constant.MOBILE);
        File validationFile = new File(cacheFile, "Cache_Validation_" + Constant.MOBILE);
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(validationFile);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(new ValidationCode(Constant.VALIDATE_CODE, Constant.VALIDATE_CODE_TIME));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fos);
            IOUtils.closeQuietly(oos);
        }
        return false;
    }

    public static ValidationCode getValidationCode(){
        File cacheFile = LoginActivity.instance.getCacheDir();
        File validationFile = new File(cacheFile, "Cache_Validation_" + "18201912117");
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            fis = new FileInputStream(validationFile);
            ois = new ObjectInputStream(fis);
            ValidationCode vc = (ValidationCode) ois.readObject();
            return vc;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(fis);
            IOUtils.closeQuietly(ois);
        }
        return null;
    }
}
