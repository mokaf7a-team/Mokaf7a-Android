package com.resala.mokaf7a.adapters;

import android.content.Context;
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
import com.resala.mokaf7a.R;
import com.resala.mokaf7a.classes.Case;

import java.util.ArrayList;

public class CasesAdapter extends RecyclerView.Adapter<CasesAdapter.ViewHolder> {
    private final ArrayList<Case> caseItems;
    private final Context context;
    private final String hamlaKey;

    public CasesAdapter(ArrayList<Case> caseItems, Context context, String hamlaKey) {
        this.hamlaKey = hamlaKey;
        this.caseItems = caseItems;
        this.context = context;
    }

    /**
     * Called when RecyclerView needs a new {@link CasesAdapter.ViewHolder} of the given type to
     * represent an item. This new ViewHolder should be constructed with a new View that can represent
     * the items of the given type. You can either create a new View manually or inflate it from an
     * XML layout file.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an
     *                 adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(CasesAdapter.ViewHolder, int)
     */
    @NonNull
    @Override
    public CasesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.case_item, parent, false);
        return new CasesAdapter.ViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should update
     * the contents of the {@link CasesAdapter.ViewHolder#itemView} to reflect the item at the given
     * position.
     *
     * <p>Note that unlike {@link ListView}, RecyclerView will not call this method again if the
     * position of the item changes in the data set unless the item itself is invalidated or the new
     * position cannot be determined. For this reason, you should only use the <code>position</code>
     * parameter while acquiring the related data item inside this method and should not keep a copy
     * of it. If you need the position of an item later on (e.g. in a click listener), use {@link
     * CasesAdapter.ViewHolder#getAdapterPosition()} which will have the updated adapter position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the item at
     *                 the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull CasesAdapter.ViewHolder holder, int position) {
        Case item = caseItems.get(position);
        holder.locationTV.setText(item.location);
        holder.blanketsTV.setText(String.valueOf(item.blankets));
        holder.clothesTV.setText(String.valueOf(item.clothes));
        holder.mealsTV.setText(String.valueOf(item.meals));
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return caseItems.size();
    }

    /**
     * ****************************************************************************
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView locationTV;
        TextView blanketsTV;
        TextView clothesTV;
        TextView mealsTV;
        ImageButton delete_btn;
        FirebaseDatabase database;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            locationTV = itemView.findViewById(R.id.locationTV);
            blanketsTV = itemView.findViewById(R.id.blanketsTV);
            clothesTV = itemView.findViewById(R.id.clothesTV);
            mealsTV = itemView.findViewById(R.id.mealsTV);
            delete_btn = itemView.findViewById(R.id.delete_btn);
            delete_btn.setOnClickListener(this);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Case itemClicked = caseItems.get(position);

            if (view.getId() == delete_btn.getId()) {
                database = FirebaseDatabase.getInstance();
                final DatabaseReference hamalatRef = database.getReference("hamalat");
                hamalatRef.child(hamlaKey).child("cases").child(itemClicked.getKey()).setValue(null);
                Toast.makeText(context, "تم الغاء الحالة", Toast.LENGTH_SHORT).show();
            } else {
                // do nothing till now
            }
        }
    }
}