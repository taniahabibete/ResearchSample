package com.example.researchsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SampleListDetailsActivity extends AppCompatActivity {
    public static String sampleSingleDetailUrl = "http://minagazi.com/shamimsproject/api/SampleProjectListApi/fetch_row?id=";
    public TextView sampleTitle,uploadDate,developer,projecttype,sampleDescription,userType,patternType,department;
    Bundle i;
    String id;
    ImageView imgVw;
    Button linkBtn;
    private static final String TAG = SampleListDetailsActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_list_details);

        i = getIntent().getExtras();
        id = i.getString("pSampleId");
        sampleTitle = findViewById(R.id.sample_title);
        uploadDate = findViewById(R.id.uploaddate);
        developer = findViewById(R.id.developer);
        projecttype = findViewById(R.id.projecttype);
        sampleDescription = findViewById(R.id.sampleDescription);
        department = findViewById(R.id.department);
        patternType = findViewById(R.id.patternType);
        userType = findViewById(R.id.userType);
        imgVw = findViewById(R.id.recipe_image);
        linkBtn = findViewById(R.id.view_button);

        fetchSampleDetails(id);

    }

    private void fetchSampleDetails(String id) {
        JsonArrayRequest request = new JsonArrayRequest(sampleSingleDetailUrl+id,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG,response.toString());
                        if (response == null) {
                            Toast.makeText(getApplicationContext(), "Couldn't fetch the store items! Pleas try again.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject heroObject = (JSONObject) response.get(i);
                                sampleTitle.setText(heroObject.getString("name"));
                                uploadDate.setText("Upload Date --> "+heroObject.getString("uploadDate"));
                                developer.setText("Owner --> "+heroObject.getString("developedBy"));
                                projecttype.setText("Project/Thesis type --> "+heroObject.getString("projectType"));
                                sampleDescription.setText("Description --> "+heroObject.getString("sampleDescripton"));
                                userType.setText("Status --> "+heroObject.getString("userType"));
                                patternType.setText("Genere --> "+heroObject.getString("patternType"));
                                department.setText("Department --> "+heroObject.getString("department"));
                                Glide.with(getApplicationContext())
                                        .load("http://minagazi.com/shamimsproject/app/upload/"+heroObject.getString("imageUrl"))
                                        .into(imgVw);

                                linkBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Intent internetIntent = null;
                                        try {
                                            internetIntent = new Intent(Intent.ACTION_VIEW,
                                                    Uri.parse(heroObject.getString("url")));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        internetIntent.setComponent(new ComponentName("com.android.browser","com.android.browser.BrowserActivity"));
                                        internetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        getApplicationContext().startActivity(internetIntent);

                                    }
                                });
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error in getting json
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        MyApplication.getInstance().addToRequestQueue(request);
    }
}