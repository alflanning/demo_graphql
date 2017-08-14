package graph_http.graph_http.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import graph_http.graph_http.R;

public class MyLocationsFragment extends AbstractTabFragment {

    private static final int LAYOUT = R.layout.my_locatoins_fragment_activity;

    public static MyLocationsFragment getInstance(Context context) {

        Bundle args = new Bundle();
        MyLocationsFragment fragment = new MyLocationsFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle("Мои локации");

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

        // здесь присваивать переменные

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // здесь писать код
    }
}
