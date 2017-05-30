package com.jdiazcano.modulartd.beans

data class Coin(
        override var name: String,
        override var resource: Resource,
        override var rotationAngle: Float,
        override var animationTimer: Float
): MapObject