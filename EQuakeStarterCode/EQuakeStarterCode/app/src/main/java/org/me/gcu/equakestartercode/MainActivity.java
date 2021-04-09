package org.me.gcu.equakestartercode;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.*;
import java.util.stream.*;

import org.me.gcu.equakestartercode.R;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class MainActivity extends AppCompatActivity implements OnClickListener
{

    //
    // Ewan McElroy - S1714836
    //

    private TextView rawDataDisplay;
    private Button startButton;
    private Button highestMagButon, lowestMagButton, mostEast, mostWest, mostNorth, mostSouth;
    private String result = "";
    private String url1="";
    private String urlSource="http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
    private Boolean parsed = false;
    private ArrayList<WidgetClass> parsedList = new ArrayList<WidgetClass>();
    private ArrayList<Float> magnitudes = new ArrayList<Float>();
    private Boolean mostMagSort = false;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        ArrayList<org.me.gcu.equakestartercode.WidgetClass> alist = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("MyTag","in onCreate");
        // Set up the raw links to the graphical components
        rawDataDisplay = (TextView)findViewById(R.id.rawDataDisplay);
        startButton = (Button)findViewById(R.id.startButton);
        highestMagButon = (Button)findViewById(R.id.mostMag);
        lowestMagButton = (Button)findViewById(R.id.leastMag);
        mostEast = (Button)findViewById(R.id.mostEastern);
        mostWest = (Button)findViewById(R.id.mostWestern);
        mostNorth = (Button)findViewById(R.id.mostNorth);
        mostSouth = (Button)findViewById(R.id.mostSuthern);
        mostEast.setOnClickListener(this);
        mostWest.setOnClickListener(this);
        mostNorth.setOnClickListener(this);
        mostSouth.setOnClickListener(this);
        lowestMagButton.setOnClickListener(this);
        highestMagButon.setOnClickListener(this);
        listView = (ListView)findViewById(R.id.list_item);
        startButton.setOnClickListener(this);
        Log.e("MyTag","after startButton");
        // More Code goes here
        // Write list to Log for testing
        if (alist != null)
        {
            Log.e("MyTag","List not null");
            for (Object o : alist)
            {
                Log.e("MyTag",o.toString());
            }
        }
        else
        {
            Log.e("MyTag","List is null");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClick(View aview)
    {
        Log.e("MtRah", parsed.toString());
        if(!parsed && aview == startButton) {
            Log.e("MyTag", "in onClick");
            startProgress();
            Log.e("MyTag", "after startProgress");
        }
        if(parsed && aview == highestMagButon) {
            colourList adapter = new colourList(MainActivity.this, parsedList, true, false,  false, false, false, false);
            if (adapter == null)
            {
                Log.e("MyTag","Adapter error");
            }
            listView.setAdapter(adapter);
        }
        if (parsed && aview == lowestMagButton) {
            colourList adapter = new colourList(MainActivity.this, parsedList, false, true, false, false, false, false);
            if (adapter == null)
            {
                Log.e("MyTag","Adapter error");
            }
            listView.setAdapter(adapter);
        }

        if (parsed && aview == mostEast) {
            colourList adapter = new colourList(MainActivity.this, parsedList, false, false, true, false, false, false);
            if (adapter == null)
            {
                Log.e("MyTag","Adapter error");
            }
            listView.setAdapter(adapter);
        }

        if (parsed && aview == mostWest) {
            colourList adapter = new colourList(MainActivity.this, parsedList, false, false, false, true, false, false);
            if (adapter == null)
            {
                Log.e("MyTag","Adapter error");
            }
            listView.setAdapter(adapter);
        }

        if (parsed && aview == mostNorth) {
            colourList adapter = new colourList(MainActivity.this, parsedList, false, false, false, false, true, false);
            if (adapter == null)
            {
                Log.e("MyTag","Adapter error");
            }
            listView.setAdapter(adapter);
        }

        if (parsed && aview == mostSouth) {
            colourList adapter = new colourList(MainActivity.this, parsedList, false, false, false, false, false, true);
            if (adapter == null)
            {
                Log.e("MyTag","Adapter error");
            }
            listView.setAdapter(adapter);
        }
    }

    public void startProgress()
    {
        // Run network access on a separate thread;
        new Thread(new Task(urlSource)).start();
    } //

    // Need separate thread to access the internet resource over network
    // Other neater solutions should be adopted in later iterations.
    private class Task implements Runnable
    {
        private String url;

        public Task(String aurl)
        {
            url = aurl;
        }
        @Override
        public void run()
        {

            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";

            Log.e("MyTag","in run");

            try
            {
                Log.e("MyTag","in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                Log.e("MyTag","after ready");
                //
                // Now read the data. Make sure that there are no specific hedrs
                // in the data file that you need to ignore.
                // The useful data that you need is in each of the item entries
                //
                while ((inputLine = in.readLine()) != null)
                {
                    result = result + inputLine;
                    //Log.e("MyTag",inputLine);

                }
                in.close();
            }
            catch (IOException ae)
            {
                Log.e("MyTag", "ioexception in run");
            }

            //
            // Now that you have the xml data you can parse it
            //

            // Now update the TextView to display raw XML data
            // Probably not the best way to update TextView
            // but we are just getting started !

            MainActivity.this.runOnUiThread(new Runnable()
            {
                public void run()  {
                    //Foreach result parsed display that
                    //ArrayList<org.me.gcu.equakestartercode.WidgetClass> tempList = parseData(result);
                    parsedList = parseData(result);
                    //ArrayList<String> titles = new ArrayList<String>();
                    Log.e("My tag", String.valueOf(parsedList.size()));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if(!mostMagSort) {
                                showAlert(parsedList.get(position));
                            }
                        }
                    });
                    /*for (Object o : tempList) {
                        titles.add(o.toString());
                        Log.d("my Tag", o.toString());
                    }*/
                    /*for (int i = 0; i < widgets.size(); i++) {
                        titles.add(widgets.get(i).toString());
                    }*/

                    List Data = new ArrayList<String>();

                    for (int i = 0; i < parsedList.size(); i++) {

                        //Data.add("long:" + tempList.get(i).getCoordLong() + " " + "lat:" + tempList.get(i).getCoordLat() + " " + "Magnitude: " + tempList.get(i).getDescription().substring(tempList.get(i).getDescription().lastIndexOf(';') + 1));
                        Data.add(parsedList.get(i).getTitle().substring(parsedList.get(i).getTitle().indexOf('M'), parsedList.get(i).getTitle().indexOf(',')));
                    }
                    //ArrayAdapter adapter = new ArrayAdapter<WidgetClass>(MainActivity.this, R.layout.activity_listview, Data);
                    colourList adapter = new colourList(MainActivity.this, parsedList, false, false, false, false, false, false);
                    if (adapter == null)
                    {
                        Log.e("MyTag","Adapter error");
                    }
                    listView.setAdapter(adapter);

                    ajudstAdapter(listView, adapter);

                    adapter.notifyDataSetChanged();
                    Log.d("UI thread", "I am the UI thread");
                    //rawDataDisplay.setText(result);
                }
            });
        }
    }

    private void showAlert(WidgetClass item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(item.getDescription());
        builder.setCancelable(false);
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Info Closed", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private  void ajudstAdapter(ListView listView, colourList adapter){
        int listHeight = listView.getPaddingTop() - listView.getPaddingBottom();
        for (int i = 0; i < listView.getAdapter().getCount(); i++) {
            View listItem = listView.getAdapter().getView(i, null, listView);
            if(listItem instanceof ViewGroup) {
                listItem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            listItem.measure(0, 0);
            listHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = listHeight + (listView.getDividerHeight() * adapter.getCount() - 1);
        listView.setLayoutParams(params);
    }

    private ArrayList<org.me.gcu.equakestartercode.WidgetClass> parseData(String dataToParse)//url
    {
        org.me.gcu.equakestartercode.WidgetClass widget = null;
        ArrayList<WidgetClass> alist = new ArrayList<org.me.gcu.equakestartercode.WidgetClass>();
        try
        {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput( new StringReader( dataToParse ) );
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                // Found a start tag
                if(eventType == XmlPullParser.START_TAG)
                {
                    //In the future don't hand out spaghetti code pls.
                    switch  (xpp.getName().toLowerCase())
                    {
                        case "item":
                            if(alist == null) {
                                alist = new ArrayList<org.me.gcu.equakestartercode.WidgetClass>();
                            }
                            break;
                        case "title":
                            //Log.e("MyTag","Item Start Tag found");
                            widget = new org.me.gcu.equakestartercode.WidgetClass();
                            String title;
                            title = xpp.nextText();
                            widget.setTitle(title);
                            break;
                        case "description":
                            // Now just get the associated text
                            String aDescription = xpp.nextText();
                            // Do something with text
                            //Log.e("MyTag","Bolt is " + aDescription);
                            widget.setDescription(aDescription);
                            break;
                        case "link":
                            // Now just get the associated text
                            String aLink = xpp.nextText();
                            // Do something with text
                            //Log.e("MyTag","Nut is " + aLink);
                            widget.setLink(aLink);
                            break;
                        case "pubdate":
                            // Now just get the associated text
                            String aPubDate = xpp.nextText();
                            // Do something with text
                            //Log.e("MyTag","Washer is " + aPubDate);
                            widget.setPublishDate(aPubDate);
                            break;
                        case "categorytype":
                            // Now just get the associated text
                            String aCategoryType = xpp.nextText();
                            // Do something with text
                            //Log.e("MyTag","Washer is " + aPubDate);
                            widget.setCategoryType(aCategoryType);
                            break;
                        case "lat":
                            // Now just get the associated text
                            String aCoordLat = xpp.nextText();
                            // Do something with text
                            //Log.e("MyTag","Washer is " + aPubDate);
                            Log.e("Parse", aCoordLat);
                            widget.setCoordLat(aCoordLat);
                            break;
                        case "long":
                            // Now just get the associated text
                            String aCoordLong = xpp.nextText();
                            // Do something with text
                            //Log.e("MyTag","Washer is " + aPubDate);
                            widget.setCoordLong(aCoordLong);
                            Log.e("Long", widget.getCoordLong());
                            break;
                    }
                }
                if(eventType == XmlPullParser.END_TAG)
                {
                    if (xpp.getName().equalsIgnoreCase("item"))
                    {
                        alist.add(widget);
                    }
                }
                // Get the next event
                eventType = xpp.next();

            } // End of while

            //return alist;
        }
        catch (XmlPullParserException ae1)
        {
            Log.e("MyTag","Parsing error" + ae1.toString());
        }
        catch (IOException ae1)
        {
            Log.e("MyTag","IO error during parsing");
        }
        Log.e("MyTag","End document");

        parsed = true;
        Log.e("MY TAG", parsed.toString());
        return alist;
    }
}