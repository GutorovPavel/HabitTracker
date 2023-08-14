package com.example.habittracker.presentation.screens.home

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material.FractionalThreshold
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.habittracker.presentation.navigation.Screen
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {

//    val data = viewModel.state.value
    val habits = viewModel.habits.collectAsState()

    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val lazyListState = rememberLazyListState()

    var showSheet by remember {
        mutableStateOf(false)
    }

    Scaffold (
        topBar = {
             CenterAlignedTopAppBar(
                 title = {
                     Text(
                         text = "Мои привычки",
                         style = MaterialTheme.typography.headlineSmall,
                         fontWeight = FontWeight.Medium,
                     )
                 },
                 navigationIcon = {
                     IconButton(onClick = { /*TODO*/ }) {
                         Icon(imageVector = Icons.Default.Settings, contentDescription = "settings")
                     }
                 },
             )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = "Создать привычку") },
                icon = { Icon(imageVector = Icons.Default.Add, contentDescription = "add") },
                onClick = {
                    showSheet = !showSheet
                },
                shape = MaterialTheme.shapes.large,
                elevation = FloatingActionButtonDefaults.elevation(8.dp),
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        if (showSheet) {
            ModalBottomSheet(
                onDismissRequest = { showSheet = false },
                sheetState = sheetState
            ) {
                BottomSheetContent(
                    viewModel = viewModel,
                    onClose = {
                        scope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showSheet = false
                            }
                        }
                    },
                    onAdd = {
                        viewModel.addHabit()
                    }
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(12f)
                        .background(MaterialTheme.colorScheme.background),
                    contentPadding = PaddingValues(10.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    reverseLayout = true,
                    state = lazyListState
                ) {
                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                    items(
                        items = habits.value,
                        key = { habit -> habit.id.toString() }) { habit ->
                        val dismissState = rememberDismissState(
//                                confirmStateChange = {
//                                    when(it) {
//                                        DismissValue.DismissedToStart -> {
//                                            viewModel.deleteHabit(habit)
//                                            true
//                                        }
//
//                                        else -> { false }
//                                    }
//                                }
                        )
                        SwipeToDismiss(
                            state = dismissState,
                            modifier = Modifier.animateItemPlacement(),
                            directions = setOf(
                                DismissDirection.EndToStart
                            ),
                            dismissThresholds = { direction ->
                                FractionalThreshold(
                                    if (direction == DismissDirection.EndToStart) 0.4f else 0.05f
                                )
                            },
                            background = {
                                val color by animateColorAsState(
                                    when (dismissState.targetValue) {
                                        DismissValue.Default -> MaterialTheme.colorScheme.background
                                        else -> Color.Red
                                    }
                                )

                                Card(
                                    modifier = Modifier.fillMaxSize(),
                                    colors = CardDefaults.cardColors(
                                        containerColor = color,
                                    ),
                                    elevation = CardDefaults.cardElevation(0.dp)
                                ) {
                                    Row(
                                        Modifier
                                            .fillMaxSize()
                                            .padding(30.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        IconButton(
                                            modifier = Modifier
                                                .size(50.dp)
                                                .weight(1f),
                                            onClick = {
                                                scope.launch { dismissState.reset() }
                                            }
                                        ) {
                                            Icon(
                                                Icons.Default.Refresh,
                                                contentDescription = "refresh",
                                                tint = Color.White,
                                            )
                                        }
                                        IconButton(
                                            modifier = Modifier
                                                .size(50.dp)
                                                .weight(1f),
                                            onClick = {
                                                viewModel.deleteHabit(habit)
                                            }
                                        ) {
                                            Icon(
                                                Icons.Default.Delete,
                                                contentDescription = "delete",
                                                tint = Color.White,
                                            )
                                        }
                                    }
                                }
                            }
                        ) {
                            HabitItem(
                                onClick = {
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        key = "habit",
                                        value = it
                                    )
                                    navController.navigate(Screen.Detail.route)
                                },
                                habit = habit
                            )
                        }
                    }
                }
            }

        }
    }
}

