package com.anomalydev.videogamefinder.framework.presentation.ui.components.exo_player

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.anomalydev.videogamefinder.business.domain.model.GameTrailers
import com.anomalydev.videogamefinder.util.Constants.TAG
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView

@Composable
fun Player(
    trailers: List<GameTrailers>
) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    var autoPlay = rememberSaveable { true }
    var window = rememberSaveable { 0 }
    var position = rememberSaveable { 0L }

    val player = remember {
        val player = SimpleExoPlayer.Builder(context)
            .build()
        val mediaItem = MediaItem.fromUri(trailers[0].trailer.max)
        player.setMediaItem(mediaItem)
        Log.d(TAG, "Player: ${trailers[0].trailer.max}")
        player.playWhenReady = autoPlay
        player.seekTo(window, position)
        player
    }

    fun updateState() {
        autoPlay = player.playWhenReady
        window = player.currentWindowIndex
        position = 0L.coerceAtLeast(player.contentPosition)
    }

    val playerView = remember {
        val playerView = PlayerView(context)
        lifecycle.addObserver(object: LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onStart() {
                playerView.onResume()
                player.playWhenReady = autoPlay
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            fun onStop(){
                updateState()
                playerView.onPause()
                player.playWhenReady = false
            }
        })
        playerView
    }
    
    DisposableEffect(Unit) {
        onDispose {
            updateState()
            player.release()
        }
    }

    AndroidView(
        factory = { playerView },
        modifier = Modifier
            .fillMaxWidth()
    ) {
        playerView.player = player
    }
}