package com.sh3h.mainshell_chaobiaoji.adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sh3h.datautil.data.entity.DUConfigXmlFile.Component;
import com.sh3h.mainshell_chaobiaoji.R;

import java.util.List;

/**
 * Created by dengzhimin on 2016/5/27.
 */
public class GridViewAdapter extends BaseAdapter {

    private List<Component> mComponents;

    private Context mContext;

    private LayoutInflater mInflater;

    private String mApkFolder;

    private int mResId;

    public GridViewAdapter(Context context, List<Component> components, String apkFolder, int resId) {
        mComponents = components;
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mApkFolder = apkFolder;
        mResId = resId;
    }

    public void setApkInfos(List<Component> components) {
        mComponents = components;
    }

    public void setResId(int resId) {
        mResId = resId;
    }

    @Override
    public int getCount() {
        return mComponents.size();
    }

    @Override
    public Object getItem(int position) {
        return mComponents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(mResId, null);
            mHolder = new ViewHolder();
            mHolder.mApkIcon = (ImageView) convertView.findViewById(R.id.apk_icon);
            mHolder.mApkName = (TextView) convertView.findViewById(R.id.apk_name);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        Component component = mComponents.get(position);
        Drawable drawable = getIcon(component.getPackageName(), component.getIcon());
        if (drawable != null) {
            mHolder.mApkIcon.setImageDrawable(drawable);
        }

        mHolder.mApkName.setText(component.getName());
        return convertView;
    }

    private Drawable getIcon(String packageName, String icon) {
        Drawable drawable = null;
        try {
            if (icon.contains(".png")) {
                int index = icon.indexOf(".png");
                icon = icon.substring(0, index);
            }

            PackageManager pm = mContext.getPackageManager();
            PackageInfo info = pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            if (info != null) {
                ApplicationInfo appInfo = info.applicationInfo;
                Resources resources = pm.getResourcesForApplication(packageName);
                int id = resources.getIdentifier(icon, "mipmap", packageName);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    drawable = resources.getDrawable(id, null);
                } else {
                    drawable = resources.getDrawable(id);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            drawable = null;
        }

        if (drawable == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawable = mContext.getResources().getDrawable(R.mipmap.ic_launcher, null);
            } else {
                drawable = mContext.getResources().getDrawable(R.mipmap.ic_launcher);
            }
        }

        return drawable;
    }

    public class ViewHolder {
        private ImageView mApkIcon;
        private TextView mApkName;
    }
}
