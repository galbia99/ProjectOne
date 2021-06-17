package com.example.myapplication.ui.`2player`

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private lateinit var galleryViewModel: SecondViewModel
    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProvider(this).get(SecondViewModel::class.java)

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //val textView: TextView = binding.textGallery
        //galleryViewModel.text.observe(viewLifecycleOwner, Observer {
        //    textView.text = it
        //})
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.plus_button1).setOnClickListener {
            plusMe1(view)
        }
        view.findViewById<Button>(R.id.less_button1).setOnClickListener {
            lessMe1(view)
        }
        view.findViewById<Button>(R.id.plus_button2).setOnClickListener {
            plusMe2(view)
        }
        view.findViewById<Button>(R.id.less_button2).setOnClickListener {
            lessMe2(view)
        }
        view.findViewById<Button>(R.id.reset1).setOnClickListener {
            resetMe(view)
        }

    }

    private fun plusMe1(view: View) {
        val showCountTextView = view.findViewById<TextView>(R.id.textview_first2)
        val countString = showCountTextView.text.toString()
        var count = countString.toInt()
        count++
        showCountTextView.text = count.toString()}

    private fun lessMe1(view: View) {
        val showCountTextView = view.findViewById<TextView>(R.id.textview_first2)
        val countString = showCountTextView.text.toString()
        var count = countString.toInt()
        count--
        showCountTextView.text = count.toString()}

    private fun plusMe2(view: View) {
        val showCountTextView = view.findViewById<TextView>(R.id.textview_first22)
        val countString = showCountTextView.text.toString()
        var count = countString.toInt()
        count++
        showCountTextView.text = count.toString()}

    private fun lessMe2(view: View) {
        val showCountTextView = view.findViewById<TextView>(R.id.textview_first22)
        val countString = showCountTextView.text.toString()
        var count = countString.toInt()
        count--
        showCountTextView.text = count.toString()}

    private fun resetMe(view: View) {
        val showCountTextView = view.findViewById<TextView>(R.id.textview_first2)
        val showCountTextView2 = view.findViewById<TextView>(R.id.textview_first22)
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
        showCountTextView.text = count.toString()
        showCountTextView2.text = count.toString()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}