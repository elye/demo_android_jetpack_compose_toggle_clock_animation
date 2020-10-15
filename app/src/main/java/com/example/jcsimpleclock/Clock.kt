package com.example.jcsimpleclock

import android.util.Log
import androidx.compose.animation.animatedFloat
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.dp

var currentAngle = 0f
var previousT = 0f

@Composable
fun Clock(modifier: Modifier = Modifier, animationStart: MutableState<Boolean>) {

    val animatedFloat = animatedFloat(initVal = 0f)
    val handColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    Canvas(modifier = modifier) {
        if (animatedFloat.isRunning && !animationStart.value) {
            animatedFloat.stop()
        } else if(!animatedFloat.isRunning && animationStart.value) {
            animatedFloat.snapTo(0f)
            animatedFloat.animateTo(
                    targetValue = 1f,
                    anim = repeatable(
                            iterations = AnimationConstants.Infinite,
                            animation = tween(durationMillis = 2000, easing = LinearEasing),
                    )
            )
            currentAngle = 0f
            previousT = 0f
        }

        val t = animatedFloat.value


        val middle = Offset(size.minDimension / 2, size.minDimension / 2)
        drawCircle(
                color = handColor,
                center = middle,
                radius = size.minDimension / 2,
                style = Stroke(4.dp.toPx()),
        )

        withTransform(
                {
                    if (previousT > t) {
                        currentAngle += 360 / 60
                    }

                    previousT = t
                    rotate(currentAngle, middle)
                }, {
            drawLine(
                    cap = StrokeCap.Round,
                    strokeWidth = 12.dp.toPx(),
                    color = handColor,
                    start = middle,
                    end = Offset(size.minDimension / 2, 36.dp.toPx()))
        }
        )
        withTransform({
            rotate(360 * t, middle)
        }, {
            drawLine(strokeWidth = 8.dp.toPx(),
                    cap = StrokeCap.Round,
                    color = Color.Red,
                    start = middle,
                    end = Offset(size.minDimension / 2, 12.dp.toPx()))
        }
        )
    }
}

