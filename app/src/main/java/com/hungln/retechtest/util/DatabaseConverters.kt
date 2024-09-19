package com.hungln.retechtest.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hungln.retechtest.data.network.response.Owner

class DatabaseConverters {
    @TypeConverter
    fun fromOwner(owner: Owner?): String? {
        if (owner == null) {
            return null
        }
        return Gson().toJson(owner)
    }

    @TypeConverter
    fun toOwner(ownerString: String?): Owner? {
        if (ownerString == null) {
            return null
        }
        val ownerType = object : TypeToken<Owner>() {}.type
        return Gson().fromJson(ownerString, ownerType)
    }
}