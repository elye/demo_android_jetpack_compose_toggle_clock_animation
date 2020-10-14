package com.example.jcsimpleclock

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.dp

@Composable
fun Clock(modifier: Modifier = Modifier,
          clockAnimator: ClockAnimator) {

    val handColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    val t = clockAnimator.getCurrentT()

    Canvas(modifier = modifier) {
        val middle = Offset(size.minDimension / 2, size.minDimension / 2)
        drawCircle(
            color = handColor,
            center = middle,
            radius = size.minDimension/2,
            style = Stroke(4.dp.toPx()),
        )

        withTransform(
                {
                    if (clockAnimator.previousT > t) {
                        clockAnimator.currentAngle += 360 / 60
                    }
                    clockAnimator.previousT = t
                    rotate(clockAnimator.currentAngle, middle)
                }, {
            drawLine(
                    cap = StrokeCap.Round,
                    strokeWidth = 12.dp.toPx(),
                    color = handColor,
                    start = middle,
                    end = Offset(size.minDimension / 2, 36.dp.toPx()))
        }
        )
        withTransform(
            {
                rotate( 360*t, middle)
            }, {
                drawLine(
                    strokeWidth = 8.dp.toPx(),
                    cap = StrokeCap.Round,
                    color = Color.Red,
                    start = middle,
                    end = Offset(size.minDimension / 2, 12.dp.toPx()))
            }
        )
    }
}

class ClockAnimator(var animatedFloat: AnimatedFloat) {

    var currentAngle = 0f
    var previousT = 0f

    fun getCurrentT() = animatedFloat.value

    fun toggleAnimation(isAnimated: Boolean) {
        if (isAnimated) {
            stopAnimation()
        } else {
            startAnimation()
        }
    }

    private fun startAnimation() {
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

    private fun stopAnimation() {
        animatedFloat.stop()
    }
}
