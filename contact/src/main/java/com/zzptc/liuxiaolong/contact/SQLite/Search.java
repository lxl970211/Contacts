package com.zzptc.liuxiaolong.contact.SQLite;




import com.zzptc.liuxiaolong.contact.Model.Contacts;

import java.util.ArrayList;


/**
 * Created by lxl97 on 2016/2/21.
 */
public class Search {

    //数据
    public void getmDataSub(ArrayList<Contacts> mDataSubs, ArrayList<Contacts> list2, String data) {
        int length = list2.size();
        for (int i = 0; i < length; i++) {
            if (list2.get(i).getName().contains(data) || list2.get(i).getPhone().contains(data)) {
                Contacts con = new Contacts();
                con.setName(list2.get(i).getName());
                con.setPhone(list2.get(i).getPhone());
                con.setPhoto(list2.get(i).getPhoto());
                mDataSubs.add(con);
            }
        }
    }
}
