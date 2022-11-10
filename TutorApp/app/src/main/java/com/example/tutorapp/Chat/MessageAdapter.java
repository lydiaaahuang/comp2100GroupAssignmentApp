package com.example.tutorapp.Chat;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.constraintlayout.widget.ConstraintLayout;
        import androidx.constraintlayout.widget.ConstraintSet;
        import androidx.recyclerview.widget.RecyclerView;

//        import com.bumptech.glide.Glide;
        import com.example.tutorapp.R;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;

        import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private ArrayList<ChatMessage> messages;
    private String receiverImg;
    private Context context;

    public MessageAdapter(ArrayList<ChatMessage> messages,String receiverImg,Context context) {
        this.messages = messages;
        this.receiverImg = receiverImg;
        this.context = context;
    }

    public static  final int MSG_TYPE_LEFT = 0;
    public static  final int MSG_TYPE_RIGHT = 1;

    FirebaseUser fuser;

    ///////
    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatMessage chat = messages.get(position);
        holder.show_message.setText(chat.getMessage());
    }

//    @Override
//    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
////        holder.txtmessage.setText((messages).get(position).getContentOfMsg());  //gets msgs by position and sets it as the txtmessage to be displayed
////
////        ConstraintLayout constraintLayout = holder.x;
//
//        ChatMessage chat = mChat.get(position);
//
//        holder.show_message.setText(chat.getMessage());
//
////
////        if(messages.get(position).getSender().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {  //Might be UID???? check this
////            ConstraintSet constraintSet = new ConstraintSet();
////            constraintSet.clone(constraintLayout);  //get info in that moment
////
////        } else {
////            Glide.with(context).load(receiverImg).error(R.drawable.ic_baseline_profile_icon).placeholder(R.drawable.ic_baseline_profile_icon).into(holder.pfp);
////            ConstraintSet constraintSet = new ConstraintSet();
////            constraintSet.clone(constraintLayout);  //get info in that moment
////        }
//    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView show_message;
        public TextView txt_seen;

        public ViewHolder(View itemView) {
            super(itemView);
            show_message = itemView.findViewById(R.id.show_message);
            txt_seen = itemView.findViewById(R.id.txt_seen);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if (messages.get(position).getReceiver().equals(fuser.getUid())){
            return MSG_TYPE_LEFT;
        } else {
            return MSG_TYPE_RIGHT;
        }
    }
    //////

}