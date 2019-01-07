package net.halasat.flutterapp

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.hls.playlist.DefaultHlsPlaylistParserFactory


import com.google.android.exoplayer2.util.Util



class PlayerActivity : Activity() {
    var playWhenReady: Boolean = true
    var playbackPosition: Long = 0
    var currentWindow = 0
    lateinit var playerView: PlayerView
    var player: SimpleExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_activity)
        Log.d("Taggg", " this is before")
        playerView = findViewById<PlayerView>(R.id.exo_player)
//        fullscreenPlayer()

        Log.d("Taaag", "this is after")


    }

    private fun initializePlayer() {
         player = ExoPlayerFactory.newSimpleInstance(this,DefaultRenderersFactory(this), DefaultTrackSelector())
        playerView.setPlayer(player)
        player?.setPlayWhenReady(playWhenReady)
        player?.seekTo(currentWindow, playbackPosition)

        val videoUrl: Uri = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4")
        val media: MediaSource = buildMediaSource(videoUrl)
        player?.prepare(media)

    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        return ExtractorMediaSource.Factory(
                DefaultHttpDataSourceFactory("exoplayer-codelab")).createMediaSource(uri)
    }

    public override fun onStart() {
        super.onStart()
//        fullscreenPlayer()
        if (Util.SDK_INT > 23) {
            initializePlayer()
        }
    }

    public override fun onResume() {
        super.onResume()
//        fullscreenPlayer()
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    public override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    public override fun onPause() {
        super.onPause()
        releasePlayer()

    }

//    public fun fullscreenPlayer() {
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//    }


    private fun releasePlayer() {
        if(player != null) {
            currentWindow = player?.currentWindowIndex!!
            playbackPosition = player?.currentPosition!!
            playWhenReady = player?.playWhenReady!!
            player?.release()
            player = null
        }
    }



}
