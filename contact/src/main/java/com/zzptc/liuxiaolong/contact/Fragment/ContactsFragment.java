package com.zzptc.liuxiaolong.contact.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


import com.zzptc.liuxiaolong.contact.R;

import com.zzptc.liuxiaolong.contact.Adapter.ContactsAdapter;
import com.zzptc.liuxiaolong.contact.AddContacts;
import com.zzptc.liuxiaolong.contact.ContactsDetails_Activity;
import com.zzptc.liuxiaolong.contact.Model.Contacts;
import com.zzptc.liuxiaolong.contact.SQLite.FindAllContacts;
import com.zzptc.liuxiaolong.contact.Search_contacts;
import com.zzptc.liuxiaolong.contact.other.Roundcorner;

import java.util.ArrayList;

/**
 * Created by lxl97 on 2016/1/11.
 */
public class ContactsFragment extends Fragment {
    private ImageButton img_searchcontacts,img_addcontacts,img_setting;
    private ListView lv_conlist;

    private ArrayList<Contacts> list = null;
    private ContactsAdapter adapter;

    private FindAllContacts fc;
    private Roundcorner r;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.contacts_fragment,container,false);
        butcontacts();
        //找到控件
        lv_conlist = (ListView) v.findViewById(R.id.lv_contactslist);
        img_searchcontacts = (ImageButton) v.findViewById(R.id.img_searchcontacts);
        img_addcontacts = (ImageButton) v.findViewById(R.id.img_addcontacts);
        img_setting = (ImageButton) v.findViewById(R.id.img_setting);
        //加载联系人列表
        adapter = new ContactsAdapter(list,getActivity());
        lv_conlist.setAdapter(adapter);
        //联系人列表监听
        lv_conlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv_name = (TextView) view.findViewById(R.id.tv_con_name);

                //姓名
                String name = tv_name.getText().toString();
                /*
                跳转到联系人详情页面
                 */
                Intent intent = new Intent(getActivity(), ContactsDetails_Activity.class);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });

        //搜索按钮监听
        img_searchcontacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Search_contacts.class);
                startActivity(intent);
            }
        });
        final String [] about = {"关于", "版本"};
        img_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setItems(about, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        AlertDialog dialog1 = new AlertDialog.Builder(getActivity())
                                                .setMessage("开发者：刘小龙\n"+"邮箱:lxl970211@outlook.com\n"+"osChina:lxl970211\n")
                                                .setPositiveButton("确定",null)
                                                .create();
                                        dialog1.show();
                                        break;
                                    case 1:
                                        AlertDialog dialog2 = new AlertDialog.Builder(getActivity())
                                                .setMessage("版本号:1.0.1\n"+"修复拨号盘搜索联系人数据叠加问题")
                                                .setPositiveButton("确定",null)
                                                .create();
                                        dialog2.show();
                                        break;
                                }
                            }
                        })
                        .create();
                dialog.show();
            }
        });
        return v;

    }

    @Override
    public void onResume() {
        //添加联系人按钮监听
        img_addcontacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddContacts.class);
                startActivity(intent);
            }
        });
        super.onResume();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onStart() {
        //当删除联系人后跳回本页面刷新联系人列表
        butcontacts();
        adapter = new ContactsAdapter(list,getActivity());

        lv_conlist.setAdapter(adapter);
        super.onStart();
    }


    /*
    读取所有联系人
     */
    public void butcontacts() {
            fc = new FindAllContacts(getActivity());
                list = fc.testContacts();
                for (int i = 0;i<list.size();i++){
                    Contacts con  = list.get(i);
                }
        }
}
