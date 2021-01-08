package com.gsharemall.client.workmanagertest

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.util.Log

class TestService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    private val pushHandler = Handler()
    private var getMessageThread: Thread? = null

    private val startGetMessageRunnable:Runnable = Runnable {
        Log.i("TestService", "initMessageRunnable 시작")
        getMessageThread?.also {
            it.interrupt()
        }

        // 읽어들일 최종 시간 정보를 획득한다.
        getMessageThread = Thread(getMessageRunnable)
        getMessageThread!!.start()
    }

    private val getMessageRunnable:Runnable = Runnable {
        Log.i("TestService", "getMessageRunnable 시작")

        //반복을 위해 다시 실행
        pushHandler.postDelayed(startGetMessageRunnable, 10 * 1000L)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        pushHandler.postDelayed(startGetMessageRunnable, 0)
        return START_NOT_STICKY
    }

    companion object{
        var instance:TestService? = null
    }
}