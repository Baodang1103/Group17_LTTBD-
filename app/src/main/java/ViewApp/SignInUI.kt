package ViewApp


import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app.viewmodel.AuthViewModel


@Composable
fun SignInScreen(navController: NavController, viewModel: AuthViewModel) {
    var email by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxWidth().padding(top = 150.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Đăng Ký", fontSize = 40.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(50.dp))

        Username (username){ username = it}
        Spacer(modifier = Modifier.height(20.dp))

        Email(email) { email = it }
        Spacer(modifier = Modifier.height(20.dp))


        Password(password) { password = it }
        Spacer(modifier = Modifier.height(30.dp))

        Button(onClick = {
            viewModel.userName.value = username
            viewModel.userEmail.value = email
            viewModel.userPassword.value = password
            viewModel.registerUser()
        }) {
            Text("Đăng ký")
        }

        // Chuyển sang màn hình đăng nhập nếu thành công
        LaunchedEffect(viewModel.authResult.value) {
            if (viewModel.authResult.value == "Vui lòng kiểm tra Email") {
                Toast.makeText(context,viewModel.authResult.value, Toast.LENGTH_SHORT).show()
                navController.navigate("HomeScreen")
            } else if (viewModel.authResult.value.startsWith("Vui lòng nhập đầy đủ thông tin")) {
                Toast.makeText(context, viewModel.authResult.value, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
