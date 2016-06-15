package com.zzptc.liuxiaolong.contact.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.zzptc.liuxiaolong.contact.R;

import com.zzptc.liuxiaolong.contact.Model.Contacts;

import java.util.List;

/**
 * Created by lxl97 on 2016/1/19.
 */
public class ContactsAdapter extends BaseAdapter {
    private Context context;
    private List<Contacts> list;
    public ContactsAdapter(List<Contacts> list, Context context){
        this.list = list;
        this.context = context;

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
        View v = convertView;
        ViewHolder holder;
        if (v == null){
            v = LayoutInflater.from(context).inflate(R.layout.contacts_listview_items,null);
            holder = new ViewHolder();
            holder.tv_con_name = (TextView) v.findViewById(R.id.tv_con_name);
            holder.tv_con_phone = (TextView) v.findViewById(R.id.tv_con_phonenumber);
            holder.iv_photo = (ImageView) v.findViewById(R.id.iv_con_photo);

            v.setTag(holder);
        }else{
            holder = (ViewHolder) v.getTag();

        }
        Contacts con = list.get(position);

        if (con != null){

            holder.tv_con_name.setText(con.getName());
            holder.tv_con_phone.setText(con.getPhone());
            holder.iv_photo.setImageBitmap(con.getPhoto());


        }


        return v;
    }

    private static class ViewHolder{
        TextView tv_con_name,tv_con_phone;
        ImageView iv_photo;
    }
}
