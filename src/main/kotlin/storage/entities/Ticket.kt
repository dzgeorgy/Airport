package storage.entities

data class Ticket(
    var flightId: String,
    var firstName: String? = null,
    var lastName: String? = null,
    var age: String? = null,
    var passportNumber: String? = null,
) {
    fun isComplete() = firstName != null && lastName != null && age != null && passportNumber != null
}
