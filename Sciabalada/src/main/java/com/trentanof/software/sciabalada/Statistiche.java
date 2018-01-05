package com.trentanof.software.sciabalada;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MenuItem;

public class Statistiche extends Activity {

    String path;

    float winGames;
    float winGamesAC;
    float winGamesCM;
    float winGamesKR;

    float cashIn;
    float cashInAC;
    float cashInCM;
    float cashInKR;

    int winScore;

    int scopaScoreAC;
    int scopaScoreCM;
    int scopaScoreKR;

    float singleGAC;
    float singleGCM;
    float singleGKR;

    String range;
    String contentSaldo;
    String contentSaldoTop = "<html>"
            + "  <head>"
            + "    <script type=\"text/javascript\" src=\"jsapi.js\"></script>"
            + "    <script type=\"text/javascript\">"
            + "      google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});"
            + "      google.setOnLoadCallback(drawChart);"
            + "      function drawChart() {"
            + "        var data = google.visualization.arrayToDataTable([";

    String contentSaldoBottom = ""
            + "        chart.draw(data, options);"
            + "      }"
            + "    </script>"
            + "  </head>"
            + "  <body>"
            + "    <div id=\"chart_div\" style=\"width: 1000px; height: 500px;\"></div>"
            // + "    <img style=\"padding: 0; margin: 0 0 0 330px; display: block;\" src=\"/drawable/sciabalada.png\"/>"
            + "  </body>" + "</html>";

    float[] angelo = new float[8];
    float[] co = new float[8];
    float[] guest1 = new float[8];
    float[] guest2 = new float[8];
    float[] karmen = new float[8];
    float[] katia = new float[8];
    float[] guest3 = new float[8];
    float[] mario = new float[8];
    float[] mauro = new float[8];
    float[] renzo = new float[8];

    float[] angeloCo = new float[8];
    float[] mauroKarmen = new float[8];
    float[] renzoKatia = new float[8];


    public final static String EXTRA_MESSAGE = "com.trentanof.software.sciabalada.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistiche);

        Intent intent = getIntent();
        range = intent.getStringExtra(Statistiche.EXTRA_MESSAGE);

        setTitle("Statistiche " + range);


        // path = getFilesDir().getAbsolutePath();

        //** get destination path
        try {
            FileReader fr = new FileReader(getFilesDir() + "/settings");
            System.out.println(getFilesDir());

            BufferedReader br = new BufferedReader(fr);
            path = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File f = new File(path);
        File file[] = f.listFiles();

        for (int i = 0; i < file.length; i++) {

            String fileName = file[i].getName();
            if (fileName.equals(".ttxfolder")) {
                continue;
            }
            if (!range.equals("Overall")) {
                //verify range
                String[] fileNameParts = fileName.split("_");
                String year = fileNameParts[1].substring(0, 4);
                if (!range.equals(year)) {
                    continue;
                }
            }
            try {
                FileReader fr = new FileReader(path + "/" + file[i].getName());
                BufferedReader br = new BufferedReader(fr);
                String fl = br.readLine();
                if (fl.equals("closed") & file[i].getName().substring(0, 7).equals("scala40")) {
                    String sl = br.readLine();
                    String[] partsSl = sl.split(",");
                    String jackpot = partsSl[0];
                    String valueP = partsSl[1];
                    String valueR = partsSl[2];
                    String plData;
                    if (file[i].getName().substring(0, 9).equals("scala40QD")) {
                        winScore = 151;
                    } else {
                        winScore = 301;
                    }
                    while (true) {
                        plData = br.readLine();
                        if (plData == null)
                            break;
                        String[] parts = plData.split(",");
                        if (parts[0].equals("Angelo")) {
                            //partite giocate
                            float games = angelo[0] + 1;
                            //mani giocate
                            float singleG = angelo[7] + Float.parseFloat(parts[3]);
                            //rientri
                            float reEnters = angelo[1] + Float.parseFloat(parts[4]);
                            //partite vinte
                            if (Integer.parseInt(parts[1]) < winScore) {
                                winGames = angelo[2] + 1;
                            } else {
                                winGames = angelo[2];
                            }
                            //puntate
                            float cashOut = angelo[3] + Float.parseFloat(valueP) + (Float.parseFloat(parts[4]) * Float.parseFloat(valueR));
                            //vincite
                            float cashIn;
                            if (Integer.parseInt(parts[1]) < winScore) {
                                cashIn = angelo[4] + Float.parseFloat(jackpot);
                            } else {
                                cashIn = angelo[4];
                            }
                            //saldo
                            float cashTot = cashIn - cashOut;
                            //%partite vinte
                            int gamesRatio = Math.round(100 * winGames / games);
                            angelo[0] = games;
                            angelo[1] = reEnters;
                            angelo[2] = winGames;
                            angelo[3] = cashOut;
                            angelo[4] = cashIn;
                            angelo[5] = cashTot;
                            angelo[6] = gamesRatio;
                            angelo[7] = singleG;
                        }
                        if (parts[0].equals("Co")) {
                            //partite giocate
                            float games = co[0] + 1;
                            //mani giocate
                            float singleG = co[7] + Float.parseFloat(parts[3]);
                            //rientri
                            float reEnters = co[1] + Float.parseFloat(parts[4]);
                            //partite vinte
                            if (Integer.parseInt(parts[1]) < winScore) {
                                winGames = co[2] + 1;
                            } else {
                                winGames = co[2];
                            }
                            //puntate
                            float cashOut = co[3] + Float.parseFloat(valueP) + (Float.parseFloat(parts[4]) * Float.parseFloat(valueR));
                            //vincite
                            float cashIn;
                            if (Integer.parseInt(parts[1]) < winScore) {
                                cashIn = co[4] + Float.parseFloat(jackpot);
                            } else {
                                cashIn = co[4];
                            }
                            //saldo
                            float cashTot = cashIn - cashOut;
                            //%partite vinte
                            int gamesRatio = Math.round(100 * winGames / games);
                            co[0] = games;
                            co[1] = reEnters;
                            co[2] = winGames;
                            co[3] = cashOut;
                            co[4] = cashIn;
                            co[5] = cashTot;
                            co[6] = gamesRatio;
                            co[7] = singleG;
                        }
                        if (parts[0].equals("Guest1")) {
                            //partite giocate
                            float games = guest1[0] + 1;
                            //mani giocate
                            float singleG = guest1[7] + Float.parseFloat(parts[3]);
                            //rientri
                            float reEnters = guest1[1] + Float.parseFloat(parts[4]);
                            //partite vinte
                            if (Integer.parseInt(parts[1]) < winScore) {
                                winGames = guest1[2] + 1;
                            } else {
                                winGames = guest1[2];
                            }
                            //puntate
                            float cashOut = guest1[3] + Float.parseFloat(valueP) + (Float.parseFloat(parts[4]) * Float.parseFloat(valueR));
                            //vincite
                            float cashIn;
                            if (Integer.parseInt(parts[1]) < winScore) {
                                cashIn = guest1[4] + Float.parseFloat(jackpot);
                            } else {
                                cashIn = guest1[4];
                            }
                            //saldo
                            float cashTot = cashIn - cashOut;
                            //%partite vinte
                            int gamesRatio = Math.round(100 * winGames / games);
                            guest1[0] = games;
                            guest1[1] = reEnters;
                            guest1[2] = winGames;
                            guest1[3] = cashOut;
                            guest1[4] = cashIn;
                            guest1[5] = cashTot;
                            guest1[6] = gamesRatio;
                            guest1[7] = singleG;

                        }
                        if (parts[0].equals("Guest2")) {
                            //partite giocate
                            float games = guest2[0] + 1;
                            //mani giocate
                            float singleG = guest2[7] + Float.parseFloat(parts[3]);
                            //rientri
                            float reEnters = guest2[1] + Float.parseFloat(parts[4]);
                            //partite vinte
                            if (Integer.parseInt(parts[1]) < winScore) {
                                winGames = guest2[2] + 1;
                            } else {
                                winGames = guest2[2];
                            }
                            //puntate
                            float cashOut = guest2[3] + Float.parseFloat(valueP) + (Float.parseFloat(parts[4]) * Float.parseFloat(valueR));
                            //vincite
                            float cashIn;
                            if (Integer.parseInt(parts[1]) < winScore) {
                                cashIn = guest2[4] + Float.parseFloat(jackpot);
                            } else {
                                cashIn = guest2[4];
                            }
                            //saldo
                            float cashTot = cashIn - cashOut;
                            //%partite vinte
                            int gamesRatio = Math.round(100 * winGames / games);
                            guest2[0] = games;
                            guest2[1] = reEnters;
                            guest2[2] = winGames;
                            guest2[3] = cashOut;
                            guest2[4] = cashIn;
                            guest2[5] = cashTot;
                            guest2[6] = gamesRatio;
                            guest2[7] = singleG;

                        }
                        if (parts[0].equals("Karmen")) {
                            //partite giocate
                            float games = karmen[0] + 1;
                            //mani giocate
                            float singleG = karmen[7] + Float.parseFloat(parts[3]);
                            //rientri
                            float reEnters = karmen[1] + Float.parseFloat(parts[4]);
                            //partite vinte
                            if (Integer.parseInt(parts[1]) < winScore) {
                                winGames = karmen[2] + 1;
                            } else {
                                winGames = karmen[2];
                            }
                            //puntate
                            float cashOut = karmen[3] + Float.parseFloat(valueP) + (Float.parseFloat(parts[4]) * Float.parseFloat(valueR));
                            //vincite
                            float cashIn;
                            if (Integer.parseInt(parts[1]) < winScore) {
                                cashIn = karmen[4] + Float.parseFloat(jackpot);
                            } else {
                                cashIn = karmen[4];
                            }
                            //saldo
                            float cashTot = cashIn - cashOut;
                            //%partite vinte
                            int gamesRatio = Math.round(100 * winGames / games);
                            karmen[0] = games;
                            karmen[1] = reEnters;
                            karmen[2] = winGames;
                            karmen[3] = cashOut;
                            karmen[4] = cashIn;
                            karmen[5] = cashTot;
                            karmen[6] = gamesRatio;
                            karmen[7] = singleG;

                        }
                        if (parts[0].equals("Katia")) {
                            //partite giocate
                            float games = katia[0] + 1;
                            //mani giocate
                            float singleG = katia[7] + Float.parseFloat(parts[3]);
                            //rientri
                            float reEnters = katia[1] + Float.parseFloat(parts[4]);
                            //partite vinte
                            if (Integer.parseInt(parts[1]) < winScore) {
                                winGames = katia[2] + 1;
                            } else {
                                winGames = katia[2];
                            }
                            //puntate
                            float cashOut = katia[3] + Float.parseFloat(valueP) + (Float.parseFloat(parts[4]) * Float.parseFloat(valueR));
                            //vincite
                            float cashIn;
                            if (Integer.parseInt(parts[1]) < winScore) {
                                cashIn = katia[4] + Float.parseFloat(jackpot);
                            } else {
                                cashIn = katia[4];
                            }
                            //saldo
                            float cashTot = cashIn - cashOut;
                            //%partite vinte
                            int gamesRatio = Math.round(100 * winGames / games);
                            katia[0] = games;
                            katia[1] = reEnters;
                            katia[2] = winGames;
                            katia[3] = cashOut;
                            katia[4] = cashIn;
                            katia[5] = cashTot;
                            katia[6] = gamesRatio;
                            katia[7] = singleG;

                        }

                        if (parts[0].equals("Guest3")) {
                            //partite giocate
                            float games = guest3[0] + 1;
                            //mani giocate
                            float singleG = guest3[7] + Float.parseFloat(parts[3]);
                            //rientri
                            float reEnters = guest3[1] + Float.parseFloat(parts[4]);
                            //partite vinte
                            if (Integer.parseInt(parts[1]) < winScore) {
                                winGames = guest3[2] + 1;
                            } else {
                                winGames = guest3[2];
                            }
                            //puntate
                            float cashOut = guest3[3] + Float.parseFloat(valueP) + (Float.parseFloat(parts[4]) * Float.parseFloat(valueR));
                            //vincite
                            float cashIn;
                            if (Integer.parseInt(parts[1]) < winScore) {
                                cashIn = guest3[4] + Float.parseFloat(jackpot);
                            } else {
                                cashIn = guest3[4];
                            }
                            //saldo
                            float cashTot = cashIn - cashOut;
                            //%partite vinte
                            int gamesRatio = Math.round(100 * winGames / games);
                            guest3[0] = games;
                            guest3[1] = reEnters;
                            guest3[2] = winGames;
                            guest3[3] = cashOut;
                            guest3[4] = cashIn;
                            guest3[5] = cashTot;
                            guest3[6] = gamesRatio;
                            guest3[7] = singleG;

                        }


                        if (parts[0].equals("Mario")) {
                            //partite giocate
                            float games = mario[0] + 1;
                            //mani giocate
                            float singleG = mario[7] + Float.parseFloat(parts[3]);
                            //rientri
                            float reEnters = mario[1] + Float.parseFloat(parts[4]);
                            //partite vinte
                            if (Integer.parseInt(parts[1]) < winScore) {
                                winGames = mario[2] + 1;
                            } else {
                                winGames = mario[2];
                            }
                            //puntate
                            float cashOut = mario[3] + Float.parseFloat(valueP) + (Float.parseFloat(parts[4]) * Float.parseFloat(valueR));
                            //vincite
                            float cashIn;
                            if (Integer.parseInt(parts[1]) < winScore) {
                                cashIn = mario[4] + Float.parseFloat(jackpot);
                            } else {
                                cashIn = mario[4];
                            }
                            //saldo
                            float cashTot = cashIn - cashOut;
                            //%partite vinte
                            int gamesRatio = Math.round(100 * winGames / games);
                            mario[0] = games;
                            mario[1] = reEnters;
                            mario[2] = winGames;
                            mario[3] = cashOut;
                            mario[4] = cashIn;
                            mario[5] = cashTot;
                            mario[6] = gamesRatio;
                            mario[7] = singleG;

                        }
                        if (parts[0].equals("Mauro")) {
                            //partite giocate
                            float games = mauro[0] + 1;
                            //mani giocate
                            float singleG = mauro[7] + Float.parseFloat(parts[3]);
                            //rientri
                            float reEnters = mauro[1] + Float.parseFloat(parts[4]);
                            //partite vinte
                            if (Integer.parseInt(parts[1]) < winScore) {
                                winGames = mauro[2] + 1;
                            } else {
                                winGames = mauro[2];
                            }
                            //puntate
                            float cashOut = mauro[3] + Float.parseFloat(valueP) + (Float.parseFloat(parts[4]) * Float.parseFloat(valueR));
                            //vincite
                            float cashIn;
                            if (Integer.parseInt(parts[1]) < winScore) {
                                cashIn = mauro[4] + Float.parseFloat(jackpot);
                            } else {
                                cashIn = mauro[4];
                            }
                            //saldo
                            float cashTot = cashIn - cashOut;
                            //%partite vinte
                            int gamesRatio = Math.round(100 * winGames / games);
                            mauro[0] = games;
                            mauro[1] = reEnters;
                            mauro[2] = winGames;
                            mauro[3] = cashOut;
                            mauro[4] = cashIn;
                            mauro[5] = cashTot;
                            mauro[6] = gamesRatio;
                            mauro[7] = singleG;

                        }
                        if (parts[0].equals("Renzo")) {
                            //partite giocate
                            float games = renzo[0] + 1;
                            //mani giocate
                            float singleG = renzo[7] + Float.parseFloat(parts[3]);
                            //rientri
                            float reEnters = renzo[1] + Float.parseFloat(parts[4]);
                            //partite vinte
                            if (Integer.parseInt(parts[1]) < winScore) {
                                winGames = renzo[2] + 1;
                            } else {
                                winGames = renzo[2];
                            }
                            //puntate
                            float cashOut = renzo[3] + Float.parseFloat(valueP) + (Float.parseFloat(parts[4]) * Float.parseFloat(valueR));
                            //vincite
                            float cashIn;
                            if (Integer.parseInt(parts[1]) < winScore) {
                                cashIn = renzo[4] + Float.parseFloat(jackpot);
                            } else {
                                cashIn = renzo[4];
                            }
                            //saldo
                            float cashTot = cashIn - cashOut;
                            //%partite vinte
                            int gamesRatio = Math.round(100 * winGames / games);
                            renzo[0] = games;
                            renzo[1] = reEnters;
                            renzo[2] = winGames;
                            renzo[3] = cashOut;
                            renzo[4] = cashIn;
                            renzo[5] = cashTot;
                            renzo[6] = gamesRatio;
                            renzo[7] = singleG;

                        }

                    }
                }

                if (fl.equals("closed") & file[i].getName().substring(0, 5).equals("scopa")) {
                    String jackpot = br.readLine();
                    String plData;
                    boolean chkAC = false;
                    boolean chkCM = false;
                    boolean chkKR = false;

                    float singleGAC = 0;
                    float singleGCM = 0;
                    float singleGKR = 0;

                    int scopaScoreAC = 0;
                    int scopaScoreCM = 0;
                    int scopaScoreKR = 0;

                    while (true) {
                        plData = br.readLine();
                        if (plData == null)
                            break;
                        String[] parts = plData.split(",");
                        if (parts[0].equals("Angelo & Co")) {
                            chkAC = true;
                            //mani giocate
                            singleGAC = Float.parseFloat(parts[3]);
                            //punteggio
                            scopaScoreAC = Integer.parseInt(parts[1]);
                        }
                        if (parts[0].equals("Mauro & Karmen")) {
                            chkCM = true;
                            //mani giocate
                            singleGCM = Float.parseFloat(parts[3]);
                            //punteggio
                            scopaScoreCM = Integer.parseInt(parts[1]);
                        }
                        if (parts[0].equals("Renzo & Katia")) {
                            chkKR = true;
                            //mani giocate
                            singleGKR = Float.parseFloat(parts[3]);
                            //punteggio
                            scopaScoreKR = Integer.parseInt(parts[1]);
                        }
                        if (chkAC == true & chkCM == true) {
                            //partite giocate
                            float gamesAC = angeloCo[0] + 1;
                            float gamesCM = mauroKarmen[0] + 1;

                            //mani giocate
                            float singleTAC = angeloCo[7] + singleGAC;
                            float singleTCM = mauroKarmen[7] + singleGCM;

                            //partite vinte
                            if (scopaScoreAC > scopaScoreCM) {
                                winGamesAC = angeloCo[2] + 1;
                                cashInAC = angeloCo[4] + Float.parseFloat(jackpot);
                                winGamesCM = mauroKarmen[2];
                                cashInCM = mauroKarmen[4];
                            } else {
                                winGamesAC = angeloCo[2];
                                cashInAC = angeloCo[4];
                                winGamesCM = mauroKarmen[2] + 1;
                                cashInCM = mauroKarmen[4] + Float.parseFloat(jackpot);
                            }

                            //puntate
                            float cashOutAC = angeloCo[3] + Float.parseFloat(jackpot) / 2;
                            float cashOutCM = mauroKarmen[3] + Float.parseFloat(jackpot) / 2;

                            //saldo
                            float cashTotAC = cashInAC - cashOutAC;
                            float cashTotCM = cashInCM - cashOutCM;

                            //%partite vinte
                            int gamesRatioAC = Math.round(100 * winGamesAC / gamesAC);
                            int gamesRatioCM = Math.round(100 * winGamesCM / gamesCM);

                            angeloCo[0] = gamesAC;
                            angeloCo[1] = 0;
                            angeloCo[2] = winGamesAC;
                            angeloCo[3] = cashOutAC;
                            angeloCo[4] = cashInAC;
                            angeloCo[5] = cashTotAC;
                            angeloCo[6] = gamesRatioAC;
                            angeloCo[7] = singleTAC;

                            mauroKarmen[0] = gamesCM;
                            mauroKarmen[1] = 0;
                            mauroKarmen[2] = winGamesCM;
                            mauroKarmen[3] = cashOutCM;
                            mauroKarmen[4] = cashInCM;
                            mauroKarmen[5] = cashTotCM;
                            mauroKarmen[6] = gamesRatioCM;
                            mauroKarmen[7] = singleTCM;
                        }

                        if (chkCM == true & chkKR == true) {
                            //partite giocate
                            float gamesCM = mauroKarmen[0] + 1;
                            float gamesKR = renzoKatia[0] + 1;

                            //mani giocate
                            float singleTCM = mauroKarmen[7] + singleGCM;
                            float singleTKR = renzoKatia[7] + singleGKR;

                            //partite vinte
                            if (scopaScoreCM > scopaScoreKR) {
                                winGamesCM = mauroKarmen[2] + 1;
                                cashInCM = mauroKarmen[4] + Float.parseFloat(jackpot);
                                winGamesKR = renzoKatia[2];
                                cashInKR = renzoKatia[4];
                            } else {
                                winGamesCM = mauroKarmen[2];
                                cashInCM = mauroKarmen[4];
                                winGamesKR = renzoKatia[2] + 1;
                                cashInKR = renzoKatia[4] + Float.parseFloat(jackpot);
                            }

                            //puntate
                            float cashOutCM = mauroKarmen[3] + Float.parseFloat(jackpot) / 2;
                            float cashOutKR = renzoKatia[3] + Float.parseFloat(jackpot) / 2;

                            //saldo
                            float cashTotCM = cashInCM - cashOutCM;
                            float cashTotKR = cashInKR - cashOutKR;

                            //%partite vinte
                            int gamesRatioCM = Math.round(100 * winGamesCM / gamesCM);
                            int gamesRatioKR = Math.round(100 * winGamesKR / gamesKR);

                            mauroKarmen[0] = gamesCM;
                            mauroKarmen[1] = 0;
                            mauroKarmen[2] = winGamesCM;
                            mauroKarmen[3] = cashOutCM;
                            mauroKarmen[4] = cashInCM;
                            mauroKarmen[5] = cashTotCM;
                            mauroKarmen[6] = gamesRatioCM;
                            mauroKarmen[7] = singleTCM;

                            renzoKatia[0] = gamesKR;
                            renzoKatia[1] = 0;
                            renzoKatia[2] = winGamesKR;
                            renzoKatia[3] = cashOutKR;
                            renzoKatia[4] = cashInKR;
                            renzoKatia[5] = cashTotKR;
                            renzoKatia[6] = gamesRatioKR;
                            renzoKatia[7] = singleTKR;
                        }

                        if (chkAC == true & chkKR == true) {
                            //partite giocate
                            float gamesAC = angeloCo[0] + 1;
                            float gamesKR = renzoKatia[0] + 1;

                            //mani giocate
                            float singleTAC = angeloCo[7] + singleGAC;
                            float singleTKR = renzoKatia[7] + singleGKR;

                            //partite vinte
                            if (scopaScoreAC > scopaScoreKR) {
                                winGamesAC = angeloCo[2] + 1;
                                cashInAC = angeloCo[4] + Float.parseFloat(jackpot);
                                winGamesKR = renzoKatia[2];
                                cashInKR = renzoKatia[4];
                            } else {
                                winGamesAC = angeloCo[2];
                                cashInAC = angeloCo[4];
                                winGamesKR = renzoKatia[2] + 1;
                                cashInKR = renzoKatia[4] + Float.parseFloat(jackpot);
                            }

                            //puntate
                            float cashOutAC = angeloCo[3] + Float.parseFloat(jackpot) / 2;
                            float cashOutKR = renzoKatia[3] + Float.parseFloat(jackpot) / 2;

                            //saldo
                            float cashTotAC = cashInAC - cashOutAC;
                            float cashTotKR = cashInKR - cashOutKR;

                            //%partite vinte
                            int gamesRatioAC = Math.round(100 * winGamesAC / gamesAC);
                            int gamesRatioKR = Math.round(100 * winGamesKR / gamesKR);

                            angeloCo[0] = gamesAC;
                            angeloCo[1] = 0;
                            angeloCo[2] = winGamesAC;
                            angeloCo[3] = cashOutAC;
                            angeloCo[4] = cashInAC;
                            angeloCo[5] = cashTotAC;
                            angeloCo[6] = gamesRatioAC;
                            angeloCo[7] = singleTAC;

                            renzoKatia[0] = gamesKR;
                            renzoKatia[1] = 0;
                            renzoKatia[2] = winGamesKR;
                            renzoKatia[3] = cashOutKR;
                            renzoKatia[4] = cashInKR;
                            renzoKatia[5] = cashTotKR;
                            renzoKatia[6] = gamesRatioKR;
                            renzoKatia[7] = singleTKR;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //angelo
        TextView pl1PG = (TextView) findViewById(R.id.pl1PG);
        pl1PG.setText(Integer.toString(Math.round(angelo[0])));
        TextView pl1MG = (TextView) findViewById(R.id.pl1MG);
        pl1MG.setText(Integer.toString(Math.round(angelo[7])));
        TextView pl1RI = (TextView) findViewById(R.id.pl1RI);
        pl1RI.setText(Integer.toString(Math.round(angelo[1])));
        TextView pl1PV = (TextView) findViewById(R.id.pl1PV);
        pl1PV.setText(Integer.toString(Math.round(angelo[2])));
        TextView pl1SS = (TextView) findViewById(R.id.pl1SS);
        pl1SS.setText(Float.toString(angelo[3]));
        TextView pl1SV = (TextView) findViewById(R.id.pl1SV);
        pl1SV.setText(Float.toString(angelo[4]));
        TextView pl1SA = (TextView) findViewById(R.id.pl1SA);
        pl1SA.setText(Float.toString(angelo[5]));
        TextView pl1PPV = (TextView) findViewById(R.id.pl1PPV);
        pl1PPV.setText(Integer.toString(Math.round(angelo[6])));

        //co
        TextView pl2PG = (TextView) findViewById(R.id.pl2PG);
        pl2PG.setText(Integer.toString(Math.round(co[0])));
        TextView pl2MG = (TextView) findViewById(R.id.pl2MG);
        pl2MG.setText(Integer.toString(Math.round(co[7])));
        TextView pl2RI = (TextView) findViewById(R.id.pl2RI);
        pl2RI.setText(Integer.toString(Math.round(co[1])));
        TextView pl2PV = (TextView) findViewById(R.id.pl2PV);
        pl2PV.setText(Integer.toString(Math.round(co[2])));
        TextView pl2SS = (TextView) findViewById(R.id.pl2SS);
        pl2SS.setText(Float.toString(co[3]));
        TextView pl2SV = (TextView) findViewById(R.id.pl2SV);
        pl2SV.setText(Float.toString(co[4]));
        TextView pl2SA = (TextView) findViewById(R.id.pl2SA);
        pl2SA.setText(Float.toString(co[5]));
        TextView pl2PPV = (TextView) findViewById(R.id.pl2PPV);
        pl2PPV.setText(Integer.toString(Math.round(co[6])));

        //karmen
        TextView pl3PG = (TextView) findViewById(R.id.pl3PG);
        pl3PG.setText(Integer.toString(Math.round(karmen[0])));
        TextView pl3MG = (TextView) findViewById(R.id.pl3MG);
        pl3MG.setText(Integer.toString(Math.round(karmen[7])));
        TextView pl3RI = (TextView) findViewById(R.id.pl3RI);
        pl3RI.setText(Integer.toString(Math.round(karmen[1])));
        TextView pl3PV = (TextView) findViewById(R.id.pl3PV);
        pl3PV.setText(Integer.toString(Math.round(karmen[2])));
        TextView pl3SS = (TextView) findViewById(R.id.pl3SS);
        pl3SS.setText(Float.toString(karmen[3]));
        TextView pl3SV = (TextView) findViewById(R.id.pl3SV);
        pl3SV.setText(Float.toString(karmen[4]));
        TextView pl3SA = (TextView) findViewById(R.id.pl3SA);
        pl3SA.setText(Float.toString(karmen[5]));
        TextView pl3PPV = (TextView) findViewById(R.id.pl3PPV);
        pl3PPV.setText(Integer.toString(Math.round(karmen[6])));

        //katia
        TextView pl4PG = (TextView) findViewById(R.id.pl4PG);
        pl4PG.setText(Integer.toString(Math.round(katia[0])));
        TextView pl4MG = (TextView) findViewById(R.id.pl4MG);
        pl4MG.setText(Integer.toString(Math.round(katia[7])));
        TextView pl4RI = (TextView) findViewById(R.id.pl4RI);
        pl4RI.setText(Integer.toString(Math.round(katia[1])));
        TextView pl4PV = (TextView) findViewById(R.id.pl4PV);
        pl4PV.setText(Integer.toString(Math.round(katia[2])));
        TextView pl4SS = (TextView) findViewById(R.id.pl4SS);
        pl4SS.setText(Float.toString(katia[3]));
        TextView pl4SV = (TextView) findViewById(R.id.pl4SV);
        pl4SV.setText(Float.toString(katia[4]));
        TextView pl4SA = (TextView) findViewById(R.id.pl4SA);
        pl4SA.setText(Float.toString(katia[5]));
        TextView pl4PPV = (TextView) findViewById(R.id.pl4PPV);
        pl4PPV.setText(Integer.toString(Math.round(katia[6])));


        //mario
        TextView pl5PG = (TextView) findViewById(R.id.pl5PG);
        pl5PG.setText(Integer.toString(Math.round(mario[0])));
        TextView pl5MG = (TextView) findViewById(R.id.pl5MG);
        pl5MG.setText(Integer.toString(Math.round(mario[7])));
        TextView pl5RI = (TextView) findViewById(R.id.pl5RI);
        pl5RI.setText(Integer.toString(Math.round(mario[1])));
        TextView pl5PV = (TextView) findViewById(R.id.pl5PV);
        pl5PV.setText(Integer.toString(Math.round(mario[2])));
        TextView pl5SS = (TextView) findViewById(R.id.pl5SS);
        pl5SS.setText(Float.toString(mario[3]));
        TextView pl5SV = (TextView) findViewById(R.id.pl5SV);
        pl5SV.setText(Float.toString(mario[4]));
        TextView pl5SA = (TextView) findViewById(R.id.pl5SA);
        pl5SA.setText(Float.toString(mario[5]));
        TextView pl5PPV = (TextView) findViewById(R.id.pl5PPV);
        pl5PPV.setText(Integer.toString(Math.round(mario[6])));

        //mauro
        TextView pl9PG = (TextView) findViewById(R.id.pl6PG);
        pl9PG.setText(Integer.toString(Math.round(mauro[0])));
        TextView pl9MG = (TextView) findViewById(R.id.pl6MG);
        pl9MG.setText(Integer.toString(Math.round(mauro[7])));
        TextView pl9RI = (TextView) findViewById(R.id.pl6RI);
        pl9RI.setText(Integer.toString(Math.round(mauro[1])));
        TextView pl9PV = (TextView) findViewById(R.id.pl6PV);
        pl9PV.setText(Integer.toString(Math.round(mauro[2])));
        TextView pl9SS = (TextView) findViewById(R.id.pl6SS);
        pl9SS.setText(Float.toString(mauro[3]));
        TextView pl9SV = (TextView) findViewById(R.id.pl6SV);
        pl9SV.setText(Float.toString(mauro[4]));
        TextView pl9SA = (TextView) findViewById(R.id.pl6SA);
        pl9SA.setText(Float.toString(mauro[5]));
        TextView pl9PPV = (TextView) findViewById(R.id.pl6PPV);
        pl9PPV.setText(Integer.toString(Math.round(mauro[6])));

        //renzo
        TextView pl7PG = (TextView) findViewById(R.id.pl7PG);
        pl7PG.setText(Integer.toString(Math.round(renzo[0])));
        TextView pl7MG = (TextView) findViewById(R.id.pl7MG);
        pl7MG.setText(Integer.toString(Math.round(renzo[7])));
        TextView pl7RI = (TextView) findViewById(R.id.pl7RI);
        pl7RI.setText(Integer.toString(Math.round(renzo[1])));
        TextView pl7PV = (TextView) findViewById(R.id.pl7PV);
        pl7PV.setText(Integer.toString(Math.round(renzo[2])));
        TextView pl7SS = (TextView) findViewById(R.id.pl7SS);
        pl7SS.setText(Float.toString(renzo[3]));
        TextView pl7SV = (TextView) findViewById(R.id.pl7SV);
        pl7SV.setText(Float.toString(renzo[4]));
        TextView pl7SA = (TextView) findViewById(R.id.pl7SA);
        pl7SA.setText(Float.toString(renzo[5]));
        TextView pl7PPV = (TextView) findViewById(R.id.pl7PPV);
        pl7PPV.setText(Integer.toString(Math.round(renzo[6])));

        //others
        TextView pl8PG = (TextView) findViewById(R.id.pl8PG);
        pl8PG.setText(Integer.toString(Math.round(guest1[0]+guest2[0]+guest3[0])));
        TextView pl8MG = (TextView) findViewById(R.id.pl8MG);
        pl8MG.setText(Integer.toString(Math.round(guest1[7]+guest2[7]+guest3[7])));
        TextView pl8RI = (TextView) findViewById(R.id.pl8RI);
        pl8RI.setText(Integer.toString(Math.round(guest1[1]+guest2[1]+guest3[1])));
        TextView pl8PV = (TextView) findViewById(R.id.pl8PV);
        pl8PV.setText(Integer.toString(Math.round(guest1[2]+guest2[2]+guest3[2])));
        TextView pl8SS = (TextView) findViewById(R.id.pl8SS);
        pl8SS.setText(Float.toString(guest1[3]+guest2[3]+guest3[3]));
        TextView pl8SV = (TextView) findViewById(R.id.pl8SV);
        pl8SV.setText(Float.toString(guest1[4]+guest2[4]+guest3[4]));
        TextView pl8SA = (TextView) findViewById(R.id.pl8SA);
        pl8SA.setText(Float.toString(guest1[5]+guest2[5]+guest3[5]));
        TextView pl8PPV = (TextView) findViewById(R.id.pl8PPV);
//        pl8PPV.setText(Integer.toString(Math.round(guest1[6]+guest2[6]+guest3[6])));
        pl8PPV.setText(Integer.toString(Math.round(100*(guest1[2]+guest2[2]+guest3[2])/(guest1[0]+guest2[0]+guest3[0]))));


        //totale
        TextView plTPG = (TextView) findViewById(R.id.plTPG);
        plTPG.setText(Integer.toString(Math.round(angelo[0] + co[0] + guest1[0] + guest2[0] + karmen[0] + katia[0] + guest3[0] + mario[0] + mauro[0] + renzo[0])));
        TextView plTMG = (TextView) findViewById(R.id.plTMG);
        plTMG.setText(Integer.toString(Math.round(angelo[7] + co[7] + guest1[7] + guest2[7] + karmen[7] + katia[7] + guest3[7] + mario[7] + mauro[7] + renzo[7])));
        TextView plTRI = (TextView) findViewById(R.id.plTRI);
        plTRI.setText(Integer.toString(Math.round(angelo[1] + co[1] + guest1[1] + guest2[1] + karmen[1] + katia[1] + guest3[1] + mario[1] + mauro[1] + renzo[1])));
        TextView plTPV = (TextView) findViewById(R.id.plTPV);
        plTPV.setText(Integer.toString(Math.round(angelo[2] + co[2] + guest1[2] + guest2[2] + karmen[2] + katia[2] + guest3[2] + mario[2] + mauro[2] + renzo[2])));
        TextView plTSS = (TextView) findViewById(R.id.plTSS);
        plTSS.setText(Float.toString(angelo[3] + co[3] + guest1[3] + guest2[3] + karmen[3] + katia[3] + guest3[3] + mario[3] + mauro[3] + renzo[3]));
        TextView plTSV = (TextView) findViewById(R.id.plTSV);
        plTSV.setText(Float.toString(angelo[4] + co[4] + guest1[4] + guest2[4] + karmen[4] + katia[4] + guest3[4] + mario[4] + mauro[4] + renzo[4]));
        TextView plTSA = (TextView) findViewById(R.id.plTSA);
        plTSA.setText(Float.toString(angelo[5] + co[5] + guest1[5] + guest2[5] + karmen[5] + katia[5] + guest3[5] + mario[5] + mauro[5] + renzo[5]));
        TextView plTPPV = (TextView) findViewById(R.id.plTPPV);
        if ((angelo[5] + co[5] + guest1[5] + guest2[5] + karmen[5] + katia[5] + guest3[5] + mario[5] + mauro[5] + renzo[5] == 0) & ((angelo[4] + co[4] + guest1[4] + guest2[4] + karmen[4] + katia[4] + guest3[4] + mario[4] + mauro[4] + renzo[4]) == (angelo[3] + co[3] + guest1[3] + guest2[3] + karmen[3] + katia[3] + guest3[3] + mario[3] + mauro[3] + renzo[3]))) {
            plTPPV.setText("OK");
            plTPPV.setBackgroundColor(Integer.parseInt("00be00", 16) + 0xFF000000);
        } else {
            plTPPV.setText("KO");
            plTPPV.setBackgroundColor(Color.RED);
        }


        //angelo & Co
        TextView cp1PG = (TextView) findViewById(R.id.cp1PG);
        cp1PG.setText(Integer.toString(Math.round(angeloCo[0])));
        TextView cp1MG = (TextView) findViewById(R.id.cp1MG);
        cp1MG.setText(Integer.toString(Math.round(angeloCo[7])));
        TextView cp1PV = (TextView) findViewById(R.id.cp1PV);
        cp1PV.setText(Integer.toString(Math.round(angeloCo[2])));
        TextView cp1SS = (TextView) findViewById(R.id.cp1SS);
        cp1SS.setText(Float.toString(angeloCo[3]));
        TextView cp1SV = (TextView) findViewById(R.id.cp1SV);
        cp1SV.setText(Float.toString(angeloCo[4]));
        TextView cp1SA = (TextView) findViewById(R.id.cp1SA);
        cp1SA.setText(Float.toString(angeloCo[5]));
        TextView cp1PPV = (TextView) findViewById(R.id.cp1PPV);
        cp1PPV.setText(Integer.toString(Math.round(angeloCo[6])));

        //Mauro & Karmen
        TextView cp2PG = (TextView) findViewById(R.id.cp2PG);
        cp2PG.setText(Integer.toString(Math.round(mauroKarmen[0])));
        TextView cp2MG = (TextView) findViewById(R.id.cp2MG);
        cp2MG.setText(Integer.toString(Math.round(mauroKarmen[7])));
        TextView cp2PV = (TextView) findViewById(R.id.cp2PV);
        cp2PV.setText(Integer.toString(Math.round(mauroKarmen[2])));
        TextView cp2SS = (TextView) findViewById(R.id.cp2SS);
        cp2SS.setText(Float.toString(mauroKarmen[3]));
        TextView cp2SV = (TextView) findViewById(R.id.cp2SV);
        cp2SV.setText(Float.toString(mauroKarmen[4]));
        TextView cp2SA = (TextView) findViewById(R.id.cp2SA);
        cp2SA.setText(Float.toString(mauroKarmen[5]));
        TextView cp2PPV = (TextView) findViewById(R.id.cp2PPV);
        cp2PPV.setText(Integer.toString(Math.round(mauroKarmen[6])));

        //Renzo & Katia
        TextView cp3PG = (TextView) findViewById(R.id.cp3PG);
        cp3PG.setText(Integer.toString(Math.round(renzoKatia[0])));
        TextView cp3MG = (TextView) findViewById(R.id.cp3MG);
        cp3MG.setText(Integer.toString(Math.round(renzoKatia[7])));
        TextView cp3PV = (TextView) findViewById(R.id.cp3PV);
        cp3PV.setText(Integer.toString(Math.round(renzoKatia[2])));
        TextView cp3SS = (TextView) findViewById(R.id.cp3SS);
        cp3SS.setText(Float.toString(renzoKatia[3]));
        TextView cp3SV = (TextView) findViewById(R.id.cp3SV);
        cp3SV.setText(Float.toString(renzoKatia[4]));
        TextView cp3SA = (TextView) findViewById(R.id.cp3SA);
        cp3SA.setText(Float.toString(renzoKatia[5]));
        TextView cp3PPV = (TextView) findViewById(R.id.cp3PPV);
        cp3PPV.setText(Integer.toString(Math.round(renzoKatia[6])));

        //totale
        TextView cpTPG = (TextView) findViewById(R.id.cpTPG);
        cpTPG.setText(Integer.toString(Math.round(angeloCo[0] + mauroKarmen[0] + renzoKatia[0])));
        TextView cpTMG = (TextView) findViewById(R.id.cpTMG);
        cpTMG.setText(Integer.toString(Math.round(angeloCo[7] + mauroKarmen[7] + renzoKatia[7])));
        TextView cpTPV = (TextView) findViewById(R.id.cpTPV);
        cpTPV.setText(Integer.toString(Math.round(angeloCo[2] + mauroKarmen[2] + renzoKatia[2])));
        TextView cpTSS = (TextView) findViewById(R.id.cpTSS);
        cpTSS.setText(Float.toString(angeloCo[3] + mauroKarmen[3] + renzoKatia[3]));
        TextView cpTSV = (TextView) findViewById(R.id.cpTSV);
        cpTSV.setText(Float.toString(angeloCo[4] + mauroKarmen[4] + renzoKatia[4]));
        TextView cpTSA = (TextView) findViewById(R.id.cpTSA);
        cpTSA.setText(Float.toString(angeloCo[5] + mauroKarmen[5] + renzoKatia[5]));
        TextView cpTPPV = (TextView) findViewById(R.id.cpTPPV);
        if ((angeloCo[5] + mauroKarmen[5] + renzoKatia[5] == 0) & ((angeloCo[4] + mauroKarmen[4] + renzoKatia[4]) == (angeloCo[3] + mauroKarmen[3] + renzoKatia[3]))) {
            cpTPPV.setText("OK");
            cpTPPV.setBackgroundColor(Integer.parseInt("00be00", 16) + 0xFF000000);
        } else {
            cpTPPV.setText("KO");
            cpTPPV.setBackgroundColor(Color.RED);
        }

        //overall
        TextView cpOPG = (TextView) findViewById(R.id.cpOPG);
        cpOPG.setText(Integer.toString(Math.round(angelo[2] + co[2] + guest1[2] + guest2[2] + karmen[2] + katia[2] + guest3[2] + mario[2] + mauro[2] + renzo[2] + angeloCo[2] + mauroKarmen[2] + renzoKatia[2])));
        TextView cpOMG = (TextView) findViewById(R.id.cpOMG);
        cpOMG.setText(Integer.toString(Math.round(angelo[7] + co[7] + guest1[7] + guest2[7] + karmen[7] + katia[7] + guest3[7] + mario[7] + mauro[7] + renzo[7] + angeloCo[7] + mauroKarmen[7] + renzoKatia[7])));
        TextView cpOSS = (TextView) findViewById(R.id.cpOSS);
        cpOSS.setText(Float.toString(angelo[3] + co[3] + guest1[3] + guest2[3] + karmen[3] + katia[3] + guest3[3] + mario[3] + mauro[3] + renzo[3] + angeloCo[3] + mauroKarmen[3] + renzoKatia[3]));



    }

	/* @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.statistiche, menu);
		return true;
	}
    */

    //Called when the user clicks the GraficoPartiteGiocate button */
    public void GraficoPartiteGiocate(View v) {
        String contentData= "['Giocatore', 'Partite Giocate'],";

        if(angelo[0] != 0)  {
            contentData=contentData+"['Angelo', "+Float.toString(angelo[0])+"],";
        }
        if(co[0] != 0)  {
            contentData=contentData+"['Co', "+Float.toString(co[0])+"],";
        }
        if(karmen[0] != 0)  {
            contentData=contentData+"['Karmen', "+Float.toString(karmen[0])+"],";
        }
        if(katia[0] != 0)  {
            contentData=contentData+"['Katia', "+Float.toString(katia[0])+"],";
        }
        if(mario[0] != 0)  {
            contentData=contentData+"['Mario', "+Float.toString(mario[0])+"],";
        }
        if(mauro[0] != 0)  {
            contentData=contentData+"['Mauro', "+Float.toString(mauro[0])+"],";
        }
        if(renzo[0] != 0)  {
            contentData=contentData+"['Renzo', "+Float.toString(renzo[0])+"],";
        }

        Float others = guest1[0] + guest2[0] + guest3[0];
        if(others != 0)  {
            contentData=contentData+"['Others', "+Float.toString(others)+"],";
        }

        contentData=contentData + "        ]);"
                + "        var options = {"
                + "          title: 'Partite Giocate "+range+"',"
                + "          colors: ['green']"
                + "        };"
                + "        var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));";

        contentSaldo = contentSaldoTop +contentData + contentSaldoBottom;

        Intent intent = new Intent(this, GraficoPartiteGiocate.class);
        intent.putExtra(EXTRA_MESSAGE, contentSaldo);
        this.startActivity(intent);
    }


        //Called when the user clicks the GraficoManiGiocate button */
    public void GraficoManiGiocate(View v) {
        String contentData= "['Giocatore', 'Mani Giocate'],";

        if(angelo[7] != 0)  {
            contentData=contentData+"['Angelo', "+Float.toString(angelo[7])+"],";
        }
        if(co[7] != 0)  {
            contentData=contentData+"['Co', "+Float.toString(co[7])+"],";
        }
        if(karmen[7] != 0)  {
            contentData=contentData+"['Karmen', "+Float.toString(karmen[7])+"],";
        }
        if(katia[7] != 0)  {
            contentData=contentData+"['Katia', "+Float.toString(katia[7])+"],";
        }
        if(mario[7] != 0)  {
            contentData=contentData+"['Mario', "+Float.toString(mario[7])+"],";
        }
        if(mauro[7] != 0)  {
            contentData=contentData+"['Mauro', "+Float.toString(mauro[7])+"],";
        }
        if(renzo[7] != 0)  {
            contentData=contentData+"['Renzo', "+Float.toString(renzo[7])+"],";
        }

        Float others = guest1[7] + guest2[7] + guest3[7];
        if(others != 0)  {
            contentData=contentData+"['Others', "+Float.toString(others)+"],";
        }

        contentData=contentData + "        ]);"
                + "        var options = {"
                + "          title: 'Mani Giocate "+range+"',"
                + "          colors: ['orange']"
                + "        };"
                + "        var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));";

        contentSaldo = contentSaldoTop +contentData + contentSaldoBottom;

        Intent intent = new Intent(this, GraficoManiGiocate.class);
        intent.putExtra(EXTRA_MESSAGE, contentSaldo);
        this.startActivity(intent);
    }


    //Called when the user clicks the GraficoRientri button */
    public void GraficoRientri(View v) {
        String contentData= "['Giocatore', 'Rientri'],";

        contentData=contentData+"['Angelo', "+Float.toString(angelo[1])+"],";
        contentData=contentData+"['Co', "+Float.toString(co[1])+"],";
        contentData=contentData+"['Karmen', "+Float.toString(karmen[1])+"],";
        contentData=contentData+"['Katia', "+Float.toString(katia[1])+"],";
        contentData=contentData+"['Mario', "+Float.toString(mario[1])+"],";
        contentData=contentData+"['Mauro', "+Float.toString(mauro[1])+"],";
        contentData=contentData+"['Renzo', "+Float.toString(renzo[1])+"],";
        Float others = guest1[1] + guest2[1] + guest3[1];
        contentData=contentData+"['Others', "+Float.toString(others)+"],";

        contentData=contentData + "        ]);"
                + "        var options = {"
                + "          title: 'Rientri "+range+"',"
                + "          colors: ['red']"
                + "        };"
                + "        var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));";

        contentSaldo = contentSaldoTop +contentData + contentSaldoBottom;

        Intent intent = new Intent(this, GraficoRientri.class);
        intent.putExtra(EXTRA_MESSAGE, contentSaldo);
        this.startActivity(intent);
    }


    //Called when the user clicks the GraficoPartiteVinte button */
    public void GraficoPartiteVinte(View v) {
        String contentData= "['Giocatore', 'Partite Vinte'],";

        contentData=contentData+"['Angelo', "+Float.toString(angelo[2])+"],";
        contentData=contentData+"['Co', "+Float.toString(co[2])+"],";
        contentData=contentData+"['Karmen', "+Float.toString(karmen[2])+"],";
        contentData=contentData+"['Katia', "+Float.toString(katia[2])+"],";
        contentData=contentData+"['Mario', "+Float.toString(mario[2])+"],";
        contentData=contentData+"['Mauro', "+Float.toString(mauro[2])+"],";
        contentData=contentData+"['Renzo', "+Float.toString(renzo[2])+"],";
        Float others = guest1[2] + guest2[2] + guest3[2];
        contentData=contentData+"['Others', "+Float.toString(others)+"],";

        contentData=contentData + "        ]);"
                + "        var options = {"
                + "          title: 'Partite Vinte "+range+"',"
                + "        };"
                + "        var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));";

        contentSaldo = contentSaldoTop +contentData + contentSaldoBottom;

        Intent intent = new Intent(this, GraficoPartiteVinte.class);
        intent.putExtra(EXTRA_MESSAGE, contentSaldo);
        this.startActivity(intent);
    }


    //Called when the user clicks the GraficoPuntate button */
    public void GraficoPuntate(View v) {
        String contentData= "['Giocatore', 'Puntate (€)'],";

        if(angelo[3] != 0)  {
            contentData=contentData+"['Angelo', "+Float.toString(angelo[3])+"],";
        }
        if(co[3] != 0)  {
            contentData=contentData+"['Co', "+Float.toString(co[3])+"],";
        }
        if(karmen[3] != 0)  {
            contentData=contentData+"['Karmen', "+Float.toString(karmen[3])+"],";
        }
        if(katia[3] != 0)  {
            contentData=contentData+"['Katia', "+Float.toString(katia[3])+"],";
        }
        if(mario[3] != 0)  {
            contentData=contentData+"['Mario', "+Float.toString(mario[3])+"],";
        }
        if(mauro[3] != 0)  {
            contentData=contentData+"['Mauro', "+Float.toString(mauro[3])+"],";
        }
        if(renzo[3] != 0)  {
            contentData=contentData+"['Renzo', "+Float.toString(renzo[3])+"],";
        }

        Float others = guest1[3] + guest2[3] + guest3[3];
        if(others != 0)  {
            contentData=contentData+"['Others', "+Float.toString(others)+"],";
        }

        contentData=contentData + "        ]);"
                + "        var options = {"
                + "          title: 'Puntate "+range+"',"
                + "        };"
                + "        var chart = new google.visualization.PieChart(document.getElementById('chart_div'));";

        contentSaldo = contentSaldoTop +contentData + contentSaldoBottom;

        Intent intent = new Intent(this, GraficoPuntate.class);
        intent.putExtra(EXTRA_MESSAGE, contentSaldo);
        this.startActivity(intent);
    }

    //Called when the user clicks the GraficoVincite button */
    public void GraficoVincite(View v) {
        String contentData= "['Giocatore', 'Vincite (€)'],";

        if(angelo[4] != 0)  {
            contentData=contentData+"['Angelo', "+Float.toString(angelo[4])+"],";
        }
        if(co[4] != 0)  {
            contentData=contentData+"['Co', "+Float.toString(co[4])+"],";
        }
        if(karmen[4] != 0)  {
            contentData=contentData+"['Karmen', "+Float.toString(karmen[4])+"],";
        }
        if(katia[4] != 0)  {
            contentData=contentData+"['Katia', "+Float.toString(katia[4])+"],";
        }
        if(mario[4] != 0)  {
            contentData=contentData+"['Mario', "+Float.toString(mario[4])+"],";
        }
        if(mauro[4] != 0)  {
            contentData=contentData+"['Mauro', "+Float.toString(mauro[4])+"],";
        }
        if(renzo[4] != 0)  {
            contentData=contentData+"['Renzo', "+Float.toString(renzo[4])+"],";
        }

        Float others = guest1[4] + guest2[4] + guest3[4];
        if(others != 0)  {
            contentData=contentData+"['Others', "+Float.toString(others)+"],";
        }

        contentData=contentData + "        ]);"
                + "        var options = {"
                + "          title: 'Vincite "+range+"',"
                + "        };"
                + "        var chart = new google.visualization.PieChart(document.getElementById('chart_div'));";

        contentSaldo = contentSaldoTop +contentData + contentSaldoBottom;

        Intent intent = new Intent(this, GraficoVincite.class);
        intent.putExtra(EXTRA_MESSAGE, contentSaldo);
        this.startActivity(intent);
    }


    //Called when the user clicks the GraficoSaldo button */
    public void GraficoSaldo(View v) {
        String contentData= "['Giocatore', 'Saldo (€)'],";

            contentData=contentData+"['Angelo', "+Float.toString(angelo[5])+"],";
            contentData=contentData+"['Co', "+Float.toString(co[5])+"],";
        if(karmen[5] != 0)  {
            contentData=contentData+"['Karmen', "+Float.toString(karmen[5])+"],";
        }
            contentData=contentData+"['Katia', "+Float.toString(katia[5])+"],";
            contentData=contentData+"['Mario', "+Float.toString(mario[5])+"],";
            contentData=contentData+"['Mauro', "+Float.toString(mauro[5])+"],";
            contentData=contentData+"['Renzo', "+Float.toString(renzo[5])+"],";

        Float others = guest1[5] + guest2[5] + guest3[5];
        if(others != 0)  {
            contentData=contentData+"['Others', "+Float.toString(others)+"],";
        }

        contentData=contentData + "        ]);"
                + "        var options = {"
                + "          title: 'Saldo "+range+"',"
                + "          colors: ['#e0440e']"
                + "        };"
                + "        var chart = new google.visualization.BarChart(document.getElementById('chart_div'));";


        contentSaldo = contentSaldoTop +contentData + contentSaldoBottom;

        Intent intent = new Intent(this, GraficoSaldo.class);
        intent.putExtra(EXTRA_MESSAGE, contentSaldo);
        this.startActivity(intent);
    }


    //Called when the user clicks the GraficoPPartiteVinte button */
    public void GraficoPPartiteVinte(View v) {
        String contentData= "['Giocatore', '% Partite Vinte'],";

        contentData=contentData+"['Angelo', "+Float.toString(angelo[6])+"],";
        contentData=contentData+"['Co', "+Float.toString(co[6])+"],";
        contentData=contentData+"['Karmen', "+Float.toString(karmen[6])+"],";
        contentData=contentData+"['Katia', "+Float.toString(katia[6])+"],";
        contentData=contentData+"['Mario', "+Float.toString(mario[6])+"],";
        contentData=contentData+"['Mauro', "+Float.toString(mauro[6])+"],";
        contentData=contentData+"['Renzo', "+Float.toString(renzo[6])+"],";
        Float others = guest1[6] + guest2[6] + guest3[6];
        contentData=contentData+"['Others', "+Float.toString(others)+"],";

        contentData=contentData + "        ]);"
                + "        var options = {"
                + "          title: '% Partite Vinte "+range+"',"
                + "          colors: ['black']"
                + "        };"
                + "        var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));";

        contentSaldo = contentSaldoTop +contentData + contentSaldoBottom;

        Intent intent = new Intent(this, GraficoPPartiteVinte.class);
        intent.putExtra(EXTRA_MESSAGE, contentSaldo);
        this.startActivity(intent);
    }

}
