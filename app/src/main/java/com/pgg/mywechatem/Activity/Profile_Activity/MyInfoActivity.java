package com.pgg.mywechatem.Activity.Profile_Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.pgg.mywechatem.Activity.BaseActivity;
import com.pgg.mywechatem.R;
import com.pgg.mywechatem.Uitils.Constants;
import com.pgg.mywechatem.Uitils.GlideImageLoader;
import com.pgg.mywechatem.Uitils.Utils;


import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by PDD on 2017/11/18.
 */

public class MyInfoActivity extends BaseActivity implements View.OnClickListener{
    private ImageButton ib_exit_01;
    private View vertical_line;
    private TextView title_tv_left;
    private TextView title_tv_center;
    private RelativeLayout rl_head,rl_nickname,rl_id,rl_erweima;
    private TextView tv_more,tv_my_location;
    private ImagePicker imagePicker;
    ArrayList<ImageItem> images = null;
    private ImageView iv_head;
    private TextView tv_nickname_content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_info);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initController() {
        ib_exit_01 =findViewById(R.id.ib_exit_01);
        vertical_line =findViewById(R.id.vertical_line);
        title_tv_left =findViewById(R.id.title_tv_left);
        title_tv_center =findViewById(R.id.title_tv_center);
        rl_head=findViewById(R.id.rl_head);
        rl_nickname=findViewById(R.id.rl_nickname);
        rl_id=findViewById(R.id.rl_id);
        rl_erweima=findViewById(R.id.rl_erweima);
        tv_more=findViewById(R.id.tv_more);
        tv_my_location=findViewById(R.id.tv_my_location);
        iv_head=findViewById(R.id.iv_head);
        tv_nickname_content=findViewById(R.id.tv_nickname_content);
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null&&bundle.getString(Constants.NAME)!=null){
            tv_nickname_content.setText(bundle.getString(Constants.NAME));
        }
    }

    @Override
    public void initView() {
        ib_exit_01.setVisibility(View.VISIBLE);
        vertical_line.setVisibility(View.VISIBLE);
        title_tv_left.setVisibility(View.VISIBLE);
        title_tv_center.setVisibility(View.GONE);
        title_tv_left.setText("个人信息");
        ib_exit_01.setBackgroundResource(R.drawable.ic_menu_exit_left);
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        ib_exit_01.setOnClickListener(this);
        rl_head.setOnClickListener(this);
        rl_nickname.setOnClickListener(this);
        rl_id.setOnClickListener(this);
        rl_erweima.setOnClickListener(this);
        tv_more.setOnClickListener(this);
        tv_my_location.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_exit_01:
                Utils.finish(MyInfoActivity.this);
                break;
            case R.id.rl_head:
                //选择头像
                ChooseHeaderMethod();
                break;
            case R.id.rl_nickname:
                //我的昵称
                Utils.start_Activity(MyInfoActivity.this,ChangeNicknameActivity.class
                        ,new BasicNameValuePair(Constants.NAME,tv_nickname_content.getText().toString()));
                finish_activity(MyInfoActivity.this);
                break;
            case R.id.rl_id:
                //我的账号
                break;
            case R.id.rl_erweima:
                //我的二维码
                Utils.start_Activity(MyInfoActivity.this,MyCodeActivity.class);
                break;
            case R.id.tv_more:
                //更多
                Utils.start_Activity(MyInfoActivity.this,MoreInfoActivity.class);
                break;
            case R.id.tv_my_location:
                //我的地址
                Utils.start_Activity(MyInfoActivity.this,MyLocationActivity.class);
                break;
        }
    }

    private void ChooseHeaderMethod() {
        imagePicker.setMultiMode(false);
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);
        Integer width = Integer.valueOf(300);
        Integer height = Integer.valueOf(300);
        width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
        height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, getResources().getDisplayMetrics());
        imagePicker.setFocusWidth(width);
        imagePicker.setFocusHeight(height);
        imagePicker.setOutPutX(Integer.valueOf(60));
        imagePicker.setOutPutY(Integer.valueOf(60));
        Intent intent = new Intent(this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
        //ImagePicker.getInstance().setSelectedImages(images);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                File file=new File(images.get(0).path);
                if (file.exists()){
                    Bitmap bm= BitmapFactory.decodeFile(images.get(0).path);
                    iv_head.setImageBitmap(bm);
                }
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
