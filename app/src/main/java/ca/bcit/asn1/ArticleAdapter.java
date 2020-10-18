package ca.bcit.asn1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ArticleAdapter extends ArrayAdapter<Article> {
    Context _context;
    public ArticleAdapter(Context context, ArrayList<Article> articles) {
        super(context, 0, articles);
        _context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Activity activity = (Activity) _context;
        // Get the data item for this position
        final Article article = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.article_list_layout, parent, false);
        }

        // Lookup view for data population
        // Populate the data into the template view using the data object
        TextView textView = convertView.findViewById(R.id.article_title);
        if (article.getTitle() != null) {
            textView.setText(article.getTitle());
        } else {
            //textView.setText(R.string.default_title);
            textView.setText("Title");
        }

        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(_context, ArticleContent.class);
                Gson gson = new Gson();
                String articleInStr = gson.toJson(article, Article.class);
                i.putExtra(GetNews.ARTICLE, articleInStr);
                _context.startActivity(i);
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }
}

