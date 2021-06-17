package ui.screens

import Logger
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.Screen

object AdminScreen : Screen {

    private val logger = Logger

    @Composable
    override fun Content() {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            val scroll = rememberScrollState()
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(scroll),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Log")
                println(logger.log)
                logger.log.forEach {
                    Text(text = it, modifier = Modifier.fillMaxWidth())
                    Spacer(Modifier.height(4.dp))
                }
            }
        }
    }

}