package com.mainshell.datautil.data.entity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.mainshell.datautil.data.local.config.ConfigHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by xulongjun on 2016/5/18.
 */
@Deprecated
public class DUApkFile {

    private static final String ICON_PNG = ".png";
    private Drawable icon;
    private String iconPath;
    private CharSequence title;
    private String versionName;
    private int versionCode;
    private String apkSize;
    public String apkfile;
    public PackageInfo packageInfo;
    private String apkDownLoadUrl;
    private String packageName;

    private int percent;
    private boolean isOnDownApkFile;
    private boolean isDownApkFile;

    public boolean installing = false;


    public DUApkFile(){

    }

    public DUApkFile(Context context, PackageInfo info, String path) {
        PackageManager pm = context.getPackageManager();
        Resources resources = null;
        try {
            resources = getResources(context, path);
        } catch (Exception e) {
        }
        try {
            if (resources != null) {
                icon = resources.getDrawable(info.applicationInfo.icon);
            }
        } catch (Exception e) {
            icon = pm.getDefaultActivityIcon();
        }

        try {
            if (resources != null) {

                title = resources.getString(info.applicationInfo.labelRes);
            }
        } catch (Exception e) {
            title = info.packageName;
        }
        packageName = info.packageName;
        versionName = info.versionName;
        versionCode = info.versionCode;
        apkfile = path;
        packageInfo = info;
        //iconPath = saveDrawable(icon, title + ICON_PNG);
    }

    public DUApkFile(PackageManager pm, PackageInfo info, String path) {
        try {
            icon = pm.getApplicationIcon(info.applicationInfo);
        } catch (Exception e) {
            icon = pm.getDefaultActivityIcon();
        }
        title = pm.getApplicationLabel(info.applicationInfo);
        packageName = info.packageName;
        versionName = info.versionName;
        versionCode = info.versionCode;
        apkfile = path;
        packageInfo = info;
        //iconPath = saveDrawable(icon, title + ICON_PNG);
    }

//    public String saveDrawable(Drawable drawable, String fileName){
//        Bitmap bitmap = null;
//        FileOutputStream out = null;
//        File file = new File(ConfigHelper.STORAGE_PATH, ConfigHelper.FOLDER_APKS_ICONS + "/" + fileName);
//        if(!file.exists()){
//            try {
//                bitmap = ((BitmapDrawable)drawable).getBitmap();
//                out = new FileOutputStream(file);
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }finally {
//                if(out != null){
//                    try {
//                        out.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//        return file.getPath();
//    }

    public static Resources getResources(Context context, String apkPath) throws Exception {
        String PATH_AssetManager = "android.content.res.AssetManager";
        Class assetMagCls = Class.forName(PATH_AssetManager);
        Constructor assetMagCt = assetMagCls.getConstructor((Class[]) null);
        Object assetMag = assetMagCt.newInstance((Object[]) null);
        Class[] typeArgs = new Class[1];
        typeArgs[0] = String.class;
        Method assetMag_addAssetPathMtd = assetMagCls.getDeclaredMethod("addAssetPath",
                typeArgs);
        Object[] valueArgs = new Object[1];
        valueArgs[0] = apkPath;
        assetMag_addAssetPathMtd.invoke(assetMag, valueArgs);
        Resources res = context.getResources();
        typeArgs = new Class[3];
        typeArgs[0] = assetMag.getClass();
        typeArgs[1] = res.getDisplayMetrics().getClass();
        typeArgs[2] = res.getConfiguration().getClass();
        Constructor resCt = Resources.class.getConstructor(typeArgs);
        valueArgs = new Object[3];
        valueArgs[0] = assetMag;
        valueArgs[1] = res.getDisplayMetrics();
        valueArgs[2] = res.getConfiguration();
        res = (Resources) resCt.newInstance(valueArgs);
        return res;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
        if(title != null && title.length()>0){
            //this.iconPath = saveDrawable(icon, title + ICON_PNG);
        }

    }

    public CharSequence getTitle() {
        return title;
    }

    public void setTitle(CharSequence title) {
        this.title = title;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getApkfile() {
        return apkfile;
    }

    public void setApkfile(String apkfile) {
        this.apkfile = apkfile;
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(PackageInfo packageInfo) {
        this.packageInfo = packageInfo;
    }

    public String getApkSize() {
        return apkSize;
    }

    public void setApkSize(String apkSize) {
        this.apkSize = apkSize;
    }

    public String getApkDownLoadUrl() {
        return apkDownLoadUrl;
    }

    public void setApkDownLoadUrl(String apkDownLoadUrl) {
        this.apkDownLoadUrl = apkDownLoadUrl;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public boolean isOnDownApkFile() {
        return isOnDownApkFile;
    }

    public void setOnDownApkFile(boolean onDownApkFile) {
        isOnDownApkFile = onDownApkFile;
    }

    public boolean isDownApkFile() {
        return isDownApkFile;
    }

    public void setDownApkFile(boolean downApkFile) {
        isDownApkFile = downApkFile;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }
}
