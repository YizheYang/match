package com.yyz.match.entity

/**
 * description none
 * author ez_yang@qq.com
 * date 2022.5.10 下午 9:38
 * version 1.0
 * update none
 **/
data class UpdateInfoBean(
    val versionCode: Int,
    val version: String,
    val url: String,
    val size: Long,
    val md5: String
)
