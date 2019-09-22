package com.example.utaputranto.thirdsubmission.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.example.utaputranto.thirdsubmission.preference.AppPreference;
import com.example.utaputranto.thirdsubmission.R;
import com.example.utaputranto.thirdsubmission.reminder.MovieReminder;


public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private final MovieReminder movieReminder = new MovieReminder();
    private Switch dailySwitch, upcomingSwitch;
    private boolean isUpcoming, isDaily;

    private AppPreference appPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        dailySwitch = findViewById(R.id.switch_daily);
        upcomingSwitch = findViewById(R.id.switch_upcoming);
        dailySwitch.setOnClickListener(this);
        upcomingSwitch.setOnClickListener(this);

        appPreference = new AppPreference(this);

        setEnabledisableNotif();

    }

    void setEnabledisableNotif() {
        if (appPreference.isDaily()) {
            dailySwitch.setChecked(true);
        } else {
            dailySwitch.setChecked(false);
        }
        if (appPreference.isUpcoming()) {
            upcomingSwitch.setChecked(true);
        } else {
            upcomingSwitch.setChecked(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_daily:
                isDaily = dailySwitch.isChecked();
                if (isDaily) {
                    String timeDaily = "07:00";
                    dailySwitch.setEnabled(true);
                    appPreference.setDaily(isDaily);
                    movieReminder.setRepeatingAlarm(this, MovieReminder.TYPE_DAILY, timeDaily, getString(R.string.msg_daily_reminder), getString(R.string.daily_reminder_message));
                } else {
                    dailySwitch.setChecked(false);
                    appPreference.setDaily(isDaily);
                    movieReminder.cancelAlarm(this, MovieReminder.TYPE_DAILY);
                }
                break;
            case R.id.switch_upcoming:
                isUpcoming = upcomingSwitch.isChecked();
                if (isUpcoming) {
                    String timeRelease = "08:00";
                    upcomingSwitch.setEnabled(true);
                    appPreference.setUpcoming(isUpcoming);
                    movieReminder.setRepeatingAlarm(this, MovieReminder.TYPE_RELEASE, timeRelease, getString(R.string.release_reminder_title), getString(R.string.release_reminder_message));
                } else {
                    upcomingSwitch.setChecked(false);
                    appPreference.setUpcoming(isUpcoming);
                    movieReminder.cancelAlarm(this, MovieReminder.TYPE_RELEASE);
                }
                break;
        }
    }

}
