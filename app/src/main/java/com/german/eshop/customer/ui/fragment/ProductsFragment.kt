package com.german.eshop.customer.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.german.eshop.customer.databinding.FragmentProductsBinding
import com.german.eshop.customer.model.Department
import com.german.eshop.customer.ui.adapter.ProductAdapter
import com.german.eshop.customer.ui.viewmodel.MainViewModel
import com.german.eshop.customer.utils.FirebaseLinearLayoutManager
import com.german.eshop.customer.utils.ProductPagingSource
import com.google.android.material.chip.Chip
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private lateinit var binding: FragmentProductsBinding
    private val viewModel: MainViewModel by viewModels()
    private val adapter: ProductAdapter = ProductAdapter()

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

        binding.productsList.layoutManager = FirebaseLinearLayoutManager(requireActivity(), 2)
//        adapter.setHasStableIds(true)
        binding.productsList.adapter = adapter
        lifecycleScope.launch {
            Pager(
                PagingConfig(
                    pageSize = 10
                )
            ) {
                ProductPagingSource(FirebaseFirestore.getInstance()
                    .collection("products")
                    .limit(10.toLong()))
            }.flow.cachedIn(lifecycleScope).collectLatest {
                adapter.submitData(it)
            }
        }

//        adapter = ProductAdapter(FirestoreRecyclerOptions.Builder<Product>().productOptions(FirebaseFirestore.getInstance().queryProducts()))
//        adapter.setHasStableIds(true)
//        binding.productsList.adapter = adapter
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

    companion object {
        @JvmStatic
        fun newInstance() =
            ProductsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}