package com.mvi.upvote.common

interface Intent<T> {
    fun reduce(oldState: T): T
}


fun <T> intent(block: T.() -> T) = BlockIntent(block)

class BlockIntent<T>(val block:T.()->T) : Intent<T> {
    override fun reduce(oldState: T): T = block(oldState)
}

