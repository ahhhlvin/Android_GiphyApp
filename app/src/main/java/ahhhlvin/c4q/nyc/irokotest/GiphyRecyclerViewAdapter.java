package ahhhlvin.c4q.nyc.irokotest;

/**
 * Created by alvin2 on 11/3/16.
 */
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

public class GiphyRecyclerViewAdapter extends RecyclerView.Adapter<GifImageViewHolders> {
    private List<GiphyObject> itemList;
    private Context context;

    private int visibleThreshold = 5;
    private int totalItemCount, visibleItemCount;
    private int[] firstVisibleItems;
    private int previousTotal = 0;
    private boolean loading = true;

    GiphyRecyclerViewAdapter(Context context, final List<GiphyObject> itemList, RecyclerView recyclerView) {
        this.itemList = itemList;
        this.context = context;

        if(recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            final StaggeredGridLayoutManager mStaggeredGridLayoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(final RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    visibleItemCount = mStaggeredGridLayoutManager.getChildCount();
                    totalItemCount = mStaggeredGridLayoutManager.getItemCount();
                    firstVisibleItems = mStaggeredGridLayoutManager.findFirstCompletelyVisibleItemPositions(null);

                    if (loading) {
                        if (totalItemCount > previousTotal) {
                            loading = false;
                            previousTotal = totalItemCount;
                        }
                    }
                    if (!loading && (totalItemCount - visibleItemCount)
                            <= (firstVisibleItems[0] + visibleThreshold)) {
//                        itemList.addAll(itemList);
//                        recyclerView.getAdapter().notifyDataSetChanged();
                        loading = true;
                    }
                }
            });
        }

    }

    @Override
    public GifImageViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gif_list, null);
        return new GifImageViewHolders(layoutView);
    }

    @Override
    public void onBindViewHolder(GifImageViewHolders holder, int position) {

        if (itemList.size() > 0) {
            holder.highDefGifURL = itemList.get(position).getHighDefGifURL();
            String gifURL = itemList.get(position).getGifURL();
            if (!gifURL.isEmpty()) {
                Glide.with(context).load(gifURL).fitCenter().error(R.drawable.loading).placeholder(R.drawable.loading).into(holder.gifImageView);
            }
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}


