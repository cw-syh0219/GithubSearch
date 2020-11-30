package com.example.githubapi.data.local

import androidx.room.TypeConverter
import com.example.githubapi.data.entites.Owner

class Converter {
    @TypeConverter
    fun stringToOwner(str: String): Owner {
        val split = str.split(",")
        return Owner(split[0], split[1])
    }

    @TypeConverter
    fun ownerToString(owner: Owner): String {
        return "${owner.login},${owner.avatar_url}"
    }
}