package net.halasat.flutterapp

import android.content.Intent
import android.os.Bundle

import io.flutter.app.FlutterActivity
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant

class MainActivity: FlutterActivity() {

  val REQUEST_CODE = 20
  val CHANNEL_ID: String = "unique"
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    GeneratedPluginRegistrant.registerWith(this)
    val mChannel: MethodChannel = MethodChannel(flutterView ,CHANNEL_ID)
    val playerIntent = Intent(this, PlayerActivity::class.java)
    val handler: MethodChannel.MethodCallHandler = MethodChannel.MethodCallHandler { call, result ->
      if( call.method.equals("launch_player")) {
        startActivityForResult(playerIntent, REQUEST_CODE)
      }
    }
    mChannel.setMethodCallHandler(handler)

  }


}
