/**
 *
 * Nama : Rudiansyah Adi Prasetyo
 * Nim : 10120001
 * Kelas : IF 1
 * Email : rudiansyahadi21@gmail.com
 *
 */

package com.rudiansyah10120001.tugasmaps.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}