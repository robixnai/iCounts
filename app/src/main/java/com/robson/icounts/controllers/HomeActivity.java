package com.robson.icounts.controllers;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.robson.icounts.R;
import com.robson.icounts.development.MockTestDevelopment;
import com.robson.icounts.libraries.AnimateHorizontalProgressBar;
import com.robson.icounts.utils.AppUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int SLEEP_TIME = 50;
    private static final int PROGRESS_INIT = 0;
    private static final int PROGRESS_MAX = 100;

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.drawer_layout) DrawerLayout mDrawer;
    @Bind(R.id.nav_view) NavigationView mNavigationView;
    @Bind(R.id.fabMenu) FloatingActionMenu mFabMenu;
    @Bind(R.id.fabTransactions) FloatingActionButton mFabTransactions;
    @Bind(R.id.fabRecurrence) FloatingActionButton mFabRecurrence;
    @Bind(R.id.fabCategories) FloatingActionButton mFabCategories;
    @Bind(R.id.textViewCredit) TextView mTextViewCredit;
    @Bind(R.id.textViewDebit) TextView mTextViewDebit;
    @Bind(R.id.textViewRents) TextView mTextViewRents;
    @Bind(R.id.textViewSpending) TextView mTextViewSpending;
    @Bind(R.id.textViewRecurrence) TextView mTextViewRecurrence;
    @Bind(R.id.progressRents) AnimateHorizontalProgressBar mProgressRents;
    @Bind(R.id.progressSpending) AnimateHorizontalProgressBar mProgressSpending;
    @Bind(R.id.progressRecurrence) AnimateHorizontalProgressBar mProgressRecurrence;
    @Bind(R.id.pieChart) PieChart mPieChart;

    private ActionBarDrawerToggle mToggle;
    private OnClickListener mClickListenerFab;
    private Typeface mTypeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        mClickListenerFab = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = "";

                switch (view.getId()) {
                    case R.id.fabTransactions:
                        text = mFabTransactions.getLabelText();
                        mFabMenu.close(true);
                        break;
                    case R.id.fabRecurrence:
                        text = mFabRecurrence.getLabelText();
                        mFabMenu.close(true);
                        break;
                    case R.id.fabCategories:
                        text = mFabCategories.getLabelText();
                        mFabMenu.close(true);
                        break;
                }

                Toast.makeText(HomeActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    protected void onResume() {
        initToolbar();
        initDrawer();
        initNavigationView();
        initFab();
        initTextView();
        initProgressBar();
        initPieChart();

        super.onResume();
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
    }

    private void initDrawer() {
        mToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
    }

    private void initNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    private void initFab() {
        mFabMenu.setClosedOnTouchOutside(true);

        mFabTransactions.setOnClickListener(mClickListenerFab);
        mFabRecurrence.setOnClickListener(mClickListenerFab);
        mFabCategories.setOnClickListener(mClickListenerFab);
    }

    private void initTextView() {
        mTextViewCredit.setText("R$ " + AppUtil.formatDecimal(MockTestDevelopment.getValueCredit()));
        mTextViewDebit.setText("R$ " + AppUtil.formatDecimal(MockTestDevelopment.getValueDebit()));

        double rendas = MockTestDevelopment.getValueRents();
        mTextViewRents.setText("R$ " + AppUtil.formatDecimal(rendas));

        double gastos = MockTestDevelopment.getValueSpending();
        mTextViewSpending.setText("R$ " +  AppUtil.formatDecimal(gastos));

        String recurrenceMoment = AppUtil.formatDecimal(MockTestDevelopment.getValueRecurrenceMoment());
        String recurrence = AppUtil.formatDecimal(MockTestDevelopment.getValueRecurrence());
        mTextViewRecurrence.setText("R$ " + recurrenceMoment + " gastos de R$ " + recurrence + " previsto");
    }

    private void initProgressBar() {
        mProgressRents.setProgress(PROGRESS_INIT);
        mProgressRents.setMax(PROGRESS_MAX);

        mProgressSpending.setProgress(PROGRESS_INIT);
        mProgressSpending.setMax(PROGRESS_MAX);

        mProgressRecurrence.setProgress(PROGRESS_INIT);
        mProgressRecurrence.setMax(PROGRESS_MAX);

        new UpdateProgressColorsTask().execute();
    }

    private void initPieChart() {
        mPieChart.setUsePercentValues(true);
        mPieChart.setDescription("");
        mPieChart.setExtraOffsets(0, 0, 70, 0);
        mPieChart.setDragDecelerationFrictionCoef(0.95f);

//        mTypeface = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

//        mPieChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));

        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setHoleColor(Color.WHITE);
        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);
        mPieChart.setHoleRadius(58f);
        mPieChart.setTransparentCircleRadius(61f);
        mPieChart.setDrawCenterText(true);
        mPieChart.setRotationAngle(0);
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(true);
        mPieChart.setDrawSliceText(false);
        mPieChart.invalidate();

        setData(3, 100);

        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend legend = mPieChart.getLegend();
        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        legend.setXEntrySpace(7f);
        legend.setYEntrySpace(0f);
        legend.setYOffset(0f);
    }

    private void setData(int count, float range) {
        String[] mParties = MockTestDevelopment.mockPieChart();

        float mult = range;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        for (int i = 0; i < count + 1; i++) {
            yVals1.add(new Entry((float) (Math.random() * mult) + mult / 5, i));
        }

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < count + 1; i++)
            xVals.add(mParties[i % mParties.length]);

        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        dataSet.setSelectionShift(0f);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
//        data.setValueTypeface(mTypeface);
        mPieChart.setData(data);

        // undo all highlights
        mPieChart.highlightValues(null);

        mPieChart.invalidate();
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_extract) {

        } else if (id == R.id.nav_recurrence) {

        } else if (id == R.id.nav_graphics) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_exit) {

        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class UpdateProgressColorsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mProgressRents.setProgressWithAnim(MockTestDevelopment.mockProgressBarRents());
                    mProgressSpending.setProgressWithAnim(MockTestDevelopment.mockProgressBarSpending());
                    mProgressRecurrence.setProgressWithAnim(MockTestDevelopment.mockProgressBarRecurrence());
                }
            });

            return null;
        }

    }

}
