package com.geekbrains.december.ui.films.main

import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.geekbrains.december.R
import com.geekbrains.december.Service.BoundService
import com.geekbrains.december.Service.MyForegroundService
import com.geekbrains.december.databinding.FragmentFilmsBinding
import com.geekbrains.december.model.AppState
import com.geekbrains.december.model.entities.DataFilms
import com.geekbrains.december.model.entities.showSnackBarNoAction
import com.geekbrains.december.ui.films.adapters.FilmsFragmentAdapter
import com.geekbrains.december.ui.films.details.DetailsFragment
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel


class FilmsFragment : Fragment() {

    private val viewModel: FilmsViewModel by viewModel()

    private var _binding: FragmentFilmsBinding? = null
    private val binding get() = _binding!!

    private var adapter: FilmsFragmentAdapter? = null


    /*ТЕСТ Обработка соединения с сервисом*/
    private var isBound = false
    private var boundService: BoundService.ServiceBinder? = null
    /*ТЕСТ Обработка соединения с сервисом*/


         // Обработка соединения с сервисом
        private val boundServiceConnection: ServiceConnection = object : ServiceConnection {
        // При соединении с сервисом
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            boundService = service as BoundService.ServiceBinder
            isBound = boundService != null
            Log.i("SERVICE TEST", "BOUND SERVICE TEST")
            Log.i("SERVICE TEST", "next fibonacci TEST: ${service.nextFibonacci}")
        }

            // При разрыве соединения с сервисом
        override fun onServiceDisconnected(name: ComponentName) {
            isBound = false
            boundService = null
        }
    }
            // Обработка соединения с сервисом


    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?)
    : View {
        _binding = FragmentFilmsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){

            /*Запускаем RecyclerView*/
            FilmsFragmentRecyclerView.adapter = adapter

            /*Для запуска и обвноления данных*/
            viewModel.getLiveData().observe(viewLifecycleOwner, {renderData(it)})
            viewModel.getFilmsFromLoad()

            /*запускаем MyForegroundService*/
            MyForegroundService.start(requireContext())
            /*запускаем MyForegroundService*/
        }
    }

    /*Для MyForegroundService*/
    private val testReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Toast.makeText(context,"FROM SERVICE: ${intent?.getBooleanExtra(MyForegroundService.INTENT_SERVICE_DATA, false)}", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onStart() {
        super.onStart()
        // Обработка соединения с сервисом
        if (!isBound) {
            val bindServiceIntent = Intent(context, BoundService::class.java)
            activity?.bindService(bindServiceIntent, boundServiceConnection, Context.BIND_AUTO_CREATE)
        }
        // Обработка соединения с сервисом
        activity?.registerReceiver(testReceiver, IntentFilter(MyForegroundService.INTENT_ACTION_KEY))
    }

    override fun onStop() {
        activity?.unregisterReceiver(testReceiver)
        // Обработка соединения с сервисом
        if (isBound) {
            activity?.unbindService(boundServiceConnection)
        }
        // Обработка соединения с сервисом

        super.onStop()

    }
    /*MyForegroundService*/


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun renderData(appState: AppState) = with(binding){
        when(appState){
            is AppState.Success -> {
                FilmsProgressBar.visibility = View.GONE // режим работы прогресс бара
                FilmsFragmentRecyclerView.showSnackBarNoAction("Загрузка завершена")
                adapter = FilmsFragmentAdapter(object : OnItemViewClickListener{
                    override fun onItemViewClick(films: DataFilms) {
                        val manager = activity?.supportFragmentManager
                        manager?.let { manager ->
                            val bundle = Bundle().apply {
                                putParcelable(DetailsFragment.BUNDLE_EXTRA, films)
                           }
                            /*я использую. в проекте навхост поэтому детаилс фрагмент нужно открывать так*/
                            requireActivity().findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.action_navigation_films_to_detailsFragment,bundle)

                        /*так просто открываем и передаем в новый фрагмент без использования навхост
                                manager.beginTransaction()
                                .add(R.id.container_films_fragment,DetailsFragment.newInstance(bundle)) // ЧЕРЕЗ ФОН ИДЁТ КЛИКАБЕЛЬНОСТЬ!!! а если убрать белый фон тогда всё видно...
                                .addToBackStack("")
                                .commitAllowingStateLoss()*/
                        }
                    }
                }).apply { setFilms(appState.filmsData) }
                FilmsFragmentRecyclerView.adapter = adapter
            }
            is AppState.Loading -> {
                FilmsProgressBar.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                FilmsProgressBar.visibility = View.GONE
                FilmsFragmentRecyclerView.showSnackBarNoAction("Ошибка загрузки")
                //Функцию расширение используем стандартную
            }
        }
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(films: DataFilms)
    }

    companion object{
        fun newInstance() = FilmsFragment()
    }

}

