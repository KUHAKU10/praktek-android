package com.heggies.belajarcrud;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TambahActivity extends Activity {
	
	private ProgressDialog pDialog;
	
	JSONParser jsonParser = new JSONParser();
	private static String url_tambah_siswa = "http://192.168.1.148/belajarcrud/create_siswa.php";
	
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_NISN= "nisn";
	private static final String TAG_NAMA = "nama";
	private static final String TAG_KELAS= "kelas";
	private static final String TAG_ALAMAT= "alamat";
	
	EditText editADDNISN, editADDNAMA, editADDkelas, editADDALAMAT;
	Button tambahdata, kembali;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tambah);
		
		editADDNISN = (EditText) findViewById(R.id.editNISN);
		editADDNAMA = (EditText) findViewById(R.id.editNAMA);
		editADDkelas = (EditText) findViewById(R.id.editKELAS);
		editADDALAMAT = (EditText) findViewById(R.id.editALAMAT);
		
		tambahdata = (Button) findViewById(R.id.btnTambahData);
		tambahdata.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new TambahSiswa().execute();
				
			}
		});
		kembali = (Button) findViewById(R.id.btnBatalkan);
		kembali.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent kembali = new Intent(TambahActivity.this, MainActivity.class);
				startActivity(kembali);
				
			}
		});
	}
	class TambahSiswa extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(TambahActivity.this);
			pDialog.setMessage("Sedang membuat pendaftaran...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
		@Override
		protected String doInBackground(String... params) {
			String nisn = editADDNISN.getText().toString();
			String nama = editADDNAMA.getText().toString();
			String kelas = editADDkelas.getText().toString();
			String alamat = editADDALAMAT.getText().toString();
			
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			
			param.add(new BasicNameValuePair(TAG_NISN, nisn));
			param.add(new BasicNameValuePair(TAG_NAMA, nama));
			param.add(new BasicNameValuePair(TAG_KELAS, kelas));
			param.add(new BasicNameValuePair(TAG_ALAMAT, alamat));
			
			JSONObject json = jsonParser.makeHTTPRequest(url_tambah_siswa, "POST", param);
			try{
				int success = json.getInt(TAG_SUCCESS);
				if(success == 1) {
					Intent tambah = new Intent(TambahActivity.this, MainActivity.class);
					startActivity(tambah);
				} else {
					
				}
			} catch(JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
		}
	}
	
}
