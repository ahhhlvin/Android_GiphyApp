package ahhhlvin.c4q.nyc.irokotest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class GifActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);

        ImageView soloGifImageView = (ImageView) findViewById(R.id.soloGifImageView);
        Glide.with(this).load(getIntent().getStringExtra("HD_GIF_URL")).fitCenter().error(R.drawable.loading).placeholder(R.drawable.loading).into(soloGifImageView);

    }
}
