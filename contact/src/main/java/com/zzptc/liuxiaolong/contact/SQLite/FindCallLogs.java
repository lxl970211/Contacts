package com.zzptc.liuxiaolong.contact.SQLite;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.test.AndroidTestCase;


import com.zzptc.liuxiaolong.contact.Model.CallLogs;
import com.zzptc.liuxiaolong.contact.other.Roundcorner;
import com.zzptc.liuxiaolong.contact.R;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by lxl97 on 2016/1/21.
 */
public class FindCallLogs extends AndroidTestCase{
    private Context context;
    public FindCallLogs(Context context){
        this.context = context;

    }
    public ArrayList<CallLogs> select_calllogs(){
        ArrayList<CallLogs> list = new ArrayList<>();

        Uri uri = CallLog.Calls.CONTENT_URI;
        //需要查询的数据
        String [] projection = {
                CallLog.Calls.DATE,CallLog.Calls.NUMBER,CallLog.Calls.TYPE,CallLog.Calls.CACHED_NAME
                ,CallLog.Calls._ID};
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 hh点mm分");
        Date date;
        Roundcorner round = new Roundcorner();
        try {
            ContentResolver resolver = context.getContentResolver();
            Cursor cursor = resolver.query(uri, projection, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
            cursor.moveToFirst();

            for (int i = 0; i<15; i++){
                cursor.moveToPosition(i);

                date = new Date(cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE)));
                String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));

                String id = cursor.getString(cursor.getColumnIndex(CallLog.Calls._ID));

                Bitmap photo = contactshead(name);


                CallLogs log = new CallLogs();
                log.setName(name);
                log.setNumber(number);
                log.setTime(sdf.format(date));
                log.setType(type);
                log.setId(id);
                log.setPhoto(photo);
                list.add(log);
            }
            cursor.close();

        }catch (Exception e){
            e.printStackTrace();

        }

        return list;
    }


    public Boolean deletelogs(String id){
        boolean del = false;
        ContentResolver resolver = context.getContentResolver();
        try {
            resolver.delete(CallLog.Calls.CONTENT_URI,CallLog.Calls._ID+"=?", new String[]{id});
            del = true;
        }catch (SecurityException e){
            e.printStackTrace();
        }
        return true;
    }
        public Bitmap contactshead(String name){
            Roundcorner r = new Roundcorner();

            Bitmap photo = null;
            ContentResolver resolver = context.getContentResolver();
            Cursor cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,new String []{ContactsContract.CommonDataKinds.Phone.PHOTO_ID, ContactsContract.CommonDataKinds.Phone.CONTACT_ID},ContactsContract.Contacts.DISPLAY_NAME + "='"+name+"'",null,null);
            if (cursor.moveToFirst()){

                String id = cursor.getString(1);
                long photo_id = cursor.getLong(0);

                Bitmap contactphoto = null;

                if (photo_id >0){
                    Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(id));
                    InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver,uri);
                    contactphoto = BitmapFactory.decodeStream(input);
                    photo = r.GetRoundedCornerBitmap(contactphoto);
                }else{
                    contactphoto = BitmapFactory.decodeResource(context.getResources(), R.mipmap.sx);
                    photo = r.GetRoundedCornerBitmap(contactphoto);
                }

            }
            cursor.close();

            return photo;
        }


}
