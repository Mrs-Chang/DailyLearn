package com.chang.dailylearn.words

data class EditStep(
    val action: EditAction,
    val distance: Int,
    val prev: EditStep?
)
