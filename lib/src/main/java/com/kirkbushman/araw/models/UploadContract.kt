package com.kirkbushman.araw.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class UploadContract(

    @Json(name = "args")
    val args: UploadContractArgs,

    @Json(name = "assets")
    val assets: UploadContractAssets

) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class UploadContractArgs(

    @Json(name = "action")
    val action: String,

    @Json(name = "fields")
    val fields: List<UploadContractFields>

) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class UploadContractFields(

    @Json(name = "name")
    val name: String,

    @Json(name = "value")
    val value: String

) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class UploadContractAssets(

    @Json(name = "asset_id")
    val assetId: String,

    @Json(name = "processing_state")
    val processingState: String,

    @Json(name = "payload")
    val payload: UploadContractPayload,

    @Json(name = "websocket_url")
    val websocketUrl: String

) : Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class UploadContractPayload(

    @Json(name = "filename")
    val filename: String

) : Parcelable
