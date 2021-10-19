package com.compose.template.db

import androidx.room.TypeConverter
import com.compose.template.models.BuiltBy
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString

class GithubTypeConverter {

    /* List of BuildBy */
    @TypeConverter
    fun fromBuildByToString(buildBy: List<BuiltBy>): String {
        return Json.encodeToString(buildBy)
    }

    @TypeConverter
    fun fromStringToBuildBy(buildBy: String): List<BuiltBy> {
        return Json.decodeFromString(buildBy)
    }
}