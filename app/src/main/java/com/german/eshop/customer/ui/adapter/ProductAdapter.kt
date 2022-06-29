package com.german.eshop.customer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.german.eshop.customer.databinding.ItemProductBinding
import com.german.eshop.customer.model.Product

class ProductAdapter: PagingDataAdapter<Product, ProductAdapter.ProductViewHolder>(Companion) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder = ProductViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    companion object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    class ProductViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product) {
            binding.name.text = product.name.toString()
        }
    }


}