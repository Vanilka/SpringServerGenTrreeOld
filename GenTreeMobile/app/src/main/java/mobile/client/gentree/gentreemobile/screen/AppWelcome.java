package mobile.client.gentree.gentreemobile.screen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import mobile.client.gentree.gentreemobile.R;
import mobile.client.gentree.gentreemobile.utilities.LoginHelper;

public class AppWelcome extends AppCompatActivity {

    private EditText loginField;
    private EditText passwordField;

    private TextView errorLabel;

    private Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_welcome);
        initComponents();
    }


    private void initComponents() {
        loginButton = (Button) findViewById(R.id.loginButton);
        loginField = (EditText) findViewById(R.id.loginField);
        passwordField = (EditText) findViewById(R.id.passwordField);
        errorLabel = (TextView) findViewById(R.id.errorLabel);


    }

    public void connect(View view) {
        String login = loginField.getText().toString();
        String password = passwordField.getText().toString();

        if (LoginHelper.isNotEmpty(login) && LoginHelper.isNotEmpty(password)) {
            errorLabel.setText("");
        } else {
            errorLabel.setText("ERROR OCCURED");
        }

    }

}
