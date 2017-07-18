package com.onemoreerror.mygate.ContactsModel;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class ContactVO implements Parcelable {
    private String ContactImage;
    private String ContactName;
    private String ContactNumber;

    public ContactVO(Parcel in) {
        ContactImage = in.readString();
        ContactName = in.readString();
        ContactNumber = in.readString();
    }

    public ContactVO() {
        //empty
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ContactImage);
        dest.writeString(ContactName);
        dest.writeString(ContactNumber);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ContactVO> CREATOR = new Creator<ContactVO>() {
        @Override
        public ContactVO createFromParcel(Parcel in) {
            return new ContactVO(in);
        }

        @Override
        public ContactVO[] newArray(int size) {
            return new ContactVO[size];
        }
    };

    public String getContactImage() {
        return ContactImage;
    }

    public void setContactImage(String contactImage) {
        Log.e("AK",""+contactImage);
        this.ContactImage = contactImage;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }
}