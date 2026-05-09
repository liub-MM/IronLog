package ironlog.app.data.local.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ironlog.app.data.local.dao.WorkoutDao
import ironlog.app.data.local.database.entity.ExerciseEntity
import ironlog.app.data.local.database.entity.SetEntity
import ironlog.app.data.local.database.entity.WorkoutEntity

@Database(
    version = 1,
    entities = [
        WorkoutEntity::class,
        SetEntity::class,
        ExerciseEntity::class,
    ],
    exportSchema = true
)
abstract class IronLogDatabase : RoomDatabase() {

    abstract val workoutDao: WorkoutDao
}