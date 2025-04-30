package com.example.app.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.runtime.State
import com.google.firebase.database.FirebaseDatabase

open class AuthViewModel : ViewModel() {
    val auth = FirebaseAuth.getInstance()
    val userName = mutableStateOf("")
    val userEmail = mutableStateOf("")
    val userPassword = mutableStateOf("")
    val authResult = mutableStateOf("")

    private val database = FirebaseDatabase.getInstance()

    private val _userWallet = mutableStateOf(0)
    val userWallet: State<Int> = _userWallet

    private val _userPoints = mutableStateOf(0)
    val userPoints: State<Int> = _userPoints

    fun registerUser() {
        val email = userEmail.value
        val password = userPassword.value
        val username = userName.value

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val currentUser = auth.currentUser
                        val userID = currentUser?.uid

                        if (userID != null) {
                            val userRef = database.getReference("users").child(userID)
                            val userMap = mapOf(
                                "username" to username,
                                "email" to email,
                                "wallet" to 0,
                                "points" to 0
                            )
                            userRef.setValue(userMap)
                        }

                        // Gửi email xác thực
                        currentUser?.sendEmailVerification()
                            ?.addOnCompleteListener { verifyTask ->
                                if (verifyTask.isSuccessful) {
                                    authResult.value = "Vui lòng kiểm tra Email"
                                } else {
                                    authResult.value = "Đăng ký thành công nhưng gửi email xác thực thất bại."
                                }
                            }
                    } else {
                        authResult.value = "Lỗi: ${task.exception?.message}"
                    }
                }
        } else {
            authResult.value = "Vui lòng nhập đầy đủ thông tin"
        }
    }

    fun givingData() {
        val userID = auth.currentUser?.uid ?: return
        val userRef = database.getReference("users").child(userID)

        userRef.get().addOnSuccessListener { dataSnapshot ->
            userName.value = dataSnapshot.child("username").value?.toString() ?: ""
            _userWallet.value = dataSnapshot.child("wallet").value?.toString()?.toIntOrNull() ?: 0
            _userPoints.value = dataSnapshot.child("points").value?.toString()?.toIntOrNull() ?: 0
        }.addOnFailureListener {
            authResult.value = "Không thể lấy dữ liệu người dùng"
        }
    }


    fun loginUser() {
        auth.signInWithEmailAndPassword(userEmail.value, userPassword.value)
            .addOnCompleteListener {
                authResult.value = if (it.isSuccessful){
                    givingData()
                    "Đăng nhập thành công"
                } else "Lỗi: ${it.exception?.message}"
            }
    }

    fun updateWallet(amount: Int) {
        _userWallet.value = amount
    }

    fun updatePoints(points: Int) {
        _userPoints.value = points
    }
}



