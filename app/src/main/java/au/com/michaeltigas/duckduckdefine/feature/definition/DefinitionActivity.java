package au.com.michaeltigas.duckduckdefine.feature.definition;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.fernandocejas.arrow.strings.Strings;
import com.squareup.moshi.Moshi;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

import javax.inject.Inject;

import au.com.michaeltigas.duckduckdefine.R;
import au.com.michaeltigas.duckduckdefine.data.remote.model.SearchDefinition;
import au.com.michaeltigas.duckduckdefine.feature.common.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Michael Tigas on 15/3/17.
 *
 * This activity displays information relating to a returned Definition
 */
public class DefinitionActivity extends BaseActivity {
    @Inject Moshi moshi;
    @Inject Picasso picasso;

    @BindView(R.id.definitionToolbar)           Toolbar toolbar;
    @BindView(R.id.definitionImageView)         AppCompatImageView imageView;
    @BindView(R.id.definitionDescriptionLabel)  TextView descriptionLabel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition);
        ButterKnife.bind(this);
        getActivityComponent().inject(this);
        init();
    }

    private void init() {
        initialiseToolbar();
        displayDefinitionDetails();
    }

    private void initialiseToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Populate definition details
     */
    private void displayDefinitionDetails() {
        String definitionIntentExtra = getIntent().getStringExtra("definition");

        if (Strings.isNullOrEmpty(definitionIntentExtra)) {
            finish();
            return;
        }

        // Parse JSON to SearchDefinition POJO
        SearchDefinition searchDefinition;
        try {
            searchDefinition = moshi.adapter(SearchDefinition.class).fromJson(definitionIntentExtra);
        } catch (IOException e) {
            e.printStackTrace();
            finish();
            return;
        }


        getSupportActionBar().setTitle(searchDefinition.heading);
        descriptionLabel.setText(searchDefinition.abstractText);

        if (!Strings.isNullOrEmpty(searchDefinition.imageUrl)){
            picasso.load(searchDefinition.imageUrl)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        RoundedBitmapDrawable circleCropBitmap = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                        circleCropBitmap.setCircular(true);
                        imageView.setImageDrawable(circleCropBitmap);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Respond to the action bar's Up/Home button in the same way as the Back button
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
