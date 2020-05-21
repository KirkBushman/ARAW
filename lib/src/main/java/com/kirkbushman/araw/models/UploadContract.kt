package com.kirkbushman.araw.models

import android.os.Parcelable
import com.kirkbushman.araw.exceptions.UploadContractFieldMissingException
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

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
            ?: throw UploadContractFieldMissingException("acl")

        val key = args.fields.find { it.name == "key" }
            ?: throw UploadContractFieldMissingException("key")

        val xAmzCredential = args.fields.find { it.name == "X-Amz-Credential" }
            ?: throw UploadContractFieldMissingException("X-Amz-Credential")

        val xAmzAlgorithm = args.fields.find { it.name == "X-Amz-Algorithm" }
            ?: throw UploadContractFieldMissingException("X-Amz-Algorithm")

        val xAmzDate = args.fields.find { it.name == "X-Amz-Date" }
            ?: throw UploadContractFieldMissingException("X-Amz-Date")

        val successActionStatus = args.fields.find { it.name == "success_action_status" }
            ?: throw UploadContractFieldMissingException("success_action_status")

        val contentType = args.fields.find { it.name == "content-type" }
            ?: throw UploadContractFieldMissingException("content-type")

        val xAmzStorageClass = args.fields.find { it.name == "x-amz-storage-class" }
            ?: throw UploadContractFieldMissingException("x-amz-storage-class")

        val xAmzMetaExt = args.fields.find { it.name == "x-amz-meta-ext" }
            ?: throw UploadContractFieldMissingException("x-amz-meta-ext")

        val policy = args.fields.find { it.name == "policy" }
            ?: throw UploadContractFieldMissingException("policy")

        val xAmzSignature = args.fields.find { it.name == "X-Amz-Signature" }
            ?: throw UploadContractFieldMissingException("X-Amz-Signature")

        val xAmzSecurityToken = args.fields.find { it.name == "x-amz-security-token" }
            ?: throw UploadContractFieldMissingException("x-amz-security-token")

        return UploadData(
            acl = acl.value,
            key = key.value,
            xAmzCredential = xAmzCredential.value,
            xAmzAlgorithm = xAmzAlgorithm.value,
            xAmzDate = xAmzDate.value,
            successActionStatus = successActionStatus.value,
            contentType = contentType.value,
            xAmzStorageClass = xAmzStorageClass.value,
            xAmzMetaExt = xAmzMetaExt.value,
            policy = policy.value,
            xAmzSignature = xAmzSignature.value,
            xAmzSecurityToken = xAmzSecurityToken.value
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
