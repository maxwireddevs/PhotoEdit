package com.matchadevs.styleworks;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ja.burhanrashid52.photoeditor.CustomEffect;
import ja.burhanrashid52.photoeditor.OnPhotoEditorListener;
import ja.burhanrashid52.photoeditor.OnSaveBitmap;
import ja.burhanrashid52.photoeditor.PhotoFilter;
import jp.wasabeef.blurry.Blurry;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.media.effect.EffectFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.matchadevs.styleworks.RecyclerView.AdjustRecyclerViewAdapter;
import com.matchadevs.styleworks.RecyclerView.CropRecyclerViewAdapter;
import com.matchadevs.styleworks.RecyclerView.CropRecyclerViewHolder;
import com.matchadevs.styleworks.RecyclerView.RecyclerViewAdapter;
import com.matchadevs.styleworks.RecyclerView.RecyclerViewItemClickListener;
import com.matchadevs.styleworks.RecyclerView.StickerListRecyclerViewAdapter;
import com.matchadevs.styleworks.RecyclerView.StickerRecyclerViewAdapter;
import com.matchadevs.styleworks.database.itemHelper;
import com.matchadevs.styleworks.utils.BrushDrawingView;
import com.matchadevs.styleworks.utils.ColorFilterGenerator;
import com.matchadevs.styleworks.utils.FilterItem;
import com.matchadevs.styleworks.utils.FilterPack;
import com.matchadevs.styleworks.utils.ImageFilterView;
import com.matchadevs.styleworks.utils.MultiTouchListener;
import com.matchadevs.styleworks.utils.StickerList;
import com.matchadevs.styleworks.utils.Stickers;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.zomato.photofilters.imageprocessors.Filter;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
import static android.app.AlertDialog.THEME_DEVICE_DEFAULT_DARK;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class Create extends Fragment {
    static
    {
        System.loadLibrary("NativeImageProcessor");
    }
    private static final String TAG = "Create.java";
    private int photoheight;
    private int photowidth;
    private int adapterFont;
    private int brightnesslevel=50;
    private int saturationlevel=50;
    private int huelevel=0;
    private int filterlevel;
    private int filterlevel2;
    private int fontID=R.font.kreon_regular;
    private int textSize;
    private int textColour=0;
    private int textOpacity;
    private ImageView photo;
    private Button okbutton, cancelbutton, applybutton;
    private Bitmap originalbitmap;
    private Bitmap rootbitmap;
    private Bitmap bitmap;
    private Bitmap tempbitmap;
    private ImageView colourpreviewbrush;
    private RelativeLayout additionalblurtoolbar;
    private RelativeLayout stickertoolbar;
    private RelativeLayout filtertoolbar;
    private RelativeLayout colortoolbar;
    private ConstraintLayout toolstoolbar;
    private ConstraintLayout addonstoolbar;
    private RelativeLayout drawtoolbar;
    private RelativeLayout adjusttoolbar;
    private RelativeLayout effectstoolbar;
    private RelativeLayout savebutton;
    private RelativeLayout backbutton;
    private RelativeLayout measurelayout;
    private RelativeLayout rl;
    private OnPhotoEditorListener mOnPhotoEditorListener;
    private RelativeLayout stickersbutton, textbutton, drawbutton;
    private ImageView colorbutton, framebutton, revertbutton, adjustbutton, brushbutton, eraserbutton;
    private RecyclerView itemrecyclerview, stickerrecyclerview, stickerpackrecyclerview, adjustrecyclerview, effectsrecyclerview, croprecyclerview;
    private ArrayList<FilterItem> filterlist = new ArrayList<>();
    private ArrayList<Stickers> stickerlist = new ArrayList<>();
    private ArrayList<FilterItem> adjustlist = new ArrayList<>();
    private ArrayList<FilterItem> effectslist = new ArrayList<>();
    private ArrayList<FilterItem> croplist = new ArrayList<>();
    private ArrayList<String> stickerpacklist = new ArrayList<>();
    private SeekBar hue, saturation, brightness,  adjustseekbar, additionalseekbar, brushsizeseekbar;
    private int currentstickerpack=0;
    private CropImageView cropImageView;
    private SeekBar rotateseekbar;
    private BottomNavigationView bottomNavigation;
    private ImageFilterView mImageFilterView;
    private BrushDrawingView mBrushDrawingView;
    private TextView brushsize;
    private TextView adjusttext;
    private boolean isEraser=false;
    private Filter myFilter;
    private int brushcolour;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = inflater.inflate(R.layout.activity_create, container, false);
        rl = (RelativeLayout) view.findViewById(R.id.rootLayout);
        measurelayout = (RelativeLayout) view.findViewById(R.id.measurelayout) ;
        savebutton = (RelativeLayout) view.findViewById(R.id.reviewbutton);
        applybutton = (Button) view.findViewById(R.id.applybutton);
        additionalseekbar = (SeekBar) view.findViewById(R.id.additionalseekbar);
        adjustseekbar = (SeekBar) view.findViewById(R.id.adjustseekbar);
        mImageFilterView = (ImageFilterView) view.findViewById(R.id.mImageFilterView);
        okbutton = (Button)view.findViewById(R.id.okbutton);
        cancelbutton = (Button)view.findViewById(R.id.cancelbutton);
        adjustbutton = (ImageView) view.findViewById(R.id.adjustbutton);
        backbutton = (RelativeLayout) view.findViewById(R.id.backbutton);
        adjusttoolbar = (RelativeLayout) view.findViewById(R.id.adjusttoolbar);
        addonstoolbar = (ConstraintLayout) view.findViewById(R.id.addonstoolbar);
        colortoolbar = (RelativeLayout) view.findViewById(R.id.colortoolbar);
        filtertoolbar = (RelativeLayout) view.findViewById(R.id.filtertoolbar);
        drawtoolbar = (RelativeLayout)view.findViewById(R.id.drawtoolbar);
        toolstoolbar=(ConstraintLayout)view.findViewById(R.id.whitetoolbar);
        stickertoolbar = (RelativeLayout)view.findViewById(R.id.stickertoolbar);
        hue = (SeekBar) view.findViewById(R.id.hueseekbar);
        saturation = (SeekBar) view.findViewById(R.id.saturationseekbar);
        brightness = (SeekBar) view.findViewById(R.id.brightnessseekbar);
        stickersbutton = (RelativeLayout) view.findViewById(R.id.stickerbutton);
        textbutton = (RelativeLayout) view.findViewById(R.id.textbutton);
        drawbutton = (RelativeLayout) view.findViewById(R.id.drawbutton);
        colorbutton = (ImageView) view.findViewById(R.id.colorbutton);
        framebutton = (ImageView) view.findViewById(R.id.framebutton);
        revertbutton = (ImageView) view.findViewById(R.id.revertoriginalbutton);
        cropImageView = (CropImageView) view.findViewById(R.id.cropImageView);
        rotateseekbar= (SeekBar) view.findViewById(R.id.rotateseekbar);
        effectstoolbar = (RelativeLayout) view.findViewById(R.id.effectstoolbar);
        effectsrecyclerview = (RecyclerView) view.findViewById(R.id.effectslist);
        brushbutton = (ImageView) view.findViewById(R.id.brushbutton);
        eraserbutton = (ImageView) view.findViewById(R.id.eraserbutton);
        brushsizeseekbar = (SeekBar) view.findViewById(R.id.brushsizeseekbar);
        mBrushDrawingView = (BrushDrawingView) view.findViewById(R.id.mBrushDrawingView);
        colourpreviewbrush = (ImageView) view.findViewById(R.id.colourpreviewbrush);
        brushsize = (TextView) view.findViewById(R.id.brushsize);
        adjusttext = (TextView) view.findViewById(R.id.adjusttext);
        additionalblurtoolbar = (RelativeLayout) view.findViewById(R.id.additionalblurtoolbar);
        photo = (ImageView) view.findViewById(R.id.photo);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            byte[] byteArray = bundle.getByteArray("ByteArray");
            bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            rootbitmap =bitmap;
            originalbitmap=bitmap;
            photo.setImageBitmap(bitmap);
        }

        filterlist.add(new FilterItem("None", R.drawable.ic_image_black_24dp));
        filterlist.add(new FilterItem("Struck", R.drawable.ic_image_black_24dp));
        filterlist.add(new FilterItem("Clarendon", R.drawable.ic_image_black_24dp));
        filterlist.add(new FilterItem("OldMan", R.drawable.ic_image_black_24dp));
        filterlist.add(new FilterItem("Mars", R.drawable.ic_image_black_24dp));
        filterlist.add(new FilterItem("Rise", R.drawable.ic_image_black_24dp));
        filterlist.add(new FilterItem("April", R.drawable.ic_image_black_24dp));
        filterlist.add(new FilterItem("Amazon", R.drawable.ic_image_black_24dp));
        filterlist.add(new FilterItem("Starlit", R.drawable.ic_image_black_24dp));
        filterlist.add(new FilterItem("Whisper", R.drawable.ic_image_black_24dp));
        filterlist.add(new FilterItem("Lime", R.drawable.ic_image_black_24dp));
        filterlist.add(new FilterItem("Haan", R.drawable.ic_image_black_24dp));
        filterlist.add(new FilterItem("Bluemess", R.drawable.ic_image_black_24dp));
        filterlist.add(new FilterItem("Adele", R.drawable.ic_image_black_24dp));
        filterlist.add(new FilterItem("Cruz", R.drawable.ic_image_black_24dp));
        filterlist.add(new FilterItem("Metropolis", R.drawable.ic_image_black_24dp));
        filterlist.add(new FilterItem("Audrey", R.drawable.ic_image_black_24dp));

        stickerpacklist.add("Love Set 1");
        stickerpacklist.add("Love Set 2");
        stickerpacklist.add("Love Set 3");
        stickerpacklist.add("Hello Set 1");
        stickerpacklist.add("Hello Set 2");
        stickerpacklist.add("Hello Set 3");
        stickerpacklist.add("Summer Camp");
        stickerpacklist.add("Tropical");

        adjustlist.add(new FilterItem("Contrast",R.drawable.contrast));
        adjustlist.add(new FilterItem("Brightness",R.drawable.brightness));
        adjustlist.add(new FilterItem("Saturation",R.drawable.saturation));
        adjustlist.add(new FilterItem("Warmth",R.drawable.warmth));
        adjustlist.add(new FilterItem("Sharpen",R.drawable.sharpen));
        adjustlist.add(new FilterItem("Blur",R.drawable.adjustcolordrop));
        adjustlist.add(new FilterItem("Grain",R.drawable.grid));
        adjustlist.add(new FilterItem("Backlight",R.drawable.light));
        adjustlist.add(new FilterItem("Fisheye",R.drawable.fisheye));
        adjustlist.add(new FilterItem("Vignette",R.drawable.vignettelogo));

        effectslist.add(new FilterItem("Autofix",R.drawable.auto_fix));
        effectslist.add(new FilterItem("Negative",R.drawable.negative));
        effectslist.add(new FilterItem("Poster",R.drawable.posterize));
        effectslist.add(new FilterItem("Grayscale",R.drawable.gray_scale));
        effectslist.add(new FilterItem("Sepia",R.drawable.sepia));
        effectslist.add(new FilterItem("Lomish",R.drawable.lomish));
        effectslist.add(new FilterItem("XPC",R.drawable.cross_process));
        effectslist.add(new FilterItem("Docs",R.drawable.documentary));

        croplist.add(new FilterItem("Free",R.drawable.ic_image_black_24dp));
        croplist.add(new FilterItem("1:1",R.drawable.oneone));
        croplist.add(new FilterItem("1:2",R.drawable.onetwo));
        croplist.add(new FilterItem("3:4",R.drawable.threefour));
        croplist.add(new FilterItem("4:3",R.drawable.fourthree));
        croplist.add(new FilterItem("3:2",R.drawable.threetwo));
        croplist.add(new FilterItem("5:4",R.drawable.fivefour));
        croplist.add(new FilterItem("9:16",R.drawable.ninesixteen));
        croplist.add(new FilterItem("16:9",R.drawable.sixteennine));


        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        itemrecyclerview = (RecyclerView) view.findViewById(R.id.filterlist);
        itemrecyclerview.setHasFixedSize(true);
        itemrecyclerview.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), LinearLayoutManager.HORIZONTAL, filterlist);
        itemrecyclerview.setAdapter(adapter);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        stickerrecyclerview=(RecyclerView)view.findViewById(R.id.stickerlist);
        stickerrecyclerview.setHasFixedSize(true);
        stickerrecyclerview.setLayoutManager(layoutManager2);


        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        adjustrecyclerview =(RecyclerView) view.findViewById(R.id.adjustlist);
        adjustrecyclerview.setHasFixedSize(true);
        adjustrecyclerview.setLayoutManager(layoutManager3);

        LinearLayoutManager layoutManager4 = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        stickerpackrecyclerview=(RecyclerView) view.findViewById(R.id.categorylist);
        stickerpackrecyclerview.setHasFixedSize(true);
        stickerpackrecyclerview.setLayoutManager(layoutManager4);

        LinearLayoutManager layoutManager5 = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        croprecyclerview=(RecyclerView) view.findViewById(R.id.croplist);
        croprecyclerview.setHasFixedSize(true);
        croprecyclerview.setLayoutManager(layoutManager5);
        CropRecyclerViewAdapter adapter2 = new CropRecyclerViewAdapter(getActivity(), LinearLayoutManager.HORIZONTAL, croplist);
        croprecyclerview.setAdapter(adapter2);




        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                devBitmapFrmViewFnc(rl);
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        revertbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap=originalbitmap;
                rootbitmap=originalbitmap;
                photo.setImageBitmap(originalbitmap);
            }
        });

        bottomNavigation = view.findViewById(R.id.navigationView);
        BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_filter:
                                Blurry.delete(rl);
                                mImageFilterView.setVisibility(GONE);
                                adjusttoolbar.setVisibility(GONE);
                                effectstoolbar.setVisibility(GONE);
                                colortoolbar.setVisibility(GONE);
                                filtertoolbar.setVisibility(View.VISIBLE);
                                toolstoolbar.setVisibility(GONE);
                                addonstoolbar.setVisibility(GONE);
                                applybutton.setVisibility(GONE);
                                drawtoolbar.setVisibility(View.GONE);
                                stickertoolbar.setVisibility(GONE);
                                additionalblurtoolbar.setVisibility(GONE);
                                setBrushDrawingMode(false);
                                setUpFiltersToolbar();
                                return true;
                            case R.id.navigation_effect:
                                Blurry.delete(rl);
                                mImageFilterView.setVisibility(GONE);
                                adjusttoolbar.setVisibility(GONE);
                                effectstoolbar.setVisibility(View.VISIBLE);
                                colortoolbar.setVisibility(GONE);
                                filtertoolbar.setVisibility(GONE);
                                toolstoolbar.setVisibility(GONE);
                                addonstoolbar.setVisibility(GONE);
                                drawtoolbar.setVisibility(View.GONE);
                                stickertoolbar.setVisibility(GONE);
                                additionalblurtoolbar.setVisibility(GONE);
                                setBrushDrawingMode(false);
                                setUpEffectsToolbar();
                                return true;
                            case R.id.navigation_addons:
                                Blurry.delete(rl);
                                mImageFilterView.setVisibility(GONE);
                                adjusttoolbar.setVisibility(GONE);
                                effectstoolbar.setVisibility(GONE);
                                colortoolbar.setVisibility(GONE);
                                filtertoolbar.setVisibility(GONE);
                                toolstoolbar.setVisibility(GONE);
                                applybutton.setVisibility(GONE);
                                addonstoolbar.setVisibility(View.VISIBLE);
                                additionalblurtoolbar.setVisibility(GONE);
                                setUpAddons();
                                return true;
                            case R.id.navigation_tool:
                                mImageFilterView.setVisibility(GONE);
                                adjusttoolbar.setVisibility(GONE);
                                effectstoolbar.setVisibility(GONE);
                                filtertoolbar.setVisibility(GONE);
                                toolstoolbar.setVisibility(View.VISIBLE);
                                addonstoolbar.setVisibility(GONE);
                                drawtoolbar.setVisibility(View.GONE);
                                stickertoolbar.setVisibility(GONE);
                                applybutton.setVisibility(GONE);
                                setBrushDrawingMode(false);
                                setUpTool();
                                return true;
                            case R.id.navigation_shop:

                                return true;
                        }
                        return false;
                    }
                };
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        return view;
    }

    private Bitmap loadBitmapFromView(View v) {
        final int w = v.getWidth();
        final int h = v.getHeight();
        final Bitmap b = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        final Canvas c = new  Canvas(b);
        //v.layout(0, 0, w, h);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        return b;
    }

    private void setPhoto(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                photo.setImageBitmap(rootbitmap);
                photo.setColorFilter(ColorFilterGenerator.adjustHue(huelevel));
                bitmap=loadBitmapFromView(photo);
                photo.setImageBitmap(bitmap);
                photo.setColorFilter(ColorFilterGenerator.setSaturation(saturationlevel));
                bitmap=loadBitmapFromView(photo);
                photo.setImageBitmap(bitmap);
                photo.setColorFilter(ColorFilterGenerator.setBrightness(brightnesslevel));
                bitmap=loadBitmapFromView(photo);
            }
        }).run();
    }

    private void setUpAddons(){
        drawbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stickertoolbar.setVisibility(GONE);
                if(drawtoolbar.getVisibility()==VISIBLE){
                    drawtoolbar.setVisibility(View.GONE);
                    setBrushDrawingMode(false);
                }
                else{
                    drawtoolbar.setVisibility(View.VISIBLE);
                    mBrushDrawingView.setVisibility(View.VISIBLE);
                    setBrushDrawingMode(true);
                    Toast.makeText(Create.this.getContext(), "Brush mode", Toast.LENGTH_SHORT).show();
                    brushbutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setBrushDrawingMode(true);
                            isEraser=false;
                            Toast.makeText(Create.this.getContext(), "Brush mode", Toast.LENGTH_SHORT).show();
                            setBrushSize(brushsizeseekbar.getProgress());
                            colourpreviewbrush.setVisibility(VISIBLE);
                        }
                    });

                    eraserbutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isEraser=true;
                            Toast.makeText(Create.this.getContext(), "Eraser mode", Toast.LENGTH_SHORT).show();
                            colourpreviewbrush.setVisibility(GONE);
                            setBrushDrawingMode(false);
                            setEraserSize(brushsizeseekbar.getProgress());
                            brushEraser();
                        }
                    });

                    brushsizeseekbar.setMax(100);
                    brushsizeseekbar.setProgress(50);
                    setBrushSize(50);
                    brushsize.setText("50");
                    brushsizeseekbar.setKeyProgressIncrement(1);
                    brushsizeseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            brushsize.setText(String.valueOf(progress));
                            if(isEraser){
                                setBrushDrawingMode(false);
                                setEraserSize(progress);
                                brushEraser();
                            }
                            else{
                                setBrushSize(progress);
                            }
                        }
                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {}
                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {}
                    });

                    brushcolour=Color.BLACK;
                    mBrushDrawingView.setBrushColor(brushcolour);
                    colourpreviewbrush.setBackgroundColor(brushcolour);
                    colourpreviewbrush.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new ColorPickerDialog.Builder(Create.this.getContext(),THEME_DEVICE_DEFAULT_DARK)
                                    .setTitle("Pick Colour")
                                    .setPreferenceName("MyColorPickerDialog")
                                    .setPositiveButton("Confirm",
                                            new ColorEnvelopeListener() {
                                                @Override
                                                public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                                                    brushcolour=envelope.getColor();
                                                    colourpreviewbrush.setBackgroundColor(brushcolour);
                                                    mBrushDrawingView.setBrushColor(brushcolour);
                                                }
                                            })
                                    .setNegativeButton("Cancel",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.dismiss();
                                                }
                                            })
                                    .attachAlphaSlideBar(false) // default is true. If false, do not show the AlphaSlideBar.
                                    .attachBrightnessSlideBar(true)  // default is true. If false, do not show the BrightnessSlideBar.
                                    .show();
                        }
                    });
                }
            }
        });
        textbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stickertoolbar.setVisibility(GONE);
                setBrushDrawingMode(false);
                setUpTextDialog();
            }
        }
        );
        stickersbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawtoolbar.setVisibility(GONE);
                setBrushDrawingMode(false);
                if(stickertoolbar.getVisibility()==VISIBLE){
                    stickertoolbar.setVisibility(GONE);
                }
                else{
                    stickertoolbar.setVisibility(View.VISIBLE);
                    StickerListRecyclerViewAdapter stickerpackadapter = new StickerListRecyclerViewAdapter(getActivity(), LinearLayoutManager.HORIZONTAL, stickerpacklist);
                    stickerpackrecyclerview.setAdapter(stickerpackadapter);
                    getStickerList();

                    stickerpackrecyclerview.addOnItemTouchListener(
                            new RecyclerViewItemClickListener(Create.this.getContext(), itemrecyclerview ,new RecyclerViewItemClickListener.OnItemClickListener() {
                                @Override public void onItemClick(View view, int position) {
                                    currentstickerpack=position;
                                    getStickerList();
                                }
                                @Override public void onLongItemClick(View view, int position) {}
                            })
                    );

                }
            }
        });
    }

    private void setUpTool(){
        adjustbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colortoolbar.setVisibility(GONE);
                if(adjusttoolbar.getVisibility()==VISIBLE){
                    adjusttoolbar.setVisibility(GONE);
                    mImageFilterView.setVisibility(GONE);
                }
                else{
                    adjusttoolbar.setVisibility(View.VISIBLE);
                    setUpAdjustToolbar();
                }
            }
        });
        framebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFrameToolbar();
                colortoolbar.setVisibility(GONE);
                adjusttoolbar.setVisibility(GONE);
            }
        });
        colorbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adjusttoolbar.setVisibility(GONE);
                additionalblurtoolbar.setVisibility(GONE);
                Blurry.delete(rl);
                if (colortoolbar.getVisibility()==VISIBLE) {
                    setPhoto();
                    colortoolbar.setVisibility(GONE);
                } else {
                    setPhoto();
                    colortoolbar.setVisibility(View.VISIBLE);
                    setUpColorSeekbar();
                }
            }
        });
    }

    private void addTextView(String edittext){
        final TextView addtext=new TextView(Create.this.getContext());
        Typeface typeface = ResourcesCompat.getFont(Create.this.getContext(), fontID);
        addtext.setTypeface(typeface);
        addtext.setTextSize(textSize);
        addtext.setTextColor(textColour);
        addtext.setAlpha((float)textOpacity/100);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addtext.setText(edittext);
        addtext.setLayoutParams(layoutParams);
        rl.addView(addtext);
        RelativeLayout.LayoutParams textlayoutparams = (RelativeLayout.LayoutParams)addtext.getLayoutParams();
        textlayoutparams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        addtext.setLayoutParams(textlayoutparams);
        MultiTouchListener multiTouchListener = getMultiTouchListener();
        multiTouchListener.setOnGestureControl(new MultiTouchListener.OnGestureControl() {
            @Override
            public void onClick() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Create.this.getContext());
                builder.setTitle("Delete object");
                builder.setCancelable(true);
                builder.setMessage("Delete this object?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addtext.setVisibility(View.GONE);
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
            @Override
            public void onLongClick() {}
        });
        addtext.setOnTouchListener(multiTouchListener);
    }

    private void addSticker(int stickerid){
        final ImageView sticker=new ImageView(Create.this.getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.width=200;
        layoutParams.height=200;
        sticker.setImageDrawable(getResources().getDrawable(stickerlist.get(stickerid).getSticker()));
        sticker.setLayoutParams(layoutParams);
        sticker.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        rl.addView(sticker);
        RelativeLayout.LayoutParams stickerlayoutparams = (RelativeLayout.LayoutParams)sticker.getLayoutParams();
        stickerlayoutparams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        sticker.setLayoutParams(stickerlayoutparams);
        MultiTouchListener multiTouchListener = getMultiTouchListener();
        multiTouchListener.setOnGestureControl(new MultiTouchListener.OnGestureControl() {
            @Override
            public void onClick() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Create.this.getContext());
                builder.setTitle("Delete object");
                builder.setCancelable(true);
                builder.setMessage("Delete this object?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sticker.setVisibility(View.GONE);
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
            @Override
            public void onLongClick() {}
        });
        sticker.setOnTouchListener(multiTouchListener);
    }

    private void setUpTextDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(Create.this.getContext());
        builder.setTitle("Add text");
        builder.setCancelable(true);
        LayoutInflater inflater = getLayoutInflater();
        final View editview= inflater.inflate(R.layout.customtextdialog, null);
        builder.setView(editview);

        final TextView textsize=editview.findViewById(R.id.textsizetext);
        final TextView textopacity=editview.findViewById(R.id.opacitytext);

        final SeekBar sizeSlider =(SeekBar) editview.findViewById(R.id.textsizeslider);
        sizeSlider.setMax(100);
        sizeSlider.setProgress(30);
        textSize=30;
        textsize.setText("30");
        sizeSlider.setKeyProgressIncrement(1);
        sizeSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textSize=progress;
                textsize.setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        final SeekBar opacitySlider = (SeekBar) editview.findViewById(R.id.opacityslider);
        opacitySlider.setMax(100);
        opacitySlider.setProgress(100);
        textOpacity=100;
        textopacity.setText("100");
        opacitySlider.setKeyProgressIncrement(1);
        opacitySlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textOpacity=progress;
                textopacity.setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        final ListView fontListView = (ListView) editview.findViewById(R.id.fontslist);
        final String[] fontList=getResources().getStringArray(R.array.font_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Create.this.getContext(), android.R.layout.simple_list_item_single_choice, fontList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView item = (TextView) super.getView(position, convertView, parent);
                    switch (position) {
                        case 0:
                            adapterFont = R.font.contra;
                            item.setTypeface(ResourcesCompat.getFont(Create.this.getContext(), adapterFont));
                            break;
                        case 1:
                            adapterFont = R.font.a_little_sunshine;
                            item.setTypeface(ResourcesCompat.getFont(Create.this.getContext(), adapterFont));
                            break;
                        case 2:
                            adapterFont = R.font.boldriley;
                            item.setTypeface(ResourcesCompat.getFont(Create.this.getContext(), adapterFont));
                            break;
                        case 3:
                            adapterFont = R.font.zboldriley_oblique;
                            item.setTypeface(ResourcesCompat.getFont(Create.this.getContext(), adapterFont));
                            break;
                        case 4:
                            adapterFont = R.font.candelion_regular;
                            item.setTypeface(ResourcesCompat.getFont(Create.this.getContext(), adapterFont));
                            break;
                        case 5:
                            adapterFont = R.font.zcandelion_bold;
                            item.setTypeface(ResourcesCompat.getFont(Create.this.getContext(), adapterFont));
                            break;
                        case 6:
                            adapterFont = R.font.fairytales_script;
                            item.setTypeface(ResourcesCompat.getFont(Create.this.getContext(), adapterFont));
                            break;
                        case 7:
                            adapterFont = R.font.funtastic;
                            item.setTypeface(ResourcesCompat.getFont(Create.this.getContext(), adapterFont));
                            break;
                        case 8:
                            adapterFont = R.font.gineva_regular;
                            item.setTypeface(ResourcesCompat.getFont(Create.this.getContext(), adapterFont));
                            break;
                        case 9:
                            adapterFont = R.font.kellyslab_regular;
                            item.setTypeface(ResourcesCompat.getFont(Create.this.getContext(), adapterFont));
                            break;
                        case 10:
                            adapterFont = R.font.kreon_regular;
                            item.setTypeface(ResourcesCompat.getFont(Create.this.getContext(), adapterFont));
                            break;
                        case 11:
                            adapterFont = R.font.zkreon_light;
                            item.setTypeface(ResourcesCompat.getFont(Create.this.getContext(), adapterFont));
                            break;
                        case 12:
                            adapterFont = R.font.zkreon_bold;
                            item.setTypeface(ResourcesCompat.getFont(Create.this.getContext(), adapterFont));
                            break;
                        case 13:
                            adapterFont = R.font.limerence;
                            item.setTypeface(ResourcesCompat.getFont(Create.this.getContext(), adapterFont));
                            break;
                        case 14:
                            adapterFont = R.font.zlimerence_thin;
                            item.setTypeface(ResourcesCompat.getFont(Create.this.getContext(), adapterFont));
                            break;
                        case 15:
                            adapterFont = R.font.merriweather;
                            item.setTypeface(ResourcesCompat.getFont(Create.this.getContext(), adapterFont));
                            break;
                        case 16:
                            adapterFont = R.font.zmerriweather_light;
                            item.setTypeface(ResourcesCompat.getFont(Create.this.getContext(), adapterFont));
                            break;
                        case 17:
                            adapterFont = R.font.zmerriweather_bold;
                            item.setTypeface(ResourcesCompat.getFont(Create.this.getContext(), adapterFont));
                            break;
                        case 18:
                            adapterFont = R.font.zmerriweather_italic;
                            item.setTypeface(ResourcesCompat.getFont(Create.this.getContext(), adapterFont));
                            break;
                        case 19:
                            adapterFont = R.font.modern_serif;
                            item.setTypeface(ResourcesCompat.getFont(Create.this.getContext(), adapterFont));
                            break;
                        case 20:
                            adapterFont = R.font.zmodernserif_italic;
                            item.setTypeface(ResourcesCompat.getFont(Create.this.getContext(), adapterFont));
                            break;
                        case 21:
                            adapterFont = R.font.napoleon;
                            item.setTypeface(ResourcesCompat.getFont(Create.this.getContext(), adapterFont));
                            break;
                        case 22:
                            adapterFont = R.font.opensans;
                            item.setTypeface(ResourcesCompat.getFont(Create.this.getContext(), adapterFont));
                            break;
                        case 23:
                            adapterFont = R.font.zopensans_light;
                            item.setTypeface(ResourcesCompat.getFont(Create.this.getContext(), adapterFont));
                            break;
                        case 24:
                            adapterFont = R.font.zopensans_bold;
                            item.setTypeface(ResourcesCompat.getFont(Create.this.getContext(), adapterFont));
                            break;
                        case 25:
                            adapterFont = R.font.zopensans_italic;
                            item.setTypeface(ResourcesCompat.getFont(Create.this.getContext(), adapterFont));
                            break;
                        case 26:
                            adapterFont = R.font.pretzel;
                            item.setTypeface(ResourcesCompat.getFont(Create.this.getContext(), adapterFont));
                            break;
                        case 27:
                            adapterFont = R.font.quintessa;
                            item.setTypeface(ResourcesCompat.getFont(Create.this.getContext(), adapterFont));
                            break;
                        case 28:
                            adapterFont = R.font.sandglow;
                            item.setTypeface(ResourcesCompat.getFont(Create.this.getContext(), adapterFont));
                            break;
                        case 29:
                            adapterFont = R.font.sphere_sans;
                            item.setTypeface(ResourcesCompat.getFont(Create.this.getContext(), adapterFont));
                            break;
                        case 30:
                            adapterFont = R.font.wildwest;
                            item.setTypeface(ResourcesCompat.getFont(Create.this.getContext(), adapterFont));
                            break;
                    }

                return item;
            }
        };
        fontListView.setAdapter(adapter);
        fontListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        fontListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        fontID=R.font.contra;
                        break;
                    case 1:
                        fontID=R.font.a_little_sunshine;
                        break;
                    case 2:
                        fontID=R.font.boldriley;
                        break;
                    case 3:
                        fontID=R.font.zboldriley_oblique;
                        break;
                    case 4:
                        fontID=R.font.candelion_regular;
                        break;
                    case 5:
                        fontID=R.font.zcandelion_bold;
                        break;
                    case 6:
                        fontID=R.font.fairytales_script;
                        break;
                    case 7:
                        fontID=R.font.funtastic;
                        break;
                    case 8:
                        fontID=R.font.gineva_regular;
                        break;
                    case 9:
                        fontID=R.font.kellyslab_regular;
                        break;
                    case 10:
                        fontID=R.font.kreon_regular;
                        break;
                    case 11:
                        fontID=R.font.zkreon_light;
                        break;
                    case 12:
                        fontID=R.font.zkreon_bold;
                        break;
                    case 13:
                        fontID=R.font.limerence;
                        break;
                    case 14:
                        fontID=R.font.zlimerence_thin;
                        break;
                    case 15:
                        fontID = R.font.merriweather;
                        break;
                    case 16:
                        fontID=R.font.zmerriweather_light;
                        break;
                    case 17:
                        fontID=R.font.zmerriweather_bold;
                        break;
                    case 18:
                        fontID=R.font.zmerriweather_italic;
                        break;
                    case 19:
                        fontID = R.font.modern_serif;
                        break;
                    case 20:
                        fontID=R.font.zmodernserif_italic;
                        break;
                    case 21:
                        fontID = R.font.napoleon;
                        break;
                    case 22:
                        fontID = R.font.opensans;
                        break;
                    case 23:
                        fontID=R.font.zopensans_light;
                        break;
                    case 24:
                        fontID=R.font.zopensans_bold;
                        break;
                    case 25:
                        fontID=R.font.zopensans_italic;
                        break;
                    case 26:
                        fontID = R.font.pretzel;
                        break;
                    case 27:
                        fontID = R.font.quintessa;
                        break;
                    case 28:
                        fontID = R.font.sandglow;
                        break;
                    case 29:
                        fontID = R.font.sphere_sans;
                        break;
                    case 30:
                        fontID = R.font.wildwest;
                        break;
                }
            }
        });
        final EditText edittext=(EditText) editview.findViewById(R.id.customtext);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addTextView(edittext.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final ImageView colourpreview=editview.findViewById(R.id.colourpreview);
        textColour=Color.BLACK;
        colourpreview.setBackgroundColor(textColour);
        Button colorpicker=editview.findViewById(R.id.pickcolourbutton);
        colorpicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorPickerDialog.Builder(Create.this.getContext(),THEME_DEVICE_DEFAULT_DARK)
                        .setTitle("Pick Colour")
                        .setPreferenceName("MyColorPickerDialog")
                        .setPositiveButton("Confirm",
                                new ColorEnvelopeListener() {
                                    @Override
                                    public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                                        textColour=envelope.getColor();
                                        colourpreview.setBackgroundColor(textColour);
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                        .attachAlphaSlideBar(false) // default is true. If false, do not show the AlphaSlideBar.
                        .attachBrightnessSlideBar(true)  // default is true. If false, do not show the BrightnessSlideBar.
                        .show();
            }
        });
        builder.show();
    }

    private void setUpColorSeekbar(){
        hue.setMax(360);
        hue.setProgress(huelevel+180);
        hue.incrementProgressBy(1);
        hue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                huelevel=progress - 180;
                setPhoto();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        saturation.setMax(100);
        saturation.setProgress(saturationlevel);
        saturation.incrementProgressBy(1);
        saturation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                saturationlevel=progress;
                setPhoto();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        brightness.setMax(100);
        brightness.setProgress(brightnesslevel);
        brightness.incrementProgressBy(1);
        brightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                brightnesslevel=progress;
                setPhoto();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void setFrameToolbar(){
        Blurry.delete(rl);
            photo.setVisibility(View.INVISIBLE);
            cropImageView.setVisibility(View.VISIBLE);
            toolstoolbar.setVisibility(GONE);
            okbutton.setVisibility(View.VISIBLE);
            rotateseekbar.setVisibility(View.VISIBLE);
            cancelbutton.setVisibility(View.VISIBLE);
            croprecyclerview.setVisibility(View.VISIBLE);
            bottomNavigation.setVisibility(GONE);
            cropImageView.setImageBitmap(bitmap);
            cropImageView.setAutoZoomEnabled(false);
            rotateseekbar.setMax(360);
            rotateseekbar.setProgress(180);
            rotateseekbar.incrementProgressBy(1);
            croprecyclerview.addOnItemTouchListener(
                    new RecyclerViewItemClickListener(Create.this.getContext(), itemrecyclerview ,new RecyclerViewItemClickListener.OnItemClickListener() {
                        @Override public void onItemClick(View view, int position) {
                            switch (position){
                                case 0:
                                    cropImageView.setFixedAspectRatio(false);
                                    break;
                                case 1:
                                    cropImageView.setAspectRatio(1,1);
                                    cropImageView.setFixedAspectRatio(true);
                                    break;
                                case 2:
                                    cropImageView.setAspectRatio(1,2);
                                    cropImageView.setFixedAspectRatio(true);
                                    break;
                                case 3:
                                    cropImageView.setAspectRatio(3,4);
                                    cropImageView.setFixedAspectRatio(true);
                                    break;
                                case 4:
                                    cropImageView.setAspectRatio(4,3);
                                    cropImageView.setFixedAspectRatio(true);
                                    break;
                                case 5:
                                    cropImageView.setAspectRatio(3,2);
                                    cropImageView.setFixedAspectRatio(true);
                                    break;
                                case 6:
                                    cropImageView.setAspectRatio(5,4);
                                    cropImageView.setFixedAspectRatio(true);
                                    break;
                                case 7:
                                    cropImageView.setAspectRatio(9,16);
                                    cropImageView.setFixedAspectRatio(true);
                                    break;
                                case 8:
                                    cropImageView.setAspectRatio(16,9);
                                    cropImageView.setFixedAspectRatio(true);
                                    break;
                            }
                        }
                        @Override public void onLongItemClick(View view, int position) {}
                    })
            );
            rotateseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    cropImageView.setRotatedDegrees((int)(progress-180)/5);
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });
            okbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bitmap=cropImageView.getCroppedImage();
                    rootbitmap =bitmap;
                    cropImageView.setVisibility(GONE);
                    photo.setVisibility(View.VISIBLE);
                    photo.setImageBitmap(null);
                    photo.setImageBitmap(bitmap);
                    rl.setLayoutParams(new RelativeLayout.LayoutParams(photo.getMaxWidth(),photo.getMaxHeight()));
                    toolstoolbar.setVisibility(View.VISIBLE);
                    okbutton.setVisibility(GONE);
                    rotateseekbar.setVisibility(GONE);
                    croprecyclerview.setVisibility(GONE);
                    cancelbutton.setVisibility(GONE);
                    bottomNavigation.setVisibility(View.VISIBLE);
                    brightnesslevel=50;
                    saturationlevel=50;
                    huelevel=0;
                }
            });
            cancelbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    photo.setVisibility(View.VISIBLE);
                    cropImageView.clearImage();
                    cropImageView.setVisibility(GONE);
                    toolstoolbar.setVisibility(View.VISIBLE);
                    okbutton.setVisibility(GONE);
                    rotateseekbar.setVisibility(GONE);
                    croprecyclerview.setVisibility(GONE);
                    cancelbutton.setVisibility(GONE);
                    bottomNavigation.setVisibility(View.VISIBLE);
                }
            });
    }

    private void setUpAdjustToolbar(){
        AdjustRecyclerViewAdapter adjustRecyclerViewAdapter = new AdjustRecyclerViewAdapter(getActivity(), LinearLayoutManager.HORIZONTAL, adjustlist);
        adjustrecyclerview.setAdapter(adjustRecyclerViewAdapter);
        mImageFilterView.setSourceBitmap(bitmap);
        mImageFilterView.setVisibility(View.VISIBLE);
        adjustseekbar.setVisibility(View.INVISIBLE);
        adjustrecyclerview.addOnItemTouchListener(new RecyclerViewItemClickListener(Create.this.getContext(), adjustrecyclerview,new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override public void onItemClick(View view, final int position) {
                switch(position){
                    case 0:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Log.v(TAG,"Contrast");
                                adjusttext.setText("Contrast");
                                Blurry.delete(rl);
                                mImageFilterView.setSourceBitmap(bitmap);
                                additionalblurtoolbar.setVisibility(GONE);
                                mImageFilterView.setVisibility(View.VISIBLE);
                                applybutton.setVisibility(View.VISIBLE);
                                filterlevel=50;
                                adjustseekbar.setVisibility(View.VISIBLE);
                                adjustseekbar.setMax(100);
                                adjustseekbar.setProgress(50);
                                adjustseekbar.setKeyProgressIncrement(1);
                                adjustseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                    @Override
                                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                        filterlevel=progress;
                                        mImageFilterView.setFilterEffect(new CustomEffect.Builder(EffectFactory.EFFECT_CONTRAST)
                                                .setParameter("contrast",(float)filterlevel/50)
                                                .build());
                                    }
                                    @Override
                                    public void onStartTrackingTouch(SeekBar seekBar) {}
                                    @Override
                                    public void onStopTrackingTouch(SeekBar seekBar) {}
                                });
                                setApplyButton();
                                mImageFilterView.setFilterEffect(new CustomEffect.Builder(EffectFactory.EFFECT_CONTRAST)
                                        .setParameter("contrast",1.0f)
                                        .build());

                            }
                        }).run();
                        break;

                    case 1:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Log.v(TAG,"Brightness");
                                adjusttext.setText("Brightness");
                                Blurry.delete(rl);
                                mImageFilterView.setSourceBitmap(bitmap);
                                additionalblurtoolbar.setVisibility(GONE);
                                mImageFilterView.setVisibility(View.VISIBLE);
                                applybutton.setVisibility(View.VISIBLE);
                                filterlevel=50;
                                adjustseekbar.setVisibility(View.VISIBLE);
                                adjustseekbar.setMax(100);
                                adjustseekbar.setProgress(50);
                                adjustseekbar.setKeyProgressIncrement(1);
                                adjustseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                    @Override
                                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                        filterlevel=progress;
                                        mImageFilterView.setFilterEffect(new CustomEffect.Builder(EffectFactory.EFFECT_BRIGHTNESS)
                                                .setParameter("brightness",(float)filterlevel/50)
                                                .build());
                                    }
                                    @Override
                                    public void onStartTrackingTouch(SeekBar seekBar) {}
                                    @Override
                                    public void onStopTrackingTouch(SeekBar seekBar) {}
                                });
                                setApplyButton();
                                mImageFilterView.setFilterEffect(new CustomEffect.Builder(EffectFactory.EFFECT_BRIGHTNESS)
                                        .setParameter("brightness",1.0f)
                                        .build());

                            }
                        }).run();
                        break;

                    case 2:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Log.v(TAG,"Saturation");
                                adjusttext.setText("Saturation");
                                Blurry.delete(rl);
                                mImageFilterView.setSourceBitmap(bitmap);
                                additionalblurtoolbar.setVisibility(GONE);
                                mImageFilterView.setVisibility(View.VISIBLE);
                                applybutton.setVisibility(View.VISIBLE);
                                filterlevel=50;
                                adjustseekbar.setVisibility(View.VISIBLE);
                                adjustseekbar.setMax(100);
                                adjustseekbar.setProgress(50);
                                adjustseekbar.setKeyProgressIncrement(1);
                                adjustseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                    @Override
                                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                        filterlevel=progress;
                                        mImageFilterView.setFilterEffect(new CustomEffect.Builder(EffectFactory.EFFECT_SATURATE)
                                                .setParameter("scale",(float)(filterlevel-50)/50)
                                                .build());
                                    }
                                    @Override
                                    public void onStartTrackingTouch(SeekBar seekBar) {}
                                    @Override
                                    public void onStopTrackingTouch(SeekBar seekBar) {}
                                });
                                setApplyButton();
                                mImageFilterView.setFilterEffect(new CustomEffect.Builder(EffectFactory.EFFECT_SATURATE)
                                        .setParameter("scale",0.0f)
                                        .build());

                            }
                        }).run();
                        break;

                    case 3:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Log.v(TAG,"Warmth");
                                adjusttext.setText("Warmth");
                                Blurry.delete(rl);
                                mImageFilterView.setSourceBitmap(bitmap);
                                additionalblurtoolbar.setVisibility(GONE);
                                mImageFilterView.setVisibility(View.VISIBLE);
                                applybutton.setVisibility(View.VISIBLE);
                                filterlevel=50;
                                adjustseekbar.setVisibility(View.VISIBLE);
                                adjustseekbar.setMax(100);
                                adjustseekbar.setProgress(50);
                                adjustseekbar.setKeyProgressIncrement(1);
                                adjustseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                    @Override
                                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                        filterlevel=progress;
                                        mImageFilterView.setFilterEffect(new CustomEffect.Builder(EffectFactory.EFFECT_TEMPERATURE)
                                                .setParameter("scale",(float)filterlevel/100)
                                                .build());
                                    }
                                    @Override
                                    public void onStartTrackingTouch(SeekBar seekBar) {}
                                    @Override
                                    public void onStopTrackingTouch(SeekBar seekBar) {}
                                });
                                setApplyButton();
                                mImageFilterView.setFilterEffect(new CustomEffect.Builder(EffectFactory.EFFECT_TEMPERATURE)
                                        .setParameter("scale",0.5f)
                                        .build());

                            }
                        }).run();
                        break;

                    case 4:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Log.v(TAG,"Sharpen");
                                adjusttext.setText("Sharpen");
                                Blurry.delete(rl);
                                mImageFilterView.setSourceBitmap(bitmap);
                                additionalblurtoolbar.setVisibility(GONE);
                                mImageFilterView.setVisibility(View.VISIBLE);
                                applybutton.setVisibility(View.VISIBLE);
                                filterlevel=50;
                                adjustseekbar.setVisibility(View.VISIBLE);
                                adjustseekbar.setMax(100);
                                adjustseekbar.setProgress(50);
                                adjustseekbar.setKeyProgressIncrement(1);
                                adjustseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                    @Override
                                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                        filterlevel=progress;
                                        mImageFilterView.setFilterEffect(new CustomEffect.Builder(EffectFactory.EFFECT_SHARPEN)
                                                .setParameter("scale",(float)filterlevel/100)
                                                .build());
                                    }
                                    @Override
                                    public void onStartTrackingTouch(SeekBar seekBar) {}
                                    @Override
                                    public void onStopTrackingTouch(SeekBar seekBar) {}
                                });
                                setApplyButton();
                                mImageFilterView.setFilterEffect(new CustomEffect.Builder(EffectFactory.EFFECT_SHARPEN)
                                        .setParameter("scale",0.5f)
                                        .build());

                            }
                        }).run();
                        break;

                    case 5:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Log.v(TAG,"Blur");
                                adjusttext.setText("Blur");
                                Blurry.delete(rl);
                                mImageFilterView.setVisibility(GONE);
                                applybutton.setVisibility(View.VISIBLE);
                                tempbitmap=bitmap;
                                filterlevel=0;
                                filterlevel2=0;
                                photo.setImageBitmap(bitmap);
                                Blurry.delete(rl);
                                Blurry.with(Create.this.getContext()).radius(1).sampling(1).onto(rl);
                                additionalblurtoolbar.setVisibility(View.VISIBLE);
                                additionalseekbar.setMax(10);
                                additionalseekbar.setProgress(0);
                                additionalseekbar.setKeyProgressIncrement(1);
                                additionalseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                    @Override
                                    public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Blurry.delete(rl);
                                                filterlevel2=progress;
                                                Blurry.with(Create.this.getContext()).radius(filterlevel+1).sampling(filterlevel2+1).onto(rl);
                                            }
                                        }).run();
                                    }
                                    @Override
                                    public void onStartTrackingTouch(SeekBar seekBar) {}
                                    @Override
                                    public void onStopTrackingTouch(SeekBar seekBar) {}
                                });

                                adjustseekbar.setVisibility(View.VISIBLE);
                                adjustseekbar.setMax(10);
                                adjustseekbar.setProgress(0);
                                adjustseekbar.setKeyProgressIncrement(1);
                                adjustseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                    @Override
                                    public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Blurry.delete(rl);
                                                filterlevel=progress;
                                                Blurry.with(Create.this.getContext()).radius(filterlevel+1).sampling(filterlevel2+1).onto(rl);
                                            }
                                        }).run();
                                    }
                                    @Override
                                    public void onStartTrackingTouch(SeekBar seekBar) {}
                                    @Override
                                    public void onStopTrackingTouch(SeekBar seekBar) {}
                                });
                                applybutton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Blurry.delete(rl);
                                        Blurry.with(Create.this.getContext()).radius(filterlevel+1).sampling(filterlevel2+1).from(bitmap).into(photo);
                                        bitmap=loadBitmapFromView(photo);
                                        rootbitmap=bitmap;
                                        photo.setImageBitmap(bitmap);
                                    }
                                });
                            }
                        }).run();
                        break;

                    case 6:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Log.v(TAG,"Grain");
                                adjusttext.setText("Grain");
                                Blurry.delete(rl);
                                mImageFilterView.setSourceBitmap(bitmap);
                                additionalblurtoolbar.setVisibility(GONE);
                                mImageFilterView.setVisibility(View.VISIBLE);
                                applybutton.setVisibility(View.VISIBLE);
                                filterlevel=50;
                                adjustseekbar.setVisibility(View.VISIBLE);
                                adjustseekbar.setMax(100);
                                adjustseekbar.setProgress(50);
                                adjustseekbar.setKeyProgressIncrement(1);
                                adjustseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                    @Override
                                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                        filterlevel=progress;
                                        mImageFilterView.setFilterEffect(new CustomEffect.Builder(EffectFactory.EFFECT_GRAIN)
                                                .setParameter("strength",(float)filterlevel/100)
                                                .build());
                                    }
                                    @Override
                                    public void onStartTrackingTouch(SeekBar seekBar) {}
                                    @Override
                                    public void onStopTrackingTouch(SeekBar seekBar) {}
                                });
                                setApplyButton();
                                mImageFilterView.setFilterEffect(new CustomEffect.Builder(EffectFactory.EFFECT_GRAIN)
                                        .setParameter("strength",0.5f)
                                        .build());

                            }
                        }).run();
                        break;

                    case 7:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Log.v(TAG,"Light");
                                adjusttext.setText("Backlight");
                                Blurry.delete(rl);
                                mImageFilterView.setSourceBitmap(bitmap);
                                additionalblurtoolbar.setVisibility(GONE);
                                mImageFilterView.setVisibility(View.VISIBLE);
                                applybutton.setVisibility(View.VISIBLE);
                                filterlevel=50;
                                adjustseekbar.setVisibility(View.VISIBLE);
                                adjustseekbar.setMax(100);
                                adjustseekbar.setProgress(50);
                                adjustseekbar.setKeyProgressIncrement(1);
                                adjustseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                    @Override
                                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                        filterlevel=progress;
                                        mImageFilterView.setFilterEffect(new CustomEffect.Builder(EffectFactory.EFFECT_FILLLIGHT)
                                                .setParameter("strength",(float)filterlevel/100)
                                                .build());
                                    }
                                    @Override
                                    public void onStartTrackingTouch(SeekBar seekBar) {}
                                    @Override
                                    public void onStopTrackingTouch(SeekBar seekBar) {}
                                });
                                setApplyButton();
                                mImageFilterView.setFilterEffect(new CustomEffect.Builder(EffectFactory.EFFECT_FILLLIGHT)
                                        .setParameter("strength",0.5f)
                                        .build());

                            }
                        }).run();
                        break;

                    case 8:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Log.v(TAG,"Fisheye");
                                adjusttext.setText("Fisheye");
                                Blurry.delete(rl);
                                mImageFilterView.setSourceBitmap(bitmap);
                                additionalblurtoolbar.setVisibility(GONE);
                                mImageFilterView.setVisibility(VISIBLE);
                                applybutton.setVisibility(VISIBLE);
                                filterlevel=50;
                                adjustseekbar.setVisibility(VISIBLE);
                                adjustseekbar.setMax(100);
                                adjustseekbar.setProgress(50);
                                adjustseekbar.setKeyProgressIncrement(1);
                                adjustseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                    @Override
                                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                        filterlevel=progress;
                                        mImageFilterView.setFilterEffect(new CustomEffect.Builder(EffectFactory.EFFECT_FISHEYE)
                                                .setParameter("scale",(float)filterlevel/100)
                                                .build());
                                    }
                                    @Override
                                    public void onStartTrackingTouch(SeekBar seekBar) {}
                                    @Override
                                    public void onStopTrackingTouch(SeekBar seekBar) {}
                                });
                                setApplyButton();
                                mImageFilterView.setFilterEffect(new CustomEffect.Builder(EffectFactory.EFFECT_FISHEYE)
                                        .setParameter("scale",0.5f)
                                        .build());

                            }
                        }).run();
                        break;

                    case 9:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Log.v(TAG,"Vignette");
                                adjusttext.setText("Vignette");
                                Blurry.delete(rl);
                                mImageFilterView.setSourceBitmap(bitmap);
                                additionalblurtoolbar.setVisibility(GONE);
                                mImageFilterView.setVisibility(View.VISIBLE);
                                applybutton.setVisibility(View.VISIBLE);
                                filterlevel=50;
                                adjustseekbar.setVisibility(View.VISIBLE);
                                adjustseekbar.setMax(100);
                                adjustseekbar.setProgress(50);
                                adjustseekbar.setKeyProgressIncrement(1);
                                adjustseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                    @Override
                                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                        filterlevel=progress;
                                        mImageFilterView.setFilterEffect(new CustomEffect.Builder(EffectFactory.EFFECT_VIGNETTE)
                                                .setParameter("scale",(float)filterlevel/100)
                                                .build());
                                    }
                                    @Override
                                    public void onStartTrackingTouch(SeekBar seekBar) {}
                                    @Override
                                    public void onStopTrackingTouch(SeekBar seekBar) {}
                                });
                                setApplyButton();
                                mImageFilterView.setFilterEffect(new CustomEffect.Builder(EffectFactory.EFFECT_VIGNETTE)
                                        .setParameter("scale",0.5f)
                                        .build());

                            }
                        }).run();
                        break;
                }
            }
            @Override public void onLongItemClick(View view, int position) {}
        }));
    }

    private void setUpEffectsToolbar(){
        RecyclerViewAdapter effectsRecyclerViewAdapter = new RecyclerViewAdapter(getActivity(), LinearLayoutManager.HORIZONTAL, effectslist);
        effectsrecyclerview.setAdapter(effectsRecyclerViewAdapter);
        mImageFilterView.setSourceBitmap(bitmap);
        mImageFilterView.setVisibility(View.VISIBLE);
        applybutton.setVisibility(View.VISIBLE);
        setApplyButton();
        effectsrecyclerview.addOnItemTouchListener(new RecyclerViewItemClickListener(Create.this.getContext(), effectsrecyclerview, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position){
                    case 0:
                        mImageFilterView.setSourceBitmap(loadBitmapFromView(photo));
                        mImageFilterView.setVisibility(View.VISIBLE);
                        mImageFilterView.setFilterEffect(PhotoFilter.AUTO_FIX);
                        break;

                    case 1:
                        mImageFilterView.setSourceBitmap(loadBitmapFromView(photo));
                        mImageFilterView.setVisibility(View.VISIBLE);
                        mImageFilterView.setFilterEffect(PhotoFilter.NEGATIVE);
                        break;

                    case 2:
                        mImageFilterView.setSourceBitmap(loadBitmapFromView(photo));
                        mImageFilterView.setVisibility(View.VISIBLE);
                        mImageFilterView.setFilterEffect(PhotoFilter.POSTERIZE);
                        break;

                    case 3:
                        mImageFilterView.setSourceBitmap(loadBitmapFromView(photo));
                        mImageFilterView.setVisibility(View.VISIBLE);
                        mImageFilterView.setFilterEffect(PhotoFilter.GRAY_SCALE);
                        break;

                    case 4:
                        mImageFilterView.setSourceBitmap(loadBitmapFromView(photo));
                        mImageFilterView.setVisibility(View.VISIBLE);
                        mImageFilterView.setFilterEffect(PhotoFilter.SEPIA);
                        break;

                    case 5:
                        mImageFilterView.setSourceBitmap(loadBitmapFromView(photo));
                        mImageFilterView.setVisibility(View.VISIBLE);
                        mImageFilterView.setFilterEffect(PhotoFilter.LOMISH);
                        break;

                    case 6:
                        mImageFilterView.setSourceBitmap(loadBitmapFromView(photo));
                        mImageFilterView.setVisibility(View.VISIBLE);
                        mImageFilterView.setFilterEffect(PhotoFilter.CROSS_PROCESS);
                        break;

                    case 7:
                        mImageFilterView.setSourceBitmap(loadBitmapFromView(photo));
                        mImageFilterView.setVisibility(View.VISIBLE);
                        mImageFilterView.setFilterEffect(PhotoFilter.DOCUMENTARY);
                        break;
                }
            }
            @Override
            public void onLongItemClick(View view, int position) {}
        }));
    }

    private void setApplyButton(){
        applybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFilter();
                okbutton.setVisibility(GONE);
                rotateseekbar.setVisibility(GONE);
                croprecyclerview.setVisibility(GONE);
                cancelbutton.setVisibility(GONE);
                filtertoolbar.setVisibility(GONE);
                effectstoolbar.setVisibility(GONE);
                adjusttoolbar.setVisibility(GONE);
                additionalblurtoolbar.setVisibility(GONE);
                bottomNavigation.setVisibility(View.VISIBLE);
                applybutton.setVisibility(GONE);
                brightnesslevel=50;
                saturationlevel=50;
                huelevel=0;
            }
        });
    }

    private void getStickerList(){
        stickerlist= new StickerList().getStickerList(currentstickerpack);
        StickerRecyclerViewAdapter stickeradapter = new StickerRecyclerViewAdapter(getActivity(), LinearLayoutManager.HORIZONTAL, stickerlist);
        stickerrecyclerview.setAdapter(stickeradapter);

        stickerrecyclerview.addOnItemTouchListener(
                new RecyclerViewItemClickListener(Create.this.getContext(), itemrecyclerview ,new RecyclerViewItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        addSticker(position);
                    }
                    @Override public void onLongItemClick(View view, int position) {}
                })
        );
    }

    private MultiTouchListener getMultiTouchListener() {
        MultiTouchListener multiTouchListener = new MultiTouchListener(
                null,
                rl,
                photo,
                true,
                mOnPhotoEditorListener);
        //multiTouchListener.setOnMultiTouchListener(this);
        return multiTouchListener;
    }

    private void setBrushDrawingMode(boolean brushDrawingMode){
        mBrushDrawingView.setBrushDrawingMode(brushDrawingMode);
    }

    private void setBrushSize(float size) {
        if (mBrushDrawingView != null)
            mBrushDrawingView.setBrushSize(size);
    }

    private void setOpacity(@IntRange(from = 0, to = 100) int opacity) {
        if (mBrushDrawingView != null) {
            opacity = (int) ((opacity / 100.0) * 255.0);
            mBrushDrawingView.setOpacity(opacity);
        }
    }

    private void brushEraser() {
        if (mBrushDrawingView != null)
            mBrushDrawingView.brushEraser();
    }

    private void setEraserSize(float size){
        if (mBrushDrawingView != null){
            mBrushDrawingView.setBrushEraserSize(size);
        }
    }

    private void clearBrushAllViews() {
        if (mBrushDrawingView != null)
            mBrushDrawingView.clearAll();
    }

    private void setUpFiltersToolbar(){
        tempbitmap=bitmap;
        tempbitmap= tempbitmap.copy(Bitmap.Config.ARGB_8888, true);
        applybutton.setVisibility(VISIBLE);
        itemrecyclerview.addOnItemTouchListener(new RecyclerViewItemClickListener(Create.this.getContext(), itemrecyclerview, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position){
                    case 0:
                        photo.setImageBitmap(tempbitmap);
                        break;

                    case 1:
                        tempbitmap=bitmap;
                        tempbitmap= tempbitmap.copy(Bitmap.Config.ARGB_8888, true);
                        myFilter = FilterPack.getAweStruckVibeFilter(Create.this.getContext());
                        photo.setImageBitmap(myFilter.processFilter(tempbitmap));
                        break;

                    case 2:
                        tempbitmap=bitmap;
                        tempbitmap= tempbitmap.copy(Bitmap.Config.ARGB_8888, true);
                        myFilter = FilterPack.getClarendon(Create.this.getContext());
                        photo.setImageBitmap(myFilter.processFilter(tempbitmap));
                        break;

                    case 3:
                        tempbitmap=bitmap;
                        tempbitmap= tempbitmap.copy(Bitmap.Config.ARGB_8888, true);
                        myFilter = FilterPack.getOldManFilter(Create.this.getContext());
                        photo.setImageBitmap(myFilter.processFilter(tempbitmap));
                        break;

                    case 4:
                        tempbitmap=bitmap;
                        tempbitmap= tempbitmap.copy(Bitmap.Config.ARGB_8888, true);
                        myFilter = FilterPack.getMarsFilter(Create.this.getContext());
                        photo.setImageBitmap(myFilter.processFilter(tempbitmap));
                        break;

                    case 5:
                        tempbitmap=bitmap;
                        tempbitmap= tempbitmap.copy(Bitmap.Config.ARGB_8888, true);
                        myFilter = FilterPack.getRiseFilter(Create.this.getContext());
                        photo.setImageBitmap(myFilter.processFilter(tempbitmap));
                        break;

                    case 6:
                        tempbitmap=bitmap;
                        tempbitmap= tempbitmap.copy(Bitmap.Config.ARGB_8888, true);
                        myFilter = FilterPack.getAprilFilter(Create.this.getContext());
                        photo.setImageBitmap(myFilter.processFilter(tempbitmap));
                        break;

                    case 7:
                        tempbitmap=bitmap;
                        tempbitmap= tempbitmap.copy(Bitmap.Config.ARGB_8888, true);
                        myFilter = FilterPack.getAmazonFilter(Create.this.getContext());
                        photo.setImageBitmap(myFilter.processFilter(tempbitmap));
                        break;

                    case 8:
                        tempbitmap=bitmap;
                        tempbitmap= tempbitmap.copy(Bitmap.Config.ARGB_8888, true);
                        myFilter = FilterPack.getStarLitFilter(Create.this.getContext());
                        photo.setImageBitmap(myFilter.processFilter(tempbitmap));
                        break;

                    case 9:
                        tempbitmap=bitmap;
                        tempbitmap= tempbitmap.copy(Bitmap.Config.ARGB_8888, true);
                        myFilter = FilterPack.getNightWhisperFilter(Create.this.getContext());
                        photo.setImageBitmap(myFilter.processFilter(tempbitmap));
                        break;

                    case 10:
                        tempbitmap=bitmap;
                        tempbitmap= tempbitmap.copy(Bitmap.Config.ARGB_8888, true);
                        myFilter = FilterPack.getLimeStutterFilter(Create.this.getContext());
                        photo.setImageBitmap(myFilter.processFilter(tempbitmap));
                        break;

                    case 11:
                        tempbitmap=bitmap;
                        tempbitmap= tempbitmap.copy(Bitmap.Config.ARGB_8888, true);
                        myFilter = FilterPack.getHaanFilter(Create.this.getContext());
                        photo.setImageBitmap(myFilter.processFilter(tempbitmap));
                        break;

                    case 12:
                        tempbitmap=bitmap;
                        tempbitmap= tempbitmap.copy(Bitmap.Config.ARGB_8888, true);
                        myFilter = FilterPack.getBlueMessFilter(Create.this.getContext());
                        photo.setImageBitmap(myFilter.processFilter(tempbitmap));
                        break;

                    case 13:
                        tempbitmap=bitmap;
                        tempbitmap= tempbitmap.copy(Bitmap.Config.ARGB_8888, true);
                        myFilter = FilterPack.getAdeleFilter(Create.this.getContext());
                        photo.setImageBitmap(myFilter.processFilter(tempbitmap));
                        break;

                    case 14:
                        tempbitmap=bitmap;
                        tempbitmap= tempbitmap.copy(Bitmap.Config.ARGB_8888, true);
                        myFilter = FilterPack.getCruzFilter(Create.this.getContext());
                        photo.setImageBitmap(myFilter.processFilter(tempbitmap));
                        break;

                    case 15:
                        tempbitmap=bitmap;
                        tempbitmap= tempbitmap.copy(Bitmap.Config.ARGB_8888, true);
                        myFilter = FilterPack.getMetropolis(Create.this.getContext());
                        photo.setImageBitmap(myFilter.processFilter(tempbitmap));
                        break;

                    case 16:
                        tempbitmap=bitmap;
                        tempbitmap= tempbitmap.copy(Bitmap.Config.ARGB_8888, true);
                        myFilter = FilterPack.getAudreyFilter(Create.this.getContext());
                        photo.setImageBitmap(myFilter.processFilter(tempbitmap));
                        break;
                }
            }
            @Override
            public void onLongItemClick(View view, int position) {}
        }));
        applybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap=loadBitmapFromView(photo);
                rootbitmap=bitmap;
                photo.setImageBitmap(bitmap);
                Toast.makeText(Create.this.getContext(), "Filter applied!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void saveFilter() {
            mImageFilterView.saveBitmap(new OnSaveBitmap() {
                @Override
                public void onBitmapReady(final Bitmap saveBitmap) {
                    Log.e(TAG, "saveFilter: " + saveBitmap);
                    photo.setImageBitmap(saveBitmap);
                    bitmap=saveBitmap;
                    rootbitmap=saveBitmap;
                    mImageFilterView.setVisibility(GONE);
                }
                @Override
                public void onFailure(Exception e){}
            });
        }

    private void devBitmapFrmViewFnc(View viewVar) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Create.this.getContext());
        builder.setTitle("Save image");
        builder.setCancelable(true);
        LayoutInflater inflater = getLayoutInflater();
        View confirmview= inflater.inflate(R.layout.confirmdialog, null);
        builder.setView(confirmview);
        ImageView confirmimage = (ImageView) confirmview.findViewById(R.id.confirmphoto);
        confirmimage.setImageBitmap(null);
        final Bitmap bitmapk=createBitmapFromView(viewVar);
        confirmimage.setImageBitmap(bitmapk);
        builder.setPositiveButton("Save as JPG", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                storeImageAsJPG(bitmapk, UUID.randomUUID().toString());

            }
        });
        builder.setNeutralButton("Save as PNG",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                storeImageAsPNG(bitmapk, UUID.randomUUID().toString());
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

    private Bitmap createBitmapFromView(View view){
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(),view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable background = view.getBackground();
        if (background != null) {
            background.draw(canvas);
        }
        view.draw(canvas);
        return bitmap;
    }

    private void storeImageAsJPG(Bitmap imageData, String filename) {
        itemHelper ih=new itemHelper(Create.this.getContext());
        ih.insertItem(filename,imageData);
        // get path to external storage (SD card)
        File sdIconStorageDir = null;
        sdIconStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Styleworks");
        // create storage directories, if they don't exist
        if (!sdIconStorageDir.exists()) {
            sdIconStorageDir.mkdirs();
        }
        try {
            String filePath = sdIconStorageDir.toString() + File.separator + filename+".jpeg";
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
            //Toast.makeText(m_cont, "Image Saved at----" + filePath, Toast.LENGTH_LONG).show();
            // choose another format if PNG doesn't suit you
            imageData.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            MediaScannerConnection.scanFile(Create.this.getContext(),new String[] { filePath }, null,new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("ExternalStorage", "Scanned " + path + ":");
                            Log.i("ExternalStorage", "-> uri=" + uri);
                        }
                    });
            Toast.makeText(Create.this.getContext(), "Image saved to: "+filePath, Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Log.w("TAG", "Error saving image file: " + e.getMessage());
            Toast.makeText(Create.this.getContext(), "Failed to save image", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.w("TAG", "Error saving image file: " + e.getMessage());
            Toast.makeText(Create.this.getContext(), "Failed to save image", Toast.LENGTH_SHORT).show();
        }
    }

    private void storeImageAsPNG(Bitmap imageData, String filename) {
        itemHelper ih=new itemHelper(Create.this.getContext());
        ih.insertItem(filename,imageData);
        // get path to external storage (SD card)
        File sdIconStorageDir = null;
        sdIconStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Styleworks");
        // create storage directories, if they don't exist
        if (!sdIconStorageDir.exists()) {
            sdIconStorageDir.mkdirs();
        }
        try {
            String filePath = sdIconStorageDir.toString() + File.separator + filename+".jpeg";
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
            //Toast.makeText(m_cont, "Image Saved at----" + filePath, Toast.LENGTH_LONG).show();
            // choose another format if PNG doesn't suit you
            imageData.compress(Bitmap.CompressFormat.PNG, 100, bos);
            bos.flush();
            bos.close();
            MediaScannerConnection.scanFile(Create.this.getContext(),new String[] { filePath }, null,new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                    Log.i("ExternalStorage", "Scanned " + path + ":");
                    Log.i("ExternalStorage", "-> uri=" + uri);
                }
            });
            Toast.makeText(Create.this.getContext(), "Image saved to: "+filePath, Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Log.w("TAG", "Error saving image file: " + e.getMessage());
            Toast.makeText(Create.this.getContext(), "Failed to save image", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.w("TAG", "Error saving image file: " + e.getMessage());
            Toast.makeText(Create.this.getContext(), "Failed to save image", Toast.LENGTH_SHORT).show();
        }
    }
}