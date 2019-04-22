package com.study.android.ex43_telephonyex;


import android.content.ContentUris;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.InputStream;

public class ContactsListActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    AddressAdapter adapter;
    ListView mContactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        adapter = new AddressAdapter(this);

        mContactList = findViewById(R.id.contactsList);
        mContactList.setAdapter(adapter);
        mContactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                AddressItem item = (AddressItem) adapter.getItem(position);
                Toast.makeText(getApplicationContext(),
                        "selected: " + item.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        populateContactList();
    }
    // 주소록을 현재 선택된 계정으로 채운다.
    private void populateContactList() {
        // 연락처를 생성한다.
        Cursor cursor = getContacts();

        // 연락처를 하나씩 처리한다.
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts._ID));

            String name = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            // 연락처의 전화번호 데이터를 구한다.
            String telNum = retrieveContactNumber(id);
            Bitmap photo = retrieveContactPhoto(id);

            AddressItem item1 = new AddressItem(name, telNum, photo);
            adapter.addItem(item1);
        }
    }

    // 현재 선택된 계정의 전체 주소록 리스트를 구한다.
    private Cursor getContacts()
    {
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
        };
        return managedQuery(uri, projection, null, null, null);
    }

    // 아이디가 contactId인 연락처의 전화번호만을 구한다.
    private String retrieveContactNumber(String contactId) {

        Cursor cursor1 = getContentResolver()
                .query(ContactsContract.Data.CONTENT_URI,
                        new String[] { ContactsContract.Data._ID, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.TYPE,
                                ContactsContract.CommonDataKinds.Phone.LABEL },
                        ContactsContract.Data.CONTACT_ID + "=?" + " AND " + ContactsContract.Data.MIMETYPE + "='"
                                + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "'",
                        new String[] { contactId }, null);;

        String telNum = "";
        if (cursor1.moveToFirst()) {
            telNum = cursor1.getString(cursor1
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }
//        while (cursor1.moveToNext()) {
//            telNum = cursor1.getString(cursor1
//                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//        }
        cursor1.close();

        return telNum;
    }

    private Bitmap retrieveContactPhoto(String contactId) {

        Bitmap photo = null;

        try {
            // Get photo of contactId as input stream:
            InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream (
                    getContentResolver(),
                    ContentUris.withAppendedId(
                            ContactsContract.Contacts.CONTENT_URI,
                            new Long(contactId)));

            if (inputStream != null) {
                photo = BitmapFactory.decodeStream(inputStream);
            }

            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return photo;
    }
}