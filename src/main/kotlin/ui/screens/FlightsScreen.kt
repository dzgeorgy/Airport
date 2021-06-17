package ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import storage.AirportDatabase
import storage.entities.Flight
import ui.Screen

@ExperimentalMaterialApi
object FlightsScreen : Screen {

    private val db = AirportDatabase

    @ExperimentalMaterialApi
    @Composable
    override fun Content() {
        Surface(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background)
        ) {
            val scroll = rememberScrollState()
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(scroll).padding(8.dp).background(Color.Transparent)
            ) {
                TableHeader()
                db.getFlights().forEach {
                    FlightRow(it)
                }
            }
        }
    }

    @Composable
    internal fun TableHeader(headersStyle: TextStyle = MaterialTheme.typography.h6) {
        ListItem(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                listOf(
                    Pair("ID", 0.05f),
                    Pair("Отправление", 0.2f),
                    Pair("Время вылета", 0.2f),
                    Pair("Прибытие", 0.2f),
                    Pair("Время посадки", 0.2f),
                    Pair("Самолет", 0.15f)
                ).forEach {
                    Header(it.first, Modifier.weight(it.second), headersStyle)
                }
            }
        }
    }

    @Composable
    private fun Header(
        text: String,
        modifier: Modifier = Modifier,
        headersStyle: TextStyle = MaterialTheme.typography.subtitle1
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Start,
            modifier = modifier,
            style = headersStyle
        )
    }

    @Composable
    internal fun FlightRow(
        flight: Flight,
        textStyle: TextStyle = MaterialTheme.typography.subtitle2,
        onClick: (String) -> Unit = {}
    ) = ListItem(
        modifier = Modifier.fillMaxWidth().background(Color.Transparent).clickable(onClick = { onClick(flight.id) })
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().background(Color.Transparent),
        ) {
            Text(
                text = flight.id,
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(0.05f),
                style = textStyle
            )
            Text(
                text = flight.departure,
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(0.2f),
                fontWeight = if (flight.isDeparture()) FontWeight.Bold else FontWeight.Normal,
                color = if (flight.isDeparture()) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface,
                style = textStyle
            )
            Text(
                text = flight.departureTime,
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(0.2f),
                fontWeight = if (flight.isDeparture()) FontWeight.Bold else FontWeight.Normal,
                color = if (flight.isDeparture()) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface,
                style = textStyle
            )
            Text(
                text = flight.arrival,
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(0.2f),
                fontWeight = if (flight.isArrival()) FontWeight.Bold else FontWeight.Normal,
                color = if (flight.isArrival()) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface,
                style = textStyle
            )
            Text(
                text = flight.arrivalTime,
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(0.2f),
                fontWeight = if (flight.isArrival()) FontWeight.Bold else FontWeight.Normal,
                color = if (flight.isArrival()) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface,
                style = textStyle
            )
            Text(
                text = flight.aircraft,
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(0.15f),
                style = textStyle
            )
        }
    }

}