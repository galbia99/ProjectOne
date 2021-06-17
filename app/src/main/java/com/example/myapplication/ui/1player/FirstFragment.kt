package com.example.myapplication.ui.`1player`

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFirstBinding


class FirstFragment : Fragment() {

    private lateinit var homeViewModel: FirstViewModel
    private var _binding: FragmentFirstBinding? = null

    private fun plusMe(view: View) {
        val showCountTextView = view.findViewById<TextView>(R.id.textview_first)
        val countString = showCountTextView.text.toString()
        var count = countString.toInt()
        count++
        showCountTextView.text = count.toString()}

    private fun lessMe(view: View) {
        val showCountTextView = view.findViewById<TextView>(R.id.textview_first)
        val countString = showCountTextView.text.toString()
        var count = countString.toInt()
        count--
        showCountTextView.text = count.toString()}

    private fun resetMe(view: View) {
        val showCountTextView = view.findViewById<TextView>(R.id.textview_first)
        val countString = showCountTextView.text.toString()
        var count = countString.toInt()
        if(count!=0){
            if (count==10){
                count=20
            }else{
                if(count==20){
                    count=30
                }else{
                    if(count==30){count=40}else{count=0}
                }
            }
        }else{
            count=10
        }
        showCountTextView.text = count.toString()}


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(FirstViewModel::class.java)
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        val root: View = binding.root
       // val textView: TextView = binding.textHome
        //homeViewModel.text.observe(viewLifecycleOwner, Observer {
        //    textView.text = it
        //})
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.plus_button).setOnClickListener {
            plusMe(view)
        }
        view.findViewById<Button>(R.id.less_button).setOnClickListener {
            lessMe(view)
        }
        view.findViewById<Button>(R.id.reset1).setOnClickListener {
            resetMe(view)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}