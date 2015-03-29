package com.maximbravo.upcoming.app;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * {@link CalendarAdapter} exposes a list of calendars
 * from a {@link Cursor} to a {@link android.widget.ListView}.
 */
public class CalendarAdapter extends ArrayAdapter<CalendarItem> {

    // Flag to determine if we want to use a separate view for "today".

    private Context mContext;
    /**
     * Cache of the children views for a calendar list item.
     */
    public static class ViewHolder {
        public final TextView eventNameView;
        public final TextView eventTimeView;

        public ViewHolder(View view) {
            eventNameView = (TextView) view.findViewById(R.id.list_item_event_name);
            eventTimeView = (TextView) view.findViewById(R.id.list_item_event_time);
        }
    }

    public CalendarAdapter(Context context, int resource, List<CalendarItem> objects) {
        super(context, resource, objects);
        mContext = context;

    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent){
        if(convertView == null){
            return newView(position, parent);
        } else {
            bindView(position, convertView);
            return convertView;
        }
    }

    public View newView(int position, ViewGroup parent) {
        // Choose the layout type
        int layoutId = -1;
        if(getItem(position).type == CalendarItem.VIEW_TYPE_HEADER) {
            layoutId = R.layout.list_item_calendar_header;
        } else {
            layoutId = R.layout.list_item_calendar_event;
        }

        View view = LayoutInflater.from(mContext).inflate(layoutId, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        bindView(position, view);
        return view;
    }

    public void bindView(int position, View view) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        if(getItem(position).type == CalendarItem.VIEW_TYPE_HEADER) {
            ((TextView)view).setText(getItem(position).time);
        } else {
            if(viewHolder.eventNameView != null) {
                viewHolder.eventNameView.setText(getItem(position).name);
            }
            if(viewHolder.eventTimeView != null) {
                viewHolder.eventTimeView.setText(getItem(position).time);
            }
        }
    }

    public void setUseTodayLayout(boolean useTodayLayout) {
        //mUseTodayLayout = useTodayLayout;
    }

    @Override
    public long getItemId(int position) {
        // TODO: MB Fix this
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).type;
    }

    @Override
    public int getViewTypeCount() {
        return CalendarItem.VIEW_TYPE_COUNT;
    }
}