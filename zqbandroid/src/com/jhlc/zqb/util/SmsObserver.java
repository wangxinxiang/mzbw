package com.jhlc.zqb.util;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.jhlc.zqb.ui.RegistActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by licheng on 18/9/15.
 */
public class SmsObserver extends ContentObserver {
    private Context mContext;
    private Handler mHandler;
    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public SmsObserver(Context context,Handler handler) {
        super(handler);
        this.mContext = context;
        this.mHandler = handler;
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);

        String code = "";

        Log.d("SmsObserver-->",uri.toString());

        if(uri.toString().equals("content://sms/raw")){
            return;
        }

        Uri inboxUri = Uri.parse("content://sms/inbox");
        Cursor c = mContext.getContentResolver().query(inboxUri,null,null,null,"date desc");
        if(c!=null){
            if(c.moveToFirst()){
                String address = c.getString(c.getColumnIndex("address"));
                String body = c.getString(c.getColumnIndex("body"));

                Log.d("SmsObserver-->",address+"  "+body);

                Pattern pattern = Pattern.compile("\\d{4}");
                Matcher matcher = pattern.matcher(body);
                if(matcher.find()){
                    code = matcher.group(0);
                    Log.d("SmsObserver-->",code);
                    mHandler.obtainMessage(RegistActivity.MSG_RECEIVE_CODE,code).sendToTarget();
                }
            }
            c.close();
        }
    }
}
