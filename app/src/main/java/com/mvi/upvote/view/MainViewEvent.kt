package com.mvi.upvote.view

sealed class MainViewEvent {
    object ThumbsUpClick : MainViewEvent()
    object LoveItClick : MainViewEvent()
}