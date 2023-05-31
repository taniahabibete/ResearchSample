package com.example.researchsample;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.researchsample.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddThesisActivity extends AppCompatActivity {
    private static final String TAG = StoreFragment.class.getSimpleName();
    private Spinner VCategory;
    private Spinner VProject;
    private Spinner VUser;

    private EditText  name, sampleDescripton, developedBy,patternType,url;

    private CircleImageView mPicture;
    private FloatingActionButton mFabChoosePic;

    Calendar myCalendar = Calendar.getInstance();

    private String VCategoryIndex = "CSE";
    private String VProjectIndex = "Thesis";
    private String VUserIndex = "Student";
    public static final String CSE = "CSE";
    public static final String EEE = "EEE";
    public static final String ETE = "ETE";
    public static final String Project = "Project";
    public static final String Thesis = "Thesis";
    public static final String Teacher = "Teacher";
    public static final String Student = "Student";

    //private String name, species, breed, picture, birth;
    private int id, gender;
    private HashMap<String, String> userProfile = new HashMap<>();
    String userUploadId;

    private Menu action;
    private Bitmap bitmap;

    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_thesis);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mPicture = findViewById(R.id.picture);
        mFabChoosePic = findViewById(R.id.fabChoosePic);
        name = findViewById(R.id.name);
        sampleDescripton = findViewById(R.id.sampleDescripton);
        developedBy = findViewById(R.id.developedBy);
        url = findViewById(R.id.url);
        VCategory = findViewById(R.id.department);
        VProject = findViewById(R.id.projectType);
        VUser = findViewById(R.id.userType);
        patternType = findViewById(R.id.patternType);


        DatabaseHandler dbProfile = new DatabaseHandler(AddThesisActivity.this);
        userProfile  = dbProfile.getUserDetails("uid");
        userUploadId = userProfile.get("uid");

        mFabChoosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });

        setupSpinner();


    }


    private void setupSpinner(){
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_gender_options, android.R.layout.simple_spinner_item);
        ArrayAdapter projectSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_project_options, android.R.layout.simple_spinner_item);
        ArrayAdapter userSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_user_options, android.R.layout.simple_spinner_item);
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        projectSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        userSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        VCategory.setAdapter(genderSpinnerAdapter);
        VProject.setAdapter(projectSpinnerAdapter);
        VUser.setAdapter(userSpinnerAdapter);

        VCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.CSE))) {
                        VCategoryIndex = CSE;
                    } else if (selection.equals(getString(R.string.EEE))) {
                        VCategoryIndex = EEE;
                    } else if (selection.equals(getString(R.string.ETE))) {
                        VCategoryIndex = ETE;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                VCategoryIndex = "CSE";


            }
        });
        VProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.Project))) {
                        VProjectIndex = Project;
                    } else if (selection.equals(getString(R.string.Thesis))) {
                        VProjectIndex = Thesis;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                VProjectIndex = "Thesis";


            }
        });
        VUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.Teacher))) {
                        VUserIndex = Teacher;
                    } else if (selection.equals(getString(R.string.Student))) {
                        VUserIndex = Student;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                VUserIndex = "Student";


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_editor, menu);
        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);

        if (id == 0){

            action.findItem(R.id.menu_edit).setVisible(false);
            // action.findItem(R.id.menu_delete).setVisible(false);
            action.findItem(R.id.menu_save).setVisible(true);

        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                this.finish();

                return true;

            case R.id.menu_save:

                if (id == 0) {

                    if (TextUtils.isEmpty(name.getText().toString()) ||
                            TextUtils.isEmpty(developedBy.getText().toString()) ){
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                        alertDialog.setMessage("Please complete the field!");
                        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialog.show();
                    }

                    else {

                        postData("insert");
                        action.findItem(R.id.menu_edit).setVisible(true);
                        action.findItem(R.id.menu_save).setVisible(false);
                        //action.findItem(R.id.menu_delete).setVisible(true);

                        // readMode();

                    }

                } else {

                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    private void setBirth() {
        String myFormat = "dd MMMM yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        //mBirth.setText(sdf.format(myCalendar.getTime()));
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                mPicture.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void postData(final String key) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        String vname = name.getText().toString().trim();
        String vsampleDescripton = sampleDescripton.getText().toString().trim();
        String vdevelopedBy = developedBy.getText().toString().trim();
        String vprojectType = VProjectIndex;
        String vdepartment = VCategoryIndex;
        String picture = null;
        if (bitmap == null) {
            picture = "";
        } else {
            picture = getStringImage(bitmap);
        }
        String vpatternType = patternType.getText().toString().trim();
        String vuserType = VUserIndex;
        String vUploadBy = userUploadId ;
        String weburl = url.getText().toString().trim() ;

        // String birth = mBirth.getText().toString().trim();


        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<AddThesis> call = apiInterface.insertThesis(key,vname, vsampleDescripton, vdevelopedBy,vprojectType,vdepartment,picture,vpatternType,vuserType,vUploadBy,weburl);

        call.enqueue(new Callback<AddThesis>() {
            @Override
            public void onResponse(Call<AddThesis> call, Response<AddThesis> response) {

                progressDialog.dismiss();

                Log.i(AddThesisActivity.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    Toast.makeText(AddThesisActivity.this, "Waiting For Approval" +
                            "", Toast.LENGTH_SHORT).show();
                    finish();
                    getCallingActivity();
                    Intent intent = new Intent(AddThesisActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(Call<AddThesis> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(AddThesisActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
 