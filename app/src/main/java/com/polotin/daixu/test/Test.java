package com.polotin.daixu.test;

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

public class Test {
    public static void main(String args[]) {
        File cacheFile = LoginActivity.instance.getCacheDir();
        File validationFile = new File(cacheFile, "Cache_Validation_" + "18201912117");
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            fis = new FileInputStream(validationFile);
            ois = new ObjectInputStream(fis);
            ValidationCode vc = (ValidationCode) ois.readObject();
            System.out.print(vc.getCode());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(fis);
            IOUtils.closeQuietly(ois);
        }
    }
}
