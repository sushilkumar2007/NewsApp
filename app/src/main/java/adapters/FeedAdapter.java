package adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.nyt.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import entites.Result;
import interfaces.IObjectInterface;


public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> implements Filterable {

    private List<Result> itemList;
    private List<Result> feedListFiltered;
    private Context context;
    IObjectInterface iObjectInterface;

    public FeedAdapter(Context context, IObjectInterface iObjectInterface, ArrayList<Result> itemList) {
        this.context = context;
        this.itemList = itemList;
        this.feedListFiltered = itemList;
        this.iObjectInterface = iObjectInterface;


    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_row_item, parent, false);
        FeedViewHolder rcv = new FeedViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {
        final Result result = feedListFiltered.get(position);
        holder.title.setText(result.getTitle());
        holder.time.setText(result.getPublishedDate());
        if (!TextUtils.isEmpty(result.getByline())) {
            holder.auther.setText(result.getByline());
        } else {
            if (!TextUtils.isEmpty(result.getColumn())) {
                holder.auther.setText(result.getColumn());
            }

        }
        String imagePath = getImagePath(result);

        if (imagePath != null)
            Glide
                    .with(holder.itemView.getContext())
                    .load(imagePath)
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .dontAnimate()

                            .dontTransform().placeholder(R.drawable.circle_profile).error(R.drawable.circle_profile))

                    .into(holder.icon);
        else {
            Glide
                    .with(holder.itemView.getContext())
                    .load(R.drawable.circle_profile)
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .dontAnimate()

                            .dontTransform().placeholder(R.drawable.circle_profile).error(R.drawable.circle_profile))

                    .into(holder.icon);
        }
        holder.itemView.setTag(result);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Result result = (Result) v.getTag();
                iObjectInterface.OnItemClick(result);
            }
        });


    }

    @Override
    public int getItemCount() {
        return feedListFiltered.size();
    }

    private String getImagePath(Result result) {
        if (result.getMedia() != null) {
            if (result.getMedia().size() > 0) {
                if (result.getMedia().get(0).getMediaMetadata() != null) {
                    if (result.getMedia().get(0).getMediaMetadata().size() > 0) {
                        if (result.getMedia().get(0).getMediaMetadata().get(0).getUrl() != null) {
                            return result.getMedia().get(0).getMediaMetadata().get(0).getUrl();
                        }
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
        return null;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    feedListFiltered = itemList;
                } else {
                    List<Result> filteredList = new ArrayList<>();
                    for (Result row : itemList) {
                            if (row.getTitle().toLowerCase(Locale.ENGLISH).contains(charString.toLowerCase(Locale.ENGLISH))||row.getByline().toLowerCase(Locale.ENGLISH).contains(charString.toLowerCase(Locale.ENGLISH)) || row.getAbstract().toLowerCase(Locale.ENGLISH).contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    feedListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = feedListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                feedListFiltered = (ArrayList<Result>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void clear() {
        final int size = itemList.size();
        itemList.clear();
        feedListFiltered.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void addAll(ArrayList<Result> results) {
        this.itemList.addAll(results);
        this.feedListFiltered.addAll(results);
        notifyDataSetChanged();
    }


    public class FeedViewHolder extends RecyclerView.ViewHolder {
        public TextView title, time, auther;
        ImageView icon;

        public FeedViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            time = (TextView) view.findViewById(R.id.time);
            icon = (ImageView) view.findViewById(R.id.thumbnail);
            auther = (TextView) view.findViewById(R.id.auther);

        }
    }
}
