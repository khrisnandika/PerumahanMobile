package com.example.perumahan.Config;

public class URLs {
    private static final String ROOT_URL = "http://10.0.2.2:80/api/registrationapi.php?apicall=";
    public static final String URL_REGISTER = ROOT_URL + "signup";
    public static final String URL_LOGIN= ROOT_URL + "login";
    public static final String SIMPAN_URL = "http://10.0.2.2:80/api/upload.php";
    public static final String TAMPIL_URL = "http://10.0.2.2:80/api/read_data.php";
    public static final String UPDATE_URL = "http://10.0.2.2:80/api/ubah.php";
    public static final String HAPUS_URL = "http://10.0.2.2:80/api/hapus.php";
    public static final String UBAHSANDI_URL = "http://10.0.2.2:80/api/ubah_password.php";
}
