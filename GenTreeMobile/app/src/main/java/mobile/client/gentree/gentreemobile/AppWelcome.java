package mobile.client.gentree.gentreemobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import mobile.client.gentree.gentreemobile.configuration.utilities.LoginHelper;

public class AppWelcome extends AppCompatActivity {

    private EditText loginField;
    private EditText passwordField;
    private TextView ERROR_LABEL;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_welcome);
        initialize();
    }

    private void initialize() {
        loginField = (EditText) findViewById(R.id.loginField);
        passwordField = (EditText) findViewById(R.id.passwordField);
        loginButton = (Button) findViewById(R.id.loginButton);
        ERROR_LABEL = (TextView) findViewById(R.id.errorLabel);
    }

    public void connect(View view) {
        String login = loginField.getText().toString();
        String password = passwordField.getText().toString();

        if (LoginHelper.isNotEmpty(login) && LoginHelper.isNotEmpty(password)) {
            ERROR_LABEL.setText("");
            
        } else {
            ERROR_LABEL.setText("ERROR OCCURED");

        }
    }
}
