package com.nyt;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import entites.Medium;
import entites.Result;
import utils.Constants;

public class FeedDetailActivity extends AppCompatActivity {

    private Result result;
    private TextView title, details, auther, caption, time;
    ImageView feedImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_details_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // toolbar fancy stuff
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.most_popular);
        feedImageView = findViewById(R.id.feed_imageView);
        time = findViewById(R.id.time);
        details = findViewById(R.id.details);
        caption = findViewById(R.id.caption);
        title = findViewById(R.id.title);
        auther = findViewById(R.id.auther);
        result = getIntent().getParcelableExtra(Constants.FEED_ITEM);
        if (result != null) {
            List<Medium> medium = result.getMedia();
            if (medium.size() > 0)
                if (medium.get(0).getMediaMetadata() != null) {
                    caption.setText(medium.get(0).getCaption());
                    String imagePath = medium.get(0).getMediaMetadata().get(2).getUrl();
                    Glide
                            .with(this)
                            .load(imagePath)
                            .apply(new RequestOptions()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .dontAnimate()

                                    .dontTransform().placeholder(R.drawable.circle_profile).error(R.drawable.circle_profile))

                            .into(feedImageView);
                }


            title.setText(result.getTitle());
            time.setText(result.getPublishedDate());
            details.setText(result.getAbstract());
            if (!TextUtils.isEmpty(result.getByline())) {
                auther.setText(result.getByline());
            } else {
                if (!TextUtils.isEmpty(result.getColumn())) {
                    auther.setText(result.getColumn());
                }

            }


        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
          int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
