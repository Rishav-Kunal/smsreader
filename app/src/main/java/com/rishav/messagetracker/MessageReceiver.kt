package com.rishav.messagetracker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.rishav.messagetracker.data.StoredSms
import com.rishav.messagetracker.repository.SmsRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG= "message_receiver"
@AndroidEntryPoint
class MessageReceiver : BroadcastReceiver() {
    @Inject
    lateinit var smsRepository: SmsRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        val bundle : Bundle? = intent!!.extras
        val format = bundle!!.getString("format")
        val pdus = bundle["pdus"] as Array<Any>?

        processAndSaveSms(pdus,format, context!!)
    }
    private fun processAndSaveSms(pdus: Array<Any>?, format: String?, context: Context) = GlobalScope.launch{
        if (pdus != null && format != null) {
            val storedSms = StoredSms(System.currentTimeMillis())
            var msgs: Array<SmsMessage?> = arrayOfNulls(pdus.size)
            for (i in msgs.indices) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    msgs[i] = SmsMessage.createFromPdu(
                        pdus[i] as ByteArray,
                        format
                    )
                }
                else {
                    msgs[i] =
                        SmsMessage.createFromPdu(pdus[i] as ByteArray)
                }
                // Build the message to show.

                storedSms.message = msgs[i]?.messageBody
                storedSms.originatingNumber = msgs[i]?.originatingAddress
                Log.d(TAG, "sms time - ${storedSms.timeInMillis}, sender ${storedSms.originatingNumber}, message - ${storedSms.message}")

            }
            publishNotification(sms = storedSms,context)
            smsRepository.saveSms(storedSms)
        }
    }
    private fun publishNotification(sms : StoredSms, context: Context){
        val intent = Intent(context, SmsListActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        var builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(sms.originatingNumber)
            .setContentText(sms.message)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(sms.message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            val notificationId : Int = sms.timeInMillis.toInt()
            notify(notificationId, builder.build())
        }
    }


}