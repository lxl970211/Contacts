package com.zzptc.liuxiaolong.contact.Model;

import android.graphics.Bitmap;

/**
 * Created by lxl97 on 2016/1/21.
 */
public class CallLogs {
    private String id;
    private String number;
    private String name;
    private int type;//来电1、拨出2、未接3
    private String time;
    private int count;
    private Bitmap photo;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }
}
