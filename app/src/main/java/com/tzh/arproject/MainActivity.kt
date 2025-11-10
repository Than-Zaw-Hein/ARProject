package com.tzh.arproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tzh.arproject.solarSystem.SolarSystemScreen
import com.tzh.arproject.ui.theme.ARProjectTheme

data class ArModel(
    val name: String,
    val path: String,
    @DrawableRes val imageResId: Int
)

private val arModels = listOf(
    ArModel("SUN", "models/sun.glb", R.drawable.sun),
    ArModel(
        "EARTH",
        "models/earth.glb", R.drawable.earth
    ),

    )

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ARProjectTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { CenterAlignedTopAppBar(title = { Text("AR Preview") }) }) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var selectedArModel by remember() { mutableStateOf<String?>(null) }

    AnimatedContent(selectedArModel, modifier = modifier) { it ->

        if (it.isNullOrEmpty()) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(count = 2),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(arModels) { item ->
                    ArModelCard(modifier = Modifier.fillMaxWidth(), item) {
                        selectedArModel = item.path
                    }
                }
            }
        } else {
            BackHandler() { selectedArModel = null }
            SolarSystemScreen(it)
        }
    }
}

@Composable
fun ArModelCard(modifier: Modifier = Modifier, armodel: ArModel, onClick: () -> Unit) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(
            bottomStart = 8.dp,
            bottomEnd = 8.dp
        ),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(armodel.imageResId),
                contentDescription = armodel.path,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.FillBounds
            )
            Text(
                armodel.name, style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.Black
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ARProjectTheme {
        SolarSystemScreen("models/sun.glb")
    }
}