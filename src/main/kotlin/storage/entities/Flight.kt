package storage.entities

data class Flight(
    val id: String,
    val departure: String,
    val departureTime: String,
    val arrival: String,
    val arrivalTime: String,
    val aircraft: String
) {
    fun isArrival() = arrival == "Санкт-Петербург"
    fun isDeparture() = !isArrival()
}
