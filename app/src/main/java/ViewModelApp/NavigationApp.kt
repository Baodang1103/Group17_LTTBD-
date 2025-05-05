package ViewModelApp

import ViewApp.AccountScreen
import ViewApp.AddScreen
import ViewApp.LoginScreen
import ViewApp.MainScreen
import ViewApp.OptionlScreen
import ViewApp.PaymentScreen
import ViewApp.PickupScreen
import ViewApp.ShoppingScreen
import ViewApp.SignInScreen
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app.viewmodel.AuthViewModel

@Composable
fun NavigationApp() {
    val navController = rememberNavController()
    val viewModel: AuthViewModel = viewModel()

    NavHost(navController = navController, startDestination = "LoginScreen") {
        composable("LoginScreen") {
            LoginScreen(navController, viewModel)
        }
        composable("SignInScreen") {
            SignInScreen(navController, viewModel)
        }
        composable("MainScreen") {
            MainScreen(navController, viewModel)
        }
        composable("OptionlScreen") {
            OptionlScreen(navController, viewModel)
        }
        composable("AccountScreen") {
            AccountScreen(navController, viewModel)
        }
        composable("AddScreen") {
            AddScreen(navController)
        }
        composable("PickupScreen") {
            PickupScreen(navController)
        }
/*        composable("PaymentScreen") {
            PaymentScreen(navController, subCourtName = )
        }*/
        composable("ShoppingScreen") {
            ShoppingScreen(navController,viewModel)
        }
    }
}
