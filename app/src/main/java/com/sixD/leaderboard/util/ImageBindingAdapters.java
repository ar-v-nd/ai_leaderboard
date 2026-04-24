package com.sixD.leaderboard.util;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.sixD.leaderboard.R;

/**
 * Custom Data Binding adapters for [ImageView] using the Glide library.
 */
public class ImageBindingAdapters {

    /**
     * Loads an image from a URL into an [ImageView].
     * If the URL is empty or null, a placeholder image is shown.
     * Applies a circular transformation with a large corner radius.
     *
     * @param view The ImageView target.
     * @param url  The image URL to load.
     */
    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView view, String url) {
        if (url == null || url.isEmpty()) {
            // Load placeholder if URL is null or empty
            Glide.with(view.getContext())
                .load(R.drawable.placeholder_image)
                .apply(new RequestOptions().transform(new CenterCrop(), new RoundedCornersTransformation(16f)))
                .into(view);
        } else {
            // Load image from URL with rounded corners
            Glide.with(view.getContext())
                .load(url)
                .apply(new RequestOptions()
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.placeholder_image)
                    .transform(new CenterCrop(), new RoundedCornersTransformation(60f)))
                .into(view);
        }
    }
}
