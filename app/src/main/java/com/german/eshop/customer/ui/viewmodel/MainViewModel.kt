package com.german.eshop.customer.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.german.eshop.customer.model.Department
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(firebaseAuth: FirebaseAuth,
                                        firestore: FirebaseFirestore
) : BaseViewModel(firebaseAuth, firestore) {

    private val _departments : MutableLiveData<List<Department>> = MutableLiveData<List<Department>>().apply {
        firestore.collection("departments").addSnapshotListener { value, error ->
            value?.let {
                val departments = value.toObjects(Department::class.java)
                postValue(departments)
            }
        }
    }

    val departments : LiveData<List<Department>> = _departments
}