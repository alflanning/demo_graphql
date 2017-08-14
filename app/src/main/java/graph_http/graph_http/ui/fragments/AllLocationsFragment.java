package graph_http.graph_http.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;

import graph_http.graph_http.R;
import graph_http.graph_http.adapters.recycler_view_adapters.OvondoQueryAdapter;
import graph_http.graph_http.ui.OvondoQueryActivity;
import graph_http.graph_http.utils.Constants;

public class AllLocationsFragment extends AbstractTabFragment {

    RecyclerView rv;
    private static final int LAYOUT = R.layout.all_locations_fragment_activity;

    public static AllLocationsFragment getInstance(Context context) {

        Bundle args = new Bundle();
        AllLocationsFragment fragment = new AllLocationsFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle("Все локации");

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        rv = (RecyclerView) view.findViewById(R.id.AllLocationsRecyclerView);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        JSONArray arrayLocations = Constants.getArrayLocations();
        LinearLayoutManager llm = new LinearLayoutManager(context);
        rv.setLayoutManager(llm);

        OvondoQueryAdapter adapter = new OvondoQueryAdapter(arrayLocations);
        rv.setAdapter(adapter);
    }
}
