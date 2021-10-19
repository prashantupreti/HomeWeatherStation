package com.example.drawertest.ui.how;

import android.os.Build;
import android.text.Html;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HowViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HowViewModel() {
        mText = new MutableLiveData<>();
        String content="<p>\n" +
                "The <strong>Home Weather Station</strong> Android App works as a    <strong>Graphic User Interface (GUI)</strong> for all the data gathered by\n" +
                "    the Home Weather Station’s hardware system into a server database. The\n" +
                "    latest data is displayed at the top in the four blocks. Other old and\n" +
                "    logged data are shown below in a card view under the four blocks.\n" +
                "</p>\n" +
                "<p>\n" +
                "    We will update more data here describing the hardware and software\n" +
                "    configurations after we are done with the Interim reports.\n" +
                "</p>\n" +
                "<p>\n" +
                "    Thank You!!!\n" +
                "</p>";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mText.setValue(String.valueOf(Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY)));
        } else
            mText.setValue(String.valueOf(Html.fromHtml(content)));
    }

    public LiveData<String> getText() {
        return mText;
    }
}