package com.zzptc.liuxiaolong.contact.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzptc.liuxiaolong.contact.R;

import com.zzptc.liuxiaolong.contact.Model.CallLogs;

import java.util.List;


/**
 * Created by lxl97 on 2016/1/21.
 */
public class CallLogsAdapter extends BaseAdapter{
    private Context context;
    private List<CallLogs> list;
    public CallLogsAdapter(List<CallLogs> list, Context context){
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
        View view = convertView;
        ViewHolder2 holder;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.call_logs_items,null);
            holder = new ViewHolder2();
            holder.mCallLogs_name = (TextView) view.findViewById(R.id.id_calllogs_name);
            holder.mCallLogs_time = (TextView) view.findViewById(R.id.id_calllogstime);
            holder.mCallLogs_type_img = (ImageView) view.findViewById(R.id.iv_callLogs_type);
            holder.mCallLogs_photo = (ImageView) view.findViewById(R.id.id_calllogs_photo);
            holder.mCallLogs_phone = (TextView) view.findViewById(R.id.tv_calllogs_phone);
            holder.mCallLogs_Logsid = (TextView) view.findViewById(R.id.tv_calllogs_logsid);
            view.setTag(holder);
        }else{
            holder = (ViewHolder2) view.getTag();
        }
        CallLogs logs = list.get(position);

        switch (logs.getType()){
            case 1:
                holder.mCallLogs_type_img.setImageResource(R.mipmap.ic_call_1);
                break;
            case 2:
                holder.mCallLogs_type_img.setImageResource(R.mipmap.ic_call_2);
                break;
            case 3:
                holder.mCallLogs_type_img.setImageResource(R.mipmap.ic_call_3);
        }
        if (logs.getName() == null || "".equals(logs.getName())){
            holder.mCallLogs_name.setText(logs.getNumber());
            holder.mCallLogs_photo.setImageResource(R.mipmap.sx);
        }else{
            holder.mCallLogs_name.setText(logs.getName());
        }
        holder.mCallLogs_time.setText(logs.getTime());
        holder.mCallLogs_photo.setImageBitmap(logs.getPhoto());
        holder.mCallLogs_phone.setText(logs.getNumber());
        holder.mCallLogs_Logsid.setText(logs.getId());
        return view;

    }

    public static class ViewHolder2{
        TextView mCallLogs_name,mCallLogs_time,mCallLogs_phone,mCallLogs_Logsid;
        ImageView mCallLogs_type_img,mCallLogs_photo;

    }
}
