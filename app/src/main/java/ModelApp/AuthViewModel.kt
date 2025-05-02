package com.example.app.viewmodel


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

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
                                "admin" to false,
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

fun saveCourtToFirestore(
    name: String,
    location: String,
    phone: String,
    rating: Double,
    imageUrl: String,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    val court = hashMapOf(
        "name" to name,
        "location" to location,
        "phone" to phone,
        "rating" to rating,
        "imageUrls" to listOf(imageUrl)
    )

    FirebaseFirestore.getInstance().collection("courts")
        .add(court)
        .addOnSuccessListener { onSuccess() }
        .addOnFailureListener { e -> onError(e.message ?: "Lỗi không xác định") }
}

data class Court(
    val name: String = "",
    val location: String = "",
    val phone: String = "",
    val rating: Double = 0.0,
    val imageUrls: List<String> = emptyList()
)

@Composable
fun rememberCourts(): List<Court> {
    var courtList by remember { mutableStateOf(listOf<Court>()) }

    LaunchedEffect(true) {
        FirebaseFirestore.getInstance()
            .collection("courts")
            .get()
            .addOnSuccessListener { result ->
                val courts = result.map { doc ->
                    Court(
                        name = doc.getString("name") ?: "",
                        location = doc.getString("location") ?: "",
                        phone = doc.getString("phone") ?: "",
                        rating = doc.getDouble("rating") ?: 0.0,
                        imageUrls = doc.get("imageUrls") as? List<String> ?: emptyList()
                    )
                }
                courtList = courts
            }
    }

    return courtList
}