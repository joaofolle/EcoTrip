package com.example.joao.ecotrip;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        LocationListener {

    private GoogleMap mMap;

    String opcao;

    private LocationManager mLocationManager = null;
    private String provider = null;



    LatLng myPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eco_trip);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        provider = mLocationManager.getBestProvider(new Criteria(), false);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        final Intent intent = getIntent();
        Log.i("Opcao", intent.getStringExtra("opcao"));
        opcao = intent.getStringExtra("opcao");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Problema!", Toast.LENGTH_SHORT).show();
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mLocationManager.getLastKnownLocation(provider);

        if (isProviderAvailable() && (provider != null)) {
            locateCurrentPosition();
        }

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = mLocationManager.getBestProvider(criteria, true);
        Location location = mLocationManager.getLastKnownLocation(provider);

        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LatLng latLng = new LatLng(latitude, longitude);
            myPosition = new LatLng(latitude, longitude);

            LatLng coordinate = new LatLng(latitude, longitude);
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 13);
            mMap.animateCamera(yourLocation);
        }

        if(opcao.equals(null) || opcao.equals("")){
            Toast.makeText(this, "Nenhuma opção de atividade selecionada!", Toast.LENGTH_SHORT).show();
        }else{
            populaMapa();
        }
    }

    private void populaMapa() {
        criaMarcas(opcao);
    }

    private void criaMarcas(String opcao) {



        if (opcao.equals("Trilhas")) {
            LatLng ecotrilhaSESC = new LatLng(-30.0406013, -51.1486018);
            LatLng morroDoOsso = new LatLng(-30.1226008, -51.2340327);
            LatLng quintaDaEstancia = new LatLng(-30.0423073, -50.9950647);
            LatLng reservaFamiliaLima = new LatLng(-29.5463366, -51.0149077);


             Marker mEcotrilhaSESC = mMap.addMarker(new MarkerOptions()
                    .position(ecotrilhaSESC)
                    .title("Eco Trilha - SESC Campestre")
                     .snippet("Tipo: Particular")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.trilhas)));

            Marker mMorroDoOsso = mMap.addMarker(new MarkerOptions()
                    .position(morroDoOsso)
                    .title("Morro do Osso")
                    .snippet("Tipo: Público")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.trilhas)));

            Marker mQuintaDaEstancia = mMap.addMarker(new MarkerOptions()
                    .position(quintaDaEstancia)
                    .title("Quinta da Estância")
                    .snippet("Tipo: Particular")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.trilhas)));

            Marker mReservaFamiliaLima = mMap.addMarker(new MarkerOptions()
                    .position(reservaFamiliaLima)
                    .title("Reserva Ecológica Família Lima")
                    .snippet("Tipo: Particular")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.trilhas)));
        }

        else if(opcao.equals("Rafting")) {
            LatLng BrasilRaft = new LatLng(-29.5176552, -50.8403985);
            LatLng CentralSulRaft = new LatLng(-29.4156627, -50.8392732);
            LatLng Exxtreme4Rafting = new LatLng(-29.4159314, -50.8387129);

            Marker mBrasilRaft = mMap.addMarker(new MarkerOptions()
                    .position(BrasilRaft)
                    .title("Brasil Raft")
                    .snippet("Site: http://www.brasilraft.com.br/")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.rafting)));

            Marker mCentralSulRaft = mMap.addMarker(new MarkerOptions()
                    .position(CentralSulRaft)
                    .title("Central Sul Raft")
                    .snippet("Site: http://www.centralsulraft.com.br")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.rafting)));

            Marker mExxtreme4Rafting = mMap.addMarker(new MarkerOptions()
                    .position(Exxtreme4Rafting)
                    .title("Exxtreme.4 Rafting")
                    .snippet("Site: http://www.exxtreme.com.br/")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.rafting)));
        }

        else if(opcao.equals("Parques")) {
            LatLng ParqueMarinha = new LatLng(-30.0493089, -51.3002009);
            LatLng ParqueGabrielKnijnik = new LatLng(-30.1033779, -51.274635);
            LatLng ParqueGermania = new LatLng(-30.0255338, -51.2288069);
            LatLng ParqueFarroupilha = new LatLng(-30.0375148, -51.2854608);

            Marker mParqueMarinha = mMap.addMarker(new MarkerOptions()
                    .position(ParqueMarinha)
                    .title("Parque Marinha do Brasil")
                    .snippet("Acesso: Gratuito")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.parques)));

            Marker mParqueGabrielKnijnik = mMap.addMarker(new MarkerOptions()
                    .position(ParqueGabrielKnijnik)
                    .title("Parque Gabriel Knijnik")
                    .snippet("Acesso: Gratuito")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.parques)));

            Marker mParqueParqueGermania = mMap.addMarker(new MarkerOptions()
                    .position(ParqueGermania)
                    .title("Parque Germânia")
                    .snippet("Acesso: Gratuito")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.parques)));

            Marker mParqueFarroupilha = mMap.addMarker(new MarkerOptions()
                    .position(ParqueFarroupilha)
                    .title("Parque Farroupilha (Redenção)")
                    .snippet("Acesso: Gratuito")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.parques)));
        }


    }


    @Override
    public boolean onMyLocationButtonClick() {
        //Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        //Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    private void locateCurrentPosition() {

        int status = getPackageManager().checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION,
                getPackageName());

        if (status == PackageManager.PERMISSION_GRANTED) {
            mLocationManager.requestLocationUpdates(provider, 400, 1, this);

            Location location = mLocationManager.getLastKnownLocation(provider);
            updateWithNewLocation(location);
            //  mLocationManager.addGpsStatusListener(this);
            long minTime = 5000;// ms
            float minDist = 5.0f;// meter
            mLocationManager.requestLocationUpdates(provider, minTime, minDist,
                    this);

        }

    }

    private void updateWithNewLocation(Location location) {

        if (location != null && provider != null) {
            double lng = location.getLongitude();
            double lat = location.getLatitude();


            CameraPosition camPosition = new CameraPosition.Builder()
                    .target(new LatLng(lat, lng)).zoom(13f).build();

            if (mMap != null)
                mMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(camPosition));

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 13f));

        } else {
            Log.d("Erro Localização", "Algo deu errado!");
        }
    }


    private boolean isProviderAvailable() {
        mLocationManager = (LocationManager) getSystemService(
                Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setAltitudeRequired(false);  
        return false;
    }

    @Override
    public void onLocationChanged(Location location) {
        updateWithNewLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch (status) {
            case LocationProvider.OUT_OF_SERVICE:
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                break;
            case LocationProvider.AVAILABLE:
                break;
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
        updateWithNewLocation(null);
    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
