package com.example.foodies.main_meal_screens.presentation.main_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodies.R
import com.example.foodies.auth.presentation.profile_screen.data.PreferencesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityDropDownMenu(
    preferencesManager: PreferencesManager,
    scope: CoroutineScope
) {

    var city = preferencesManager.cityNameFlow.collectAsState(initial = null).value
    if(city == null) {
        city = "Мурманск"
    }
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = it }
    ) {
        TextField(
            value = city!!,
            onValueChange = {
                scope.launch { city = it }
            },
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                unfocusedBorderColor = MaterialTheme.colorScheme.tertiary,
                focusedLabelColor = MaterialTheme.colorScheme.tertiary,
                unfocusedLabelColor = MaterialTheme.colorScheme.tertiary,
                focusedTextColor = MaterialTheme.colorScheme.primary,
                unfocusedTextColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            readOnly = true,
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_down),
                    contentDescription = "Chose city icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(14.dp)
                )
            },
            modifier = Modifier
                .width(150.dp)
                .padding(bottom = 10.dp)
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = "Мурманск",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 13.sp
                    )
                },
                onClick = {
                    scope.launch { preferencesManager.storeCityName("Мурманск") }
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(
                        text = "СПБ",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 13.sp
                    )
                },
                onClick = {
                    scope.launch { preferencesManager.storeCityName("СПБ") }
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(
                        text = "Москва",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 13.sp
                    )
                },
                onClick = {
                    scope.launch { preferencesManager.storeCityName("Москва") }
                    isExpanded = false
                }
            )
        }
    }
}