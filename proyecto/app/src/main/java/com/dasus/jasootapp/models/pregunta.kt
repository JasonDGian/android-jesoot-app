package com.dasus.jasootapp.models

data class Pregunta(
    val pregunta:String,
    val respuesta1:String,
    val respuesta2:String,
    val respuesta3:String,
    val respuesta4:String,
    val correcta: Int){


    fun validaRespuesta( respuesta:Int ):Boolean {
        return (respuesta == this.correcta);
    }
}



// 1. añadir dependencias.
// 2. crear modelo dataclass. -> Debe tener ID @PrimaryKey

