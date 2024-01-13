package com.jihenwerdi.maps;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.FragmentActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker isetMarker;
    public static final float ISET_LONG = 10.772176f;
    public static final float ISET_LAT = 34.756932f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        marquerISET();
        zoomISET();

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                ouvrirActiviteParam(latLng);
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                return true;
            }
        });
    }

    private void marquerISET() {
        LatLng isetLocation = new LatLng(ISET_LAT, ISET_LONG);
        isetMarker = mMap.addMarker(new MarkerOptions()
                .position(isetLocation)
                .title("ISET Sfax")
                .snippet("Classe GPT-3.5")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.iset_sfax)));
    }

    private void zoomISET() {
        LatLng isetLocation = new LatLng(ISET_LAT, ISET_LONG);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(isetLocation, 15);
        mMap.moveCamera(cameraUpdate);
    }

    private void ouvrirActiviteParam(LatLng latLng) {
        Intent intent = new Intent(MapsActivity.this, Param.class);
        // Envoyer les coordonnées au nouvel Intent
        intent.putExtra("latitude", latLng.latitude);
        intent.putExtra("longitude", latLng.longitude);

        // Lancer l'activité avec le résultat attendu
        activityResultLauncher.launch(intent);
    }

    // ...

    // Callback appelé lorsque l'activité Param est fermée
    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        // Récupérer les données renvoyées par l'activité Param
                        String titre = result.getData().getStringExtra("titre");
                        String snippet = result.getData().getStringExtra("snippet");
                        double latitude = result.getData().getDoubleExtra("latitude", 0);
                        double longitude = result.getData().getDoubleExtra("longitude", 0);

                        // Ajouter un marqueur avec les données saisies
                        ajouterMarqueur(latitude, longitude, titre, snippet);
                    } else {
                        Toast.makeText(MapsActivity.this, "Annulé", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    // Ajouter un marqueur aux coordonnées spécifiées
    private void ajouterMarqueur(double latitude, double longitude, String titre, String snippet) {
        LatLng newMarkerLocation = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions()
                .position(newMarkerLocation)
                .title(titre)
                .snippet(snippet));
    }
}