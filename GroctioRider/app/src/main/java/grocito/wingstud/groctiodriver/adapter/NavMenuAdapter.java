package grocito.wingstud.groctiodriver.adapter;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import grocito.wingstud.groctiodriver.R;
import grocito.wingstud.groctiodriver.databinding.NavMenuItemBinding;


/**
 * Created by Nss Solutions on 17-11-2016.
 */

public class NavMenuAdapter extends RecyclerView.Adapter<NavMenuAdapter.DrawerViewHolder> {

    private String[] drawerMenuList;
    private Context mContext;

    public NavMenuAdapter(Context mContext, String[] drawerMenuList) {
        this.mContext = mContext;
        this.drawerMenuList = drawerMenuList;
    }

    @Override
    public DrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NavMenuItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.nav_menu_item, parent, false);
        return new DrawerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(DrawerViewHolder holder, int position) {
        String menuNmae = drawerMenuList[position];
        holder.binding.txtNavItemTitle.setText(menuNmae);
//        if (menuNmae.equalsIgnoreCase(mContext.getResources().getString(R.string.profile)))
//            holder.imvNavItemIcon.setImageResource(R.drawable.ic_person);
//        else if (menuNmae.equalsIgnoreCase(mContext.getResources().getString(R.string.orders_history)))
//            holder.imvNavItemIcon.setImageResource(R.drawable.ic_history);
//        else if (menuNmae.equalsIgnoreCase(mContext.getResources().getString(R.string.terms)))
//            holder.imvNavItemIcon.setImageResource(R.drawable.ic_terms);
//        else if (menuNmae.equalsIgnoreCase(mContext.getResources().getString(R.string.help)))
//            holder.imvNavItemIcon.setImageResource(R.drawable.ic_help);
//        else if (menuNmae.equalsIgnoreCase(mContext.getResources().getString(R.string.logout)))
//            holder.imvNavItemIcon.setImageResource(R.drawable.ic_logout);

    }

    @Override
    public int getItemCount() {
        return drawerMenuList.length;
    }

    class DrawerViewHolder extends RecyclerView.ViewHolder {

        private NavMenuItemBinding binding;

        public DrawerViewHolder(final NavMenuItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
