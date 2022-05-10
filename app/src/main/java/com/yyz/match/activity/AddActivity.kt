package com.yyz.match.activity

import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.EditText
import com.yyz.match.Constants
import com.yyz.match.MyDbHelper
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
            val list = processList(s)
            val tableName = table.text.toString()
            insertList(list)
        }
    }

    private fun processList(s: String): MutableList<PersonBean> {
        val list = mutableListOf<PersonBean>()
        for (p in s.split(Regex("\n"))) {
            if (p.isNotEmpty()) {
                val personBean = PersonBean(p)
                list.add(personBean)
            }
        }
        return list
    }

    private fun insertList(list: MutableList<PersonBean>) {
        val table = Constants.table
        val sql = "INSERT INTO $table(parameter) values(?)"
        val helper1 = MyDbHelper(this, table, 1)
        val db1 = helper1.writableDatabase
        val stat = db1.compileStatement(sql)
        for (l in list) {
            stat.bindString(1, l.parameter)
            stat.executeInsert()
        }
//        db1.setTransactionSuccessful()
//        db1.endTransaction()
        db1.close()
    }

    private fun insertList(table: String, list: MutableList<PersonBean>) {
        val sql = "INSERT INTO $table(parameter) values(?)"
        val helper1 = MyDbHelper(this, table, 1)
        val db1 = helper1.writableDatabase
        val stat = db1.compileStatement(sql)
        for (l in list) {
            stat.bindString(1, l.parameter)
            stat.executeInsert()
        }
//        db1.setTransactionSuccessful()
//        db1.endTransaction()
        db1.close()
    }

    private fun getTableList(): MutableList<TableBean> {
        val table = Constants.tableList
        val list = mutableListOf<TableBean>()
        val helper1 = MyDbHelper(this, table, Constants.version)
        val db1 = helper1.readableDatabase
        val cursor = db1.query(table, null, null, null, null, null, null)
        if (cursor.moveToNext()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val chinese = cursor.getString(cursor.getColumnIndex("chinese"))
                list.add(TableBean(id, name, chinese))
            } while (cursor.moveToNext())
        }
        cursor.close()
        Constants.version = list.size
        return list
    }
}