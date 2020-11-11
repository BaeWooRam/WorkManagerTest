package com.gsharemall.client.workmanagertest

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.gsharemall.client.workmanagertest.TestService.Companion.instance
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startWorkManager()
    }

    private fun startWorkManager(){
        for (index in 0 until 20) {
            val updateWorker: WorkRequest = OneTimeWorkRequest.Builder(UpdateWorker::class.java)
                .setBackoffCriteria(
                    BackoffPolicy.EXPONENTIAL,
                    OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .build()

            val result = WorkManager.getInstance(this).enqueue(updateWorker).result.get()

            Log.i("result", "success? ${result is Operation.State.SUCCESS}")
        }
    }

    private fun startPushService(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (instance != null) {
                instance!!.stopSelf()
            }
            val intent = Intent(this, TestService::class.java)
            startForegroundService(intent)
        } else {
            if (instance != null) {
                instance!!.stopSelf()
            }
            val intent = Intent(this, TestService::class.java)
            startService(intent)
        }
    }
}