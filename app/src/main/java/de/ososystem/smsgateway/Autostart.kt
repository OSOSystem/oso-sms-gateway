package de.ososystem.smsgateway

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class Autostart : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "I'm back!", Toast.LENGTH_SHORT).show()
    }
}