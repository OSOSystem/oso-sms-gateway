package de.ososystem.smsgateway

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION != intent?.action) {
            return
        }

        /**
         * A sms can be split into multiple parts when it is too long,
         * therefore we collect them in this step and merge them into one
         */
        val smsCollection =
            Telephony
            .Sms
            .Intents
            .getMessagesFromIntent(intent)

        val phoneNumber =
            smsCollection
            .firstOrNull()
            ?.originatingAddress

        smsCollection.joinToString("") { sms -> sms.messageBody }
            .also { message ->
                val enrichedMessage =
                    "[[<SMS>,<${phoneNumber.orEmpty()}>]]${message}"
                // and forward it to oso-device
                Log.d("gateway", "sending sms")
                ReverseProxy().send(enrichedMessage)
            }
    }
}


