package com.example.aircraftwar2024.activity;


import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Stack;

/**
 * @brief 饿汉模式
 */
public class ActivityManager {

    public final static String TAG = "ActivityManager";

    private static Stack<Activity> activityStack = new Stack<Activity>();

    // 单例模式界面
    private static ActivityManager m_instance = new ActivityManager();

    private ActivityManager() {
    }

    public static ActivityManager getActivityManager() {
        return m_instance;
    }

    /**
     * @brief 栈顶是当前的 activity
     */
    public void addActivity(Activity activity) {
        activityStack.add(activity);
        Log.v("info", "ActivityManager stack size:" + activityStack.size());
    }

    /**
     * @brief 栈顶是当前的 activity
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * @brief 弹出栈顶的 activity
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }


    /**
     * @brief 关掉 activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * @brief 关掉 activity，接受 class 名字
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * @brief 关掉所有的 activity, 在 exit 中调用
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (activityStack.get(i) != null) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * @brief activity 是一个 stack ，这里连续弹出多个 activity
     */
    public void back2Title() {
        finishActivity(RankListActivity.class);
        finishActivity(OfflineGameActivity.class);
        finishActivity(OfflineActivity.class);
    }

    /**
     * @brief 按下两次 back 以后就会调用这个
     */
    public void exitApp(Context context) {
        try {
            finishAllActivity();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception ex) {
        }
    }

}
