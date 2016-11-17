package in.srain.cube.views.ptr.util;

import android.util.Log;

/**
 * Created by sinye on 16/11/17
 */

public class DLog {
    private static boolean isDebug = true;

    public static void d(String tag,String content){
        if(isDebug){
            Log.d(tag,content);
        }
    }

    public static void v(String tag,String content){
        if(isDebug){
            Log.v(tag,content);
        }
    }

    public static void e(String tag,String content){
        if(isDebug){
            Log.e(tag,content);
        }
    }

}
