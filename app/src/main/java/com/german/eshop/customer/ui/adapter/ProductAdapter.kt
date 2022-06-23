package com.german.eshop.customer.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.german.eshop.customer.databinding.ItemProductBinding
import com.german.eshop.customer.model.Product

class ProductAdapter(options: FirestoreRecyclerOptions<Product>) : FirestoreRecyclerAdapter<Product, ProductAdapter.ProductViewHolder>(options) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder =
        ProductViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int, model: Product) {
        holder.bind(getItem(position))
    }

    override fun getItemId(position: Int): Long {
        val toLong = getItem(position).id?.filter { it.isDigit() }!!.toLong()
        return toLong
    }

    class ProductViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product) {
            binding.name.text = product.name.toString()
        }
    }

}