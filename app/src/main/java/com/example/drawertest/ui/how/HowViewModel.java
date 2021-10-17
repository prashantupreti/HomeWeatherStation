package com.example.drawertest.ui.how;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HowViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam vel auctor nisl, luctus maximus nibh. Quisque in elit nisi. Donec blandit aliquam ullamcorper. Donec feugiat porta nibh eget lacinia. Quisque scelerisque eget sapien non fringilla. Morbi eleifend sagittis posuere. Sed non scelerisque erat, in pulvinar lacus. Vivamus suscipit ex vel pharetra sollicitudin. Donec vulputate lobortis fermentum.\n" +
                "\n" +
                "Cras a erat erat. Duis vulputate faucibus eros, ut scelerisque ligula rhoncus non. Quisque vitae magna lacinia risus ultrices blandit. Fusce et ultricies enim. Proin varius turpis at mollis ullamcorper. Donec sem justo, tempor in elementum vel, ullamcorper at sapien. Nulla facilisi. Suspendisse eu felis sed odio dignissim blandit. Maecenas lobortis vel mi sed lobortis. Duis viverra nibh id neque mattis iaculis. Cras vitae convallis magna. Maecenas pretium nisl ut magna interdum, sit amet ullamcorper mauris porttitor. Duis porttitor bibendum luctus. Nunc eleifend sit amet sem ut finibus. Aenean blandit accumsan turpis, ac blandit dui dapibus imperdiet.\n" +
                "\n" +
                "Sed vulputate tincidunt risus, eu dictum nisl posuere at. Etiam commodo nec nunc non fringilla. Nullam sodales auctor arcu et placerat. Maecenas quis ante ligula. Duis justo nulla, mattis convallis condimentum sed, blandit a mi. Mauris eget eros lacus. Vivamus commodo nulla ex, vitae sagittis elit gravida in. Sed aliquam blandit nibh sed tincidunt. Duis lacinia molestie sapien, at pellentesque quam feugiat vitae. Cras interdum rhoncus elit. Fusce dictum rhoncus molestie. Curabitur et arcu vel purus eleifend molestie. Aenean interdum elit dolor. Etiam a ex blandit, dignissim tortor rutrum, egestas mi. Aenean iaculis augue sapien, eget consectetur massa sollicitudin fermentum.\n" +
                "\n" +
                "Etiam sit amet ipsum a nibh faucibus aliquet ut tempus sem. Donec ex nibh, tristique id luctus a, convallis non metus. Quisque vitae vulputate risus. Aenean in auctor metus. Ut aliquet eget enim eget fermentum. Pellentesque nec ante ornare, gravida mauris eu, lacinia diam. Aenean vestibulum sapien augue, quis egestas augue pulvinar in. Aenean risus ligula, venenatis vel luctus vitae, consectetur a lacus.\n" +
                "\n" +
                "Praesent tincidunt quam at pretium rutrum. Duis tempor turpis eu neque maximus finibus. In a neque nec ante rutrum aliquam. Nam maximus mi cursus congue rhoncus. Ut volutpat non mauris sed lobortis. Proin congue metus non justo lacinia, vel efficitur velit pulvinar. Aliquam in magna nibh. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In aliquet vestibulum velit, pulvinar fringilla nisi suscipit quis. Maecenas ut volutpat nisi. Sed vulputate nibh a ipsum volutpat, sit amet bibendum dui dictum. Aliquam erat volutpat. Aenean pulvinar fermentum ante at efficitur. Aliquam et magna justo. Nulla turpis nisi, aliquam vitae orci non, semper tincidunt metus.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}