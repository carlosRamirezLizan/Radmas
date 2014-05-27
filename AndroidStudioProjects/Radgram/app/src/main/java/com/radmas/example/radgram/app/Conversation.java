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
    ArrayList<Message> conversation = new ArrayList<Message>();



/*
   public Conversation (ArrayList<Message> arrayList){
       this.arrayMessages = arrayList;
   }
*/

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

    @Override
    public String toString(){
        return _id + " " + _rev;
    }

    /*
    public ArrayList<Message> getArrayMessages() {
        return arrayMessages;
    }

    public Message[] getArray() {
        return array;
    }

    public static void ordenar(Message lista[]){
        //Usamos un bucle anidado
        for(int i=0;i<(lista.length-1);i++){
            for(int j=i+1;j<lista.length;j++){
                Message message1 = lista[i];
                Message message2 = lista[j];
                if(message1.getTimeSent()>message2.getTimeSent()){
                    //Intercambiamos valores
                    Message variableAuxiliar=lista[i];
                    lista[i]=lista[j];
                    lista[j]=variableAuxiliar;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "" +
                 arrayMessages;
    }*/
}


