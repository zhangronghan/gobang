package com.example.administrator.gobang;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private MyView mMyView;
    private ImageView mIvMenu;
    private PopupMenu popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        init();

    }

    private void initViews() {
        mMyView= (MyView) findViewById(R.id.myView);
        mIvMenu = (ImageView) findViewById(R.id.iv_image);
    }

    private void init() {
        mIvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup=new PopupMenu(MainActivity.this,mIvMenu);
                popup.getMenuInflater().inflate(R.menu.popup_menu,popup.getMenu());
                popupClickItem();
                popup.show();
            }
        });

    }

    private void popupClickItem() {
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_new:
                        mMyView.ReStart();
                        break;

                    case R.id.menu_back:
                        mMyView.gameBack();
                        break;
                    default:
                        break;

                }
                return false;
            }
        });

    }


}
