package com.untitledapps.meetasweedt;

import android.os.AsyncTask;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

import java.net.URL;

/**
 * Created by fredr on 2016-10-04.
 */

public class MicrosoftTranslation{
    String translatedText;
    public MicrosoftTranslation() {
        //For translation

    }

    public String TranslateString(String from, String to, String message) {
        new MyAsyncTask() {
            protected void onPostExecute(Boolean result) {
                System.out.println(translatedText);;
            }
        }.execute();
        return translatedText;
    }
    class MyAsyncTask extends AsyncTask<Void, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(Void... arg0) {
            Translate.setClientId("MeetASweedt");
            Translate.setClientSecret("aF+QBqtp8FANWemB7hqvkYPWrUbVwl85aih3n1vtDsc=");
            try {
                translatedText = Translate.execute("Hej jag heter Daniel och jag gillar inte Arvid för han är längre än mig!", Language.SWEDISH, Language.ENGLISH);
            } catch(Exception e) {
                translatedText = e.toString();
            }
            return true;
        }
    }
}
