package barqsoft.footballscores.widget;

/**
 * Created by rubymobile on 10/11/15.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.R;
import barqsoft.footballscores.ScoresDBHelper;
import barqsoft.footballscores.Utilies;

@SuppressLint("NewApi")
public class WidgetDataProvider implements RemoteViewsFactory {

    Cursor cursor;

    private static final String[] COLUMNS = {
            DatabaseContract.scores_table.LEAGUE_COL,
            DatabaseContract.scores_table.DATE_COL,
            DatabaseContract.scores_table.TIME_COL,
            DatabaseContract.scores_table.HOME_COL,
            DatabaseContract.scores_table.AWAY_COL,
            DatabaseContract.scores_table.HOME_GOALS_COL,
            DatabaseContract.scores_table.AWAY_GOALS_COL,
            DatabaseContract.scores_table.MATCH_ID,
            DatabaseContract.scores_table.MATCH_DAY,
            DatabaseContract.scores_table._ID
    };

    private static final int COL_LEAGUE = 0;
    private static final int COL_DATE = 1;
    private static final int COL_TIME = 2;
    private static final int COL_HOME = 3;
    private static final int COL_AWAY = 4;
    private static final int COL_HOME_GOALS = 5;
    private static final int COL_AWAY_GOALS = 6;
    private static final int COL_MATCH_ID = 7;
    private static final int COL_MATCH_DAY = 8;
    private static final int COL_SCORE_ID = 9;



    Context mContext = null;
    private static final String TAG = "Widget";

    public WidgetDataProvider(Context context, Intent intent) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews mView = new RemoteViews(mContext.getPackageName(), R.layout.scores_list_item);

        if (cursor != null ) {
            if(cursor.moveToPosition(position)){
                String dir = cursor.getString(cursor.getColumnIndex("date"));

                mView.setTextViewText(R.id.home_name, "" + cursor.getString(COL_HOME));
                mView.setTextViewText(R.id.away_name, "" + cursor.getString(COL_AWAY));
                mView.setTextViewText(R.id.data_textview, "" + cursor.getString(COL_DATE));
                mView.setTextViewText(R.id.score_textview, "" + Utilies.getScores(cursor.getInt(COL_HOME_GOALS), cursor.getInt(COL_AWAY_GOALS)));
                mView.setImageViewResource(R.id.home_crest, Utilies.getTeamCrestByTeamName(cursor.getString(COL_HOME)));
                mView.setImageViewResource(R.id.away_crest, Utilies.getTeamCrestByTeamName(cursor.getString(COL_AWAY)));
            }
        }

        final Intent fillInIntent = new Intent();
        fillInIntent.setAction(WidgetProvider.ACTION_OPEN_ACTIVITY);
        final Bundle bundle = new Bundle();


        bundle.putString(WidgetProvider.EXTRA_HOME_NAME, cursor.getString(COL_HOME));
        bundle.putString(WidgetProvider.EXTRA_AWAY_NAME, cursor.getString(COL_AWAY));
        bundle.putString(WidgetProvider.EXTRA_SCORE, Utilies.getScores(cursor.getInt(COL_HOME_GOALS), cursor.getInt(COL_AWAY_GOALS)));
        bundle.putString(WidgetProvider.EXTRA_DATE,  cursor.getString(COL_DATE));
        bundle.putInt(WidgetProvider.EXTRA_MATCH_DAY, cursor.getInt(COL_MATCH_DAY));
        bundle.putInt(WidgetProvider.EXTRA_LEAGUE, cursor.getInt(COL_LEAGUE));
        fillInIntent.putExtras(bundle);
        mView.setOnClickFillInIntent(R.id.widget_id, fillInIntent);

        return mView;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onCreate() {
        initData();

    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    private void initData() {

        ScoresDBHelper mDbHelper = new ScoresDBHelper(mContext);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        cursor = db.rawQuery("SELECT * FROM scores_table",null);
    }

    @Override
    public void onDestroy() {

    }

}
