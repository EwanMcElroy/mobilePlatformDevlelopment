package org.me.gcu.equakestartercode;

//
// Ewan McElroy - S1714836
//

public class WidgetClass
{
    private String title;
    private String description;
    private String link;
    private String publishDate;
    private String categoryType;
    private String coordLat;
    private String coordLong;
    private float mag;

    public WidgetClass()
    {
        title = "";
        description = "";
        link = "";
        publishDate = "";
        categoryType = "";
        coordLat = "";
        coordLong = "";
    }

    public WidgetClass(String aTitle, String aDescription, String aLink, String aPublishDate, String aCategoryType, String aCoordLat, String aCoordLong)
    {
        title = aTitle;
        description = aDescription;
        link = aLink;
        publishDate = aPublishDate;
        categoryType = aCategoryType;
        coordLat = aCoordLat;
        coordLong = aCoordLong;
    }

    public String getTitle()
    {
        return title;
    }
    public String getDescription()
    {
        return description;
    }
    public String getLink()
    {
    return link;
    }
    public String getPublishDate()
    {
    return publishDate;
    }
    public String getCategoryType()
    {
    return categoryType;
    }
    public String getCoordLat()
    {
    return coordLat;
    }
    public String getCoordLong()
    {
    return coordLong;
    }
    public float getMag() { return mag;};

    public void setTitle(String Title)
    {
        title = Title;
    }
    public void setDescription(String aDescription)
    {
        description = aDescription;
    }



    public void setLink(String aLink)
    {
        link = aLink;
    }



    public void setPublishDate(String aPublishDate)
    {
        publishDate = aPublishDate;
    }



    public void setCategoryType(String aCategoryType)
    {
        categoryType = aCategoryType;
    }



    public void setCoordLat(String aCoordLat)
    {
        coordLat = aCoordLat;
    }



    public void setCoordLong(String aCoordLong)
    {
        coordLong = aCoordLong;
    }
    void setMag(float _mag) { mag = _mag; }

} // End of class

