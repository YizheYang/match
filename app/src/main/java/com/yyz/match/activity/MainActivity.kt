package com.yyz.match.activity

import android.widget.Button
import com.yyz.match.R
import com.yyz.match.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun getLayoutId() = R.layout.activity_main
    private lateinit var btn_match: Button
    private lateinit var btn_setting: Button
    private lateinit var btn_view: Button

    override fun initView() {
        btn_match = findViewById(R.id.btn_matchAc)
        btn_setting = findViewById(R.id.btn_addList)
        btn_view = findViewById(R.id.btn_viewList)
        setListener()
    }

    private fun setListener() {
        btn_match.setOnClickListener {
            MatchActivity.start(this)
        }

        btn_setting.setOnClickListener {
            AddActivity.start(this)
        }

        btn_view.setOnClickListener {
            ViewActivity.start(this)
        }
    }

}