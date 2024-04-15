package com.example.foodies.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val logRegScreensSpacer: Dp = 0.dp,
    val logRegScreensUpSpacer: Dp = 0.dp,
    val mealBoxHeight: Dp = 0.dp,
    val profileScreenUserBlockSize: Float = 0f,
    val mealScreenImage: Float = 0f
)

val compactSmallDimens = Dimens(
    logRegScreensUpSpacer = 37.dp,
    logRegScreensSpacer = 18.5.dp,
    mealBoxHeight = 210.dp,
    profileScreenUserBlockSize = 0.55f,
    mealScreenImage = 0.2f
) //Small phone

val compactMediumDimens = Dimens(
    logRegScreensUpSpacer = 75.dp,
    logRegScreensSpacer = 37.5.dp,
    mealBoxHeight = 215.dp,
    profileScreenUserBlockSize = 0.35f
) //Pixel 8

val compactLargeDimens = Dimens(
    logRegScreensUpSpacer = 90.dp,
    logRegScreensSpacer = 45.dp,
    mealBoxHeight = 245.dp,
    profileScreenUserBlockSize = 0.35f
) //Pixel 8 pro

val compactSmallMediumDimens = Dimens(
    logRegScreensUpSpacer = 75.dp,
    logRegScreensSpacer = 37.5.dp,
    mealBoxHeight = 210.dp,
    profileScreenUserBlockSize = 0.4f
) //Samsung s20fe
