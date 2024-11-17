package com.dasus.jasootapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pregunta(

    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "pregunta") var pregunta:String,
    @ColumnInfo(name = "respuesta1") var respuesta1:String,
    @ColumnInfo(name = "respuesta2") var respuesta2:String,
    @ColumnInfo(name = "respuesta3")  var respuesta3:String,
    @ColumnInfo(name = "respuesta4") var respuesta4:String,
    @ColumnInfo(name = "respuesta_correcta") var correcta: Int){

}