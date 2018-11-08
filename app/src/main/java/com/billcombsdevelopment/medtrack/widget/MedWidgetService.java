package com.billcombsdevelopment.medtrack.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.billcombsdevelopment.medtrack.R;
import com.billcombsdevelopment.medtrack.model.AppDatabase;
import com.billcombsdevelopment.medtrack.model.Medicine;

import java.util.ArrayList;
import java.util.List;

public class MedWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MedRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

class MedRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private List<Medicine> mMedList = new ArrayList<>();
    private Context mContext;
    private int mAppWidgetId;
    private AppDatabase mDb;


    public MedRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mDb = AppDatabase.getInstance(context);
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        // Get the new medicine list from the database
        mMedList = mDb.medDao().getWidgettMedList();
    }

    @Override
    public void onDestroy() {
        mMedList.clear();
    }

    @Override
    public int getCount() {
        return mMedList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews views = new RemoteViews(mContext.getPackageName(),
                R.layout.med_widget_list_item);
        views.setTextViewText(R.id.widget_med_name_tv, mMedList.get(i).getName());
        String dosage = mMedList.get(i).getDose() + " " + mMedList.get(i).getDosageInterval();
        views.setTextViewText(R.id.widget_med_dosage_tv, dosage);
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
