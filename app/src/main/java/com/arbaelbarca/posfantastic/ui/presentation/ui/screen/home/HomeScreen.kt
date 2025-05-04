package com.arbaelbarca.posfantastic.ui.presentation.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.arbaelbarca.posfantastic.ui.model.response.UsersResponse
import com.arbaelbarca.posfantastic.ui.presentation.state.UiState
import com.arbaelbarca.posfantastic.ui.presentation.viewmodel.UsersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeFragment() {
    val userViewModel = hiltViewModel<UsersViewModel>()
    val stateUsers = userViewModel.stateUsers.collectAsState()

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = { Text("Home") }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            when (val uiState = stateUsers.value) {
                is UiState.Error -> {

                }

                is UiState.Loading -> {

                }

                is UiState.Success -> {
                    val getDataUser = uiState.data
                    ShowListUsersScreen(getDataUser)
                }
            }
        }
    }

    // ✅ Trigger fetch once when Composable is first composed
    LaunchedEffect(Unit) {
        userViewModel.fetchDataUsers()
    }

}

@Composable
fun ShowListUsersScreen(listUserItem: List<UsersResponse.UsersResponseItem>) {
    LazyColumn {
        items(listUserItem.size) { index ->
            val item = listUserItem[index]
            Text(text = item.name.toString())
        }
    }
}