package com.example.githubapi.util.workManager

import android.content.Context
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BackgroundWorker(private val ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    override fun doWork(): Result {
        GlobalScope.launch(Dispatchers.Main) {
            Toast.makeText(ctx, "NETWORK OK", Toast.LENGTH_LONG).show()
        }
        return Result.success()
    }
}