package com.example.researchsample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ProfileActivity extends AppCompatActivity implements StoreAdapter.MovieAdapterListener{
    private static final String TAG = StoreFragment.class.getSimpleName();
    private static final String URL = "http://minagazi.com/shamimsproject/api/SampleProjectListApi/fetch_uploader_row?id=";
    private static final String profileloadURL = "http://minagazi.com/shamimsproject/api/SampleProjectListApi/fetch_userProfile?id=";

    private HashMap<String, String> userProfile = new HashMap<>();
    String userUploadId;
    private RecyclerView recyclerView;
    private List<SampleList> movieList;
    private StoreAdapter mAdapter;

    EditText name,mail,gender,phoneno,userType;

    private ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        recyclerView = findViewById(R.id.recycler_view);
        movieList = new ArrayList<>();
        mAdapter = new StoreAdapter(getApplicationContext(), movieList,this);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new ProfileActivity.GridSpacingItemDecoration(2, dpToPx(8), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        DatabaseHandler dbProfile = new DatabaseHandler(ProfileActivity.this);
        userProfile  = dbProfile.getUserDetails("uid");
        userUploadId = userProfile.get("uid");

        name = findViewById(R.id.name);
        name.setEnabled(false);

        mail = findViewById(R.id.mail);
        mail.setEnabled(false);

        gender = findViewById(R.id.gender);
        gender.setEnabled(false);

        phoneno = findViewById(R.id.phoneno);
        phoneno.setEnabled(false);

        userType = findViewById(R.id.usertype);
        userType.setEnabled(false);

        fetchStoreItems(userUploadId);
        fetchProfileDetails(userUploadId);
    }

    private void fetchProfileDetails(String id) {
        JsonArrayRequest request = new JsonArrayRequest(profileloadURL+id,
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
                                name.setText(heroObject.getString("name"));
                                mail.setText(heroObject.getString("email"));
                                gender.setText(heroObject.getString("gender"));
                                userType.setText(heroObject.getString("usertype"));
                                phoneno.setText(heroObject.getString("phone_no"));

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

    private void fetchStoreItems(String id) {
        JsonArrayRequest request = new JsonArrayRequest(URL+id,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response == null) {
                            Toast.makeText(getApplicationContext(), "Couldn't fetch the store items! Pleas try again.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        List<SampleList> items = new Gson().fromJson(response.toString(), new TypeToken<List<SampleList>>() {
                        }.getType());

                        movieList.clear();
                        movieList.addAll(items);

                        // refreshing recycler view
                        mAdapter.notifyDataSetChanged();
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

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onMovieSelected(SampleList contact) {

        Intent mIntent = new Intent(getApplicationContext(),SampleListDetailsActivity.class);
        String getId = String.valueOf(contact.getId());
        mIntent.putExtra("pSampleId", getId);
        startActivity(mIntent);

    }

    private void updateData(final String key, final int id) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        //readMode();

        String uName = name.getText().toString().trim();
        String uGender = gender.getText().toString().trim();
        String phone_no = phoneno.getText().toString().trim();
        String uType = userType.getText().toString().trim();



        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<UpdateProfileModel> call = apiInterface.updateProfile(key, uName, uGender, phone_no, uType,userUploadId);



        call.enqueue(new Callback<UpdateProfileModel>() {
            @Override
            public void onResponse(Call<UpdateProfileModel> call, retrofit2.Response<UpdateProfileModel> response) {

                progressDialog.dismiss();

                Log.i(AddThesisActivity.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    Toast.makeText(ProfileActivity.this, "Waiting For Approval", Toast.LENGTH_SHORT).show();
                    finish();
                    getCallingActivity();
                    Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(Call<UpdateProfileModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ProfileActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}