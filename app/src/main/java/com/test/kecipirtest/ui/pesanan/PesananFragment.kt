package com.test.kecipirtest.ui.pesanan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.test.kecipirtest.R

class PesananFragment : Fragment() {

    private lateinit var pesananViewModel: PesananViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        pesananViewModel =
                ViewModelProvider(this).get(PesananViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_pesanan, container, false)
        val textView: TextView = root.findViewById(R.id.text_pesanan)
        pesananViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}