package mobile.client.gentree.gentreemobile.screen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import mobile.client.gentree.gentreemobile.R;
import mobile.client.gentree.gentreemobile.configuration.utilities.LoginHelper;
import mobile.client.gentree.gentreemobile.rest.tasks.LoginTask;

public class AppWelcome extends AppCompatActivity {

    private EditText loginField;
    private EditText passwordField;
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
    }

    public void connect(View view) {
        String login = loginField.getText().toString();
        String password = passwordField.getText().toString();

        if (LoginHelper.isNotEmpty(login) && LoginHelper.isNotEmpty(password)) {
            doLogin(view, login, password);
        } else {
            Snackbar.make(view, "ERROR OCCURED", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }
    }

    private void doLogin(View view, String login, String password) {
        LoginTask lt = new LoginTask(view);
        lt.execute(login, password);

    }
}
