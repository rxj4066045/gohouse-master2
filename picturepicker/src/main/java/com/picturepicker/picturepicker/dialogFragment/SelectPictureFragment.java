package com.picturepicker.picturepicker.dialogFragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IntRange;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.picturepicker.picturepicker.PicturePicker;
import com.picturepicker.picturepicker.R;
import com.picturepicker.picturepicker.base.BaseFragment;
import com.picturepicker.picturepicker.bean.SelectPackageBean;
import com.picturepicker.picturepicker.interfaces.OnAdapterCheckedChangeListener;
import com.picturepicker.picturepicker.interfaces.PictureLoader;
import com.picturepicker.picturepicker.permissionUtils.PermissionAdapter;
import com.picturepicker.picturepicker.permissionUtils.PermissionInit;
import com.picturepicker.picturepicker.permissionUtils.PermissionUtil;
import com.picturepicker.picturepicker.ui.MarkCheckBox;
import com.picturepicker.picturepicker.ui.SelectPackagePopupWindow;
import com.picturepicker.picturepicker.utils.PhotoUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectPictureFragment extends BaseFragment implements View.OnClickListener {

    private int count = 0;
    private RecyclerView recyclerView;
    private SelectPictureAdapter adapter;
    private Map<String, List<String>> pictures = new HashMap<>();
    private List<String> allImages = new ArrayList<>();
    private List<SelectPackageBean> packageBeans = new ArrayList<>();
    private SelectPackagePopupWindow albumsSelectPopWin;
    private TextView tv_selectAlbum;
    private View v_bottomTab;
    private List<String> selectImages = new ArrayList<>();
    private PicturePicker.SelectImagesCallBack selectImagesCallBack;

    public static SelectPictureFragment newInstance(@IntRange(from = 1) int count) {
        SelectPictureFragment fragment = new SelectPictureFragment();
        Bundle args = new Bundle();
        args.putInt("count", count);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            count = getArguments().getInt("count", count);
        }
    }

    public void setSelectImagesCallBack(PicturePicker.SelectImagesCallBack selectImagesCallBack) {
        this.selectImagesCallBack = selectImagesCallBack;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        initRecyclerView();
        initbottomTab();
        initPopWin();

        //获取读写内存卡权限
        PermissionUtil.builder(getActivity())
                .addPermissions(PermissionInit.STORAGE)
                .execute(new PermissionAdapter() {
                    @Override
                    public void allAgree() {
                        initData();
                    }
                });
    }

    //初始化底部标签控件
    private void initbottomTab() {
        v_bottomTab = findViewById(R.id.selectPictureFragment_bottomTab);
        findViewById(R.id.selectPictureFragment_textView_confirm).setOnClickListener(this);
        findViewById(R.id.selectPictureFragment_openCamera).setOnClickListener(this);
        tv_selectAlbum = findViewById(R.id.selectPictureFragment_textView_selectAlbum);
        tv_selectAlbum.setOnClickListener(this);
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.selectPictureFragment_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapter = new SelectPictureAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setAdapterCheckedChangeListener(new OnAdapterCheckedChangeListener() {
            @Override
            public void onCheckedChanged(boolean isChecked, int position) {
                String path = adapter.getData().get(position);
                if (isChecked)
                    selectImages.add(path);
                else
                    selectImages.remove(path);
                if (selectImages.size() >= count) {
                    int max = selectImages.size() - count;
                    for (int i = 0; i < max; i++) {
                        selectImages.remove(0);
                    }
                    showToast("最多只能选择"+count+"张图");
                }
                adapter.setNewData(adapter.getData());
            }
        });
    }

    //初始化相册选择弹窗
    private void initPopWin() {
        albumsSelectPopWin = new SelectPackagePopupWindow(getContext());
//        设置弹窗状态监听
        albumsSelectPopWin.setSelectPackageLineaer(new SelectPackagePopupWindow.SelectPackageLineaer() {
            @Override
            public void showing() {

            }

            @Override
            public void dismiss() {

            }

            @Override
            public void selectPackage(SelectPackageBean packageBean) {
                //关闭弹窗
                albumsSelectPopWin.dismiss();
                String packageName = packageBean.getPackageName();
                //判断相册名称是否“全部图片”，是的话显示全部图片，不是则显示对应相册
                tv_selectAlbum.setText(packageName + "(" + packageBean.getCount() + ")");
                if (!packageName.equals("全部图片")) {
                    initRecyclerViewData(pictures.get(packageName));
                } else {
                    initRecyclerViewData(allImages);
                }
            }
        });
    }

    //获取手机全部图片，通过PhotoUtils.getPictures()获取
    private void initData() {
        PhotoUtils.getPictures(this)
                .achieve(new PhotoUtils.PicturesCallBack() {
                    @Override
                    public void callBack(Map<String, List<String>> pictures) {
                        initData(pictures);
                    }
                });
    }

    //初始化获取到的图片
    private void initData(Map<String, List<String>> pictures) {
        this.pictures = pictures;
        //把所有图片填充进allImages里
        for (String packageName : pictures.keySet()) {
            List<String> list = pictures.get(packageName);
            if (list != null && list.size() > 0) {
                allImages.addAll(list);
                SelectPackageBean bean = new SelectPackageBean(packageName, list.get(0), list.size());
                packageBeans.add(bean);
            }
        }

        if (allImages.size() > 0) {
            SelectPackageBean bean = new SelectPackageBean("全部图片", allImages.get(0), allImages.size());
            packageBeans.add(0, bean);
        }
        //把相册对应信息填入相册选择器里
        albumsSelectPopWin.setData(packageBeans);
        initRecyclerViewData(allImages);
        //popupWindow高度改成和recyclerView一致
        albumsSelectPopWin.setHeight(recyclerView.getHeight());
    }

    //填充recyclerView数据
    private void initRecyclerViewData(List<String> images) {
        adapter.setNewData(images);
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_select_picture_in_bottom;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.selectPictureFragment_openCamera) {
            openCamera();
        } else if (i == R.id.selectPictureFragment_textView_selectAlbum) {
            showPopWin();
        } else if (i == R.id.selectPictureFragment_textView_confirm) {
            confirm();
        }
    }

    //把选择的图片通过回调传递出去
    private void confirm() {
        if (selectImagesCallBack != null) {
            if (selectImages.size() > 0) {
                selectImagesCallBack.selectImages(selectImages);
                selectImages.clear();
                adapter.setNewData(adapter.getData());
            } else {
                showToast("请至少选择一张图片");
            }
        }
    }

    //打开相机，首先需要获取摄像头权限
    private void openCamera() {
        PermissionUtil.builder(getActivity())
                .addPermissions(PermissionInit.CAMERA,PermissionInit.STORAGE)
                .execute(new PermissionAdapter() {
                    @Override
                    public void allAgree() {
                        openCamera2();
                    }
                });
    }

    //开启拍照
    private void openCamera2() {
        PhotoUtils.camera(getActivity(), PicturePicker.authority)
                .toCamera(new PhotoUtils.CameraImageBack() {
                    @Override
                    public void cameraImage(File file) {
                        adapter.addData(0, file.getAbsolutePath());
                        recyclerView.scrollToPosition(0);
                    }

                    @Override
                    public void erron(String err) {

                    }
                });
    }

//    显示相册选择器
    private void showPopWin() {
        if (albumsSelectPopWin != null && !albumsSelectPopWin.isShowing())
            albumsSelectPopWin.showAsDropDown(v_bottomTab
                    , 0, 0, Gravity.BOTTOM | Gravity.START);
    }

    public class SelectPictureAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        private OnAdapterCheckedChangeListener adapterCheckedChangeListener;

        public SelectPictureAdapter() {
            super(R.layout.select_picture_bottom_item);
        }

        @Override
        protected void convert(final BaseViewHolder helper, final String item) {
            ImageView imageView = helper.getView(R.id.select_picture_bottom_item_imageView);
            PictureLoader loader = PicturePicker.prictureLoader;
            if (loader != null)
                loader.loader(mContext, imageView, item);

            final MarkCheckBox checkBox = helper.getView(R.id.select_picture_much_item_checkBox);
            checkBox.setOnCheckedChangeListener(null);
            int index = selectImages.indexOf(item);
            checkBox.setChecked(index != -1);
            checkBox.setMarkText((index + 1) + "");
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    isChecked = checkBox.isChecked();
                    if (adapterCheckedChangeListener != null)
                        adapterCheckedChangeListener.onCheckedChanged(isChecked, helper.getLayoutPosition());
                }
            });
        }

        public void setAdapterCheckedChangeListener(OnAdapterCheckedChangeListener adapterCheckedChangeListener) {
            this.adapterCheckedChangeListener = adapterCheckedChangeListener;
        }
    }

}
