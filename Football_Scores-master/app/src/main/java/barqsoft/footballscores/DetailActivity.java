package barqsoft.footballscores;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import barqsoft.footballscores.DatabaseContract.scores_table;

import barqsoft.footballscores.widget.WidgetProvider;

public class DetailActivity extends Activity {

    private TextView tv_home_name;
    private TextView tv_away_name;
    private TextView tv_score;
    private TextView tv_date;
    private TextView tv_match_day;
    private TextView tv_league;
    private Button btn_share_button;
    private ImageView iv_home_crest;
    private ImageView iv_away_crest;
    private String FOOTBALL_SCORES_HASHTAG = "#Football_Scores";

    String home_name;
    String away_name;
    String score;
    String date;
    int match_day;
    int league;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            home_name = extras.getString(WidgetProvider.EXTRA_HOME_NAME);
            away_name = extras.getString(WidgetProvider.EXTRA_AWAY_NAME);
            score = extras.getString(WidgetProvider.EXTRA_SCORE);
            date = extras.getString(WidgetProvider.EXTRA_DATE);
            match_day = extras.getInt(WidgetProvider.EXTRA_MATCH_DAY);
            league = extras.getInt(WidgetProvider.EXTRA_LEAGUE);

            setupView();
        }

    }

    public void setupView(){

        tv_home_name = (TextView)findViewById(R.id.home_name);
        tv_away_name = (TextView)findViewById(R.id.away_name);
        tv_score     = (TextView)findViewById(R.id.score_textview);
        tv_date      = (TextView)findViewById(R.id.data_textview);
        iv_home_crest = (ImageView)findViewById(R.id.home_crest);
        iv_away_crest = (ImageView)findViewById(R.id.away_crest);
        tv_match_day = (TextView)findViewById(R.id.matchday_textview);
        tv_league = (TextView)findViewById(R.id.league_textview);
        btn_share_button = (Button)findViewById(R.id.share_button);

        tv_home_name.setText(home_name);
        tv_away_name.setText(away_name);
        tv_date.setText(date);
        tv_score.setText(score);
        iv_home_crest.setImageResource(Utilies.getTeamCrestByTeamName(home_name));
        iv_away_crest.setImageResource(Utilies.getTeamCrestByTeamName(away_name));

        tv_match_day.setText(Utilies.getMatchDay(match_day, league));
        tv_league.setText(Utilies.getLeague(league));
        btn_share_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add Share Action
                startActivity(createShareForecastIntent(home_name+" " +score+" "+away_name+ " "));}
        });
    }


    public Intent createShareForecastIntent(String ShareText) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, ShareText + FOOTBALL_SCORES_HASHTAG);
        return shareIntent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
