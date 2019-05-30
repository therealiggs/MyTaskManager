package spbu.apmath.mytaskmanager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class SimpleEventAdapter extends RecyclerView.Adapter<SimpleEventAdapter.EventViewHolder> {

    public ArrayList<SimpleEvent> mEventsList;

    public static class EventViewHolder extends RecyclerView.ViewHolder {

        public TextView mDateView;
        public TextView mTextView;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            mDateView = itemView.findViewById(R.id.textView1);
            mTextView = itemView.findViewById(R.id.textView2);
        }
    }

    public SimpleEventAdapter(ArrayList<SimpleEvent> eventsList) {
        mEventsList = eventsList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.any_event, viewGroup, false);
        EventViewHolder evh = new EventViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder eventViewHolder, int i) {
        SimpleEvent currentEvent = mEventsList.get(i);

        eventViewHolder.mDateView.setText(currentEvent.getDate());
        eventViewHolder.mTextView.setText(currentEvent.getText());
    }

    @Override
    public int getItemCount() {
        return mEventsList.size();
    }
}
