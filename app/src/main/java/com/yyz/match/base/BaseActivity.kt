package com.yyz.match.base

import android.content.pm.ApplicationInfo
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.yyz.match.database.MatchDatabase
import com.yyz.match.widget.MyUncaughtExceptionHandler

/**
 * description none
 * author ez_yang@qq.com
 * date 2022.5.10 上午 12:25
 * version 1.0
 * update none
 **/
abstract class BaseActivity : AppCompatActivity() {
    lateinit var db: MatchDatabase

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE == 0) {
            Thread.setDefaultUncaughtExceptionHandler(MyUncaughtExceptionHandler(this))
        }
        setContentView(getLayoutId())
//        autoAdjustStatusBarText()
        db = Room.databaseBuilder(this, MatchDatabase::class.java, "Store.db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
        initView()
        if (savedInstanceState != null) {
            recoveryData(savedInstanceState)
        }
    }

    open fun initView() {}

    open fun recoveryData(savedInstanceState: Bundle) {}

    private fun autoAdjustStatusBarText() {
        //手机为浅色模主题时，状态栏字体颜色设为黑色，由于状态栏字体颜色默认为白色，所以深色主题不需要适配
        if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK === Configuration.UI_MODE_NIGHT_NO) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.decorView.windowInsetsController!!.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            } else {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }

    fun getTableNameList(): MutableList<String> {
        return db.getTableDao().getAllTable().map { it.chinese }.sorted().toMutableList()
    }

    fun getPersonList(table: String): MutableList<String> {
        return db.getPersonDao().getPersonByTable(table).map { it.parameter }.sorted().toMutableList()
    }

    fun showToast(string: String, length: Int = Toast.LENGTH_SHORT) {
        runOnUiThread {
            Toast.makeText(this, string, length).show()
        }
    }
}