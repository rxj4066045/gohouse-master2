package com.picturepicker.picturepicker.bean;

public class SelectPackageBean {
    public String imagePaht;
    public String packageName;
    public int count;

    public SelectPackageBean(String packageName, String imagePaht, int count) {
        this.imagePaht = imagePaht;
        this.packageName = packageName;
        this.count = count;
    }

    public String getImagePaht() {
        return imagePaht;
    }

    public void setImagePaht(String imagePaht) {
        this.imagePaht = imagePaht;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
