package com.dahtkahm.alphas.test2;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    int team0Score, team1Score, workingScore;
    int currentRound = 1;
    boolean workingTeam = false;
    TextView team0Summary,team1Summary, team0Preview, team1Preview, roundSummary;
    LinearLayout buttonGroup0, buttonGroup1;
    Button addOne0, addRinger0, addOne1, addRinger1, apply0, apply1,
            cancel0, cancel1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        setListeners();
        updateUIInit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
/*
        if (id == R.id.action_settings) {
            return true;
        }
*/

        return super.onOptionsItemSelected(item);
    }

    private void addToWorkingScore (boolean team, int score) {
        workingTeam = team;
        workingScore += score;

        disablePointControls(team);
        updateUIPostWorkingScoreChange();
    }

    private void applyScore (){
        team0Score = workingTeam ? team0Score : team0Score + workingScore;
        team1Score = workingTeam ? team1Score + workingScore : team1Score;
        workingScore = 0;
        currentRound++;

        enablePointControls();
        updateUIPostApplyScore();
    }

    private  void cancelWorkingScore () {
        workingScore = 0;
        hidePendingElements();
        enablePointControls();
    }

    private  void updateUIPostWorkingScoreChange() {

        if (0 >= workingScore) {
            hidePendingElements();
            return;
        }

        if (!workingTeam) {
            // points are being added to T0
            buttonGroup0.setVisibility(View.VISIBLE);
            team0Preview.setVisibility(View.VISIBLE);
            team0Preview.setText(String.format(getString(R.string.applyScoreFormat), workingScore, getString(R.string.team_name_0)));

            return;
        }

        // points are being added to T1
        buttonGroup1.setVisibility(View.VISIBLE);
        team1Preview.setVisibility(View.VISIBLE);
        team1Preview.setText(String.format(getString(R.string.applyScoreFormat), workingScore, getString(R.string.team_name_1)));
    }

    private  void updateUIPostApplyScore() {
        hidePendingElements();

        roundSummary.setText
        (
                String.format
                (
                        getString(R.string.round_summary_text),
                        currentRound
                )
        );

        if (!workingTeam) {
            team0Summary.setText(String.format(getString(R.string.scoreSummaryFormat), getString(R.string.team_name_0), team0Score));
            return;
        }

        team1Summary.setText(String.format(getString(R.string.scoreSummaryFormat), getString(R.string.team_name_1), team1Score));
    }

    private void updateUIInit() {
        team0Summary.setText
                (
                        String.format
                                (
                                        getString(R.string.scoreSummaryFormat),
                                        getString(R.string.team_name_0),
                                        team0Score
                                )
                );
        team1Summary.setText
                (
                        String.format
                                (
                                        getString(R.string.scoreSummaryFormat),
                                        getString(R.string.team_name_1),
                                        team1Score
                                )
                );
        roundSummary.setText
                (
                        String.format
                                (
                                        getString(R.string.round_summary_text),
                                        currentRound
                                )
                );
    }

    private void setListeners() {
        addOne0.setOnClickListener (new View.OnClickListener (){
            public  void onClick (View view){
                addToWorkingScore (false, 1);
            }
        });
        addOne1.setOnClickListener (new View.OnClickListener (){
            public  void onClick (View view){
                addToWorkingScore (true, 1);
            }
        });
        addRinger0.setOnClickListener (new View.OnClickListener (){
            public  void onClick (View view){
                addToWorkingScore (false, 2);
            }
        });
        addRinger1.setOnClickListener (new View.OnClickListener (){
            public  void onClick (View view){
                addToWorkingScore (true, 2);
            }
        });
        apply0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                applyScore();
            }
        });
        apply1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                applyScore();
            }
        });
        cancel0.setOnClickListener(new View.OnClickListener() {
            public void onClick (View view) {
                cancelWorkingScore();
            }
        });
        cancel1.setOnClickListener(new View.OnClickListener() {
            public void onClick (View view) {
                cancelWorkingScore ();
            }
        });
    }

    private void findViews() {
        addOne0 = (Button)findViewById(R.id.add_one_0);
        addRinger0 = (Button)findViewById(R.id.add_ringer_0);
        addOne1 = (Button)findViewById(R.id.add_one_1);
        addRinger1 = (Button)findViewById(R.id.add_ringer_1);
        apply0 = (Button)findViewById(R.id.apply0);
        apply1 = (Button)findViewById(R.id.apply1);
        team0Summary = (TextView)findViewById(R.id.team_0_summary);
        team1Summary = (TextView)findViewById(R.id.team_1_summary);
        team0Preview = (TextView)findViewById(R.id.team_0_preview);
        team1Preview = (TextView)findViewById(R.id.team_1_preview);
        cancel0 = (Button)findViewById(R.id.cancel0);
        cancel1 = (Button)findViewById(R.id.cancel1);
        buttonGroup0 = (LinearLayout)findViewById(R.id.buttonGroup0 );
        buttonGroup1 = (LinearLayout)findViewById(R.id.buttonGroup1 );
        roundSummary = (TextView)findViewById(R.id.round);
    }

    private void hidePendingElements() {
//        apply0.setVisibility(View.INVISIBLE);
//        apply1.setVisibility(View.INVISIBLE);
        buttonGroup0.setVisibility(View.INVISIBLE);
        buttonGroup1.setVisibility(View.INVISIBLE);
        team0Preview.setVisibility(View.INVISIBLE);
        team1Preview.setVisibility(View.INVISIBLE);
    }

    private void disablePointControls(boolean team) {
        if (!team) {
            // team 0 stays enabled; disable T1:
            addOne1.setEnabled(false);
            addRinger1.setEnabled(false);
            return;
        }

        addOne0.setEnabled(false);
        addRinger0.setEnabled(false);
    }

    private void enablePointControls() {
        addOne0.setEnabled(true);
        addRinger0.setEnabled(true);

        addOne1.setEnabled(true);
        addRinger1.setEnabled(true);
    }
}
