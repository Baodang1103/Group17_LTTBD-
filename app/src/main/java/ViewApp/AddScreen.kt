package ViewApp

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.app.viewmodel.saveCourtToFirestore

@Composable
fun AddScreen(navController: NavController){
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text("Thêm sân mới", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Tên sân") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = location, onValueChange = { location = it }, label = { Text("Vị trí") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Số điện thoại") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = rating, onValueChange = { rating = it }, label = { Text("Đánh giá") })
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = imageUrl, onValueChange = { imageUrl = it }, label = { Text("URL ảnh") })
        Spacer(modifier = Modifier.height(12.dp))

        if (imageUrl.isNotBlank()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Ảnh sân",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (name.isNotBlank() && imageUrl.isNotBlank()) {
                saveCourtToFirestore(
                    name = name,
                    location = location,
                    phone = phone,
                    rating = rating.toDoubleOrNull() ?: 0.0,
                    imageUrl = imageUrl,
                    onSuccess = {
                        Toast.makeText(context, "Đã lưu sân!", Toast.LENGTH_SHORT).show()
                        navController.popBackStack() // Trở về OptionlScreen
                    },
                    onError = {
                        Toast.makeText(context, "Lỗi: $it", Toast.LENGTH_SHORT).show()
                    }
                )
            } else {
                Toast.makeText(context, "Vui lòng nhập đầy đủ tên sân và URL ảnh!", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Lưu")
        }
    }
}