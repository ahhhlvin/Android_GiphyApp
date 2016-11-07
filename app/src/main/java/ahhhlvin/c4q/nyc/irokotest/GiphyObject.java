package ahhhlvin.c4q.nyc.irokotest;

/**
 * Created by alvin2 on 11/3/16.
 */

class GiphyObject {

    private String gifURL;
    private String highDefGifURL;

    GiphyObject() {
    }

    void setGifURL(String gifURL) {
        this.gifURL = gifURL;
    }

    String getGifURL() {
        return gifURL;
    }

    String getHighDefGifURL() {
        return highDefGifURL;
    }

    void setHighDefGifURL(String highDefGifURL) {
        this.highDefGifURL = highDefGifURL;
    }
}
