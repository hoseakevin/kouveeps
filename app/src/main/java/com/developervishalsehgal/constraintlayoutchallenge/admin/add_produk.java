package com.developervishalsehgal.constraintlayoutchallenge.admin;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.developervishalsehgal.constraintlayoutchallenge.R;
import com.developervishalsehgal.constraintlayoutchallenge.helper.RealPathUtil;
import com.developervishalsehgal.constraintlayoutchallenge.helper.RestManager;
import com.developervishalsehgal.constraintlayoutchallenge.helper.apidata;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import info.hoang8f.widget.FButton;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class add_produk extends AppCompatActivity {
    EditText add_nama_barang, add_harga, add_stok;
    ImageView image_produk;
    FButton simpan_barang;
    private int STORAGE_PERMISSION_CODE = 23;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";
    private int PICK_IMAGE_REQUEST = 1;
    Bitmap bitmap;
    File resultingFile;
    static final int REQUEST_TAKE_PHOTO = 11111;
    private static final int PERMISSION_REQUEST_CODE = 1;
    Uri filePath;
    private RestManager restManager;
    apidata mApiService;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_produk);
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tambah Produk");

        add_nama_barang = findViewById(R.id.add_nama_barang);
        add_harga = findViewById(R.id.add_harga);
        add_stok = findViewById(R.id.add_stok);
        image_produk = findViewById(R.id.image_produk);
        simpan_barang = findViewById(R.id.simpan_produk);

        if(isReadStorageAllowed()){
            Toast.makeText(add_produk.this,"You already have the permission",Toast.LENGTH_LONG).show();

        }

        requestStoragePermission();

        if (ContextCompat.checkSelfPermission(add_produk.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            showAlert();

            if (getFromPref(add_produk.this, ALLOW_KEY)) {

            } else if (ContextCompat.checkSelfPermission(add_produk.this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                showAlert();
                if (ActivityCompat.shouldShowRequestPermissionRationale(add_produk.this,
                        Manifest.permission.CAMERA)) {
//                    showAlert();
                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(add_produk.this,
                            new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CAMERA);
                    showAlert();
                }
            }
        } else {
            //openCamera();
        }

        image_produk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        restManager = new RestManager();
        mApiService = restManager.ambil_data();
        simpan_barang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String backgroundImageName = String.valueOf(image_produk.getTag());
                final String image = getStringImage(bitmap);
                loading = ProgressDialog.show(add_produk.this, null, "Harap Tunggu...", true, false);
                mApiService.add_barang(add_nama_barang.getText().toString(),add_stok.getText().toString(),
                        add_harga.getText().toString(),image)
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    loading.dismiss();
                                    Toast.makeText(add_produk.this, "Berhasil simpan data", Toast.LENGTH_SHORT).show();
//                                    startActivity(new Intent(add_kegiatan.this, ContentFragment.class)
//                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));

                                } else {
                                    loading.dismiss();
                                    Toast.makeText(add_produk.this, response.message().toString(), Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                loading.dismiss();
                                Toast.makeText(add_produk.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    public void showFileChooser() {
        final CharSequence[] items = {"Ambil Foto","Pilih dari Galeri",
                "Batal"};

        AlertDialog.Builder builder = new AlertDialog.Builder(add_produk.this);
        builder.setTitle("Tambah Foto");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Ambil Foto")) {
                    Intent cameraIntent = new Intent(
                            android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(
                            cameraIntent,
                            REQUEST_TAKE_PHOTO);

                } else if (items[item].equals("Pilih dari Galeri")) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                } else if (items[item].equals("Batal")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private boolean isReadStorageAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(add_produk.this, Manifest.permission.READ_EXTERNAL_STORAGE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    //Requesting permission
    private void requestStoragePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(add_produk.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(add_produk.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                for (int i = 0, len = permissions.length; i < len; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean showRationale =
                                ActivityCompat.shouldShowRequestPermissionRationale
                                        (add_produk.this, permission);
                        if (showRationale) {
                            showAlert();
                        } else if (!showRationale) {
                            // user denied flagging NEVER ASK AGAIN
                            // you can either enable some fall back,
                            // disable features of your app
                            // or open another dialog explaining
                            // again the permission and directing to
                            // the app setting
                            saveToPreferences(add_produk.this, ALLOW_KEY, true);
                        }}}}}


        //Checking the request code of our request
        if(requestCode == STORAGE_PERMISSION_CODE){

            //If permission is granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //Displaying a toast
                Toast.makeText(add_produk.this,"Permission granted now you can read the storage",Toast.LENGTH_LONG).show();
            }else{
                //Displaying another toast if permission is not granted
                Toast.makeText(add_produk.this,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void showAlert(){
        AlertDialog alertDialog = new AlertDialog.Builder(add_produk.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        add_produk.this.finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ActivityCompat.requestPermissions(add_produk.this,
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);

                    }
                });
        alertDialog.show();
    }

    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    public static void saveToPreferences(Context context, String key,
                                         Boolean allowed) {
        SharedPreferences myPrefs = context.getSharedPreferences
                (CAMERA_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();
        prefsEditor.putBoolean(key, allowed);
        prefsEditor.commit();
    }

    public static Boolean getFromPref(Context context, String key) {
        SharedPreferences myPrefs = context.getSharedPreferences
                (CAMERA_PREF, Context.MODE_PRIVATE);
        return (myPrefs.getBoolean(key, false));
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            //Bitmap imageBitmap = (Bitmap) extras.get("data");
            //img.setImageBitmap(imageBitmap);
            bitmap = (Bitmap) extras.get("data");
            image_produk.setImageBitmap(bitmap);
            filePath = data.getData();
            simpan_barang.setEnabled(true);
            simpan_barang.setBackgroundColor(Color.BLUE);
        }
        else if (data != null) {
            String selectedImagePath;
            Uri selectedImageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                selectedImagePath = RealPathUtil.getPath(getApplicationContext(), selectedImageUri);
                Log.i("Image File Path", ""+selectedImagePath);

            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                image_produk.setImageBitmap(bitmap);
                simpan_barang.setEnabled(true);
                simpan_barang.setBackgroundColor(Color.RED);
            }

        } else {
            Toast.makeText(getApplicationContext(), "Try Again!!", Toast.LENGTH_SHORT).show();
            simpan_barang.setEnabled(false);
            simpan_barang.setBackgroundColor(Color.BLUE);
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        menu.findItem(R.id.load).setVisible(false);
//        menu.findItem(R.id.action_settings).setVisible(false);
        return true;
    }


}
