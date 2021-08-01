package sg.edu.np.mad.remembertodo;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    TextView creditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        creditText = findViewById(R.id.creditText);

        String creditString = "Music Credits: \nDova - Syndrome: Music Provider Site \nしゃろう / Sharou: Composer Page";

        SpannableString ss = new SpannableString(creditString);

        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Uri uriUrl1 = Uri.parse("https://dova-s.jp/");
                Intent launchBrowser1 = new Intent(Intent.ACTION_VIEW, uriUrl1);
                startActivity(launchBrowser1);
            }
        };

        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Uri uriUrl2 = Uri.parse("https://dova-s.jp/_contents/author/profile106.html");
                Intent launchBrowser2 = new Intent(Intent.ACTION_VIEW, uriUrl2);
                startActivity(launchBrowser2);
            }
        };

        ss.setSpan(clickableSpan1, 33, 52, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(clickableSpan2, 69, 82, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        creditText.setText(ss);
        creditText.setMovementMethod(LinkMovementMethod.getInstance());

        findViewById(R.id.about_act_back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
