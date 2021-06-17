package entities

import kotlin.random.Random

data class Ticket(
    var flightId: String,
    var firstName: String? = null,
    var lastName: String? = null,
    var age: String? = null,
    var passportNumber: String? = null,
    var email: String? = null,
    var id: Int = Random.nextInt(10000, 99999)
) {
    fun isComplete() = firstName != null && lastName != null && age != null && passportNumber != null && email != null
}
