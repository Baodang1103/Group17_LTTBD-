package ViewApp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border // Added import for border modifier
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
// Explicitly import only used icons from material.icons.filled
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Info // Import Info icon for testing
// import androidx.compose.material.icons.filled.NotificationsNone // Remove this import for now
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
// Explicitly import used Material 3 components instead of wildcard
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon // Explicit import for Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
// import androidx.compose.material3.* // Removed wildcard import
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.app.viewmodel.AuthViewModel
import com.example.projectapp.R // Ensure R class is imported correctly
import com.example.projectapp.ui.theme.ProjectAppTheme

// Data classes for View 5
data class ShopCategory(
    val name: String,
    val imageRes: Int // Placeholder image resource ID
)

data class EventInfo(
    val title: String = "#BLACKFRIDAY",
    val description: String = "It’s time to get",
    val location: String = "70 TO KY DISTRICT 12 HO CHI MINH CITY",
    val date: String = "28 NOV",
    val time: String = "6:30AM",
    val imageRes: Int = R.drawable.logo_pickleball, // Placeholder event image
    val backgroundImageRes: Int = R.drawable.avatar_event // Placeholder background
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingScreen(navController: NavController,viewModel: AuthViewModel) {
    val categories = listOf(
        ShopCategory("Vợt Pickleball", R.drawable.logo_pickleball), // Replace with actual images
        ShopCategory("Bóng Pickleball", R.drawable.logo_pickleball),
        ShopCategory("Phụ kiện", R.drawable.logo_pickleball),
        ShopCategory("Áo quần", R.drawable.shop) // Example different image
    )
    val eventInfo = EventInfo()

    Scaffold(
        // Bottom Navigation is likely handled in MainUI or NavigationApp,
        // but we can include a placeholder structure if needed.
        // For this example, we assume it's handled elsewhere.
        // bottomBar = { BottomNavigationBar(navController = navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF0E6CD)) // Background from Figma
                .verticalScroll(rememberScrollState()) // Make content scrollable
        ) {
            // Profile Header Section
            ProfileHeader(viewModel)

            // Shop Categories Section
            ShopSection(navController, categories)

            // Event/Promotion Section
            EventSection(navController, eventInfo)

            Spacer(modifier = Modifier.height(16.dp)) // Add some padding at the bottom
        }
    }
}

@Composable
fun ProfileHeader(viewModel: AuthViewModel = viewModel()) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_personal), // Replace with actual user avatar
            contentDescription = "User Avatar",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(1.dp, Color.Gray, CircleShape) // Optional border
        )
        Spacer(modifier = Modifier.width(12.dp))
        val username = viewModel.userName.value
        Column {
            Text("Chào bạn, $username", fontSize = 11.sp, color = Color.DarkGray)
            Text("Hi, $username", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.weight(1f))
        // Add notification icon or other icons if needed
        IconButton(onClick = { /* TODO: Handle notifications */ }) {
            // Temporarily replace Notifications icon with Info icon to test further
            Icon(imageVector = Icons.Default.Info, contentDescription = "Info")
        }
    }
}

@Composable
fun ShopSection(navController: NavController, categories: List<ShopCategory>) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Shop dụng cụ", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            TextButton(onClick = { /* TODO: Navigate to full shop screen */ }) {
                Text("See all", fontSize = 12.sp, color = Color(0xFF6C63FF)) // Accent color
                // Explicitly name the imageVector parameter to resolve ambiguity
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight, // Explicitly named
                    contentDescription = "See all",
                    modifier = Modifier.size(12.dp),
                    tint = Color(0xFF6C63FF)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text("Menu", fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(categories) { category ->
                CategoryItem(category = category, onClick = { /* TODO: Navigate to category */ })
            }
        }
    }
}

@Composable
fun CategoryItem(category: ShopCategory, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(80.dp) // Adjust width as needed
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = category.imageRes),
            contentDescription = category.name,
            modifier = Modifier
                .size(70.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color.White), // Add white background if images have transparency
            contentScale = ContentScale.Crop // Use Crop or Fit as appropriate
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = category.name,
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = Color(0xFF3E3D3D) // Dark gray text
        )
    }
}

@Composable
fun EventSection(navController: NavController, eventInfo: EventInfo) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Cập nhật tất cả sự kiện", fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(modifier = Modifier.height(180.dp)) { // Adjust height as needed
                Image(
                    painter = painterResource(id = eventInfo.backgroundImageRes),
                    contentDescription = "Event Background",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                // Overlay Content
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f)) // Semi-transparent overlay
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column {
                            Text(eventInfo.description, color = Color(0xFFEC0004), fontSize = 10.sp) // Red text
                            Text(eventInfo.title, color = Color(0xFF00ECBD), fontWeight = FontWeight.Bold, fontSize = 14.sp) // Teal text
                        }
                        // Bookmark Icon (Simplified)
                        Column(
                            modifier = Modifier
                                .background(Color.White.copy(alpha = 0.8f), RoundedCornerShape(5.dp))
                                .padding(horizontal = 6.dp, vertical = 3.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(eventInfo.date, color = Color.Black, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            Text(eventInfo.time, color = Color.Black, fontSize = 8.sp)
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Explicitly name the imageVector parameter
                            Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Location", tint = Color.White, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(eventInfo.location, color = Color.White, fontSize = 10.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                        Button(
                            onClick = { /* TODO: Handle event registration */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3E3D3D)), // Dark button
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                            shape = RoundedCornerShape(7.dp)
                        ) {
                            Text("REGISTER NOW", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}


// Placeholder for Bottom Navigation - Actual implementation might be in MainUI.kt
@Composable
fun BottomNavigationBar(navController: NavController) {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf(
        BottomNavItem("Home", Icons.Default.Home, "view5"), // Assuming view5 is home
        BottomNavItem("Search", Icons.Default.Search, "view2"), // Assuming view2 is search/list
        BottomNavItem("Booking", Icons.Default.DateRange, "booking_history"), // Placeholder route
        BottomNavItem("Notifications", Icons.Default.Notifications, "notifications"), // This one uses the filled Notifications icon already
        BottomNavItem("Profile", Icons.Default.Person, "view1") // Assuming view1 is profile
    )

    NavigationBar(
        containerColor = Color(0xFFE3E9F2), // Background from Figma
        contentColor = Color.Gray // Default icon color
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                // Explicitly name the imageVector parameter and add an explicit cast as a last resort
                icon = { Icon(imageVector = item.icon as ImageVector, contentDescription = item.title) },
                label = { Text(item.title, fontSize = 9.sp) }, // Smaller font size for labels
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF3259A3), // Selected icon color from Figma
                    selectedTextColor = Color(0xFF3259A3),
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent // Hide indicator for this style
                )
            )
        }
    }
}

data class BottomNavItem(val title: String, val icon: ImageVector, val route: String)


