package com.ataulm.basic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ataulm.vpa.ViewPagerAdapter;

public class MyActivity extends Activity {

    private ViewPager viewPager;
    private View[] tabs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        tabs = new View[]{findViewById(R.id.tab_one), findViewById(R.id.tab_two), findViewById(R.id.tab_three)};
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new PagesAdapter(getLayoutInflater()));
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                activateTabAtPosition(position);
            }
        });

        showPage(0);
    }

    public void showPageOne(View view) {
        showPage(0);
    }

    public void showPageTwo(View view) {
        showPage(1);
    }

    public void showPageThree(View view) {
        showPage(2);
    }

    private void showPage(int page) {
        viewPager.setCurrentItem(page);
        activateTabAtPosition(page);
    }

    private void activateTabAtPosition(int page) {
        for (int i = 0; i < tabs.length; i++) {
            tabs[i].setActivated(i == page);
        }
    }

    private static class FixedFocusGridLayoutManager extends GridLayoutManager {

        public FixedFocusGridLayoutManager(Context context, int spanCount) {
            super(context, spanCount);
        }

        @Override
        public View onInterceptFocusSearch(View currentFocusedView, int direction) {
            int position = getPosition(currentFocusedView);

            switch (direction) {

                case View.FOCUS_LEFT:
                    return nextFocusableViewOnLeft(currentFocusedView, position);

                case View.FOCUS_RIGHT:
                    return nextFocusableViewOnRight(currentFocusedView, position);

                default:
                    return super.onInterceptFocusSearch(currentFocusedView, direction);

            }
        }

        private View nextFocusableViewOnRight(View currentFocusedView, int position) {
            if (position == getItemCount() - 1) {
                return super.onInterceptFocusSearch(currentFocusedView, View.FOCUS_RIGHT);
            }
            return getChildAt(position + 1);
        }

        private View nextFocusableViewOnLeft(View currentFocusedView, int position) {
            if (position == 0) {
                return super.onInterceptFocusSearch(currentFocusedView, View.FOCUS_LEFT);
            }
            return getChildAt(position - 1);
        }

    }

    private static class PagesAdapter extends ViewPagerAdapter {

        private static final int PAGE_COUNT = 3;
        private static final int ITEMS_IN_ROW_SIZE = 3;

        private final LayoutInflater layoutInflater;

        PagesAdapter(LayoutInflater layoutInflater) {
            this.layoutInflater = layoutInflater;
        }

        @Override
        protected View getView(ViewGroup viewGroup, int i) {
            RecyclerView recyclerView = (RecyclerView) layoutInflater.inflate(R.layout.view_page, viewGroup, false);
            recyclerView.setLayoutManager(new FixedFocusGridLayoutManager(viewGroup.getContext(), ITEMS_IN_ROW_SIZE));
            recyclerView.setAdapter(generateItemAdapter(i));
            return recyclerView;
        }

        private ItemAdapter generateItemAdapter(int page) {
            switch (page) {
                case 0:
                    return new ItemAdapter(15, layoutInflater);
                case 1:
                    return new ItemAdapter(15, layoutInflater);
                case 2:
                    return new ItemAdapter(7, layoutInflater);
                default:
                    throw new RuntimeException("unknown page: " + page);
            }
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

    }

    private static class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {

        private final int size;
        private final LayoutInflater layoutInflater;

        ItemAdapter(int size, LayoutInflater layoutInflater) {
            this.size = size;
            this.layoutInflater = layoutInflater;
        }

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = layoutInflater.inflate(R.layout.view_item, parent, false);
            return new ItemViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, int position) {
            ((TextView) holder.itemView).setText(String.valueOf(position));
        }

        @Override
        public int getItemCount() {
            return size;
        }

    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {

        public ItemViewHolder(View itemView) {
            super(itemView);
        }

    }

}
