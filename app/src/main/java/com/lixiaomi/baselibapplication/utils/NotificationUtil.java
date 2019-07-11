package com.lixiaomi.baselibapplication.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.RemoteViews;

import com.lixiaomi.baselib.utils.MiDateUtils;
import com.lixiaomi.baselibapplication.R;

import java.util.Date;

/**
 * Description: <br>
 *
 * @author : dell - XiaomiLi<br>
 * Date: 2018-07-23<br>
 * Time: 14:29<br>
 * UpdateDescription：<br>
 */
public class NotificationUtil {

    private static NotificationManager notificationManager = null;

    private static NotificationManager getInstance(Context context) {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    private static String NotificationChannelGroupId = "1";
    private static String NotificationChannelGroupName = "预警推送";
    private static String NotificationChannelId = "2";
    private static String NotificationChannelName = "预警消息";

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void initNotificationChannel(Context context) {
        //构建通知组
        getInstance(context).createNotificationChannelGroup(new NotificationChannelGroup(NotificationChannelGroupId, NotificationChannelGroupName));
        NotificationChannel channel2 = new NotificationChannel(NotificationChannelId, NotificationChannelName, NotificationManager.IMPORTANCE_DEFAULT);
        // 设置通知出现时的闪灯（如果 android 设备支持的话）
        channel2.enableLights(true);
        channel2.setLightColor(Color.GREEN);

        channel2.setGroup(NotificationChannelGroupId);
        getInstance(context).createNotificationChannel(channel2);
    }

    /**
     * 最多显示几条
     */
    private static int NotificationNumber = 3;

    /**
     * 发通知
     *
     * @param title          标题
     * @param content        内容
     * @param ticker         收到消息后在通知栏展示的那一行字
     * @param notificationId id
     * @param pendingIntent
     * @param context
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void sendNotification(String title, String content, String ticker, int notificationId, int icon, PendingIntent pendingIntent, Context context) {
        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            initNotificationChannel(context);
            builder = new Notification.Builder(context, NotificationChannelId);
        } else {
            builder = new Notification.Builder(context);
        }
        builder.setContentIntent(pendingIntent)
                .setSmallIcon(icon)
                .setAutoCancel(true)
                .setTicker(ticker)
                .setVisibility(Notification.VISIBILITY_PRIVATE)
                .setContentTitle(title)
                .setContentText(content);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setNumber(NotificationNumber);
        } else {
            builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        }
        getInstance(context).notify(notificationId, builder.build());
    }

    /**
     * 发通知
     *
     * @param title
     * @param content
     * @param ticker
     * @param notificationId
     * @param icon
     * @param pendingIntent
     * @param context
     */
    public static void sendNotificationMyView(String title, String content, String ticker, int notificationId, int icon, PendingIntent pendingIntent, Context context) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.custom_notification);
        remoteViews.setImageViewResource(R.id.notification_image, icon);
        remoteViews.setTextViewText(R.id.notification_title, title);
        remoteViews.setTextViewText(R.id.notification_text, content);
        remoteViews.setTextViewText(R.id.notification_time, MiDateUtils.dateToString(new Date(), MiDateUtils.HM));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            initNotificationChannel(context);
            final Notification.Builder builder = new Notification.Builder(context, NotificationChannelId);
            builder.setContentIntent(pendingIntent)
                    .setSmallIcon(icon)
                    .setAutoCancel(true)
                    .setTicker(ticker)
                    .setVisibility(Notification.VISIBILITY_PRIVATE)
                    .setCustomContentView(remoteViews)
                    .setNumber(NotificationNumber);
            getInstance(context).notify(notificationId, builder.build());
        } else {
            Notification notification = new Notification(icon, ticker, System.currentTimeMillis());
            notification.contentView = remoteViews;
            notification.contentIntent = pendingIntent;
            notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            notification.visibility = Notification.VISIBILITY_PRIVATE;
            getInstance(context).notify(notificationId, notification);
        }
    }


    /**
     * 清空
     *
     * @param notificationId
     */
    public static void delNotificationById(Context context, int notificationId) {
        getInstance(context).cancel(notificationId);
    }

    /**
     * 清空全部
     *
     * @param context
     */
    public static void delAllNotification(Context context) {
        getInstance(context).cancelAll();
    }


}
