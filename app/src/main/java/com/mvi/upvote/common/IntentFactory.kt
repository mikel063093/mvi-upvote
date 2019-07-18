package com.mvi.upvote.common

interface IntentFactory<E> {
    fun process(viewEvent:E)
}