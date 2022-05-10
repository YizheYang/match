package com.yyz.match.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yyz.match.entity.PersonBean
import com.yyz.match.entity.TableBean

/**
 * description none
 * author ez_yang@qq.com
 * date 2022.5.10 下午 1:28
 * version 1.0
 * update none
 **/
@Database(entities = [PersonBean::class, TableBean::class], version = 1)
abstract class MatchDatabase : RoomDatabase() {
    abstract fun getPersonDao(): PersonDao

    abstract fun getTableDao(): TableDao

}