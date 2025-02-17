package com.example.drinkinggameapp.Views

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutQuad
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.example.drinkinggameapp.Models.SpinWheelItem
import com.example.drinkinggameapp.Models.getDegreesPerItem
import com.example.drinkinggameapp.R
import com.example.drinkinggameapp.rememberSpinWheelState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class WheelSpinCompleteFile {

    @Composable
    fun SpinWheel(
        modifier: Modifier = Modifier,
        items: List<com.example.drinkinggameapp.Models.SpinWheelItem>,
    ) {
        BoxWithConstraints(
            modifier = modifier
        ) {
            val degreesPerItems = items.getDegreesPerItem()
            val size = min(this.maxHeight, this.maxWidth)
            val brushEnd = with(LocalDensity.current) { size.toPx() / 2f }

            items.forEachIndexed { index, item ->
                SpinWheelSlice(
                    modifier = Modifier.rotate(degrees = degreesPerItems * index),
                    size = size,
                    brush = Brush.verticalGradient(
                        colors = item.colors.map { color -> Color(color.toArgb()) },
                        endY = brushEnd
                    ),
                    degree = degreesPerItems,
                    content = item.content
                )
            }
        }
    }

    //@Preview
    @Composable
    fun SpinWheelComponent(
        spinWheelState: SpinWheelState
    ) {
//        val items = remember {
//            persistentListOf(
//                com.example.drinkinggameapp.Models.SpinWheelItem(
//                    colors = persistentListOf(Color.Red, Color.Red),
//                    content = { Text("20$") }
//                ),
//                com.example.drinkinggameapp.Models.SpinWheelItem(
//                    colors = persistentListOf(Color.Green, Color.Green),
//                    content = { Text("40$") }
//                ),
//                com.example.drinkinggameapp.Models.SpinWheelItem(
//                    colors = persistentListOf(Color.Magenta, Color.Magenta),
//                    content = { Text("60$") }
//                ),
//                com.example.drinkinggameapp.Models.SpinWheelItem(
//                    colors = persistentListOf(Color.Gray, Color.Gray),
//                    content = { Text("80$") }
//                ),
//                com.example.drinkinggameapp.Models.SpinWheelItem(
//                    colors = persistentListOf(Color.Blue, Color.Blue),
//                    content = { Text("100$") }
//                ),
//                com.example.drinkinggameapp.Models.SpinWheelItem(
//                    colors = persistentListOf(Color.White, Color.White),
//                    content = { Text("200$") }
//                ),
//            )
//        }
//
//        //Dein SpinWheelSate mit Beispielbildern und Werten
//        val spinWheelState = rememberSpinWheelState(
//            items = items,
//            backgroundImage = R.drawable.ic_launcher_background,
//            centerImage = R.drawable.kreis,
//            indicatorImage = R.drawable.blitz,
//            stopDuration = 3.seconds,
//            stopNbTurn = 3f,
//        )

        Box(
            modifier = Modifier
                //.fillMaxSize()
                //.fillMaxWidth()
                .aspectRatio(0.769f)
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = spinWheelState.backgroundImage),
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
            )

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 10.dp, top = 20.dp)
                //.padding(bottom = 10.dp, top = 180.dp, start = 10.dp, end = 10.dp)
            ) {
                val imageSize = this.maxHeight.times(0.14f)

                //Das Rad mit Rotation
                Box(
                    modifier = Modifier
                        .align(alignment = Alignment.Center)
                        .size(this.maxHeight.times(0.82f))
                ) {
                    SpinWheel(
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer {
                                rotationZ = spinWheelState.rotation.value
                            },
                        items = spinWheelState.items
                    )

                    SpinTheWheelButton {
                        //Hier startest du den Spin, indem du die Stop-Logik anwendest
                        val randomSection = (0 until spinWheelState.items.size).random()
                        spinWheelState.stoppingWheel(randomSection)
                    }
                }

                // Indikator über dem Rad
                Image(
                    modifier = Modifier
                        .size(imageSize.times(1.2f)) // Falls nötig, die Größe leicht erhöhen
                        .align(Alignment.TopCenter)
                        .padding(top = 0.dp)
                        .offset(
                            y = (-23).dp,
                            x = (-10).dp
                        ), // Hiermit wird das Bild über das Rad geschoben
                    painter = painterResource(id = spinWheelState.indicatorImage),
                    contentDescription = null
                )
            }
        }
    }

    //@Preview
    @Composable
    fun SpinWheelComponentPreview() {
        val items = remember {
            persistentListOf(
                com.example.drinkinggameapp.Models.SpinWheelItem(
                    colors = persistentListOf(Color.Red, Color.Red),
                    content = { Text("20$") }
                ),
                com.example.drinkinggameapp.Models.SpinWheelItem(
                    colors = persistentListOf(Color.Green, Color.Green),
                    content = { Text("40$") }
                ),
                com.example.drinkinggameapp.Models.SpinWheelItem(
                    colors = persistentListOf(Color.Magenta, Color.Magenta),
                    content = { Text("60$") }
                ),
                com.example.drinkinggameapp.Models.SpinWheelItem(
                    colors = persistentListOf(Color.Gray, Color.Gray),
                    content = { Text("80$") }
                ),
                com.example.drinkinggameapp.Models.SpinWheelItem(
                    colors = persistentListOf(Color.Blue, Color.Blue),
                    content = { Text("100$") }
                ),
                com.example.drinkinggameapp.Models.SpinWheelItem(
                    colors = persistentListOf(Color.White, Color.White),
                    content = { Text("200$") }
                ),
            )
        }

        //Dein SpinWheelSate mit Beispielbildern und Werten
        val spinWheelState = rememberSpinWheelState(
            items = items,
            backgroundImage = R.drawable.ic_launcher_background,
            centerImage = R.drawable.kreis,
            indicatorImage = R.drawable.blitz,
            stopDuration = 3.seconds,
            stopNbTurn = 3f,
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
            //.aspectRatio(0.769f)
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = spinWheelState.backgroundImage),
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
            )

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 10.dp, top = 180.dp, start = 10.dp, end = 10.dp)
            ) {
                val imageSize = this.maxHeight.times(0.14f)

                //Das Rad mit Rotation
                Box(
                    modifier = Modifier
                        .align(alignment = Alignment.Center)
                        .size(this.maxHeight.times(0.82f))
                ) {
                    SpinWheel(
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer {
                                rotationZ = spinWheelState.rotation.value
                            },
                        items = spinWheelState.items
                    )

                    SpinTheWheelButton {
                        //Hier startest du den Spin, indem du die Stop-Logik anwendest
                        val randomSection = (0 until spinWheelState.items.size).random()
                        spinWheelState.stoppingWheel(randomSection)
                    }
                }

                // Indikator über dem Rad
                Image(
                    modifier = Modifier
                        .size(imageSize.times(1.2f)) // Falls nötig, die Größe leicht erhöhen
                        .align(Alignment.TopCenter)
                        .padding(top = 0.dp)
                        .offset(
                            y = (-35).dp,
                            x = (17).dp
                        ), // Hiermit wird das Bild über das Rad geschoben
                    painter = painterResource(id = spinWheelState.indicatorImage),
                    contentDescription = null
                )
            }
        }
    }

    @Composable
    fun SpinTheWheelButton(
        onClick: () -> Unit
    ) {
        val image: Painter = painterResource(R.drawable.kreis)

        Box(
            modifier = Modifier
                .offset(x = (185).dp, y = (185).dp)
        ) {
            Image(
                painter = image,
                contentDescription = "WheelSpinCircle",
                modifier = Modifier
                    //.offset(x = (190).dp, y = (190).dp)
                    .size(70.dp)
                    .clickable(onClick = onClick)
            )
            Text(
                text = "Spin the Wheel",
                style = TextStyle.Default.copy(
                    lineBreak = LineBreak.Paragraph,
                    textAlign = TextAlign.Center
                ),
                color = Color.White,
                modifier = Modifier
                    .width(40.dp)
                    //.height(20.dp)
                    .offset(x = (15).dp, y = (10).dp)
            )
        }
    }

    @Composable
    fun SpinWheelSlice(
        modifier: Modifier = Modifier,
        size: Dp,
        brush: Brush,
        degree: Float,
        content: @Composable () -> Unit,
    ) {
        Box(
            modifier = modifier
                .size(size)
                .drawBehind {
                    drawArc(
                        brush = brush,
                        startAngle = -90f - (degree / 2),
                        sweepAngle = degree,
                        useCenter = true,
                    )
                }
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 10.dp)
            ) {
                content()
            }
        }
    }


    //Data
    @Stable
    data class SpinWheelItem(
        val colors: PersistentList<Color>,
        val content: @Composable () -> Unit,
    )

    internal fun List<SpinWheelItem>.getDegreesPerItem(): Float = 360f / this.size.toFloat()
}

@Stable
data class SpinWheelState(
    internal val items: PersistentList<com.example.drinkinggameapp.Models.SpinWheelItem>,
    @DrawableRes internal val backgroundImage: Int,
    @DrawableRes internal val centerImage: Int,
    @DrawableRes internal val indicatorImage: Int,
    private val initSpinWheelSection: Int?,
    private val onSpinningFinished: (() -> Unit)?,
    private val stopDuration: Duration,
    private val stopNbTurn: Float,
    private val rotationPerSecond: Float,
    private val scope: CoroutineScope,
) {
    internal val rotation = Animatable(0f)

    init {
        initSpinWheelSection?.let {
            goto(it)
        } ?: launchInfinite()
    }

    fun stoppingWheel(sectionStop: Int) {
        if (sectionStop !in items.indices) {
            Log.e("spin-wheel", "cannot stop wheel, section $sectionStop not exists in items")
            return
        }

        scope.launch {
            val destinationDegree = getDegreeFromSection(items, sectionStop)

            rotation.animateTo(
                targetValue = rotation.value + (stopNbTurn * 360f) + destinationDegree + (360f - (rotation.value % 360f)),
                animationSpec = tween(
                    durationMillis = stopDuration.inWholeMilliseconds.toInt(),
                    easing = EaseOutQuad
                )
            )
        }
    }

    fun goto(section: Int) {
        scope.launch {
            if (section !in items.indices) {
                Log.e(
                    "spin-wheel",
                    "cannot goto specific section of wheel, section $section not exists in items"
                )
                return@launch
            }

            val positionDegree = getDegreeFromSection(items, section)
            rotation.snapTo(positionDegree)
        }
    }

    fun launchInfinite() {
        scope.launch {
            rotation.animateTo(
                targetValue = rotation.value + 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = (rotationPerSecond * 1000f).toInt(),
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    fun getDegreeFromSection(
        items: PersistentList<com.example.drinkinggameapp.Models.SpinWheelItem>,
        section: Int
    ): Float {
        val pieDegree = 360f / items.size
        return pieDegree * section.times(-1)
    }

    fun getDegreeFromSectionWithRandom(
        items: PersistentList<com.example.drinkinggameapp.Models.SpinWheelItem>,
        section: Int
    ): Float {
        val pieDegree = 360f / items.size
        val exactDegree = pieDegree * section.times(-1)

        val pieReduced = pieDegree * 0.9f //to avoid stop near border
        val multiplier = if (Random.nextBoolean()) 1f else -1f //before or after exact degree
        val randomDegrees = Random.nextDouble(0.0, pieReduced / 2.0)
        return exactDegree + (randomDegrees.toFloat() * multiplier)
    }
}