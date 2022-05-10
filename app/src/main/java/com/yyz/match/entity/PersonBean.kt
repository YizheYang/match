package com.yyz.match.entity

/**
 * description none
 * author ez_yang@qq.com
 * date 2022.5.9 下午 10:47
 * version 1.0
 * update none
 **/
data class PersonBean(
    val id: Int?,
    val parameter: String
) {
    constructor(name: String) : this(null, name)
}
