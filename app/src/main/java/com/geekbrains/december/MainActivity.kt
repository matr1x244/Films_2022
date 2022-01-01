package com.geekbrains.december

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.geekbrains.december.databinding.ActivityMainBinding
import com.geekbrains.december.ui.films.FilmsFragment
import com.geekbrains.december.ui.setting.SettingFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_films,
                R.id.navigation_serials,
                R.id.navigation_setting
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        /*Не могу понять как при нажатии открыть новый фрагмент, но при нажатии назад ИЛИ по навигации меню фрагменты удалены*/
        val btnNext: Button = findViewById(R.id.btnTest)

        btnNext.setOnClickListener {


            val nextFragment = SettingFragment()
            val thisFragment = FilmsFragment()

            supportFragmentManager
                .beginTransaction()
                .detach(thisFragment)
                //.add(R.id.container_main, nextFragment)
                //.replace(R.id.container_main, nextFragment) // если тут "R.id.container_main" - тогда при нажатии заменяем на nextFragment - тогда как вернутся обратно??
                .replace(R.id.container_films_fragment, nextFragment) // если тут "R.id.container_films_fragment" - тогда при нажатии накладывается поверх nextFragment - в таком случае это убрать?? а так же когда переходаишь например во вкладку сериалы и возвращаешся обратно в фильмы прожимаешь опять кнопку вылетает и ошибка: (/**/)
                .commit()

           /* val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)*/


            Toast.makeText(this, "Запускаем: 123", Toast.LENGTH_SHORT).show()

        }
        /*Не могу понять как при нажатии открыть новый фрагмент, но при нажатии назад ИЛИ по навигации меню фрагменты удалены*/

    }
}




