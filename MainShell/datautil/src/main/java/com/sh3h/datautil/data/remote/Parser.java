package com.sh3h.datautil.data.remote;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.sh3h.datautil.data.entity.DUFile;
import com.sh3h.datautil.data.entity.DUUpdateXmlFile;
import com.sh3h.datautil.data.local.config.ConfigHelper;
import com.sh3h.datautil.data.local.db.DbHelper;
import com.sh3h.datautil.data.local.xml.XmlHelper;
import com.sh3h.datautil.injection.annotation.ApplicationContext;
import com.sh3h.datautil.util.EventPosterHelper;
import com.sh3h.datautil.util.FileUtil;
import com.sh3h.datautil.util.PackageUtil;
import com.sh3h.datautil.util.ZipUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Parser {
    private static final String TAG = "Parser";

    private final Context mContext;
    private final DbHelper mDbHelper;
    private final ConfigHelper mConfigHelper;
    private final EventPosterHelper mEventPosterHelper;
    private final XmlHelper mXmlHelper;

    @Inject
    public Parser(@ApplicationContext Context context,
                  DbHelper dbHelper,
                  ConfigHelper configHelper,
                  EventPosterHelper eventPosterHelper,
                  XmlHelper xmlHelper) {
        mContext = context;
        mDbHelper = dbHelper;
        mConfigHelper = configHelper;
        mEventPosterHelper = eventPosterHelper;
        mXmlHelper = xmlHelper;
    }

    /**
     * parser the file
     *
     * @param duFile
     * @return
     */
    public boolean parseFile(DUFile duFile) {
        LogUtil.i(TAG, "---parseFile---");

        try {
            File file = new File(duFile.getPath());
            if (!file.exists()) {
                return false;
            }

            File apksFolderPath = mConfigHelper.getApksFolderPath();
            if (!apksFolderPath.exists()) {
                apksFolderPath.mkdirs();
            }

            String outPathString = apksFolderPath.getPath();
            if (duFile.getFileType() == DUFile.FileType.DATA) {
                File updateFolderPath = mConfigHelper.isUsingRoleFunction() ?
                        mConfigHelper.getUpdateFolderPathByUser() : mConfigHelper.getUpdateFolderPath();
                if (!updateFolderPath.exists()) {
                    updateFolderPath.mkdirs();
                }

                outPathString = updateFolderPath.getPath();
            }
            ZipUtil.UnZipFolder(file.getPath(), outPathString);
            file.delete();

            removeConfigFiles(duFile);

            switch (duFile.getFileType()) {
                case APK:
                    return save2UpdateXml(duFile);
                case DATA:
                    return save2UpdateXml(duFile);
                default:
                    return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, "---parseFile---" + e.getMessage());
            return false;
        }
    }

    /**
     * @param duFile
     * @return
     */
    private boolean save2UpdateXml(DUFile duFile) {
        LogUtil.i(TAG, "---save2UpdateXml---");

        boolean found = false;
        DUUpdateXmlFile duUpdateXmlFileClone = mXmlHelper.getDuUpdateXmlFileClone();
        if ((duUpdateXmlFileClone != null) && (duUpdateXmlFileClone.getApplicationList() != null)) {
            List<DUUpdateXmlFile.Application> applicationList = duUpdateXmlFileClone.getApplicationList();
            for (DUUpdateXmlFile.Application application : applicationList) {
                if (application.getAppId().equals(duFile.getAppId())) {
                    switch (duFile.getFileType()) {
                        case APK:
                            if (application.getVersionCode() < duFile.getVersionCode()) {
                                application.setVersionCode(duFile.getVersionCode());
                                application.setVersionName(duFile.getVersionName());
                            }
                            break;
                        case DATA:
                            if (application.getData() != null) {
                                if (application.getData().getVersion() < duFile.getVersionCode()) {
                                    application.getData().setVersion(duFile.getVersionCode());
                                }
                            } else {
                                DUUpdateXmlFile.Data data = new DUUpdateXmlFile.Data(1,
                                        "Data", duFile.getVersionCode());
                                application.setData(data);
                            }
                            break;
                        default:
                            break;
                    }

                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            if (duFile.getFileType() == DUFile.FileType.APK) {
                if (duUpdateXmlFileClone == null) {
                    duUpdateXmlFileClone = new DUUpdateXmlFile();
                }

                int id = 1;
                List<DUUpdateXmlFile.Application> applicationList = duUpdateXmlFileClone.getApplicationList();
                if (applicationList == null) {
                    applicationList = new ArrayList<>();
                    duUpdateXmlFileClone.setApplicationList(applicationList);
                } else {
                    if (applicationList.size() > 0) {
                        id = applicationList.get(applicationList.size() - 1).getId() + 1;
                    }
                }

                applicationList.add(new DUUpdateXmlFile.Application(id, duFile.getAppName(),
                        duFile.getAppId(), duFile.getPackageName(), duFile.getVersionCode(),
                        duFile.getVersionName()));
            } else if (duFile.getFileType() == DUFile.FileType.DATA) {
                String packageName = mContext.getPackageName(); // mainshell
                if (TextUtil.isNullOrEmpty(packageName)
                        || TextUtil.isNullOrEmpty(duFile.getPackageName())
                        || !packageName.equals(duFile.getPackageName())) {
                    return false;
                }

                if (duUpdateXmlFileClone == null) {
                    duUpdateXmlFileClone = new DUUpdateXmlFile();
                }

                int id = 1;
                List<DUUpdateXmlFile.Application> applicationList = duUpdateXmlFileClone.getApplicationList();
                if (applicationList == null) {
                    applicationList = new ArrayList<>();
                    duUpdateXmlFileClone.setApplicationList(applicationList);
                } else {
                    if (applicationList.size() > 0) {
                        id = applicationList.get(applicationList.size() - 1).getId() + 1;
                    }
                }

                int versionCode = duFile.getVersionCode();
                String versionName = duFile.getVersionName();
                PackageInfo packageInfo = PackageUtil.getPackageInfo(mContext);
                if (packageInfo != null) {
                    versionCode = packageInfo.versionCode;
                    versionName = packageInfo.versionName;
                }

                DUUpdateXmlFile.Application application = new DUUpdateXmlFile.Application(id,
                        duFile.getAppName(), duFile.getAppId(), duFile.getPackageName(),
                        versionCode, versionName);
                applicationList.add(application);

                DUUpdateXmlFile.Data data = new DUUpdateXmlFile.Data(1,
                        "Data", duFile.getVersionCode());
                application.setData(data);
            } else {
                return false;
            }
        }

        mXmlHelper.setDuUpdateXmlFile(duUpdateXmlFileClone);
        return mXmlHelper.saveDuUpdateXmlFile();
    }

    /**
     * @param zipFile
     * @param destFolder
     */
    private void deleteFiles(File zipFile, File destFolder) {
        try {
            FileUtil.deleteFile(zipFile);
            FileUtil.deleteFile(destFolder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void removeConfigFiles(DUFile duFile) {
        try {
            String packageName = mContext.getPackageName(); // mainshell
            if ((duFile == null)
                    || TextUtil.isNullOrEmpty(duFile.getPackageName())
                    || TextUtil.isNullOrEmpty(packageName)
                    || TextUtil.isNullOrEmpty(duFile.getPath())) {
                return;
            }

            if (!packageName.equals(duFile.getPackageName())) {
                removeOtherApkConfigFiles(duFile);
                return;
            }

            String path = duFile.getPath();
            int lastIndex = path.lastIndexOf("_Data_V");
            if (lastIndex < 0) {
                return;
            }
            path = path.substring(0, lastIndex);
            if (TextUtil.isNullOrEmpty(path)) {
                return;
            }

            copyDirectory(path, mConfigHelper.getRootFolderPath().getPath());
            FileUtil.deleteFile(new File(path));

//            File[] files = getConfigFiles(path + "/");
//            if ((files == null) || (files.length <= 0)) {
//                return;
//            }
//
//            File destFolder = mConfigHelper.getConfigFolderPath();
//            boolean b = false;
//            for (File file : files) {
//                b = file.renameTo(new File(destFolder, file.getName()));
//            }
//
//            mConfigHelper.getOtherConfig().setRead(false);
            mConfigHelper.getSystemConfig().setRead(false);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(TAG, e.getMessage());
        }
    }

    private void removeOtherApkConfigFiles(DUFile duFile) {
        String path = duFile.getPath();
        int lastIndex1 = path.lastIndexOf("/");
        int lastIndex2 = path.lastIndexOf("_Data_V");
        if ((lastIndex1 < 0) || (lastIndex2 < 0) || (lastIndex1 >= lastIndex2)) {
            return;
        }

        String apkName = path.substring(lastIndex1 + 1, lastIndex2);
        path = path.substring(0, lastIndex2);
        if (TextUtil.isNullOrEmpty(apkName)
                || TextUtil.isNullOrEmpty(path)) {
            return;
        }

        /*File[] files = getConfigFiles(path + "/");
        if ((files == null) || (files.length <= 0)) {
            return;
        }

        final String sh3hRoot = Environment.getExternalStorageDirectory() + "/sh3h";
        final String config = "/config";
        File destFolder = new File(sh3hRoot, apkName.toLowerCase() + config);
        if (!destFolder.exists()) {
            destFolder.mkdirs();
        }

        boolean b = false;
        for (File file : files) {
            b = file.renameTo(new File(destFolder, file.getName()));
        }*/

        final String sh3hRoot = Environment.getExternalStorageDirectory() + "/sh3h";
        File destFolder = new File(sh3hRoot, apkName.toLowerCase());
        if (!destFolder.exists()) {
            destFolder.mkdirs();
        }
        copyDirectory(path, destFolder.getPath());
        FileUtil.deleteFile(new File(path));
    }

    private File[] getConfigFiles(String folder) {
        File[] childFiles = new File(folder).listFiles();
        if (childFiles.length == 0) {
            return null;
        }
        return childFiles[0].listFiles();
    }

    private void copyDirectory(String oldPath, String newPath) {
        try {
            File newFolder = new File(newPath);
            if (!newFolder.exists()) {
                newFolder.mkdirs();
            }

            File oldFile = new File(oldPath);
            String[] files = oldFile.list();
            File file;
            for (int i = 0; i < files.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    file = new File(oldPath + files[i]);
                } else {
                    file = new File(oldPath + File.separator + files[i]);
                }

                if (file.isFile()) {
                    if (!file.renameTo(new File(newPath, file.getName()))) {
                        LogUtil.e(TAG, file.getPath() + " failure to copy");
                    }
                } else if (file.isDirectory()) {//如果是子文件夹
                    copyDirectory(oldPath + File.separator + files[i], newPath + File.separator + files[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(TAG, e.getMessage());
        }
    }
}
