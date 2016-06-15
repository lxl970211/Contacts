package com.zzptc.liuxiaolong.contact.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.zzptc.liuxiaolong.contact.R;

import com.zzptc.liuxiaolong.contact.Model.Dial;

import java.util.List;

/**
 * Created by lxl97 on 2016/1/14.
 */
public class DialAdapter extends BaseAdapter {
    private Context context;
    private List<Dial> list;

    public DialAdapter (List<Dial> list, Context context){
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
        View v = LayoutInflater.from(context).inflate(R.layout.dial_items,null);
        ImageView img_dial = (ImageView) v.findViewById(R.id.img_dial);
        Dial dial = list.get(position);
        img_dial.setImageResource(dial.getButpic());
        return v;
    }
}
