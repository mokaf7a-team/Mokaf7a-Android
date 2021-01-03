package com.resala.mokaf7a.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.resala.mokaf7a.EditHamlaActivity;
import com.resala.mokaf7a.R;
import com.resala.mokaf7a.classes.Hamla;

import java.util.ArrayList;

public class HamalatAdapter extends RecyclerView.Adapter<HamalatAdapter.ViewHolder> {
    private final ArrayList<Hamla> hamlaItems;
    private final Context context;

    public HamalatAdapter(ArrayList<Hamla> hamlaItems, Context context) {
        this.hamlaItems = hamlaItems;
        this.context = context;
    }

    /**
     * Called when RecyclerView needs a new {@link HamalatAdapter.ViewHolder} of the given type to
     * represent an item. This new ViewHolder should be constructed with a new View that can represent
     * the items of the given type. You can either create a new View manually or inflate it from an
     * XML layout file.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an
     *                 adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(HamalatAdapter.ViewHolder, int)
     */
    @NonNull
    @Override
    public HamalatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.hamla_item, parent, false);
        return new HamalatAdapter.ViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should update
     * the contents of the {@link HamalatAdapter.ViewHolder#itemView} to reflect the item at the given
     * position.
     *
     * <p>Note that unlike {@link ListView}, RecyclerView will not call this method again if the
     * position of the item changes in the data set unless the item itself is invalidated or the new
     * position cannot be determined. For this reason, you should only use the <code>position</code>
     * parameter while acquiring the related data item inside this method and should not keep a copy
     * of it. If you need the position of an item later on (e.g. in a click listener), use {@link
     * HamalatAdapter.ViewHolder#getAdapterPosition()} which will have the updated adapter position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the item at
     *                 the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull HamalatAdapter.ViewHolder holder, int position) {
        Hamla item = hamlaItems.get(position);
        holder.locationTV.setText(item.road);
        holder.dateTV.setText(item.date);
        holder.girlsTV.setText(String.valueOf(item.girls_count));
        holder.boysTV.setText(String.valueOf(item.boys_count));
        holder.branchTV.setText(item.branch);

        holder.casesCountTV.setText(String.valueOf(item.allCases));
        holder.blanketsTV.setText(String.valueOf(item.allBlankets));
        holder.clothesTV.setText(String.valueOf(item.allClothes));
        holder.mealsTV.setText(String.valueOf(item.allMeals));

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return hamlaItems.size();
    }

    /**
     * ****************************************************************************
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView locationTV;
        TextView dateTV;
        TextView branchTV;
        TextView boysTV;
        TextView girlsTV;

        TextView casesCountTV;
        TextView blanketsTV;
        TextView clothesTV;
        TextView mealsTV;

        ImageButton delete_btn;
        FirebaseDatabase database;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            locationTV = itemView.findViewById(R.id.locationTV);
            dateTV = itemView.findViewById(R.id.dateTV);
            branchTV = itemView.findViewById(R.id.branchTV);
            boysTV = itemView.findViewById(R.id.boysTV);
            girlsTV = itemView.findViewById(R.id.girlsTV);
            blanketsTV = itemView.findViewById(R.id.blanketsTV);
            clothesTV = itemView.findViewById(R.id.clothesTV);
            mealsTV = itemView.findViewById(R.id.mealsTV);
            casesCountTV = itemView.findViewById(R.id.casesCountTV);

            delete_btn = itemView.findViewById(R.id.delete_btn);
            delete_btn.setOnClickListener(this);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Hamla itemClicked = hamlaItems.get(position);

            if (view.getId() == delete_btn.getId()) {
                database = FirebaseDatabase.getInstance();
                final DatabaseReference hamalatRef = database.getReference("hamalat");
                hamalatRef.child(itemClicked.getKey()).setValue(null);
                Toast.makeText(context, "تم الغاء الحملة", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent;
                intent = new Intent(context, EditHamlaActivity.class);
                intent.putExtra("boys_count", String.valueOf(itemClicked.boys_count));
                intent.putExtra("branch", itemClicked.branch);
                intent.putExtra("girls_count", String.valueOf(itemClicked.girls_count));
                intent.putExtra("road", itemClicked.road);
                intent.putExtra("date", itemClicked.date);
                intent.putExtra("key", itemClicked.key);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            }
        }
    }

}
