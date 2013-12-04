/***
	License
	=======

    Síragon - Quality Control App for Android
    Copyright (C) 2013  Síragon R&D

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

	Original Author: Síragon I&D <android@siragon.com.ve>

	Developers: Uclides Gil <desarrollo06@siragon.com.ve>
	Contributors: Alexander Salas <desarrollo03@siragon.com.ve>
	Translators: Fatma Youssef <fatma.youssef@globaltranslator.info>
	
*/
        package com.siragon.qc;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

    public class MainActivity extends Activity {

        //text
        public TextView timerValue;
        private long startTime = 0L;
        private Handler customHandler = new Handler();
        long timeInMilliseconds = 0L,timeSwapBuff = 0L,updatedTime = 0L;
        Toast tous;
        boolean des=false;
        int valor=1,valor1 = 0;
        int ram= (int) Math.ceil(Math.random()*1000);
        Button boton;
        TextView texto;
        Intent intent;
        File directory = new File("/mnt/sdcard/logs");
        File[] contents = directory.listFiles();
        String[] commands0 = {"sysrw", "mkdir /sdcard/logs ", "sysro"}, commands = {"sysrw", "monkey -p com.android.launcher --pct-nav 100 --ignore-crashes -v 7500", "sysro"};
        String[] commands2 = {"sysrw", "logcat > /sdcard/logs/"+Integer.valueOf(ram)+".txt", "sysro"};
        String[] commands3 = {"sysrw", "rm /data/misc/wifi/wpa_supplicant.conf", "sysro"};
        String[] commands4 = {"sysrw", "rm /sdcard/Pictures/Screenshots/*", "sysro"},commands5 = {"sysrw", "rm /sdcard/DCIM/Camera/*", "sysro"};
        String path="/data/misc/wifi/wpa_supplicant.conf";
        String[] unroot = {"sysrw", "mount -o rw,remount /system", "sysro"},unroot2 = {"sysrw", "rm /system/bin/su", "sysro"};
        String[] unroot5 = {"sysrw", "rm -r /sdcard/logs", "sysro"},unroot3 = {"sysrw", "rm /system/xbin/su", "sysro"};
        String[] unroot4 = {"sysrw", "rm /system/app/Superuser.apk", "sysro"},unroot6 = {"sysrw", "input keyevent 20", "sysro"};

        public static int MILISEGUNDOS_ESPERA = 10000, seg = 2000;

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            //timerValue = (TextView) findViewById(R.id.timer);

            if (savedInstanceState == null) {
                getFragmentManager().beginTransaction()
                        .add(R.id.container, new PlaceholderFragment())
                        .commit();
            }
            startTime = SystemClock.uptimeMillis();
            customHandler.postDelayed(timer, 0);

            try{
                texto=(TextView) findViewById(R.id.texto1);
                boton = (Button) findViewById(R.id.boton1);
                WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                wifiManager.setWifiEnabled(true);
                EstatusW(valor1 = 2);
                BluetoothAdapter mB = BluetoothAdapter.getDefaultAdapter();
                mB.enable();

                RunAsRoot(commands0);
                boton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try{
                            showI();
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });

            }
            catch(Exception ex){

            }

        }
        public void RunAsRoot(String[] cmds) throws IOException {
            Process p = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(p.getOutputStream());
            for (String tmpCmd : cmds) {
                os.writeBytes(tmpCmd+"\n");
            }
            os.writeBytes("exit\n");
            os.flush();
        }
        public void waitClose(int milisegundos) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    // acciones que se ejecutan tras los milisegundos
                    Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.android.settings");
                    startActivity(LaunchIntent);
                }
            }, milisegundos);
        }
        public void time(int milisegundos) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    for (valor = 0; valor <= 24; valor++) {
                        try {
                            RunAsRoot(unroot6);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        valor++;
                    }
                }
            }, milisegundos);
        }

        public void showI(){
            new AlertDialog.Builder(this)
                    .setTitle("")
                    .setMessage("¿reestablecer dispositivo?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Thread thread2 = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    //     try {
                                    try {
                                        EstatusW(valor1 = 1);
                                        RunAsRoot(commands3);
                                        RunAsRoot(commands4);
                                        RunAsRoot(commands5);
                                        RunAsRoot(unroot5);
                                        Bluetooth();
                                        RunAsRoot(unroot);
                                        RunAsRoot(unroot4);
                                        RunAsRoot(unroot2);
                                        RunAsRoot(unroot3);

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            );
                            thread2.start();
                            if (thread2.getState() != Thread.State.TERMINATED)
                            {
                                Uninstall("package:com.siragon.qc");
                                Uninstall("package:ats.mmi.chagall");
                                //waitClose(MILISEGUNDOS_ESPERA);
                                //time(seg);

                            }
                        }
                    }

                    )
                    .
                            setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        Thread thread = new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {

                                                    EstatusW(valor1 = 2);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                        thread.start();
                                        if (thread.getState() != Thread.State.TERMINATED) {
                                            Bluetooth();
                                            RunAsRoot(commands);
                                            RunAsRoot(commands2);
                                            MainActivity.super.isFinishing();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            )
                    .

                            show();
        }
        //tous =Toast.makeText(this,"guardando",Toast.LENGTH_SHORT);
        //tous.show(


        public void Uninstall(String cad) {
            Uri packageUri = Uri.parse(cad);
            Intent uninstallIntent =
                    new Intent(Intent.ACTION_UNINSTALL_PACKAGE, packageUri);
            startActivity(uninstallIntent);
        }
        public void Bluetooth() {
            BluetoothAdapter mB = BluetoothAdapter.getDefaultAdapter();
            if (!mB.isEnabled()) {
                mB.enable();
            } else {
                mB.disable();
            }
        }

        public void EstatusW(int pos) throws IOException {

            try {
                WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                // setup a wifi configuration

                WifiConfiguration wc = new WifiConfiguration();
                wc.SSID = "\"DaisyTek01\"";
                wc.preSharedKey = "\"Cirtr0nIc$\"";
                wc.status = WifiConfiguration.Status.ENABLED;
                wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                wc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
                wc.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
                // connect to and enable the connection
                if(pos==1){
                    int netId = wifiManager.addNetwork(wc);
                    ;
                    wifiManager.removeNetwork(netId);
                    wifiManager.setWifiEnabled(false);
                }else{
                    int netId = wifiManager.addNetwork(wc);
                    wifiManager.enableNetwork(netId, true);
                    wifiManager.setWifiEnabled(true);

                }
            } catch (Exception e) {
                //log for error
            }
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {

            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();
            if (id == R.id.action_settings) {
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        /**
         * A placeholder fragment containing a simple view.
         */
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public static class PlaceholderFragment extends Fragment {

            public PlaceholderFragment() {
            }

            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
                View rootView = inflater.inflate(R.layout.fragment_main, container, false);
                return rootView;
            }
        }

        public Runnable timer;

        {
            timer = new Runnable() {
                @Override
                public void run() {

                    timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
                    updatedTime = timeSwapBuff + timeInMilliseconds;
                    int secs = (int) (updatedTime / 1000);
                    int mins = secs / 60;
                    if (mins == valor) {
                        showI();
                        valor++;
                    }
                    secs = secs % 60;
                    int milliseconds = (int) (updatedTime % 1000);
                    customHandler.postDelayed(this, 0);
                    valor1 = mins;
                }
            };
        }
    }


