package ahhhlvin.c4q.nyc.irokotest;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by alvin2 on 11/3/16.
 */

class GifImageViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView gifImageView;
    String highDefGifURL;

    GifImageViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        gifImageView = (ImageView) itemView.findViewById(R.id.gifImageView);
    }

    @Override
    public void onClick(View view) {
        Intent gifActivityIntent = new Intent(view.getContext(), GifActivity.class);
        gifActivityIntent.putExtra("HD_GIF_URL", highDefGifURL);
        view.getContext().startActivity(gifActivityIntent);
    }
}
