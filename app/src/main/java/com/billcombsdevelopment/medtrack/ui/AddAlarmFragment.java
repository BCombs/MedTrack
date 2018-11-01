/*
 * Copyright (c) 2018 - Bill Combs
 */

package com.billcombsdevelopment.medtrack.ui;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.billcombsdevelopment.medtrack.R;
import com.billcombsdevelopment.medtrack.model.MedAlarm;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddAlarmFragment extends Fragment {

    @BindView(R.id.time_picker)
    TimePicker mTimePicker;
    @BindView(R.id.add_alarm_btn)
    Button mAddAlarmBtn;

    public AddAlarmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_alarm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mAddAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                // getHour() and getMinute() require version 23
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    calendar.set(calendar.get(Calendar.YEAR),
                            Calendar.MONTH,
                            Calendar.DAY_OF_MONTH,
                            mTimePicker.getHour(),
                            mTimePicker.getMinute(),
                            0);
                } else {
                    calendar.set(calendar.get(Calendar.YEAR),
                            Calendar.MONTH,
                            Calendar.DAY_OF_MONTH,
                            mTimePicker.getCurrentHour(),
                            mTimePicker.getCurrentMinute(),
                            0);
                }

                addAlarm(calendar.getTimeInMillis());
            }
        });
    }

    private void addAlarm(long timeInMillis) {
        AlarmManager alarmManager =
                (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(getActivity(), MedAlarm.class);
        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(getActivity(), 0, intent, 0);

        // Set the alarm
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent);

        // Inform the user that the alarm was set
        Toast.makeText(getActivity(), getActivity()
                .getResources()
                .getString(R.string.alarm_set),
                Toast.LENGTH_SHORT).show();
    }
}
