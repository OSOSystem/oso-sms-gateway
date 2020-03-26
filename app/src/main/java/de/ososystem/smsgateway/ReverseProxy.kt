package de.ososystem.smsgateway

import android.os.AsyncTask
import android.util.Log
import org.zeromq.SocketType
import org.zeromq.ZContext
import org.zeromq.ZMQ

class ReverseProxy : AsyncTask<String, Unit, Unit>() {
    /**
     * Wrapper which ends up calling [doInBackground]
     */
    fun send(message: String) {
        execute(message)
    }

    override fun doInBackground(vararg params: String?) {
        ZContext().use { context ->
            Log.d("gateway", "zeromq")
            val forwarder: ZMQ.Socket = context.createSocket(SocketType.REQ)
            // forwards call to the emulator for now
            forwarder.connect( "tcp://10.0.2.2:5556")
            forwarder.send(params[0])
            val reply = forwarder.recvStr()
            forwarder.close()
            println(reply)
        }
    }
}