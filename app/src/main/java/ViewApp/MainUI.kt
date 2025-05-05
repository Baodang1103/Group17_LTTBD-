package ViewApp

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.app.viewmodel.AuthViewModel
import com.example.projectapp.R
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainScreen(navController: NavController,viewModel: AuthViewModel = viewModel()){
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color(0xFFF0E6CD))
    ){
        Row (modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF0E6CD)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically){
            Row (verticalAlignment = Alignment.CenterVertically){
                Image(
                    painter = painterResource(R.drawable.logo_pickleball),
                    contentDescription = "logo pickleball",
                    Modifier.size(70.dp)
                )
                Text(
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
                        Text(
                            text = "Hi, $username",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
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
            Spacer(modifier = Modifier.height(12.dp))
        }
        val walletAmount = viewModel.userWallet.value
        val rewardPoints = viewModel.userPoints.value
        AccountDetail(walletAmount,rewardPoints)
        Spacer(modifier = Modifier.height(18.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(cardDataList) { card ->
                CardOption(name = card.title, image = card.image, navController = navController)
            }
        }
        BottomBar(navController)
    }
}

data class CardData(val title: String, val image: Int)

val cardDataList = listOf(
    CardData("🔥 Sân giá rẻ", R.drawable.avatar_datsan),
    CardData("📊 Lượt đặt nhiều nhất", R.drawable.avatar_danhgia),
    CardData("🎁 Đổi điểm sân mã hời", R.drawable.avatar_bigsale),
    CardData("🔗 Kết nối người lạ", R.drawable.avatar_connect),
    CardData("🎉 Live sự kiện giải đấu", R.drawable.avatar_event),
    CardData("phát triển thêm", R.drawable.avatar_datsan)
)

@Composable
fun CardOption(name: String, image: Int, navController: NavController){
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        elevation = CardDefaults.cardElevation(4.dp)
    ){
        Column {
            Text(
                modifier = Modifier.clickable { navController.navigate("OptionlScreen") },
                text = name,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Image(
                painter = painterResource(id = image),
                contentDescription = "Image of card",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}


@Composable
fun AccountDetail(amount: Int, points: Int) {
    val formattedWallet = "%,d".format(amount).replace(',', '.')
    val formattedPoints = "%,d".format(points).replace(',', '.')

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF0E6CD))
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Cột tài khoản
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.wallet_icon),
                contentDescription = "Wallet Icon",
                modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = "Tài khoản", fontWeight = FontWeight.Bold)
                Text(text = "Nạp thêm  Ví của bạn: $formattedWallet", fontSize = 14.sp)
            }
        }

        // Đường phân cách
        Box(
            modifier = Modifier
                .height(40.dp)
                .width(1.dp)
                .background(Color.Gray.copy(alpha = 0.5f))
        )

        // Cột điểm thưởng
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Điểm thưởng", fontWeight = FontWeight.Bold)
            Text(text = "Điểm của bạn: $formattedPoints", fontSize = 14.sp)
        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    val items = listOf(
        "Home" to "MainScreen",
        "Giỏ" to "ShoppingScreen",
        "Sân" to "OptionlScreen",
        "Tôi" to "AccountScreen"
    )

    Row(
        modifier = Modifier.height(70.dp)
            .fillMaxWidth()
            .background(Color(0xFFF0E6CD))
            .padding(top = 15.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items.forEach { (label, route) ->
            Text(
                text = label,
                modifier = Modifier.clickable {
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }.padding(10.dp),
                fontSize = 18.sp,

            )
        }
    }
}

fun logout(viewModel: AuthViewModel){
    FirebaseAuth.getInstance().signOut()
    viewModel.authResult.value = ""
    viewModel.userEmail.value = ""
    viewModel.userPassword.value = ""
}