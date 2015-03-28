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
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Encapsulates fetching the calendar and displaying it as a {@link ListView} layout.
 */
public class CalendarFragment extends Fragment { //implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String LOG_TAG = CalendarFragment.class.getSimpleName();
    private CalendarAdapter mCalendarAdapter;

    private ListView mListView;
    private int mPosition = ListView.INVALID_POSITION;
    private boolean mUseTodayLayout;

    private static final String SELECTED_KEY = "selected_position";

    private static final int CALENDAR_LOADER = 0;
    // For the calendar view we're showing only a small subset of the stored data.
    // Specify the columns we need.

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */

    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(Uri dateUri);
    }

    public CalendarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.calendarfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//        if (id == R.id.action_refresh) {
//            updateEvent();
//            return true;
//        }
        if (id == R.id.action_map) {
            openPreferredLocationInMap();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        long oneDay = 1000 * 60 * 60 * 24;

        // Find TextView and set formatted date on it

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        List<String> calenderList = new ArrayList<>();
        for(int i = 1; i < 8; i++) {
            long dateInMillis = new Date().getTime() + oneDay * i;
            String date = Utility.getFriendlyDayString(getActivity(), dateInMillis);

            calenderList.add(date);
        }



        // The CalendarAdapter will take data from a source and
        // use it to populate the ListView it's attached to.
        mCalendarAdapter = new CalendarAdapter(
                getActivity(),
                R.layout.list_item_calendar_header,
                calenderList);



        // Get a reference to the ListView, and attach this adapter to it.
        mListView = (ListView) rootView.findViewById(R.id.listview_calendar);
        mListView.setAdapter(mCalendarAdapter);
        // We'll call our MainActivity
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // CursorAdapter returns a cursor at the correct position for getItem(), or null
                // if it cannot seek to that position.
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (cursor != null) {
                    String locationSetting = Utility.getPreferredLocation(getActivity());
                    ((Callback) getActivity())
                            .onItemSelected(null);
                }
                mPosition = position;
            }
        });

        // If there's instance state, mine it for useful information.
        // The end-goal here is that the user never knows that turning their device sideways
        // does crazy lifecycle related things.  It should feel like some stuff stretched out,
        // or magically appeared to take advantage of room, but data or place in the app was never
        // actually *lost*.
        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            // The listview probably hasn't even been populated yet.  Actually perform the
            // swapout in onLoadFinished.
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }

        mCalendarAdapter.setUseTodayLayout(mUseTodayLayout);


        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        //getLoaderManager().initLoader(CALENDAR_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    // since we read the location when we create the loader, all we need to do is restart things

    private void updateEvent() {
        //UpcomingSyncAdapter.syncImmediately(getActivity());
    }



    private void openPreferredLocationInMap() {
        // Using the URI scheme for showing a location found on a map.  This super-handy
        // intent can is detailed in the "Common Intents" page of Android's developer site:
        // http://developer.android.com/guide/components/intents-common.html#Maps

        if ( null != mCalendarAdapter) {
            //Cursor c = mCalendarAdapter.getCursor();
            //c.moveToPosition(0);
            String posLat = "1";//c.getString(COL_COORD_LAT);
            String posLong = "2";//c.getString(COL_COORD_LONG);
            Uri geoLocation = Uri.parse("geo:" + posLat + "," + posLong);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(geoLocation);

            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Log.d(LOG_TAG, "Couldn't call " + geoLocation.toString() + ", no receiving apps installed!");
            }
        //}

    }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // When tablets rotate, the currently selected list item needs to be saved.
        // When no item is selected, mPosition will be set to Listview.INVALID_POSITION,
        // so check for that before storing.
        if (mPosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    public void setUseTodayLayout(boolean useTodayLayout) {
        mUseTodayLayout = useTodayLayout;
        if (mCalendarAdapter != null) {
            mCalendarAdapter.setUseTodayLayout(mUseTodayLayout);
        }
    }
}