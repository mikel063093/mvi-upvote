package com.mvi.upvote.intent

import com.mvi.upvote.common.Intent
import com.mvi.upvote.common.IntentFactory
import com.mvi.upvote.model.UpvoteModel
import com.mvi.upvote.model.UpvoteModelStore
import com.mvi.upvote.view.MainViewEvent
import com.mvi.upvote.view.MainViewEvent.LoveItClick
import com.mvi.upvote.view.MainViewEvent.ThumbsUpClick

object MainViewIntentFactory : IntentFactory<MainViewEvent> {

    override fun process(viewEvent: MainViewEvent) {
        UpvoteModelStore.process(toIntent(viewEvent))
    }

    private fun toIntent(viewEvent: MainViewEvent): Intent<UpvoteModel> {
        return when (viewEvent) {
            LoveItClick -> AddHeart()
            ThumbsUpClick -> AddThumbsUp()
        }

    }

    class AddHeart :Intent<UpvoteModel> {
        override fun reduce(oldState: UpvoteModel): UpvoteModel {
            return oldState.copy(hearts = oldState.hearts + 1)
        }
    }

    class AddThumbsUp :Intent<UpvoteModel> {
        override fun reduce(oldState: UpvoteModel) = oldState.copy(thumbs = oldState.thumbs + 1)
    }

}

