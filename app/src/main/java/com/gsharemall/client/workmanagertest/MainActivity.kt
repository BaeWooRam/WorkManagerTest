package com.gsharemall.client.workmanagertest

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StreamDownloadTask
import java.io.InputStream
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val storage = FirebaseStorage.getInstance()
        storage.getReference("")
            .storage
            .reference
            .child("")
            .getStream()

        findViewById<TextView>(R.id.text).setOnClickListener {
            val intent = Intent(this, TestActivity::class.java)
            startActivity(intent)
        }

        startPushService()
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
        val intent = Intent(this, TestService::class.java)
        startService(intent)
    }
}