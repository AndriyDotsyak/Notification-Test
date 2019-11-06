package com.notification.screen.base

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.notification.di.Injectable
import javax.inject.Inject

abstract class BaseFragment : Fragment(), Injectable {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var fragmentListener: FragmentListener

    interface FragmentListener {
        fun addFragment()
        fun removeFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        fragmentListener = context as FragmentListener
    }
}