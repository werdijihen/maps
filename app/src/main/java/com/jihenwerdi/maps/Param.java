package com.jihenwerdi.maps;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Param extends AppCompatActivity {

    private EditText edTitre;
    private EditText edMessage;
    private Button btnValider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_param);
        initialiser();
    }

    private void initialiser() {
        edTitre = findViewById(R.id.edTitre);
        edMessage = findViewById(R.id.edMessage);
        btnValider = findViewById(R.id.btnValider);
        ecouteurs();
    }

    private void ecouteurs() {
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                valider();
            }
        });
    }

    protected void valider() {
        // Récupérer les données saisies
        String titre = edTitre.getText().toString();
        String snippet = edMessage.getText().toString();

        // Créer un nouvel Intent pour renvoyer les données à l'activité principale
        Intent intent = new Intent();
        intent.putExtra("titre", titre);
        intent.putExtra("snippet", snippet);

        // Ajouter les coordonnées de la carte (si disponibles)
        if (getIntent().hasExtra("latitude") && getIntent().hasExtra("longitude")) {
            double latitude = getIntent().getDoubleExtra("latitude", 0);
            double longitude = getIntent().getDoubleExtra("longitude", 0);
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
        }

        // Renvoyer le résultat à l'activité appelante
        setResult(RESULT_OK, intent);

        // Fermer l'activité Param
        finish();
    }
}