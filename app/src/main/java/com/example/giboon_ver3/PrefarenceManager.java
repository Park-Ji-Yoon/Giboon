package rebuild.com.sharedpreferences;



import android.content.Context;

import android.content.SharedPreferences;



/**

 * 데이터 저장 및 로드 클래스

 */

public class PrefarenceManager {

    public static final String PREFERENCES_NAME = "rebuild_preference";

    private static final String DEFAULT_VALUE_EMAIL = "";
    private static final String DEFAULT_VALUE_PW = "";
    private static final boolean DEFAULT_VALUE_BOOLEAN = false;

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static void setDefaultValueEmail(Context context, String key, String value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static void setDefaultValuePw(Context context, String key, String value) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getDefaultValueEmail(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        String value = prefs.getString(key, DEFAULT_VALUE_EMAIL);
        return value;
    }
    public static String getDefaultValuePw(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        String value = prefs.getString(key, DEFAULT_VALUE_PW);
        return value;
    }
    public static boolean getBoolean(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        boolean value = prefs.getBoolean(key, DEFAULT_VALUE_BOOLEAN);
        return value;
    }




    /**
     * 키 값 삭제
     * @param context
     * @param key
     */
    public static void removeKey(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.remove(key);
        edit.commit();
    }

    /**
     * 모든 저장 데이터 삭제
     * @param context
     */

    public static void clear(Context context) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.clear();
        edit.commit();
    }
}