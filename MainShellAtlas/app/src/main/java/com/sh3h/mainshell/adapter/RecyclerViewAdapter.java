package com.sh3h.mainshell.adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mainshell.datautil.data.entity.DUConfigXmlFile;
import com.sh3h.mainshell.R;
import com.sh3h.mainshell.util.ConstDataUtil;
import com.sh3h.mobileutil.util.LogUtil;
import com.sh3h.mobileutil.util.TextUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Collections;
import java.util.List;

import co.paulburke.android.itemtouchhelper.helper.ItemTouchHelperAdapter;
import co.paulburke.android.itemtouchhelper.helper.ItemTouchHelperViewHolder;

/**
 * create by tanghuijuan
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>
        implements ItemTouchHelperAdapter {
    public interface OnItemClickListener {
        void onItemClick(DUConfigXmlFile.Component component);
    }

    public interface OnDataChangedListener {
        void OnDataChanged();
    }

    private static final String TAG = "RecyclerViewAdapter";
    private List<DUConfigXmlFile.Component> mComponents;
    private Context mContext;
    private boolean mIsAtlasMode;
    private String mIconFolder;
    private LayoutInflater mInflater;
    private int mItemType;
    private OnItemClickListener mOnItemClickListener;
    private OnDataChangedListener mOnDataChangedListener;

    public RecyclerViewAdapter(List<DUConfigXmlFile.Component> components,
                               Context mContext,
                               boolean isAtlasMode,
                               String iconFolder) {
        this.mComponents = components;
        this.mContext = mContext;
        this.mIsAtlasMode = isAtlasMode;
        this.mIconFolder = iconFolder;
        this.mInflater = LayoutInflater.from(mContext);
        this.mOnItemClickListener = null;
        this.mOnDataChangedListener = null;
    }

    public void setOnDataChangedListener(OnDataChangedListener listener) {
        this.mOnDataChangedListener = listener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setApkInfos(List<DUConfigXmlFile.Component> components) {
        this.mComponents = components;
    }

    public int getItemType() {
        return mItemType;
    }

    public void setItemType(int mItemType) {
        this.mItemType = mItemType;
    }

    @Override
    public int getItemViewType(int position) {
        return mItemType;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int resource;
        if (viewType == ConstDataUtil.TYPE_LIST_VIEW) {
            //listView
            resource = R.layout.gridview_item_list;
        } else if (viewType == ConstDataUtil.TYPE_GRID_VIEW) {
            //gridView
            resource = R.layout.gridview_item;
        } else {
            resource = R.layout.gridview_item;
        }

        View view = mInflater.inflate(resource, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        DUConfigXmlFile.Component component = mComponents.get(position);
        if (mIsAtlasMode) {
            getIcon(holder.mApkIcon, component.getIcon());
        } else {
            Drawable drawable = getIcon(component.getPackageName(), component.getIcon());
            if (drawable != null) {
                holder.mApkIcon.setImageDrawable(drawable);
            }
        }

        holder.mApkName.setText(component.getName());
        int count = component.getCount();
        if (count <= 0) {
            holder.mUnreadCount.setVisibility(View.INVISIBLE);
        } else {
            holder.mUnreadCount.setVisibility(View.VISIBLE);
            holder.mUnreadCount.setText(String.valueOf(count));
        }

        holder.mApkItem.setTag(component);
        holder.mApkItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object object = v.getTag();
                if (object instanceof DUConfigXmlFile.Component) {
                    DUConfigXmlFile.Component component = (DUConfigXmlFile.Component) object;
                    LogUtil.i(TAG, "FunctionKey: " + component.getFunctionKey());

                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(component);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mComponents.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        LogUtil.i(TAG, fromPosition + "-------------->" + toPosition);
        moveItems(fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    private void moveItems(int fromPosition, int toPosition) {
        if (fromPosition <= toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                DUConfigXmlFile.Component fromComponent = mComponents.get(i);
                DUConfigXmlFile.Component toComponent = mComponents.get(i + 1);
                LogUtil.i(TAG, "<1>fromComponent: " + fromComponent.getName() + ", order: " + fromComponent.getOrder()
                        + "--->toPosition: " + toComponent.getName() + ", order: " + toComponent.getOrder());
                int order = fromComponent.getOrder();
                fromComponent.setOrder(toComponent.getOrder());
                toComponent.setOrder(order);
                LogUtil.i(TAG, "<2>fromComponent: " + fromComponent.getName() + ", order: " + fromComponent.getOrder()
                        + "--->toPosition: " + toComponent.getName() + ", order: " + toComponent.getOrder());
                Collections.swap(mComponents, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                DUConfigXmlFile.Component fromComponent = mComponents.get(i);
                DUConfigXmlFile.Component toComponent = mComponents.get(i - 1);
                LogUtil.i(TAG, "<1>fromComponent: " + fromComponent.getName() + ", order: " + fromComponent.getOrder()
                        + "--->toPosition: " + toComponent.getName() + ", order: " + toComponent.getOrder());
                int order = fromComponent.getOrder();
                fromComponent.setOrder(toComponent.getOrder());
                toComponent.setOrder(order);
                LogUtil.i(TAG, "<2>fromComponent: " + fromComponent.getName() + ", order: " + fromComponent.getOrder()
                        + "--->toPosition: " + toComponent.getName() + ", order: " + toComponent.getOrder());
                Collections.swap(mComponents, i, i - 1);
            }
        }

        if (mOnDataChangedListener != null) {
            mOnDataChangedListener.OnDataChanged();
        }
    }

    @Override
    public void onItemDismiss(int position) {
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder
            implements ItemTouchHelperViewHolder {
        private ViewGroup mApkItem;
        private ImageView mApkIcon;
        private TextView mApkName;
        private TextView mUnreadCount;

        public ItemViewHolder(View itemView) {
            super(itemView);

            mApkItem = (ViewGroup) itemView.findViewById(R.id.apk_item);
            mApkIcon = (ImageView) itemView.findViewById(R.id.apk_icon);
            mApkName = (TextView) itemView.findViewById(R.id.apk_name);
            mUnreadCount = (TextView) itemView.findViewById(R.id.tv_unread_count);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    private void getIcon(ImageView imageView, String icon) {
        if (!TextUtil.isNullOrEmpty(mIconFolder)) {
            File file = new File(mIconFolder, icon + ".png");
            if (file.exists()) {
                Picasso.with(mContext).load(file).resize(128, 128).placeholder(R.mipmap.ic_placeholder).into(imageView);
                return;
            }
        }

        Drawable drawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = mContext.getResources().getDrawable(R.mipmap.ic_launcher, null);
        } else {
            drawable = mContext.getResources().getDrawable(R.mipmap.ic_launcher);
        }

        if (drawable != null) {
            imageView.setImageDrawable(drawable);
        }
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
        } catch (Exception e) {
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
}
