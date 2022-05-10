package com.yyz.match.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * description none
 * author ez_yang@qq.com
 * date 2022.5.10 上午 10:48
 * version 1.0
 * update none
 **/
@Entity(tableName = "table")
data class TableBean(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    var name: String?,
    val chinese: String
) {
    @Ignore
    constructor(chinese: String) : this(null, null, chinese) {
        generateName()
    }

    private fun generateName() {
        name = "table${chinese.hashCode().toString().takeLast(4)}"
    }
}
