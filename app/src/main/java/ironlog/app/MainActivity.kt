package ironlog.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import ironlog.app.presentation.navigation.AppNavHost
import ironlog.app.ui.theme.IronLogTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            IronLogTheme {
                val navController = rememberNavController()
                AppNavHost(
                    navController = navController,
                    modifier = Modifier
                )

            }
        }
    }
}
