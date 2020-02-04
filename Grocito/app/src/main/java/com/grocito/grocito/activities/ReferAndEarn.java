package com.grocito.grocito.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.widget.ShareDialog;
import com.grocito.grocito.R;
import com.grocito.grocito.api.WebUrls;
import com.grocito.grocito.databinding.ActivityReferAndEarnBinding;
import com.grocito.grocito.databinding.EmailDialogBinding;
import com.grocito.grocito.utils.Utils;

import java.util.List;
import java.util.Objects;

public class ReferAndEarn extends AppCompatActivity {

    private ActivityReferAndEarnBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_refer_and_earn);

        binding.headlyaout.cartRL.setVisibility(View.GONE);
        binding.headlyaout.searchIcon.setVisibility(View.GONE);
        binding.headlyaout.productCatName.setText(R.string.refer_earm);
        binding.headlyaout.backBtn.setOnClickListener(v -> finish());

        binding.copyUrlTv.setOnClickListener(view->{
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            clipboard.setText(binding.referCodeTv.getText());
            Utils.Toast(this,"Copy Referral Code");
        });

        binding.whatsAppLL.setOnClickListener(view -> {
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.whatsapp");
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, WebUrls.BASE_URL +
                    "\n Referrel Code:  "+ binding.referCodeTv.getText().toString());
            try {
                startActivity(whatsappIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.whatsapp")));
            }
        });

        binding.fbLL.setOnClickListener(view -> {
//            shareFacebook(this, "Referrel Code:  "+ binding.referCodeTv.getText().toString(),WebUrls.BASE_URL);
            FacebookSdk.sdkInitialize(getApplicationContext());
            ShareDialog shareDialog = new ShareDialog(this);
            if (ShareDialog.canShow(ShareLinkContent.class)) {
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setQuote("Referrel Code:  "+ binding.referCodeTv.getText().toString())
//                        .setImageUrl(Uri.parse("https://lh3.googleusercontent.com/jUej7mN6M6iulmuwmW6Mk28PrzXgl-Ebn-MpTmkmwtOfj5f0hvnuw8j0NEzK0GuKoDE=w300-rw"))
                        .setContentUrl(Uri.parse(WebUrls.BASE_URL))
                        .build();

                shareDialog.show(linkContent);
            }
        });

        binding.emailLL.setOnClickListener(view->{

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            EmailDialogBinding emailDialogBinding = DataBindingUtil.inflate(getLayoutInflater(),
                    R.layout.email_dialog,
                    (ViewGroup) binding.getRoot(),
                    false);

            emailDialogBinding.copyUrlTv.setOnClickListener(v->{
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                clipboard.setText(emailDialogBinding.referCodeTv.getText());
                Utils.Toast(this,"Copy Referral Code");
            });
            builder.setView(emailDialogBinding.getRoot());
            emailDialogBinding.referCodeTv.setText(binding.referCodeTv.getText().toString());
            AlertDialog dialog = builder.create();
            emailDialogBinding.sendBtn.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto",emailDialogBinding.emailEt.getText().toString(), null));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Referral Code");
                intent.putExtra(Intent.EXTRA_TEXT,"Referrel Code:  "+ binding.referCodeTv.getText().toString()
                        + "\n"+ emailDialogBinding.messageEt.getText().toString());
                startActivity(Intent.createChooser(intent, getResources().getString(R.string.app_name)));
                dialog.dismiss();
            });

            emailDialogBinding.closeBtn.setOnClickListener(v -> {dialog.dismiss();});

            dialog.show();

        });
    }

    public static void shareFacebook(Activity activity, String text, String url) {
        boolean facebookAppFound = false;
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, url);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(url));

        PackageManager pm = activity.getPackageManager();
        List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
        for (final ResolveInfo app : activityList) {
            if ((app.activityInfo.packageName).contains("com.facebook.katana")) {
                final ActivityInfo activityInfo = app.activityInfo;
                final ComponentName name = new ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name);
                shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                shareIntent.setComponent(name);
                facebookAppFound = true;
                break;
            }
        }
        if (!facebookAppFound) {
            String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + url;
            shareIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
        }
        activity.startActivity(shareIntent);
    }

}
