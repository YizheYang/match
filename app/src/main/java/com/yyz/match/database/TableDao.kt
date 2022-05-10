package com.yyz.match.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yyz.match.entity.TableBean

/**
 * description none
 * author ez_yang@qq.com
 * date 2022.5.10 下午 1:16
 * version 1.0
 * update none
 **/
@Dao
interface TableDao {
    @Insert
    fun insert(tableBean: TableBean)

    @Query("SELECT * FROM `table`")
    fun getAllTable():MutableList<TableBean>
}