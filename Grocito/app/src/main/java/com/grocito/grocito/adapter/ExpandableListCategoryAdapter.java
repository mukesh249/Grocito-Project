package com.grocito.grocito.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import com.grocito.grocito.model.CategoryModelE;
import com.grocito.grocito.model.SubCategoryModelE;
import com.grocito.grocito.R;

public class ExpandableListCategoryAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<CategoryModelE> listDataHeader;
    private HashMap<CategoryModelE, List<SubCategoryModelE>> listDataChild;

    public ExpandableListCategoryAdapter(Context context, List<CategoryModelE> listDataHeader,
                                         HashMap<CategoryModelE, List<SubCategoryModelE>> listChildData) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
    }

    @Override
    public SubCategoryModelE getChild(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = getChild(groupPosition, childPosition).getMenuName();
        final int image_icon = getChild(groupPosition, childPosition).getImage();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_submenu, null);
        }

        TextView txtListChild = convertView.findViewById(R.id.submenu);
        ImageView imageView = convertView.findViewById(R.id.iconimage);
        imageView.setImageResource(image_icon);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        if (this.listDataChild.get(this.listDataHeader.get(groupPosition)) == null)
            return 0;
        else
            return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                    .size();
    }

    @Override
    public CategoryModelE getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();

    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = getGroup(groupPosition).getMenuName();
        int image_icon = getGroup(groupPosition).getImage();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.listheader, null);
        }

        TextView lblListHeader = convertView.findViewById(R.id.submenu);
        ImageView imageView = convertView.findViewById(R.id.iconimage);
        ImageView side_menu_dropicon = convertView.findViewById(R.id.endicon);
        if (headerTitle.compareTo(context.getResources().getString(R.string.my_account))==0){
            side_menu_dropicon.setVisibility(View.GONE);
        }else {
            side_menu_dropicon.setVisibility(View.GONE);
        }
        imageView.setImageResource(image_icon);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}