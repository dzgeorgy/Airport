import storage.AirportDatabase
import entities.Flight
import entities.Ticket

class ControlClass : IController {

    private val db = AirportDatabase

    override fun getFlights(): List<Flight> {
        return db.getFlights()
    }

    override fun buyTicket(ticket: Ticket) {
        db.addTicket(ticket)
    }

    override fun getFlightsByParameter(predicate: (Flight) -> Boolean): List<Flight> {
        return db.getByParameters(predicate)
    }

    override fun getById(id: String): Flight? {
        return db.getById(id)
    }

}