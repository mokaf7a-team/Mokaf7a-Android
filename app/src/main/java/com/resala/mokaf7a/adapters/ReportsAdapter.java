package com.resala.mokaf7a.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.resala.mokaf7a.R;
import com.resala.mokaf7a.ReportDetails;
import com.resala.mokaf7a.classes.Report;

import java.util.ArrayList;

public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.ViewHolder> {
    private final ArrayList<Report> reportItems;
    private final Context context;

    public ReportsAdapter(ArrayList<Report> reportItems, Context context) {
        this.reportItems = reportItems;
        this.context = context;
    }

    /**
     * Called when RecyclerView needs a new {@link ReportsAdapter.ViewHolder} of the given type to
     * represent an item. This new ViewHolder should be constructed with a new View that can represent
     * the items of the given type. You can either create a new View manually or inflate it from an
     * XML layout file.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an
     *                 adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ReportsAdapter.ViewHolder, int)
     */
    @NonNull
    @Override
    public ReportsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.report_item, parent, false);
        return new ReportsAdapter.ViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should update
     * the contents of the {@link ReportsAdapter.ViewHolder#itemView} to reflect the item at the given
     * position.
     *
     * <p>Note that unlike {@link ListView}, RecyclerView will not call this method again if the
     * position of the item changes in the data set unless the item itself is invalidated or the new
     * position cannot be determined. For this reason, you should only use the <code>position</code>
     * parameter while acquiring the related data item inside this method and should not keep a copy
     * of it. If you need the position of an item later on (e.g. in a click listener), use {@link
     * ReportsAdapter.ViewHolder#getAdapterPosition()} which will have the updated adapter position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the item at
     *                 the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ReportsAdapter.ViewHolder holder, int position) {
        Report item = reportItems.get(position);
        holder.areaTV.setText(item.area);
        holder.branchTV.setText(item.branch);
        holder.dateTV.setText(item.date);
        if (item.feed_back_type == null || item.feed_back_type.trim().isEmpty()) {
            holder.id_feedbackTV.setText(item.id + " = جاري التعامل");
            holder.id_feedbackTV.setTextColor(ContextCompat.getColor(context, R.color.whiteColor));
        } else {
            holder.id_feedbackTV.setText(item.id + " = " + item.feed_back_type);
            if (item.feed_back_type.contains("موجود") || item.feed_back_type.contains("مستحق"))
                holder.id_feedbackTV.setTextColor(ContextCompat.getColor(context, R.color.redColor));
            else if (item.feed_back_type.contains("تم") || item.feed_back_type.contains("التعامل"))
                holder.id_feedbackTV.setTextColor(ContextCompat.getColor(context, R.color.mintGreen));
            else
                holder.id_feedbackTV.setTextColor(ContextCompat.getColor(context, R.color.gold));

        }
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return reportItems.size();
    }

    /**
     * ****************************************************************************
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView id_feedbackTV;
        TextView areaTV;
        TextView branchTV;
        TextView dateTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id_feedbackTV = itemView.findViewById(R.id.id_feedbackTV);
            areaTV = itemView.findViewById(R.id.areaTV);
            dateTV = itemView.findViewById(R.id.dateTV);
            branchTV = itemView.findViewById(R.id.branchTV);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Report itemClicked = reportItems.get(position);

            Intent intent;
            intent = new Intent(context, ReportDetails.class);
            intent.putExtra("name", itemClicked.name);
            intent.putExtra("phone", itemClicked.phoneNo);
            intent.putExtra("address", itemClicked.address);
            intent.putExtra("gender", itemClicked.gender);
            intent.putExtra("date", itemClicked.date);
            intent.putExtra("seen", itemClicked.seen);
            intent.putExtra("state", itemClicked.state);
            intent.putExtra("notes", itemClicked.notes);
            intent.putExtra("first_feedback", itemClicked.first_feedback);
            intent.putExtra("second_feedback", itemClicked.second_feedback);

            intent.putExtra("id", String.valueOf(itemClicked.id));
            intent.putExtra("blankets", String.valueOf(itemClicked.blankets));
            intent.putExtra("case_num", String.valueOf(itemClicked.case_num));
            intent.putExtra("clothes_num", String.valueOf(itemClicked.clothes_num));
            intent.putExtra("meals", String.valueOf(itemClicked.meals));
            intent.putExtra("help_date", itemClicked.help_date);
            intent.putExtra("feed_back_type", itemClicked.feed_back_type);
            intent.putExtra("feed_back", itemClicked.feed_back);

            intent.putExtra("key", itemClicked.pushid);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);
        }
    }
}
