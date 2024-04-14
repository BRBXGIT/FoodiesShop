package com.example.foodies.basic_top_bar.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.foodies.R
import com.example.foodies.bottom_bar.presentation.noRippleClickable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicTopBar(
    navController: NavHostController,
    title: String,
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = "Navigation icon",
                modifier = Modifier
                    .noRippleClickable {
                        navController.popBackStack()
                    }
                    .padding(start = 16.dp)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.tertiary
        ),
        modifier = Modifier.shadow(4.dp)
    )
}