package gobal;

/**
 * Created by Banyan1 on 4/13/2016.
 */
public class GetSet_QandA {

    private String ask_id="";
    private String qustion="";
    private String time="";
    private String count="";
    private String date="";
    private String username="";
    /*********** Set Methods ******************/

    public void setask_id(String ask_id)
    {

        this.ask_id = ask_id;
    }

    public void setqustion(String qustion)
    {

        this.qustion = qustion;
    }

    public void setdate(String date)
    {

        this.date = date;
    }

    public void setusername(String username)
    {

        this.username = username;
    }
    public void settime(String time)
    {

        this.time = time;
    }
    public void setcount(String count)
    {

        this.count = count;
    }

    /*********** Get Methods ****************/

    public String getask_id()
    {

        return this.ask_id;
    }

    public String getqustion()
    {
        return this.qustion;
    }

    public String getdate()
    {

        return this.date;
    }
    public String getusername()
    {

        return this.username;
    }
    public String gettime()
    {

        return this.time;
    }
    public String getcount()
    {

        return this.count;
    }
}
