package com.onemoreerror.mygate.Adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.onemoreerror.mygate.ContactsModel.ContactVO;
import com.onemoreerror.mygate.ImageUtilities.ImageUtils;
import com.onemoreerror.mygate.R;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllContactsAdapter extends RecyclerView.Adapter<AllContactsAdapter.ContactViewHolder>{

    private List<ContactVO> contactVOList;
    private Context mContext;
    public AllContactsAdapter(List<ContactVO> contactVOList, Context mContext){
        this.contactVOList = contactVOList;
        this.mContext = mContext;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_contact_layout, null);
        ContactViewHolder contactViewHolder = new ContactViewHolder(view);
        ButterKnife.bind(this,view);
        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        ContactVO contactVO = contactVOList.get(position);
//        holder.ivContactImage.set
        holder.setData(contactVO);


    }

    @Override
    public int getItemCount() {
        return contactVOList.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder{


        ImageView ivContactImage;
        TextView tvContactName;
        TextView tvPhoneNumber;
        ImageView ivEditable ;



        public ContactViewHolder(View itemView) {
            super(itemView);
            ivContactImage = (ImageView) itemView.findViewById(R.id.contact_image_iv);
            tvContactName = (TextView) itemView.findViewById(R.id.contact_name_tv);
            tvPhoneNumber = (TextView) itemView.findViewById(R.id.phone_number_tv);
            ivEditable =  (ImageView)itemView.findViewById(R.id.contact_edit_iv);
        }


        public void setData(ContactVO data){
            tvContactName.setText(data.getContactName());
            tvPhoneNumber.setText(data.getContactNumber());
            //Setting the bitmap.
            Bitmap bitmap = null ;
            Log.e("AK",""+data.getContactImage());
            Uri capturedImageUri = Uri.parse(data.getContactImage());
            try {
                bitmap = MediaStore.Images.Media.getBitmap(ivContactImage.getContext().getContentResolver(),capturedImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap compressedBitmap = ImageUtils.getInstant().getCompressedBitmap(bitmap);
            ivContactImage.setImageBitmap(compressedBitmap);
            ivEditable.setVisibility(View.VISIBLE);
        }
    }
}