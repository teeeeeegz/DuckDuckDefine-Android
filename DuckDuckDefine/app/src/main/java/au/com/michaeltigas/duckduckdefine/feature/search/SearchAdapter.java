package au.com.michaeltigas.duckduckdefine.feature.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import au.com.michaeltigas.duckduckdefine.R;
import au.com.michaeltigas.duckduckdefine.injection.context.ActivityContext;
import au.com.michaeltigas.duckduckdefine.util.ViewUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Michael Tigas on 16/3/17.
 *
 * Generate custom views for the SearchActivity recycler adapter
 *
 * Depending on the type of View, various ViewHolders will be called to create various styled layouts
 */
public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // View Types
    private static final int TYPE_SEARCHTERM = 0;

    // Adapter item list
    private final List<String> items = new ArrayList<>();

    // Context from Search Activity
    private final Context parentClassContext;

    @Inject
    public SearchAdapter(@ActivityContext Context context) {
        parentClassContext = context;
    }

    /**
     * If the ViewType is of TYPE_SEARCHTERM, create a Search Term item view
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_SEARCHTERM) {
            View searchTermView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_searchterm, null);
            ViewUtil.setListLayoutBoundsMaxWidth(searchTermView);

            return new SearchTermViewHolder(searchTermView);
        }

        return null;
    }

    /**
     * If the Item Type is of TYPE_SEARCHTERM, bind the Search Term item view to the SearchTermViewHolder
     *
     * Manage view recycling here
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int itemType = getItemViewType(position);

        if (itemType == TYPE_SEARCHTERM) {
            ((SearchTermViewHolder) holder).setTitle(items.get(position));
        }
    }

    /**
     * Determine the type of view for an item in the adapter's ArrayList
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return TYPE_SEARCHTERM;
    }

    /**
     * Return adapter list
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * Add an item to the adapter
     *
     * @param term
     */
    public void insertItem(String term) {
        if (!itemAlreadyExists(term)) items.add(term.trim());
        notifyDataSetChanged();
    }

    /**
     * Check if string value already exists in adapter
     *
     * @param value
     * @return
     */
    private boolean itemAlreadyExists(String value) {
        for (String item : items) {
            if (TextUtils.equals(item.trim(), value.trim())) return true;
        }

        return false;
    }

    /**
     * ViewHolder inner class for views of type TYPE_SEARCHTERM
     */
    class SearchTermViewHolder extends RecyclerView.ViewHolder {
        private String term;

        @BindView(R.id.searchTerm) TextView searchTerm;

        public SearchTermViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setTitle(String term) {
            this.term = term;

            searchTerm.setText(this.term);
        }

        /**
         * Perform search for the term in this view
         */
        @OnClick(R.id.searchTermLayout)
        void parentLayoutClicked() {
            SearchActivity parentActivity = (SearchActivity) parentClassContext;
            parentActivity.performSearchForTerm(term);
        }
    }
}
