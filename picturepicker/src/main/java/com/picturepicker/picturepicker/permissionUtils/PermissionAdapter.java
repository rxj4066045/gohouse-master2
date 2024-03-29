package com.picturepicker.picturepicker.permissionUtils;

/**
 * Created by Administrator - PC on 2017/10/24.
 */

public class PermissionAdapter implements PermissionsInterface{
    @Override
    public void allAgree() {
        //所有权限被授权时调用
    }

    @Override
    public void refuse() {
        //至少有一项权限没被授权时调用
    }

    @Override
    public void agree(String permission) {
        //通过授权的权限
    }

    @Override
    public void refuse(String permission) {
        //没通过授权的权限
    }

    @Override
    public void refusAndNotPrompt(String permission) {
        //没通过授权并选择了“不再提示的权限”
    }
}
