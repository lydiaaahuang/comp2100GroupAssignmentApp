package com.example.tutorapp.Saved;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutorapp.R;


import java.util.ArrayList;
import java.util.List;

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.ViewHolder> {
    List<SavedInfo> savedInfoList;
    private final SavedRecyclerInterface savedRecyclerInterface;
    Context context;


    /**
     * Initialise the dataset of the adapter
     * @param savedInfoList
     * @param savedRecyclerInterface
     */

    public SavedAdapter(List<SavedInfo> savedInfoList, SavedRecyclerInterface savedRecyclerInterface, Context context){
        this.savedRecyclerInterface = savedRecyclerInterface;
        this.savedInfoList = savedInfoList;
        this.context = context;
    }

    /**
     * Creates and instantiates the ViewHolder and its associated View
     *
     */

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.saved_profile, parent, false);
        return new ViewHolder(view, savedRecyclerInterface);
    }

    @Override
    /**
     * Method to associate a ViewHolder with data. The method fetches the appropriate
     * data and uses the data to fill in the view holder's layout.
     */
    public void onBindViewHolder(@NonNull SavedAdapter.ViewHolder holder, int position) {

        // Used different names for clarity
        int image = savedInfoList.get(position).getProfileImage();
        int status = savedInfoList.get(position).getStatusIcon();
        String name = savedInfoList.get(position).getName_saved_profile();
        String bio = savedInfoList.get(position).getSaved_bio();
        String recommend = savedInfoList.get(position).getSaved_recommend();
        holder.setData(image, status, name, bio,recommend);

    }

    @Override
    public int getItemCount() {
        int size;
        if (savedInfoList == null){
            size = 0;
        } else {
            size = savedInfoList.size();
        }
        return size;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView profilePic;
        private ImageView statusPic;
        private TextView nameText;
        private TextView bioText;
        private TextView recommendText;

        public ViewHolder(@NonNull View itemView, SavedRecyclerInterface savedRecyclerInterface) {
            super(itemView);
            profilePic= itemView.findViewById(R.id.saved_profileImage);
            statusPic = itemView.findViewById(R.id.saved_statusIcon);
            nameText = itemView.findViewById(R.id.name_saved_profile);
            bioText = itemView.findViewById(R.id.saved_bio);
            recommendText = itemView.findViewById(R.id.saved_recommendations);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (savedRecyclerInterface != null){
                        int position = getBindingAdapterPosition();

                        if (position != RecyclerView.NO_POSITION){
                            savedRecyclerInterface.onItemClick(position);
                        }
                    }
                }
            });

        }

        public void setData(int image, int status, String name, String bio, String recommend)  {
            profilePic.setImageResource(image);
            statusPic.setImageResource(status);
            nameText.setText(name);
            bioText.setText(bio);
            recommendText.setText(recommend);
        }
    }
}
