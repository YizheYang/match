package com.yyz.match.entity

/**
 * description none
 * author ez_yang@qq.com
 * date 2022.5.10 上午 10:48
 * version 1.0
 * update none
 **/
data class TableBean(
    val id: Int?,
    var name: String?,
    val chinese: String
) {
    constructor(chinese: String) : this(null, null, chinese) {
        generateName()
    }

    private fun generateName() {
        name = "table${chinese.hashCode().toString().takeLast(4)}"
    }
}
