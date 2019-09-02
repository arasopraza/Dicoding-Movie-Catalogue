package com.smk.informatics.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.smk.informatics.R;
import com.smk.informatics.notifications.MovieDailyReceiver;
import com.smk.informatics.notifications.MovieReleaseReceiver;
import com.smk.informatics.setting.SettingPreference;

import java.util.Objects;

public class SettingActivity extends AppCompatActivity {
    private Switch mSwitchReminder;
    private Switch mSwitchRelease;
    private MovieDailyReceiver mMovieDailyReceiver;
    private MovieReleaseReceiver mMovieReleaseReceiver;
    private SettingPreference mSettingPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mSwitchReminder = findViewById(R.id.switch_daily_reminder);
        mSwitchRelease = findViewById(R.id.switch_release_today);
        Button button = findViewById(R.id.button_change_language);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Setting");

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_24dp);

        mMovieDailyReceiver = new MovieDailyReceiver();
        mMovieReleaseReceiver = new MovieReleaseReceiver();

        mSettingPreference = new SettingPreference(this);
        setSwitchRelease();
        setSwitchReminder();

        // Switch Reminder OnClick
        mSwitchReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSwitchReminder.isChecked()) {
                    mMovieDailyReceiver.setDailyAlarm(getApplicationContext());
                    mSettingPreference.setDailyReminder(true);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.set_daily_reminder), Toast.LENGTH_SHORT).show();
                } else {
                    mMovieDailyReceiver.cancelAlarm(getApplicationContext());
                    mSettingPreference.setDailyReminder(false);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.cancel_daily_reminder), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Switch Release OnClick
        mSwitchRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSwitchRelease.isChecked()) {
                    mMovieReleaseReceiver.setReleaseAlarm(getApplicationContext());
                    mSettingPreference.setReleaseReminder(true);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.set_release_reminder), Toast.LENGTH_SHORT).show();
                } else {
                    mMovieReleaseReceiver.cancelAlarm(getApplicationContext());
                    mSettingPreference.setReleaseReminder(false);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.cancel_release_reminder), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Button Change Language OnClick
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setSwitchReminder() {
        if (mSettingPreference.getDailyReminder()) mSwitchReminder.setChecked(true);
        else mSwitchReminder.setChecked(false);
    }

    private void setSwitchRelease() {
        if (mSettingPreference.getReleaseReminder()) mSwitchRelease.setChecked(true);
        else mSwitchRelease.setChecked(false);
    }
}
