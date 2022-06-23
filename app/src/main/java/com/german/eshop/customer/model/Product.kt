package com.german.eshop.customer.model

import com.google.firebase.firestore.DocumentId

data class Product(
    @DocumentId var id: String? = null,
    var idCategoria: String? = null,
    var name: HashMap<String, String>? = null,
    var price: Float? = null
)