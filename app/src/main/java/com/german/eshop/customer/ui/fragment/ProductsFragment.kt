package com.german.eshop.customer.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.german.eshop.customer.databinding.FragmentProductsBinding
import com.german.eshop.customer.model.Department
import com.german.eshop.customer.model.Product
import com.german.eshop.customer.ui.adapter.ProductAdapter
import com.german.eshop.customer.ui.viewmodel.MainViewModel
import com.german.eshop.customer.utils.FirebaseLinearLayoutManager
import com.german.eshop.customer.utils.productOptions
import com.german.eshop.customer.utils.queryProducts
import com.google.android.material.chip.Chip
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private lateinit var binding: FragmentProductsBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: ProductAdapter

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
        adapter = ProductAdapter(FirestoreRecyclerOptions.Builder<Product>().productOptions(FirebaseFirestore.getInstance().queryProducts()))
        binding.productsList.layoutManager = FirebaseLinearLayoutManager(requireActivity())
        adapter.setHasStableIds(true)
        binding.productsList.adapter = adapter
//        binding.productsList.itemAnimator = null
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

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
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