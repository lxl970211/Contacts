package com.zzptc.liuxiaolong.contact.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.zzptc.liuxiaolong.contact.R;

import com.zzptc.liuxiaolong.contact.Adapter.DialAdapter;
import com.zzptc.liuxiaolong.contact.Adapter.SearchAdapter;
import com.zzptc.liuxiaolong.contact.AddContacts;
import com.zzptc.liuxiaolong.contact.Model.Contacts;
import com.zzptc.liuxiaolong.contact.Model.Dial;
import com.zzptc.liuxiaolong.contact.SQLite.FindAllContacts;
import com.zzptc.liuxiaolong.contact.SQLite.Search;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxl97 on 2016/1/10.
 */
public class DialFragment extends Fragment {
    private GridView gv_dial;
    private EditText et_phonenumber;
    private ImageButton img_del,img_call,img_dialaddcon;

    private List<Dial> list;
    private DialAdapter adapter;
    private ListView mSearchListView;
    private SearchAdapter searchAdapter;
    Handler handler = new Handler();
    private FindAllContacts find ;
    private ArrayList<Contacts> conlist1 = new ArrayList<Contacts>();
    private ArrayList<Contacts> conlist2 = new ArrayList<Contacts>();
    private String phone ="1";
    private Search search = new Search();
    //拨号盘图片
    private static final int [] dialpic = {R.mipmap.one,R.mipmap.two,R.mipmap.three,R.mipmap.four,R.mipmap.five,R.mipmap.six,R.mipmap.seven,R.mipmap.eight,R.mipmap.nine,R.mipmap.xing,R.mipmap.zore,R.mipmap.jing};

    StringBuilder builder = new StringBuilder();

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.dial_fragment,container,false);
        //找到控件
        et_phonenumber = (EditText) v.findViewById(R.id.et_phonenumber);
        img_del = (ImageButton) v.findViewById(R.id.img_del);
        img_call = (ImageButton) v.findViewById(R.id.img_call);
        img_dialaddcon = (ImageButton) v.findViewById(R.id.img_dialaddcon);
        mSearchListView = (ListView) v.findViewById(R.id.lv_dial_search);
        gv_dial = (GridView) v.findViewById(R.id.gv_dial);

        find = new FindAllContacts(getActivity());
        set_listview_adapter();

        mSearchListView.setVisibility(v.GONE);

        et_phonenumber.setEnabled(false);
        et_phonenumber.setInputType(InputType.TYPE_NULL);
        //加载拨号盘
        adapter = new DialAdapter(list, getActivity());
        gv_dial.setAdapter(adapter);
        //拨号盘监听
        gv_dial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int num = (position + 1);
                if (num == 10) {
                    builder.append("*");
                } else if (num == 11) {
                    builder.append(0);
                } else if (num == 12) {
                    builder.append("#");
                } else {
                    builder.append(num);

                }
                et_phonenumber.setText(builder.toString());
            }
        });
        //删除号码监听
        int numlength = 0;
        img_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (builder.length() > 0) {
                    builder.deleteCharAt(builder.length() - 1);
                    et_phonenumber.setText(builder.toString());
                }
            }
        });
        img_del.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                builder.delete(0, builder.length());
                et_phonenumber.setText(null);
                return true;
            }
        });
        //拨打号码监听
        img_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = et_phonenumber.getText().toString();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + tel));
                startActivity(intent);
            }
        });
        //添加联系人按钮监听，跳转到添加联系人页面
        img_dialaddcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddContacts.class);

                intent.putExtra("phonenumber", et_phonenumber.getText().toString());
                startActivity(intent);
            }
        });

        et_phonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    mSearchListView.setVisibility(v.GONE);
                } else {
                    mSearchListView.setVisibility(v.VISIBLE);
                    handler.post(eChanged);


                }
            }
        });
        mSearchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tel = (TextView) view.findViewById(R.id.tv_search_phonenumber);

                Intent callintent = new Intent();
                callintent.setAction(Intent.ACTION_CALL);
                callintent.setData(Uri.parse("tel:" + tel.getText().toString()));
                startActivity(callintent);
            }
        });



        return v;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {

        System.out.println("DialFragment.onCreate");
        super.onCreate(savedInstanceState);
        list = new ArrayList<Dial>();
            for (int i = 0; i<dialpic.length; i++ ){
                Dial d = new Dial();
                d.setButpic(dialpic[i]);
                list.add(d);

            }

    }




    public void set_listview_adapter(){

        conlist1 = find.getFuzzyQueryByNumber(phone);
        conlist2.addAll(conlist1);

        searchAdapter = new SearchAdapter(conlist1,getActivity());
        mSearchListView.setAdapter(searchAdapter);
    }
    Runnable eChanged = new Runnable() {
        @Override
        public void run() {
            phone = et_phonenumber.getText().toString();

            conlist1.clear();
            getmDataSub(conlist1, phone);
            searchAdapter.notifyDataSetChanged();
        }
    };


    private void getmDataSub(ArrayList<Contacts> mDataSubs, String data)
    {
        int length = conlist2.size();
        for(int i = 0; i < length; i++){
            if(conlist2.get(i).getName().contains(data) || conlist2.get(i).getPhone().contains(data)){
                Contacts con = new Contacts();
                con.setName(conlist2.get(i).getName());
                con.setPhone(conlist2.get(i).getPhone());
                con.setPhoto(conlist2.get(i).getPhoto());
                mDataSubs.add(con);
            }
        }
    }

}
