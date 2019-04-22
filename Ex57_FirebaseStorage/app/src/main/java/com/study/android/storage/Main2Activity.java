package com.study.android.storage;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {
    private static final String TAG = "lecture";

    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_ALBUM = 2;

    private ImageView imageView2;
    private Uri photoUri;
    private String currentPhotoPath;    //실제 사진 파일경로
    private String mImageCaptureName;   //이미지 이름

    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        imageView2 = findViewById(R.id.imageView2);
    }

    public void onButton1Clicked(View v) { takeAPicture(); }

    public void onButton2Clicked(View v) { selectFromAlbum(); }

    public void onButton3Clicked(View v) { imageUpload1(); }

    public void onButton4Clicked(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void takeAPicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try{
            photoFile = createImageFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        if(photoFile != null){
            photoUri = FileProvider.getUriForFile(
                    this,
                    getApplicationContext().getPackageName() + ".provider",
                    photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            startActivityForResult(intent, PICK_FROM_CAMERA);
        }
    }

    private File createImageFile() throws IOException{
        File dir = new File(Environment.getExternalStorageDirectory() + "/images/");
        if(!dir.exists()){
            dir.mkdirs();
        }
        String timeStamp = new SimpleDateFormat("YYYYMMdd_HHmmss").format(new Date());
        mImageCaptureName = timeStamp + ".png";

        File storageDir = new File(Environment.getExternalStorageDirectory()
                .getAbsoluteFile() + "/images/" + mImageCaptureName);

        currentPhotoPath = storageDir.getAbsolutePath();

        return storageDir;
    }

    private void selectFromAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            if(requestCode == PICK_FROM_ALBUM){
                //갤러리 에서 가져오기
                getPictureFromGallery(data.getData());
            }else if(requestCode == PICK_FROM_CAMERA){
                getPictureFromCamera();
            }
        }
    }

    private void getPictureFromCamera(){
        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
        ExifInterface exif = null;
        try{
            exif = new ExifInterface(currentPhotoPath);
        }catch (IOException e){
            e.printStackTrace();
        }
        int exifOrientation;
        int exifDegree;

        if(exif != null){
            exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            exifDegree = exifOrientationToDegrees(exifOrientation);
        }else{
            exifDegree = 0;
        }

        //이미지뷰에 비트맵 넣기

        imageView2.setImageBitmap(rotate(bitmap, exifDegree));
        Log.d(TAG, "카메라로 찍기:" + exifDegree);
        imageView2.invalidate();
    }

    private void getPictureFromGallery(Uri imgUri){

        String imagePath = getRealPathFromURI(imgUri);
        ExifInterface exif = null;
        try{
            exif = new ExifInterface(imagePath);
        }catch (IOException e){
            e.printStackTrace();
        }
        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL);
        int exifDegree = exifOrientationToDegrees(exifOrientation);

        //경로를 통해 비트맵으로 전환
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        if(bitmap != null){
            Log.d(TAG, "앨범에서가져오기 : "+exifDegree);
            //이미지뷰에 비트맵 넣기
            imageView2.setImageBitmap(bitmap);
            //imageView.setImageBitmap(rotate(bitmap, exifDegree));
            imageView2.invalidate();
        }else{
            Log.d(TAG, "앨범가져오기실패");
        }
    }

    // 사진의 회전값 가져오기
    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    // 사진을 정방향대로 회전하기
    private Bitmap rotate(Bitmap src, float degree) {
        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }

    // 사진의 절대경로 구하기
    private String getRealPathFromURI(Uri contentUri) {
        int column_index=0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        return cursor.getString(column_index);
    }

    private void imageUpload1() {
        // [upload_memory]
        // Get the data from an ImageView as bytes
        imageView2.setDrawingCacheEnabled(true);
        imageView2.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView2.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        // Call requires API level 26 (current min is 19):
        //Instant start = Instant.now();
        //long nTime = start.getEpochSecond();

        long time = System.currentTimeMillis();
        String folderName = "upload";
        String imageName = String.format("%d.png", time);

        // Storage 이미지 업로드 경로
        String storagePath = folderName + "/" + imageName;

        StorageReference imageRef = mStorageRef.child(storagePath);

        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), "Fail !!", Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                Toast.makeText(getApplicationContext(), "Success !!", Toast.LENGTH_LONG).show();
            }
        });
    }

}