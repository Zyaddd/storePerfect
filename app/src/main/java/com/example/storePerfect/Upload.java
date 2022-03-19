package com.example.storePerfect;

public class Upload {

    private String mName, mPrice, mDescription;
    private String mImageUri;

    public Upload() {
        //needed
    }


    public Upload(String mName, String mImageUri) {
        if (mName.trim().equals("")) {
            mName = "No Name";
        }

        this.mName = mName;
        this.mImageUri = mImageUri;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmImageUri(String mImageUri) {
        this.mImageUri = mImageUri;
    }

    public String getmName() {
        return mName;
    }

    public String getmImageUri() {
        return mImageUri;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }
}
