package com.dasus.jasootapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.dasus.jasootapp.models.Pregunta
import kotlinx.coroutines.flow.Flow

@Dao
interface PreguntaDao {

    @Upsert
    suspend fun upsertPregunta(pregunta: Pregunta)

    @Query("DELETE FROM pregunta WHERE id = :paramId")
    suspend fun deletePreguntaById(paramId: Int)

    @Query(" SELECT * FROM pregunta ORDER BY id ")
    suspend fun loadAllPreguntas(): List<Pregunta>
}