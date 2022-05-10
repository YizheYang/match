package com.yyz.match

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Switch

/**
 * description none
 * author ez_yang@qq.com
 * date 2022.5.9 下午 11:30
 * version 1.0
 * update none
 **/
class MyDbHelper(context: Context, tableName: String, version: Int) :
    SQLiteOpenHelper(context, "Store.db", null, version) {
    private val createList =
        "CREATE TABLE ${Constants.tableList}(id integer primary key autoincrement, name text, chinese text)"
    private val create = "CREATE TABLE $tableName(id integer primary key autoincrement, parameter text)"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createList)
//        db?.execSQL(create)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        db?.execSQL(create)
    }
}