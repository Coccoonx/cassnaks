package co.wouri.coaze.uis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import co.wouri.coaze.R;
import co.wouri.coaze.utils.LoadingTask.LoadingTaskFinishedListener;
import co.wouri.coaze.utils.UIUtils;

public class SplashScreenActivity extends Activity implements LoadingTaskFinishedListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Show the activity_splash screen
        setContentView(R.layout.activity_splash);

        // Find the progress bar
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.activity_splash_progress_bar);

        TextView appName = (TextView) findViewById(R.id.appName);
        TextView slogan = (TextView) findViewById(R.id.appSlogan);

        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, appName, slogan);

        // Start your loading
//        new LoadingTask(progressBar, this).execute("www.google.co.uk"); // Pass in whatever you need a url is just an example we don't use it in this tutorial
        completeSplash();
    }

    // This is the callback for when your async task has finished
    @Override
    public void onTaskFinished() {
        completeSplash();
    }

    private void completeSplash() {
        startApp();
        finish(); // Don't forget to finish this Splash Activity so the user can't return to it!
    }

    private void startApp() {
        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(intent);
    }
}