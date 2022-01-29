package com.geekbrains.december.Service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.geekbrains.december.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyForegroundService : Service(), CoroutineScope by MainScope() {

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel() // создаем метод где создается панель уведомлений

        val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
        val stackBuilder: TaskStackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addNextIntentWithParentStack(launchIntent)
        val pendingIntent = stackBuilder.getPendingIntent( // отложенный интент начнет работу когда нажмется на уведомление в панели
            0,
            PendingIntent.FLAG_UPDATE_CURRENT // тут обновляем последнее уведомление как группируем
        )

        /*Тут задаем что будет показываться в уведомлении*/
        val notification = NotificationCompat.Builder(this, "one_alert")
            .setSmallIcon(R.drawable.films)
            //.setCustomContentView(/..) //если есть своя вьюха для поиска уведомлений кастом
            .setContentIntent(pendingIntent)
            .setOngoing(true) // для обновления увемдоления
            .setStyle(NotificationCompat.DecoratedCustomViewStyle()) // стиль уведомления
            .setSound(null) // это уведомление без звука
            .build() // создаем

        startForeground(1, notification) // запускаем

        launch {
            delay(300)
            sendMyBroadcast()
        }
    }

    private fun sendMyBroadcast() {
        val broadcastIntent = Intent()
        broadcastIntent.putExtra(INTENT_SERVICE_DATA, true)
        broadcastIntent.action = INTENT_ACTION_KEY
        sendBroadcast(broadcastIntent)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel("one_alert", "Уведомления от приложения фильмы", NotificationManager.IMPORTANCE_DEFAULT) // задаем режим работы
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel) // создаем уведомление панель
        }
    }

    companion object {
        const val INTENT_ACTION_KEY = "com.geekbrains.december.SERVICE_FINISHED_EVENT"
        const val INTENT_SERVICE_DATA = "INTENT_SERVICE_DATA"

        fun start(context: Context) {
            val usualServiceIntent = Intent(context, MyForegroundService::class.java)
            context.startService(usualServiceIntent)
        }

        fun stop(context: Context) {
            val usualServiceIntent = Intent(context, MyForegroundService::class.java)
            context.stopService(usualServiceIntent)
        }
    }


}