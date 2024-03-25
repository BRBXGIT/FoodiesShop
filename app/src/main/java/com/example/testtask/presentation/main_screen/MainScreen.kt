package com.example.testtask.presentation.main_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    mainScreenViewModel: MainScreenViewModel
) {
    val state = rememberLazyListState()

    Column {
        TopBar(
            visible = !state.canScrollBackward,
            mainScreenViewModel = mainScreenViewModel
        )

        LazyColumn(
            state = state,
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(100) {
                Text(text = it.toString())
            }
        }
    }
}