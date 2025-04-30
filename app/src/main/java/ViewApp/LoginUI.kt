package ViewApp

import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.app.viewmodel.AuthViewModel
import com.google.firebase.firestore.auth.User

@Composable
fun HomeScreen(navController: NavController, viewModel: AuthViewModel) {
    var email by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8DCDC))
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        Text("Đăng Nhập", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(20.dp))

        Email(email) { email = it }
        Spacer(modifier = Modifier.height(10.dp))

        Password(password) { password = it }
        Spacer(modifier = Modifier.height(30.dp))

        Button(onClick = {
            viewModel.userEmail.value = email
            viewModel.userPassword.value = password
            viewModel.loginUser()
        }) {
            Text("Đăng nhập")
        }

        LaunchedEffect(viewModel.authResult.value) {
            if (viewModel.authResult.value == "Đăng nhập thành công") {
                Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                navController.navigate("MenuScreen")
            } else if (viewModel.authResult.value.startsWith("Vui lòng nhập đầy đủ thông tin")) {
                Toast.makeText(context, viewModel.authResult.value, Toast.LENGTH_SHORT).show()
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row {
            Text("Đăng ký?", modifier = Modifier.clickable {
                navController.navigate("SignInScreen")
            })
        }
    }
}

@Composable
fun Username(value: String, onValueChange: (String) -> Unit){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {Text(text = "Tên đăng nhập")},
        placeholder = {Text(text = "Nhập tên đăng nhập của bạn")},
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Person, contentDescription = "User icon")
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun Email(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Email") },
        placeholder = { Text("Nhập email của bạn") },
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Email, contentDescription = "Email Icon")
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun Password(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Mật khẩu") },
        placeholder = { Text("Nhập mật khẩu") },
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Lock, contentDescription = "Password Icon")
        },
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth()
    )
}


