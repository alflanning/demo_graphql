package graph_http.graph_http.adapters.recycler_view_adapters;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import graph_http.graph_http.R;

public class OvondoQueryAdapter extends RecyclerView.Adapter<OvondoQueryAdapter.CardViewHolder>  {

    public class CardViewHolder extends RecyclerView.ViewHolder {

        TextView userName, businessName;
        CardView ovondoCard;

        public CardViewHolder(View itemView) {
            super(itemView);

            userName = (TextView) itemView.findViewById(R.id.idCardUserName);
            businessName = (TextView) itemView.findViewById(R.id.idCardBusinessName);
            ovondoCard = (CardView) itemView.findViewById(R.id.cardLocation);
        }
    }

    JSONArray cards;
    public OvondoQueryAdapter(JSONArray cards){
        this.cards = cards;
    }

    @Override
    public OvondoQueryAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_location, parent, false);
        OvondoQueryAdapter.CardViewHolder oqa = new OvondoQueryAdapter.CardViewHolder(v);

        return oqa;
    }

    @Override
    public void onBindViewHolder(OvondoQueryAdapter.CardViewHolder cardViewHolder, int i) {

        try {
            JSONObject location = cards.getJSONObject(i);
            JSONObject node = location.getJSONObject("node");
            JSONObject merchant = node.getJSONObject("merchant");

            cardViewHolder.userName.setText(node.getString("name"));
            cardViewHolder.businessName.setText(merchant.getString("businessName"));





        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return cards.length();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}
