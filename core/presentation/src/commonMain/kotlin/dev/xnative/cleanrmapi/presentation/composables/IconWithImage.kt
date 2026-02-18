package dev.xnative.cleanrmapi.presentation.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.xnative.cleanrmapi.presentation.theme.OnBackgroundColor
import dev.xnative.cleanrmapi.presentation.theme.SurfaceColor


@Composable
fun IconWithImage(
    modifier: Modifier = Modifier,
    orientation: Orientation = Orientation.Vertical,
    imageVector: ImageVector,
    text: String,
    backgroundColor: Color = SurfaceColor,
    color: Color = OnBackgroundColor,
) {

    when (orientation) {

        Orientation.Vertical ->
            Column(
                modifier = modifier
                    .background(backgroundColor, RoundedCornerShape(8.dp))
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = { Content(color, imageVector, text) }
            )

        Orientation.Horizontal ->
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically,
                content = { Content(color, imageVector, text) }
            )

    }

}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Content(
    color: Color,
    imageVector: ImageVector,
    text: String
) {
    Image(
        modifier = Modifier,
        imageVector = imageVector,
        contentDescription = "",
        colorFilter = ColorFilter.tint(color)
    )

    Text(
        modifier = Modifier.basicMarquee(iterations = Int.MAX_VALUE),
        text = text,
        color = color,
        fontSize = 14.sp
    )

}