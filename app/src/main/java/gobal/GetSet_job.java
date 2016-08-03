package gobal;

/**
 * Created by Banyan1 on 4/13/2016.
 */
public class GetSet_job {


    private String JOBID = "job_id";
    private String WANT = "position";
    private String COMPY = "company";
    private String POSTDATE = "posted_date";
    private String EXPERIENCE = "experience";
    private String LOCATION = "location";
    private String JOBDESC = "job_description";
    private String SKILLS = "skills";


  ;
    /*********** Set Methods ******************/

    public void setJOBID(String JOBID)
    {

        this.JOBID = JOBID;
    }

    public void setWANT(String WANT)
    {

        this.WANT = WANT;
    }

    public void setCOMPY(String COMPY)
    {

        this.COMPY = COMPY;
    }


    public void setPOSTDATE(String POSTDATE)
    {

        this.POSTDATE = POSTDATE;
    }

    public void setEXPERIENCE(String EXPERIENCE)
    {

        this.EXPERIENCE = EXPERIENCE;
    }
    public void setLOCATION(String LOCATION)
    {

        this.LOCATION = LOCATION;
    }

    public void setJOBDESC(String JOBDESC)
    {

        this.JOBDESC = JOBDESC;
    }

    public void setSKILLS(String SKILLS)
    {

        this.SKILLS = SKILLS;
    }


    /*********** Get Methods ****************/


    public String getJOBID()
    {

        return this.JOBID;
    }

    public String getWANT()
    {

        return this.WANT;
    }
    public String getCOMPY()
    {

        return this.COMPY;
    }

    public String getPOSTDATE()
    {

        return this.POSTDATE;
    }
    public String getEXPERIENCE()
    {

        return this.EXPERIENCE;
    }
    public String getLOCATION()
    {

        return this.LOCATION;
    }
    public String getJOBDESC()
    {

        return this.JOBDESC;
    }
    public String getSKILLS()
    {

        return this.SKILLS;
    }
}
