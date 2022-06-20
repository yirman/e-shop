package com.german.eshop.customer.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

abstract class BaseViewModel (
    val firebaseAuth: FirebaseAuth,
    val firestore: FirebaseFirestore
) : ViewModel(){
    val deviceLanguage = Locale.getDefault().language
}