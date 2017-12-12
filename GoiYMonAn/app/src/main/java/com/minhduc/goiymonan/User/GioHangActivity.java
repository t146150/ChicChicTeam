package com.minhduc.goiymonan.User;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.minhduc.goiymonan.DangNhapActivity;
import com.minhduc.goiymonan.R;

import java.util.ArrayList;

public class GioHangActivity extends AppCompatActivity {
    //    public static SQLite database;
    ListView lvGioHang;
    ArrayList<GioHang> arrayGioHang;
    GioHangAdapter adapter;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        lvGioHang = (ListView) findViewById(R.id.listGioHang);

        arrayGioHang = new ArrayList<>();

        adapter = new GioHangAdapter(this, R.layout.dong_gio_hang, arrayGioHang);
        lvGioHang.setAdapter(adapter);

        AnhXa();
        NavigationView();
        LoadData();


        //set lại màu
        navigationView.setNavigationItemSelectedListener(null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menuTrangChu:
                        Intent intent1 = new Intent(GioHangActivity.this, UserMainActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.menuDangXuat:
                        Intent intent2 = new Intent(GioHangActivity.this, DangNhapActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.menuGioHang:
                        Intent intent3 = new Intent(GioHangActivity.this, GioHangActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.menuContact:
                        Intent intent4 = new Intent(GioHangActivity.this, ContactActivity.class);
                        startActivity(intent4);
                        break;
                    case R.id.menuVideo:
                        Intent intent5 = new Intent(GioHangActivity.this, DanhSachVideoActivity.class);
                        startActivity(intent5);
                        break;

                }

                return false;
            }
        });
    }




    private void LoadData()
    {
        // duyệt dữ liệu từ bảng
        Cursor cursorHS = UserMainActivity.database.GetData("SELECT * FROM GioHangUser "); // database sẽ được lấy ra và bỏ vô con trỏ cursorHS, muốn lấy hết tất cả dữ liệu thì SELECT * ,còn lấy cái nào thì id, tên , cái cần lấy
        //    SELECT *  thì: id =0, hoten = 1, diachi = 2, sodt = 3
        //nếu SELECT hoten, diachi  thì :  hoten = 0, diachi = 1
        arrayGioHang.clear();
        while ( cursorHS.moveToNext()) // trong lúc con trỏ di truyển duyệt được dữ liệu
        {

            int id     = cursorHS.getInt(0); //// lấy id ở cột 0 đầu tiên (cột id)
            String idSP = cursorHS.getString(1);
            String thoigian = cursorHS.getString(2);
            String ghichu = cursorHS.getString(3);
            arrayGioHang.add(new GioHang(id, idSP, thoigian, ghichu));
        }
        adapter.notifyDataSetChanged();

    }
    public void NavigationView() {
        setSupportActionBar(toolbar);
        //set màu của bar
        toolbar.setBackgroundColor(Color.BLUE);
        //enable cái icon lên
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // chèn icon ba gạch
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);

        // sự kiện khi nhấn nút hiện ra cái navigation
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // chạy cái navigation ra thông qua cái Drawer
                drawerLayout.openDrawer(Gravity.START);
            }
        });
    }

    private void AnhXa()
    {
        navigationView = (NavigationView) findViewById(R.id.myNavigationViewUser);
        drawerLayout = (DrawerLayout) findViewById(R.id.myDrawerLayout);
        toolbar = (Toolbar) findViewById(R.id.myToolbarUser);

    }

}