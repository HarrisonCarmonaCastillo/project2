package barqsoft.footballscores.service;

/**
 * Created by rubymobile on 10/11/15.
 */
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.widget.RemoteViewsService;

import barqsoft.footballscores.widget.WidgetDataProvider;

@SuppressLint("NewApi")
public class WidgetService extends RemoteViewsService{

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        WidgetDataProvider dataProvider = new WidgetDataProvider(
                getApplicationContext(), intent);

        return dataProvider;
    }

}
