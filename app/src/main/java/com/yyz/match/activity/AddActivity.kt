package com.yyz.match.activity

import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.yyz.match.R
import com.yyz.match.base.BaseActivity
import com.yyz.match.entity.PersonBean
import com.yyz.match.entity.TableBean

/**
 * description none
 * author ez_yang@qq.com
 * date 2022.5.10 上午 12:33
 * version 1.0
 * update none
 **/
class AddActivity : BaseActivity() {
    override fun getLayoutId() = R.layout.activity_addlist

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, AddActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var input: EditText
    private lateinit var table: EditText
    private lateinit var add: Button

    override fun initView() {
        input = findViewById(R.id.et_add_input)
        table = findViewById(R.id.et_add_tableName)
        add = findViewById(R.id.btn_add)
        setListener()
    }

    private fun setListener() {
        add.setOnClickListener {
            val s = input.text.toString()
            val tableName = table.text.toString()
            if (s.isEmpty() || tableName.isEmpty()) {
                Toast.makeText(this, "请输入完整的参数", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val list = processList(s, tableName)
            db.getPersonDao().insertPersonList(list)
            if (tableName !in getTableNameList()) {
                db.getTableDao().insert(TableBean(tableName))
            }
            Toast.makeText(this, "名单创建完成", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun processList(s: String, table: String): MutableList<PersonBean> {
        val list = mutableListOf<PersonBean>()
        for (p in s.split(Regex("[0-9]*[\\.][ ]*|[\\n][0-9]*[\\.]*[ ]*")).distinct()) {
            if (p.isNotEmpty() && p !in getPersonList(table)) {
                val personBean = PersonBean(p, table)
                list.add(personBean)
            }
        }
        return list
    }

}