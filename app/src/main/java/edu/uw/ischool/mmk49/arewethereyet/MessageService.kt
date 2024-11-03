package edu.uw.ischool.mmk49.arewethereyet

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.widget.Toast

class MessageService: Service() {
    private val handler = Handler()
    private var interval: Long = 60000
    private var message: String = ""
    private var phone: String = ""
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Action.START.toString() -> {
                interval = intent.getLongExtra("interval", 60000L)
                message = intent.getStringExtra("message") ?: "Default message"
                phone = intent.getStringExtra("phone") ?: "Default phone"

                start()
            }
            Action.STOP.toString() -> stopSelf()
        }
        return START_STICKY
    }

    private fun start() {
        handler.post(showToastTask)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(showToastTask)
    }

    private val showToastTask = object : Runnable {
        override fun run() {
            Toast.makeText(this@MessageService, getString(R.string.send_toast, phone, message), Toast.LENGTH_SHORT).show()

            handler.postDelayed(this, interval)
        }
    }

    enum class Action {
        START, STOP
    }
}