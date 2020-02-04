package com.grocito.grocito.common;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import com.grocito.grocito.activities.Payment;
import com.grocito.grocito.R;
import com.grocito.grocito.utils.Utils;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    Date selected_date;
    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

//        dialog = dialogSettings.dialogSettings();

//        bottomSheetLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(dialog.getContext()),R.layout.bottom_sheet_layout,null,false);
////        bottomSheetLayoutBinding.setMain(this);
//        dialog.setContentView(bottomSheetLayoutBinding .getRoot());

//        //Set the custom view
        View view = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_layout, null);
        dialog.setContentView(view);



        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        selected_date = Calendar.getInstance().getTime();
//        selected_date.add(Calendar.MONTH, -1);

        ((GradientDrawable)view.getBackground()).setColor(getResources().getColor(R.color.white));
        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(view,R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                selected_date = date.getTime();
                Log.i("selected_date2",selected_date+"");
            }

            @Override
            public void onCalendarScroll(HorizontalCalendarView calendarView,
                                         int dx, int dy) {

            }

            @Override
            public boolean onDateLongClicked(Calendar date, int position) {
                return true;
            }
        });
        Log.i("selected_date", selected_date+"");
        TextView expressTv = view.findViewById(R.id.express);
        TextView standardTv = view.findViewById(R.id.standardTv);
        TextView expressViewTv = view.findViewById(R.id.expressViewTv);
        RadioButton radioBtn1 = view.findViewById(R.id.radioBtn1);
        RadioButton radioBtn2 = view.findViewById(R.id.radioBtn2);
        RadioButton radioBtn3 = view.findViewById(R.id.radioBtn3);
        TextView doneTv = view.findViewById(R.id.doneTv);
        HorizontalCalendarView calendar = view.findViewById(R.id.calendarView);
        LinearLayout TimeLL = view.findViewById(R.id.TimeLL);

        doneTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radioBtn1.isChecked() | radioBtn2.isChecked() | radioBtn3.isChecked()) {
                    String radio="";
                    if (radioBtn1.isChecked()){
                        radio = radioBtn1.getText().toString();
                    }
                    if (radioBtn2.isChecked()){
                        radio = radioBtn2.getText().toString();
                    }
                    if (radioBtn3.isChecked()){
                        radio = radioBtn3.getText().toString();
                    }

                    startActivity(new Intent(getActivity(), Payment.class).putExtra("date", selected_date.toString()).putExtra("time",radio));
                }else {
                    Utils.Toast(getContext(),"Please Select Time");
                }
            }
        });

        radioBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioBtn1.setChecked(true);
                radioBtn2.setChecked(false);
                radioBtn3.setChecked(false);
            }
        });
        radioBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioBtn1.setChecked(false);
                radioBtn2.setChecked(true);
                radioBtn3.setChecked(false);
            }
        });
        radioBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioBtn1.setChecked(false);
                radioBtn2.setChecked(false);
                radioBtn3.setChecked(true);
            }
        });

        expressTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expressTv.setBackground(getResources().getDrawable(R.drawable.greyborder));
                expressTv.setTextColor(getResources().getColor(R.color.colorPrimary));
                standardTv.setTextColor(getResources().getColor(R.color.black));
                standardTv.setBackground(null);
                TimeLL.setVisibility(View.GONE);
                expressViewTv.setVisibility(View.VISIBLE);
                calendar.setVisibility(View.GONE);
            }
        });
        standardTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                standardTv.setBackground(getResources().getDrawable(R.drawable.greyborder));
                standardTv.setTextColor(getResources().getColor(R.color.colorPrimary));
                expressTv.setTextColor(getResources().getColor(R.color.black));
                expressTv.setBackground(null);
                TimeLL.setVisibility(View.VISIBLE);
                expressViewTv.setVisibility(View.GONE);
                calendar.setVisibility(View.VISIBLE);
            }
        });


        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
        ((View)view.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    String state = "";

                    switch (newState) {
                        case BottomSheetBehavior.STATE_DRAGGING: {
                            state = "DRAGGING";
                            break;
                        }
                        case BottomSheetBehavior.STATE_SETTLING: {
                            state = "SETTLING";
                            break;
                        }
                        case BottomSheetBehavior.STATE_EXPANDED: {
                            state = "EXPANDED";
                            break;
                        }
                        case BottomSheetBehavior.STATE_COLLAPSED: {
                            state = "COLLAPSED";
                            break;
                        }
                        case BottomSheetBehavior.STATE_HIDDEN: {
                            dismiss();
                            state = "HIDDEN";
                            break;
                        }
                    }

//                    Toast.makeText(getContext(), "Bottom Sheet State Changed to: " + state, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });
        }
    }
}
