package gobal;

/**
 * Created by Banyan1 on 4/13/2016.
 */
public class GetSet_New {


    private String news_id="";
    private String title="";
    private String descrption="";
    private String date="";

    /*********** Set Methods ******************/

    public void setnews_id(String news_id)
    {

        this.news_id = news_id;
    }

    public void settitle(String title)
    {

        this.title = title;
    }

    public void setdescrption(String descrption)
    {

        this.descrption = descrption;
    }


    public void setdate(String date)
    {

        this.date = date;
    }


    /*********** Get Methods ****************/


    public String getnews_id()
    {

        return this.news_id;
    }

    public String gettitle()
    {

        return this.title;
    }
    public String getdescrption()
    {

        return this.descrption;
    }

    public String getdate()
    {

        return this.date;
    }


}
