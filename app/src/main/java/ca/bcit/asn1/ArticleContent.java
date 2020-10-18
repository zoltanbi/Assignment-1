package ca.bcit.asn1;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;

import com.google.gson.Gson;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ArticleContent extends AppCompatActivity {
    private Article article;
    private ImageView articleImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_article_details_needschange);
        String articleInStr = getIntent().getStringExtra(GetNews.ARTICLE);
        if (articleInStr == null) {
            return;
        }

        Gson gson = new Gson();
        article = gson.fromJson(articleInStr, Article.class);

        articleImage = findViewById(R.id.article_image);

        TextView articleTitle = findViewById(R.id.article_title);
        articleTitle.setText(article.getTitle());

        TextView articleAuthor = findViewById(R.id.article_author);
        articleAuthor.setText(article.getAuthor());

        TextView articleBody = findViewById(R.id.article_body);
        articleBody.setText(article.getContent());

        TextView articleDescription = findViewById(R.id.article_description);
        articleDescription.setText(article.getDescription());

        TextView articlePublishedDate = findViewById(R.id.article_publish_date);
        articlePublishedDate.setText(article.getPublishedAt());

        TextView articleSource = findViewById(R.id.article_source);
        articleSource.setText(article.getName());

        TextView articleSourceURL = findViewById(R.id.source_url);
        articleSourceURL.setText(article.getUrl());

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
                if (articleImage != null) {
                    articleImage.setImageBitmap(bitmap);
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