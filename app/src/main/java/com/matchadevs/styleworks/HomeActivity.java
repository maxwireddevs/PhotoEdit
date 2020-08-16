package com.matchadevs.styleworks;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class HomeActivity extends Fragment {
    private static final String TAG = "HomeActivity.java";
    private ImageView gallery,templates,create,studio,menu;
    private int IMAGE_SEARCH_CODE=123;
    private Bitmap bitmap;
    private ArrayList<String> sizelist = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        sizelist.add("Instagram 1:1");
        sizelist.add("Instagram 4:5");
        sizelist.add("Instagram Story");
        sizelist.add("5:4");
        sizelist.add("3:4");
        sizelist.add("4:3");
        sizelist.add("Facebook Post");
        sizelist.add("Facebook Cover");
        sizelist.add("Pinterest Post");
        sizelist.add("3:2");
        sizelist.add("9:16");
        sizelist.add("16:9");
        sizelist.add("1:2");
        sizelist.add("YouTube Cover");
        sizelist.add("Twitter Post");
        sizelist.add("Twitter Header");
        sizelist.add("A4");
        sizelist.add("A5");

        menu=view.findViewById(R.id.menubutton);
        gallery= view.findViewById(R.id.gallerybutton);
        templates= view.findViewById(R.id.templatebutton);
        create= view.findViewById(R.id.createbutton);
        studio= view.findViewById(R.id.studiobutton);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearchForImage();
            }
        });
        templates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this.getContext());
                builder.setTitle("Add text");
                builder.setCancelable(true);
                LayoutInflater inflater = getLayoutInflater();
                View choosesizeview= inflater.inflate(R.layout.picksize_dialog, null);
                builder.setView(choosesizeview);
                ListView cropsizelistview = choosesizeview.findViewById(R.id.sizelist);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(HomeActivity.this.getContext(), android.R.layout.simple_list_item_1, sizelist);
                cropsizelistview.setAdapter(adapter);
                cropsizelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch(position){
                            case 0:
                                builder.show().cancel();
                                toCreate(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(HomeActivity.this.getResources(), R.drawable.canvas), 100, 100, false));
                                break;
                            case 1:
                                builder.show().cancel();
                                toCreate(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(HomeActivity.this.getResources(), R.drawable.canvas), 120, 150, false));
                                break;
                            case 2:
                                builder.show().cancel();
                                toCreate(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(HomeActivity.this.getResources(), R.drawable.canvas), 90, 160, false));
                                break;
                            case 3:
                                builder.show().cancel();
                                toCreate(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(HomeActivity.this.getResources(), R.drawable.canvas), 100, 80, false));
                                break;
                            case 4:
                                builder.show().cancel();
                                toCreate(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(HomeActivity.this.getResources(), R.drawable.canvas), 90, 120, false));
                                break;
                            case 5:
                                builder.show().cancel();
                                toCreate(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(HomeActivity.this.getResources(), R.drawable.canvas), 120, 90, false));
                                break;
                            case 6:
                                builder.show().cancel();
                                toCreate(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(HomeActivity.this.getResources(), R.drawable.canvas), 120, 63, false));
                                break;
                            case 7:
                                builder.show().cancel();
                                toCreate(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(HomeActivity.this.getResources(), R.drawable.canvas), 820, 312, false));
                                break;
                            case 8:
                                builder.show().cancel();
                                toCreate(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(HomeActivity.this.getResources(), R.drawable.canvas), 80, 120, false));
                                break;
                            case 9:
                                builder.show().cancel();
                                toCreate(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(HomeActivity.this.getResources(), R.drawable.canvas), 120, 80, false));
                                break;
                            case 10:
                                builder.show().cancel();
                                toCreate(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(HomeActivity.this.getResources(), R.drawable.canvas), 90, 160, false));
                                break;
                            case 11:
                                builder.show().cancel();
                                toCreate(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(HomeActivity.this.getResources(), R.drawable.canvas), 160, 90, false));
                                break;
                            case 12:
                                builder.show().cancel();
                                toCreate(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(HomeActivity.this.getResources(), R.drawable.canvas), 100, 200, false));
                                break;
                            case 13:
                                builder.show().cancel();
                                toCreate(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(HomeActivity.this.getResources(), R.drawable.canvas), 128, 64, false));
                                break;
                            case 14:
                                builder.show().cancel();
                                toCreate(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(HomeActivity.this.getResources(), R.drawable.canvas), 150, 50, false));
                                break;
                            case 15:
                                builder.show().cancel();
                                toCreate(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(HomeActivity.this.getResources(), R.drawable.canvas), 210, 297, false));
                                break;
                            case 16:
                                builder.show().cancel();
                                toCreate(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(HomeActivity.this.getResources(), R.drawable.canvas), 148, 210, false));
                                break;
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
        studio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toStudio();
            }
        });

        return view;
    }

    private void performFileSearchForImage() {
        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_SEARCH_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {
        // Check which request it is that we're responding to
        super.onActivityResult(requestCode, resultCode, resultIntent);
        if (requestCode == IMAGE_SEARCH_CODE) {
            // Make sure the request was successful
            if (resultCode == getActivity().RESULT_OK) {
                // Get the URI that points to the selected csv
                Uri imageUri = resultIntent.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                    toCreate(bitmap);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "The specified file was not found", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void toCreate(Bitmap bitmap){
        Log.v(TAG, "File received");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        long currSize = stream.toByteArray().length;

        if(currSize<=250000){
            Log.v(TAG, "1st Route");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
            byte[] byteArray = stream.toByteArray();
            Bundle bundle=new Bundle();
            bundle.putByteArray("ByteArray",byteArray);
            Fragment create=new Create();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            create.setArguments(bundle);
            transaction.replace(R.id.nav_host_fragment, create);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if(250000<currSize && currSize<=500000){
            Log.v(TAG, "2nd Route");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, stream);
            byte[] byteArray = stream.toByteArray();
            Bundle bundle=new Bundle();
            bundle.putByteArray("ByteArray",byteArray);
            Fragment create=new Create();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            create.setArguments(bundle);
            transaction.replace(R.id.nav_host_fragment, create);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if(500000<currSize && currSize<=1000000){
            Log.v(TAG, "3rd Route");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
            byte[] byteArray = stream.toByteArray();
            Bundle bundle=new Bundle();
            bundle.putByteArray("ByteArray",byteArray);
            Fragment create=new Create();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            create.setArguments(bundle);
            transaction.replace(R.id.nav_host_fragment, create);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else{
            Log.v(TAG, "Final Route");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            byte[] byteArray = stream.toByteArray();
            Bundle bundle=new Bundle();
            bundle.putByteArray("ByteArray",byteArray);
            Fragment create=new Create();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            create.setArguments(bundle);
            transaction.replace(R.id.nav_host_fragment, create);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
    public void toStudio(){
        Fragment studio=new Studio();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, studio);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
