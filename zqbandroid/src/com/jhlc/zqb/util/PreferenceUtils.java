package com.jhlc.zqb.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.jhlc.zqb.ui.ZQBApplication;

/**
 * Created by Administrator on 2015/7/22 0022.
 */
public class PreferenceUtils {

    public final static SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ZQBApplication.getInstance());

    private SharedPreferences.Editor editor = sp.edit();

    private PreferenceUtils() {
    }

    //单例模式 维持应用中只存在一个实例
    private static class SingletonHolder{
        private static PreferenceUtils instance=new PreferenceUtils();
    }

    public static PreferenceUtils getInstance(){
        return SingletonHolder.instance;
    }

    //设置是否登录 标志位
    public void setIsLogin(boolean flag){
        editor.putBoolean("isLogin",flag);
        editor.commit();
    }

    public static Boolean getIsLogin(){
        return sp.getBoolean("isLogin",false);
    }

    //设置是否第一次登录 标志位
    public void setFirstLogin(boolean flag){
        editor.putBoolean("isFirstLogin",flag);
        editor.commit();
    }

    public static Boolean getFirstLogin(){
        return sp.getBoolean("isFirstLogin",false);
    }

    //设置登录用户名
    public void setUserName(String userName){
        editor.putString("userName", userName);
        editor.commit();
    }

    public static String getUserName(){
        return sp.getString("userName", "");
    }

    //设置登录密码
    public void setPassWord(String passWord){
        editor.putString("passWord", passWord);
        editor.commit();
    }

    public static String getPassWord(){
        return sp.getString("passWord", "");
    }

    //设置用户ID
    public void setUserID(int userID){
        editor.putInt("userId", userID);
        editor.commit();
    }

    //0为默认值 没有数据
    public static int getUserId(){
        return sp.getInt("userId", 0);
    }

    //设置登录用户名
    public void setUserHeadImage(String headImage){
        editor.putString("headImage", headImage);
        editor.commit();
    }

    public static String getUserHeadImage(){
        return sp.getString("headImage", "");
    }

    public boolean clear() {
        editor.clear();
        return editor.commit();
    }


}
