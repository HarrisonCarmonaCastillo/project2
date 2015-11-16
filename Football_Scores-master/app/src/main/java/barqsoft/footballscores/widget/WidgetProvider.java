package barqsoft.footballscores.widget;

/**
 * Created by rubymobile on 10/11/15.
 */
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import barqsoft.footballscores.DetailActivity;
import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;
import barqsoft.footballscores.service.WidgetService;

@SuppressWarnings("deprecation")
public class WidgetProvider extends AppWidgetProvider {

    private static final String TAG = "Widget";
    public static final String ACTION_OPEN_ACTIVITY = "openActivity";
    public static final String EXTRA_HOME_NAME = "home_name";
    public static final String EXTRA_AWAY_NAME = "away_name";
    public static final String EXTRA_SCORE = "score";
    public static final String EXTRA_DATE = "date";
    public static final String EXTRA_HOME_CREST = "home_crest";
    public static final String EXTRA_AWAY_CREST = "away_crest";
    public static final String EXTRA_MATCH_DAY = "match_day";
    public static final String EXTRA_LEAGUE = "league";


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(ACTION_OPEN_ACTIVITY)) {
            String home_name = intent.getExtras().getString(EXTRA_HOME_NAME);
            String away_name = intent.getExtras().getString(EXTRA_AWAY_NAME);
            String score = intent.getExtras().getString(EXTRA_SCORE);
            String date = intent.getExtras().getString(EXTRA_DATE);
            int match_day = intent.getExtras().getInt(EXTRA_MATCH_DAY);
            int league = intent.getExtras().getInt(EXTRA_LEAGUE);

            Toast.makeText(context,""+ home_name, Toast.LENGTH_LONG).show();

            Intent dialogIntent = new Intent(context, DetailActivity.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            dialogIntent.putExtra(WidgetProvider.EXTRA_HOME_NAME, home_name);
            dialogIntent.putExtra(WidgetProvider.EXTRA_AWAY_NAME,away_name);
            dialogIntent.putExtra(WidgetProvider.EXTRA_SCORE,score);
            dialogIntent.putExtra(WidgetProvider.EXTRA_DATE,date);
            dialogIntent.putExtra(WidgetProvider.EXTRA_MATCH_DAY,match_day);
            dialogIntent.putExtra(WidgetProvider.EXTRA_LEAGUE,league);
            context.startActivity(dialogIntent);
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        for (int widgetId : appWidgetIds) {
            RemoteViews mView = initViews(context, appWidgetManager, widgetId);

            final Intent onItemClick = new Intent(context, WidgetProvider.class);
            onItemClick.setAction(ACTION_OPEN_ACTIVITY);
            onItemClick.setData(Uri.parse(onItemClick.toUri(Intent.URI_INTENT_SCHEME)));
            final PendingIntent onClickPendingIntent = PendingIntent.getBroadcast(context, 0, onItemClick, PendingIntent.FLAG_UPDATE_CURRENT);
            mView.setPendingIntentTemplate(R.id.scores_list, onClickPendingIntent);


            appWidgetManager.updateAppWidget(widgetId, mView);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }


    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private RemoteViews initViews(Context context,
                                  AppWidgetManager widgetManager, int widgetId) {

        RemoteViews mView = new RemoteViews(context.getPackageName(),
                R.layout.widget_layout);

        Intent intent = new Intent(context, WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        mView.setRemoteAdapter(widgetId, R.id.scores_list, intent);


        Intent configIntent = new Intent(context, MainActivity.class);
        PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0);
        mView.setOnClickPendingIntent(R.id.widget_id, configPendingIntent);

        return mView;
    }

    public static void updateAllWidgets(Context context) {
        Log.v(TAG, "Forcing widget update");
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        ComponentName component = new ComponentName(context.getApplicationContext(), WidgetProvider.class);
        for (int id : manager.getAppWidgetIds(component)) {
            updateAppWidget(context, manager, id);

        }
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        // Instruct the widget manager to update the widget

        RemoteViews mView = new RemoteViews(context.getPackageName(),
                R.layout.widget_layout);
        Intent intent = new Intent(context, WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        mView.setRemoteAdapter(appWidgetId, R.id.scores_list, intent);


        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


}