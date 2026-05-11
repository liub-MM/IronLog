package ironlog.app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ironlog.app.data.network.parser.GeminiWorkoutParser
import ironlog.app.ui.theme.IronLogTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val geminiWorkoutParser = GeminiWorkoutParser()
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("IronLogTest", "Відправляємо текст до Gemini: ")
            val res = geminiWorkoutParser.parseWorkoutText(
                "Сьогодні робив жим лежачи 80 кіло на 10 разів 3 підходи, а потім добив біцепс гантелями 15кг 3 по 12."
            )

            res.onSuccess { parsedObject ->
                Log.d("IronLogTest", " Отримано об'єкт: ")
                Log.d("IronLogTest", parsedObject.toString())
            }.onFailure { error ->
                Log.e("IronLogTest", "${error.message}")
            }
        }

        setContent {
            IronLogTheme {

            }
        }
    }
}
