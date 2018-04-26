package apps.amazon.com.dide.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import apps.amazon.com.dide.R;
import apps.amazon.com.dide.activities.ViewPostActivity;
import apps.amazon.com.dide.models.PostModel;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{


    Context context;
    ArrayList<PostModel> posts;
    PostModel postModel;

    public RecyclerAdapter(Context context, ArrayList<PostModel> posts) {
        this.context = context;
        this.posts = posts;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position){
        holder.author.setText(String.format("~%s", posts.get(position).getAuthor()));
        holder.title.setText(posts.get(position).getTitle());
        postModel = posts.get(position);
    }



    @Override
    public int getItemCount() {
        return posts.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.feed_item, parent, false);

        return new MyViewHolder(v);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView author, title;

        public MyViewHolder(View itemView) {
            super(itemView);

            author = itemView.findViewById(R.id.author);
            title = itemView.findViewById(R.id.title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putString("AUTHOR", postModel.getAuthor());
            bundle.putString("TITLE", postModel.getTitle());
            bundle.putString("MESSAGE", postModel.getMessage());

            context.startActivity(new Intent(context, ViewPostActivity.class).putExtras(bundle));

        }
    }

}
