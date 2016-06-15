package com.zzptc.liuxiaolong.contact.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.zzptc.liuxiaolong.contact.R;
import com.zzptc.liuxiaolong.contact.Adapter.CallLogsAdapter;
import com.zzptc.liuxiaolong.contact.Model.CallLogs;
import com.zzptc.liuxiaolong.contact.SQLite.FindCallLogs;

import java.util.ArrayList;

/**
 * Created by lxl97 on 2016/1/10.
 */
public class CallLogsFragment extends Fragment {
    private ListView lv_calllogs;
    private FindCallLogs calllogs;
    private CallLogsAdapter adapter;
    private ArrayList<CallLogs> list;
    Handler handler = new Handler();

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.call_logs_fragment,container,false);
        lv_calllogs = (ListView) v.findViewById(R.id.lv_calllogs);

        calllogs = new FindCallLogs(getActivity());

        lv_calllogs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView phone = (TextView) view.findViewById(R.id.tv_calllogs_phone);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phone.getText().toString()));
                startActivity(intent);
            }
        });
        lv_calllogs.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {
                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setMessage("确认删除此记录?")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TextView mCallLogsId = (TextView) view.findViewById(R.id.tv_calllogs_logsid);
                                if (calllogs.deletelogs(mCallLogsId.getText().toString())) {


                                    handler.post(echaged);
                                    Toast.makeText(getActivity(), "已删除", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(getActivity(), "未删除", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .create();
                dialog.show();
                return true;
            }
        });
        return v;
    }

    Runnable echaged = new Runnable() {
        @Override
        public void run() {
            list = calllogs.select_calllogs();
            adapter = new CallLogsAdapter(list,getActivity());
            lv_calllogs.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    };
    @Override
    public void onResume() {

        list = calllogs.select_calllogs();

        adapter = new CallLogsAdapter(list,getActivity());
        lv_calllogs.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        super.onResume();
    }


}
