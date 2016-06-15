package com.zzptc.liuxiaolong.contact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


import com.zzptc.liuxiaolong.contact.Adapter.SearchAdapter;
import com.zzptc.liuxiaolong.contact.Model.Contacts;
import com.zzptc.liuxiaolong.contact.SQLite.FindAllContacts;
import com.zzptc.liuxiaolong.contact.R;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Search_contacts extends Activity {
    private ImageButton mSearchReturn;
    private EditText mSearch;
    private ListView mSearchListView;

    FindAllContacts find = new FindAllContacts(this);
    private SearchAdapter adapter;

    private ArrayList<Contacts> list = new ArrayList<Contacts>();
    private ArrayList<Contacts> list2 = new ArrayList<Contacts>();
    Handler myhander = new Handler();
    String phone = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search_contacts);


        init();
        initlisten();
        set_listview_adapter();
    }


    public void init(){
        mSearchReturn = (ImageButton) findViewById(R.id.search_return);
        mSearch = (EditText) findViewById(R.id.et_search);
        mSearchListView = (ListView) findViewById(R.id.search_list);

    }


    public void set_listview_adapter(){

        Pattern p = Pattern.compile("[0-9]*");
                Matcher m = p.matcher(phone);
                if (m.matches()){
                    list = find.getFuzzyQueryByNumber(phone);
                    list2.addAll(list);

                }
        p = Pattern.compile("[\u4e00-\u9fa5]");
                m = p.matcher(phone);
                if (m.matches()){
                    list = find.getFuzzyQueryByName(phone);
                    list2.addAll(list);
                }

        adapter = new SearchAdapter(list,this);
        mSearchListView.setAdapter(adapter);
    }
    public void initlisten(){

        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                myhander.post(eChanged);


            }

        });

        mSearchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView name = (TextView) view.findViewById(R.id.tv_search_name);
                Intent intent = new Intent(Search_contacts.this,ContactsDetails_Activity.class);
                intent.putExtra("name", name.getText().toString());
                startActivity(intent);
            }
        });


        mSearchReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    Runnable eChanged = new Runnable() {
        @Override
        public void run() {
            phone = mSearch.getText().toString();

            list.clear();
            getmDataSub(list,phone);
            adapter.notifyDataSetChanged();
        }
    };
    private void getmDataSub(ArrayList<Contacts> mDataSubs, String data)
    {
        int length = list2.size();
        for(int i = 0; i < length; i++){
            if(list2.get(i).getName().contains(data) || list2.get(i).getPhone().contains(data)){
                Contacts con = new Contacts();
                con.setName(list2.get(i).getName());
                con.setPhone(list2.get(i).getPhone());
                con.setPhoto(list2.get(i).getPhoto());
                mDataSubs.add(con);
            }
        }
    }


}
