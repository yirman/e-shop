package com.german.eshop.customer.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.german.eshop.customer.databinding.ItemProductNameBinding


class SearchAdapter(context: Context, val list: List<String>): ArrayAdapter<String>(context, 0) {


    override fun getFilter(): Filter {
        return productNameFilter
    }

    override fun getView(position: Int, view: View?, @NonNull parent: ViewGroup): View {
        var convertView: View? = view
        val productName = getItem(position)
        if (convertView == null) {
            val binding = ItemProductNameBinding.inflate(LayoutInflater.from(parent.context))
            binding.productNameTextView.text = productName
            convertView = binding.root
        }
        return convertView
    }

    private val productNameFilter: Filter = object : Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults? {
            val results = FilterResults()
            val suggestionList: MutableList<String> = ArrayList()
            if (constraint == null || constraint.length == 0) {
                suggestionList.addAll(list)
            } else {
                val filterPattern = constraint.toString().lowercase().trim { it <= ' ' }
                for (productName in list) {
                    if (productName.lowercase().contains(filterPattern)) {
                        suggestionList.add(productName)
                    }
                }
            }
            results.values = suggestionList
            results.count = suggestionList.size
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            val productNameList = results.values as List<String>
            if (productNameList != null) {
                clear()
                addAll(productNameList)
            }
            notifyDataSetChanged()
        }

        override fun convertResultToString(resultValue: Any?): CharSequence? {
            return resultValue as String?
        }
    }

}