package com.picturepicker.picturepicker.interfaces;

import android.content.Context;
import android.widget.ImageView;

public interface PictureLoader {
    public void loader(Context context, ImageView imageView, String photoPath);
}
