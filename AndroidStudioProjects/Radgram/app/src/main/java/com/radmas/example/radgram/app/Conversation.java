package com.radmas.example.radgram.app;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by carlos on 27/05/14.
 */

public class Conversation {

    /*private ArrayList<Message> arrayMessages= new ArrayList <Message> ();
    private Message array[]= new Message[arrayMessages.size()];
*/
    @SerializedName("_id")
    String _id;
    @SerializedName("_rev")
    String _rev;
    @SerializedName("user_Telephone")
    String my_phone;
    @SerializedName("contact_Telephone")
    String firend_phone;
    @SerializedName("contact_Name")
    String contact_name;
    ArrayList<Message> conversation = new ArrayList<Message>();


    public Conversation (){
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getRev() {
        return _rev;
    }

    public void setRev(String rev) {
        this._rev = rev;
    }

    public String getMy_phone() {
        return my_phone;
    }

    public void setMy_phone(String my_phone) {
        this.my_phone = my_phone;
    }

    public String getFirend_phone() {
        return firend_phone;
    }

    public void setFirend_phone(String firend_phone) {
        this.firend_phone = firend_phone;
    }

    public ArrayList<Message> getConversation() {
        return conversation;
    }

    public void setConversation(ArrayList<Message> conversation) {
        this.conversation = conversation;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    @Override
    public String toString(){
        return _id + " " + _rev;
    }

}


