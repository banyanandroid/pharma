package gobal;

/**
 * Created by Banyan1 on 4/13/2016.
 */
public class GetSetView {

    private String position="";
    private String company="";
    private String posted_date="";
    private String experience="";
    private String location="";
    private String job_description="";
    private String skills="";

    /*********** Set Methods ******************/

    public void setposition(String position)
    {

        this.position = position;
    }

    public void setcompany(String company)
    {

        this.company = company;
    }

    public void setposted_date(String posted_date)
    {

        this.posted_date = posted_date;
    }


    public void setexperience(String experience)
    {

        this.experience = experience;
    }

    public void setlocation(String location)
    {

        this.location = location;
    }

    public void setjob_description(String job_description)
    {

        this.job_description = job_description;
    }

    public void setskills(String skills)
    {

        this.skills = skills;
    }

    /*********** Get Methods ****************/


    public String getposition()
    {

        return this.position;
    }

    public String getcompany()
    {

        return this.company;
    }
    public String getposted_date()
    {

        return this.posted_date;
    }

    public String getexperience()
    {

        return this.experience;
    }
    public String getlocation()
    {

        return this.location;
    }
    public String getjob_description()
    {

        return this.job_description;
    }
    public String getskills()
    {

        return this.skills;
    }
}
