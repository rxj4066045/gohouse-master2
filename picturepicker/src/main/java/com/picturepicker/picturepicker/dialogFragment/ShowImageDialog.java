package com.picturepicker.picturepicker.dialogFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.picturepicker.picturepicker.PicturePicker;
import com.picturepicker.picturepicker.R;
import com.picturepicker.picturepicker.interfaces.PictureLoader;
import com.picturepicker.picturepicker.ui.ZoomImageView;

public class ShowImageDialog extends DialogFragment {
    private static final String ARG_PARAM1 = "param1";

    private String imagePath;
    private ZoomImageView imageView;

    public static ShowImageDialog newInstance(String imagePath) {
        ShowImageDialog fragment = new ShowImageDialog();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, imagePath);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imagePath = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show_image_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.showImageDialog_imageView);
        PictureLoader loader = PicturePicker.prictureLoader;
        if (loader != null)
            loader.loader(getContext(),imageView,imagePath);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
