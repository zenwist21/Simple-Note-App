package com.test.noteappkb.core.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DbDao {
    @Query("SELECT * FROM noteData")
    suspend fun getList(): List<NoteEntities>

    @Insert
    suspend fun addNewNote(data: NoteEntities)

    @Delete
    suspend fun deleteNote(data: NoteEntities)

    @Update
    suspend fun updateNote(data: NoteEntities)
}