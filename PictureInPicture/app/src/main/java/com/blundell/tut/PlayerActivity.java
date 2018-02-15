package com.blundell.tut;

import android.app.PendingIntent;
import android.app.PictureInPictureParams;
import android.app.RemoteAction;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Rational;
import android.view.View;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.util.Collections;

public class PlayerActivity extends AppCompatActivity {

    private View buttonLaunchPip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        buttonLaunchPip = findViewById(R.id.button_launch_pip);
        SimpleExoPlayerView simpleExoPlayerView = findViewById(R.id.exoplayer);
        VideoPlayer.play(this, "boat.mp4", simpleExoPlayerView);
    }

    public void onLaunchPiPClick(View view) {
        Icon icon = Icon.createWithResource(this, android.R.drawable.ic_dialog_info);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.license)));
        String title = "info";
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 123, intent, 0);
        PictureInPictureParams pictureInPictureParams = new PictureInPictureParams.Builder()
            .setActions(Collections.singletonList(new RemoteAction(icon, title, title, pendingIntent)))
            .setAspectRatio(Rational.parseRational("16:9"))
            .build();

        enterPictureInPictureMode(pictureInPictureParams);
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
        if (isInPictureInPictureMode) {
            buttonLaunchPip.setVisibility(View.GONE);
        } else {
            buttonLaunchPip.setVisibility(View.VISIBLE);
        }
    }
}
