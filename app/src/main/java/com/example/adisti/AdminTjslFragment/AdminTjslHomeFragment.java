package com.example.adisti.AdminTjslFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.adisti.Model.UserModel;
import com.example.adisti.PicFragment.PicProfileFragment;
import com.example.adisti.R;
import com.example.adisti.Util.DataApi;
import com.example.adisti.Util.PicInterface;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminTjslHomeFragment extends Fragment {
    TextView tvUsername;
    SharedPreferences sharedPreferences;
    CardView cvMenuRealisasiBantuan, cvMenuLpj, cvMenuLaporan ;
    PicInterface picInterface;
    String userId;
    ImageView img_profile;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_tjsl_home, container, false);
        init(view);

        cvMenuRealisasiBantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new AdminTjslRealisasiBantuanFragment());
            }
        });


        cvMenuLpj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new AdminTjslLpjKegiatanFragment());
            }
        });

        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAdminTjsl, new PicProfileFragment())
                        .addToBackStack(null).commit();
            }
        });

        cvMenuLaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new AdminTjslLaporanFragment());
            }
        });






        return view;
    }



    private void init(View view) {
        tvUsername = view.findViewById(R.id.tvUsername);
        sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        tvUsername.setText(sharedPreferences.getString("nama", null));
        cvMenuRealisasiBantuan = view.findViewById(R.id.cvMenuRealisasiBantuan);
        cvMenuLpj = view.findViewById(R.id.cvMenuLpj);
        cvMenuLaporan = view.findViewById(R.id.cvMenuLaporan);

        img_profile = view.findViewById(R.id.img_profile);
        picInterface = DataApi.getClient().create(PicInterface.class);
        userId = sharedPreferences.getString("user_id", null);
        loadProfile();


    }

    private void replace(Fragment fragment) {
        ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.frameAdminTjsl, fragment)
                .addToBackStack(null).commit();
    }

    private void loadProfile(){
        Dialog dialog = new Dialog(getContext());
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_progress_bar);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

        picInterface.getUserById(userId).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful() && response.body() != null) {

                    Glide.with(getContext()).load(response.body().getPhotoProfile())
                            .skipMemoryCache(false)
                            .dontAnimate()
                            .fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(img_profile);



                    dialog.dismiss();


                }else {
                    Toasty.error(getContext(), "Terjadi kesalahan", Toasty.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Dialog dialog1 = new Dialog(getContext());
                dialog1.setCanceledOnTouchOutside(false);
                dialog1.setContentView(R.layout.dialog_no_connection);
                dialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                Button btnRefresh;
                btnRefresh = dialog1.findViewById(R.id.btnRefresh);
                btnRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadProfile();
                        dialog1.dismiss();
                    }
                });
                dialog1.show();

            }
        });



    }

}