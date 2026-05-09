package ironlog.app.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ironlog.app.data.local.dao.WorkoutDao
import ironlog.app.data.local.database.db.IronLogDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): IronLogDatabase {
        return Room.databaseBuilder(
            context,
            IronLogDatabase::class.java,
            "ironlog_database"
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideWorkoutDao(database: IronLogDatabase): WorkoutDao {
        return database.workoutDao
    }

}