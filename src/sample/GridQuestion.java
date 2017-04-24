package sample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flavius.sefciuc on 4/5/2017.
 */
public class GridQuestion extends Question {

    int upperLimit;
    int lowerLimit;
    List<Precode<String,String>> IterationList = new ArrayList<Precode<String,String>>();
    List<Precode<String,String>> AnswerList = new ArrayList<Precode<String,String>>();

    public GridQuestion() {
        this.upperLimit = 1;
        this.lowerLimit = 1;
        this.name="";
        this.questionText="";
        this.questionType=questionTypes.GRID;
        this.questionOrder=questionOrders.NORMAL;
    }

    public int getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(int upperLimit) {
        this.upperLimit = upperLimit;
    }

    public int getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(int lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public List<Precode<String, String>> getIterationList() {
        return IterationList;
    }

    public void setIterationList(List<Precode<String, String>> iterationList) {
        IterationList = iterationList;
    }

    public List<Precode<String, String>> getAnswerList() {
        return AnswerList;
    }

    public void setAnswerList(List<Precode<String, String>> answerList) {
        AnswerList = answerList;
    }

    public String generateTableForCachePage()
    {
        String generatedTable;
        generatedTable="";
        int numarCategorii=IterationList.size();
        int numarRaspunsuri=AnswerList.size();
        String numeIntrebare=this.getName();

        for(int i=0;i<=numarCategorii;i++)
        {
            generatedTable=generatedTable+"<tr>\n";

            for(int j=0;j<=numarRaspunsuri;j++)
            {
                if (i==0)
                {
                    if(j==0){
                        generatedTable=generatedTable+"\t<td id=\"Cell.0.0\"/>";
                    }
                    else
                    {
                        generatedTable=generatedTable+"\t<td id=\"Cell."+j+".0\" class=\"mrGridQuestionText\" style=\"text-Align: Center;vertical-align: Middle;width: 10%;\">\n" +
                                "\t\t<span class=\"mrQuestionText\" style=\"\">"+AnswerList.get(j-1).getLabel()+"</span>\n" +
                                "\t</td>\n";
                    }
                }
                else
                {
                    if(j==0)
                    {
                        generatedTable=generatedTable+"\t<td id=\"Cell.0."+i+"  \" class=\"mrGridCategoryText\" style=\"text-Align: Left;vertical-align: Middle;\">\n" +
                                "\t\t<span class=\"mrQuestionText\" style=\"\">"+IterationList.get(i-1).getLabel()+"</span>\n" +
                                "\t</td>\n";
                    }
                    else
                    {
                        generatedTable=generatedTable+"\t<td id=\"Cell."+j+"."+i+"\" style=\"text-Align: Center;vertical-align: Middle;\">\n" +
                                "\t\t<div></div>\n" +
                                "\t\t<input type=\"checkbox\" name=\"_Q"+numeIntrebare+"_Q__"+i+"_Q"+numeIntrebare+"__scale_C__"+j+"\" id=\"_Q0_Q"+(i-1)+"_Q0_C"+(j-1)+"\" class=\"mrMultiple\" style=\"\" value=\"__"+j+"\"></input>\n" +
                                "\t</td>\n";
                    }
                }
            }
            generatedTable=generatedTable+"</tr>\n";
        }

        return generatedTable;
    }
    @Override
    public String toString() {
        String returnedString;
        returnedString = "\t" + this.getName() + " \"" + this.getQuestionText() + "\"\n";
        returnedString = returnedString + "\tloop\n";
        returnedString = returnedString + "\t{\n";

        for (int i = 0; i < IterationList.size(); i++) {
            returnedString= returnedString + "\t\t" + IterationList.get(i).getPrecod() + " \"" + IterationList.get(i).getLabel().replace("&","&amp;") + "\",\n";
        }
        returnedString = returnedString.replaceAll(",$", "");

        returnedString = returnedString + "\t} fields\n";
        returnedString = returnedString + "\t(\n";
        returnedString = returnedString + "\t\t" + this.getName() + "_scale \"\"\n";
        returnedString = returnedString + "\t\tcategorical [" +this.getLowerLimit() + ".." + this.getUpperLimit() + "]\n";
        returnedString = returnedString + "\t\t{\n";

        for (int i = 0; i < AnswerList.size(); i++) {
            if ( AnswerList.get(i).getLabel().toLowerCase().equals("none") || AnswerList.get(i).getLabel().toLowerCase().equals("none of these") || AnswerList.get(i).getLabel().toLowerCase().equals("none of the above") || AnswerList.get(i).getLabel().toLowerCase().equals("don't know")) {
                returnedString = returnedString + "\t\t\t" + AnswerList.get(i).getPrecod() + " \"" + AnswerList.get(i).getLabel().replace("&", "&amp;") + "\" fix exclusive,\n";
            }else
            {
                returnedString = returnedString + "\t\t\t" + AnswerList.get(i).getPrecod() + " \"" + AnswerList.get(i).getLabel().replace("&", "&amp;") + "\",\n";
            }
        }
        returnedString = returnedString.replaceAll(",$", "");

        returnedString = returnedString + "\t\t};\n" ;
        returnedString = returnedString + "\t) expand grid;" ;

        return returnedString;
    }

    public String GetGridProgresiveTemplate()
    {
        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0Transitional//EN\"\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html lang=\"en-CA\">\n" +
                "\t<head>\n" +
                "\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/><meta http-equiv=\"Content-Language\" content=\"en-CA\"/><meta http-equiv=\"Expires\" content=\"-1\"/><meta http-equiv=\"Pragma\" content=\"no-cache\"/><meta name=\"Robots\" content=\"noindex, nofollow\"/><meta name=\"Generator\" content=\"Interviewer Server HTML Player 1.0.23..312\"/><meta name=\"Template\" content=\"VisualAssets/iis-fish/iis-fish.htm\"/><meta http-equiv=\"X-UA-Compatible\" content=\"IE=Edge\"/>\n" +
                "\t\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"/>\n" +
                "\t\t<title>Research</title>\n" +
                "\t\t<div></div>\n" +
                "\t\t<span class=\"mrBannerText\" style=\"\"><link href=\"https://cdn.ipsosinteractive.com/deploy/templates/iis-sharky/v2.0/css/style.css\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "<script type=\"text/javascript\" src=\"https://cdn.ipsosinteractive.com/deploy/templates/iis-sharky/v2.0/js/jq-1.11.0.and.migrate-1.2.1.min.js\"></script></span>\n" +
                "\t\t<!--[if lt IE 9]>\n" +
                "\t\t  <script src=\"https://cdn.ipsosinteractive.com/deploy/templates/iis-sharky/v1.0/js/respond.min.js\"></script>\n" +
                "\t\t<![endif]-->\n" +
                "\t\t<div></div>\n" +
                "\t\t<span class=\"mrBannerText\" style=\"\"></span>\n" +
                "\t</head>\n" +
                "\t<body onload=\"noBack();\" onpageshow=\"if (event.persisted) noBack();\" onunload=\"\">\n" +
                "\t\t<form name=\"mrForm\" id=\"mrForm\" action=\"http://localhost:5080\" method=\"post\"><input type=\"hidden\" name=\"I.Engine\" value=\"Server\"/><input type=\"hidden\" name=\"I.Project\" value=\"S1999999\"/><input type=\"hidden\" name=\"I.Session\" value=\"jgueebxxvivejewyeqhintlkkyaqaaaa\"/><input type=\"hidden\" name=\"I.SavePoint\" value=\"Q1\"/><input type=\"hidden\" name=\"I.Renderer\" value=\"HTMLPlayer\"/><div id=\"wrapper\">\n" +
                "\t\t\t\t<div id=\"navigation-bar\" class=\"navbar navbar-inverse navbar-fixed-top is-fish-layout\">\n" +
                "\t\t\t\t\t<div class=\"container\">\n" +
                "\t\t\t\t\t\t<div class=\"navbar-header\">\n" +
                "\t\t\t\t\t\t\t<div class=\"navbar-brand\">\n" +
                "\t\t\t\t\t\t\t\t<div></div>\n" +
                "\t\t\t\t\t\t\t\t<span class=\"mrBannerText\" style=\"\"><img aria-hidden=\"true\" src=\"https://cdn.ipsosinteractive.com/deploy/templates/iis-fish-template/img/logo/no_logo_small.png\" alternate-logo=\"https://cdn.ipsosinteractive.com/deploy/templates/iis-fish-template/img/logo/no_logo.png\"/><style>.progress-percentage{margin-left : 0 !important}  .navbar-brand{ padding-left : 0 !important };</style></span>\n" +
                "\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t<div class=\"progress-bar-container\">\n" +
                "\t\t\t\t\t\t\t\t<div class=\"progress-percentage\">\n" +
                "\t\t\t\t\t\t\t\t\t<span class=\"progress-percentage-invisible-point\">.</span>\n" +
                "\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t\t<div class=\"progress\">\n" +
                "\t\t\t\t\t\t\t\t\t<div id=\"progress-bar\" class=\"progress-bar\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<table style=\"height: 100%; width: 100%\"><tr><td class=\"mrProgressText\">0%</td></tr></table></div>\n" +
                "\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t</div>\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t\t<div class=\"container\">\n" +
                "\t\t\t\t\t<div id=\"content\" class=\"panel panel-default invisible\">\n" +
                "\t\t\t\t\t\t<div></div>\n" +
                "\t\t\t\t\t\t<span class=\"mrBannerText\" style=\"\"><div id=\"current-question\">Respondent Serial: -1.   Current question: #NumeIntrebare</div></span>\n" +
                "\t\t\t\t\t\t<div></div>\n" +
                "\t\t\t\t\t\t<span class=\"mrBannerText\" style=\"\"></span>\n" +
                "\t\t\t\t\t\t<div></div>\n" +
                "\t\t\t\t\t\t<span class=\"mrBannerText\" style=\"\"><span id=\"AuxiliarFields\" style=\"display:none;\">EN</span></span>\n" +
                "\t\t\t\t\t\t<div></div>\n" +
                "\t\t\t\t\t\t<span class=\"mrBannerText\" style=\"\"></span>\n" +
                "\t\t\t\t\t\t<div></div>\n" +
                "\t\t\t\t\t\t<span class=\"mrBannerText\" style=\"\"></span>\n" +
                "\t\t\t\t\t\t<div></div>\n" +
                "\t\t\t\t\t\t<span class=\"mrBannerText\" style=\"\"><script id=\"StudyJSONProperties\" class=\"StudyJSONProperties\">$('body').data('studyJSON',  {\"theFilePath\":\"https://cdn.ipsosinteractive.com/deploy/templates/iis-sharky/v2.0/QuestionTemplates/\",\"StudyProperties\":{\"GridAlternateColorRow\":true,\"GridAlternateColorCol\":true, \"endStudyProperties\":\"\"},\"ProgressBarVersion\":\"minimal\", \"SnifferData\":  {}, \"StudyData\": {\"IDType\":\"test\"} , \"endStudyJSON\":\"\"});</script></span>\n" +
                "\t\t\t\t\t\t<div></div>\n" +
                "\t\t\t\t\t\t<span class=\"mrBannerText\" style=\"\"><style>#progress-bar,.progress-bar-container,#progressBar { display: none; } #progress_bar { visibility: hidden;} </style></span>\n" +
                "\t\t\t\t\t\t<div></div>\n" +
                "\t\t\t\t\t\t<span class=\"mrBannerText\" style=\"\"><script type=\"text/javascript\" src=\"https://cdn.ipsosinteractive.com/deploy/templates/iis-template/js/CortexASFeature/CortexASInfo.js\">//answersummart.js</script><script type=\"text/javascript\"> var cortexASOutput='<table border=\"1\" width=\"100%\" style=\"font-size:14px\"><b><i>ANSWER SUMMARY</i></b><tr><td><BR/><b><i>LPS_browserIdentifier</i></b>: <i>LPS_browserIdentifier</i></td><td><br/><b>msie</b></td></tr><tr><td><BR/></td></tr><tr><td><BR/><b><i>LPS_browserVersion</i></b>: <i>LPS_browserVersion</i></td><td><br/><b>7.0</b></td></tr><tr><td><BR/></td></tr><tr><td><BR/><b><i>LPS_browserMajorVersion</i></b>: <i>LPS_browserMajorVersion</i></td><td><br/><b>7</b></td></tr><tr><td><BR/></td></tr><tr><td><BR/><b><i>LPS_browserMinorVersion</i></b>: <i>LPS_browserMinorVersion</i></td><td><br/><b>0</b></td></tr><tr><td><BR/></td></tr><tr><td><BR/><b><i>LPS_browserEngine</i></b>: <i>LPS_browserEngine</i></td><td><br/><b>msie</b></td></tr><tr><td><BR/></td></tr><tr><td><BR/><b><i>LPS_browserEngineVersion</i></b>: <i>LPS_browserEngineVersion</i></td><td><br/><b>7.0</b></td></tr><tr><td><BR/></td></tr><tr><td><BR/><b><i>LPS_fullUserAgentString</i></b>: <i>LPS_fullUserAgentString</i></td><td><br/><b>mozilla/4.0 (compatible; msie 7.0; windows nt 6.1; win64; x64; trident/7.0; .net clr 2.0.50727; slcc2; .net clr 3.5.30729; .net clr 3.0.30729; .net4.0c; .net4.0e)</b></td></tr><tr><td><BR/></td></tr><tr><td><BR/><b><i>LPS_operatingSystemIdentifier</i></b>: <i>LPS_operatingSystemIdentifier</i></td><td><br/><b>win</b></td></tr><tr><td><BR/></td></tr><tr><td><BR/><b><i>LPS_operatingSystemVersion</i></b>: <i>LPS_operatingSystemVersion</i></td><td><br/><b>unknown</b></td></tr><tr><td><BR/></td></tr><tr><td><BR/><b><i>LPS_isFlashInstalled</i></b>: <i>LPS_isFlashInstalled</i></td><td><br/><b>Yes</b></td></tr><tr><td><BR/></td></tr><tr><td><BR/><b><i>LPS_flashVersion</i></b>: <i>LPS_flashVersion</i></td><td><br/><b>15</b></td></tr><tr><td><BR/></td></tr><tr><td><BR/><b><i>LPS_browserURL</i></b>: <i>LPS_browserURL</i></td><td><br/><b>file:///D:/Users/flavius.sefciuc/AppData/Roaming/IBM/SPSS/DataCollection/6/Base%20Professional/Interview/Cachequestion.htm</b></td></tr><tr><td><BR/></td></tr><tr><td><BR/><b><i>LPS_javascriptVersion</i></b>: <i>LPS_javascriptVersion</i></td><td><br/><b>1.3</b></td></tr><tr><td><BR/></td></tr><tr><td><BR/><b><i>LPS_screenWidth</i></b>: <i>LPS_screenWidth</i></td><td><br/><b>1920</b></td></tr><tr><td><BR/></td></tr><tr><td><BR/><b><i>LPS_screenHeight</i></b>: <i>LPS_screenHeight</i></td><td><br/><b>1080</b></td></tr><tr><td><BR/></td></tr><tr><td><BR/><b><i>LPS_screenAvailWidth</i></b>: <i>LPS_screenAvailWidth</i></td><td><br/><b>1920</b></td></tr><tr><td><BR/></td></tr><tr><td><BR/><b><i>LPS_screenAvailHeight</i></b>: <i>LPS_screenAvailHeight</i></td><td><br/><b>1050</b></td></tr><tr><td><BR/></td></tr><tr><td><BR/><b><i>LPS_screenColorDepth</i></b>: <i>LPS_screenColorDepth</i></td><td><br/><b>24</b></td></tr><tr><td><BR/></td></tr><tr><td><BR/><b><i>LPS_screenPixelDepth</i></b>: <i>LPS_screenPixelDepth</i></td><td><br/><b>24</b></td></tr><tr><td><BR/></td></tr><tr><td><BR/><b><i>LPS_navigatorAppCodeName</i></b>: <i>LPS_navigatorAppCodeName</i></td><td><br/><b>Mozilla</b></td></tr><tr><td><BR/></td></tr><tr><td><BR/><b><i>LPS_navigatorAppName</i></b>: <i>LPS_navigatorAppName</i></td><td><br/><b>Netscape</b></td></tr><tr><td><BR/></td></tr><tr><td><BR/><b><i>LPS_navigatorAppVersion</i></b>: <i>LPS_navigatorAppVersion</i></td><td><br/><b>4.0 (compatible; MSIE 7.0; Windows NT 6.1; Win64; x64; Trident/7.0; .NET CLR 2.0.50727; SLCC2; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET4.0C; .NET4.0E)</b></td></tr><tr><td><BR/></td></tr><tr><td><BR/><b><i>LPS_navigatorCookiesEnabled</i></b>: <i>LPS_navigatorCookiesEnabled</i></td><td><br/><b>true</b></td></tr><tr><td><BR/></td></tr><tr><td><BR/><b><i>LPS_navigatorPlatform</i></b>: <i>LPS_navigatorPlatform</i></td><td><br/><b>Win64</b></td></tr><tr><td><BR/></td></tr><tr><td><BR/><b><i>LPS_flashFullVersion</i></b>: <i>LPS_flashFullVersion</i></td><td><br/><b>23.0.0</b></td></tr><tr><td><BR/></td></tr><tr><td><BR/><b><i>LPS_timeZoneOffset</i></b>: <i>LPS_timeZoneOffset</i></td><td><br/><b>-180</b></td></tr><tr><td><BR/></td></tr></table>'</script></span>\n" +
                "\t\t\t\t\t\t<div class=\"question-container\">\n" +
                "\t\t\t\t\t\t\t<div></div>\n" +
                "\t\t\t\t\t\t\t<span class=\"mrBannerText\" style=\"\"><script class=\"customJSONproperties\" type=\"application/json\">{ \"QType\" : \"GRID\", \"QSubType\" : \"MA\", \"QOrientation\" : \"ROW\", \"QTopHeaders\" : false, \"QSideHeaders\" : false, \"questionLook\":\"GridProgressive\", \"InputPosition\": \"hidden\", \"AnimationAppearTime\": 500, \"AnimationAppearTime\": 300, \"AutoAdvanced\": true, \"TimeBetweenScreeenAutoNext\": 300, \"ProgressBarType\": \"smart\", \"AutoSubmitWhenFinished\": true, \"MaxHeightWidthRatio\": 1.5, \"NextArrowEnabledWhenAnswered\": true}</script></span>\n" +
                "\t\t\t\t\t\t\t<div id=\"question\" class=\"question\">\n" +
                "\t\t\t\t\t\t\t\t<div></div>\n" +
                "\t\t\t\t\t\t\t\t<span class=\"mrQuestionText\" style=\"\">#TextIntrebare</span>\n" +
                "\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t<div class=\"row pictureTop\">\n" +
                "\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t<div class=\"question-controls-container row\">\n" +
                "\t\t\t\t\t\t\t\t<div class=\"picture-left pic-xs-12 pic-sm-3\">\n" +
                "\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t\t<div class=\"the-controls col-xs-12  pic-xs-12 pic-sm-9\">\n" +
                "\t\t\t\t\t\t\t\t\t<div></div>\n" +
                "\t\t\t\t\t\t\t\t\t<span style=\"\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<table summary=\"Text intrebare???\" class=\"mrQuestionTable\" style=\"display: inline-block;\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t#TabelGenerat\n" +
                "\t\t\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t\t\t</span>\n" +
                "\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t\t<div class=\"picture-right pic-xs-12 pic-sm-3\">\n" +
                "\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t<div class=\"row pictureBottom\">\n" +
                "\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t<div id=\"nav-controls\" class=\"btn-container nav-center\">\n" +
                "\t\t\t\t\t\t\t<input type=\"submit\" name=\"_NPrev\" class=\"mrPrev\" style=\"\" value=\"Previous\" alt=\"Previous\"></input>\n" +
                "\t\t\t\t\t\t\t<input type=\"submit\" name=\"_NNext\" class=\"mrNext\" style=\"\" value=\"Next\" alt=\"Next\"></input>\n" +
                "\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t</div>\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t\t<footer id=\"footer\" class=\"footer invisible\">\n" +
                "\t\t\t\t\t<hr/>\n" +
                "\t\t\t\t\t<div class=\"container container-secondary\">\n" +
                "\t\t\t\t\t\t<div class=\"row\">\n" +
                "\t\t\t\t\t\t\t<div></div>\n" +
                "\t\t\t\t\t\t\t<span class=\"mrBannerText\" style=\"\">\n" +
                "\t\t\t\t\t\t\t</span>\n" +
                "\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t</div>\n" +
                "\t\t\t\t</footer>\n" +
                "\t\t\t</div>\n" +
                "\t\t\t<div></div>\n" +
                "\t\t\t<span class=\"mrBannerText\" style=\"\"><script type=\"text/javascript\" src=\"https://cdn.ipsosinteractive.com/deploy/templates/iis-sharky/v2.0/js/functions.js\"></script>\n" +
                "<script type=\"text/javascript\" src=\"https://cdn.ipsosinteractive.com/deploy/templates/iis-sharky/v2.0/js/core.js\"></script></span>\n" +
                "\t\t</form></body>\n" +
                "\t<div></div>\n" +
                "\t<span class=\"mrBannerText\" style=\"\"></span>\n" +
                "</html>\n";
    }

}
