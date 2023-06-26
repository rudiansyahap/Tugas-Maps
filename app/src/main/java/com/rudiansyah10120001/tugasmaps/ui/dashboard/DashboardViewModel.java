/**
 *
 * Nama : Rudiansyah Adi Prasetyo
 * Nim : 10120001
 * Kelas : IF 1
 * Email : rudiansyahadi21@gmail.com
 *
 */

package com.rudiansyah10120001.tugasmaps.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}