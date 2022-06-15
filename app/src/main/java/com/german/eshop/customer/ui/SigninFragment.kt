package com.german.eshop.customer.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.german.eshop.customer.R
import com.german.eshop.customer.databinding.FragmentSigninBinding


class SigninFragment : Fragment() {

    private lateinit var binding: FragmentSigninBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSigninBinding.inflate(inflater, container, false)
        binding.title.text = "${getString(R.string.app_name).toCharArray()[0]}!"
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SigninFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}