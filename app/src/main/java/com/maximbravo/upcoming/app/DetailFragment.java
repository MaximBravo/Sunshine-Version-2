/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.maximbravo.upcoming.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment { //implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = DetailFragment.class.getSimpleName();
    static final String DETAIL_URI = "URI";

    private static final String CALENDAR_SHARE_HASHTAG = " #UpcomingApp";


    private String mCalendar;
    //private Uri mUri;

    //private static final int DETAIL_LOADER = 0;



    // These indices are tied to DETAIL_COLUMNS.  If DETAIL_COLUMNS changes, these
    // must change.
    public static final int COL_WEATHER_ID = 0;
    public static final int COL_WEATHER_DATE = 1;
    public static final int COL_WEATHER_DESC = 2;
    public static final int COL_WEATHER_MAX_TEMP = 3;
    public static final int COL_WEATHER_MIN_TEMP = 4;
    public static final int COL_WEATHER_HUMIDITY = 5;
    public static final int COL_WEATHER_PRESSURE = 6;
    public static final int COL_WEATHER_WIND_SPEED = 7;
    public static final int COL_WEATHER_DEGREES = 8;
    public static final int COL_WEATHER_CONDITION_ID = 9;

    private ImageView mIconView;
    private TextView mFriendlyDateView;
    private TextView mDateView;
    private TextView mDescriptionView;
    private TextView mHighTempView;
    private TextView mLowTempView;
    private TextView mHumidityView;
    private TextView mWindView;
    private TextView mPressureView;

    public DetailFragment() {
        //setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        Intent intent = null;
        if (arguments != null) {
            intent = arguments.getParcelable(DetailFragment.DETAIL_URI);
        }
        if (intent == null) {
            intent = getActivity().getIntent();
        }

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        // The detail Activity called via intent.  Inspect the intent for forecast data.

        if (intent != null && intent.hasExtra("ItemName")) {
            String calendarStrName = intent.getStringExtra("ItemName");
            ((TextView) rootView.findViewById(R.id.detail_event))
                    .setText(calendarStrName);
        }

        if (intent != null && intent.hasExtra("ItemTime")) {
            String caledarStrTime = intent.getStringExtra("ItemTime");
            ((TextView) rootView.findViewById(R.id.detail_time))
                    .setText(caledarStrTime);
        }

//        if (intent != null && intent.hasExtra("ItemType")) {
//            int calendarIntType = intent.getIntExtra("ItemType", CalendarItem.VIEW_TYPE_HEADER);
//            ((TextView) rootView.findViewById(R.id.detail_type))
//                    .setText(calendarIntType);
//        }

        if (intent != null && intent.hasExtra("ItemDescription")) {
            String caledarStrDescription = intent.getStringExtra("ItemDescription");
            ((TextView) rootView.findViewById(R.id.detail_description))
                    .setText(caledarStrDescription);
        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.detailfragment, menu);

        // Retrieve the share menu item
        MenuItem menuItem = menu.findItem(R.id.action_share);




        // If onLoadFinished happens before this, we can go ahead and set the share intent now.
        if (mCalendar != null) {

        }
    }

    private Intent createShareCalendarIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mCalendar + CALENDAR_SHARE_HASHTAG);
        return shareIntent;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
//        getLoaderManager().initLoader(DETAIL_LOADER, null, null);
        super.onActivityCreated(savedInstanceState);
    }

//    void onLocationChanged( String newLocation ) {
//        // replace the uri, since the location has changed
//        Uri uri = mUri;
//        if (null != uri) {
//            long date = 565454;
//            getLoaderManager().restartLoader(DETAIL_LOADER, null, null);
//        }
//    }

}