package com.zzptc.liuxiaolong.contact.SQLite;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.provider.ContactsContract;
import android.test.AndroidTestCase;
import android.text.TextUtils;


import com.zzptc.liuxiaolong.contact.Model.Contacts;
import com.zzptc.liuxiaolong.contact.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by lxl97 on 2016/1/29.
 */
public class FindAllContacts extends AndroidTestCase{
    private Context context;

    ContentResolver resolver =null;
    private static final String[] phone_projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.PHOTO_ID, ContactsContract.CommonDataKinds.Phone.CONTACT_ID};
    /**联系人显示名称**/
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;

    /**电话号码**/
    private static final int PHONES_NUMBER_INDEX = 1;

    /**头像ID**/
    private static final int PHONES_PHOTO_ID_INDEX = 2;

    /**联系人的ID**/
    private static final int PHONES_CONTACT_ID_INDEX = 3;


    public FindAllContacts(Context context){
        this.context = context;

    }
    private FindCallLogs findphoto;

    //根据姓名查询出id
    public String selectid(String name){
        String id = null;
        resolver = context.getContentResolver();

        Cursor cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,new String[]{ContactsContract.CommonDataKinds.Phone.CONTACT_ID},ContactsContract.Contacts.DISPLAY_NAME + "='"+name+"'",null,null);
        if (cursor.moveToFirst()){
            id = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));

        }
        cursor.close();
        return id;
    }
    //获取所有联系人信息
    public ArrayList<Contacts> testContacts(){
        ArrayList<Contacts> list = new ArrayList<Contacts>();
        resolver = context.getContentResolver();

        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, phone_projection, null, null, null);

        if (phoneCursor != null){
            while (phoneCursor.moveToNext()){
                //手机号码
                String phonenumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                if (TextUtils.isEmpty(phonenumber))
                    continue;

                //姓名
                String contactsname = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
                //id
                String contacts_id = phoneCursor.getString(PHONES_CONTACT_ID_INDEX);
                //图片id
                long photo_id = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);

                Bitmap contactphoto = null;
                Bitmap roundphoto = null;
                if (photo_id >0){
                    Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(contacts_id));
                    InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver,uri);
                    contactphoto = BitmapFactory.decodeStream(input);
                    roundphoto = GetRoundedCornerBitmap(contactphoto);
                }else{
                    roundphoto = null;
                }


                Contacts con = new Contacts();
                con.setName(contactsname);
                con.setPhone(phonenumber);
                con.setPhoto(roundphoto);

                list.add(con);
            }
            phoneCursor.close();
//
        }
        return list;
    }

    //根据姓名查找联系人信息
    public Contacts findcontacts(String name){
        Contacts con = new Contacts();
        resolver = context.getContentResolver();

        Cursor cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,phone_projection,ContactsContract.Contacts.DISPLAY_NAME + "='"+name+"'",null,null);
        if (cursor.moveToFirst()){

                String phonenumber = cursor.getString(PHONES_NUMBER_INDEX);

                //id
                String contacts_id = cursor.getString(PHONES_CONTACT_ID_INDEX);

                /*
             * 查找该联系人的email信息
             */
            Cursor emails = context.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=" + contacts_id,
                    null, null);
            int emailIndex = 0;
            String email = null;
            if(emails.getCount() > 0) {
                emailIndex = emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);
            }
            while(emails.moveToNext()) {
                email = emails.getString(emailIndex);
            }
            emails.close();

            long photo_id = cursor.getLong(2);

            Bitmap contactphoto = null;
        Bitmap photo = null;
        if (photo_id >0){
                Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(contacts_id));
                InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver,uri);
                contactphoto = BitmapFactory.decodeStream(input);
                photo = GetRoundedCornerBitmap(contactphoto);
            }else{
                contactphoto = BitmapFactory.decodeResource(context.getResources(), R.mipmap.sx);
                photo = GetRoundedCornerBitmap(contactphoto);
            }

                con.setPhone(phonenumber);
                con.setPhoto(photo);
                con.setId(contacts_id);
                con.setEmail(email);
        }
        cursor.close();
        return con;
    }

    //生成圆角图片
    public static Bitmap GetRoundedCornerBitmap(Bitmap bitmap) {
        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight()));
            final float roundPx = 50;
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.BLACK);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

            final Rect src = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());

            canvas.drawBitmap(bitmap, src, rect, paint);
            return output;
        } catch (Exception e) {
            return bitmap;
        }
    }


    /*
    添加联系人

     */
    public boolean AddContacts(Contacts contacts){
        Boolean addcon = false;
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
            resolver =  context.getContentResolver();
            ContentValues values = new ContentValues();
            long contactid = ContentUris.parseId(resolver.insert(uri, values));

            uri = Uri.parse("content://com.android.contacts/data");

            //添加姓名
            values.put("raw_contact_id", contactid);
            values.put(ContactsContract.RawContacts.Data.MIMETYPE, "vnd.android.cursor.item/name");
            values.put("data1", contacts.getName());
            resolver.insert(uri, values);
            values.clear();

            //添加电话
            values.put("raw_contact_id", contactid);
            values.put(ContactsContract.RawContacts.Data.MIMETYPE, "vnd.android.cursor.item/phone_v2");
            values.put("data1", contacts.getPhone());
            resolver.insert(uri, values);
            values.clear();

            //添加Email
            values.put("raw_contact_id", contactid);
            values.put(ContactsContract.RawContacts.Data.MIMETYPE, "vnd.android.cursor.item/email_v2");
            values.put("data1", contacts.getEmail());
            resolver.insert(uri, values);
            values.clear();
            //添加头像
        if (contacts.getPhoto() != null) {
            final ByteArrayOutputStream os = new ByteArrayOutputStream();
            contacts.getPhoto().compress(Bitmap.CompressFormat.PNG, 100, os);
            byte[] avatar = os.toByteArray();
            values.put(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, contactid);
            values.put(ContactsContract.RawContacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.CommonDataKinds.Photo.PHOTO, avatar);
            resolver.insert(uri, values);
        }
            addcon = true;
        return addcon;
        }
        //根据姓名查询出id,删除联系人
        public Boolean deleteContacts(String name) {
            boolean del = false;
            resolver = context.getContentResolver();
            Cursor cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone.CONTACT_ID}, ContactsContract.Contacts.DISPLAY_NAME + "='" + name + "'", null, null);
            if (cursor.moveToFirst()) {

                //id
                long contacts_id = Long.parseLong(cursor.getString(0));
                resolver.delete(ContentUris.withAppendedId(ContactsContract.RawContacts.CONTENT_URI, contacts_id), null, null);

            }
            cursor.close();
            del = true;
            return del;
        }

    //修改联系人
    public Boolean modify(Contacts contacts){
        boolean modify = false;
        resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        String id = selectid(contacts.getName());
        Uri uri = Uri.parse("content://com.android.contacts/data");//对data表的所有数据操作


        values.put("data1", contacts.getPhone());
        resolver.update(uri, values, "mimetype=? and raw_contact_id=?", new String[]{"vnd.android.cursor.item/phone_v2",id});
        values.clear();

        values.put("data1", contacts.getEmail());
        resolver.update(uri, values, "mimetype=? and raw_contact_id=?", new String[]{"vnd.android.cursor.item/email_v2", id});
        values.clear();


        modify = true;
        return modify;

    }

        //___________________________
    //搜索联系人
        public ArrayList<Contacts> getFuzzyQueryByName(String key){
            ArrayList<Contacts> list = new ArrayList<>();
            ContentResolver cr = context.getContentResolver();
            String[] projection = {ContactsContract.PhoneLookup.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER};
            Cursor cursor = cr.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    projection,
                    ContactsContract.Contacts.DISPLAY_NAME + " like " + "'%" + key + "%'",
                    null, null);
            while(cursor.moveToNext()){
                String name = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String number = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                findphoto = new FindCallLogs(context);
                Bitmap photo = findphoto.contactshead(name);
                Contacts con = new Contacts();
                con.setName(name);
                con.setPhone(number);
                con.setPhoto(photo);
                list.add(con);
            }
            cursor.close();

            return list;
        }
    /**
     * 根据名字中的某一个字进行模糊查询
     * @param key
     */
    public ArrayList<Contacts> getFuzzyQueryByNumber(String key){
        ArrayList<Contacts> list = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        String[] projection = {ContactsContract.PhoneLookup.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor cursor = cr.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection,
                ContactsContract.CommonDataKinds.Phone.NUMBER+ " like " + "'%" + key + "%'",
                null, null);
        while(cursor.moveToNext()){
            String name = cursor.getString(
                    cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String number = cursor.getString(
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            findphoto = new FindCallLogs(context);

            Bitmap photo = findphoto.contactshead(name);

            Contacts con = new Contacts();
            con.setName(name);
            con.setPhone(number);
            con.setPhoto(photo);
            list.add(con);
        }
        cursor.close();

        return list;
    }
}
