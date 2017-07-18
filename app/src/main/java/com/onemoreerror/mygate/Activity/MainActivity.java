package com.onemoreerror.mygate.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.onemoreerror.mygate.Adapter.AllContactsAdapter;
import com.onemoreerror.mygate.Constants.AppConstants;
import com.onemoreerror.mygate.ContactsModel.ContactVO;
import com.onemoreerror.mygate.R;
import com.tbruyelle.rxpermissions.RxPermissions;
//import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_all_contacts_rv)
    RecyclerView rvContacts;
    List<ContactVO> contactVOList ;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getIsPermitted();
    }
    public void getIsPermitted(){
        final boolean isPermited = false;
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.READ_CONTACTS)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M

                        new AsyncTask<Void, Void, Void>() {

                            @Override
                            protected void onPreExecute()
                            {
                                pd = ProgressDialog.show(MainActivity.this,
                                        "Loading..", "Please Wait", true, false);
                            }// End of onPreExecute method

                            @Override
                            protected Void doInBackground(Void... params)
                            {
                                    getAllContacts();
                                return null;
                            }// End of doInBackground method

                            @Override
                            protected void onPostExecute(Void result)
                            {
                                pd.dismiss();
                                AllContactsAdapter contactAdapter = new AllContactsAdapter(contactVOList, getApplicationContext());
                                rvContacts.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                                rvContacts.setAdapter(contactAdapter);
                                contactAdapter.notifyDataSetChanged();

                            }//End of onPostExecute method
                        }.execute((Void[]) null);
                    } else {
                        Toast.makeText(MainActivity.this, R.string.permission_required, Toast.LENGTH_LONG);
                    }
                });
    }
    private void getAllContacts() {
        contactVOList = new ArrayList();
        ContactVO contactVO;
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    contactVO = new ContactVO();
                    contactVO.setContactName(name);
                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id},
                            null);
                    if (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contactVO.setContactNumber(phoneNumber);
                    }
                    phoneCursor.close();
                    Cursor emailCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (emailCursor.moveToNext()) {
                        String emailId = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    }
                    contactVOList.add(contactVO);
                }
            }
        }
    }
    @OnClick(R.id.main_add_fab)
    public void fabPressed(){
        //open add new contact Activity
        Intent intent = new Intent(this,AddNewContactActivity.class);
        startActivityForResult(intent, AppConstants.REQUEST_TAGS.ADD_NEW_CONTACT_TAG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppConstants.REQUEST_TAGS.ADD_NEW_CONTACT_TAG ){
            if(resultCode == Activity.RESULT_OK){
                //In Case of no Contacts .
                AllContactsAdapter contactAdapter = new AllContactsAdapter(contactVOList, getApplicationContext());
                rvContacts.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                rvContacts.setAdapter(contactAdapter);
                ContactVO obj = data.getParcelableExtra(AppConstants.DATA_PASSING_TAGS.SEND_CONTACT_OBJ);
                if(obj != null){
                    Log.i("AK","Object Delievered !");
//                    Log.e("AK",obj.getContactName());
//                    Log.e("AK",obj.getContactNumber());
                    contactVOList.add(0,obj);
                    if(rvContacts != null) {
                        rvContacts.getAdapter().notifyDataSetChanged();
                    }
                }
                else {
                    Log.e("AK", "Result Object is null");
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                Toast.makeText(this,"Can't Pass the Object between Activities. ",Toast.LENGTH_LONG).show();
            }
        }
    }
}
