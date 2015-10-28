package com.kuhaku.crud;

import java.util.ArrayList;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
* @author kuhaku
*
*/

public class TambahBukuTamu extends Activity {

// Progress Dialog
private ProgressDialog pDialog;

JSONParser jsonParser = new JSONParser();
EditText inputName;
EditText inputEmail;
EditText inputDesc;

// url to membuat pendaftaran / bukutamu baru
private static String url_tambah_pendaftaran = "http://10.0.2.2/pendaftaran/create_pendaftaran.php";

private static final String TAG_SUCCESS = "success";

@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.tambah_bukutamu);

// Edit Text
inputName = (EditText) findViewById(R.id.inputName);
inputEmail = (EditText) findViewById(R.id.inputEmail);
inputDesc = (EditText) findViewById(R.id.inputDesc);

// button untuk buat pendaftaran baru
Button btnCreatePendaftaran = (Button) findViewById(R.id.btnCreatePendaftaran);

// event jika di klik
btnCreatePendaftaran.setOnClickListener(new View.OnClickListener() {

@Override
public void onClick(View view) {
// jalankan buat pendaftaran di background
new CreateNewpendaftaran().execute();
}
});
}

/**
* Background Async Task untuk membuat buku tamu baru
* */
class CreateNewpendaftaran extends AsyncTask<String, String, String> {

/**
* tampilkan progress dialog
* */
@Override
protected void onPreExecute() {
super.onPreExecute();
pDialog = new ProgressDialog(TambahBukuTamu.this);
pDialog.setMessage("Sedang membuat pendaftaran…");
pDialog.setIndeterminate(false);
pDialog.setCancelable(true);
pDialog.show();
}

/**
* buat bukutamu baru
* */
protected String doInBackground(String… args) {
String name = inputName.getText().toString();
String email = inputEmail.getText().toString();
String description = inputDesc.getText().toString();

// parameter
List<NameValuePair> params = new ArrayList<NameValuePair>();
params.add(new BasicNameValuePair("name", name));
params.add(new BasicNameValuePair("email", email));
params.add(new BasicNameValuePair("description", description));

// json object
JSONObject json = jsonParser.makeHttpRequest(url_tambah_pendaftaran,
"POST", params);

// cek respon di logcat
Log.d("Create Response", json.toString());

// cek tag success
try {
int success = json.getInt(TAG_SUCCESS);

if (success == 1) {
// sukses buat pendaftaran
Intent i = new Intent(getApplicationContext(), SemuaBukuTamu.class);
startActivity(i);

// tutup screen
finish();
} else {
// jika gagal
}
} catch (JSONException e) {
e.printStackTrace();
}

return null;
}

/**
* jika proses selesai maka hentikan progress dialog
* **/
protected void onPostExecute(String file_url) {
pDialog.dismiss();
}

@Override
protected String doInBackground(String... params) {
	// TODO Auto-generated method stub
	return null;
}

}
}