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


public class MyDocumentsAdapter extends RecyclerView.Adapter<MainProfileViewHolder>{


    List<ProfileItem> items ;
    Context context;

    public MyDocumentsAdapter(Context context) {
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


    private ArrayList<ProfileItem> initItems() {
        ArrayList items = new ArrayList();
        items.add(new ProfileItem(R.string.id_card, R.drawable.ic_id_card));
        items.add(new ProfileItem(R.string.bank_card, R.drawable.ic_bank_cards));
        items.add(new ProfileItem(R.string.driver_license, R.drawable.ic_driver_license));
        return items;
    }

    @Override
    public void onBindViewHolder(MainProfileViewHolder mainProfileViewHolder, int i) {
        ProfileItem profileItem = items.get(i);

        mainProfileViewHolder.itemText.setText(profileItem.getTitle());
        mainProfileViewHolder.itemIcon.setImageResource(profileItem.getIconResource());
        mainProfileViewHolder.id = i+1;

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    @Data
    private class ProfileItem {
        int title;
        int iconResource;

        public ProfileItem(int title, int iconResource){
            this.title = title;
            this.iconResource = iconResource;
        }
    }
}
