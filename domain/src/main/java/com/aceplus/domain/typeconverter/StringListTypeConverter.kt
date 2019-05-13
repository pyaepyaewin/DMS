package com.aceplus.domain.typeconverter

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson

class StringListTypeConverter {

    @TypeConverter
    fun toString(list: MutableList<String>): String {
        val gson = Gson()
        return if (list.isEmpty()) {
            ""
        } else
            gson.toJson(list)
    }

    @TypeConverter
    fun toList(data: String): MutableList<String> {
        val gson = Gson()
        return if (data == "") {
            mutableListOf()
        } else {
            val objects = gson.fromJson(data, Array<String>::class.java) as Array<String>
            objects.toMutableList()
        }
    }
}