package com.notification.screen.main.fragment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.notification.R
import com.notification.screen.base.BaseFragment
import com.notification.screen.main.MainActivity
import com.notification.screen.main.MainActivity.Companion.CURRENT_PAGE
import com.notification.screen.main.MainActivity.Companion.START_PAGE_ID
import kotlinx.android.synthetic.main.fragment_notification.*

class NotificationFragment : BaseFragment() {

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "CHANNEL_0"
        const val PAGE_ID_KEY = "PAGE_ID_KEY"

        fun newInstance(pageID: Int) : NotificationFragment {
            val arguments = Bundle()
            arguments.putInt(PAGE_ID_KEY, pageID)

            val fragment = NotificationFragment()
            fragment.arguments = arguments

            return fragment
        }
    }

    private val pageID: Int by lazy { arguments?.getInt(PAGE_ID_KEY) ?: 1 }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        visibleButtonMinus()

        text_view_number_fragment_FN.text = pageID.toString()

        button_minus_FN.setOnClickListener {
            context?.applicationContext?.let { context -> removeNotification(context) }
            fragmentListener.removeFragment()
        }

        button_plus_FN.setOnClickListener {
            fragmentListener.addFragment()
        }

        button_create_notification_FN.setOnClickListener {
            createNotification()
        }
    }

    private fun createNotification() {
        context?.applicationContext?.let {

            val resultIntent = Intent(it, MainActivity::class.java).apply {
                putExtra(CURRENT_PAGE, pageID)
            }

            val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(it).run {
                addNextIntentWithParentStack(resultIntent)
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            }

            val notificationBuilder = NotificationCompat.Builder(it, pageID.toString())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(resources.getString(R.string.fn_title_notification))
                .setContentText(String.format(resources.getString(R.string.fn_text_notification), pageID))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setChannelId(NOTIFICATION_CHANNEL_ID)
                .setContentIntent(resultPendingIntent)
                .setAutoCancel(true)

            notificationChannel(it)

            with(NotificationManagerCompat.from(it)) {
                notify(pageID, notificationBuilder.build())
            }
        }
    }

    private fun notificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Channel_notification"
            val descriptionText = "Notification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun removeNotification(context: Context) {
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(pageID)
    }

    private fun visibleButtonMinus() {
        if (pageID == START_PAGE_ID)
            button_minus_FN.visibility = View.INVISIBLE
    }
}