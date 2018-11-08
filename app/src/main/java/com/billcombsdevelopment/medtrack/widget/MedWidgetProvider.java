package com.billcombsdevelopment.medtrack.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.billcombsdevelopment.medtrack.R;

public class MedWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        // loop through each widget the user has places
        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Intent to launch MainActivity
            Intent intent = new Intent(context, MedWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.med_widget_layout);
            views.setRemoteAdapter(R.id.widget_linear_layout, intent);
            views.setEmptyView(R.id.widget_list_view, R.id.empty_view);

            // Update the current widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}