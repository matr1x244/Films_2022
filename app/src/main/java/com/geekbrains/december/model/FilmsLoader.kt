package com.geekbrains.december.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.geekbrains.december.model.entities.rest_entities.MovieDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

private const val API_KEY = "1KK4612-HEMM8RX-P3QSGPJ-VR0AQ82"

object FilmsLoader {
    fun loadFilms(search: Long): MovieDTO? {
        try {

            val uri = URL ("https://api.kinopoisk.dev/movie?search=${search}&field=id&token=$API_KEY") // что нужно передать в API


            lateinit var urlConnection: HttpsURLConnection
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.requestMethod = "GET"

                // TOKEN
                //urlConnection.addRequestProperty("token", "1KK4612-HEMM8RX-P3QSGPJ-VR0AQ82")

                urlConnection.readTimeout = 10000
                val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                // преобразование ответа от сервера (JSON) в модель данных (FilmsDTO)
                val lines = if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    getLinesForOld(bufferedReader)
                } else {
                    getLines(bufferedReader)
                }
                return Gson().fromJson(lines, MovieDTO::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                urlConnection.disconnect()
            }
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }

        return null
    }

    private fun getLinesForOld(reader: BufferedReader): String {
        val rawData = StringBuilder(1024)
        var tempVariable: String?

        while (reader.readLine().also { tempVariable = it } != null) {
            rawData.append(tempVariable).append("\n")
        }

        reader.close()
        return rawData.toString()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }
}