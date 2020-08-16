package com.matchadevs.styleworks;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import com.matchadevs.styleworks.database.itemHelper;
import com.matchadevs.styleworks.utils.ImageAdapter;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Studio extends Fragment {
    private static final String TAG = "HomeActivity.java";
    private ArrayList<byte[]> itemList=new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.studio_fragment, container, false);

        itemHelper ih=new itemHelper(Studio.this.getContext());
        Cursor res=ih.getAllData();
        while(res.moveToNext()){
            itemList.add(res.getBlob(1));
        }

        GridView gridview = (GridView) view.findViewById(R.id.studiogrid);
        gridview.setAdapter(new ImageAdapter(Studio.this.getContext()));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                toCreate(BitmapFactory.decodeByteArray(itemList.get(position),0,itemList.get(position).length));
            }
        });

        return view;
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
}
