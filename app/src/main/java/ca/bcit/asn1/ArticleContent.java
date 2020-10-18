package ca.bcit.asn1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.os.Bundle;

import com.google.gson.Gson;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ArticleContent extends AppCompatActivity {
    private Article article;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_info_layout);
       String articleInStr = getIntent().getStringExtra(MainActivity.TAGHELPER);
        if (articleInStr == null) {
            return;
        }

        Gson gson = new Gson();
        article = gson.fromJson(articleInStr, Article.class);

        image = findViewById(R.id.image);

        TextView title = findViewById(R.id.title);
        title.setText(article.getTitle());

        TextView source = findViewById(R.id.sourceName);
        source.setText(article.getName());

        TextView author = findViewById(R.id.author);
        author.setText(article.getAuthor());

        TextView body = findViewById(R.id.description);
        body.setText(article.getDescription());

        TextView url = findViewById(R.id.url);
        url.setText(article.getUrl());

        TextView publishedDate = findViewById(R.id.publishedAt);
        publishedDate.setText(article.getPublishedAt());

        TextView content = findViewById(R.id.content);
        content.setText(article.getContent());

        ImageDownloader downloader = new ImageDownloader();
        downloader.execute();
    }

    private class ImageDownloader extends AsyncTask<Void, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Void... arg0) {
            return downloadBitmap(article.getUrlToImage());
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            super.onPostExecute(bitmap);
            if (bitmap != null) {
                if (image != null) {
                    image.setImageBitmap(bitmap);
                }
            }
        }

        private Bitmap downloadBitmap(String url) {
            HttpURLConnection urlConnection = null;
            try {
                URL uri = new URL(url);
                urlConnection = (HttpURLConnection) uri.openConnection();
                int statusCode = urlConnection.getResponseCode();
                if (statusCode !=  HttpURLConnection.HTTP_OK) {
                    return null;
                }

                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream != null) {
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                }
            } catch (Exception e) {
                urlConnection.disconnect();
                Log.w("ImageDownloader", "Error downloading image from " + url);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }
    }
}