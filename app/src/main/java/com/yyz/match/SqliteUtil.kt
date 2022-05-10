package com.yyz.match

import android.content.Context
import com.yyz.match.entity.PersonBean
import com.yyz.match.entity.TableBean

/**
 * description none
 * author ez_yang@qq.com
 * date 2022.5.10 上午 11:17
 * version 1.0
 * update none
 **/
class SqliteUtil(private val context: Context) {

    init {
        val helper = MyDbHelper(context, Constants.tableList, Constants.version)
        helper.writableDatabase
    }

    fun getTableList(): MutableList<TableBean> {
        val list = mutableListOf<TableBean>()
        val helper = MyDbHelper(context, Constants.tableList, Constants.version)
        val db1 = helper.readableDatabase
        val cursor = db1.query(Constants.tableList, null, null, null, null, null, null)
        if (cursor.moveToNext()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val chinese = cursor.getString(cursor.getColumnIndex("chinese"))
                list.add(TableBean(id, name, chinese))
            } while (cursor.moveToNext())
        }
        cursor.close()
//        Constants.version = list.size
        return list
    }

    fun insertTable(tableBean: TableBean) {
        val sql = "INSERT INTO ${Constants.tableList}(name, chinese) values(?, ?)"
        val helper1 = MyDbHelper(context, Constants.tableList, Constants.version)
        val db1 = helper1.writableDatabase
        val stat = db1.compileStatement(sql)
        stat.bindString(1, tableBean.name)
        stat.bindString(2, tableBean.chinese)
        stat.executeInsert()
        db1.execSQL("CREATE TABLE ${tableBean.name}(id integer primary key autoincrement, parameter text)")
        db1.close()
//        Constants.version++
    }

    fun getPerson(table: String): MutableList<PersonBean> {
        val list = mutableListOf<PersonBean>()
        val helper1 = MyDbHelper(context, table, Constants.version)
        val db1 = helper1.readableDatabase
        val cursor = db1.query(table, null, null, null, null, null, null)
        if (cursor.moveToNext()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val parameter = cursor.getString(cursor.getColumnIndex("parameter"))
                list.add(PersonBean(id, parameter))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    fun insertPerson(list: MutableList<PersonBean>) {
        val table = Constants.table
        val sql = "INSERT INTO $table(parameter) values(?)"
        val helper1 = MyDbHelper(context, table, Constants.version)
        val db1 = helper1.writableDatabase
        val stat = db1.compileStatement(sql)
        for (l in list) {
            stat.bindString(1, l.parameter)
            stat.executeInsert()
        }
        db1.close()
    }

    fun insertPerson(personBean: PersonBean) {
        val table = Constants.table
        val sql = "INSERT INTO $table(parameter) values(?)"
        val helper1 = MyDbHelper(context, table, Constants.version)
        val db1 = helper1.writableDatabase
        val stat = db1.compileStatement(sql)
        stat.bindString(1, personBean.parameter)
        stat.executeInsert()
        db1.close()
    }
}