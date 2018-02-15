package com.blundell.notifications;

import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class NotificationListener extends NotificationListenerService {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TUT", "created");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("TUT", "bound");
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Log.d("TUT", "POSTED");
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn, RankingMap rankingMap, int reason) {
        Log.d("TUT", "Notification dismissed. Reason code: " + reason);
    }

}
