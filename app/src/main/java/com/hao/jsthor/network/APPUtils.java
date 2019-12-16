package com.hao.jsthor.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class APPUtils {
    /**
     * 通过网络接口取
     * @return
     */
    public static String getNewMac() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return null;
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                Log.e("title",res1.toString());
                return res1.toString();

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public static boolean isLogin(Context context){
        SharedPreferences jst = context.getSharedPreferences("jst", Context.MODE_PRIVATE);
        Boolean isLogin = jst.getBoolean("isLogin", false);
        Log.e("tag",isLogin+"");
        return isLogin;
    }

    public static String getName(Context context){
        SharedPreferences jst = context.getSharedPreferences("jst", Context.MODE_PRIVATE);
        String userName = jst.getString("userName","");
        Log.e("tag",userName+"");
        return userName;
    }
}
