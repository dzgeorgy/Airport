import entities.Flight
import entities.Ticket

interface IController {

    fun getFlights(): List<Flight>

    fun buyTicket(ticket: Ticket)

    fun getFlightsByParameter(predicate: (Flight) -> Boolean): List<Flight>

    fun getById(id: String): Flight?

}