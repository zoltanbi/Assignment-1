package ca.bcit.asn1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;


import com.google.gson.Gson;

public class GetNews extends AppCompatActivity {
    private ListView searchResultListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_news_layout);
        searchResultListView = findViewById(R.id.search_results);


        MyAsyncTask task = new MyAsyncTask();
        task.setKeyword(getIntent().getStringExtra(MainActivity.TAGHELPER));
        task.execute();
    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        private String keyword = "";
        private BaseArticles newsSearchResult;

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String url = String.format(
                    "https://newsapi.org/v2/everything?q=%s&sortBy=publishedAt&apiKey=b516a9c911354f97b24c0650bc5818b0",
                    this.keyword);
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Gson gson = new Gson();
            newsSearchResult = gson.fromJson(jsonStr, BaseArticles.class);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ArticleAdapter articleAdapter= new ArticleAdapter(GetNews.this, newsSearchResult.getArticles());
            searchResultListView.setAdapter(articleAdapter);
        }
    }
}
