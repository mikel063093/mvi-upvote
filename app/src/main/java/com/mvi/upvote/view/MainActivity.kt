package com.mvi.upvote.view

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding2.view.clicks
import com.mvi.upvote.R
import com.mvi.upvote.common.ModelSubscriber
import com.mvi.upvote.common.ViewEventObservable
import com.mvi.upvote.intent.MainViewIntentFactory
import com.mvi.upvote.model.UpvoteModel
import com.mvi.upvote.model.UpvoteModelStore
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign

class MainActivity : AppCompatActivity(), ViewEventObservable<MainViewEvent>, ModelSubscriber<UpvoteModel> {

    private lateinit var counterTextView: TextView

    private lateinit var heartButton: Button

    private lateinit var thumbButton: Button

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        counterTextView = findViewById(R.id.counterTextView)
        heartButton = findViewById(R.id.heartButton)
        thumbButton = findViewById(R.id.thumbButton)
    }

    override fun onResume() {
        super.onResume()
        disposables += viewEvents().subscribe(MainViewIntentFactory::process) // Output
        disposables += UpvoteModelStore.modelState().subscribeToModel() // Input
    }

    override fun onPause() {
        super.onPause()
        disposables.clear()
    }

    override fun viewEvents(): Observable<MainViewEvent> {
        return Observable.merge(
            heartButton.clicks().map { MainViewEvent.LoveItClick },
            thumbButton.clicks().map { MainViewEvent.ThumbsUpClick }
        )
    }

    override fun Observable<UpvoteModel>.subscribeToModel(): Disposable {
        return subscribe { model ->
            counterTextView.text =
                resources.getString(
                    R.string.upvotes,
                    model.hearts,
                    model.thumbs
                )
        }
    }

}
