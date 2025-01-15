package com.starkindustries.jetpackcomposefirebase.Backend.Utility

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import com.starkindustries.jetpackcomposefirebase.Backend.Data.TabRowItem

fun tabRowItems():List<TabRowItem>{
    return listOf(
        TabRowItem("Home",Icons.Filled.Home,Icons.Outlined.Home)
        ,TabRowItem("Profile",Icons.Filled.Person,Icons.Outlined.Person)
    )
}