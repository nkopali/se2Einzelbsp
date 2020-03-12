package com.example.se2bsp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    public TextView response;
    public EditText sendmsg;
    String serverMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendmsg = findViewById(R.id.message);
        response = findViewById(R.id.serverResponse);
    }

    public void checkMat(View v) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Socket s = new Socket("se2-isys.aau.at", 53212);

                    PrintWriter output = new PrintWriter(s.getOutputStream());

                    output.println(sendmsg.getText().toString());
                    output.flush();

                    BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    serverMsg = input.readLine();

                    output.close();
                    s.close();

                } catch (Exception e ){
                    response.setText(e.getMessage());
                }
            }
        });
        thread.start();
        thread.join();
        response.setText(serverMsg);

    }

    public void calculate(View v){
        int numbers = Integer.parseInt(sendmsg.getText().toString());
        int summe = 0;

        while (numbers > 0){
            summe = summe + numbers%10;
            numbers /= 10;
        }
        System.out.println(summe);
        String binary = Integer.toBinaryString(summe);
        System.out.println(binary);

        String calcResponse = "Quersumme: "+ summe + "\nBinary: "+ binary;

        response.setText(calcResponse);

    }
}
