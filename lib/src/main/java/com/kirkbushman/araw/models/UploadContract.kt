package com.kirkbushman.araw.models

import android.os.Parcelable
import com.kirkbushman.araw.exceptions.UploadContractFieldMissingException
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class UploadContract(

    @Json(name = "args")
    val args: UploadContractArgs,

    @Json(name = "asset")
    val assets: UploadContractAssets

) : Parcelable {

    fun toUploadData(): UploadData {

        val acl = args.fields.find { it.name == "acl" }
        val key = args.fields.find { it.name == "key" }
        val xAmzCredential = args.fields.find { it.name == "X-Amz-Credential" }
        val xAmzAlgorithm = args.fields.find { it.name == "X-Amz-Algorithm" }
        val xAmzDate = args.fields.find { it.name == "X-Amz-Date" }
        val successActionStatus = args.fields.find { it.name == "success_action_status" }
        val contentType = args.fields.find { it.name == "content-type" }
        val xAmzStorageClass = args.fields.find { it.name == "x-amz-storage-class" }
        val xAmzMetaExt = args.fields.find { it.name == "x-amz-meta-ext" }
        val policy = args.fields.find { it.name == "policy" }
        val xAmzSignature = args.fields.find { it.name == "X-Amz-Signature" }
        val xAmzSecurityToken = args.fields.find { it.name == "x-amz-security-token" }

        val exception = when {
            acl == null ->
                UploadContractFieldMissingException("acl")
            key == null ->
                UploadContractFieldMissingException("key")
            xAmzCredential == null ->
                UploadContractFieldMissingException("X-Amz-Credential")
            xAmzAlgorithm == null ->
                UploadContractFieldMissingException("X-Amz-Algorithm")
            xAmzDate == null ->
                UploadContractFieldMissingException("X-Amz-Date")
            successActionStatus == null ->
                UploadContractFieldMissingException("success_action_status")
            contentType == null ->
                UploadContractFieldMissingException("content-type")
            xAmzStorageClass == null ->
                UploadContractFieldMissingException("x-amz-storage-class")
            xAmzMetaExt == null ->
                UploadContractFieldMissingException("x-amz-meta-ext")
            policy == null ->
                UploadContractFieldMissingException("policy")
            xAmzSignature == null ->
                UploadContractFieldMissingException("X-Amz-Signature")
            xAmzSecurityToken == null ->
                UploadContractFieldMissingException("x-amz-security-token")
            else -> null
        }

        if (exception != null) {
            throw exception
        }

        return UploadData(
            acl = acl!!.value,
            key = key!!.value,
            xAmzCredential = xAmzCredential!!.value,
            xAmzAlgorithm = xAmzAlgorithm!!.value,
            xAmzDate = xAmzDate!!.value,
            successActionStatus = successActionStatus!!.value,
            contentType = contentType!!.value,
            xAmzStorageClass = xAmzStorageClass!!.value,
            xAmzMetaExt = xAmzMetaExt!!.value,
            policy = policy!!.value,
            xAmzSignature = xAmzSignature!!.value,
            xAmzSecurityToken = xAmzSecurityToken!!.value
        )
    }
}

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

    @Json(name = "filepath")
    val filepath: String

) : Parcelable
