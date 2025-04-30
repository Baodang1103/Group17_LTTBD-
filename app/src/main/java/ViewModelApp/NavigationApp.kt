package ViewModelApp

import ViewApp.HomeScreen
import ViewApp.MenuScreen
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

    NavHost(navController = navController, startDestination = "HomeScreen") {
        composable("HomeScreen") {
            HomeScreen(navController, viewModel)
        }
        composable("SignInScreen") {
            SignInScreen(navController, viewModel)
        }
        composable("MenuScreen") {
            MenuScreen(navController, viewModel)
        }
    }
}
