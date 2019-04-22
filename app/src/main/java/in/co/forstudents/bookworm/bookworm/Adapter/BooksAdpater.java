package in.co.forstudents.bookworm.bookworm.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import in.co.forstudents.bookworm.bookworm.MainDB;
import in.co.forstudents.bookworm.bookworm.R;
import in.co.forstudents.bookworm.bookworm.WebViewActivity;

/**
 * Created by VRAj on 20-02-2018.
 */

public class BooksAdpater extends RecyclerView.Adapter<BooksAdpater.ViewHolder> {
    private Context context;
    private List<MainDB> movies;
    public BooksAdpater(Context context, List<MainDB> movies) {
        this.context = context;
        this.movies = movies;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.movieName.setText(movies.get(position).getBookname());
        holder.movieGenre.setText(movies.get(position).getBookgenre());
        Glide.with(context).load(movies.get(position).getImageLink()).into(holder.imageView);
    }



    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView movieName;
        public TextView movieGenre;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            movieName = (TextView) itemView.findViewById(R.id.moviename);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            movieGenre = (TextView) itemView.findViewById(R.id.genre);
            imageView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            int position = getPosition();
            showPopupMenu(v,position);
        }
    }

    public void showPopupMenu(View view, int poaition) {
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new MenuClickListener(poaition));
        popup.show();
    }


    class MenuClickListener implements PopupMenu.OnMenuItemClickListener {
        Integer pos;
        public MenuClickListener(int pos) {
            this.pos=pos;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_favourite:
                    Toast.makeText(context, movies.get(pos).getBookname()+" is added to cart", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_watch:
                    Intent towebview = new Intent(context, WebViewActivity.class);
                    towebview.putExtra("nameofbook",movies.get(pos).getBookgenre());
                    Log.i("LINK",movies.get(pos).getBookgenre());
                    context.startActivity(towebview);
                    return true;
                case R.id.action_book:
                    Toast.makeText(context, "Your library is included with "+movies.get(pos).getBookname(), Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }
}
