package ViewApp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.app.viewmodel.AuthViewModel
import com.example.projectapp.R // Ensure R class is imported correctly
import com.example.projectapp.ui.theme.ProjectAppTheme

// Data classes (replace with actual models later)
data class CourtInfo(
    val name: String = "Sân Orange Pickleball",
    val imageRes: Int = R.drawable.logo_pickleball // Placeholder image
)

data class SubCourt(
    val name: String,
    val price: String,
    val imageRes: Int // Placeholder image
)

data class TimeSlot(
    val time: String,
    val available: Boolean,
    val bookedByOther: Boolean = false // Example state
)

data class DayInfo(
    val dayOfMonth: String,
    val dayOfWeek: String,
    val isToday: Boolean = false
)

@Composable
fun PickupScreen(navController: NavController, viewModel: AuthViewModel = viewModel()) {
    val courtInfo = CourtInfo() // Replace with actual data fetching
    val subCourts = listOf(
        SubCourt("Sân Enlio 1", "400K/h", R.drawable.logo_pickleball),
        SubCourt("Sân Enlio 2", "400K/h", R.drawable.logo_pickleball),
        SubCourt("Sân SNB 1", "220K/h", R.drawable.logo_pickleball),
        SubCourt("Sân SNB 2", "220K/h", R.drawable.logo_pickleball)
    )
    val days = listOf(
        DayInfo("04", "H.nay", isToday = true),
        DayInfo("05", "Thứ 3"),
        DayInfo("06", "Thứ 4"),
        DayInfo("07", "Thứ 5"),
        DayInfo("08", "Thứ 6"),
        DayInfo("09", "Thứ 7")
        // Add more days as needed
    )
    val timeSlots = remember { // Use remember for state that changes based on selection
        mutableStateListOf(
            TimeSlot("6:00", true), TimeSlot("6:30", true),
            TimeSlot("7:00", false), TimeSlot("7:30", false), // Example booked slot
            TimeSlot("8:00", true), TimeSlot("8:30", true),
            TimeSlot("9:00", true), TimeSlot("9:30", true),
            TimeSlot("10:00", true), TimeSlot("10:30", true),
            TimeSlot("11:00", true), TimeSlot("11:30", true),
            TimeSlot("12:00", true), TimeSlot("12:30", true),
            TimeSlot("13:00", true), TimeSlot("13:30", true),
            TimeSlot("14:00", true), TimeSlot("14:30", true),
            TimeSlot("15:00", true), TimeSlot("15:30", true),
            TimeSlot("16:00", true), TimeSlot("16:30", true),
            TimeSlot("17:00", true), TimeSlot("17:30", true),
            TimeSlot("18:00", true), TimeSlot("18:30", true),
            TimeSlot("19:00", true), TimeSlot("19:30", true),
            TimeSlot("20:00", true), TimeSlot("20:30", true),
            TimeSlot("21:00", true), TimeSlot("21:30", true),
            TimeSlot("22:00", true), TimeSlot("22:30", true),
            TimeSlot("23:00", true)
        )
    }
    var selectedDay by remember { mutableStateOf(days.first { it.isToday }) }
    var selectedTime by remember { mutableStateOf<TimeSlot?>(null) }
    var selectedSubCourt by remember { mutableStateOf<SubCourt?>(subCourts[1]) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0E6CD))
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE8F0BD))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_personal),
                contentDescription = "User Avatar",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            val username = viewModel.userName.value
            Column {
                Text("Hi, $username", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("Chào bạn", fontSize = 11.sp)
            }
            Spacer(modifier = Modifier.weight(1f))

        }


        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text(
                text = "Pickle Ball",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Image(
                painter = painterResource(id = courtInfo.imageRes),
                contentDescription = courtInfo.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = courtInfo.name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { /* TODO: Chat action */ }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD9D9D9))) {
                    Text("Chat nhanh", color = Color.Black, fontSize = 11.sp)
                }
                Button(onClick = { navController.navigate("ShoppingScreen")}, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD9D9D9))) {
                    Text("Cửa hàng dụng cụ", color = Color.Black, fontSize = 11.sp)
                }
            }
        }

        // Day Selector
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(days) { day ->
                DayItem(day = day, isSelected = day == selectedDay) {
                    selectedDay = day

                    selectedTime = null
                }
            }
        }


        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(subCourts) { court ->
                SubCourtItem(court = court, isSelected = court == selectedSubCourt) {
                    selectedSubCourt = court

                    selectedTime = null
                }
            }
        }


        // Time Slot Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(timeSlots) { slot ->
                TimeSlotItem(
                    timeSlot = slot,
                    isSelected = slot == selectedTime,
                    onClick = {
                        if (slot.available) {
                            selectedTime = if (selectedTime == slot) null else slot // Toggle selection
                        }
                    }
                )
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFF0106))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Đặt sân với voucher\nĐặt sân ngay",
                color = Color.White,
                fontSize = 14.sp,
                lineHeight = 18.sp
            )
            Button(
                onClick = {
                    if (selectedSubCourt != null && selectedTime != null) {
                        navController.navigate("PaymentScreen/${selectedSubCourt?.name}/${selectedDay.dayOfMonth}/${selectedTime?.time}/${selectedSubCourt?.price}")
                    } else {
                        // Show error or prompt to select court/time
                    }
                },
                enabled = selectedSubCourt != null && selectedTime != null, // Enable only when court and time are selected
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedSubCourt != null && selectedTime != null) Color.White else Color.Gray, // Change color when enabled/disabled
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Đặt ngay")
            }
        }
    }
}

@Composable
fun DayItem(day: DayInfo, isSelected: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(50.dp)
            .clickable(onClick = onClick)
            .background(
                color = if (isSelected) Color(0xFFF8537C) else Color(0xFFA2A1AE),
                shape = RoundedCornerShape(3.dp)
            )
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = if (isSelected) Color.White else Color.White,
                    shape = RoundedCornerShape(topStart = 3.dp, topEnd = 3.dp)
                )
                .padding(vertical = 2.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = day.dayOfWeek,
                fontSize = 8.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = day.dayOfMonth,
            fontSize = 8.sp,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun SubCourtItem(court: SubCourt, isSelected: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) Color(0xFFF8537C) else Color.Transparent, // Highlight selected court
                shape = RoundedCornerShape(8.dp)
            )
            .padding(4.dp)
    ) {
        Image(
            painter = painterResource(id = court.imageRes),
            contentDescription = court.name,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(5.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(court.name, fontSize = 13.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        Text(court.price, fontSize = 10.sp, textAlign = TextAlign.Center)
    }
}


@Composable
fun TimeSlotItem(timeSlot: TimeSlot, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(4.dp))
            .background(
                when {
                    isSelected -> Color(0xFFF8537C)
                    !timeSlot.available -> Color.LightGray
                    else -> Color(0xFF93F18C)
                }
            )
            .clickable(enabled = timeSlot.available, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = timeSlot.time,
            color = when {
                isSelected -> Color.White
                !timeSlot.available -> Color.DarkGray
                else -> Color.Black
            },
            fontSize = 10.sp,
            textAlign = TextAlign.Center
        )
    }
}


@Preview(showBackground = true, device = "id:pixel_5")
@Composable
fun View3ScreenPreview() {
    ProjectAppTheme {
        PickupScreen(rememberNavController())
    }
}