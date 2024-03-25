package com.example.flearning;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


/*Activity реализует интерфейс OnFragmentSendDataListener,
* для взаимодействия фрагмента ListFragment с другим фрагментом через MainActivity
*
* Здесь мы реализуем метод onSendData() интерфейса*/
public class MainActivity extends AppCompatActivity  implements ListFragment.OnFragmentSendDataListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onSendData(String selectedItem) {
        DetailFragment fragment = (DetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detailFragment);
        if (fragment != null)
            fragment.setSelectedItem(selectedItem);
    }
}