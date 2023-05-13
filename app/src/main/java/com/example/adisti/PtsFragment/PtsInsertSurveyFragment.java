package com.example.adisti.PtsFragment;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.adisti.Model.BidangManfaatPerusahaanModel;
import com.example.adisti.Model.IndikatorBidangManfaatPerusahaanModel;
import com.example.adisti.Model.KategoriPemohonBantuanModel;
import com.example.adisti.Model.PihakPenerimaBantuanModel;
import com.example.adisti.Model.PilarModel;
import com.example.adisti.Model.RanModel;
import com.example.adisti.Model.ResponseModel;
import com.example.adisti.Model.TpbModel;
import com.example.adisti.PicAdapter.SpinnerBidangManfaatPerusahaanAdapter;
import com.example.adisti.PicAdapter.SpinnerIndikatorBidangManfaatAdapter;
import com.example.adisti.PicAdapter.SpinnerKategoriPemohonBantuanAdapter;
import com.example.adisti.PicAdapter.SpinnerPemohonBantuanAdapter;
import com.example.adisti.PicAdapter.SpinnerPilarAdapter;
import com.example.adisti.PicAdapter.SpinnerRanAdapter;
import com.example.adisti.PicAdapter.SpinnerTpbAdapter;
import com.example.adisti.R;
import com.example.adisti.Util.DataApi;
import com.example.adisti.Util.PicInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PtsInsertSurveyFragment extends Fragment {

    EditText etNamaPetugasSurvey, etJabatanPetugasSurvey, etUnitKerja, etKetuaPanitia, etNamaPanitia,
            etAlamatPanitia, etNoTelpPanitia, etNamaBendahara, etAlamatBendahara, etSumberDana,
            etNominalDanaKegiatan, etSumberPrioritas, etNominalPrioritas, etKtpPath, etButabPath, etFotoSurveyPath;

    Button btnKtpPicker, btnButabPicker, btnFotoSurveyPicker, btnSubmit, btnBatal;
    ImageView ivKtp, ivButab, ivFotoSurvey;
    private File fileKtp, fileButab, fileFotoSurvey;






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pts_insert_survey, container, false);
        init(view);

        btnKtpPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdfFilePicker(1);
            }
        });
        btnButabPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdfFilePicker(2);
            }
        });
        btnFotoSurveyPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdfFilePicker(3);
            }
        });
        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });



        return view;
    }

    private void init(View view) {
        etNamaPetugasSurvey = view.findViewById(R.id.etNamaPetugasSurvey);
        etJabatanPetugasSurvey = view.findViewById(R.id.etJabatanPetugasSurvey);
        etUnitKerja = view.findViewById(R.id.etUnitKerja);
        etKetuaPanitia = view.findViewById(R.id.etKetuaPanitia);
        etNamaPanitia = view.findViewById(R.id.etNamaPanitia);
        etAlamatPanitia = view.findViewById(R.id.etAlamatPanitia);
        etNoTelpPanitia = view.findViewById(R.id.etNoTelpPanitia);
        etNamaBendahara = view.findViewById(R.id.etNamaBendahara);
        etAlamatBendahara = view.findViewById(R.id.etAlamatBendahara);
        etSumberDana = view.findViewById(R.id.etSumberDana);
        etNominalDanaKegiatan = view.findViewById(R.id.etNominalDanaKegiatan);
        etSumberPrioritas = view.findViewById(R.id.etSumberPrioritas);
        etNominalPrioritas = view.findViewById(R.id.etNominalPrioritas);
        etKtpPath = view.findViewById(R.id.etKtpPath);
        etButabPath = view.findViewById(R.id.etButabPath);
        etFotoSurveyPath = view.findViewById(R.id.etFotoSurveyPath);
        btnKtpPicker = view.findViewById(R.id.btnKtpPicker);
        btnButabPicker = view.findViewById(R.id.btnButabPicker);
        btnFotoSurveyPicker = view.findViewById(R.id.btnFotoSurveyPicker);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnBatal = view.findViewById(R.id.btnBatal);
        ivKtp = view.findViewById(R.id.ivKtp);
        ivButab = view.findViewById(R.id.ivButab);
        ivFotoSurvey = view.findViewById(R.id.ivFotoSurvey);

    }
    private void pdfFilePicker(Integer code) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, code);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 1) {
                Uri uri = data.getData();
                String pdfPath = getRealPathFromUri(uri);
                fileKtp = new File(pdfPath);
                ivKtp.setVisibility(View.VISIBLE);
                etKtpPath.setText(fileKtp.getName());
            }else if (requestCode == 2) {
                Uri uri = data.getData();
                String pdfPath = getRealPathFromUri(uri);
                fileButab = new File(pdfPath);
                ivButab.setVisibility(View.VISIBLE);
                etButabPath.setText(fileButab.getName());
            }
            else if (requestCode == 3){
                Uri uri = data.getData();
                String pdfPath = getRealPathFromUri(uri);
                fileFotoSurvey = new File(pdfPath);
                ivFotoSurvey.setVisibility(View.VISIBLE);
                etFotoSurveyPath.setText(fileFotoSurvey.getName());
            }
        }
    }


    public String getRealPathFromUri(Uri uri) {
        String filePath = "";
        if (getContext().getContentResolver() != null) {
            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
                File file = new File(getContext().getCacheDir(), getFileName(uri));
                writeFile(inputStream, file);
                filePath = file.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filePath;
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {

                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private void writeFile(InputStream inputStream, File file) throws IOException {
        OutputStream outputStream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }



}