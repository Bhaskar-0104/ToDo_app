package com.mvvm.todoapp.data.database

import androidx.room.TypeConverter
import com.mvvm.todoapp.data.database.entities.Priority

class Converters {
    @TypeConverter
    fun fromPriority(priority: Priority): String = priority.name

    @TypeConverter
    fun toPriority(value: String): Priority = Priority.valueOf(value)

    @TypeConverter
    fun fromTags(tags: List<String>): String = tags.joinToString(",")

    @TypeConverter
    fun toTags(value: String): List<String> = if (value.isEmpty()) emptyList() else value.split(",")
}