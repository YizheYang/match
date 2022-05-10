package com.yyz.match.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.yyz.match.entity.PersonBean

/**
 * description none
 * author ez_yang@qq.com
 * date 2022.5.10 下午 1:23
 * version 1.0
 * update none
 **/
@Dao
interface PersonDao {
    @Insert
    fun insert(personBean: PersonBean)

    @Insert
    fun insertPersonList(list: MutableList<PersonBean>)

    @Insert
    fun insertPersonList(array: Array<PersonBean>)

    @Query("SELECT * FROM person WHERE table_chinese==:table")
    fun getPersonByTable(table: String): MutableList<PersonBean>

    @Delete
    fun delete(personBean: PersonBean)

    @Delete
    fun delete(mutableList: MutableList<PersonBean>)
}