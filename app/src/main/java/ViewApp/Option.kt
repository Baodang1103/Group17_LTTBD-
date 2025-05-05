package ViewApp

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.app.viewmodel.AuthViewModel
import com.example.app.viewmodel.rememberCourts
import com.example.projectapp.R
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@SuppressLint("UnrememberedMutableState")
@Composable
fun OptionlScreen(navController: NavController, viewModel: AuthViewModel = viewModel()){
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color(0xFFF0E6CD))){
        Row(modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF0E6CD)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Row (verticalAlignment = Alignment.CenterVertically){
                Image(
                    painter = painterResource(R.drawable.logo_pickleball),
                    contentDescription = "logo pickleball",
                    Modifier.size(70.dp)
                )
                androidx.compose.material3.Text(
                    text = "Pickle Ball",
                    modifier = Modifier.padding(top = 20.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Column {
                Row (verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.logo_personal),
                        contentDescription = "logo personal",
                        Modifier.size(70.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    val username = viewModel.userName.value
                    Column {
                        androidx.compose.material3.Text(
                            text = "Hi, $username",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        androidx.compose.material3.Text(
                            text = "Đăng xuất",
                            color = Color.Blue,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                logout(viewModel)
                                navController.popBackStack("LoginScreen", inclusive = false)
                            }.padding(top = 4.dp)
                        )
                    }

                }
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
        var searchbar by remember { mutableStateOf("") }
        SearchBar(searchbar) {searchbar = it}
        Spacer(modifier = Modifier.height(5.dp))
        FilterBar()
        val courts = rememberCourts()

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(courts) { court ->
                Card(
                    elevation = 4.dp,
                    modifier = Modifier.fillMaxWidth().clickable { navController.navigate("PickupScreen") }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        court.imageUrls.firstOrNull()?.let { imageUrl ->
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = "Hình ảnh sân",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Tên sân: ${court.name}", fontWeight = FontWeight.Bold)
                        Text("Vị trí: ${court.location}")
                        Text("Số điện thoại: ${court.phone}")
                        Text("Đánh giá: ${court.rating}")
                    }
                }
            }
        }
        BottomBar(navController)
    }
}

@Composable
fun SearchBar(value: String, onValueChange: (String) -> Unit){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF0E6CD))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text("Search") },
            placeholder = { Text("Tìm kiếm")},
            leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = "Search bar icon")},
            singleLine = true,
            modifier = Modifier.width(300.dp).height(50.dp)
        )
    }
}

@Composable
fun FilterBar(){
    val tabs = listOf("Gần nhất", "Đặt nhiều nhất", "Sân đã đặt", "Giá")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        tabs.forEach {
            Text(
                text = it,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { /* xử lý lọc */ }
            )
        }
    }
}

