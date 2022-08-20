package com.kirkbushman.araw.adapters

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

class PolyJsonAdapterFactory<T>(

    private val baseType: Class<T>,
    private val possibleTypes: Array<Type>,
    private inline val selectType: (label: String, value: Any?) -> Type?
) : JsonAdapter.Factory {

    override fun create(type: Type, annotations: Set<Annotation?>, moshi: Moshi): JsonAdapter<Any>? {
        if (Types.getRawType(type) != baseType || annotations.isNotEmpty()) {
            return null
        }

        val map = HashMap<Type, JsonAdapter<Any?>>()
        possibleTypes.forEach {

            map[it] = moshi.adapter(it)
        }

        return PolyJsonAdapter(map, selectType).nullSafe()
    }

    internal class PolyJsonAdapter(

        private val adaptersMap: Map<Type, JsonAdapter<Any?>>,
        private inline val selectType: (label: String, value: Any?) -> Type?
    ) : JsonAdapter<Any>() {

        override fun fromJson(reader: JsonReader): Any? {
            val peeked = reader.peekJson()
            peeked.setFailOnUnknown(false)

            var type: Type?
            peeked.use {
                type = matchType(it)
            }

            if (type == null) {
                throw JsonDataException("No match found in JSON object!")
            }

            val adapter = adaptersMap[type]
                ?: throw IllegalStateException("Adapter not found!")

            return adapter.fromJson(reader)
        }

        override fun toJson(writer: JsonWriter, value: Any?) {
            val type = value!!.javaClass
            val adapter = adaptersMap[type]
                ?: throw IllegalStateException("Adapter not found!")

            writer.beginObject()
            val flattenToken = writer.beginFlatten()
            adapter.toJson(writer, value)
            writer.endFlatten(flattenToken)
            writer.endObject()
        }

        private fun matchType(reader: JsonReader): Type? {
            reader.beginObject()
            while (reader.hasNext()) {

                val label = reader.nextName()
                val value = reader.readJsonValue()

                val type = selectType(label, value)
                if (type != null) {
                    return type
                }
            }

            return null
        }
    }
}
