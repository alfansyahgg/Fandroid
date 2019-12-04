package com.example.pkl.alfansimplecrud;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<String> array_noinduk,array_nama,array_alamat,array_hobi;
    ProgressDialog progressDialog;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_noind,tv_nama,tv_alamat,tv_hobi;
        public CardView cv_main;

        public MyViewHolder(View view) {
            super(view);
            cv_main = itemView.findViewById(R.id.cv_main);
            tv_noind = itemView.findViewById(R.id.tv_noind);
            tv_nama = itemView.findViewById(R.id.tv_nama);
            tv_alamat = itemView.findViewById(R.id.tv_alamat);
            tv_hobi = itemView.findViewById(R.id.tv_hobi);

            progressDialog = new ProgressDialog(mContext);
        }
    }

    public RecycleViewAdapter(Context mContext, ArrayList<String> array_noinduk,ArrayList<String> array_nama,ArrayList<String> array_alamat,ArrayList<String> array_hobi) {
        super();
        this.mContext = mContext;
        this.array_noinduk = array_noinduk;
        this.array_nama = array_nama;
        this.array_alamat = array_alamat;
        this.array_hobi = array_hobi;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.template_rv,parent,false);
        return new RecycleViewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_noind.setText(array_noinduk.get(position));
        holder.tv_nama.setText(array_nama.get(position));
        holder.tv_alamat.setText(array_alamat.get(position));
        holder.tv_hobi.setText(array_hobi.get(position));
        holder.cv_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext,Activity_Edit.class);
                i.putExtra("noinduk",array_noinduk.get(position));
                i.putExtra("nama",array_nama.get(position));
                i.putExtra("alamat",array_alamat.get(position));
                i.putExtra("hobi",array_hobi.get(position));
                ((Activity_Main)mContext).startActivityForResult(i,2);
            }
        });
        holder.cv_main.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder((Activity_Main)mContext)
                        .setMessage("Ingin menghapus nomor induk "+array_noinduk.get(position)+" ?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                progressDialog.setMessage("Menghapus...");
                                progressDialog.setCancelable(false);
                                progressDialog.show();

                                AndroidNetworking.post("http://192.168.168.11/api-kompikaleng/deleteSiswa.php")
                                        .addBodyParameter("noinduk",""+array_noinduk.get(position))
                                        .setPriority(Priority.MEDIUM)
                                        .build()
                                        .getAsJSONObject(new JSONObjectRequestListener() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                progressDialog.dismiss();
                                                try {
                                                    Boolean status = response.getBoolean("status");
                                                    Log.d("statuss",""+status);
                                                    String result = response.getString("result");
                                                    if(status){
                                                        if(mContext instanceof Activity_Main){
                                                            ((Activity_Main)mContext).scrollRefresh();
                                                        }
                                                    }else{
                                                        Toast.makeText(mContext, ""+result, Toast.LENGTH_SHORT).show();
                                                    }
                                                }catch (Exception e){
                                                    e.printStackTrace();
                                                }
                                            }

                                            @Override
                                            public void onError(ANError anError) {
                                                anError.printStackTrace();
                                            }
                                        });
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return array_noinduk.size();
    }
}
