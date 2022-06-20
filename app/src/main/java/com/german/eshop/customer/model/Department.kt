package com.german.eshop.customer.model

import com.google.firebase.firestore.DocumentId

data class Department(
    @DocumentId var id: String? = null,
    var name: HashMap<String, String>? = null
)