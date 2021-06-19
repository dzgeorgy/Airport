package ui.screens

import ControlClass
import IController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.isMetaPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import entities.Ticket
import ui.Screen

@ExperimentalComposeUiApi
object BuyTicketScreen : Screen {

    private val controller: IController = ControlClass()

    @ExperimentalMaterialApi
    @Composable
    override fun Content() {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            val scroll = rememberScrollState()
            var chosenFlight: String? by remember { mutableStateOf(null) }
            Column(
                modifier = Modifier.fillMaxWidth().verticalScroll(scroll).padding(16.dp)
            ) {
                if (chosenFlight == null) {
                    Text(text = "Выберите рейс:", style = MaterialTheme.typography.h6)
                    var flights by remember { mutableStateOf(controller.getFlights()) }
                    Spacer(Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        var departureFilter by remember { mutableStateOf("") }
                        var arrivalFilter by remember { mutableStateOf("") }
                        var aircraftFilter by remember { mutableStateOf("") }
                        Input(
                            label = "Отправление",
                            value = departureFilter,
                            modifier = Modifier.weight(0.2f)
                        ) { departureFilter = it }
                        Spacer(Modifier.weight(0.033f))
                        Input(
                            label = "Прибытие",
                            value = arrivalFilter,
                            modifier = Modifier.weight(0.2f)
                        ) { arrivalFilter = it }
                        Spacer(Modifier.weight(0.033f))
                        Input(
                            label = "Самолет",
                            value = aircraftFilter,
                            modifier = Modifier.weight(0.2f)
                        ) { aircraftFilter = it }
                        Spacer(Modifier.weight(0.033f))
                        Button(
                            onClick = {
                                flights = controller.getFlightsByParameter {
                                    departureFilter.isEmpty().xor(it.departure == departureFilter) &&
                                            arrivalFilter.isEmpty().xor(it.arrival == arrivalFilter) &&
                                            aircraftFilter.isEmpty().xor(it.aircraft == aircraftFilter)
                                }
                            },
                            modifier = Modifier.weight(0.15f)
                        ) {
                            Text("Поиск")
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                    FlightsScreen.TableHeader(MaterialTheme.typography.subtitle1)
                    if (flights.isNotEmpty())
                        flights.forEach { flight ->
                            FlightsScreen.FlightRow(flight, MaterialTheme.typography.body2) { chosenFlight = it }
                        }
                    else {
                        Text(
                            text = "В ближайшее время нет рейсов, соответствующих вашим требованиям!",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h5
                        )
                    }
                } else {
                    val ticket = Ticket(chosenFlight!!)
                    var done by remember { mutableStateOf(false) }
                    Text(text = "Рейс:", style = MaterialTheme.typography.h6)
                    Spacer(Modifier.height(16.dp))
                    FlightsScreen.FlightRow(controller.getById(ticket.flightId)!!, MaterialTheme.typography.body2)
                    Spacer(Modifier.height(16.dp))
                    Column(
                        modifier = Modifier.weight(0.5f)
                    ) {
                        Text(text = "Заполните данные:", style = MaterialTheme.typography.h6)
                        Spacer(Modifier.height(16.dp))
                        Input(
                            label = "Фамилия",
                            value = ticket.lastName ?: ""
                        ) { ticket.lastName = it }
                        Spacer(Modifier.height(16.dp))
                        Input(
                            label = "Имя",
                            value = ticket.firstName ?: ""
                        ) { ticket.firstName = it }
                        Spacer(Modifier.height(16.dp))
                        Input(
                            label = "Возраст",
                            numbersOnly = true,
                            value = ticket.age ?: ""
                        ) { ticket.age = it }
                        Spacer(Modifier.height(16.dp))
                        Input(
                            label = "Номер паспорта",
                            numbersOnly = true,
                            value = ticket.passportNumber ?: ""
                        ) {
                            ticket.passportNumber = it
                        }
                        Spacer(Modifier.height(16.dp))
                        Input(
                            label = "Электронная почта",
                            value = ticket.email ?: ""
                        ) {
                            ticket.email = it
                        }
                        Spacer(Modifier.height(16.dp))
                        var error by remember { mutableStateOf(false) }
                        if (error) {
                            Text(text = "Все поля должны быть заполнены!", color = MaterialTheme.colors.error)
                            Spacer(Modifier.height(16.dp))
                        }
                        Button(onClick = {
                            error = !ticket.isComplete()
                            done = ticket.isComplete()
                            if (done) controller.buyTicket(ticket)
                        }) {
                            Text("Купить")
                        }
                        if (done) {
                            Spacer(Modifier.height(16.dp))
                            Text(
                                "Билет №${ticket.id} успешно приобретен и будет выслан на указанную почту!",
                                style = MaterialTheme.typography.h4,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun Input(
        label: String,
        numbersOnly: Boolean = false,
        value: String,
        modifier: Modifier = Modifier,
        onValueChange: (String) -> Unit
    ) {
        var consumedText by remember { mutableStateOf(0) }
        var text by remember { mutableStateOf(value) }
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                keyboardType = if (numbersOnly) KeyboardType.Phone else KeyboardType.Text,
            ),
            isError = if (numbersOnly) !text.matches(Regex("""\d+$|^$""")) else false,
            singleLine = true,
            label = {
                if (numbersOnly && !text.matches(Regex("""\d+$|^$"""))) Text("Поле должно содержать только цифры!") else Text(
                    label
                )
            },
            value = text,
            onValueChange = { text = it; onValueChange(text) },
            modifier = modifier.onPreviewKeyEvent {
                when {
                    (it.isMetaPressed && it.key == Key.Minus) -> {
                        consumedText -= text.length
                        text = ""
                        true
                    }
                    (it.isMetaPressed && it.key == Key.Equals) -> {
                        consumedText += text.length
                        text = ""
                        true
                    }
                    else -> false
                }
            }
        )
    }

}
