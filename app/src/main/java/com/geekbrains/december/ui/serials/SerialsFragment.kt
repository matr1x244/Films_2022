package com.geekbrains.december.ui.serials

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.geekbrains.december.R
import com.geekbrains.december.databinding.FragmentSerialsBinding
import com.geekbrains.december.model.AppState
import com.geekbrains.december.model.entities.DataFilms
import com.geekbrains.december.model.entities.showSnackBarNoAction
import com.geekbrains.december.ui.adapters.RecyclerViewFragmentAdapter
import com.geekbrains.december.ui.details.DetailsFragment
import com.geekbrains.december.ui.films.films.FilmsFragment
import com.geekbrains.december.ui.setting.datasetKeyCheckBox
import org.koin.androidx.viewmodel.ext.android.viewModel


class SerialsFragment : Fragment() {

    private val viewModel: SerialsViewModel by viewModel()

    private var _binding: FragmentSerialsBinding? = null
    private val binding get() = _binding!!

    private var adapterSerial: RecyclerViewFragmentAdapter? = null
    private var isDataCheckBoxSetDefault = true

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentSerialsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            /*Для запуска и обвноления данных*/
            viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
            viewModel.getSerialFromLoad()

            /*Запускаем RecyclerView*/
            /*Проверяем checkbox на сериалы*/

            loadDataSetCheckBox()
            if (isDataCheckBoxSetDefault) {
                serialsProgressBar.visibility = View.VISIBLE
                serialsFragmentRecyclerView.adapter = adapterSerial
                serialsFragmentRecyclerView.visibility = View.VISIBLE
                containerSerialsFragment.showSnackBarNoAction("Загрузка успешна")
            } else {
                serialsProgressBar.visibility = View.GONE
                serialsFragmentRecyclerView.adapter = adapterSerial
                serialsFragmentRecyclerView.visibility = View.GONE
                containerSerialsFragment.showSnackBarNoAction("Ошибка загрузки")
            }
        }
    }

    // Запускаем проверку checkbox про сериалы
    private fun loadDataSetCheckBox() {
        activity?.let {
             isDataCheckBoxSetDefault = activity
                ?.getPreferences(Context.MODE_PRIVATE)
                ?.getBoolean(datasetKeyCheckBox, true) ?: true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun renderData(appState: AppState) = with(binding) {
            when (appState) {
                is AppState.Success -> {
                    serialsProgressBar.visibility = View.GONE // режим работы прогресс бара
                    //serialsFragmentRecyclerView.showSnackBarNoAction("Загрузка завершена")
                    adapterSerial = RecyclerViewFragmentAdapter(object :
                        FilmsFragment.OnItemViewClickListener {
                        override fun onItemViewClick(films: DataFilms) {
                            val manager = activity?.supportFragmentManager
                            manager?.let { manager ->
                                val bundle = Bundle().apply {
                                    putParcelable(DetailsFragment.BUNDLE_EXTRA, films)
                                }
                                /*я использую. в проекте навхост поэтому детаилс фрагмент нужно открывать так*/
                                requireActivity().findNavController(R.id.nav_host_fragment_activity_main)
                                    .navigate(
                                        R.id.action_navigation_serials_to_detailsFragment, bundle
                                    )
                            }

                        }
                        override fun onDataEnd(from: Int, sizeToRequest: Int) {
                            //
                        }

                    }).apply { setData(appState.filmsData) }
                    serialsFragmentRecyclerView.adapter = adapterSerial
                }
                is AppState.Loading -> {
                    serialsProgressBar.visibility = View.VISIBLE
                }
                is AppState.Error -> {
                    serialsProgressBar.visibility = View.GONE
                    //serialsFragmentRecyclerView.showSnackBarNoAction("Ошибка загрузки")
                }
            }
        }


    companion object{
        fun newInstance() = SerialsFragment()
    }



}


