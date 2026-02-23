package com.estalin.citasmedicasapp.data

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import com.estalin.citasmedicasapp.data.entity.UsuarioEntity

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<Map<String, Any>>

    @POST("login")
    suspend fun login(
        @Body credentials: Map<String, String>
    ): UsuarioEntity
}