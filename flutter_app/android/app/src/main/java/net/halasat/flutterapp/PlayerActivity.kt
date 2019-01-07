package net.halasat.flutterapp

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.util.Util


class PlayerActivity : Activity() {
    val playWhenReady: Boolean = true
    val playbackPosition: Long = 0
    val currentWindow = 0
    lateinit var playerView: PlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_activity)
        Log.d("Taggg", " this is before")
        playerView = findViewById<PlayerView>(R.id.exo_player)

        Log.d("Taaag", "this is after")


    }

    private fun initializePlayer() {
        val player = ExoPlayerFactory.newSimpleInstance(this,DefaultRenderersFactory(this), DefaultTrackSelector())

        playerView.setPlayer(player)

        player.setPlayWhenReady(playWhenReady)
        player.seekTo(currentWindow, playbackPosition)

        val videoUrl: Uri = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4")
        val media: MediaSource = buildMediaSource(videoUrl)
        player.prepare(media)

    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        return ExtractorMediaSource.Factory(
                DefaultHttpDataSourceFactory("exoplayer-codelab")).createMediaSource(uri)
    }

    public override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initializePlayer()
        }
    }



}
