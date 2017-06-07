package com.jdiazcano.modulartd.beans

data class Coin(
        override var name: String,
        override var resource: Resource = Resource(),
        override var rotationAngle: Float = 0F,
        override var animationTimer: Float = 0.2F
): MapObject