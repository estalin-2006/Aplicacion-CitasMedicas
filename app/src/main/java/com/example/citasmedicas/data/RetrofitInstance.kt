package com.estalin.citasmedicasapp.data

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    // TUS DATOS DE SUPABASE (Los mismos del CMD)
    private const val BASE_URL = "https://gaxilqtvkofiarrnhmfp.supabase.co/rest/v1/"
    private const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImdheGlscXR2a29maWFycm5obWZwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzI3MzQ3OTUsImV4cCI6MjA4ODMxMDc5NX0.deYqod8sTZZSGZ6IT4Uyv_0xjxIdv-qUSTMWmMtcJdY"

    private val client = OkHttpClient.Builder().addInterceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("apikey", SUPABASE_KEY)
            .addHeader("Authorization", "Bearer $SUPABASE_KEY")
            .build()
        chain.proceed(request)
    }.build()

    // Configuramos GSON para que ignore el campo ID al subir (Evita el error que viste en CMD)
    private val gson = GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation()
        .create()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)
    }
}
