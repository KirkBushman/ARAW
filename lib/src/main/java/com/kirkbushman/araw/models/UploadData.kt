package com.kirkbushman.araw.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class UploadData(

    @Json(name = "acl")
    val acl: String,

    @Json(name = "key")
    val key: String,

    @Json(name = "X-Amz-Credential")
    val xAmzCredential: String,

    @Json(name = "X-Amz-Algorithm")
    val xAmzAlgorithm: String,

    @Json(name = "X-Amz-Date")
    val xAmzDate: String,

    @Json(name = "success_action_status")
    val successActionStatus: String,

    @Json(name = "content-type")
    val contentType: String,

    @Json(name = "x-amz-storage-class")
    val xAmzStorageClass: String,

    @Json(name = "x-amz-meta-ext")
    val xAmzMetaExt: String,

    @Json(name = "policy")
    val policy: String,

    @Json(name = "X-Amz-Signature")
    val xAmzSignature: String,

    @Json(name = "x-amz-security-token")
    val xAmzSecurityToken: String

) : Parcelable
