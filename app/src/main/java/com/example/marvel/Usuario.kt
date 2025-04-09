package com.example.marvel

import android.R.string

object Usuario {
    var nombre: String = ""
    var apellido: String = ""
    var correo: String = ""
    var nacimiento: String = ""
    var password: String = ""
    var  foto: String = ""

    fun crearUsuario(nombre: String, apellido: String, correo: String, nacimiento: String, password: String, foto: String)
    {
        this.nombre = nombre
        this.apellido = apellido
        this.correo = correo
        this.nacimiento = nacimiento
        this.password = password
        this.foto = foto
    }

    fun editarUsuario(nombre: String, apellido: String, correo: String, nacimiento: String, password: String, foto: String)
    {
        this.nombre = nombre
        this.apellido = apellido
        this.correo = correo
        this.nacimiento = nacimiento
        this.password = password
        this.foto = foto
    }

    fun verUsuario():String {

        return "Usuario(nombre='$nombre', apellido='$apellido', correo='$correo', nacimiento='$nacimiento', password='$password', foto='$foto')"
    }

}