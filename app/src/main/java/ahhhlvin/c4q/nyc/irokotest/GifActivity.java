package ahhhlvin.c4q.nyc.irokotest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import pl.droidsonroids.gif.GifImageView;

public class GifActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);

        GifImageView soloGifImageView = (GifImageView) findViewById(R.id.soloGifImageView);
        Glide.with(this).load(getIntent().getStringExtra("HD_GIF_URL")).fitCenter().error(R.drawable.loading).placeholder(R.drawable.loading).into(soloGifImageView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_gif, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.share_button:
                shareGif();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void shareGif() {

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
//        share.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.putExtra(Intent.EXTRA_TEXT, getIntent().getStringExtra("HD_GIF_URL"));
        startActivity(Intent.createChooser(shareIntent, "Share GIF!"));
    }
}
