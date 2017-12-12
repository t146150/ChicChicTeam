package com.minhduc.goiymonan.User;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.minhduc.goiymonan.DangNhapActivity;
import com.minhduc.goiymonan.DatHang;
import com.minhduc.goiymonan.R;

public class DatMuaActivity extends AppCompatActivity {
    EditText edtHoten, edtEmail, edtSoDT, edtChiChu;
    Button btnDatMua;
    DatabaseReference mData;
    String idSP;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_mua);
        AnhXa();
        NavigationView();
        mData = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        idSP = intent.getStringExtra("idSanPhamMua");

        btnDatMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XacNhanMua();
            }
        });

        navigationView.setNavigationItemSelectedListener(null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menuTrangChu:
                        Intent intent1 = new Intent(DatMuaActivity.this, UserMainActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.menuDangXuat:
                        Intent intent2 = new Intent(DatMuaActivity.this, DangNhapActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.menuGioHang:
                        Intent intent3 = new Intent(DatMuaActivity.this, GioHangActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.menuContact:
                        Intent intent4 = new Intent(DatMuaActivity.this, ContactActivity.class);
                        startActivity(intent4);
                        break;
                    case R.id.menuVideo:
                        Intent intent5 = new Intent(DatMuaActivity.this, DanhSachVideoActivity.class);
                        startActivity(intent5);
                        break;

                }

                return false;
            }
        });
    }
    private void XacNhanMua(){
        AlertDialog.Builder xacNhanMua = new AlertDialog.Builder(this);
        xacNhanMua.setMessage("Bạn có muốn đặt hàng sản phẩm này không");
        xacNhanMua.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatHang datHang = new DatHang(
                        null,
                        idSP,
                        edtHoten.getText().toString(),
                        edtEmail.getText().toString(),
                        edtSoDT.getText().toString(),
                        edtChiChu.getText().toString()
                );
                mData.child("ORDER").push().setValue(datHang, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                        if (databaseError == null){
                            Toast.makeText(DatMuaActivity.this, "Đặt mua thành công\n chúng tôi sẽ sớm liên hệ với bạn", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else {
                            Toast.makeText(DatMuaActivity.this, "Xảy ra lỗi, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Time today = new Time(Time.getCurrentTimezone());

                today.setToNow();
                String time =  today.format("%k:%M:%S") +"";
                String ghichu =  edtChiChu.getText().toString().trim();
                UserMainActivity.database.
                        QueryData("INSERT INTO GioHangUser VALUES(null, '" +idSP +"', '" + time + "', '"  +  ghichu+ "')" );
            }
        });
        xacNhanMua.setNegativeButton("Không ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        xacNhanMua.show();
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
        btnDatMua = (Button) findViewById(R.id.buttonDatMua);
        edtHoten = (EditText) findViewById(R.id.editTextHoten);
        edtEmail = (EditText) findViewById(R.id.editTextEmail);
        edtSoDT = (EditText) findViewById(R.id.editTextSoDienThoai);
        edtChiChu = (EditText) findViewById(R.id.editTextGhiChu);
        navigationView = (NavigationView) findViewById(R.id.myNavigationViewUser);
        drawerLayout = (DrawerLayout) findViewById(R.id.myDrawerLayout);
        toolbar = (Toolbar) findViewById(R.id.myToolbarUser);
    }
}