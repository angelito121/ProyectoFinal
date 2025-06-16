import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ReserBar2.ui.screens.home.HomeScreen
import com.example.reserbar2.ui.screens.entry.GestionarReservasScreen
import com.example.reserbar2.ui.screens.entry.MesaScreen
import com.example.reserbar2.navigation.NavigationRoutes

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.HOME
    ) {
        composable(NavigationRoutes.HOME) {
            HomeScreen(navController)
        }
        composable(NavigationRoutes.MESA) {
            MesaScreen(navController)
        }
        composable(NavigationRoutes.RESERVA) {
            ReservaScreen(navController)
        }
        composable(NavigationRoutes.GESTIONARRESERVA) {
            GestionarReservasScreen(navController)
        }
    }
}