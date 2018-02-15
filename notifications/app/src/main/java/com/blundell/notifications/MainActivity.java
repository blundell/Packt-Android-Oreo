package com.blundell.notifications;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String FUNNY_GROUP_ID = "my_group_01";
    private static final String SERIOUS_GROUP_ID = "my_group_02";
    private static final String FUNNY_CHANNEL_ID = "my_channel_01";
    private static final String SERIOUS_CHANNEL_ID = "my_channel_02";
    private static final String SUPER_SERIOUS_CHANNEL_ID = "my_channel_03";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager == null) {
            throw new IllegalStateException("NotificationManager was null, we never expect this to happen. What did you do?");
        }

        // Channels https://developer.android.com/guide/topics/ui/notifiers/notifications.html#ManageChannels
        NotificationChannelGroup funnyGroup = new NotificationChannelGroup(FUNNY_GROUP_ID, "Funny Group");
        NotificationChannelGroup seriousGroup = new NotificationChannelGroup(SERIOUS_GROUP_ID, "Serious Group");

        NotificationChannel funnyChannel = createFunnyNotificationChannel();
        NotificationChannel seriousChannel = createSeriousNotificationChannel();
        NotificationChannel superSeriousChannel = createSuperSeriousNotificationChannel();

        NotificationCompat.Builder funnyNotification = createNotification(FUNNY_CHANNEL_ID, "Hello Funny World!");
        NotificationCompat.Builder seriousNotification = createNotification(SERIOUS_CHANNEL_ID, "Hello Serious World!");
        // Dots https://developer.android.com/guide/topics/ui/notifiers/notifications.html#Badges

        funnyChannel.setShowBadge(true);
        seriousChannel.setShowBadge(false);
        superSeriousChannel.setShowBadge(false);

        funnyNotification.setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL);
        funnyNotification.setNumber(99);

        // Snoozing
        // is transparent to the dev

        // Timeouts https://developer.android.com/reference/android/app/Notification.Builder.html#setTimeoutAfter(long)

        seriousNotification.setTimeoutAfter(TimeUnit.HOURS.toMillis(1));

        // Settings https://developer.android.com/reference/android/app/Notification.Builder.html#setSettingsText(java.lang.CharSequence)

//        ((Notification.Builder) funnyNotification).setSettingsText("Testing 1 2 3");
//        funnyNotification.setSettingsText("");

        // Dismissal https://developer.android.com/reference/android/service/notification/NotificationListenerService.html#onNotificationRemoved(android.service.notification.StatusBarNotification)

        // to monitor for dismissal we register a service in the manifest
        // however the user then needs to give us permission to monitor
        // https://github.com/Chagall/notification-listener-service-example/blob/master/app/src/main/java/com/github/chagall/notificationlistenerexample/MainActivity.java#L146

        // Background color https://developer.android.com/reference/android/app/Notification.Builder.html#setColor(int)
        // You can set and enable a background color for a notification. You should only use this feature in notifications for ongoing tasks which are critical for a user to see at a glance
        funnyNotification.setColor(Color.RED);
        funnyNotification.setColorized(true);
        funnyNotification.setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
                                       .setMediaSession(null)); // doesn't work

        // Messaging style https://developer.android.com/reference/android/app/Notification.MessagingStyle.html

        NotificationCompat.MessagingStyle messagingStyle = new NotificationCompat.MessagingStyle("Tim");
        NotificationCompat.MessagingStyle.Message msg = new NotificationCompat.MessagingStyle.Message("hello msg", System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(3), "your mum");
        NotificationCompat.MessagingStyle.Message msg2 = new NotificationCompat.MessagingStyle.Message("hello ?", System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(2), "your mum");
        NotificationCompat.MessagingStyle.Message msg3 = new NotificationCompat.MessagingStyle.Message("omg reply", System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(1), "your mum");
        messagingStyle.addMessage(msg);
        messagingStyle.addMessage(msg2);
        messagingStyle.addMessage(msg3);
//        messagingStyle.addHistoricMessage()
        seriousNotification.setStyle(messagingStyle);

        notificationManager.createNotificationChannelGroups(Arrays.asList(funnyGroup, seriousGroup));
        notificationManager.createNotificationChannels(Arrays.asList(funnyChannel, seriousChannel, superSeriousChannel));
        notificationManager.notify(FUNNY_CHANNEL_ID.hashCode(), funnyNotification.build());
        notificationManager.notify(SERIOUS_CHANNEL_ID.hashCode(), seriousNotification.build());
    }

    @NonNull
    private NotificationChannel createFunnyNotificationChannel() {
        String name = "funny category";
        String desc = "This category is for your funny group.";
        return createNotificationChannel(name, desc, FUNNY_GROUP_ID, FUNNY_CHANNEL_ID);
    }

    @NonNull
    private NotificationChannel createSeriousNotificationChannel() {
        String name = "serious category";
        String desc = "This category is for your serious group.";
        return createNotificationChannel(name, desc, SERIOUS_GROUP_ID, SERIOUS_CHANNEL_ID);
    }

    @NonNull
    private NotificationChannel createSuperSeriousNotificationChannel() {
        String name = "super serious category";
        String desc = "This category is for your super serious group.";
        return createNotificationChannel(name, desc, SERIOUS_GROUP_ID, SUPER_SERIOUS_CHANNEL_ID);
    }

    @NonNull
    private NotificationChannel createNotificationChannel(CharSequence name, String description, String groupId, String channelId) {
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(channelId, name, importance);
        channel.setDescription(description);
        channel.enableLights(true);
        channel.setLightColor(Color.BLUE);
        channel.enableVibration(true);
        channel.setVibrationPattern(new long[]{300, 200, 100});
        channel.setGroup(groupId);
        return channel;
    }

    @NonNull
    private NotificationCompat.Builder createNotification(String channelId, String contentText) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
            .setSmallIcon(android.R.drawable.ic_notification_clear_all)
            .setContentTitle("My notification")
            .setContentText(contentText);
        Intent resultIntent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        return builder;
    }

    public void onSettingsClick(View view) {
        Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_CHANNEL_ID, FUNNY_CHANNEL_ID);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        startActivity(intent);
    }
}
