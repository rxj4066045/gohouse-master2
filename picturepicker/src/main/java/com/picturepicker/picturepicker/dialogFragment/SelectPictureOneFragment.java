package com.picturepicker.picturepicker.dialogFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.picturepicker.picturepicker.PicturePicker;
import com.picturepicker.picturepicker.R;
import com.picturepicker.picturepicker.base.BaseDialogFragment;
import com.picturepicker.picturepicker.bean.SelectPackageBean;
import com.picturepicker.picturepicker.interfaces.OnAdapterCheckedChangeListener;
import com.picturepicker.picturepicker.interfaces.PictureLoader;
import com.picturepicker.picturepicker.permissionUtils.PermissionAdapter;
import com.picturepicker.picturepicker.permissionUtils.PermissionInit;
import com.picturepicker.picturepicker.permissionUtils.PermissionUtil;
import com.picturepicker.picturepicker.ui.SelectPackagePopupWindow;
import com.picturepicker.picturepicker.utils.PhotoUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectPictureOneFragment extends BaseDialogFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match

    private RecyclerView recyclerView;
    private SelectPictureOneAdapter adapter;
    private Map<String, List<String>> pictures = new HashMap<>();
    private List<String> images = new ArrayList<>();
    private List<SelectPackageBean> packageNames = new ArrayList<>();
    private SelectPackagePopupWindow albumsSelectPopWin;
    private ConstraintLayout cl_toolbar;
    private ImageView iv_arrow;
    private TextView tv_packagename;
    private boolean isEnableCamera = true;
    private List<String> selectsImages = new ArrayList<>();
    private int selectPosition = -1;
    private PicturePicker.SelectImagesCallBack selectImagesCallBack;

    public static SelectPictureOneFragment newInstance(boolean isEnableCamera) {
        SelectPictureOneFragment fragment = new SelectPictureOneFragment();
        Bundle args = new Bundle();
        args.putBoolean("isEnableCamera", isEnableCamera);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isEnableCamera = getArguments().getBoolean("isEnableCamera", false);
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        //获取内存卡读写权限
        PermissionUtil.builder(getActivity())
                .addPermissions(PermissionInit.STORAGE)
                .execute(new PermissionAdapter() {
                    @Override
                    public void allAgree() {
                        initData();
                    }
                });

    }

    private void initView() {
        initToolbar();
        initPopWin();
        initRecyclerView();
    }

    private void initToolbar() {
        findViewById(R.id.toolbar_imageView_back).setOnClickListener(this);
        findViewById(R.id.toolbar_confirm).setOnClickListener(this);
        findViewById(R.id.toolbar_selectPackage).setOnClickListener(this);
        cl_toolbar = findViewById(R.id.toolbar);
        iv_arrow = findViewById(R.id.toolbar_selectPackage_image);
        tv_packagename = findViewById(R.id.toolbar_selectPackage_text);
        tv_packagename.setText("所有图片");
    }

    //初始化相册选择器
    private void initPopWin() {
        albumsSelectPopWin = new SelectPackagePopupWindow(getContext());
        albumsSelectPopWin.setSelectPackageLineaer(new SelectPackagePopupWindow.SelectPackageLineaer() {
            @Override
            public void showing() {
                //箭头旋转90度
                iv_arrow.animate().rotation(-90).setDuration(250).start();
            }

            @Override
            public void dismiss() {
                //箭头回复原状
                iv_arrow.animate().rotation(0).setDuration(250).start();
            }

            @Override
            public void selectPackage(SelectPackageBean packageBean) {
                //获取选择的相册信息
                //关闭相册选择弹窗
                albumsSelectPopWin.dismiss();
                //填充相册对应数据进recyclerView
                String packageName = packageBean.getPackageName();
                tv_packagename.setText(packageName);
                if (packageName.equals("所有图片")) {
                    initRecyclerViewData(images);
                } else {
                    initRecyclerViewData(pictures.get(packageName));
                }
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.selectPictureOneFragment_recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter = new SelectPictureOneAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //判断点击的item是不是相机，是的话打开相机，不是的话显示图片
                String paht = selectsImages.get(position);
                if (paht.equals("相机")) {
                    //打开相机
                    openCamera();
                } else {
                    //展示图片
                    ShowImageDialog.newInstance(paht).show(getChildFragmentManager(), "");
                }
            }
        });

        adapter.setAdapterCheckedChangeListener(new OnAdapterCheckedChangeListener() {
            @Override
            public void onCheckedChanged(boolean isChecked, int position) {
                //item选择监听
                if (isChecked) {
                    int oldPosition = selectPosition;
                    selectPosition = position;
                    if (oldPosition != -1) {
                        adapter.notifyItemChanged(oldPosition);
                    }
                } else {
                    selectPosition = -1;
                }
            }
        });
    }

    //打开相机_判断是否有相机权限
    private void openCamera() {
        PermissionUtil.builder(getActivity())
                .addPermissions(PermissionInit.CAMERA)
                .execute(new PermissionAdapter() {
                    @Override
                    public void allAgree() {
                        openCamera2();
                    }
                });
    }

    //打开相机
    private void openCamera2() {
        PhotoUtils.camera(getActivity(), PicturePicker.authority)
                .toCamera(new PhotoUtils.CameraImageBack() {
                    @Override
                    public void cameraImage(File file) {
                        selectPosition = -1;
                        selectsImages.add(1, file.getAbsolutePath());
                        adapter.setNewData(selectsImages);
                    }

                    @Override
                    public void erron(String err) {
                        showToast(err);
                    }
                });
    }


    //获取所有图片
    private void initData() {
        PhotoUtils.getPictures(this)
                .achieve(new PhotoUtils.PicturesCallBack() {
                    @Override
                    public void callBack(Map<String, List<String>> ps) {
                        initData2(ps);
                    }
                });
    }

    //相册归类
    private void initData2(Map<String, List<String>> pictures) {
        this.pictures = pictures;
        packageNames.clear();
        for (String packageName : pictures.keySet()) {
            List<String> list = pictures.get(packageName);
            if (list != null && list.size() > 0) {
                packageNames.add(new SelectPackageBean(packageName, list.get(0), list.size()));
                images.addAll(list);
            }
        }
        if (images.size() > 0) {
            packageNames.add(0, new SelectPackageBean("所有图片", images.get(0), images.size()));
        }
        albumsSelectPopWin.setData(packageNames);
        initRecyclerViewData(images);
    }

    //填充recyclerView数据
    private void initRecyclerViewData(List<String> images) {
        selectPosition = -1;
        selectsImages.addAll(images);
        if (isEnableCamera) {
            selectsImages.add(0, "相机");
        }
        adapter.setNewData(selectsImages);
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_select_picture;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.toolbar_imageView_back) {
            dismiss();

        } else if (i == R.id.toolbar_confirm) {
            confirm();

        } else if (i == R.id.toolbar_selectPackage) {
            showPopWin();

        }
    }

    //把选择的图片通过回调传递出去
    private void confirm() {
        if (selectPosition == -1) {
            showToast("请选择图片");
            return;
        }
        if (selectImagesCallBack != null) {
            dismiss();
            List<String> list = new ArrayList<>();
            list.add(selectsImages.get(selectPosition));
            selectImagesCallBack.selectImages(list);
        }
    }

    private void showPopWin() {
        if (albumsSelectPopWin.isShowing()) {
            albumsSelectPopWin.dismiss();
        } else {
            albumsSelectPopWin.showAsDropDown(cl_toolbar);
        }
    }


    public void setSelectImagesCallBack(PicturePicker.SelectImagesCallBack selectImagesCallBack) {
        this.selectImagesCallBack = selectImagesCallBack;
    }

    private class SelectPictureOneAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public SelectPictureOneAdapter() {
            super(R.layout.select_picture_one_item);
            openLoadAnimation(SCALEIN);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            final int position = helper.getLayoutPosition();
            PictureLoader loader = PicturePicker.prictureLoader;
            final CheckBox checkBox = helper.getView(R.id.select_picture_one_item_checkBox);
            checkBox.setOnCheckedChangeListener(null);
            ImageView imageView = helper.getView(R.id.select_picture_one_item_image);
            if (item.equals("相机")) {
                checkBox.setVisibility(View.GONE);
                imageView.setImageResource(R.mipmap.camera_icon);
            } else {
                if (loader != null) {
                    loader.loader(mContext, imageView, item);
                }
                checkBox.setChecked(position == selectPosition);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (adapterCheckedChangeListener != null) {
                            adapterCheckedChangeListener.onCheckedChanged(checkBox.isChecked(), position);
                        }
                    }
                });
            }

        }

        private OnAdapterCheckedChangeListener adapterCheckedChangeListener;

        public void setAdapterCheckedChangeListener(OnAdapterCheckedChangeListener adapterCheckedChangeListener) {
            this.adapterCheckedChangeListener = adapterCheckedChangeListener;
        }
    }

}
