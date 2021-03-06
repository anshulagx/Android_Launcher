package com.example.launcher.utils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.launcher.MainActivity;
import com.example.launcher.R;

import java.util.List;

public class FragmentB_RecyclerViewAdapter extends RecyclerView.Adapter<FragmentB_RecyclerViewAdapter.MyViewHolder>{

    List<AppInfo> appData;
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;
        public LinearLayout linearLayout;
        public MyViewHolder(LinearLayout v) {
            super(v);
            linearLayout=v;
            textView=v.findViewById(R.id.itemText);
        }
    }

    public FragmentB_RecyclerViewAdapter(List<AppInfo> data)
    {
        appData=data;
    }

    @NonNull
    @Override
    public FragmentB_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v=(LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_b_item_layout,parent,false);
        MyViewHolder vh=new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final FragmentB_RecyclerViewAdapter.MyViewHolder holder, final int position) {
        holder.textView.setText(appData.get(position).label);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchIntent = view.getContext().getPackageManager().getLaunchIntentForPackage(appData.get(position).packageName);
                view.getContext().startActivity(launchIntent);
            }
        });
        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                builder.setTitle("Settings");
                String options[]={"Add to Favourite","Settings","Uninstall"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TextView txt=(TextView)view.findViewById(R.id.itemText);
                        String option=txt.getText().toString();

                        switch (i)
                        {
                            case 0:
                                //add to favourite
                                //TODO
                                break;
                            case 1:
                                //settings
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", MainActivity.appMap.get(option), null);
                                intent.setData(uri);
                                view.getContext().startActivity(intent);
                                break;
                            case 2:
                                //uninstall
                                Intent intent1=new Intent(Intent.ACTION_DELETE);
                                intent1.setData(Uri.parse("package:"+MainActivity.appMap.get(option)));
                                view.getContext().startActivity(intent1);
                                break;

                        }
                    }

                });
                AlertDialog dialog=builder.create();
                dialog.show();

                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return appData.size();
    }
}
