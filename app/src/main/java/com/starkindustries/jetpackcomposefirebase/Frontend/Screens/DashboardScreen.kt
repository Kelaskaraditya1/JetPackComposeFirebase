package com.starkindustries.jetpackcomposefirebase.Frontend.Screens

import android.widget.ImageButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.starkindustries.jetpackcomposefirebase.Backend.Authentication.Login.signout
import com.starkindustries.jetpackcomposefirebase.Backend.Utility.tabRowItems
import com.starkindustries.jetpackcomposefirebase.Frontend.Routes.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController) {

    var selectedTab by remember {
        mutableStateOf(0)
    }

    val pagerState = rememberPagerState {
        tabRowItems().size
    }
    


    LaunchedEffect(selectedTab) {
        pagerState.scrollToPage(selectedTab)
    }

    LaunchedEffect(pagerState.currentPage) {
        selectedTab = pagerState.currentPage
    }

    val tabNavController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Notes App",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W500,
                        color = Color.Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        // Handle back navigation
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // TabRow for tab navigation
            TabRow(
                selectedTabIndex = selectedTab,
                modifier = Modifier.fillMaxWidth()
            ) {
                tabRowItems().forEachIndexed { index, item ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = {
                            selectedTab = index
                        },
                        icon = {
                            Icon(
                                imageVector = if (selectedTab == index)
                                    item.selectedIcon
                                else
                                    item.unselectedIcon,
                                contentDescription = null
                            )
                        },
                        text = {
                            Text(
                                text = item.title,
                                fontWeight = FontWeight.W500,
                                fontSize = 16.sp
                            )
                        }
                    )
                }
            }

            // HorizontalPager for switching tabs
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { index ->
                when (index) {
                    0 -> {
                        // Navigate to HomeScreen
                        NavHost(
                            navController = tabNavController,
                            startDestination = Routes.HomeScreen.route
                        ) {
                            composable(Routes.HomeScreen.route) {
                                HomeScreen()
                            }
                        }
                    }
                    1 -> {
                        // Navigate to ProfileScreen
                        NavHost(
                            navController = tabNavController,
                            startDestination = Routes.ProfileScreen.route
                        ) {
                            composable(Routes.ProfileScreen.route) {
                                ProfileScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DashboardPreview() {
    DashboardScreen(navController = rememberNavController())
}





