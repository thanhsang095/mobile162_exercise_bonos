package com.developer.sangbarca.currencyexchange;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Spinner spinnerNal, spinnerNalTo;
    ListView lvNal;

    EditText edtMoney;
    Button btnConV;
    TextView txtKQ;

    ArrayList<National> arrayQG = new ArrayList<>();
    ArrayList<National> arrayVN = new ArrayList<>();
    SpinnerAdapter adapter, adapterTo;

    int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        GetDataVolley();
    }

    private void GetDataVolley() {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://www.floatrates.com/daily/vnd.json";
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Lấy tỉ giá usd, cái khác tương tự
                            JSONObject usd = response.getJSONObject("usd");
                            final Double usd_t = 1/Double.valueOf(usd.getString("rate"));
                            arrayQG.add(new National("Đôla Mỹ", usd_t , R.drawable.usa));

                            JSONObject gbp = response.getJSONObject("gbp");
                            final Double gbp_t = 1/Double.valueOf(gbp.getString("rate"));
                            arrayQG.add(new National("Bảng Anh", gbp_t, R.drawable.england));

                            JSONObject jpy = response.getJSONObject("jpy");
                            final Double jpy_t = 1/Double.valueOf(jpy.getString("rate"));
                            arrayQG.add(new National("Yên Nhật", jpy_t, R.drawable.japan));

                            final JSONObject cad = response.getJSONObject("cad");
                            final Double cad_t = 1/Double.valueOf(cad.getString("rate"));
                            arrayQG.add(new National("Canada", cad_t, R.drawable.canada));

                            JSONObject aud = response.getJSONObject("aud");
                            final Double aud_t = 1/Double.valueOf(aud.getString("rate"));
                            arrayQG.add(new National("Đôla Úc", aud_t, R.drawable.australia));

                            arrayVN.add(new National("Việt Nam", 0, R.drawable.vietnam));

                            adapter = new SpinnerAdapter(MainActivity.this, R.layout.dong_nation, arrayQG);
                            adapterTo = new SpinnerAdapter(MainActivity.this, R.layout.dong_nation, arrayVN);

                            spinnerNal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    index = position;
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            //Currency Convert
                            btnConV.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int so = Integer.parseInt(edtMoney.getText().toString().trim());
                                    DecimalFormat dinhDang = new DecimalFormat("###,###");
                                    switch (index){
                                        case 0:
                                            txtKQ.setText(dinhDang.format(so * usd_t) + " VNĐ");
                                            break;
                                        case 1:
                                            txtKQ.setText(dinhDang.format(so * gbp_t) + " VNĐ");
                                            break;
                                        case 2:
                                            txtKQ.setText(dinhDang.format(so * jpy_t) + " VNĐ");
                                            break;
                                        case 3:
                                            txtKQ.setText(dinhDang.format(so * cad_t) + " VNĐ");
                                            break;
                                        case 4:
                                            txtKQ.setText(dinhDang.format(so * aud_t) + " VNĐ");
                                            break;
                                        default:

                                    }
                                }
                            });


                            spinnerNal.setAdapter(adapter);
                            spinnerNalTo.setAdapter(adapterTo);
                            lvNal.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonRequest);
    }

    private void AnhXa(){
        spinnerNal = (Spinner) findViewById(R.id.spinnerFrom);
        spinnerNalTo = (Spinner) findViewById(R.id.spinnerTo);
        lvNal = (ListView) findViewById(R.id.listViewQG);
        edtMoney = (EditText) findViewById(R.id.editTextMoney);
        btnConV = (Button) findViewById(R.id.buttonConvert);
        txtKQ = (TextView) findViewById(R.id.textViewKQ);
    }
}
