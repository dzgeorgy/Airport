package storage

import Logger
import entities.Flight
import entities.Ticket
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random
import kotlin.random.nextInt

object AirportDatabase {

    private val flights: MutableList<Flight> = mutableListOf()
    private val tickets: MutableList<Ticket> = mutableListOf()

    fun getFlights() = flights.toList()

    fun getById(id: String) = flights.firstOrNull { it.id == id }

    fun addTicket(ticket: Ticket) {
        Logger.add("Added ticket: $ticket"); tickets.add(ticket)
    }

    fun getByParameters(predicate: (Flight) -> Boolean) = flights.filter(predicate)

    /**
     * Random database pre-generation below
     */

    //Possible cities of departure/arrival
    private val cities =
        listOf(
            "Москва",
            "Санкт-Петербург",
            "Нью-Йорк",
            "Лондон",
            "Париж",
            "Лас-Вегас",
            "Хельсинки",
            "Токио",
            "Мадрид",
            "Барселона",
            "Рим",
            "Таллин",
            "Неаполь"
        )

    //Possible aircrafts
    private val aircrafts =
        listOf(
            "Airbus A220",
            "Airbus A310",
            "Airbus A380",
            "Airbus A350",
            "Boeing-777",
            "Boeing-787",
            "Boeing-737",
            "Boeing-717",
            "Superjet-100",
            "Tu-204",
        )

    init {
        val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.YY HH:mm")
        for (i in 0..19) {
            val departure = cities[Random.nextInt(0, cities.size)]
            val departureTime = LocalDateTime.now().plusSeconds(Random.nextLong(-10000, 10000))
            val arrival: String =
                if (departure != "Санкт-Петербург") "Санкт-Петербург" else cities[Random.nextInt(0, cities.size)]
            val flight = Flight(
                Random.nextInt(100..999).toString(),
                departure,
                dateFormatter.format(departureTime),
                arrival,
                dateFormatter.format(departureTime.plusSeconds(Random.nextLong(20000))),
                aircrafts[Random.nextInt(0, aircrafts.size)]
            )
            addFlight(flight)
        }
        flights.sortBy {
            if (it.arrival == "Санкт-Петербург") it.arrivalTime
            else it.departureTime
        }
    }

    private fun addFlight(flight: Flight) {
        Logger.add("Added flight: $flight")
        flights.add(flight)
    }

}
