package com.gsharemall.client.workmanagertest

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlin.random.Random

class UpdateWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        Log.i("UpdateWorker", "update!")

        return when(Random.nextInt(3)){
            0 -> Result.retry()
            1 -> Result.success()
            2 -> Result.failure()
            else -> Result.success()
        }
    }

}