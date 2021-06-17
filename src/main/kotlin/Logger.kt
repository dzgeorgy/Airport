import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Logger {

    val log = mutableListOf<String>()

    private val formatter = DateTimeFormatter.ofPattern("dd.mm.yyyy hh:mm:ss")

    fun add(string: String) { log.add(formatter.format(LocalDateTime.now()) + " : " + string) }

}