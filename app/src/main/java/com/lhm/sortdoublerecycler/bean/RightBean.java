package com.lhm.sortdoublerecycler.bean;

import java.io.Serializable;

/**
 * Created on 2019/5/31 10:32
 * <p>
 * author lhm
 * <p>
 * Description:
 * <p>
 * Remarks:
 */
public class RightBean implements Serializable {
    private String name;
    private String titleName;
    private String tag;
    private int isTitle;
    private String imgsrc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getIsTitle() {
        return isTitle;
    }

    public void setIsTitle(int isTitle) {
        this.isTitle = isTitle;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }
}
