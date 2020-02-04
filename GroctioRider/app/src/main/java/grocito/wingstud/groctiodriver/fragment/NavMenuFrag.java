package grocito.wingstud.groctiodriver.fragment;


import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import grocito.wingstud.groctiodriver.Common.Constrants;
import grocito.wingstud.groctiodriver.Common.SharedPrefManager;
import grocito.wingstud.groctiodriver.R;
import grocito.wingstud.groctiodriver.adapter.NavMenuAdapter;
import grocito.wingstud.groctiodriver.bean.UserBean;
import grocito.wingstud.groctiodriver.databinding.FragmentNavMenuBinding;
import grocito.wingstud.groctiodriver.util.SharedPref;
import grocito.wingstud.groctiodriver.util.Utils;
import grocito.wingstud.groctiodriver.util.recycler_view_utilities.RecyclerItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavMenuFrag extends Fragment implements View.OnClickListener {

    private FrameLayout fLayoutProfileNav;

    private View view;

    private NavMenuAdapter adapter;

    private FragmentNavMenuBinding binding;

    private FragmentDrawerListener drawerListener;

    private DrawerLayout mDrawerLayout;
    private View containerView;

    private ActionBarDrawerToggle mDrawerToggle;

    private String[] navigationDrawerItems;

    private UserBean userBean;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_nav_menu, container, false);
        view = binding.getRoot();

        initialize();
        return view;
    }

    private void initialize() {
        navigationDrawerItems = getResources().getStringArray(R.array.nav_drawer_items);

        binding.rvNavMenuList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new NavMenuAdapter(getActivity(), navigationDrawerItems);
        binding.rvNavMenuList.setAdapter(adapter);

        binding.rvNavMenuList.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                drawerListener.onDrawerItemSelected(view, navigationDrawerItems[position]);
                mDrawerLayout.closeDrawer(containerView);
            }
        }));

        binding.relProfileSideBar.setOnClickListener(this);
    }

    private void setData() {
        userBean = (UserBean) Utils.getJsonToClassObject(SharedPref.getUserModelJSON(getActivity()), UserBean.class);
        binding.txtUsername.setText(SharedPrefManager.getUserName(Constrants.UserName));
        if (userBean != null) {

            if (!TextUtils.isEmpty(userBean.getDriver_imageUrl()))
                Utils.setImage(getActivity(), binding.imvUserImage, userBean.getDriver_imageUrl());
        }
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
                setData();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int vId = view.getId();
        if (vId == R.id.relProfileSideBar) {
            drawerListener.onDrawerItemSelected(view, navigationDrawerItems[0]);
            mDrawerLayout.closeDrawer(containerView);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, String menuName);
    }

}
