package com.zzptc.liuxiaolong.contact.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.zzptc.liuxiaolong.contact.Model.Contacts;
import com.zzptc.liuxiaolong.contact.R;

import java.util.List;

/**
 * Created by lxl97 on 2016/2/20.
 */
public class SearchAdapter extends BaseAdapter {
    private Context context;
    private List<Contacts> list;
    public SearchAdapter(List<Contacts> list,Context context){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.search_items,null);
            holder = new ViewHolder();
            holder.mName = (TextView) convertView.findViewById(R.id.tv_search_name);
            holder.mPhone = (TextView) convertView.findViewById(R.id.tv_search_phonenumber);
            holder.mPhoto = (ImageView) convertView.findViewById(R.id.iv_search_photo);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Contacts con = list.get(position);
        holder.mName.setText(con.getName());
        holder.mPhone.setText(con.getPhone());
        holder.mPhoto.setImageBitmap(con.getPhoto());
        return convertView;
    }

    public static class ViewHolder{
        TextView mName;
        TextView mPhone;
        ImageView mPhoto;
    }
}
