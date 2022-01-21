package com.geekbrains.december.model.entities

import android.os.Parcelable
import com.geekbrains.december.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataFilms(
    val dataMovie: DataMovie = getDefaultFilms(),
    var poster_path: Int = R.drawable.serials,
    val id: Int = 0,
    var original_title: String = "Не загружено",
    var popularity: Double = 0.0,
    var year: String = "0000",
    var description_about: String = "О фильме",
    var slogan: String = "0000",

    ) : Parcelable

fun getDefaultFilms() = DataMovie(R.drawable.serials,
    500,
    "Reservoir Dogs",
    26.27,
    "1992-09-02",
    "A botched robbery indicates a police informant, and the pressure mounts in the aftermath at a warehouse. Crime begets violence as the survivors",
    "veteran Mr. White, newcomer Mr. Orange, psychopathic parolee Mr. Blonde, bickering weasel Mr. Pink and Nice Guy Eddie -- unravel."
)


fun getWorldFilms() = mutableListOf( // не верно отображает иконку poster_path !!!!

        DataFilms(DataMovie(R.drawable.serials,300,"La Science des rêves",1.0,"2050","Это описание фильма #3", "Слоган #3")),
        DataFilms(DataMovie(R.drawable.films,401,"Garden State",2.0,"2040","Это описание фильма #4","Слоган #4")),
    )

fun getRussianFilms() = mutableListOf(

        DataFilms(DataMovie(R.drawable.serials,330,"The Lost World: Jurassic Park", 4.0, "2020","Это описание фильма #0","Слоган #0")),
        DataFilms(DataMovie(R.drawable.films,331,"Jurassic Park III", 5.0, "2010","Это описание фильма #1","Слоган #1")),
    )
