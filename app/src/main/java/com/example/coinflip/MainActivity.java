package com.example.coinflip;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import android. app. AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ImageView coin;
    private Button head;
    private Button tail;
    private TextView thrown;
    private TextView wins;
    private TextView losses;
    private Random rnd;
    private int countThrows;
    private int countWins;
    private int countLosses;
    private int userChoice;
    private AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();

        this.head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bet(0);
            }
        });

        this.tail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bet(1);
            }
        });

    }

    private void init(){
        this.coin = findViewById(R.id.coin);
        this.head = findViewById(R.id.head);
        this.tail = findViewById(R.id.tails);
        this.thrown = findViewById(R.id.thrown);
        this.wins = findViewById(R.id.wins);
        this.losses = findViewById(R.id.losses);
        this.rnd = new Random();
        this.countThrows = 0;
        this.countWins = 0;
        this.countLosses = 0;
        this.userChoice = 0;
        this.alert = new android.app.AlertDialog.Builder(this).setTitle("Győzelem").setMessage("Szeretne új játékot játszani?").setCancelable(false).setPositiveButton("Igen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ujJatek();
            }
        }).setNegativeButton("Nem", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).create();
    }

    private void Bet(int choice){
        int throwResult = rnd.nextInt(2);
        setImage(throwResult);
        this.countThrows++;
        this.thrown.setText( String.format("Dobások: %d", this.countThrows) );
        if(choice == throwResult){
            this.countWins++;
            this.wins.setText( String.format("Győzelem: %d", this.countWins) );
            Toast.makeText(MainActivity.this, "Ezt a kört nyerted!", Toast.LENGTH_SHORT).show();
        }
        else{
            this.countLosses++;
            this.losses.setText( String.format("Vereség: %d", this.countLosses) );
            Toast.makeText(MainActivity.this, "Ezt most nem sikerült!", Toast.LENGTH_SHORT).show();
        }
        checkForEnd();
    }

    private void setImage(int throwResult){
        if(throwResult == 0){
            this.coin.setImageResource(R.drawable.heads);
        }
        else{
            this.coin.setImageResource(R.drawable.tails);
        }
    }

    private void checkForEnd(){
        if(this.countWins == 3){
            this.alert.show();
        }
        else if(this.countLosses == 3){
            this.alert.setTitle("Vereség");
            this.alert.create();
            this.alert.show();
        }
    }

    private void ujJatek(){
        this.countThrows = 0;
        this.countWins = 0;
        this.countLosses = 0;
        this.userChoice = 0;
        this.thrown.setText( String.format("Dobások: %d", this.countThrows) );
        this.wins.setText( String.format("Győzelem: %d", this.countWins) );
        this.losses.setText( String.format("Vereség: %d", this.countLosses) );
    }
}