package ViewApp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
// Import KeyboardArrowRight instead of ArrowForwardIos
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projectapp.R // Ensure R class is imported correctly
import com.example.projectapp.ui.theme.ProjectAppTheme

// Data for View 4 (Passed via navigation or ViewModel)
data class BookingDetails(
    val courtName: String = "Orange Pickleball",
    val subCourtName: String = "Sân Enlio 2",
    val time: String = "7:00-8:00",
    val date: String = "Thứ 3, ngày 4/4/2025", // Combine day and date
    val price: String = "400.000VND", // Base price
    val discount: String = "-30.000VND", // Example discount
    val finalPrice: String = "360.000VND", // Price after discount
    val courtImageRes: Int = R.drawable.logo_pickleball // Placeholder
)

enum class PaymentMethod {
    CASH, PREPAY
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    navController: NavController,
    subCourtName: String?,
    dayOfMonth: String?,
    time: String?,
    price: String?
) {
    // Use passed arguments or default/fetch details
    val bookingDetails = BookingDetails(
        subCourtName = subCourtName ?: "Sân Enlio 2",
        time = time ?: "7:00-8:00",
        date = "Thứ 3, ngày ${dayOfMonth ?: "4"}/4/2025", // Construct date
        price = price?.replace("/h", "VND") ?: "400.000VND", // Format price
        // Calculate discount and final price based on logic or fetched data
        discount = "-30.000VND",
        finalPrice = "360.000VND"
    )

    var selectedPaymentMethod by remember { mutableStateOf(PaymentMethod.CASH) }
    var voucherCode by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Thanh toán", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFE8F0BD) // Match header color
                )
            )
        },
        bottomBar = {
            // Footer Button Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFF0106)) // Red background
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tổng thanh toán: ${bookingDetails.finalPrice}",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Button(
                    onClick = { /* TODO: Handle booking confirmation */
                        navController.navigate("main") // Navigate back to main or a success screen
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Đặt ngay")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF0E6CD)) // Background color
                .padding(16.dp)
        ) {
            // Booking Summary Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF).copy(alpha = 0.45f)) // Semi-transparent white
            ) {
                Row(modifier = Modifier.padding(16.dp)) {
                    Image(
                        painter = painterResource(id = bookingDetails.courtImageRes),
                        contentDescription = bookingDetails.courtName,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(bookingDetails.courtName, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Chi tiết:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text("Đã đặt: ${bookingDetails.subCourtName}", fontSize = 12.sp)
                        Text("Thời gian: ${bookingDetails.time}", fontSize = 12.sp)
                        Text("Ngày: ${bookingDetails.date}", fontSize = 12.sp)
                        Text("Thanh toán: ${bookingDetails.price}", fontSize = 12.sp) // Base price shown here
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Payment Method Section
            Text("Phương thức thanh toán", fontWeight = FontWeight.Medium, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(5.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    PaymentOptionRow(
                        text = "Thanh toán bằng tiền mặt",
                        selected = selectedPaymentMethod == PaymentMethod.CASH,
                        imageRes = R.drawable.wallet_icon // Replace with actual cash icon if available
                    ) { selectedPaymentMethod = PaymentMethod.CASH }
                    Divider(color = Color.LightGray, thickness = 0.5.dp)
                    PaymentOptionRow(
                        text = "Thanh toán trước",
                        selected = selectedPaymentMethod == PaymentMethod.PREPAY,
                        imageRes = R.drawable.logo_app // Replace with actual prepay icon (e.g., card, wallet)
                    ) { selectedPaymentMethod = PaymentMethod.PREPAY }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Voucher Section
            Text("Sử dụng mã giảm", fontWeight = FontWeight.Medium, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /* TODO: Open voucher selection */ },
                shape = RoundedCornerShape(5.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Chọn voucher ở đây", fontSize = 12.sp, color = Color.Gray)
                    // Use KeyboardArrowRight icon instead
                    Icon(Icons.Filled.KeyboardArrowRight, contentDescription = "Select Voucher", modifier = Modifier.size(16.dp), tint = Color.Gray)
                    // Add voucher icon if available (like R.drawable.ic_voucher)
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            // Final Price Confirmation Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(5.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Xác nhận tổng số tiền", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Tổng tiền sân:", fontSize = 10.sp)
                        Text(bookingDetails.price, fontSize = 10.sp)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Giảm giá:", fontSize = 10.sp)
                        Text(bookingDetails.discount, fontSize = 10.sp, color = Color.Red) // Show discount in red
                    }
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Tổng thanh toán:", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Text(bookingDetails.finalPrice, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun PaymentOptionRow(text: String, selected: Boolean, imageRes: Int, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = text,
            modifier = Modifier.size(24.dp) // Adjust size as needed
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text, fontSize = 12.sp, modifier = Modifier.weight(1f))
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF6C63FF))
        )
    }
}

