package com.example.raaznamehencryption;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;


public class MainActivity extends AppCompatActivity {

    TextView textView_pub_a;
    TextView textView_pub_b;

    TextView textView_prv_a;
    TextView textView_prv_b;

    Spinner spinner_securityLevel_a;
    Spinner spinner_securityLevel_b;

    Button button_genKeys_a;
    Button button_genKeys_b;

    EditText editText_plain_a;
    EditText editText_plain_b;

    Button button_enc_a;
    Button button_enc_b;

    TextView textView_cipher_a;
    TextView textView_cipher_b;

    Button button_dec_a;
    Button button_dec_b;

    TextView textView_decrypted_a;
    TextView textView_decrypted_b;

    String[] security_level_names = {"Low", "Medium", "High"};

    KeyPair keyPair_a;
    KeyPair keyPair_b;

    String cipher_a, cipher_b;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView_pub_a = (TextView) findViewById(R.id.textView_pub_a);
        textView_pub_a.setMovementMethod(new ScrollingMovementMethod());
        textView_pub_b = (TextView) findViewById(R.id.textView_pub_b);
        textView_pub_b.setMovementMethod(new ScrollingMovementMethod());

        textView_prv_a = (TextView) findViewById(R.id.textView_prv_a);
        textView_prv_a.setMovementMethod(new ScrollingMovementMethod());
        textView_prv_b = (TextView) findViewById(R.id.textView_prv_b);
        textView_prv_b.setMovementMethod(new ScrollingMovementMethod());

        spinner_securityLevel_a = (Spinner) findViewById(R.id.spinner_security_level_a);
        spinner_securityLevel_b = (Spinner) findViewById(R.id.spinner_security_level_b);

        button_genKeys_a = (Button) findViewById(R.id.button_genKeys_a);
        button_genKeys_b = (Button) findViewById(R.id.button_genKeys_b);

        editText_plain_a = (EditText) findViewById(R.id.editText_plain_a);
        editText_plain_b = (EditText) findViewById(R.id.editText_plain_b);

        button_enc_a = (Button) findViewById(R.id.button_encrypt_a);
        button_enc_b = (Button) findViewById(R.id.button_encrypt_b);

        textView_cipher_a = (TextView) findViewById(R.id.textView_cipher_a);
        textView_cipher_a.setMovementMethod(new ScrollingMovementMethod());
        textView_cipher_b = (TextView) findViewById(R.id.textView_cipher_b);
        textView_cipher_b.setMovementMethod(new ScrollingMovementMethod());

        button_dec_a = (Button) findViewById(R.id.button_decrypt_a);
        button_dec_b = (Button) findViewById(R.id.button_decrypt_b);

        textView_decrypted_a = (TextView) findViewById(R.id.textView_decrypted_a);
        textView_decrypted_b = (TextView) findViewById(R.id.textView_decrypted_b);


        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter<String> aa = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,security_level_names);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner_securityLevel_a.setAdapter(aa);
        spinner_securityLevel_b.setAdapter(aa);

        button_genKeys_a.setOnClickListener(view -> genKeys_a());
        button_genKeys_b.setOnClickListener(view -> genKeys_b());

        button_enc_a.setOnClickListener(view -> enc_a());
        button_enc_b.setOnClickListener(view -> enc_b());

        button_dec_a.setOnClickListener(view -> dec_a());
        button_dec_b.setOnClickListener(view -> dec_b());



    }


    @SuppressLint("SetTextI18n")
    protected void genKeys_a (){
        try {
            long keySize;
            keySize=spinner_securityLevel_a.getSelectedItemId();
            keyPair_a = Raaz.generateKeyPair(512 * (int)Math.pow(2,keySize));
            textView_prv_a.setText("Private Key: " + new String(keyPair_a.getPrivate().getEncoded()));
            textView_pub_a.setText("Public Key : " + new String(keyPair_a.getPublic().getEncoded()));

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @SuppressLint("SetTextI18n")
    protected void genKeys_b (){
        try {
            long keySize;
            keySize=spinner_securityLevel_a.getSelectedItemId();
            keyPair_b = Raaz.generateKeyPair(512 * (int)Math.pow(2,keySize));
            textView_prv_b.setText("Private Key: " + new String(keyPair_b.getPrivate().getEncoded()));
            textView_pub_b.setText("Public Key : " + new String(keyPair_b.getPublic().getEncoded()));

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    protected void enc_a (){
        try {

            cipher_a = Raaz.encrypt(editText_plain_a.getText().toString(),keyPair_b.getPublic());

            textView_cipher_a.setText(cipher_a);

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    protected void enc_b (){
        try {

            cipher_b = Raaz.encrypt(editText_plain_b.getText().toString(),keyPair_a.getPublic());

            textView_cipher_b.setText(cipher_b);

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    protected void dec_a (){
        try {

            String decrypted_string_a = Raaz.decrypt(textView_cipher_a.getText().toString(),keyPair_b.getPrivate());

            textView_decrypted_a.setText(decrypted_string_a);

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    protected void dec_b (){
        try {

            String decrypted_string_b = Raaz.decrypt(textView_cipher_b.getText().toString(),keyPair_a.getPrivate());

            textView_decrypted_b.setText(decrypted_string_b);

        }catch(Exception e) {
            e.printStackTrace();
        }
    }


}