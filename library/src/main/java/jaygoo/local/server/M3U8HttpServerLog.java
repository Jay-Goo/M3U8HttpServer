package jaygoo.local.server;

import android.util.Log;

/**
 * ================================================
 * 作    者：JayGoo
 * 版    本：
 * 创建日期：2017/11/21
 * 描    述: M3U8HttpServer 日志系统
 * ================================================
 */
public class M3U8HttpServerLog {

    private static String TAG = "M3U8HttpServerLog";
    public static boolean isDebugMode = false;

    public static void d(String msg){
        if (isDebugMode) Log.d(TAG, msg);
    }

    public static void e(String msg){
        if (isDebugMode) Log.e(TAG, msg);
    }


}
