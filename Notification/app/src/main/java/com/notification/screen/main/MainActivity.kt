package com.notification.screen.main

import android.os.Bundle
import com.notification.R
import com.notification.screen.base.BaseActivity
import com.notification.screen.base.BaseFragment
import com.notification.screen.main.adapter.PagerAdapter
import com.notification.screen.main.fragment.NotificationFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), BaseFragment.FragmentListener {

    companion object {
        const val START_PAGE_ID = 1

        const val CURRENT_PAGE = "CURRENT_PAGE"
        const val COUNT_PAGES = "COUNT_PAGES"
    }

    private val currentPage: Int? by lazy { intent.extras?.getInt(CURRENT_PAGE) }
    lateinit var pagerAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewPager()
        initPages()
    }

    override fun addFragment() {
        pagerAdapter.addFragment(NotificationFragment.newInstance(pagerAdapter.count + 1))
        view_pager_notification_AM.setCurrentItem(pagerAdapter.count - 1, true)
        saveIntPreferences(COUNT_PAGES, pagerAdapter.count)
    }

    override fun removeFragment() {
        view_pager_notification_AM.setCurrentItem(pagerAdapter.count - 2, false)
        pagerAdapter.removeFragment()
        saveIntPreferences(COUNT_PAGES, pagerAdapter.count)
    }

    private fun setupViewPager() {
        pagerAdapter = PagerAdapter(supportFragmentManager)
        pagerAdapter.addFragment(NotificationFragment.newInstance(START_PAGE_ID))

        view_pager_notification_AM.adapter = pagerAdapter
    }

    private fun initPages() {
        currentPage?.let {
            for (page in 2..getIntPreferences(COUNT_PAGES)) {
                pagerAdapter.addFragment(NotificationFragment.newInstance(page))
            }

            view_pager_notification_AM.setCurrentItem(it - 1, false)
        }
    }
}
