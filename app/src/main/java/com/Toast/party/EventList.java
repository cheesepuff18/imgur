package com.Toast.party;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventList#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class EventList extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";

    private ListView eventList;
    private static Context context;
    private Button addEventButton;

    public EventList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_event_list, container, false);

        addEventButton = (Button) view.findViewById(R.id.add_event_button);
        addEventButton.setOnClickListener(this);

        eventList = (ListView) view.findViewById(R.id.event_list);
        final ArrayList<String> name_list = new ArrayList<String>();

        final StableArrayAdapter adapter = new StableArrayAdapter(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1, name_list);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");
        query.whereNotEqualTo("name","billy");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> eventDataList, ParseException e) {
                if (e == null) {
                    for (int i = 0; i < eventDataList.size(); i++) {
                        name_list.add(eventDataList.get(i).getString("name"));
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

        eventList.setAdapter(adapter);

        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                name_list.remove(item);
                adapter.notifyDataSetChanged();
                Log.d("Remove", "Removed at " + position + " " + item);
            }

        });

        return view;
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            return(position);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_event_button:
                Intent intent = new Intent(getActivity(), AddEventActivity.class);
                startActivity(intent);
                break;
        }
    }
}
