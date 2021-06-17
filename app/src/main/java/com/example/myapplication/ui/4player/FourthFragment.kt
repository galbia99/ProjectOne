package com.example.myapplication.ui.`4player`

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFourthBinding
import com.example.myapplication.databinding.FragmentThirdBinding


class FourthFragment : Fragment() {

    private lateinit var fourthViewModel: FourthViewModel
    private var _binding: FragmentFourthBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fourthViewModel =
            ViewModelProvider(this).get(FourthViewModel::class.java)

        _binding = FragmentFourthBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // val textView: TextView = binding.textSlideshow
        // slideshowViewModel.text.observe(viewLifecycleOwner, Observer {
        //    textView.text = it
        //})
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.plus_button4).setOnClickListener {
            plusMe1(view)
        }
        view.findViewById<Button>(R.id.less_button4).setOnClickListener {
            lessMe1(view)
        }
        view.findViewById<Button>(R.id.plus_button444).setOnClickListener {
            plusMe2(view)
        }
        view.findViewById<Button>(R.id.less_button444).setOnClickListener {
            lessMe2(view)
        }
        view.findViewById<Button>(R.id.plus_button44).setOnClickListener {
            plusMe3(view)
        }
        view.findViewById<Button>(R.id.less_button44).setOnClickListener {
            lessMe3(view)
        }
        view.findViewById<Button>(R.id.plus_button4444).setOnClickListener {
            plusMe4(view)
        }
        view.findViewById<Button>(R.id.less_button4444).setOnClickListener {
            lessMe4(view)
        }
        view.findViewById<Button>(R.id.reset1).setOnClickListener {
            resetMe(view)
        }

    }

    private fun plusMe1(view: View) {
        val showCountTextView = view.findViewById<TextView>(R.id.textview_first4)
        val countString = showCountTextView.text.toString()
        var count = countString.toInt()
        count++
        showCountTextView.text = count.toString()}

    private fun lessMe1(view: View) {
        val showCountTextView = view.findViewById<TextView>(R.id.textview_first4)
        val countString = showCountTextView.text.toString()
        var count = countString.toInt()
        count--
        showCountTextView.text = count.toString()}

    private fun plusMe2(view: View) {
        val showCountTextView = view.findViewById<TextView>(R.id.textview_first44)
        val countString = showCountTextView.text.toString()
        var count = countString.toInt()
        count++
        showCountTextView.text = count.toString()}

    private fun lessMe2(view: View) {
        val showCountTextView = view.findViewById<TextView>(R.id.textview_first44)
        val countString = showCountTextView.text.toString()
        var count = countString.toInt()
        count--
        showCountTextView.text = count.toString()}

    private fun plusMe3(view: View) {
        val showCountTextView = view.findViewById<TextView>(R.id.textview_first444)
        val countString = showCountTextView.text.toString()
        var count = countString.toInt()
        count++
        showCountTextView.text = count.toString()}

    private fun lessMe3(view: View) {
        val showCountTextView = view.findViewById<TextView>(R.id.textview_first444)
        val countString = showCountTextView.text.toString()
        var count = countString.toInt()
        count--
        showCountTextView.text = count.toString()}

    private fun plusMe4(view: View) {
        val showCountTextView = view.findViewById<TextView>(R.id.textview_first4444)
        val countString = showCountTextView.text.toString()
        var count = countString.toInt()
        count++
        showCountTextView.text = count.toString()}

    private fun lessMe4(view: View) {
        val showCountTextView = view.findViewById<TextView>(R.id.textview_first4444)
        val countString = showCountTextView.text.toString()
        var count = countString.toInt()
        count--
        showCountTextView.text = count.toString()}

    private fun resetMe(view: View) {
        val showCountTextView = view.findViewById<TextView>(R.id.textview_first4)
        val showCountTextView2 = view.findViewById<TextView>(R.id.textview_first44)
        val showCountTextView3 = view.findViewById<TextView>(R.id.textview_first444)
        val showCountTextView4 = view.findViewById<TextView>(R.id.textview_first4444)
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
        showCountTextView3.text = count.toString()
        showCountTextView4.text = count.toString()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}