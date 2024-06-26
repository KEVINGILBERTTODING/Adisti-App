package com.example.adisti.Util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataApi {

    public static final String IP_ADDRESS = "192.168.100.54";
    public static final String PATH_PROPOSAL = "http://"+IP_ADDRESS+"/adisti-jr/";
    public static final String BASE_URL = "http://"+IP_ADDRESS+"/adisti-jr/api/";
    public static final String URL_PHOTO_PROFILE = "http://"+IP_ADDRESS+"/adisti-jr/uploads/photo_profile/";
    public static final String URL_DOWNLOAD_PROPOSAL = "http://"+IP_ADDRESS+"/adisti-jr/api/pengaju/downloadProposal/";
    public static final String URL_DOWNLOAD_FILE_SURVEY = "http://" + IP_ADDRESS +"/adisti-jr/api/pts/downloadfile/";
    public static final String URL_QR_CODE = "http://" + IP_ADDRESS + "/adisti-jr/uploads/qrcode/";
    public static final String URL_DOWLOAD_FILE_REALISASI = "http://" + IP_ADDRESS + "/adisti-jr/api/adminTjsl/downloadFileRealisasiBantuan/";
    public static final String URL_FILE_REALISASI = "http://" + IP_ADDRESS + "/adisti-jr/uploads/realisasi/";
    public static final String URL_FILE_LPJ_KEGIATAN = "http://" + IP_ADDRESS + "/adisti-jr/uploads/lpj_kegiatan/";
    public static  final String URL_DOWNLOAD_FILE_LPJ_KEGIATAN = "http://" + IP_ADDRESS +"/adisti-jr/api/admintjsl/downloadFileLpjKegiatan/";
    public static final String URL_DOWNLOAD_SURAT_KACAB = "http://" + IP_ADDRESS + "/adisti-jr/api/downloadSurat/cetak_surat/";

    public static final String URL_CETAK_LAPORAN_PROPOSAL = "http://" + IP_ADDRESS +"/adisti-jr/api/adminTjsl/cetak_laporan/";
    public static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
