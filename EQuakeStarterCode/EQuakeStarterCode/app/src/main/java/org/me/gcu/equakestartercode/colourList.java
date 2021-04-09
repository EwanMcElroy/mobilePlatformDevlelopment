package org.me.gcu.equakestartercode;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.Loader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

//
// Ewan McElroy - S1714836
//

public class colourList extends ArrayAdapter<WidgetClass>
{
    private final Context thisContext;
    private final ArrayList<WidgetClass> items;
    private boolean magSortHigh, magSortLow, mostEast, mostWest, mostNorth, mostSouth;
    private ArrayList<Float> mags = new ArrayList<Float>();

    public colourList(@NonNull Context context, @LayoutRes ArrayList<WidgetClass> list, Boolean _magSortHigh, Boolean _magSortLow, Boolean _mostEast, Boolean _mostWest, Boolean _mostNorth, Boolean _mostSouth) {
        super(context, 0, list);
        thisContext = context;
        items = list;
        magSortHigh = _magSortHigh;
        magSortLow = _magSortLow;
        mostEast = _mostEast;
        mostNorth = _mostNorth;
        mostSouth = _mostSouth;
        mostWest = _mostWest;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {

        View listItem = convertView;
        if(listItem == null)
        {
            listItem = LayoutInflater.from(thisContext).inflate(R.layout.activity_listview, parent, false);
        }

        TextView textView = (TextView)listItem.findViewById(R.id.label);



        if(!magSortHigh && !magSortLow && !mostEast && !mostWest && !mostNorth && !mostSouth) {
            WidgetClass item = items.get(position);
            textView.setText(item.getTitle());
            textView.setBackgroundColor(setBckgrndCol(item, textView));
        } else if (magSortHigh && !magSortLow && !mostEast && !mostWest && !mostNorth && !mostSouth) {
            Log.e("mag", "mag high");
            Collections.sort(items, new Comparator<WidgetClass>() {
                @Override
                public int compare(WidgetClass o1, WidgetClass o2) {
                    return o1.getMag() > o2.getMag() ? -1 : o1.getMag() == o2.getMag() ? 0 : 1;
                }
            });
            WidgetClass item = items.get(position);
            textView.setText(item.getTitle());
            textView.setBackgroundColor(setBckgrndCol(item, textView));
        } else if (magSortLow && !magSortHigh && !mostEast && !mostWest && !mostNorth && !mostSouth) {
            Log.e("East", "Most East");
            Collections.sort(items, new Comparator<WidgetClass>() {
                @Override
                public int compare(WidgetClass o1, WidgetClass o2) {
                    return o1.getMag() > o2.getMag() ? 1 : o1.getMag() == o2.getMag() ? 0 : -1;
                }
            });
            WidgetClass item = items.get(position);
            textView.setText(item.getTitle());
            textView.setBackgroundColor(setBckgrndCol(item, textView));
        } else if (!magSortHigh && !magSortLow && !mostEast && !mostWest && !mostNorth && mostSouth) {
            Log.e("South", "Displaying south");
            Collections.sort(items, new Comparator<WidgetClass>() {
                @Override
                public int compare(WidgetClass o1, WidgetClass o2) {
                    return Float.parseFloat(o1.getCoordLat()) > Float.parseFloat(o2.getCoordLat()) ? -1 : Float.parseFloat(o1.getCoordLat()) == Float.parseFloat(o2.getCoordLat()) ? 0 : 1;
                }
            });
            WidgetClass item = items.get(position);
            textView.setText(item.getTitle());
            textView.setBackgroundColor(setBckgrndCol(item, textView));
        } else if (!magSortHigh && !magSortLow && !mostEast && !mostWest && mostNorth && !mostSouth) {
            Log.e("North", "display north");
            Collections.sort(items, new Comparator<WidgetClass>() {
                @Override
                public int compare(WidgetClass o1, WidgetClass o2) {
                    return Float.parseFloat(o1.getCoordLat()) > Float.parseFloat(o2.getCoordLat()) ? 1 : Float.parseFloat(o1.getCoordLat()) == Float.parseFloat(o2.getCoordLat()) ? 0 : -1;
                }
            });
            WidgetClass item = items.get(position);
            textView.setText(item.getTitle());
            textView.setBackgroundColor(setBckgrndCol(item, textView));
        }  else if (!magSortHigh && !magSortLow && mostEast && !mostWest && !mostNorth && !mostSouth) {
            Log.e("North", "display north");
            Collections.sort(items, new Comparator<WidgetClass>() {
                @Override
                public int compare(WidgetClass o1, WidgetClass o2) {
                    return Float.parseFloat(o1.getCoordLong()) > Float.parseFloat(o2.getCoordLong()) ? -1 : Float.parseFloat(o1.getCoordLong()) == Float.parseFloat(o2.getCoordLong()) ? 0 : 1;
                }
            });
            WidgetClass item = items.get(position);
            textView.setText(item.getTitle());
            textView.setBackgroundColor(setBckgrndCol(item, textView));
        }  else if (!magSortHigh && !magSortLow && !mostEast && mostWest && !mostNorth && !mostSouth) {
            Log.e("North", "display north");
            Collections.sort(items, new Comparator<WidgetClass>() {
                @Override
                public int compare(WidgetClass o1, WidgetClass o2) {
                    return Float.parseFloat(o1.getCoordLong()) > Float.parseFloat(o2.getCoordLong()) ? 1 : Float.parseFloat(o1.getCoordLong()) == Float.parseFloat(o2.getCoordLong()) ? 0 : -1;
                }
            });
            WidgetClass item = items.get(position);
            textView.setText(item.getTitle());
            textView.setBackgroundColor(setBckgrndCol(item, textView));
        }
        return textView;
    }

    private int setBckgrndCol(WidgetClass item, TextView textView) {
        String magStart = "M ";
        int magStartLocation = item.getTitle().indexOf(magStart);
        int magEndLocation = item.getTitle().indexOf(',');
        String testString = item.getTitle();

        String magnitudeNumber = (testString.substring(item.getTitle().indexOf('M') + 1, item.getTitle().indexOf('M') + 6));
        Log.e("tag", magnitudeNumber);
        textView.setText(item.getTitle().substring(magStartLocation, magEndLocation));
        float magnitude = Float.parseFloat(magnitudeNumber);

        int colorNum = 0;
        item.setMag(magnitude);
        if (magnitude <= 1.0) {
            colorNum = Color.parseColor("#FFA500");
        } else if (magnitude <= 2.0) {
            colorNum = Color.parseColor("#0000FF");
        } else {
            colorNum = Color.parseColor("#8B0000");
        }

        return colorNum;
    }
}
