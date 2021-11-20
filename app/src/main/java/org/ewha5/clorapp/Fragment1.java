package org.ewha5.clorapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Matrix;
import android.graphics.Picture;
import android.media.ExifInterface;
import android.os.Environment;
import android.os.Handler;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class Fragment1 extends AppCompatActivity {
    private static final String TAG = "Fragment1";

    Context context;
    ImageView pictureImageView;
    boolean isPhotoCaptured;

    Uri uri;
    File file;
    Bitmap resultPhotoBitmap;

    //추가
    Handler handler = new Handler();

    ProgressDialog dialog;
    String picturePath;

    private File tempFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment1);

        //사진 미리보기
        pictureImageView = findViewById(R.id.pictureImageView);

        //사진 촬영 버튼
        Button addButton1 = findViewById(R.id.addButton1);
        addButton1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                showPhotoCaptureActivity();
            }
        });

        //갤러리에서 선택 버튼
        Button addButton2 = (Button) findViewById(R.id.addButton2);
        addButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPhotoSelectionActivity();
            }
        });

        //저장 버튼 - 서버와 다음 단계로 연결
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //웹 서버로 연결
                request();

                //프로그레스 바 - 서버 후 수정 필요
                dialog = new ProgressDialog(Fragment1.this);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("서버에서 정보를 받아오는 중입니다.");
                dialog.setMax((int) 50000);
                dialog.setProgress((int) 50000);
                dialog.show();

                //받아오고 나면,,
                showMessage();

            }

        });
    }

    //서버로 연결
    private void request() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
            }
            //서버 연결
        }, 5000);
    }

    //서버 연결 후, 다음 화면 넘어가기 위한 메시지
    private void showMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("안내");
        builder.setMessage("조합을 확인할까요?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                //결과 보여주기 - ShowResult class로 연결
                Intent intent = new Intent(getApplicationContext(), ShowResult.class);

                //사진 경로 함께 보내기
                intent.putExtra("path", picturePath);
                //사진 경로 확인용 메시지
                Toast.makeText(getApplicationContext(), picturePath+" 파일 경로",
                        Toast.LENGTH_SHORT).show();

                startActivityForResult(intent, AppConstants.REQ_SHOW_COMBINATION);
            }
        });
        /*
        builder.setNeutralButton("취소", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        */
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //설정하기
            }
        });
        AlertDialog dialog = builder.create();

        dialog.show();
    }

    /*
    사진 촬영
     */

    public void showPhotoCaptureActivity() {

        try {
            file = createFile();
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch(Exception e) {
            e.printStackTrace();
        }

        if(Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, file);
        } else {
            uri = Uri.fromFile(file);
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

        startActivityForResult(intent, AppConstants.REQ_PHOTO_CAPTURE);
    }

    //파일 경로 수정
    /*
    private File createFile() {
        String filename = createFilename();
        //File outFile = new File(getFilesDir(), filename);
        File outFile = new File("/sdcard/Android/data/org.ewha5.clorapp/files", filename);
        Log.d("Main", "File path : " + outFile.getAbsolutePath());

        return outFile;
    }
     */

    private File createFile() {
        String filename = createFilename();
        File outFile = new File(getExternalFilesDir(null), filename);
        picturePath = outFile.toString();
        Log.d("Main", "File path : " + outFile.getAbsolutePath());
        return outFile;
    }

    //사진 촬영 끝...

    //
    public void showPhotoSelectionActivity() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, AppConstants.REQ_PHOTO_SELECTION);
    }
    //

    /**
     * 다른 액티비티로부터의 응답 처리
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode,  @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (intent != null) {
            switch (requestCode) {
                case AppConstants.REQ_PHOTO_CAPTURE:  // 사진 찍는 경우
                    Log.d(TAG, "onActivityResult() for REQ_PHOTO_CAPTURE.");
                    Log.d(TAG, "resultCode : " + resultCode);


                    //setPicture(file.getAbsolutePath(), 8);
                    resultPhotoBitmap = decodeSampledBitmapFromResource(file, pictureImageView.getWidth(), pictureImageView.getHeight());
                    pictureImageView.setImageBitmap(resultPhotoBitmap);

                    break;

                case AppConstants.REQ_PHOTO_SELECTION:  // 사진을 앨범에서 선택하는 경우
                    Log.d(TAG, "onActivityResult() for REQ_PHOTO_SELECTION.");

                    Uri fileUri = intent.getData();


                    Log.d(TAG, "fileUri : " + fileUri);

                    /*
                    ContentResolver resolver = context.getContentResolver();

                    try {
                        InputStream instream = resolver.openInputStream(fileUri);
                        resultPhotoBitmap = BitmapFactory.decodeStream(instream);
                        pictureImageView.setImageBitmap(resultPhotoBitmap);

                        instream.close();

                        isPhotoCaptured = true;
                    } catch(Exception e) {
                        e.printStackTrace();
                    }

                     */

                    /*
                    Cursor cursor = null;

                    try{
                        String[] proj = { MediaStore.Images.Media.DATA };
                        assert fileUri != null;
                        cursor = getContentResolver().query(fileUri, proj, null, null, null);

                        assert cursor != null;
                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        tempFile = new File(cursor.getString(column_index));

                    }finally {
                        if (cursor != null) {
                            cursor.close();
                        }
                    }

                    setImage();

                     */

                    if (intent != null && resultCode == RESULT_OK ) {


                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(fileUri, filePathColumn, null, null, null);

                        if(cursor == null || cursor.getCount() <1){
                            return;
                        }

                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                        if(columnIndex < 0)
                            return;

                        picturePath = cursor.getString(columnIndex);
                        tempFile = new File(picturePath);


                        cursor.close();

                        setImage();
                    }

                    break;

            }
        }
    }
    //onActivitybyresult 끝.

    private void setImage() {

        ImageView imageView = findViewById(R.id.pictureImageView);

        /*
        ExifInterface exif = null;
        int exifOrientation;
        int exifDegree;
        if (exif != null) {
            exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            exifDegree = exifOrientationToDegrees(exifOrientation);
        } else { exifDegree = 0; }
        imageView.setImageBitmap(rotate(originalBm,exifDegree));
         */


        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);

        imageView.setImageBitmap(originalBm);

    }

    public static Bitmap decodeSampledBitmapFromResource(File res, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(res.getAbsolutePath(),options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(res.getAbsolutePath(),options);
    }


    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height;
            final int halfWidth = width;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    private String createFilename() {
        Date curDate = new Date();
        String curDateStr = String.valueOf(curDate.getTime()) + ".jpg"; //확장자로 저장
        return curDateStr;
    }


    /*
    public String savePicture() {
        if (resultPhotoBitmap == null) {
            AppConstants.println("No picture to be saved.");
            return "";
        }

        File photoFolder = new File(AppConstants.FOLDER_PHOTO);

        if(!photoFolder.isDirectory()) {
            Log.d(TAG, "creating photo folder : " + photoFolder);
            photoFolder.mkdirs();
        }

        String photoFilename = createFilename();
        String picturePath = photoFolder + File.separator + photoFilename;

        try {
            FileOutputStream outstream = new FileOutputStream(picturePath);
            resultPhotoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outstream);
            outstream.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return picturePath;
    }
     */

    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        } return 0;

    }

    // 이미지를 회전시키는 메서드 선언
    private Bitmap rotate(Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }




}