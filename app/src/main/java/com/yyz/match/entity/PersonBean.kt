package com.yyz.match.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * description none
 * author ez_yang@qq.com
 * date 2022.5.9 下午 10:47
 * version 1.0
 * update none
 **/
@Entity(tableName = "person")
data class PersonBean(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val parameter: String,
    val table_chinese: String?
) {
    @Ignore
    constructor(name: String, table_chinese: String) : this(null, name, table_chinese)

    @Ignore
    constructor(name: String) : this(null, name, null)
}
