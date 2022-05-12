package com.yyz.match.activity

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Button
import com.google.gson.Gson
import com.yyz.match.BuildConfig
import com.yyz.match.Constants
import com.yyz.match.R
import com.yyz.match.base.BaseActivity
import com.yyz.match.entity.UpdateInfoBean
import com.yyz.match.network.RequestByOkhttp
import okhttp3.Call
import okhttp3.Response
import kotlin.concurrent.thread

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
        checkUpdateInfo()
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

    private fun checkUpdateInfo() {
        thread {
            RequestByOkhttp().get(Constants.ip, object : RequestByOkhttp.MyCallBack(this) {
                override fun onResponse(call: Call, response: Response) {
                    super.onResponse(call, response)
                    val myResponse = Gson().fromJson(response.body()?.string(), UpdateInfoBean::class.java)
                    if (myResponse.versionCode > BuildConfig.VERSION_CODE) {
                        update(myResponse)
                    }
                }
            })
        }
    }

    private fun update(updateInfoBean: UpdateInfoBean) {
        runOnUiThread {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("更新")
                .setMessage("有版本${updateInfoBean.version}可以更新，是否更新")
                .setNegativeButton("取消") { dialog, _ -> dialog.dismiss() }
                .setPositiveButton("确定") { _, _ ->
                    (getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(
                        ClipData.newPlainText("label", updateInfoBean.url)
                    )
                    showToast("地址已经复制到剪切板啦")
                }
            builder.create().show()
        }
    }
}