package com.geekbrains.december.ui.setting

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.geekbrains.december.databinding.FragmentSettingBinding
import com.geekbrains.december.model.AppState
import org.koin.androidx.viewmodel.ext.android.viewModel

const val datasetKeyCheckBox = "datasetKeyCheckBox"

class SettingFragment : Fragment() {

    private val viewModel: SettingViewModel by viewModel()

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private var isDataCheckBoxSetDefault: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)

        val textView: TextView = binding.textTestSetting
        viewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        loadDataSetSetting()
        checkboxSerials.isChecked = isDataCheckBoxSetDefault
        checkboxSerials.setOnClickListener { changeCheckBoxContent() }

    }

    private fun changeCheckBoxContent() {
        isDataCheckBoxSetDefault = !isDataCheckBoxSetDefault
        saveDataSetToDisk()
    }

    /**
     * Сохраняем значение
     */
    private fun loadDataSetSetting() {
        activity?.let {
            isDataCheckBoxSetDefault = activity
                ?.getPreferences(Context.MODE_PRIVATE)
                ?.getBoolean(datasetKeyCheckBox, true) ?: true
        }
    }


    private fun saveDataSetToDisk() {
        val editor = activity?.getPreferences(Context.MODE_PRIVATE)?.edit()
        editor?.putBoolean(datasetKeyCheckBox, isDataCheckBoxSetDefault)
        editor?.apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

