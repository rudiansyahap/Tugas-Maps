/**
 *
 * Nama : Rudiansyah Adi Prasetyo
 * Nim : 10120001
 * Kelas : IF 1
 * Email : rudiansyahadi21@gmail.com
 *
 */

package com.rudiansyah10120001.tugasmaps.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}