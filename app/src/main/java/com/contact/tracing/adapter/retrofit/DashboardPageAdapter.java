package com.contact.tracing.adapter.retrofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.contact.tracing.R;
import com.contact.tracing.adapter.DashboardAdapter;

import org.jetbrains.annotations.NotNull;

public class DashboardPageAdapter extends PagerAdapter {

    private final Context dashboardContext;

    public DashboardPageAdapter(Context dashboardContext) {
        this.dashboardContext = dashboardContext;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(@NonNull @NotNull View view, @NonNull @NotNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(@NonNull @NotNull ViewGroup container, int position, @NonNull @NotNull Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @NonNull
    @NotNull
    @Override
    public Object instantiateItem(@NonNull @NotNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(dashboardContext);
        View recycleViewAdapter = layoutInflater.inflate(R.layout.item_patient_list, container, false);


        RecyclerView recyclerView = recycleViewAdapter.findViewById(R.id.rv);
        DashboardAdapter dashboardAdapter = new DashboardAdapter(recycleViewAdapter.getContext());

        recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(
                                dashboardContext, LinearLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(dashboardAdapter);
        container.addView(recycleViewAdapter);
        return recycleViewAdapter;
    }
}
