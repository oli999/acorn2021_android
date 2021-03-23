package com.example.step24fileupload;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity
                            implements View.OnClickListener{
    //필드
    ImageView imageView;
    //이미지가 위치한 절대경로
    String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //버튼의 참조값 얻어와서 리스너 등록
        Button takePicBtn=findViewById(R.id.takePicBtn);
        Button uploadBtn=findViewById(R.id.uploadBtn);
        takePicBtn.setOnClickListener(this);
        uploadBtn.setOnClickListener(this);
        //사진을 출력할 ImageView 의 참조값 얻어와서 필드에 저장
        imageView=findViewById(R.id.imageView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.takePicBtn:
                //외부 저장장치에 접근할 권한이 켜져 있는지여부를 상수값으로 얻어낸다
                int permissionCheck=
                        ContextCompat.checkSelfPermission(this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if(permissionCheck != PackageManager.PERMISSION_GRANTED){ //권한이 없다면
                    String[] permissions={Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    ActivityCompat.requestPermissions(this, permissions,
                            0);
                }else{//권한이 있다면
                    requestTakePic();
                }
                break;
            case R.id.uploadBtn:

                break;
        }
    }
    //권한 요청 결과를 받을 메소드
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 0:
                //만일 권한을 승인했다면
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    requestTakePic();
                }else{//승인하지 않았다면
                    Toast.makeText(this, "퍼미션이 필요합니다.",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //사진을 찍겠다는 요청 메소드
    public void requestTakePic(){
        //사진을 찍겠다는 인텐트 객체 생성하기
        Intent intent=new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        //해당 인텐트를 받아줄 app 이 있는지 확인해서(카메라 app 이 있는지 여부)
        if(intent.resolveActivity(getPackageManager()) != null){
            //찍은 사진을 저장할 파일 객체
            File photoFile=null;
            try{
                //파일명 지정
                String timeStamp=new SimpleDateFormat("yyyyMMdd_HHmmss")
                        .format(new Date());
                //파일을 저장할 절대 경로
                String path=getExternalFilesDir(null).getAbsolutePath();
                //경로와 파일명을 이용해서 File 객체 생성
                photoFile=new File(path+"/"+timeStamp+".jpg");
                //사진을 어디에 저장할지 정보를 가지고 있는 Uri 객체
                Uri uri= FileProvider.getUriForFile(this,
                        "com.example.step24fileupload.fileprovider",
                        photoFile);
                //로그에 실제 경로 출력해보기
                Log.e("uri", uri.getPath());
                //인텐트에 Uri 객체를 담고
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                //카메라 앱을 찾아서 실행한다.
                startActivityForResult(intent, 999);
                //이미지경로를 필드에 저장한다.
                imagePath=photoFile.getAbsolutePath();
            }catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }else{//없으면
            Toast.makeText(this, "카메라 app 을 설치해 주세요",
                    Toast.LENGTH_SHORT).show();
        }
    }
    // startActivityForResult() 메소드를 호출한 결과값이 들어오는 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){ //작업이 성공적으로 수행이 되었다면
            switch (requestCode){//요청의 코드값으로 분기한다.
                case 999:
                    //실행순서가 여기까지 들어온다면 사진은 성공적으로 우리가 원하는 위치에 저장이됨
                    //저장된 이미지를 ImageView 에 출력하기
                    fitToImageView(imageView, imagePath);
                    break;
            }
        }else{
            Toast.makeText(this,"작업 실패!", Toast.LENGTH_SHORT).show();
        }
    }

    //이미지 뷰의 크기에 맞게 이미지를 출력하는 메소드
    public static void fitToImageView(ImageView imageView, String absolutePath){
        //출력할 이미지 뷰의 크기를 얻어온다.
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(absolutePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(absolutePath, bmOptions);
        /* 사진이 세로로 촬영했을때 회전하지 않도록 */
        try {
            ExifInterface ei = new ExifInterface(absolutePath);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            switch(orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    bitmap = rotateImage(bitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    bitmap = rotateImage(bitmap, 180);
                    break;
                // etc.
            }
        }catch(IOException ie){
            Log.e("####", ie.getMessage());
        }

        imageView.setImageBitmap(bitmap);
    }
    //Bitmap 이미지 회전시켜서 리턴하는 메소드
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap retVal;

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

        return retVal;
    }
}







