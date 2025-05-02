package ViewApp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.app.viewmodel.AuthViewModel
import com.example.projectapp.R

@Composable
fun AccountScreen(navController: NavController, viewModel: AuthViewModel) {
    val username by viewModel.userName
    val email by viewModel.userEmail

    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Thông tin người dùng",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Image(
                painter = painterResource(R.drawable.logo_personal),
                contentDescription = "Personal",
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(text = "Tên đăng nhập: $username", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = "Email: $email", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(15.dp))
            Button(onClick = {
                navController.navigate("AddScreen")
            }) {
                Text("Thêm sân")
            }
        }
    }
}