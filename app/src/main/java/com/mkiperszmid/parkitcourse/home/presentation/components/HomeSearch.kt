package com.mkiperszmid.parkitcourse.home.presentation.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Route
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mkiperszmid.parkitcourse.ui.theme.AccentColor

@Composable
fun HomeSearch(
    distance: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF111018),
                        Color(0xFF23212D),
                    )
                )
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(11.dp)) {
            Text(
                text = "Your Vehicle",
                color = Color(0x47FFFFFF),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Route,
                    contentDescription = null,
                    tint = AccentColor
                )
                Text(text = "$distance away", fontSize = 18.sp, color = Color.White)
            }
        }
        HomeButton(
            onClick = onClick,
            text = "Stop Searching",
            imageVector = null
        )
    }
}

@Preview
@Composable
fun HomeSearchPreview() {
    HomeSearch("1.3km", {}, modifier = Modifier.fillMaxWidth())
}
