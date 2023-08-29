package com.sh3h.mainshell.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mainshell.datautil.data.entity.DUConfigXmlFile;
import com.sh3h.mainshell.R;
import com.sh3h.mobileutil.util.TextUtil;

import java.util.List;

/**
 * 设置界面用的
 */
public class ListViewAdapter extends BaseAdapter {
    private List<DUConfigXmlFile.Component> arrayList;
    private Context c;
    private LayoutInflater inflater;

    public ListViewAdapter(List<DUConfigXmlFile.Component> arrayList, Context context) {
        this.arrayList = arrayList;
        this.c = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void setData(List<DUConfigXmlFile.Component> componentList) {
        arrayList = componentList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public DUConfigXmlFile.Component getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.setting_item, null, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.text.setText(TextUtil.getString(getItem(position).getName()));

        if (position == arrayList.size() - 1) {
            LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) viewHolder.cardView.getLayoutParams();
            param.bottomMargin = 20;
            viewHolder.cardView.setLayoutParams(param);
        }

        return convertView;
    }

    private class ViewHolder {
        TextView text;
        CardView cardView;

        public ViewHolder(View convertView) {
            text = (TextView) convertView.findViewById(R.id.text_component_name);
            cardView = (CardView) convertView.findViewById(R.id.fs_cardview);
        }
    }

}

