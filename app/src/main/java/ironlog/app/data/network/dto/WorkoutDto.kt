package ironlog.app.data.network.dto

import com.google.gson.annotations.SerializedName


data class AiWorkoutResponse(
    @SerializedName("exercises") val exercises: List<AiExercise>
)

data class AiExercise(
    @SerializedName("name") val name: String,
    @SerializedName("targetMuscle") val targetMuscle: String,
    @SerializedName("sets") val sets: List<AiSet>
)

data class AiSet(
    @SerializedName("weight") val weight: Float,
    @SerializedName("reps") val reps: Int
)