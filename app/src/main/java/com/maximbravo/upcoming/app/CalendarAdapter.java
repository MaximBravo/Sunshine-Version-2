package com.maximbravo.upcoming.app;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

/**
 * {@link CalendarAdapter} exposes a list of calendars
 * from a {@link Cursor} to a {@link android.widget.ListView}.
 */
public class CalendarAdapter extends ArrayAdapter<String> {

    private static final int VIEW_TYPE_COUNT = 2;
    private static final int VIEW_TYPE_TODAY = 0;
    private static final int VIEW_TYPE_FUTURE_DAY = 1;

    // Flag to determine if we want to use a separate view for "today".
    private boolean mUseTodayLayout = true;

    /**
     * Cache of the children views for a calendar list item.
     */
    public static class ViewHolder {
        //public final ImageView iconView;
        public final TextView dateView;
        public final TextView descriptionView;
        // final TextView highTempView;
        //public final TextView lowTempView;

        public ViewHolder(View view) {
            //iconView = (ImageView) view.findViewById(R.id.list_item_icon);
            dateView = (TextView) view.findViewById(R.id.list_item_date_textview);
            descriptionView = (TextView) view.findViewById(R.id.list_item_calendar_textview);
            //highTempView = (TextView) view.findViewById(R.id.list_item_high_textview);
            //lowTempView = (TextView) view.findViewById(R.id.list_item_low_textview);
        }
    }

    public CalendarAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
    }

//    @Override
//    public View getView (int position, View convertView, ViewGroup parent){
//        if(convertView != null){
//            bindView(position, );
//        }
//    }

    public View newView(Context context, ViewGroup parent) {
        // Choose the layout type
        int viewType = 0; //getItemViewType(cursor.getPosition());
        int layoutId = -1;
        switch (viewType) {
            case VIEW_TYPE_TODAY: {
                layoutId = R.layout.list_item_calendar_header;
                break;
            }
            case VIEW_TYPE_FUTURE_DAY: {
                layoutId = R.layout.list_item_calendar_event;
                break;
            }
        }

        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    public void bindView(View view, Context context) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        int viewType = 0;//getItemViewType(cursor.getPosition());
        switch (viewType) {
            case VIEW_TYPE_TODAY: {
                // Get event icon
                //viewHolder.iconView.setImageResource(Utility.getArtResourceForEventCondition(
                //        cursor.getInt(CalendarFragment.COL_WEATHER_CONDITION_ID)));
                break;
            }
            case VIEW_TYPE_FUTURE_DAY: {
                // Get event icon
                //viewHolder.iconView.setImageResource(Utility.getIconResourceForEventCondition(
                //        cursor.getInt(CalendarFragment.COL_WEATHER_CONDITION_ID)));
                break;
            }
        }

        // Read date from cursor
        long dateInMillis = new Date().getTime(); //cursor.getLong(CalendarFragment.COL_WEATHER_DATE);
        // Find TextView and set formatted date on it
        viewHolder.dateView.setText(Utility.getFriendlyDayString(context, dateInMillis));

        // Read calendar from cursor
        String description = "5"; //cursor.getString(CalendarFragment.COL_WEATHER_DESC);
        // Find TextView and set calendar on it
        viewHolder.descriptionView.setText(description);

        // For accessibility, add a content description to the icon field
        //viewHolder.iconView.setContentDescription(description);

        // Read user preference for metric or imperial temperature units
        boolean isMetric = Utility.isMetric(context);

        // Read high temperature from cursor
        //double high = cursor.getDouble(CalendarFragment.COL_WEATHER_MAX_TEMP);
        //viewHolder.highTempView.setText(Utility.formatTemperature(context, high));

        // Read low temperature from cursor
        //double low = cursor.getDouble(CalendarFragment.COL_WEATHER_MIN_TEMP);
        //viewHolder.lowTempView.setText(Utility.formatTemperature(context, low));
    }

    public void setUseTodayLayout(boolean useTodayLayout) {
        mUseTodayLayout = useTodayLayout;
    }

    @Override
    public long getItemId(int position) {
        // TODO: MB Fix this
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0 && mUseTodayLayout) ? VIEW_TYPE_TODAY : VIEW_TYPE_FUTURE_DAY;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }
}