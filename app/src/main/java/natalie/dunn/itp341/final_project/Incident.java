package natalie.dunn.itp341.final_project;

/**
 * Created by natalieanndunn on 12/3/16.
 */
public class Incident {

    public String date;
    public String time;
    public String location;
    public String description;

    public Incident(){

    }
    //constructor of an Incident object
    public Incident(String date,String time,String location, String description){
        this.date = date;
        this.time = time;
        this.location  = location;
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    //returns a string joining all attributes of an Incident. Used to set text of listView row in the my Incidents
    //and All Incidents listViews.
    public String joinAll(String date, String time, String location, String description) {
        return date + " " + time + " " + location + " " + description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}
}