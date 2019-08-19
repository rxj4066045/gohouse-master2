package com.picturepicker.picturepicker.permissionUtils;

/**
 * Created by Administrator - PC on 2017/11/21.
 */

public interface PermissionsInterface {
    /**
     * 通过全部权限
     */
    void allAgree();

    /**
     * 至少拒绝了一个权限
     */
    void refuse();

    /**
     * 获取到的权限
     * @param permission
     */
    void agree(String permission);

    /**
     * 被拒绝的权限
     * @param permission
     */
    void refuse(String permission);

    /**
     * 被拒绝且选择了不再提示的权限
     * @param permission
     */
    void refusAndNotPrompt(String permission);
}
