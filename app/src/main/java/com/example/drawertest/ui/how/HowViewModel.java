package com.example.drawertest.ui.how;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HowViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("The Home Weather Station Android App works as a Graphic User Interface (GUI) for all the data gathered by the Home Weather Station’s hardware system into a server database. The latest data is displayed at the top in the four blocks. Other old and logged data are shown below in a card view under the four blocks.\n\n" +
                "Now the logged data can be filtered by date and viewed by our user in a well organized manner. \n\n" +
                "GUI Developed by: Prashant Upreti");
    }

    public LiveData<String> getText() {
        return mText;
    }
}