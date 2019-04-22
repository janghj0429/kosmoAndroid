package com.study.android.ex35_camera1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "lecture";

    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_ALBUM = 2;
    private static final int CROP_FROM_CAMERA = 3;

    private String[] permissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    private static final int MULTIPLE_PERMISSIONS = 101;

    private ImageView imageView;
    private Uri photoUri;
    private String currentPhotoPath;    //실제 사진 파일경로
    private String mImageCaptureName;   //이미지 이름

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);

        checkPermissions();
    }

    private boolean checkPermissions(){
        int result;
        List<String> permissionList = new ArrayList<>();
        for(String pm:permissions){
            result = ContextCompat.checkSelfPermission(this, pm);
            if(result != PackageManager.PERMISSION_GRANTED){
                permissionList.add(pm);
            }
        }
        if(!permissionList.isEmpty()){
            ActivityCompat.requestPermissions(this,
                    permissionList.toArray(new String[permissionList.size()]),
                    MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, String permissions[], int[] grantResults){

        switch (requestCode){
            case MULTIPLE_PERMISSIONS: {
                if(grantResults.length > 0){
                    for(int i=0; i<permissions.length; i++){
                        if(permissions[i].equals(this.permissions[0])){
                            if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                                showNoPermissionToastAndFinish();
                            }
                        }else if(permissions[i].equals(this.permissions[1])){
                            if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                                showNoPermissionToastAndFinish();
                            }
                        }else if(permissions[i].equals(this.permissions[2])){
                            if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                                showNoPermissionToastAndFinish();
                            }
                        }
                    }
                }else{
                    showNoPermissionToastAndFinish();
                }
                Log.d(TAG, "권한 통과");
                return;
            }
        }
    }

    private void showNoPermissionToastAndFinish(){
        Toast.makeText(this, "권한 요청에 동의 해주셔야 이용 가능합니다.",
                Toast.LENGTH_SHORT).show();
        finish();
    }

    public void onCameraClicked(View v){
        takeAPicture();
    }

    public void onAlbumClicked(View v){
        selectFromAlbum();
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
    //////////////////////////////////////////////////////////////////
    //사용 할 때는 이미지 폴더와 파일이름정도만 변경해서 사용
    ///////////////////////////////////////////////////////////////////////
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

        imageView.setImageBitmap(rotate(bitmap, exifDegree));
        Log.d(TAG, "카메라로 찍기:" + exifDegree);
        imageView.invalidate();
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
            imageView.setImageBitmap(bitmap);
            //imageView.setImageBitmap(rotate(bitmap, exifDegree));
            imageView.invalidate();
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

}
