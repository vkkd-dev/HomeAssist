package android.example.house_assist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity_RegisterServiceProvider extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView serviceprovider_Login;
    private EditText serviceprovider_name,serviceprovider_email,serviceprovider_mobile,serviceprovider_password;
    private Button serviceprovider_SignUp;
    FirebaseAuth mAuth;
    String name, email, password, phone;
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__register_service_provider);
        // Intialize
        serviceprovider_Login = findViewById(R.id.register_serviceprovider_LogIn);
        toolbar = findViewById(R.id.sp_activity_register_toolbar);
        serviceprovider_name = findViewById(R.id.register_serviceprovider_name);
        serviceprovider_mobile = findViewById(R.id.register_serviceprovider_mobile);
        serviceprovider_email = findViewById(R.id.register_serviceprovider_email);
        serviceprovider_password = findViewById(R.id.register_serviceprovider_password);
        serviceprovider_SignUp = findViewById(R.id.register_serviceprovider_signUp);

        //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
        //Toolbar Intialize
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
        //Events
        serviceprovider_Login.setOnClickListener(v -> startActivity(new Intent(Activity_RegisterServiceProvider.this,Activity_LogInServiceProvider.class)));
        //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
        serviceprovider_SignUp.setOnClickListener(v -> {
            if(validate())
            {
                name = serviceprovider_name.getText().toString().trim();
                email = serviceprovider_email.getText().toString().trim();
                password = serviceprovider_password.getText().toString().trim();
                phone = serviceprovider_mobile.getText().toString().trim();

                mAuth = FirebaseAuth.getInstance();
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                   if (task.isSuccessful()){
                       Intent intent = new Intent(Activity_RegisterServiceProvider.this, Activity_ServiceProviderDetails.class);
                       /*intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                       intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);*/
                       intent.putExtra("name", name);
                       intent.putExtra("email", email);
                       intent.putExtra("phone", phone);
                       startActivity(intent);
                       Toast.makeText(Activity_RegisterServiceProvider.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
                   } else {
                       Toast.makeText(Activity_RegisterServiceProvider.this, "Sorry! Failed to register", Toast.LENGTH_SHORT).show();
                   }
                }).addOnFailureListener(e -> {
                    Toast.makeText(Activity_RegisterServiceProvider.this, e.toString(), Toast.LENGTH_SHORT).show();
                });

                /*Intent intent = new Intent(Activity_RegisterServiceProvider.this,Activity_OTPServiceProvider.class);
                intent.putExtra("name",serviceprovider_name.getText().toString().trim());
                intent.putExtra("mobile",serviceprovider_mobile.getText().toString().trim());
                intent.putExtra("email",serviceprovider_email.getText().toString().trim());
                intent.putExtra("password",serviceprovider_password.getText().toString().trim());
                intent.putExtra("type","OTP");
                startActivity(intent);*/

            }

        });
    }




    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//


    private boolean validate()
    {
        boolean istrue = true;
        if(serviceprovider_name.getText().toString().equals(""))
        {
            serviceprovider_name.setError("Please enter name.");
            serviceprovider_name.requestFocus();
            istrue = false;
        }

        if(serviceprovider_mobile.getText().toString().equals(""))
        {
            serviceprovider_mobile.setError("Please enter mobile no.");
            serviceprovider_mobile.requestFocus();
            istrue = false;
        }
        if(serviceprovider_mobile.getText().toString().length()!=10)
        {
            serviceprovider_mobile.setError("Please enter valid mobile no.");
            serviceprovider_mobile.requestFocus();
            istrue = false;
        }
        if(serviceprovider_email.getText().toString().equals(""))
        {
            serviceprovider_email.setError("Please enter email.");
            serviceprovider_email.requestFocus();
            istrue = false;
        }
        if(serviceprovider_email.getText().toString().matches("^[\\\\w!#$%&’*+/=?`{|}~^-]+(?:\\\\.[\\\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,6}$"))
        {
            serviceprovider_email.setError("Please enter valid email.");
            serviceprovider_email.requestFocus();
            istrue = false;
        }
        if(serviceprovider_password.getText().toString().equals(""))
        {
            serviceprovider_password.setError("Please enter password.");
            serviceprovider_password.requestFocus();
            istrue = false;
        }
        if(serviceprovider_password.getText().toString().length()<5)
        {
            serviceprovider_password.setError("Please enter valid password(minimum 6 alphanumeric).");
            serviceprovider_password.requestFocus();
            istrue = false;
        }
        return istrue;
    }
}
