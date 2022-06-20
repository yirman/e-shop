package com.german.eshop.customer.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.german.eshop.customer.databinding.FragmentProductsBinding
import com.german.eshop.customer.model.Department
import com.german.eshop.customer.ui.viewmodel.MainViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private lateinit var binding: FragmentProductsBinding
    private val viewModel: MainViewModel by viewModels()

    private lateinit var search: String
    private lateinit var idFilter: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idFilter = it.get("idFilter").toString()
            search = it.get("search").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI(){
        if(!isFilterApplied()) {
            viewModel.departments.observe(viewLifecycleOwner) { departments -> buildDepartmentList(departments) }
        }
        else{
            binding.departmentsChipGroup.visibility = View.GONE
        }
    }

    private fun buildDepartmentList(departments: List<Department>){
        departments.forEach { department ->
            val departmentChip = Chip(requireContext()).apply {
                text = department.name?.get(viewModel.deviceLanguage)
                tag = department.id
                setOnClickListener {
                    findNavController().navigate(
                        ProductsFragmentDirections.actionProductFragmentSelf(tag.toString())
                    )
                }
            }
            binding.departmentsChipGroup.addView(departmentChip)
        }
    }

    private fun isFilterApplied() = !idFilter.isEmpty()

    companion object {
        @JvmStatic
        fun newInstance() =
            ProductsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}