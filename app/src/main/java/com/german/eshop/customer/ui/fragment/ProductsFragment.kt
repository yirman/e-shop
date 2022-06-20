package com.german.eshop.customer.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.german.eshop.customer.databinding.FragmentProductsBinding
import com.german.eshop.customer.model.Department
import com.german.eshop.customer.ui.viewmodel.MainViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private lateinit var binding: FragmentProductsBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        viewModel.departments.observe(viewLifecycleOwner){ departments ->
            buildDepartmentList(departments)
        }
        return binding.root
    }

    private fun buildDepartmentList(departments: List<Department>){
        departments.forEach { department ->
            Chip(requireContext()).apply {
                text = department.name?.get(viewModel.deviceLanguage)
                tag = department.id
                binding.departments.addView(this)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ProductsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}