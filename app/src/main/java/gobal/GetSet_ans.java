package gobal;

/**
 * Created by Banyan1 on 4/13/2016.
 */
public class GetSet_ans {

    private String answer_id="";
    private String answer="";
    private String question_id="";
    private String date="";
    private String username="";
    /*********** Set Methods ******************/

    public void setanswer_id(String answer_id)
    {

        this.answer_id = answer_id;
    }

    public void setanswer(String answer)
    {

        this.answer = answer;
    }

    public void setquestion_id(String question_id)
    {

        this.question_id = question_id;
    }


    public void setdate(String date)
    {

        this.date = date;
    }

    public void setusername(String username)
    {

        this.username = username;
    }

    /*********** Get Methods ****************/


    public String getanswer_id()
    {

        return this.answer_id;
    }

    public String getanswer()
    {

        return this.answer;
    }
    public String getquestion_id()
    {

        return this.question_id;
    }

    public String getdate()
    {

        return this.date;
    }
    public String getusername()
    {

        return this.username;
    }
}
