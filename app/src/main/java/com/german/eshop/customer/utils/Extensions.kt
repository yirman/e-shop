package com.german.eshop.customer.utils

import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.Query

fun FirebaseFirestore.queryProducts(): Query = this.collection("products")

inline fun <reified Product> FirestoreRecyclerOptions.Builder<Product>.productOptions(productQuery: Query): FirestoreRecyclerOptions<Product> {
    return setQuery(productQuery, MetadataChanges.INCLUDE, Product::class.java)
        .build()
}