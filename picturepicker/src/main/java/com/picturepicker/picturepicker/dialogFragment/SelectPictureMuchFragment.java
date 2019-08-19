package com.picturepicker.picturepicker.dialogFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
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
import com.picturepicker.picturepicker.ui.MarkCheckBox;
import com.picturepicker.picturepicker.ui.SelectPackagePopupWindow;
import com.picturepicker.picturepicker.utils.PhotoUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectPictureMuchFragment extends BaseDialogFragment implements View.OnClickListener {

    private boolean isEnableCamera = false;
    private int count = 1;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private Map<String, List<String>> pictures = new HashMap<>();
    private List<String> allImage = new ArrayList<>();
    private List<String> popSelectImages = new ArrayList<>();
    private List<String> selectImages = new ArrayList<>();
    private List<SelectPackageBean> packageNames = new ArrayList<>();
    private SelectPackagePopupWindow albumsSelectPopWin;

    private TextView tv_packageName;
    private ImageView iv_packageName;
    private TextView tv_confirm;

    private PicturePicker.SelectImagesCallBack selectImagesCallBack;

    public static SelectPictureMuchFragment newInstance(int count, boolean isEnableCamera) {
        SelectPictureMuchFragment fragment = new SelectPictureMuchFragment();
        Bundle args = new Bundle();
        args.putBoolean("isEnableCamera", isEnableCamera);
        args.putInt("count", count);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isEnableCamera = getArguments().getBoolean("isEnableCamera", false);
            count = getArguments().getInt("count", count);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        PermissionUtil.builder(getActivity())
                .addPermissions(PermissionInit.STORAGE)
                .execute(new PermissionAdapter() {
                    @Override
                    public void allAgree() {
                        initPictures();
                    }
                });
    }

    //初始化控件
    private void initView() {
        initToolbar();
        initPopWin();
        initRecyclerView();
    }

    private void initToolbar() {
        findViewById(R.id.toolbar_imageView_back).setOnClickListener(this);
        findViewById(R.id.toolbar_selectPackage).setOnClickListener(this);
        tv_packageName = findViewById(R.id.toolbar_selectPackage_text);
        iv_packageName = findViewById(R.id.toolbar_selectPackage_image);
        tv_confirm = findViewById(R.id.toolbar_confirm);
        tv_confirm.setText("确定" + "(" + selectImages.size() + "\\" + count + ")");
        tv_confirm.setOnClickListener(this);
    }

    private void initPopWin() {
        albumsSelectPopWin = new SelectPackagePopupWindow(getContext());
        albumsSelectPopWin.setSelectPackageLineaer(new SelectPackagePopupWindow.SelectPackageLineaer() {
            @Override
            public void showing() {
                //箭头旋转90度
                iv_packageName.animate().rotation(-90).setDuration(300).start();
            }

            @Override
            public void dismiss() {
                //箭头复原
                iv_packageName.animate().rotation(0).setDuration(300).start();
            }

            @Override
            public void selectPackage(SelectPackageBean packageBean) {
                //把相册对应图片填充进recyclerView
                if (albumsSelectPopWin.isShowing())
                    albumsSelectPopWin.dismiss();
                String packageName = packageBean.getPackageName();
                tv_packageName.setText(packageName);
                if (packageName.equals("所有图片")) {
                    popSelectImages = allImage;
                } else
                    popSelectImages = pictures.get(packageName);

                initRecyclerViewData(popSelectImages);
            }
        });
    }


    private void initRecyclerView() {
        recyclerView = findViewById(R.id.selectPictureOneFragment_recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter = new Adapter();
        recyclerView.setAdapter(adapter);
        adapter.setAdapterCheckedChangeListener(new OnAdapterCheckedChangeListener() {
            @Override
            public void onCheckedChanged(boolean isChecked, int position) {
                String path = adapter.getData().get(position);
                if (isChecked) {
                    selectImages.add(path);
                    adapter.notifyItemChanged(position);
                } else {
                    selectImages.remove(path);
                    adapter.setNewData(popSelectImages);
                }
                tv_confirm.setText("确定" + "(" + selectImages.size() + "\\" + count + ")");
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter a, View view, int position) {
                String path = adapter.getData().get(position);
                //判断点击的item是不是相机图标，是的话打开相机，不是则显示对应图片
                if (path.equals("相机")) {
                    //获取相机权限
                    PermissionUtil.builder(getActivity())
                            .addPermissions(PermissionInit.CAMERA, PermissionInit.STORAGE)
                            .execute(new PermissionAdapter() {
                                @Override
                                public void allAgree() {
                                    openCamera();
                                }
                            });

                } else {
                    //弹窗放大图片
                    ShowImageDialog.newInstance(path).show(getChildFragmentManager(), "");
                }
            }
        });
    }

    //获取内存卡内选择图片
    private void initPictures() {
        PhotoUtils.getPictures(this)
                .achieve(new PhotoUtils.PicturesCallBack() {
                    @Override
                    public void callBack(Map<String, List<String>> pictures) {
                        initPictures(pictures);
                    }
                });
    }


    //开启相机_获取照片
    private void openCamera() {

        if (selectImages.size() >= count) {
            showToast("最多只能选择" + count + "张图");
            return;
        }

        PhotoUtils.camera(getActivity(), PicturePicker.authority)
                .toCamera(new PhotoUtils.CameraImageBack() {
                    @Override
                    public void cameraImage(File file) {
                        openCamera(file.getAbsolutePath());
                    }

                    @Override
                    public void erron(String err) {
                        showToast(err);
                    }
                });
    }

    //把照片填充进recycerView
    private void openCamera(String absolutePath) {
        if (allImage.size() != popSelectImages.size()) {
            if (allImage.size() > 0 && allImage.get(0).equals("相机")) {
                allImage.add(1, absolutePath);
            } else {
                allImage.add(absolutePath);
            }
        }
        selectImages.add(absolutePath);
        adapter.addData(1, absolutePath);
        popSelectImages = adapter.getData();
    }


    //初始化图片信息，相册归类
    private void initPictures(Map<String, List<String>> pictures) {
        this.pictures = pictures;
        packageNames.clear();
        allImage.clear();
        for (String packageName : pictures.keySet()) {
            List<String> list = pictures.get(packageName);
            if (list != null && list.size() > 0) {
                SelectPackageBean packageBean = new SelectPackageBean(packageName, list.get(0), list.size());
                packageNames.add(packageBean);
                allImage.addAll(list);
            }
        }

        if (allImage.size() > 0) {
            packageNames.add(0, new SelectPackageBean("所有图片", allImage.get(0), allImage.size()));
        }

        albumsSelectPopWin.setData(packageNames);
        initRecyclerViewData(allImage);

    }

    //把相册数据填充进recyclerView
    private void initRecyclerViewData(List<String> images) {
        popSelectImages = images;
        if (isEnableCamera) {
            if (popSelectImages.size() > 0) {
                if (!popSelectImages.get(0).equals("相机")) {
                    popSelectImages.add(0, "相机");
                }
            }
        }
        adapter.setNewData(popSelectImages);
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_select_picture;
    }

    public void setSelectImagesCallBack(PicturePicker.SelectImagesCallBack selectImagesCallBack) {
        this.selectImagesCallBack = selectImagesCallBack;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.toolbar_imageView_back) {
            dismiss();

        } else if (i == R.id.toolbar_selectPackage) {
            showPopWin();

        } else if (i == R.id.toolbar_confirm) {
            dismiss();
            if (selectImagesCallBack != null)
                selectImagesCallBack.selectImages(selectImages);

        }
    }

    //显示相册选择弹窗
    private void showPopWin() {
        if (!albumsSelectPopWin.isShowing())
            albumsSelectPopWin.showAsDropDown(findViewById(R.id.toolbar));
    }

    public class Adapter extends BaseQuickAdapter<String, BaseViewHolder> {

        private OnAdapterCheckedChangeListener adapterCheckedChangeListener;

        public Adapter() {
            super(R.layout.select_picture_much_item);
        }

        @Override
        protected void convert(final BaseViewHolder helper, String item) {
            ImageView imageView = helper.getView(R.id.select_picture_much_item_image);

            final MarkCheckBox checkBox = helper.getView(R.id.select_picture_much_item_checkBox);
            checkBox.setOnCheckedChangeListener(null);

            if (item.equals("相机")) {
                checkBox.setVisibility(View.GONE);
                imageView.setImageResource(R.mipmap.camera_icon);
            } else {
                PictureLoader loader = PicturePicker.prictureLoader;
                if (loader != null)
                    loader.loader(mContext, imageView, item);

                checkBox.setVisibility(View.VISIBLE);
                int index = selectImages.indexOf(item);
                if (index == -1) {
                    checkBox.setChecked(false);
                    checkBox.setMarkText("");
                } else {
                    checkBox.setChecked(true);
                    checkBox.setMarkText((index + 1) + "");
                }
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        isChecked = checkBox.isChecked();
                        if (isChecked) {
                            if (selectImages.size() >= count) {
                                showToast("最多只能选择" + count + "张图");
                                checkBox.setChecked(false);
                                return;
                            }
                        }
                        if (adapterCheckedChangeListener != null)
                            adapterCheckedChangeListener.onCheckedChanged(isChecked, helper.getLayoutPosition());
                    }
                });
            }


        }

        public void setAdapterCheckedChangeListener(OnAdapterCheckedChangeListener adapterCheckedChangeListener) {
            this.adapterCheckedChangeListener = adapterCheckedChangeListener;
        }
    }

}
