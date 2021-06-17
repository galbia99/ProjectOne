package com.example.myapplication.ui.`3player`

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentThirdBinding


class ThirdFragment : Fragment() {

    private lateinit var slideshowViewModel: ThirdViewModel
    private var _binding: FragmentThirdBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
            ViewModelProvider(this).get(ThirdViewModel::class.java)

        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // val textView: TextView = binding.textSlideshow
        // slideshowViewModel.text.observe(viewLifecycleOwner, Observer {
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
        view.findViewById<Button>(R.id.plus_button3).setOnClickListener {
            plusMe3(view)
        }
        view.findViewById<Button>(R.id.less_button3).setOnClickListener {
            lessMe3(view)
        }
        view.findViewById<Button>(R.id.reset1).setOnClickListener {
            resetMe(view)
        }
    }

    private fun plusMe1(view: View) {
        val showCountTextView = view.findViewById<TextView>(R.id.textview_first3)
        val countString = showCountTextView.text.toString()
        var count = countString.toInt()
        count++
        showCountTextView.text = count.toString()}

    private fun lessMe1(view: View) {
        val showCountTextView = view.findViewById<TextView>(R.id.textview_first3)
        val countString = showCountTextView.text.toString()
        var count = countString.toInt()
        count--
        showCountTextView.text = count.toString()}

    private fun plusMe2(view: View) {
        val showCountTextView = view.findViewById<TextView>(R.id.textview_first33)
        val countString = showCountTextView.text.toString()
        var count = countString.toInt()
        count++
        showCountTextView.text = count.toString()}

    private fun lessMe2(view: View) {
        val showCountTextView = view.findViewById<TextView>(R.id.textview_first33)
        val countString = showCountTextView.text.toString()
        var count = countString.toInt()
        count--
        showCountTextView.text = count.toString()}

    private fun plusMe3(view: View) {
        val showCountTextView = view.findViewById<TextView>(R.id.textview_first333)
        val countString = showCountTextView.text.toString()
        var count = countString.toInt()
        count++
        showCountTextView.text = count.toString()}

    private fun lessMe3(view: View) {
        val showCountTextView = view.findViewById<TextView>(R.id.textview_first333)
        val countString = showCountTextView.text.toString()
        var count = countString.toInt()
        count--
        showCountTextView.text = count.toString()}

    private fun resetMe(view: View) {
        val showCountTextView = view.findViewById<TextView>(R.id.textview_first3)
        val showCountTextView2 = view.findViewById<TextView>(R.id.textview_first33)
        val showCountTextView3 = view.findViewById<TextView>(R.id.textview_first333)
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
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}