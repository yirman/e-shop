package com.german.eshop.customer.utils

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

class FirebaseLinearLayoutManager(context: Context): LinearLayoutManager(context) {
//    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
//        try{
//            super.onLayoutChildren(recycler, state)
//        }catch (e: Exception){
//            e.printStackTrace()
//        }
//    }

    override fun supportsPredictiveItemAnimations(): Boolean = false
}