package co.wouri.libreexchange.uis.account.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import co.wouri.libreexchange.R;
import co.wouri.libreexchange.uis.account.viewholders.MainProfileViewHolder;
import lombok.Data;


public class MainProfileAdapter extends RecyclerView.Adapter<MainProfileViewHolder>{


    List<MainProfileItem> items ;
    Context context;

    public MainProfileAdapter(Context context) {
        super();
        this.context = context;
        items = initItems();

    }

    @Override
    public MainProfileViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_main_profile_item, null);
        MainProfileViewHolder cv = new MainProfileViewHolder(context, v);
        return cv;
    }


    private ArrayList<MainProfileItem> initItems() {
        ArrayList items = new ArrayList();
        items.add(new MainProfileItem(R.string.personal_information, R.drawable.ic_neutral_user));
        items.add(new MainProfileItem(R.string.my_documents, R.drawable.ic_document));
        return items;
    }

    @Override
    public void onBindViewHolder(MainProfileViewHolder mainProfileViewHolder, int i) {
        MainProfileItem mainProfileItem = items.get(i);

        mainProfileViewHolder.itemText.setText(mainProfileItem.getTitle());
        mainProfileViewHolder.itemIcon.setImageResource(mainProfileItem.getIconResource());
        mainProfileViewHolder.id = i+1;

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    @Data
    private class MainProfileItem {
        int title;
        int iconResource;

        public MainProfileItem(int title, int iconResource){
            this.title = title;
            this.iconResource = iconResource;
        }
    }
}
