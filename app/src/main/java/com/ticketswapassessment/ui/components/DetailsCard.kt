package com.ticketswapassessment.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ticketswapassessment.R
import com.ticketswapassessment.network.response.ArtObjectDetail
import com.ticketswapassessment.util.applyAnimationScopes

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DetailsCard(
    sharedTransitionScope: SharedTransitionScope,
    artObject: ArtObjectDetail,
    animatedContentScope: AnimatedContentScope,
    textColor: Color
) {
    val context = LocalContext.current

    Column(
        Modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxHeight(), verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            modifier = Modifier.applyAnimationScopes(sharedTransitionScope, animatedContentScope, "title-${artObject.id ?: ""}"),
            text = artObject.title ?: "Untitled", style = MaterialTheme.typography.displaySmall, color = textColor
        )
        val authorAndYear = artObject.dating?.presentingDate + " | " + artObject.principalOrFirstMaker

        Text(text = authorAndYear, style = MaterialTheme.typography.titleMedium, color = textColor)

        Spacer(modifier = Modifier.height(4.dp))

        if (!artObject.physicalMedium.isNullOrEmpty()) {
            DetailCard(textColor, "Material: ${artObject.physicalMedium}")
        }

        if(!artObject.dimensions.isNullOrEmpty()){
            var width = "-"
            var height = "-"
            var depth = "-"
            var weight = ""
            artObject.dimensions.forEach {
                when(it.type){
                    "width"  -> width  = "${it.value ?: "N/A"} ${it.unit ?: "N/A"}"
                    "height" -> height = "${it.value ?: "N/A"} ${it.unit ?: "N/A"}"
                    "depth"  -> depth  = "${it.value ?: "N/A"} ${it.unit ?: "N/A"}"
                    "weight" -> weight = "${it.value ?: "N/A"} ${it.unit ?: "N/A"}"
                    else -> { /* nothing */ }
                }
            }
            DetailCard(textColor, "height $height width $width depth $depth")

            if(weight.isNotEmpty()){
                DetailCard(textColor, "weight $weight")
            }
        }

        Text(text = artObject.label?.description ?: "No description", style = MaterialTheme.typography.bodyMedium, color = textColor)

        Box(Modifier.fillMaxWidth().padding(top = 16.dp), contentAlignment = Alignment.Center) {
            ElevatedButton(onClick = {
                val url = "http://www.rijksmuseum.nl/en/collection/" + artObject.objectNumber
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            }) {
                Icon(painter = painterResource(id = R.drawable.ic_open_in), contentDescription = "Open in browser")
                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = "Open in browser")
            }
        }

        Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.systemBars))
    }
}

@Composable
private fun DetailCard(textColor: Color, value: String) {
    OutlinedCard(
        modifier = Modifier.offset(x = (-4).dp),
        colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor
        )
    }
    Spacer(modifier = Modifier.height(4.dp))
}