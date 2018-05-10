package com.example.joao.ecotrip;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    //criaçao da lista
    private ListView listaOpcoes;
    private ArrayAdapter<String> listaOpcoesAdapter;

    String[] permissoes = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.INTERNET,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PermissionUtils.validate(this, 0, permissoes);

        //inicia variaveis
        this.listaOpcoesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        this.listaOpcoes = (ListView) findViewById(R.id.listaOpcoes);

        //adiciona opcoes
        this.listaOpcoesAdapter.add("Trilhas");
        this.listaOpcoesAdapter.add("Rafting");
        this.listaOpcoesAdapter.add("Parques");

        //link list view com adaptador
        this.listaOpcoes.setAdapter(listaOpcoesAdapter);

        this.listaOpcoes.setOnItemClickListener(new ListView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                lugarItemClicked(position);
            }

        });
    }

    private void lugarItemClicked(int position) {
        String lugar = listaOpcoesAdapter.getItem(position);
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("opcao", lugar);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                // Alguma permissão foi negada
                alertAndFinish();
                return;
            }
        }
        // Se chegou aqui está OK
    }

    private void alertAndFinish() {
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_name).setMessage("Para utilizar este aplicativo, você precisa aceitar as permissões.");
            // Add the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
