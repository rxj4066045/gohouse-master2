package com.picturepicker.picturepicker;

import android.app.Activity;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.picturepicker.picturepicker.dialogFragment.SelectPictureMuchFragment;
import com.picturepicker.picturepicker.dialogFragment.SelectPictureOneFragment;
import com.picturepicker.picturepicker.interfaces.PictureLoader;
import com.picturepicker.picturepicker.utils.PhotoUtils;

import java.util.List;

public class PicturePicker {
    public static PictureLoader prictureLoader;
    public static final String authority = "com.picturepicker.picturepicker.fileprovide";

    private PicturePicker() {
    }

    public static void setPrictureLoader(PictureLoader prictureLoader) {
        PicturePicker.prictureLoader = prictureLoader;
    }


    private static void selectImage(FragmentManager manager, int count, boolean isEnableCamera, SelectImagesCallBack selectImagesCallBack){
        if(count < 1)
            throw new RuntimeException("需要选择的图片不能少于1张");

        if(count == 1){
            SelectPictureOneFragment oneFragment = SelectPictureOneFragment.newInstance(isEnableCamera);
            oneFragment.setSelectImagesCallBack(selectImagesCallBack);
            oneFragment.show(manager,"");
        }else {
            SelectPictureMuchFragment muchFragment = SelectPictureMuchFragment.newInstance(count, isEnableCamera);
            muchFragment.setSelectImagesCallBack(selectImagesCallBack);
            muchFragment.show(manager,"");
        }

    }

    public static Build selectImages(@NonNull FragmentManager manager){
        return new Build(manager);
    }

    public static class Build{
        private FragmentManager manager;
        private int count = 1;
        private boolean isEnableCamera = false;

        private Build(FragmentManager manager) {
            this.manager = manager;
        }

        public Build setCount(@IntRange(from=1) int count) {
            this.count = count;
            return this;
        }

        public Build setEnableCamera(boolean enableCamera) {
            isEnableCamera = enableCamera;
            return this;
        }

        public void build(SelectImagesCallBack selectImagesCallBack){
            selectImage(manager,count, isEnableCamera,selectImagesCallBack);
        }
    }

    public static PhotoUtils.CropImage cropImage(@NonNull Activity activity, @NonNull String imagePath){
        return PhotoUtils.cropImage(activity,imagePath,PicturePicker.authority);
    }

    public static interface SelectImagesCallBack{
        public void selectImages(List<String> images);
    }

}
