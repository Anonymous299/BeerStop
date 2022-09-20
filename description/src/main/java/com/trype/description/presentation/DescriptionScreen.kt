package com.trype.description.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.trype.core.extensions.collectAsStateWithLifecycle
import com.trype.core.theme.Playfair
import com.trype.core.theme.Raleway
import com.trype.description.DescriptionViewModel
import com.trype.description.R

//TODO use paged list not here but somewhere
//TODO clean up and remove hardcoded string values
@Composable
fun DescriptionScreen(
    alcoholId: Int,
    descriptionViewModel: DescriptionViewModel = hiltViewModel()
) {
    val uiState by descriptionViewModel.uiState.collectAsStateWithLifecycle()
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val smallDescriptionHeading = TextStyle(
        fontFamily = Playfair,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp
    )
    uiState.alcohol?.let {
        Column(modifier = Modifier.padding(top = 42.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_arrow_back_ios_24),
                contentDescription = "menu", modifier = Modifier.padding(start = 29.5.dp)
            )
            Row(modifier = Modifier.padding(start = screenWidth * 0.3f, end = 15.dp, top = 15.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.Center) {
                Text(
                    text = it.title, style = TextStyle(
                        fontFamily = Playfair,
                        fontWeight = FontWeight(700),
                        fontSize = 32.sp
                    ), textAlign = TextAlign.Center
                )
            }
            Box{
                Column(
                    modifier = Modifier.fillMaxHeight(),
//                verticalArrangement = Arrangement.Center
                ) {


                    val offset = if (it.volume >= 1000) {
                        -(320.dp - 0.25 * screenWidth)
                    } else {
                        -(280.dp - 0.25 * screenWidth)
                    }

                    AsyncImage(
                        model = it.image_url,
                        contentDescription = it.title, contentScale = ContentScale.None,
                        modifier = Modifier.offset(x = offset)
                    )


                }

                Column(
                    modifier = Modifier
                        .padding(start = screenWidth * 0.3f, end = 15.dp, top = 19.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
                ) {

it.brand?.let { brand ->Text(
    text = brand, style = MaterialTheme.typography.h2,
    textAlign = TextAlign.Center
) }

                    val ratingFormat = if (it.rating.isNullOrEmpty()) {
                        0f
                    } else {
                        it.rating!!.toFloat()
                    }
                    val rating: Float by remember { mutableStateOf(ratingFormat) }

                    RatingBar(
                        value = rating,
                        config = RatingBarConfig()
                            .style(RatingBarStyle.HighLighted),
                        onValueChange = {},
                        onRatingChanged = {},
                        modifier = Modifier.padding(top = 17.5.dp)
                    )
                    Text(
                        text = "Category", style = smallDescriptionHeading,
                        modifier = Modifier.padding(top = 19.dp)
                    )
                    Text(
                        text = it.category, style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(top = 7.dp)
                    )
                    Text(
                        text = "Subcategory", style = MaterialTheme.typography.h4,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    Text(
                        text = it.subcategory, style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(top = 7.dp)
                    )
                    Text(
                        text = "$ ${String.format("%.2f", it.price)}", style = TextStyle(
                            fontFamily = Playfair, fontWeight = FontWeight.Bold, fontSize = 40.sp
                        ),
                        modifier = Modifier.padding(top = 12.dp)
                    )
                    Text(
                        text = "Efficiency", style = TextStyle(
                            fontFamily = Playfair,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                        modifier = Modifier.padding(top = 19.dp)
                    )
                    Text(
                        text = "$ ${String.format("%.2f", it.price_index)}/mL", style = TextStyle(
                            fontFamily = Playfair,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            color = Color.Green
                        ),
                        modifier = Modifier.padding(top = 7.dp)
                    )
                    Text(
                        text = "Description", style = smallDescriptionHeading,
                        modifier = Modifier.padding(top = 19.dp)
                    )
                    Text(
                        text = it.description, style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(top = 7.dp), textAlign = TextAlign.Center
                    )
                    Button(
                        onClick = {
                        }, modifier = Modifier.padding(top = 27.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(text = "View on site")
                    }

                }
            }
        }
    }
}
