package com.yyz.match.activity

import android.widget.Button
import com.yyz.match.R
import com.yyz.match.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun getLayoutId() = R.layout.activity_main
    private lateinit var btn_match: Button
    private lateinit var btn_setting: Button

    override fun initView() {
        btn_match = findViewById(R.id.btn_matchAc)
        btn_setting = findViewById(R.id.btn_addList)
        setListener()
    }

    private fun setListener() {
        btn_match.setOnClickListener {
            MatchActivity.start(this)
        }

        btn_setting.setOnClickListener {
            AddActivity.start(this)
        }
    }

    //    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView()
//
//
//        val p1 = PersonBean("1")
//        val p2 = PersonBean("2")
//        val helper1 = MyDbHelper(this, "person", 1)
//        val db1 = helper1.writableDatabase
//        val values1 = ContentValues().apply {
//            put("parameter", p1.parameter)
//        }
//        db1.insert("person", null, values1)
////        db1.close()
//
//        val helper2 = MyDbHelper(this, "num", 1)
//        val db2 = helper2.writableDatabase
//        val values2 = ContentValues().apply {
//            put("parameter", p2.parameter)
//        }
//        db2.insert("num", null, values2)
//
//        Toast.makeText(this, "over", Toast.LENGTH_LONG).show()
//    }
}