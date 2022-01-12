package com.geekbrains.december.ui.serials

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.geekbrains.december.databinding.FragmentSerialsBinding


class SerialsFragment : Fragment(){

    private lateinit var serialsViewModel: SerialsViewModel

    private var _binding: FragmentSerialsBinding? = null  // надо более подробно разобраться страница 16 методички № 2
    private val binding get() = _binding!! // надо более подробно разобраться страница 16 методички № 2


    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?, savedInstanceState: Bundle?): View? {

        serialsViewModel = ViewModelProvider(this).get(SerialsViewModel::class.java)

        _binding = FragmentSerialsBinding.inflate(inflater, container, false)
        val root: View = binding.root // надо более подробно разобраться страница 16 методички № 2


        /*Стандартный метод который забирает даныне из viewmodel с текстом*/
        val textView: TextView = binding.textDashboard
        serialsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


