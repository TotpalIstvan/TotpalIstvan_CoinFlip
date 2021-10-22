package com.example.coinflip;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView coin;
    private Button fej,iras;
    private TextView dobasok,gyozelem,vereseg;
    private Random rand;
    private int dobasSzam,gyozelemSzam,veresegSzam,animationIndex;
    private boolean porgethetE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        fej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (porgethetE) {
                    porgethetE = false;
                    animacio(true);
                }
            }
        });

        iras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (porgethetE) {
                    porgethetE = false;
                    animacio(false);
                }
            }
        });

    }

    private void dobas(boolean fejTipp) {
        dobasSzam++;
        dobasok.setText("Dobások: " + dobasSzam);
        boolean fej = rand.nextBoolean();
        eredmeny(fej);

        if (fej == fejTipp) {
            gyozelemSzam++;
            gyozelem.setText("Győzelem: "+ gyozelemSzam);
        }else {
            veresegSzam++;
            vereseg.setText("Vereség: " + veresegSzam);
        }

        if(gyozelemSzam >= 3 || veresegSzam >= 3) {
                vege();
        }else {
            porgethetE = true;
        }

    }

    private void animacio(boolean fejTipp) {
        if (animationIndex > 20) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (animationIndex % 2 == 0) {
                        coin.setImageResource(R.drawable.heads);
                    }
                    else {
                        coin.setImageResource(R.drawable.tails);
                    }
                    animationIndex++;
                    animacio(fejTipp);
                }
            }, 50);
        }
        else {
            animationIndex = 0;
            dobas(fejTipp);
        }
    }

    private  void eredmeny(boolean fej) {
        Context context = getApplicationContext();
        String dobasEredmenye;
        int idotartam = Toast.LENGTH_SHORT;

        if (fej) {
            dobasEredmenye = "FEJ";
            coin.setImageResource(R.drawable.heads);
        }else {
            dobasEredmenye = "ÍRÁS";
            coin.setImageResource(R.drawable.tails);
        }
        Toast.makeText(context, dobasEredmenye, idotartam).show();
    }

    private  void vege() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(gyozelemSzam > veresegSzam) {
            builder.setTitle("Győzelem");
        }else {
            builder.setTitle("Vereség");
        }
        builder.setMessage("Szeretne új játékokót játszani?");
        builder.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ujrakezdes();
            }
        });
        builder.setNegativeButton("Nem", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.exit(1);
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }

    private void ujrakezdes() {
        dobasSzam = 0;
        gyozelemSzam = 0;
        veresegSzam = 0;
        dobasok.setText("Dobások:0");
        gyozelem.setText("Győzelem:0");
        vereseg.setText("Vereség:0");
        coin.setImageResource(R.drawable.heads);
        porgethetE = true;
    }

    private void init() {
        coin = findViewById(R.id.coin);
        fej = findViewById(R.id.fej);
        iras = findViewById(R.id.iras);
        dobasok = findViewById(R.id.dobasok);
        gyozelem = findViewById(R.id.gyozelem);
        vereseg = findViewById(R.id.vereseg);
        rand = new Random();
        porgethetE = true;
    }
}

