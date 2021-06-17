package ui

import androidx.compose.desktop.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.svgResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ui.screens.AdminScreen
import ui.screens.BuyTicketScreen
import ui.screens.FlightsScreen
import ui.theme.AirportTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@ExperimentalComposeUiApi
class MainWindow {

    companion object {
        const val TITLE = "AIRPORT"
        const val HEIGHT = 800
        const val WIDTH = 1000
    }

    @ExperimentalMaterialApi
    fun run() = Window(
        title = TITLE,
        size = IntSize(WIDTH, HEIGHT)
    ) {
        AirportTheme {
            var mode: Mode by remember { mutableStateOf(Mode.FLIGHTS) }
            Column {
                Row(
                    modifier = Modifier.weight(0.95f, true)
                ) {
                    //Menu column
                    Column(
                        modifier = Modifier.fillMaxHeight().weight(0.2f).background(MaterialTheme.colors.secondary),
                    ) {
                        ListItem(
                            icon = { Icon(svgResource("images/icons/flight.svg"), "") },
                            modifier = Modifier
                                .background(if (mode == Mode.FLIGHTS) MaterialTheme.colors.secondaryVariant else Color.Transparent)
                                .clickable {
                                    mode = Mode.FLIGHTS
                                }
                        ) {
                            Text(text = "Рейсы")
                        }
                        ListItem(
                            icon = { Icon(svgResource("images/icons/ticket.svg"), "") },
                            modifier = Modifier
                                .background(if (mode == Mode.BUY_TICKET) MaterialTheme.colors.secondaryVariant else Color.Transparent)
                                .clickable {
                                    mode = Mode.BUY_TICKET
                                }
                        ) {
                            Text(text = "Купить билет")
                        }
                        Spacer(modifier = Modifier.weight(1f, true))
                        ListItem(
                            icon = { Icon(svgResource("images/icons/key.svg"), "") },
                            modifier = Modifier
                                .background(if (mode == Mode.ADMIN) MaterialTheme.colors.secondaryVariant else Color.Transparent)
                                .clickable {
                                    mode = Mode.ADMIN
                                }
                        ) {
                            Text(text = "Админ", overflow = TextOverflow.Ellipsis)
                        }
                    }
                    //Content column
                    Column(
                        modifier = Modifier.fillMaxHeight().weight(0.8f)
                    ) {
                        mode.screen.Content()
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth().weight(0.05f).background(MaterialTheme.colors.secondaryVariant)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    var dateTime by remember { mutableStateOf(LocalDateTime.now()) }
                    CoroutineScope(Dispatchers.Default).launch {
                        withContext(Dispatchers.Main) {
                            dateTime = LocalDateTime.now()
                        }
                        @Suppress("BlockingMethodInNonBlockingContext")
                        Thread.sleep(1000)
                    }
                    val dateFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy")
                    val timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss")
                    Text(text = dateFormatter.format(dateTime).capitalize(Locale("ru")))
                    Text(text = timeFormatter.format(dateTime))
                }
            }
        }
    }

    enum class Mode(internal val screen: Screen) {
        @ExperimentalMaterialApi
        FLIGHTS(FlightsScreen),

        @ExperimentalComposeUiApi
        BUY_TICKET(BuyTicketScreen),
        ADMIN(AdminScreen)
    }

}