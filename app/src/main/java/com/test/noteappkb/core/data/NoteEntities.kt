package com.test.noteappkb.core.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "noteData")
data class NoteEntities(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo("name")
    val name: String? = null,
    @ColumnInfo("total")
    val total: Long = 0,
    @ColumnInfo("type")
    val type: String? = null,
    @ColumnInfo("date")
    val date: String? = null,
)